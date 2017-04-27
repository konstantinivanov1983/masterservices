package ru.com.mastersatwork.mastersatwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import ru.com.mastersatwork.mastersatwork.data.Task;

public class TaskCatalogAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> tasks;

    public static class ViewHolder {
        TextView job;
        TextView address;
        TextView cost;
    }

    public TaskCatalogAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Получаем данные по опр. позиции в массиве
        Task task = getItem(position);

        ViewHolder viewHolder;

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

        viewHolder.job.setText(task.getJob());
        viewHolder.address.setText(task.getCustomersAddress());
        Logger.d("Current price: " + task.getAmount());
        viewHolder.cost.setText(String.valueOf(task.getAmount()) + " \u20BD");

        return convertView;
    }

    public void removeItemByFirebaseId(String firebaseId) {
        if (tasks != null & tasks.size()>0) {
            for (Task task : tasks) {
                if (task.getOrderKey().equals(firebaseId)) {
                    tasks.remove(task);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }
}
