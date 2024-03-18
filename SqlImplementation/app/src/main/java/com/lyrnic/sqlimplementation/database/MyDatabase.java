package com.lyrnic.sqlimplementation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.lyrnic.sqlimplementation.model.Person;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    public static MyDatabase myDatabase;
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "PERSONS_DATABASE";
    public static String PERSON_TABLE_NAME = "persons_table";
    public static String ENTRY_PERSON_NAME = "name";
    public static String ENTRY_PERSON_AGE = "age";
    public static String ENTRY_PERSON_ID = "id";
    private MyDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PERSON_TABLE_NAME +
                " ("+ ENTRY_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ ENTRY_PERSON_NAME + " TEXT, "+ ENTRY_PERSON_AGE + " INTEGER);");
        //CREATE TABLE persons_table (id PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
        //DROP TABLE IF EXISTS persons_table

        onCreate(sqLiteDatabase);
    }
    public static MyDatabase getDatabase(Context context){
        if(myDatabase == null){
            myDatabase = new MyDatabase(context);
        }
        return myDatabase;
    }

    public boolean insertPerson(@NonNull Person person){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String name = person.getName();
        int age = person.getAge();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ENTRY_PERSON_NAME,name);
        contentValues.put(ENTRY_PERSON_AGE,age);

        long id = sqLiteDatabase.insert(PERSON_TABLE_NAME,null,contentValues);

        return id != -1;
    }
    public boolean removePerson(Person person){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        int id = person.getId();

        long deleted = sqLiteDatabase.delete(PERSON_TABLE_NAME,ENTRY_PERSON_ID+"=?"
                ,new String[]{Integer.toString(id)});

        return deleted != 0;
    }
    public boolean updatePerson(Person person){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String name = person.getName();
        int age = person.getAge();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ENTRY_PERSON_NAME,name);
        contentValues.put(ENTRY_PERSON_AGE,age);

        long updated = sqLiteDatabase.update(PERSON_TABLE_NAME,contentValues,
                ENTRY_PERSON_ID+"=?",new String[]{Integer.toString(person.getId())});

        return updated != 0;
    }
    public ArrayList<Person> getAllPersons(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ PERSON_TABLE_NAME,null);
        //SELECT * FROM persons_table;

        if(cursor.moveToFirst()){
            do{
                int idIndex = cursor.getColumnIndex(ENTRY_PERSON_ID);
                int nameIndex = cursor.getColumnIndex(ENTRY_PERSON_NAME);
                int ageIndex = cursor.getColumnIndex(ENTRY_PERSON_AGE);

                int id = cursor.getInt(idIndex == -1 ? 0 : idIndex); // if idIndex equals -1 then send id known index statically
                int age = cursor.getInt(ageIndex == -1 ? 2 : ageIndex);// if ageIndex equals -1 then send age known index statically
                String name = cursor.getString(nameIndex == -1 ? 1 : nameIndex);// if nameIndex equals -1 then send name known index statically

                Person person = new Person(id,name,age);

                persons.add(person);
            }
            while(cursor.moveToNext());

            cursor.close();
        }

        return persons;
    }
    public ArrayList<Person> getPersonsWithName(String nameSearch){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ PERSON_TABLE_NAME
                + " WHERE " + ENTRY_PERSON_NAME + "=?;",new String[]{nameSearch});
        //SELECT * FROM persons_table WHERE name=$nameSearch;

        if(cursor.moveToFirst()){
            do{
                int idIndex = cursor.getColumnIndex(ENTRY_PERSON_ID);
                int nameIndex = cursor.getColumnIndex(ENTRY_PERSON_NAME);
                int ageIndex = cursor.getColumnIndex(ENTRY_PERSON_AGE);

                int id = cursor.getInt(idIndex == -1 ? 0 : idIndex); // if idIndex equals -1 then send id known index statically
                int age = cursor.getInt(ageIndex == -1 ? 2 : ageIndex);// if ageIndex equals -1 then send age known index statically
                String name = cursor.getString(nameIndex == -1 ? 1 : nameIndex);// if nameIndex equals -1 then send name known index statically

                Person person = new Person(id,name,age);

                persons.add(person);
            }
            while(cursor.moveToNext());

            cursor.close();
        }

        return persons;
    }
    public ArrayList<Person> getPersonsWithAge(int ageSearch){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ PERSON_TABLE_NAME
                + " WHERE " + ENTRY_PERSON_AGE + "=?;",new String[]{Integer.toString(ageSearch)});
        //SELECT * FROM persons_table WHERE age=$ageSearch;

        if(cursor.moveToFirst()){
            do{
                int idIndex = cursor.getColumnIndex(ENTRY_PERSON_ID);
                int nameIndex = cursor.getColumnIndex(ENTRY_PERSON_NAME);
                int ageIndex = cursor.getColumnIndex(ENTRY_PERSON_AGE);

                int id = cursor.getInt(idIndex == -1 ? 0 : idIndex); // if idIndex equals -1 then send id known index statically
                int age = cursor.getInt(ageIndex == -1 ? 2 : ageIndex);// if ageIndex equals -1 then send age known index statically
                String name = cursor.getString(nameIndex == -1 ? 1 : nameIndex);// if nameIndex equals -1 then send name known index statically

                Person person = new Person(id,name,age);

                persons.add(person);
            }
            while(cursor.moveToNext());

            cursor.close();
        }

        return persons;
    }

}
