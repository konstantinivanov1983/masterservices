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
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import ru.com.mastersatwork.mastersatwork.data.Task;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CurrentTasksFragment extends Fragment {

    public static final String API_BASE_URL = "http://149.126.103.38";
    private OkHttpClient.Builder httpClient;
    private TaskApi taskApi;
    private TaskCatalogAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<Task> data;
    private String uId;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrdersDatabaseReference;
    private ChildEventListener mChildEventListener;
    private Query query;


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

        // Setting custom fonts:
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proximanoval.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Logger.d("Authenticated user id : " + uId);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mOrdersDatabaseReference = mFirebaseDatabase.getReference().child("orders");

        query = mOrdersDatabaseReference.orderByChild("assignedMaster").equalTo(false);
        query.keepSynced(true);


        ListView listView = (ListView) view.findViewById(R.id.list_view_current_tasks);

        listView.setAdapter(adapter);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        attachDatabaseReadListener();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailTaskActivity.class);
                intent.putExtra("ORDER_ID", adapter.getItem(position).getOrderNumber());
                intent.putExtra("FIREBASE_ORDER_KEY", adapter.getItem(position).getOrderKey());
                Logger.d("ORDER_ID in CurrentTasksFragment: " + adapter.getItem(position).getOrderNumber());
                intent.putExtra("CUSTOMER_NAME", adapter.getItem(position).getCustomersName());
                intent.putExtra("CUSTOMER_ADDRESS", adapter.getItem(position).getCustomersAddress());
                intent.putExtra("CUSTOMER_PHONE", adapter.getItem(position).getCustomersPhone());
                intent.putExtra("JOB", adapter.getItem(position).getJob());
                intent.putExtra("AMOUNT", adapter.getItem(position).getAmount());
                intent.putExtra("COMMENT", adapter.getItem(position).getComment());
                intent.putExtra("USER_ID", uId);
                startActivity(intent);
            }
        });

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
                    String key = dataSnapshot.getKey();
                    newOrder.setOrderKey(key);
                    Logger.d("dataSnapshot.getKey(): " + dataSnapshot.getKey());
                    Logger.d("Order ID in onChildAdded: " + newOrder.getOrderKey());

                    //Adding only those orders which have no master assigned:
                    if (newOrder.getAssignedMaster() == false) {
                        adapter.add(newOrder);
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    adapter.removeItemByFirebaseId(dataSnapshot.getKey());
                    query = mOrdersDatabaseReference.orderByChild("assignedMaster").equalTo(false);

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
            //mOrdersDatabaseReference.removeEventListener(mChildEventListener);
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
