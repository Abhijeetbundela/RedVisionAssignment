package com.example.redvisionassignment.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redvisionassignment.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.getStringExtra("profileLink")!!

        web_view.loadUrl(url)

    }
}