package com.example.android.pharmaeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.pharmaeasy.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Awinash on 21-05-2017.
 */

public class PageDetailActivity extends AppCompatActivity {

    private String mFirstName = "", mLastName = "", mImageUrl = "";

    @BindView(R.id.person_pictue)
    ImageView person_pictue;

    @BindView(R.id.first_name_detail)
    TextView firstName;

    @BindView(R.id.last_name_detail)
    TextView lastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_detail_layout);

        ButterKnife.bind(this);

        Intent data = getIntent();
        if (data != null) {

            mFirstName = data.getStringExtra("FIRSTNAME");
            mLastName = data.getStringExtra("LASTNAME");
            mImageUrl = data.getStringExtra("IMAGEURL");

            firstName.setText(mFirstName);
            lastName.setText(mLastName);
            Picasso.with(getApplicationContext()).load(mImageUrl).into(person_pictue);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
