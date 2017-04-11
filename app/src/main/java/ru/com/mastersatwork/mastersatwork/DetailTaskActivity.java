package ru.com.mastersatwork.mastersatwork;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailTaskActivity extends AppCompatActivity {

    private TextView orderNumberView;
    private TextView jobView;
    private TextView costView;
    private TextView customerNameView;
    private TextView customerAddressView;
    private TextView customerPhoneView;
    private TextView commentView;
    String map;
    String phone;
    private ImageButton buttonPhone;
    private ImageButton buttonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        // Setting custom fonts:
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proximanoval.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderNumberView = (TextView) findViewById(R.id.detail_order_number);
        orderNumberView.setText("Заказ: " + getIntent().getStringExtra("ORDER_ID"));
        jobView = (TextView) findViewById(R.id.detail_order_title);
        jobView.setText(getIntent().getStringExtra("JOB"));
        costView = (TextView) findViewById(R.id.detail_order_price);
        costView.setText(getIntent().getStringExtra("COST") + " \u20BD");
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

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
