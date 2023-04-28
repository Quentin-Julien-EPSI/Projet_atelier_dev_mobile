package fr.epsi.projet_atelier_dev_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson

class AccountCreationFormActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_creation_form)
        setHeaderTitle("Création de compte")
        showBack()

        val nom_form = findViewById<EditText>(R.id.nom_form)
        val prenom_form = findViewById<EditText>(R.id.prenom_form)
        val email_form = findViewById<EditText>(R.id.email_form)
        val adresse_form = findViewById<EditText>(R.id.adresse_form)
        val code_postal_form = findViewById<EditText>(R.id.code_postal_form)
        val ville_form = findViewById<EditText>(R.id.ville_form)
        val carte_de_fidelite_form = findViewById<EditText>(R.id.carte_de_fidelite_form)

        val qrcodeData = intent.getStringExtra("qrcodeData")
        val gson = Gson()
        if (qrcodeData != null) {
            Log.d("qrcodeData received", qrcodeData)
            val qrcodeData = gson.fromJson(qrcodeData, User::class.java)
            nom_form.setText(qrcodeData.firstName)
            prenom_form.setText(qrcodeData.lastName)
            email_form.setText(qrcodeData.email)
            adresse_form.setText(qrcodeData.address)
            code_postal_form.setText(qrcodeData.zipcode)
            ville_form.setText(qrcodeData.city)
            carte_de_fidelite_form.setText(qrcodeData.cardRef)
        }
        val submitButton = findViewById<Button>(R.id.Submit_Account_Creation_Button)
        submitButton.setOnClickListener {
            val user = User(nom_form.text.toString(), prenom_form.text.toString(), email_form.text.toString(), adresse_form.text.toString(), code_postal_form.text.toString(), ville_form.text.toString(), carte_de_fidelite_form.text.toString())
            val preferenceHelper = PreferenceHelper(this)
            preferenceHelper.saveUser(user)
            val newIntent = Intent(application, CardActivity::class.java)
            startActivity(newIntent)
        }
    }
}