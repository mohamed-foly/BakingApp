package com.example.mohamed.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

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

import java.util.Objects;

public class FullscreenVideoActivity extends AppCompatActivity {
    DefaultBandwidthMeter bandwidthMeter;
    TrackSelection.Factory videoTrackSelectionFactory;
    TrackSelector trackSelector;
    SimpleExoPlayer player;
    DataSource.Factory dataSourceFactory;
    MediaSource videoSource;
    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen_video);
        playerView = findViewById(R.id.exo_player);
        View mContentView = findViewById(R.id.fullscreen_content);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        String videoUrl;
        Step step;
//        MediaController mc;
//        mc = new MediaController(this);
//
//        VideoView videoView;
//        videoView = findViewById(R.id.videoView);

        Bundle bundle =getIntent().getExtras();
        if (bundle != null) {
            step =  bundle.getParcelable("step");
            if (step != null) {
                videoUrl = step.getVideoURL();
                Uri video = Uri.parse(videoUrl);
//                mc.setAnchorView(videoView);
//                mc.setMediaPlayer(videoView);
//                videoView.setMediaController(mc);
//                videoView.setVideoURI(video);
//                videoView.requestFocus();
//                videoView.start();





                bandwidthMeter = new DefaultBandwidthMeter();
                videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
                trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
                player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
                playerView.setPlayer(player);
                dataSourceFactory = new DefaultDataSourceFactory(Objects.requireNonNull(getApplication()), Util.getUserAgent(getApplicationContext(), "BakingApp"), bandwidthMeter);
                videoSource = new ExtractorMediaSource.Factory(dataSourceFactory) .createMediaSource(video);
                player.prepare(videoSource);
            }

        }

    }

}
