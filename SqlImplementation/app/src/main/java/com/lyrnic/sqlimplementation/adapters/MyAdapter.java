package com.lyrnic.sqlimplementation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyrnic.sqlimplementation.R;
import com.lyrnic.sqlimplementation.database.MyDatabase;
import com.lyrnic.sqlimplementation.model.Person;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<Person> persons;

    public MyAdapter(ArrayList<Person> persons){
        this.persons = persons;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Person person = persons.get(position);

        holder.nameTv.setText(person.getName());
        holder.ageTv.setText(String.format("%s",person.getAge()));

        holder.itemView.setOnLongClickListener((view) -> {
                boolean removed = MyDatabase.getDatabase(holder.itemView.getContext()).removePerson(person);
                if(removed){
                    int index = indexOf(person);// don't use position because it may cause index problems
                    persons.remove(index);
                    notifyItemRemoved(index);
                    Toast.makeText(holder.itemView.getContext(),"Person removed successfully",Toast.LENGTH_SHORT).show();
                    return true;
                }
                Toast.makeText(holder.itemView.getContext(),"error occurred during remove process",Toast.LENGTH_SHORT).show();
                return true;
            }
        );
    }
    public int indexOf(Person person){
        for(int i = 0; i < persons.size() ; i++){
            if(persons.get(i).getId() == person.getId()){
                return i;
            }
        }
        return -1;
    }

    public void setPersons(ArrayList<Person> persons) {
        int oldLength = this.persons.size();
        int newLength = persons.size();

        this.persons = persons;

        notifyItemRangeInserted(oldLength,newLength - oldLength);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv,ageTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_textview);
            ageTv = itemView.findViewById(R.id.age_textview);
        }
    }
}
