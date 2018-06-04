package com.example.mohamed.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mohamed.bakingapp.interfaces.OnStepSelected;
import com.example.mohamed.bakingapp.model.Ingredient;
import com.example.mohamed.bakingapp.model.Recipe;
import com.example.mohamed.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class Recipe_details extends AppCompatActivity implements OnStepSelected {
    Recipe_detailsFragment recipe_detailsFragment;
    FragmentManager fragmentManager;
    List<Step> steps ;
    List<Ingredient> ingredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Bundle bundle = getIntent().getExtras();
        Recipe recipe;
        recipe = (Recipe) bundle.get("foly");
        setTitle(recipe.getName());
        Log.e("-----",recipe.getName());


        steps = recipe.getSteps();
        ingredients = recipe.getIngredients();

        fragmentManager = getSupportFragmentManager() ;


        recipe_detailsFragment = new Recipe_detailsFragment(steps,ingredients,this);

        fragmentManager.beginTransaction().add(R.id.Details_fragment_container,recipe_detailsFragment).commit();



        
        if (bundle != null) {
            //steps = bundle.getParcelableArrayList("steps");
            //Step step = steps.get(bundle.getInt("step"));
            //load_step();
            //FragmentManager fragmentManager = getSupportFragmentManager() ;
//            ArrayList<Step> steps_Array = new ArrayList<>(steps);
//
//            Step_instructionFragment step_instructionFragment = new Step_instructionFragment(steps_Array,steps_Array.get(2));
//            fragmentManager.beginTransaction().add(R.id.Master_fragment_container,step_instructionFragment).commit();


        }
    }

    @Override
    public void onStepSelected_listener(Step step) {

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            ArrayList<Step> steps_Array = new ArrayList<>(steps);
            Step_instructionFragment step_instructionFragment = new Step_instructionFragment(steps_Array,step);
            fragmentManager.beginTransaction().replace(R.id.Master_fragment_container,step_instructionFragment).commit();
        } else {
            Intent intent = new Intent(getApplicationContext(),step_instructionActivity.class);
            ArrayList<Step> steps_Array = new ArrayList<>(steps);
            intent.putExtra("step",steps.indexOf(step));
            intent.putParcelableArrayListExtra("steps",steps_Array);
            startActivity(intent);
        }

    }
}
