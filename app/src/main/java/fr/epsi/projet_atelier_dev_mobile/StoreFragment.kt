package fr.epsi.projet_atelier_dev_mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

data class StoreInfo(
    val storeId: Int,
    val name: String,
    val description: String,
    val pictureStore: String,
    val address: String,
    val zipcode: String,
    val city: String,
    val longitude: Double,
    val latitude: Double
) : Serializable

class StoreFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    class CustomInfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

        override fun getInfoContents(marker: Marker): View {
            val view = LayoutInflater.from(context).inflate(R.layout.custom_map_info_window, null)

            val storeNameTextView = view.findViewById<TextView>(R.id.storeName)
            val storeAddressTextView = view.findViewById<TextView>(R.id.storeAddress)

            val storeInfo = marker.tag as? StoreInfo

            storeNameTextView.text = storeInfo?.name
            storeAddressTextView.text = storeInfo?.address

            return view
        }

        override fun getInfoWindow(marker: Marker): View? {
            return null
        }
    }


    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private val apiURL = "https://www.ugarit.online/epsi/stores.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return view
    }
    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.setInfoWindowAdapter(CustomInfoWindowAdapter(requireContext())) // Add this line
        fetchStoresAndAddMarkers()
        googleMap?.setOnInfoWindowClickListener(this)
    }

    override fun onInfoWindowClick(marker: Marker) {
        val storeInfo = marker.tag as? StoreInfo
        storeInfo?.let {
            val intent = Intent(requireActivity(), StoreDetailsActivity::class.java).apply {
                putExtra("store_info", it)
            }
            startActivity(intent)
        }
    }




    private fun fetchStoresAndAddMarkers() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(apiURL)
                .get()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val data = response.body?.string()

                    if (data != null) {
                        val jsonObject = JSONObject(data)
                        val stores = jsonObject.getJSONArray("stores")
                        addMarkersToMap(stores)
                    }
                }
            })
        }
    }

    private fun addMarkersToMap(stores: JSONArray) {
        activity?.runOnUiThread {
            for (i in 0 until stores.length()) {
                val store = stores.getJSONObject(i)
                val lat = store.getDouble("latitude")
                val lng = store.getDouble("longitude")
                val storeId = store.getInt("storeId")
                val name = store.getString("name")
                val description = store.getString("description")
                val pictureStore = store.getString("pictureStore")
                val address = store.getString("address")
                val zipcode = store.getString("zipcode")
                val city = store.getString("city")
                val position = LatLng(lat, lng)

                val marker = googleMap?.addMarker(MarkerOptions().position(position).title(name))
                marker?.tag = StoreInfo(
                    storeId, name, description, pictureStore,
                    address, zipcode, city, lng, lat
                )
            }
            googleMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(48.856614, 2.3522219), 5f)
            )
        }
    }




    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

