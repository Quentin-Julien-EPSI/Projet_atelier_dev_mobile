package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class StoreDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_details)

        val storeInfo = intent.getSerializableExtra("store_info") as? StoreInfo
        storeInfo?.let {
            setHeaderTitle(it.name)
            showBack()

            val imageView = findViewById<ImageView>(R.id.imageView)
            val addressInfoTextView = findViewById<TextView>(R.id.adress_info)
            val cpTextView = findViewById<TextView>(R.id.cp)
            val descriptionTextView = findViewById<TextView>(R.id.Description)

            Glide.with(this)
                .load(it.pictureStore)
                .into(imageView)

            addressInfoTextView.text = it.address
            cpTextView.text = "${it.zipcode} - ${it.city}"
            descriptionTextView.text = "Description: ${it.description}"
        }
    }

}