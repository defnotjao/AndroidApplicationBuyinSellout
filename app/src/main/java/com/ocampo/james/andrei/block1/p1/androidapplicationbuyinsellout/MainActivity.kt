package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, SignupAndLogin::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}