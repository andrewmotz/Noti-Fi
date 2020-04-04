package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class FAQActivity extends AppCompatActivity {

    TextView faqText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        faqText = findViewById(R.id.faq_text);

        //to make the textview scrollable
        faqText.setMovementMethod(new ScrollingMovementMethod());
    }
}
