package com.example.roomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.roomapp.ListAdapters.visitorListAdapter;
import com.example.roomapp.Room.Visitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String TAG = "mainActivity";

    VisitorRepository visitorRepository;
    LiveData<List<Visitor>> liveData;

    Button addButton;
    ListView listView;

    ImageView imageView;

    byte[] imgBlob = new byte[0];;

    final int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        visitorRepository = new VisitorRepository(getApplicationContext(), "visitors_table");
        liveData = visitorRepository.getAllVisitors();

        liveData.observe(this, new Observer<List<Visitor>>() {
            @Override
            public void onChanged(List<Visitor> visitors) {
               populateListView((ArrayList<Visitor>) visitors);
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
        if (tmp != null) {
          populateListView((ArrayList<Visitor>) liveData.getValue());
        }
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
        imageView = dialog.findViewById(R.id.previewIV);
        Button loadImageButton = dialog.findViewById(R.id.loadImageButton);
        Button confirmButton = dialog.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                int table = Integer.parseInt(tableET.getText().toString());
                String phone = phoneET.getText().toString();
                String email = emailET.getText().toString();
                String time = bookTimeET.getText().toString();
                Visitor visitor = new Visitor(name, table, phone, email, time, imgBlob);
                visitorRepository.insert(visitor);
                dialog.dismiss();


            }
        });

        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });


        dialog.show();
    }

    private void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    Bitmap yourBitmap = null;
                    try {
                        yourBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        imgBlob = bos.toByteArray();
                        imageView.setImageURI(selectedImageUri);
                        imageView.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public void populateListView(ArrayList<Visitor> visitors) {
        visitorListAdapter visitorListAdapter = new visitorListAdapter(getApplicationContext(), R.layout.list_item, visitors, visitorRepository);
        listView.setAdapter(visitorListAdapter);
    }
}