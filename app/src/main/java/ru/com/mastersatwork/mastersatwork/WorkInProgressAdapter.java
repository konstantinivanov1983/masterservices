package ru.com.mastersatwork.mastersatwork;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import ru.com.mastersatwork.mastersatwork.R;
import ru.com.mastersatwork.mastersatwork.data.Task;

public class WorkInProgressAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> data;

    public static class ViewHolder {
        TextView job;
        TextView clientAddress;
        TextView cost;
        TextView clientName;
        TextView clientPhone;
    }

    public WorkInProgressAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
        data = tasks;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.work_in_progress_item, parent, false);

            // TODO доделать адаптер
            viewHolder.job = (TextView) convertView.findViewById(R.id.work_in_progress_job_title);
            viewHolder.clientAddress = (TextView) convertView.findViewById(R.id.work_in_progress_client_address);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.work_in_progress_job_price);
            viewHolder.clientName = (TextView) convertView.findViewById(R.id.work_in_progress_client_name);
            viewHolder.clientPhone = (TextView) convertView.findViewById(R.id.work_in_progress_client_phone);

            convertView.setTag(viewHolder);

        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.job.setText(Html.fromHtml("<b>Услуга: </b>" + task.getJob()));
        viewHolder.clientAddress.setText(Html.fromHtml("<b>Адрес: </b>" + task.getCustomersAddress()));
        viewHolder.cost.setText(String.valueOf(task.getAmount()) + " \u20BD");
        viewHolder.clientName.setText(Html.fromHtml("<b>Клиент: </b>" + task.getCustomersName()));
        viewHolder.clientPhone.setText(Html.fromHtml("<b>Телефон: </b>" + task.getCustomersPhone()));

        return convertView;
    }
}