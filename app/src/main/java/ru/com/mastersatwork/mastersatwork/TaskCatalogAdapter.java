package ru.com.mastersatwork.mastersatwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import ru.com.mastersatwork.mastersatwork.data.Customer;
import ru.com.mastersatwork.mastersatwork.data.Task;
import ru.com.mastersatwork.mastersatwork.data.Work;

public class TaskCatalogAdapter extends ArrayAdapter<Task> {


    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Task> tasks;
    private Customer customer;

    public static class ViewHolder {
        TextView job;
        TextView address;
        TextView cost;
    }

    public TaskCatalogAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
        this.tasks = tasks;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Получаем данные по опр. позиции в массиве
        final Task task = getItem(position);

        final ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);

            viewHolder.job = (TextView) convertView.findViewById(R.id.list_item_job);
            viewHolder.address = (TextView) convertView.findViewById(R.id.list_item_adress);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.list_item_amount);

            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DatabaseReference workRef = firebaseDatabase.getReference().child("works").child(task.getWork());
        workRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Work work = dataSnapshot.getValue(Work.class);
                if (work != null) {
                    viewHolder.job.setText(work.getName());
                    notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("customers").child(task.getCustomer());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customer = dataSnapshot.getValue(Customer.class);
                if (customer != null ){
                    viewHolder.address.setText(customer.getPublicAddress());
                    viewHolder.cost.setText(String.valueOf(task.getInitialAmount()) + " \u20BD");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        return convertView;
    }

    public void removeItemByFirebaseId(String idOrder) {
        if (tasks != null & tasks.size()>0) {
            for (Task task : tasks) {
                if (task.getIdOrders().equals(idOrder)) {
                    tasks.remove(task);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }
}