package net.simplifiedcoding.taskorganizer.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.simplifiedcoding.taskorganizer.R;
import net.simplifiedcoding.taskorganizer.async.TodoAsync;
import net.simplifiedcoding.taskorganizer.config.Constants;
import net.simplifiedcoding.taskorganizer.fragments.HomeFragment;
import net.simplifiedcoding.taskorganizer.models.Todo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private LinearLayout bottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private FloatingActionButton fabAdd;
    private EditText editTextDesc;
    private TextView textViewDate;
    private ImageButton buttonDate, buttonCamera, buttonGallery, buttonSave;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Today");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        bottomSheet = findViewById(R.id.bottom_sheet);


        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText("");
        editTextDesc = bottomSheet.findViewById(R.id.editTextTask);
        buttonDate = bottomSheet.findViewById(R.id.buttonDate);
        buttonCamera = bottomSheet.findViewById(R.id.buttonCamera);
        buttonGallery = bottomSheet.findViewById(R.id.buttonGallery);
        buttonSave = bottomSheet.findViewById(R.id.buttonSave);

        sheetBehavior = BottomSheetBehavior.from(bottomSheet);


        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(this);
        buttonDate.setOnClickListener(this);
        buttonCamera.setOnClickListener(this);
        buttonGallery.setOnClickListener(this);
        buttonSave.setOnClickListener(this);


        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    editTextDesc.requestFocus();
                    editTextDesc.setShowSoftInputOnFocus(true);
                    fabAdd.animate().scaleX(0).scaleY(0).setDuration(0).start();
                    fabAdd.setVisibility(View.GONE);
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    fabAdd.animate().scaleX(1).scaleY(1).setDuration(0).start();
                    fabAdd.setVisibility(View.VISIBLE);
                } else if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    fabAdd.animate().scaleX(1).scaleY(1).setDuration(0).start();
                    fabAdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, new HomeFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAdd:
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.buttonDate:
                datePickerDialog.show();
                break;
            case R.id.buttonCamera:
                break;
            case R.id.buttonGallery:
                break;
            case R.id.buttonSave:
                saveTask();
                break;
        }
    }

    private void saveTask() {
        String desc = editTextDesc.getText().toString().trim();
        String finishBy = textViewDate.getText().toString().trim();

        if (desc.isEmpty()) {
            editTextDesc.setError("Field required...");
            editTextDesc.requestFocus();
            return;
        }


        if (finishBy.isEmpty()) {
            Toast.makeText(this, "Select a date...", Toast.LENGTH_LONG).show();
            return;
        }


        Todo todo = new Todo();
        todo.setDesc(desc);

        todo.setFinishBy(finishBy);
        todo.setImage("sdf");


        TodoAsync todoAsync = new TodoAsync(this, todo, Constants.CODE_CREATE_TASK);
        todoAsync.execute();

        hideKeyboard();
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        textViewDate.setText(year + "/" + month + "/" + dayOfMonth);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
