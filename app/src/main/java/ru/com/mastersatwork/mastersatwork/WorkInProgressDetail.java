package ru.com.mastersatwork.mastersatwork;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WorkInProgressDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task_with_list_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView goneView = (TextView) findViewById(R.id.detail_task_refuse_order);
        goneView.setVisibility(View.GONE);
        TextView closeOrder = (TextView) findViewById(R.id.detail_task_take_order);
        closeOrder.setText("Закрыть заказ");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
