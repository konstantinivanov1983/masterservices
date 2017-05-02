package ru.com.mastersatwork.mastersatwork;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ru.com.mastersatwork.mastersatwork.data.Customer;
import ru.com.mastersatwork.mastersatwork.data.Task;
import ru.com.mastersatwork.mastersatwork.data.Work;

public class WorkInProgressAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> data;
    private FirebaseDatabase database;
    private CallBackFromAdapter mCallback;

    public static class ViewHolder {
        TextView job;
        TextView clientAddress;
        TextView cost;
        TextView clientName;
        TextView clientPhone;
        TextView closeButton;
        TextView detailButton;
        TextView orderNumber;
    }

    public WorkInProgressAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
        data = tasks;
        database = FirebaseDatabase.getInstance();
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Task task = getItem(position);

        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.work_in_progress_item, parent, false);

            // TODO доделать адаптер
            viewHolder.job = (TextView) convertView.findViewById(R.id.work_in_progress_job_title);
            viewHolder.clientAddress = (TextView) convertView.findViewById(R.id.work_in_progress_client_address);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.work_in_progress_job_price);
            viewHolder.clientName = (TextView) convertView.findViewById(R.id.work_in_progress_client_name);
            viewHolder.clientPhone = (TextView) convertView.findViewById(R.id.work_in_progress_client_phone);
            viewHolder.closeButton = (TextView) convertView.findViewById(R.id.work_in_progress_close_order);
            viewHolder.detailButton = (TextView) convertView.findViewById(R.id.work_in_progress_detail_button);
            viewHolder.orderNumber = (TextView) convertView.findViewById(R.id.work_in_progress_order_number);

            convertView.setTag(viewHolder);

        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cost.setText(String.valueOf(task.getInitialAmount()) + " \u20BD");
        viewHolder.orderNumber.setText(task.getIdOrders());

        final DatabaseReference workRef = database.getReference().child("works").child(task.getWork());
        final boolean[] orderDownloadFinished = {false};
        workRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Work work = dataSnapshot.getValue(Work.class);
                if (work != null) {
                    viewHolder.job.setText(Html.fromHtml("<b>Услуга: </b>" + work.getName()));
                    orderDownloadFinished[0] = true;
                }

                final DatabaseReference custRef = database.getReference().child("customers").child(task.getCustomer());
                custRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Customer customer = dataSnapshot.getValue(Customer.class);
                        if (customer != null ){
                            viewHolder.clientName.setText(Html.fromHtml("<b>Клиент: </b>" + customer.getName()));
                            viewHolder.clientAddress.setText(Html.fromHtml("<b>Адрес: </b>" + customer.getPublicAddress() + " " + customer.getPrivateAddress()));
                            viewHolder.clientPhone.setText(Html.fromHtml("<b>Телефон: </b>" + customer.getPhone()));
                            custRef.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                if (orderDownloadFinished[0] == true) {
                    workRef.removeEventListener(this);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        viewHolder.clientAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "Будем смотреть на карте: " + viewHolder.clientAddress.getText(), Toast.LENGTH_SHORT).show();
                // TODO Create the intent to use google maps
            }
        });

        viewHolder.clientPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.dialClient(task.getCustomer());
            }
        });

        viewHolder.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.showAlertDialogInsideAdapter(task.getIdOrders(), task.getInitialAmount());
            }
        });

        viewHolder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.openDetailWorkInProgressActivityInTheAdapter();

            }
        });

        return convertView;
    }

    public void removeItemByFirebaseId(String idOrder) {
        if (data != null & data.size()>0) {
            for (Task task : data) {
                if (task.getIdOrders().equals(idOrder)) {
                    data.remove(task);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public interface CallBackFromAdapter {

        void showAlertDialogInsideAdapter(String order, int price);
        void openDetailWorkInProgressActivityInTheAdapter();
        void dialClient(String number);
    }

    public void setCallback(CallBackFromAdapter callback) {
        mCallback = callback;
    }

}