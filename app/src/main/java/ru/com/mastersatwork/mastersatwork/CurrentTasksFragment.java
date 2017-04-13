package ru.com.mastersatwork.mastersatwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CurrentTasksFragment extends Fragment {

    public static final String API_BASE_URL = "http://149.126.103.38";
    private OkHttpClient.Builder httpClient;
    private OkHttpClient client;
    private TaskApi taskApi;
    private TaskCatalogAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<Task> data;

    public static CurrentTasksFragment newInstance() {
        CurrentTasksFragment fragment = new CurrentTasksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_tasks, container, false);

        // Setting custom fonts:
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proximanoval.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        adapter = new TaskCatalogAdapter(getActivity(), new ArrayList<Task>());

        ListView listView = (ListView) view.findViewById(R.id.list_view_current_tasks);

        listView.setAdapter(adapter);

        taskApi = settingRetrofit();

        Call<ArrayList<Task>> call = taskApi.getData();

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Making a call through retrofit.
        call.enqueue(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                if (response.body() != null) {

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
                Intent intent = new Intent(getActivity(), DetailTaskActivity.class);
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

        return view;
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
}
