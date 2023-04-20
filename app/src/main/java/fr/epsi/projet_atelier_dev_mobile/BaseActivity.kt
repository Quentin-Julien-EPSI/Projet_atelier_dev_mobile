package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("EPSI PROJET ATELIER DEV MOBILE","################ onCreate :"+javaClass.simpleName)
    }

    override fun onStart() {
        super.onStart()
        Log.i("EPSI PROJET ATELIER DEV MOBILE","################ onStart :"+javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Log.i("EPSI PROJET ATELIER DEV MOBILE","################ onResume :"+javaClass.simpleName)
    }

    override fun onPause() {
        super.onPause()
        Log.i("EPSI PROJET ATELIER DEV MOBILE","################ onPause :"+javaClass.simpleName)
    }

    override fun onStop() {
        super.onStop()
        Log.i("EPSI PROJET ATELIER DEV MOBILE","################ onStop :"+javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("EPSI PROJET ATELIER DEV MOBILE","################ onDestroy :"+javaClass.simpleName)
    }

    override fun finish() {
        super.finish()
        Log.i("EPSI PROJET ATELIER DEV MOBILE","################ finish :"+javaClass.simpleName)
    }

    fun setHeaderTitle(title: String?) {
        val textView = findViewById<TextView>(R.id.textViewTitle)
        textView.text = title
    }

    fun showBack() {
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)
        imageViewBack.visibility = View.VISIBLE
        imageViewBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

}