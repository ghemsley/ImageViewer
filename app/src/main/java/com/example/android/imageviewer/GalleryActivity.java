package com.example.android.imageviewer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.galleryToolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabGallery);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent();
                backIntent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(backIntent);
            }
        });
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Uri uri = Uri.parse(message);
        populateGallery(uri);
    }

    public void populateGallery(Uri uri){
        DocumentFile folder = DocumentFile.fromTreeUri(this, uri);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        float doubleTapZoomScale = 1.0f;
        float maximumZoomScale = 1.0f;
        int doubleTapZoomDuration = 150;
        try {   //settings are stored as strings
            doubleTapZoomScale = Float.parseFloat(preferences.getString("doubleTapZoomScale", "1.0"));
            maximumZoomScale = Float.parseFloat(preferences.getString("maximumZoomScale", "2.0"));
            doubleTapZoomDuration = Integer.parseInt(preferences.getString("doubleTapZoomDuration", "150"));

        } catch (NumberFormatException e) {
            e.printStackTrace();
            doubleTapZoomScale = 1.0f;
            maximumZoomScale = 2.0f;
            doubleTapZoomDuration = 150;
        }
        if(folder.exists()) {
            LinearLayout layout = findViewById(R.id.galleryLinearLayout);
            DocumentFile[] allFiles = folder.listFiles();
            for(int i=0;i<allFiles.length;i++)
            {
                if (allFiles[i].getUri().toString().endsWith(".jpg")
                        || allFiles[i].getUri().toString().endsWith(".jpeg")
                        || allFiles[i].getUri().toString().endsWith(".png")) {
                    SubsamplingScaleImageView image = new SubsamplingScaleImageView(this);
                    Uri newUri = allFiles[i].getUri();
                    image.setImage(ImageSource.uri(newUri));
                    image.setOrientation(SubsamplingScaleImageView.ORIENTATION_USE_EXIF);
                    image.setDoubleTapZoomScale(doubleTapZoomScale);
                    image.setDoubleTapZoomDuration(doubleTapZoomDuration);
                    image.setMaxScale(maximumZoomScale);
                    layout.addView(image);
                }
            };
        }
    }
}
