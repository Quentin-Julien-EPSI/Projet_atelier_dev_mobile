package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle

class AccountDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)
        setHeaderTitle("Compte")
    }
}