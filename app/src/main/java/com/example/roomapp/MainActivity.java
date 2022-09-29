package com.example.roomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.roomapp.ListAdapters.visitorListAdapter;
import com.example.roomapp.Room.Visitor;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String TAG = "mainActivity";

    VisitorRepository visitorRepository;
    LiveData<List<Visitor>> liveData;

    Button addButton;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visitorRepository = new VisitorRepository(getApplicationContext(), "visitors_table");
        liveData = visitorRepository.getAllVisitors();

        liveData.observe(this, new Observer<List<Visitor>>() {
            @Override
            public void onChanged(List<Visitor> visitors) {
                populateListView(new ArrayList<>(visitors));

            }
        });

        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        List<Visitor> tmp = liveData.getValue();
        if (tmp != null)
            populateListView(new ArrayList<>(tmp));

    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_addvisitor);

        final EditText nameET = dialog.findViewById(R.id.visitorNameET);
        final EditText tableET = dialog.findViewById(R.id.tableNumberET);
        final EditText phoneET = dialog.findViewById(R.id.visitorPhoneET);
        final EditText emailET = dialog.findViewById(R.id.visitorEmailET);
        final EditText bookTimeET = dialog.findViewById(R.id.bookTimeET);
        Button confirmButton = dialog.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                int table = Integer.parseInt(tableET.getText().toString());
                String phone = phoneET.getText().toString();
                String email = emailET.getText().toString();
                String time = bookTimeET.getText().toString();
                Visitor visitor = new Visitor(name, table, phone, email, time);
                visitorRepository.insert(visitor);
                dialog.dismiss();


            }
        });


        dialog.show();
    }

    public void populateListView(ArrayList<Visitor> visitors) {
        visitorListAdapter visitorListAdapter = new visitorListAdapter(getApplicationContext(), R.layout.list_item, visitors, visitorRepository);
        listView.setAdapter(visitorListAdapter);
    }
}