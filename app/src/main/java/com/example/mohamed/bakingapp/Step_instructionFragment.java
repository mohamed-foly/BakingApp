package com.example.mohamed.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    //Long player_pos;

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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Long pos = player.getCurrentPosition();

        Log.e("savedInst","Saving value "+String.valueOf(pos));
        //savedInstanceState.putLong("POS",pos);
        step.saved_position = pos;
        savedInstanceState.putParcelable("step",step);
        savedInstanceState.putParcelableArrayList("steps",steps);
        //savedInstanceState.putParcelable("step",step);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){

            //player_pos = savedInstanceState.getLong("POS");
            step = savedInstanceState.getParcelable("step");
            steps = savedInstanceState.getParcelableArrayList("steps");

        }
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

    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        player.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null){
            player.setPlayWhenReady(true);
        }
    }
    public void previous(){
        if (steps.indexOf(step)-1 >=0) {
            step.saved_position = player.getCurrentPosition();
            step = steps.get(steps.indexOf(step) - 1);
            load_step();
        }else{
            Toast.makeText(getContext(), "no more previous", Toast.LENGTH_SHORT).show();
        }
    }
    public void  next(){
        if (steps.indexOf(step)+1 < steps.size()){
            step.saved_position = player.getCurrentPosition();
            step = steps.get(steps.indexOf(step)+1);
            load_step();
        }else{
            Toast.makeText(getContext(), "no more steps", Toast.LENGTH_SHORT).show();
        }
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

//            if (player_pos != null){
//                player.seekTo(player_pos);
//            }
            if (step.saved_position != null){
                player.seekTo(step.saved_position);
            }

        }
    }

    public void full_screen (){
        Intent intent = new Intent(getContext(),FullscreenVideoActivity.class);
        intent.putExtra("step",step);
        startActivity(intent);
    }
}
