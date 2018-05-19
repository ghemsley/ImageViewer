package com.example.android.imageviewer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.android.example.imageviewer.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fabImage = (FloatingActionButton) findViewById(R.id.fabImage);
        fabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        final FloatingActionButton fabDirectory = (FloatingActionButton) findViewById(R.id.fabDirectory);
        fabDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDirectory();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final int READ_REQUEST_CODE_IMAGE = 42;
    private static final int READ_REQUEST_CODE_DIRECTORY = 24;

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void chooseImage() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE_IMAGE);


//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//        startActivityForResult(intent, REQUEST_CODE_OPEN_DIRECTORY);

    }

    public void chooseDirectory() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, READ_REQUEST_CODE_DIRECTORY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.
        if (requestCode == READ_REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (resultData != null) {
                Uri uri = resultData.getData();
                Intent intent = new Intent(this, FullscreenActivity.class);
                intent.putExtra(EXTRA_MESSAGE, uri.toString());
                startActivity(intent);
            }
        }
        if (requestCode == READ_REQUEST_CODE_DIRECTORY && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (resultData != null) {
                Uri uri = resultData.getData();
                Intent intent = new Intent(this, GalleryActivity.class);
                intent.putExtra(EXTRA_MESSAGE, uri.toString());
                startActivity(intent);
            }
        }
    }
}

