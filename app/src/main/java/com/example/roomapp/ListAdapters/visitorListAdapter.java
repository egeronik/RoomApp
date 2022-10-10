package com.example.roomapp.ListAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roomapp.R;
import com.example.roomapp.Room.Visitor;
import com.example.roomapp.VisitorRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class visitorListAdapter extends ArrayAdapter<Visitor> {
    private static final String TAG = "ArrayAdapter";

    private Context mContext;
    int mResource;
    private final LayoutInflater layoutInflater;
    VisitorRepository mRepository;


    private static final int PICK_IMG_FILE = 2;


    public visitorListAdapter(@NonNull Context context, int resource, ArrayList<Visitor> objects, VisitorRepository repository) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

        mRepository = repository;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView idTV = convertView.findViewById(R.id.idTV);
        TextView nameTV = convertView.findViewById(R.id.visitorNameTV);
        TextView emailTV = convertView.findViewById(R.id.visitorEmailTV);
        TextView tableNumbTV = convertView.findViewById(R.id.tableNumberTV);
        TextView bookTimeTV = convertView.findViewById(R.id.bookTimeTV);
        TextView phoneTV = convertView.findViewById(R.id.visitorPhoneTV);
        ImageView visitorIV = convertView.findViewById(R.id.visitorIV);
        ImageButton loadImageButton = convertView.findViewById(R.id.loadImageButton);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);
        Visitor visitor = getItem(position);
        Log.d(TAG, visitor.toString());
        idTV.setText("ID:" + Integer.toString(visitor.vID));
        nameTV.setText("Имя:" + visitor.name);
        emailTV.setText("Email: " + visitor.email);
        tableNumbTV.setText("Номер столика: " + Integer.toString(visitor.table));
        bookTimeTV.setText("Время: " + visitor.bookTime);
        phoneTV.setText("Телефон: " + visitor.phone);
        if (!Arrays.equals(visitor.imgBlob, new byte[0])) {
            visitorIV.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(visitor.imgBlob, 0, visitor.imgBlob.length);
            visitorIV.setImageBitmap(bitmap);

        }


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepository.delete(visitor);
            }
        });


        return convertView;
    }


}

