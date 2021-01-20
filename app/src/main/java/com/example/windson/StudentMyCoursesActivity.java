package com.example.windson;import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class StudentMyCoursesActivity extends AppCompatActivity {
    SearchView mySearchView;
    ListView mList;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    private TextView textView_fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_mycourses_activity);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String name="";
        char[] ch = email.toCharArray();
        for (char c : ch){
            if(Character.valueOf(c).compareTo(Character.valueOf('@')) != 0){
                name = name+c;
            }
            else{
                break;
            }
        }
        String ID_name = new String(name);

        textView_fullname = (TextView) findViewById(R.id.textView6);
        textView_fullname.setText(ID_name);


        mySearchView = (SearchView)findViewById(R.id.searchbox);
        mList = (ListView)findViewById(R.id.mlist);
        list = new ArrayList<String>();
        list.add("Mobile Application Development");
        list.add("French");

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        mList.setAdapter(adapter);

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        ExpandableListView courses = (ExpandableListView)findViewById(R.id.course);
        courses.setAdapter(new process(this));

        courses.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(process.courseLists[childPosition].equals("Mobile Application and Development")) {
                    Intent i = new Intent(getApplicationContext(), StudentMobileAppDevActivity.class);
                    String n = ((TextView) v).getText().toString();
                    i.putExtra("courseName", process.courseLists[childPosition]);
                    startActivity(i);
                }
                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.commit();
                Intent intent = new Intent(StudentMyCoursesActivity.this, StudentProfile.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}



}