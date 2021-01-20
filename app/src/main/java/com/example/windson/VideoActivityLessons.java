package com.example.windson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VideoActivityLessons extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        recyclerView = findViewById(R.id.recyclerview_ShowVideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Videos");


    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<videomember> options =
                new FirebaseRecyclerOptions.Builder<videomember>()
                        .setQuery(databaseReference,videomember.class)
                        .build();

        FirebaseRecyclerAdapter<videomember,VideoAdapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<videomember, VideoAdapter>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull VideoAdapter holder, int position, @NonNull videomember model) {

                        holder.setExoplayer(getApplication(), model.getName(), model.getVideourl());

                    }



                    @NonNull
                    @Override
                    public VideoAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.video_item,parent,false);

                        return new VideoAdapter(view);

                    }
                };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
