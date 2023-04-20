package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle

class AccountCreationFormActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_creation_form)
        setHeaderTitle("Création de compte")
        showBack()

    }
}