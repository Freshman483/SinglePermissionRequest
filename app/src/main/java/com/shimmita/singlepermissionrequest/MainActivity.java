package com.shimmita.singlepermissionrequest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
/*we going to test using Read external storage permission
    assumed is that request code for this permission=1;*/

    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "You Already Granted Read Permission", Toast.LENGTH_SHORT).show();
        }
        else
        {
            requestPermissionReadingExternalStorage();
        }
    }

    private void requestPermissionReadingExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Request")
                    .setMessage("This Application Requires this and that to function properly")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
                        Toast.makeText(this, "Accepted Dialog", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                         // System.exit(0);
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Cancelled Dialog", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}