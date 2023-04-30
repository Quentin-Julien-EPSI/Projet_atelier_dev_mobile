package fr.epsi.projet_atelier_dev_mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.epsi.projet_atelier_dev_mobile.databinding.ActivityUserHomeBinding

open class UserHomeActivity : BaseActivity() {
    private lateinit var binding: ActivityUserHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(CardFragment());
        setHeaderTitle("Carte")
        val accountbutton = view.findViewById<ImageView>(R.id.imageviewaccount)
        accountbutton.visibility = View.VISIBLE
        accountbutton.setOnClickListener {
            //launch intent for activity account details
            val intent = Intent(this, AccountDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.magasin -> {
                    replaceFragment(StoreFragment())
                    setHeaderTitle("Magasin")
                    val accountbutton = view.findViewById<ImageView>(R.id.imageviewaccount)
                    accountbutton.visibility = View.VISIBLE
                    accountbutton.setOnClickListener {
                        //launch intent for activity account details
                        val intent = Intent(this, AccountDetailsActivity::class.java)
                        startActivity(intent)
                    }
                }
                R.id.offre -> {
                    replaceFragment(OffersFragment())
                    setHeaderTitle("Offres")
                    val accountbutton = view.findViewById<ImageView>(R.id.imageviewaccount)
                    accountbutton.visibility = View.VISIBLE
                    accountbutton.setOnClickListener {
                        //launch intent for activity account details
                        val intent = Intent(this, AccountDetailsActivity::class.java)
                        startActivity(intent)
                    }
                }
                R.id.carte -> {
                    replaceFragment(CardFragment())
                    setHeaderTitle("Carte")
                    val accountbutton = view.findViewById<ImageView>(R.id.imageviewaccount)
                    accountbutton.visibility = View.VISIBLE
                    accountbutton.setOnClickListener {
                        //launch intent for activity account details
                        val intent = Intent(this, AccountDetailsActivity::class.java)
                        startActivity(intent)
                    }
                }
                else -> {
                    replaceFragment(CardFragment())
                    setHeaderTitle("Carte")
                    val accountbutton = view.findViewById<ImageView>(R.id.imageviewaccount)
                    accountbutton.visibility = View.VISIBLE
                    accountbutton.setOnClickListener {
                        //launch intent for activity account details
                        val intent = Intent(this, AccountDetailsActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            true
        }

    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}