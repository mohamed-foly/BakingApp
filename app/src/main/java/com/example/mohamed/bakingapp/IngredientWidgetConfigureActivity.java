package com.example.mohamed.bakingapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohamed.bakingapp.interfaces.OnGrab_Recipes;
import com.example.mohamed.bakingapp.interfaces.OnRecipeSelected;
import com.example.mohamed.bakingapp.model.Ingredient;
import com.example.mohamed.bakingapp.model.Recipe;
import com.example.mohamed.bakingapp.tasks.Grab_Recipes;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration screen for the {@link IngredientWidget IngredientWidget} AppWidget.
 */
public class IngredientWidgetConfigureActivity extends AppCompatActivity implements OnGrab_Recipes,OnRecipeSelected {

    private static final String PREFS_NAME = "com.example.mohamed.bakingapp.IngredientWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = IngredientWidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
            String widgetText = mAppWidgetText.getText().toString();
            saveTitlePref(context, mAppWidgetId, widgetText);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            IngredientWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public IngredientWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    private String Recipes_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    RecipesFragment recipesFragment;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.ingredient_widget_configure);
        mAppWidgetText = findViewById(R.id.appwidget_text);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mAppWidgetText.setText(loadTitlePref(IngredientWidgetConfigureActivity.this, mAppWidgetId));




        FragmentManager fragmentManager = getSupportFragmentManager() ;
        recipesFragment = new RecipesFragment();
        fragmentManager.beginTransaction().add(R.id.Master_fragment_container,recipesFragment).commit();


        new Grab_Recipes(this).execute(Recipes_URL);

    }

    @Override
    public void onGrab_Recipes_listener(ArrayList<Recipe> recipes) {

        if (recipes != null){
            recipesFragment.setRecipes(recipes,this);
            for (Recipe x :recipes) {
                Log.d("RecipeGrabbedRow",x.getName());
            }
        }else{
            Log.e("RecipesArray","Null !");

        }


    }


    @Override
    public void onRecipeSelected_listener(Recipe recipe) {
        StringBuilder IngredientsText= new StringBuilder();
        List<Ingredient> ingredients = recipe.getIngredients();
        IngredientsText.append("(  ").append(recipe.getName()).append("  ) \n");
        for (Ingredient ingredient: ingredients)
        {
            IngredientsText.append("- ").append(ingredient.getIngredient()).append(" (").append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).append(") \n");
        }
        mAppWidgetText.setText(IngredientsText);
    }
}

