package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle

class MapsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        setHeaderTitle("Logo")
    }
}