package com.project.appstreetassignment.ui.Categories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.project.appstreetassignment.R;

public class CategorySelect extends AppCompatActivity {
Spinner country;
    Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        country = (Spinner) findViewById(R.id.country);
        category = (Spinner) findViewById(R.id.category);
       // country.setAdapter(new StatusAdapter(this, R.layout.spinner_item, R.id.txt, list));


    }
}
