package ru.com.mastersatwork.mastersatwork;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import ru.com.mastersatwork.mastersatwork.data.Customer;
import ru.com.mastersatwork.mastersatwork.data.Task;
import ru.com.mastersatwork.mastersatwork.data.Work;


public class WorkInProgressFragment extends Fragment implements ClosingOrderDialogFragment.EditCommentDialogListener {

    private String masterId;

    private ChildEventListener childEventListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ordersFirebaseRef;
    private Query orderQuery;

    private WorkInProgressAdapter adapter;

    public static WorkInProgressFragment newInstance(String userId) {
        WorkInProgressFragment fragment = new WorkInProgressFragment();
        Bundle args = new Bundle();
        args.putString("USER_ID", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        masterId = getArguments().getString("USER_ID");
        adapter = new WorkInProgressAdapter(getContext(), new ArrayList<Task>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.work_in_progress_fragment, container, false);


        ListView listView = (ListView) view.findViewById(R.id.list_view_work_in_progress);
        View emptyView = view.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        adapter.setCallback(new WorkInProgressAdapter.CallBackFromAdapter() {
            @Override
            public void showAlertDialogInsideAdapter(String order, int price) {
                FragmentManager fm = getFragmentManager();
                ClosingOrderDialogFragment fragment = ClosingOrderDialogFragment.newInstance(order, price, order);
                fragment.setTargetFragment(WorkInProgressFragment.this, 300);
                fragment.show(fm, "some tag");
            }

            @Override
            public void openDetailWorkInProgressActivityInTheAdapter(Customer c, Work work, String idOrder) {
                Intent intent = new Intent(getActivity(), WorkInProgressDetail.class);
                intent.putExtra("CustomerName", c.getName());
                intent.putExtra("CustomerPubAddress", c.getPublicAddress());
                intent.putExtra("CustomerPrivAddress", c.getPrivateAddress());
                intent.putExtra("CustomerPhone", c.getPhone());
                intent.putExtra("ServiceTitle", work.getName());
                intent.putExtra("IdOrder", idOrder);
                intent.putExtra("MasterId", masterId);
                startActivity(intent);
            }

            @Override
            public void dialClient(String phone) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ordersFirebaseRef = firebaseDatabase.getReference().child("orders");
        orderQuery = ordersFirebaseRef.orderByChild("Master").equalTo(masterId);
        orderQuery.keepSynced(true);

        attachOrderListener();

        return view;
    }

    private void attachOrderListener() {
        if (childEventListener == null) {
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Task order = dataSnapshot.getValue(Task.class);

                    if (order.getStatus() == 1) {
                        adapter.add(order);
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    adapter.removeItemByFirebaseId(dataSnapshot.getKey());
                    orderQuery = ordersFirebaseRef.orderByChild("status").equalTo(1);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Task changedOrder = dataSnapshot.getValue(Task.class);
                    if (changedOrder.getStatus() != 1) {
                        adapter.removeItemByFirebaseId(changedOrder.getIdOrders());
                        orderQuery = ordersFirebaseRef.orderByChild("status").equalTo(1);
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };

            orderQuery.addChildEventListener(childEventListener);
        }

    }

    private void detachOrderListener() {
        if (childEventListener != null) {
            orderQuery.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.setCallback(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        detachOrderListener();
        adapter.clear();
    }


    @Override
    public void onFinishCommentDialog(int receivedPrice, String receivedComment, String ordId) {
        Toast.makeText(getContext(), "Ваш комментарий: " + receivedComment +"\nПолученная Вами сумма: " + receivedPrice, Toast.LENGTH_LONG).show();
        DatabaseReference orderReference = firebaseDatabase.getReference().child("orders").child(ordId);
        orderReference.child("status").setValue(2);
        orderReference.child("MasterComment").setValue(receivedComment);
        orderReference.child("TotalAmount").setValue(receivedPrice);
    }
}
