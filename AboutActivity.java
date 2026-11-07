
package com.example.begwisejava;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        // Set up About Page Toolbar
        Toolbar aboutToolbar = findViewById(R.id.aboutToolbar);
        setSupportActionBar(aboutToolbar);

        if (getSupportActionBar() != null) {

            // Hide default title because title is in XML
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            // Show back arrow
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Connect GitHub card with Java
        LinearLayout githubCard = findViewById(R.id.githubCard);

        // Open GitHub when card is clicked
        githubCard.setOnClickListener(v -> {

            String githubUrl =
                    "https://github.com/luvnaa/FlyluneJava.git";

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(githubUrl)
            );

            startActivity(intent);
        });
    }

    // Back arrow action
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
