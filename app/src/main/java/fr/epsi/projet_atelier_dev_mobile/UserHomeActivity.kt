package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.epsi.projet_atelier_dev_mobile.databinding.ActivityUserHomeBinding

class UserHomeActivity : BaseActivity() {
    private lateinit var binding: ActivityUserHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(new CardFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()) {

                case R.id.store:
                        replaceFragment(new StoreFragment())
                        break;
                case R.id.offers:
                        replaceFragment(new OffersFragment())
                        break;
                case R.id.card:
                        replaceFragment((new CardFragment()))
                        break;
            }

            return true
        });
    }

    private fun  replaceFragment( Fragment fragment) {

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout.fragment);
        fragmentTransaction.commit();
    }
}