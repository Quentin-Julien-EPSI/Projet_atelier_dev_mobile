package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle
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

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.magasin -> {
                    replaceFragment(StoreFragment())
                    setHeaderTitle("Magasin")
                }
                R.id.offre -> {
                    replaceFragment(OffersFragment())
                    setHeaderTitle("Offres")
                }
                R.id.carte -> {
                    replaceFragment(CardFragment())
                    setHeaderTitle("Carte")
                }
                else -> {
                    replaceFragment(CardFragment())
                    setHeaderTitle("Carte")
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