package ru.com.mastersatwork.mastersatwork;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.com.mastersatwork.mastersatwork.data.Task;


public class WorkInProgressFragment extends Fragment implements ClosingOrderDialogFragment.EditCommentDialogListener {

    private WorkInProgressAdapter adapter;

    public WorkInProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.work_in_progress_fragment, container, false);


        // With fake data:
        Task task = new Task();
        task.setAmount(1000);
        task.setCustomersAddress("Варшавское шоссе, 18, к.1, кв. 101");
        task.setJob("Покрасить стены");
        task.setCustomersName("Евгений Козлов");
        task.setCustomersPhone("89266676738");

        Task task1 = new Task();
        task1.setAmount(1000);
        task1.setCustomersAddress("Адрес: Варшавское шоссе, 18, к.1");
        task1.setJob("Покрасить стены + положить паркет");
        task1.setCustomersName("Евгений Козлов");
        task1.setCustomersPhone("89266676738");

        Task task2 = new Task();
        task2.setAmount(1000);
        task2.setCustomersAddress("Варшавское шоссе, 18, к.1");
        task2.setJob("Покрасить стены и перевезти мебель");
        task2.setCustomersName("Евгений Козлов");
        task2.setCustomersPhone("89266676738");

        Task task3 = new Task();
        task3.setAmount(1000);
        task3.setCustomersAddress("Варшавское шоссе, 18, к.1");
        task3.setJob("Ремонт на балконе (застеклить)");
        task3.setCustomersName("Евгений Козлов");
        task3.setCustomersPhone("89266676738");

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);


        ListView listView = (ListView) view.findViewById(R.id.list_view_work_in_progress);
        View emptyView = view.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        adapter = new WorkInProgressAdapter(getContext(), tasks);
        adapter.setCallback(new WorkInProgressAdapter.CallBackFromAdapter() {
            @Override
            public void showAlertDialogInsideAdapter() {
                FragmentManager fm = getChildFragmentManager();
                ClosingOrderDialogFragment fragment = ClosingOrderDialogFragment.newInstance("Закрытие заказа № 2410198819");
                fragment.setTargetFragment(WorkInProgressFragment.this, 300);
                fragment.show(fm, "some tag");
            }
        });

        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.setCallback(null);
    }

    @Override
    public void onFinishCommentDialog(String inputText) {
        Toast.makeText(getContext(), "Here's your comment: " + inputText, Toast.LENGTH_SHORT).show();
    }
}
