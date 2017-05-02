package ru.com.mastersatwork.mastersatwork;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;


public class DetailTaskActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String masterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task_with_list_view);

        masterId = getIntent().getStringExtra("MASTER_ID");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Заказ: " + getIntent().getStringExtra("ORDER_ID_FIREBASE"));

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
         map.put("Услуга", getIntent().getStringExtra("JOB"));
        map.put("Стоимость", String.valueOf(getIntent().getIntExtra("AMOUNT", 0)));
        map.put("Клиент", getIntent().getStringExtra("CUSTOMER_NAME"));
        map.put("Адрес", getIntent().getStringExtra("CUSTOMER_ADDRESS"));
        map.put("Комментарий", getIntent().getStringExtra("COMMENT"));

        DetailAdapter adapter = new DetailAdapter(map);
        ListView list = (ListView) findViewById(R.id.detail_task_list_view);
        list.setAdapter(adapter);


        // Buttons part

        Button takeOrderButton = (Button) findViewById(R.id.detail_task_take_order);
        takeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailTaskActivity.this);
                builder.setTitle("Внимание!");
                builder.setMessage("Вы действительно хотите взять данный заказ?");
                builder.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference().child("orders");

                        String taskId = getIntent().getStringExtra("ORDER_ID_FIREBASE");

                        //databaseReference.push().setValue(task);
                        databaseReference.child(taskId).child("status").setValue(1);
                        databaseReference.child(taskId).child("Master").setValue(masterId);

                        openMain();
                    }
                });
                builder.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
