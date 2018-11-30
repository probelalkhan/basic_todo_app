package net.simplifiedcoding.taskorganizer.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.simplifiedcoding.taskorganizer.R;
import net.simplifiedcoding.taskorganizer.adapters.TaskListAdapter;
import net.simplifiedcoding.taskorganizer.config.Constants;
import net.simplifiedcoding.taskorganizer.models.Todo;
import net.simplifiedcoding.taskorganizer.room.DatabaseClient;
import net.simplifiedcoding.taskorganizer.room.TodoDao;

import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Todo> todoList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        getTasks();

        Objects.requireNonNull(getActivity()).registerReceiver(update, new IntentFilter(Constants.BROADCAST_UPDATE));

    }

    private BroadcastReceiver update = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getTasks();
        }
    };

    private void getTasks() {
        class GetTasksAsync extends AsyncTask<Void, Void, List<Todo>> {

            @Override
            protected List<Todo> doInBackground(Void... voids) {

                return DatabaseClient.getInstance(getActivity()).getAppDatabase()
                        .taskDao()
                        .getAll();

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                TaskListAdapter adapter = new TaskListAdapter(getActivity(), todos);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasksAsync gta = new GetTasksAsync();
        gta.execute();
    }
}
