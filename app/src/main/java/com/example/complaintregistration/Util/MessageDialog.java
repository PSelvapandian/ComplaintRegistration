package com.example.complaintregistration.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class MessageDialog
{
    public static void showAlertDialog(String message, Context context)
    {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.cancel())
                .setCancelable(false)
                .create().show();
    }

    public static void showToast(String message, Context context)
    {
        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
    }
}
