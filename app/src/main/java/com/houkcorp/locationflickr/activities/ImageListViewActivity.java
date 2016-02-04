package com.houkcorp.locationflickr.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.fragments.ImageListViewFragment;
import com.houkcorp.locationflickr.util.UIUtils;

public class ImageListViewActivity extends BaseActivity {
    private static final String IMAGE_GRID_VIEW_FRAGMENT = "image_grid_view_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            addFragment();
        }
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_image_grid_view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_grid_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about_menu: {
                String displayMessage;
                try {
                    int versionCode =
                            getBaseContext().getPackageManager().getPackageInfo(getBaseContext()
                                    .getPackageName(), 0).versionCode;
                    displayMessage = getString(R.string.about_location_flickr, versionCode);
                } catch(PackageManager.NameNotFoundException nameNotFoundException) {
                    displayMessage = "N/A";
                }

                UIUtils.showDialogMessage(this, R.string.about, displayMessage);

                break;
            }

            case R.id.refresh_menu: {
                ImageListViewFragment imageListViewFragment =
                        (ImageListViewFragment)getSupportFragmentManager()
                                .findFragmentByTag(IMAGE_GRID_VIEW_FRAGMENT);
                if(imageListViewFragment != null) {
                    imageListViewFragment.clearListAndReQuery();
                }

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Adds the fragment to the activity
     */
    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = ImageListViewFragment.newInstance();
        fragmentManager.beginTransaction().add(R.id.image_grid_fragment_id, fragment, IMAGE_GRID_VIEW_FRAGMENT).commit();
    }
}