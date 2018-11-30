package net.simplifiedcoding.taskorganizer.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.simplifiedcoding.taskorganizer.R;
import net.simplifiedcoding.taskorganizer.async.TodoAsync;
import net.simplifiedcoding.taskorganizer.config.Constants;
import net.simplifiedcoding.taskorganizer.models.Todo;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDesc;
    private TextView textViewStatus, textViewFinishBy;
    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        editTextDesc = findViewById(R.id.editTextDesc);

        textViewStatus = findViewById(R.id.textViewStatus);
        textViewFinishBy = findViewById(R.id.textViewFinishBy);

        findViewById(R.id.buttonDelete).setOnClickListener(this);
        findViewById(R.id.buttonUpdate).setOnClickListener(this);
        findViewById(R.id.buttonFinish).setOnClickListener(this);

        todo = (Todo) getIntent().getSerializableExtra(Constants.KEY_TASK);

        if (todo.isFinished()) {
            textViewStatus.setText("Status: Finished");
        } else {
            textViewStatus.setText("Status: Not Finished");
        }

        textViewFinishBy.setText("Finish By: " + todo.getFinishBy());

        editTextDesc.setText(todo.getDesc());

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.buttonDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure?");
                builder.setMessage("The action is permanent");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TodoAsync todoAsync = new TodoAsync(TaskActivity.this, todo, Constants.CODE_DELETE_TASK);
                        todoAsync.execute();
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

                break;
            case R.id.buttonUpdate:
                String desc = editTextDesc.getText().toString().trim();

                if (desc.isEmpty()) {
                    editTextDesc.setError("Field required...");
                    editTextDesc.requestFocus();
                    return;
                }

                todo.setDesc(desc);
                TodoAsync todoAsync = new TodoAsync(TaskActivity.this, todo, Constants.CODE_UPDATE_TASK);
                todoAsync.execute();
                finish();
                break;

            case R.id.buttonFinish:
                todo.setFinished(true);
                TodoAsync todoAsync1 = new TodoAsync(TaskActivity.this, todo, Constants.CODE_UPDATE_TASK);
                todoAsync1.execute();
                finish();
                break;
        }
    }


}
