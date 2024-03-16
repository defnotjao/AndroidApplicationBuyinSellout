package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout.R

class KidsCategoryActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kids_category)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val backButton: View = findViewById(R.id.backButtonKids)
        backButton.setOnClickListener {
            finish()
        }
    }
}