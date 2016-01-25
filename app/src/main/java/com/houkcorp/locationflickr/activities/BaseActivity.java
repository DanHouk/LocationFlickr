package com.houkcorp.locationflickr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.houkcorp.locationflickr.R;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private ArrayList<View> mHideableHeaderViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResources());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setHomeButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected abstract int getLayoutResources();
}