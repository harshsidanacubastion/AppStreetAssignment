package com.project.appstreetassignment.ui.Categories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.appstreetassignment.R;
import com.project.appstreetassignment.ui.InternetStatus;
import com.project.appstreetassignment.ui.ItemData;
import com.project.appstreetassignment.ui.StatusAdapter;
import com.project.appstreetassignment.ui.newsList.NewsListActivity;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category_select);
        country = (Spinner) findViewById(R.id.country);
        category = (Spinner) findViewById(R.id.category);
        proceed = (Button) findViewById(R.id.clickEvent);
        countryList=new ArrayList<>();
        categoryList=new ArrayList<>();

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
        category.setAdapter(new StatusAdapter(this, R.layout.spinner_item, R.id.txt, categoryList));


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (category.getSelectedItemPosition() != 0 && country.getSelectedItemPosition() != 0) {
                    Intent i = new Intent(getApplicationContext(), NewsListActivity.class);
                    if (InternetStatus.getInstance(getApplicationContext()).isOnline()) {

                        i.putExtra("OnlineStatus", "Online");

                    } else {
                        i.putExtra("OnlineStatus", "Offline");

                    }
                    i.putExtra("Country", ((ItemData) country.getSelectedItem()).getValue());
                    i.putExtra("Category", ((ItemData) category.getSelectedItem()).getValue());

                    startActivity(i);
                } else {
                    Toast.makeText(CategorySelect.this, "Please select valid status", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
