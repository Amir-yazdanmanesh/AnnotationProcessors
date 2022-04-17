package com.yazdanmanesh.annotationprocessors

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yazdanmanesh.annotationprocessors.MainActivityColorize.bind
import com.yazdanmanesh.lib.ByColor

class MainActivity : AppCompatActivity() {
    @ByColor(color = Color.RED)
    var tv: TextView? = null

    @ByColor(color = Color.GREEN)
    var tv1: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)
        tv1 = findViewById(R.id.tv1)
        bind(this)
    }
}