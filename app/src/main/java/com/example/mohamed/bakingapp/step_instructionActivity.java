package com.example.mohamed.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mohamed.bakingapp.model.Step;

import java.util.ArrayList;

public class step_instructionActivity extends AppCompatActivity {
    String videoUrl ="";
    Step step;
    ArrayList<Step> steps ;
    TextView Desc_tv;
    VideoView videoView;
    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_instruction);
        Desc_tv = findViewById(R.id.desc);
        videoView = findViewById(R.id.videoView);
        steps = new ArrayList<>();




        Bundle bundle =getIntent().getExtras();
        if (bundle != null) {
            steps = bundle.getParcelableArrayList("steps");
            step =  steps.get(bundle.getInt("step"));
            load_step();
        }







    }

    public void pervious(View view){
        step = steps.get(steps.indexOf(step)-1);
        load_step();
    }
    public void  next(View view){
        step = steps.get(steps.indexOf(step)+1);
        load_step();
    }

    private void load_step (){
        if (step != null) {
            videoUrl = step.getVideoURL();
            setTitle(step.getShortDescription());
            Desc_tv.setText(step.getDescription());
            Uri video = Uri.parse(videoUrl);

            videoView.setMediaController(mc);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.start();

        }
    }

    public void full_screen (View view){
        Intent intent = new Intent(getApplicationContext(),FullscreenVideoActivity.class);
        intent.putExtra("step",step);
        startActivity(intent);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            videoView.setLayoutParams(params);
            videoView.layout(10, 10, 10, 10);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("-------","Portrait");
        }
    }



}
