package net.simplifiedcoding.taskorganizer.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import net.simplifiedcoding.taskorganizer.config.Constants;
import net.simplifiedcoding.taskorganizer.models.Todo;
import net.simplifiedcoding.taskorganizer.room.DatabaseClient;
import net.simplifiedcoding.taskorganizer.room.TodoDao;

public class TodoAsync extends AsyncTask<Void, Void, Void> {

    private Context mCtx;
    private int code;
    private Todo todo;

    public TodoAsync(Context mCtx, Todo todo, int code) {
        this.mCtx = mCtx;
        this.todo = todo;
        this.code = code;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        switch (code) {
            case Constants.CODE_CREATE_TASK:
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .taskDao()
                        .insert(todo);
                break;
            case Constants.CODE_UPDATE_TASK:
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .taskDao()
                        .update(todo);
                break;
            case Constants.CODE_DELETE_TASK:
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .taskDao()
                        .delete(todo);
                break;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        String message = "";

        switch (code) {
            case Constants.CODE_CREATE_TASK:
                message = "Task Saved";
                break;
            case Constants.CODE_UPDATE_TASK:
                message = "Task Updated";
                break;
            case Constants.CODE_DELETE_TASK:
                message = "Task Deleted";
                break;
        }

        Toast.makeText(mCtx, message, Toast.LENGTH_LONG).show();
        mCtx.sendBroadcast(new Intent(Constants.BROADCAST_UPDATE));
    }
}
