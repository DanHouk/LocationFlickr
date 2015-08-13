package com.houkcorp.locationflickr.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.houkcorp.locationflickr.R;

public class ImageGridView extends AppCompatActivity implements ImageGridViewFragment.TaskCallbacks {
    private static final String IMAGE_GRID_VIEW_FRAGMENT = "image_grid_view_fragment";
    private ImageGridViewFragment mImageGridViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid_view);
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
                String displayMessage =
                        "Location Flickr\n\n An application to find Flickr images near you.\n\n";
                displayMessage += "Version: ";
                try {
                    int versionCode =
                            getBaseContext().getPackageManager().getPackageInfo(getBaseContext()
                                    .getPackageName(), 0).versionCode;
                    displayMessage += versionCode;
                } catch(PackageManager.NameNotFoundException nameNotFoundException) {
                    displayMessage += "N/A";
                }

                showDialogMessage(R.string.about, displayMessage);

                break;
            }

            case R.id.refresh_menu: {
                ImageGridViewFragment imageGridViewFragment =
                        (ImageGridViewFragment)getSupportFragmentManager()
                                .findFragmentById(R.id.image_grid_fragment_id);
                if(imageGridViewFragment != null) {
                    imageGridViewFragment.clearListAndReQuery();
                }

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogMessage(int titleResourceInt, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle(titleResourceInt)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onPreExecute() {
    }

    @Override
    public void onProgressUpdate(int percent) {
    }

    @Override
    public void onCancelled() {
    }

    @Override
    public void onPostExecute() {
    }
}