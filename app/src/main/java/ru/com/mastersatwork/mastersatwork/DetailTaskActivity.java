package ru.com.mastersatwork.mastersatwork;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedHashMap;


public class DetailTaskActivity extends AppCompatActivity {

    // TODO Implement listView + adapter?

    private TextView orderNumberView;
    private TextView jobView;
    private TextView costView;
    private TextView customerNameView;
    private TextView customerAddressView;
    private TextView customerPhoneView;
    private TextView commentView;
    private String map;
    private String phone;
    private ImageButton buttonPhone;
    private ImageButton buttonMap;

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task_with_list_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Заказ: " + getIntent().getStringExtra("ORDER_ID"));
        getSupportActionBar().setElevation(0);



        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("Клиент", "Константин");
        map.put("Адрес", "Варшавское шоссе 18, к.1., кв. 21");
        map.put("Телефон", "+79266676738");
        map.put("Тип работы:", "Сделать стул");
        map.put("Комментарий 1", "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!");
        map.put("Комментарий 1", "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!");
        map.put("Комментарий 2", "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!");
        map.put("Комментарий 3", "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!" + "Сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул, пожалуйста, сделайте стул!");
        map.put("Дата заказа", "24.10.1988");
        map.put("Стоимость заказа", "1000 р.");


        DetailAdapter adapter = new DetailAdapter(map);
        ListView list = (ListView) findViewById(R.id.detail_task_list_view);
        list.setAdapter(adapter);

        /*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Заказ: " + getIntent().getStringExtra("ORDER_ID"));
        getSupportActionBar().setSubtitle("sdsdsdsd");
        getSupportActionBar().setElevation(0);*/

        /*
        jobView = (TextView) findViewById(R.id.detail_order_title);
        jobView.setText(getIntent().getStringExtra("JOB"));
        costView = (TextView) findViewById(R.id.detail_order_price);
        costView.setText(getIntent().getStringExtra("AMOUNT") + " \u20BD");
        customerNameView = (TextView) findViewById(R.id.detail_client_name);
        customerNameView.setText(getIntent().getStringExtra("CUSTOMER_NAME"));

        String address = getIntent().getStringExtra("CUSTOMER_ADDRESS");
        map = "http://maps.google.co.in/maps?q="+ address;
        customerAddressView = (TextView) findViewById(R.id.detail_client_adress);
        customerAddressView.setText(address);

        buttonMap = (ImageButton) findViewById(R.id.image_button_maps);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(intent);
            }
        });

        phone = getIntent().getStringExtra("CUSTOMER_PHONE");
        customerPhoneView = (TextView) findViewById(R.id.detail_client_phone);
        customerPhoneView.setText(phone);

        buttonPhone = (ImageButton) findViewById(R.id.image_button_phone);
        buttonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        commentView = (TextView) findViewById(R.id.detail_comment);
        commentView.setText(getIntent().getStringExtra("COMMENT"));



        Button takeOrderButton = (Button) findViewById(R.id.button_take_order);
        takeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference().child("orders");

                String taskId = getIntent().getStringExtra("FIREBASE_ORDER_KEY");
                Logger.d("ORDER ID TO UPDATE: " + taskId);

                Task task = new Task();
                task.setAmount(getIntent().getIntExtra("AMOUNT", 0));
                task.setJob(getIntent().getStringExtra("JOB"));
                task.setComment(getIntent().getStringExtra("COMMENT"));
                task.setCustomersName(getIntent().getStringExtra("CUSTOMER_NAME"));
                task.setCustomersAddress(getIntent().getStringExtra("CUSTOMER_ADDRESS"));
                task.setCustomersPhone(getIntent().getStringExtra("CUSTOMER_PHONE"));
                task.setAssignedMaster(true);
                task.setUid(getIntent().getStringExtra("USER_ID"));

                Logger.d("Button is pressed");

                //databaseReference.push().setValue(task);
                databaseReference.child(taskId).setValue(task);
                database.getReference().child("assigned_orders").child(taskId).setValue(task);

                // writing assigned order into master profile
                DatabaseReference masterReference = database.getReference().child("masters").child(task.getUid());
                masterReference.child("assignedOrders").child(taskId).setValue(true);
            }
        });

        */
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
