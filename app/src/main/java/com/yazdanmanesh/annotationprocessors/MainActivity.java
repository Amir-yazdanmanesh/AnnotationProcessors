package com.yazdanmanesh.annotationprocessors;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yazdanmanesh.lib.ByColor;


public class MainActivity extends AppCompatActivity {
    @ByColor(color = Color.RED)
    TextView tv;
    @ByColor(color = Color.GREEN)
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        tv1 = findViewById(R.id.tv1);

        MainActivityColorize.bind(this);
    }
}