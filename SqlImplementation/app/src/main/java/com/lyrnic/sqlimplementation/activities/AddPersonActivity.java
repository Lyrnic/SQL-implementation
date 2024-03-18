package com.lyrnic.sqlimplementation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lyrnic.sqlimplementation.R;
import com.lyrnic.sqlimplementation.database.MyDatabase;
import com.lyrnic.sqlimplementation.model.Person;

public class AddPersonActivity extends AppCompatActivity {

    public static String NEW_PERSON_ADDED_EXTRA = "new_person";
    EditText nameEditText, ageEditText;
    Button saveButton;
    MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        init();
    }
    public void init(){
        nameEditText = findViewById(R.id.nameField);
        ageEditText = findViewById(R.id.ageField);

        saveButton = findViewById(R.id.save_button);

        myDatabase = MyDatabase.getDatabase(this);

        saveButton.setOnClickListener((view) -> {
            String name = nameEditText.getText().toString().trim();
            int age;
            try{
                 age = Integer.parseInt(ageEditText.getText().toString().trim());
            }
            catch(NumberFormatException exception){
                age = -1;
            }


            if(TextUtils.isEmpty(name) || age <= 0){
                Toast.makeText(getBaseContext(),"Please enter valid information.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(addPerson(name,age)){
                Toast.makeText(getBaseContext(),"New person added successfully", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getBaseContext(),"error during saving person", Toast.LENGTH_SHORT).show();

        });
    }
    public boolean addPerson(String name, int age){
        boolean added = myDatabase.insertPerson(new Person(name,age));

        Intent intent = new Intent(getBaseContext(),MainActivity.class);

        intent.putExtra(NEW_PERSON_ADDED_EXTRA,added);

        setResult(MainActivity.ADD_PERSON_ACTIVITY_LAUNCH_CODE,intent);

        if(added){
            finish();
        }

        return added;
    }
}