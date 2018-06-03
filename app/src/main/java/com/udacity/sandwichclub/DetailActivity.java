package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    TextView tvAlsoKnownAs;
    TextView tvIngredients;
    TextView tvPlaceOfOrigin;
    TextView tvDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.v("json >>>>> ", json );
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        tvAlsoKnownAs = findViewById(R.id.also_known_tv) ;
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvPlaceOfOrigin = findViewById(R.id.origin_tv);
        tvDescription = findViewById(R.id.description_tv);
        List<String> listAlsoKnownAs;
        List<String> listIngredients;
        listAlsoKnownAs=sandwich.getAlsoKnownAs();
        listIngredients= sandwich.getIngredients();
        for (int i = 0; i < listAlsoKnownAs.size(); i++ ) {
            if (i!=0){
                tvAlsoKnownAs.append(", ");
            }
            tvAlsoKnownAs.append(listAlsoKnownAs.get(i));
            if ((i+1)==listAlsoKnownAs.size()){
                tvAlsoKnownAs.append(".");
            }
        }

        for (int i = 0; i < listIngredients.size(); i++ ) {
            if (i!=0){
                tvIngredients.append(", ");
            }
            tvIngredients.append(listIngredients.get(i));
            if ((i+1)==listIngredients.size()){
                tvIngredients.append(".");
            }
        }

        tvDescription.setText(sandwich.getDescription());
        tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());

    }
}
