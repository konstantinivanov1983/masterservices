package ru.com.mastersatwork.mastersatwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import ru.com.mastersatwork.mastersatwork.data.Task;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TasksActivity extends AppCompatActivity {

  // TODO Main screen before anything starts.
  // TODO Show message if there's no internet connection
  // TODO Sql statement to read data according to specific criteria
  // TODO Two fragments for the main screen: current available orders + user assigned orders
  // TODO SQLite database for user assigned
  // TODO For authorization screen — check open source projects

    public static final String API_BASE_URL = "http://149.126.103.38";
    private OkHttpClient.Builder httpClient;
    private OkHttpClient client;
    private TaskApi taskApi;
    private TaskCatalogAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<Task> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // Setting custom fonts:
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proximanoval.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        adapter = new TaskCatalogAdapter(this, new ArrayList<Task>());

        ListView listView = (ListView) findViewById(R.id.list_view);

        listView.setAdapter(adapter);

        taskApi = settingRetrofit();

        Call<ArrayList<Task>> call = taskApi.getData();


        // Making a call through retrofit.
        call.enqueue(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                if (response.body() != null) {
                    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                    progressBar.setVisibility(View.INVISIBLE);
                    data = response.body();
                    adapter.addAll(data);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Task>> call, Throwable t) {
                Logger.d("Retrofit call onFailure: " + t.getMessage());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(TasksActivity.this, DetailTaskActivity.class);
                intent.putExtra("ORDER_ID", data.get(position).getOrderNumber());
                intent.putExtra("CUSTOMER_NAME", data.get(position).getCustomersName());
                intent.putExtra("CUSTOMER_ADDRESS", data.get(position).getCustomersAddress());
                intent.putExtra("CUSTOMER_PHONE", data.get(position).getCustomersPhone());
                intent.putExtra("JOB", data.get(position).getJob());
                intent.putExtra("AMOUNT", data.get(position).getAmount());
                intent.putExtra("COMMENT", data.get(position).getComment());
                startActivity(intent);
            }
        });


    }

    private TaskApi settingRetrofit() {

        httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        return retrofit.create(TaskApi.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog_task, menu);
        return true;
    }
}
