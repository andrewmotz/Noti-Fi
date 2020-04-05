package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import java.util.Objects;

public class FAQActivity extends AppCompatActivity {

    TextView faqtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        faqtext = findViewById(R.id.faq_text);

        faqtext.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Objects.requireNonNull(getSupportActionBar()).setTitle("FAQ");
        return true;
    }
}
