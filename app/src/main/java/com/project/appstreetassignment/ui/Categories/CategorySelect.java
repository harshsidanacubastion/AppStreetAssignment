package com.project.appstreetassignment.ui.Categories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.project.appstreetassignment.R;
import com.project.appstreetassignment.ui.InternetStatus;
import com.project.appstreetassignment.ui.ItemData;
import com.project.appstreetassignment.ui.StatusAdapter;
import com.project.appstreetassignment.ui.main.MainActivity;

import java.util.ArrayList;

public class CategorySelect extends AppCompatActivity {
    Spinner country;
    Spinner category;
    Button proceed;
    ArrayList<ItemData> countryList;
    ArrayList<ItemData> categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        country = (Spinner) findViewById(R.id.country);
        category = (Spinner) findViewById(R.id.category);
        proceed = (Button) findViewById(R.id.clickEvent);

        countryList.add(new ItemData("Please Select Country", "Please Select Country"));
        countryList.add(new ItemData("India", "in"));
        countryList.add(new ItemData("Australia", "au"));
        countryList.add(new ItemData("USA", "us"));
        countryList.add(new ItemData("Italy", "it"));
        countryList.add(new ItemData("Columbia", "co"));
        country.setAdapter(new StatusAdapter(this, R.layout.spinner_item, R.id.txt, countryList));

        categoryList.add(new ItemData("Please Select Category", "Please Select Category"));
        categoryList.add(new ItemData("Business", "business"));
        categoryList.add(new ItemData("Entertainment", "entertainment"));
        categoryList.add(new ItemData("General", "general"));
        categoryList.add(new ItemData("Health", "health"));
        categoryList.add(new ItemData("Science", "science"));
        categoryList.add(new ItemData("Sports", "sports"));
        categoryList.add(new ItemData("Technology", "technology"));
        country.setAdapter(new StatusAdapter(this, R.layout.spinner_item, R.id.txt, categoryList));


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (category.getSelectedItemPosition() != 0 && country.getSelectedItemPosition() != 0) {
                    if (InternetStatus.getInstance(getApplicationContext()).isOnline()) {

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        //((ItemData) country.getSelectedItem()).getValue()

//                        i.putExtra((ItemData) (country.getSelectedItem()).get);
//                        i.putExtra();
//                        i.putExtra();
                        startActivity(i);
                    } else {

                    }

                }

            }
        });

    }
}
