package com.f82019.education4all

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    val LOGIN_ACT = 111;
    val MAIN_ACT = 121;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivityForResult(Intent(this, LoginActivity::class.java), LOGIN_ACT)
            //finish()
        },4000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOGIN_ACT){
            startActivityForResult(Intent(this, Main3Activity::class.java), MAIN_ACT)
        } else if(requestCode == MAIN_ACT){
            finish()
        }
    }
}
