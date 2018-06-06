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
import android.widget.TextView;

import com.example.mohamed.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Objects;


@SuppressLint("ValidFragment")
public class Step_instructionFragment extends Fragment {
    String videoUrl ="";
    Step step;
    ArrayList<Step> steps ;
    TextView Desc_tv;
    //VideoView videoView;
    PlayerView playerView;
    //MediaController mc;

    View masterView ;
    Button fullscreen,next,previous;


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
        //videoView = masterView.findViewById(R.id.videoView);
        playerView = masterView.findViewById(R.id.exo_player);
        fullscreen = masterView.findViewById(R.id.fullscreen);
        next= masterView.findViewById(R.id.next);
        previous= masterView.findViewById(R.id.pervious);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous();
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



    public void previous(){
        step = steps.get(steps.indexOf(step)-1);
        load_step();
    }
    public void  next(){
        step = steps.get(steps.indexOf(step)+1);
        load_step();
    }
    DefaultBandwidthMeter bandwidthMeter;
    TrackSelection.Factory videoTrackSelectionFactory;
    TrackSelector trackSelector;
    SimpleExoPlayer player;
    DataSource.Factory dataSourceFactory;
    MediaSource videoSource;
    private void load_step (){
        if (step != null) {
            videoUrl = step.getVideoURL();
           Objects.requireNonNull(getActivity()).setTitle(step.getShortDescription());
            Desc_tv.setText(step.getDescription());
            Uri video = Uri.parse(videoUrl);





            bandwidthMeter = new DefaultBandwidthMeter();
            videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            playerView.setPlayer(player);
            dataSourceFactory = new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), Util.getUserAgent(getContext(), "BakingApp"), bandwidthMeter);
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory) .createMediaSource(video);
            player.prepare(videoSource);



            /////// old Player Configuration /////
//            videoView.setMediaController(mc);
//            videoView.setVideoURI(video);
//            videoView.requestFocus();
//            videoView.start();

        }
    }

    public void full_screen (){
        Intent intent = new Intent(getContext(),FullscreenVideoActivity.class);
        intent.putExtra("step",step);
        startActivity(intent);
    }
}
