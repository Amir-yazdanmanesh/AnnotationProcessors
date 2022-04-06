package com.yazdanmanesh.annotationprocessors;

import android.graphics.Color
import android.widget.TextView
import com.yazdanmanesh.lib.ByColor

class AnnotationClass {
    @ByColor(color = Color.RED)
    val tv: TextView? = null
}