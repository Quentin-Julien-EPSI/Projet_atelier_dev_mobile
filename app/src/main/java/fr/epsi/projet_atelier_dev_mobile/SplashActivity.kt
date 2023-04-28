package fr.epsi.projet_atelier_dev_mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val preferenceHelper = PreferenceHelper(this)
            // Save an User object
            // Retrieve an User object
            val savedUser = preferenceHelper.getUser()
            if(savedUser != null) {
                Log.i("PROJET ATELIER DEV MOBILE", "################ User :"+savedUser.firstName)
                val newIntent = Intent(application, CardActivity::class.java)
                startActivity(newIntent)
                finish()
            }else{
                Log.i("PROJET ATELIER DEV MOBILE", "################ User : null")
                val newIntent = Intent(application, AccountCreationActivity::class.java)
                startActivity(newIntent)
                finish()
            }
        }, 2000)
    }
}