package com.lyrnic.sqlimplementation.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lyrnic.sqlimplementation.R;
import com.lyrnic.sqlimplementation.adapters.MyAdapter;
import com.lyrnic.sqlimplementation.database.MyDatabase;

public class MainActivity extends AppCompatActivity {

    public static int ADD_PERSON_ACTIVITY_LAUNCH_CODE = 32901;
    FloatingActionButton fab;
    RecyclerView personsRecyclerView;
    MyDatabase myDatabase;
    MyAdapter adapter;
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        fab = findViewById(R.id.add_person_fab);
        personsRecyclerView = findViewById(R.id.persons_recycler_view);
        myDatabase = MyDatabase.getDatabase(this);
        adapter = new MyAdapter(myDatabase.getAllPersons());

        initActivityResultReceiver();

        fab.setOnClickListener((view) -> {
            Intent intent = new Intent(getBaseContext(), AddPersonActivity.class);

            activityResultLauncher.launch(intent);
            
        });

        personsRecyclerView.setAdapter(adapter);

        personsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initActivityResultReceiver(){
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode() == ADD_PERSON_ACTIVITY_LAUNCH_CODE){
                        Intent data = result.getData();
                        if(data == null){
                            Toast.makeText(getBaseContext(),"error, no data received but result is ok.",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        boolean newPerson = data.getBooleanExtra(AddPersonActivity.NEW_PERSON_ADDED_EXTRA,false);

                        if(newPerson){
                            adapter.setPersons(myDatabase.getAllPersons());
                        }
                    }
                });
    }
}