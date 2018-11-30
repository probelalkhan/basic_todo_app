package net.simplifiedcoding.taskorganizer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.simplifiedcoding.taskorganizer.R;
import net.simplifiedcoding.taskorganizer.activities.TaskActivity;
import net.simplifiedcoding.taskorganizer.config.Constants;
import net.simplifiedcoding.taskorganizer.models.Todo;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private Context mCtx;
    private List<Todo> todoList;

    public TaskListAdapter(Context mCtx, List<Todo> todoList) {
        this.mCtx = mCtx;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.textViewDesc.setText(todo.getDesc());
        holder.textViewDate.setText(todo.getFinishBy());

        if (todo.isFinished()) {
            holder.textViewIsFinished.setVisibility(View.VISIBLE);
        } else {
            holder.textViewIsFinished.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDesc, textViewDate, textViewIsFinished;

        public TaskViewHolder(View itemView) {
            super(itemView);

            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewIsFinished = itemView.findViewById(R.id.textViewIsFinished);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mCtx, TaskActivity.class);
                    intent.putExtra(Constants.KEY_TASK, todoList.get(getAdapterPosition()));
                    mCtx.startActivity(intent);
                }
            });
        }
    }
}
