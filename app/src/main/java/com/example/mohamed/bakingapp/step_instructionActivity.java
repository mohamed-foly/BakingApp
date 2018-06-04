package com.example.mohamed.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.bakingapp.model.Step;

import java.util.ArrayList;

public class step_instructionActivity extends AppCompatActivity {

    Step step;
    ArrayList<Step> steps ;

    Step_instructionFragment step_instructionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_instruction);
        Bundle bundle =getIntent().getExtras();
        if (bundle != null) {
            steps = bundle.getParcelableArrayList("steps");
            step =  steps.get(bundle.getInt("step"));
            //load_step();
            FragmentManager fragmentManager = getSupportFragmentManager() ;
            step_instructionFragment = new Step_instructionFragment(steps,step);
            fragmentManager.beginTransaction().add(R.id.Master_fragment_container,step_instructionFragment).commit();


        }


    }

}
