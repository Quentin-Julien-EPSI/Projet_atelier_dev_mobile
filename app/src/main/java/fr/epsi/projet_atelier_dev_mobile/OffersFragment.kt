package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

data class Offer(val name: String, val description: String, val pictureUrl: String)

class OfferAdapter(private val offers: List<Offer>) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView1)
        val textView: TextView = view.findViewById(R.id.textView1)
        val textView2: TextView = view.findViewById(R.id.textView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.textView.text = offer.name
        holder.textView2.text = offer.description
        holder.textView2.maxLines = 2
        Glide.with(holder.itemView.context).load(offer.pictureUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return offers.size
    }
}

class OffersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun fetchOffers() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://www.ugarit.online/epsi/offers.json")
                .get()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle failure
                }

                override fun onResponse(call: Call, response: Response) {
                    val data = response.body?.string()

                    if (data != null) {
                        val jsonObject = JSONObject(data)
                        val items = jsonObject.getJSONArray("items")
                        val offers = mutableListOf<Offer>()

                        for (i in 0 until items.length()) {
                            val item = items.getJSONObject(i)
                            val name = item.getString("name")
                            val description = item.getString("description")
                            val pictureUrl = item.getString("picture_url")
                            offers.add(Offer(name, description, pictureUrl))
                        }

                        setupRecyclerView(offers)
                    }
                }
            })
        }
    }

    private fun setupRecyclerView(offers: List<Offer>) {
        activity?.runOnUiThread {
            val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_offers)
            recyclerView?.layoutManager = LinearLayoutManager(context)
            recyclerView?.adapter = OfferAdapter(offers)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchOffers()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OffersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}