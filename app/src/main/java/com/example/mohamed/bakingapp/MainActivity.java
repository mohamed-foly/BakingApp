package com.example.mohamed.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mohamed.bakingapp.interfaces.OnGrab_Recipes;
import com.example.mohamed.bakingapp.model.Recipe;
import com.example.mohamed.bakingapp.tasks.Grab_Recipes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnGrab_Recipes {
    private String Recipes_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    RecipesFragment recipesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager() ;
        recipesFragment = new RecipesFragment();
        fragmentManager.beginTransaction().add(R.id.Master_fragment_container,recipesFragment).commit();


        new Grab_Recipes(this).execute(Recipes_URL);


    }

    @Override
    public void onGrab_Recipes_listener(ArrayList<Recipe> recipes) {

        if (recipes != null){
            recipesFragment.setRecipes(recipes);

            for (Recipe x :recipes) {
                Log.d("RecipeGrabbedRow",x.getName());
            }
        }else{
            Log.e("RecipesArray","Null !");

        }


    }








}
