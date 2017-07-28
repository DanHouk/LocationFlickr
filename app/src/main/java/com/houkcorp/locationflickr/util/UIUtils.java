package com.houkcorp.locationflickr.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.houkcorp.locationflickr.R;

public class UIUtils {
    /**
     * Displays the passed message.
     *
     * @param titleResourceInt The resource for the title.
     * @param message The message to be displayed.
     */
    /*FIXME:THis is way too dependent.*/
    public static void showDialogMessage(Context context, int titleResourceInt, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setTitle(titleResourceInt)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}