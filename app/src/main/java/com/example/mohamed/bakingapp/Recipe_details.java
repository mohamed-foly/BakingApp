package com.example.mohamed.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mohamed.bakingapp.model.Ingredient;
import com.example.mohamed.bakingapp.model.Recipe;
import com.example.mohamed.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class Recipe_details extends AppCompatActivity {
    Recipe_detailsFragment recipe_detailsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Bundle bundle = getIntent().getExtras();
        Recipe recipe;
        recipe = (Recipe) bundle.get("foly");
        setTitle(recipe.getName());
        Log.e("-----",recipe.getName());


        List<Step> steps ;
        List<Ingredient> ingredients;

        steps = recipe.getSteps();
        ingredients = recipe.getIngredients();

        FragmentManager fragmentManager = getSupportFragmentManager() ;

        recipe_detailsFragment = new Recipe_detailsFragment(steps,ingredients);

        fragmentManager.beginTransaction().add(R.id.Details_fragment_container,recipe_detailsFragment).commit();

    }
}
