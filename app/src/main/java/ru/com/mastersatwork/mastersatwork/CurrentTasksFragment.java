package ru.com.mastersatwork.mastersatwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import ru.com.mastersatwork.mastersatwork.data.Customer;
import ru.com.mastersatwork.mastersatwork.data.Task;
import ru.com.mastersatwork.mastersatwork.data.Work;
import ru.com.mastersatwork.mastersatwork.utils.Utils;

public class CurrentTasksFragment extends Fragment {

    private TaskCatalogAdapter adapter;
    private ProgressBar progressBar;
    private String uId;
    private Customer customer;

    private static FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrdersDatabaseReference;
    private ChildEventListener mChildEventListener;
    private Query query;
    private DatabaseReference mCustomerDbRef;


    public static CurrentTasksFragment newInstance(String userId) {
        CurrentTasksFragment fragment = new CurrentTasksFragment();
        Bundle args = new Bundle();
        args.putString("USER_ID", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uId = getArguments().getString("USER_ID");
        adapter = new TaskCatalogAdapter(getActivity(), new ArrayList<Task>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_tasks, container, false);

        Logger.d("Authenticated user id : " + uId);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_in_current_tasks);

        ListView listView = (ListView) view.findViewById(R.id.list_view_current_tasks);

        // Check internet connection
        if (!Utils.isOnline(getContext())) {
            View emptyView = view.findViewById(R.id.no_connnection_view);
            listView.setEmptyView(emptyView);
            progressBar.setVisibility(View.GONE);
            Logger.d("No connection");
        } else {

            View emptyView = view.findViewById(R.id.no_connnection_view);
            emptyView.setVisibility(View.GONE);


            Logger.d("Else else else");

            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mOrdersDatabaseReference = mFirebaseDatabase.getReference().child("orders");
            mCustomerDbRef = mFirebaseDatabase.getReference().child("customers");

            query = mOrdersDatabaseReference.orderByChild("status").equalTo(0);
            query.keepSynced(true);

            listView.setAdapter(adapter);

            attachDatabaseReadListener();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {

                    // Going to Detail Activity for a list item
                    final boolean[] custDownloadFinished = {false};

                    final Intent intent = new Intent(getActivity(), DetailTaskActivity.class);
                    intent.putExtra("ORDER_NUM", "NONUMNONUM");
                    intent.putExtra("AMOUNT", adapter.getItem(position).getInitialAmount());
                    intent.putExtra("COMMENT", adapter.getItem(position).getCustomerComment());
                    intent.putExtra("MASTER_ID", uId);

                    final DatabaseReference workRef = mFirebaseDatabase.getReference().child("works").child(adapter.getItem(position).getWork());
                    workRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Work work = dataSnapshot.getValue(Work.class);
                            if (work != null) {
                                intent.putExtra("JOB", work.getName());
                                custDownloadFinished[0] = true;
                            }

                            final DatabaseReference custRef = mFirebaseDatabase.getReference().child("customers").child(adapter.getItem(position).getCustomer());
                            custRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    customer = dataSnapshot.getValue(Customer.class);
                                    if (customer != null ){
                                        intent.putExtra("CUSTOMER_NAME", customer.getName());
                                        intent.putExtra("CUSTOMER_ADDRESS", customer.getPublicAddress());
                                        intent.putExtra("ORDER_ID_FIREBASE", adapter.getItem(position).getIdOrders());
                                        custRef.removeEventListener(this);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            });

                            if (custDownloadFinished[0] == true) {
                                workRef.removeEventListener(this);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                }
            });
        }

        return view;
    }


    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (progressBar.getVisibility() == View.VISIBLE) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    Task newOrder = dataSnapshot.getValue(Task.class);

                    //Adding only those orders which have no master assigned:
                    if (newOrder.getMaster() == null) {
                        adapter.add(newOrder);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    adapter.removeItemByFirebaseId(dataSnapshot.getKey());
                    query = mOrdersDatabaseReference.orderByChild("status").equalTo(0);

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
        }

        //mOrdersDatabaseReference.addChildEventListener(mChildEventListener);
        query.addChildEventListener(mChildEventListener);
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            query.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
        adapter.clear();
    }
}