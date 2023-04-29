package fr.epsi.projet_atelier_dev_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson

class AccountDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)
        setHeaderTitle("Compte")

        val nom_form = findViewById<EditText>(R.id.nom_form)
        val prenom_form = findViewById<EditText>(R.id.prenom_form)
        val email_form = findViewById<EditText>(R.id.email_form)
        val adresse_form = findViewById<EditText>(R.id.adresse_form)
        val code_postal_form = findViewById<EditText>(R.id.code_postal_form)
        val ville_form = findViewById<EditText>(R.id.ville_form)

        val preferenceHelper = PreferenceHelper(this)
        val user = preferenceHelper.getUser()

        nom_form.setText(user?.firstName ?: "")
        prenom_form.setText(user?.lastName ?: "")
        email_form.setText(user?.email ?: "")
        adresse_form.setText(user?.address ?: "")
        code_postal_form.setText(user?.zipcode ?: "")
        ville_form.setText(user?.city ?: "")

        val submitButton = findViewById<Button>(R.id.Submit_Account_Creation_Button)
        submitButton.setOnClickListener {
            val user = User(nom_form.text.toString(), prenom_form.text.toString(), email_form.text.toString(), adresse_form.text.toString(), code_postal_form.text.toString(), ville_form.text.toString(), user?.cardRef ?: "")
            val preferenceHelper = PreferenceHelper(this)
            preferenceHelper.saveUser(user)
            val newIntent = Intent(application, HomeActivity::class.java)
            startActivity(newIntent)
        }
    }
}