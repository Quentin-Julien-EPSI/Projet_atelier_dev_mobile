package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle

class StoreDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_details)
        setHeaderTitle("Nom du magasin")
    }
}