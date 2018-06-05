package com.example.mohamed.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mohamed.bakingapp.model.Step;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Step_instructionFragment extends Fragment {
    String videoUrl ="";
    Step step;
    ArrayList<Step> steps ;
    TextView Desc_tv;
    VideoView videoView;
    MediaController mc;

    View masterView ;
    Button fullscreen,next,pervious;
    public Step_instructionFragment() {
    }
    @SuppressLint("ValidFragment")
    public Step_instructionFragment(ArrayList<Step> steps , Step step) {
        // Required empty public constructor
        this.steps = new ArrayList<>();
        this.steps = steps;
        this.step = step;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        masterView = inflater.inflate(R.layout.fragment_step_instruction, container, false);
        Desc_tv = masterView.findViewById(R.id.desc);
        videoView = masterView.findViewById(R.id.videoView);
        fullscreen = masterView.findViewById(R.id.fullscreen);
        next= masterView.findViewById(R.id.next);
        pervious= masterView.findViewById(R.id.pervious);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        pervious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pervious();
            }
        });

        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                full_screen();
            }
        });
        load_step();
        return masterView;
    }



    public void pervious(){
        step = steps.get(steps.indexOf(step)-1);
        load_step();
    }
    public void  next(){
        step = steps.get(steps.indexOf(step)+1);
        load_step();
    }

    private void load_step (){
        if (step != null) {
            videoUrl = step.getVideoURL();
           getActivity().setTitle(step.getShortDescription());
            Desc_tv.setText(step.getDescription());
            Uri video = Uri.parse(videoUrl);

            videoView.setMediaController(mc);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.start();

        }
    }

    public void full_screen (){
        Intent intent = new Intent(getContext(),FullscreenVideoActivity.class);
        intent.putExtra("step",step);
        startActivity(intent);
    }
}
