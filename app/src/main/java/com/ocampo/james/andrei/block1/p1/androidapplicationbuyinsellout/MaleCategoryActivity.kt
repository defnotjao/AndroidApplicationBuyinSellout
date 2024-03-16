package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout.R

class MaleCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_male_category)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val backButton: View = findViewById(R.id.backButtonMen)
        backButton.setOnClickListener {
            finish()
        }
    }
}