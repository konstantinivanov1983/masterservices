package ru.com.mastersatwork.mastersatwork;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.com.mastersatwork.mastersatwork.data.Task;

public interface TaskApi {

    @GET("/MastersServices/hs/Root/GetNewOrders/1")
    Call<ArrayList<Task>> getData();

}
