package ru.com.mastersatwork.mastersatwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.com.mastersatwork.mastersatwork.data.Task;

public class TaskCatalogAdapter extends ArrayAdapter<Task> {

    public TaskCatalogAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Получаем данные по опр. позиции в массиве
        Task task = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
        }

        TextView job = (TextView) convertView.findViewById(R.id.list_item_job);
        TextView adress = (TextView) convertView.findViewById(R.id.list_item_adress);
        TextView cost = (TextView) convertView.findViewById(R.id.list_item_amount);

        job.setText(task.getJob());
        adress.setText(task.getCustomersAddress());
        cost.setText(task.getAmount());

        return convertView;
    }
}
