package fr.epsi.projet_atelier_dev_mobile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private fun generateBarcode(fidelityCardNumber: String) {
        val imageView = requireView().findViewById<ImageView>(R.id.barcodeImageView)
        val barcodeBitmap = encodeAsBitmap(fidelityCardNumber, BarcodeFormat.CODE_128, 800, 300)
        imageView.setImageBitmap(barcodeBitmap)
    }
    @Throws(WriterException::class)
    private fun encodeAsBitmap(contents: String, format: BarcodeFormat, imgWidth: Int, imgHeight: Int): Bitmap {
        val writer = MultiFormatWriter()
        val bitMatrix: BitMatrix = writer.encode(contents, format, imgWidth, imgHeight)
        val pixels = IntArray(imgWidth * imgHeight)
        for (y in 0 until imgHeight) {
            val offset = y * imgWidth
            for (x in 0 until imgWidth) {
                pixels[offset + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, imgWidth, 0, 0, imgWidth, imgHeight)
        return bitmap
    }

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferenceHelper = PreferenceHelper(activity?.applicationContext!!)

        val clearSharedPreferencesButton: Button = requireView().findViewById(R.id.clearsharedpreferences)
        clearSharedPreferencesButton.setOnClickListener {
//            clear the shared preferences
            preferenceHelper.clearSharedPreferences()
//            restart the app
            val ctx = getActivity()?.getApplicationContext();
            val pm = ctx?.packageManager
            val intent = pm?.getLaunchIntentForPackage(ctx.packageName)
            val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
            ctx.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }
        val textViewNames = requireView().findViewById<TextView>(R.id.textViewNames)
        textViewNames.text = (preferenceHelper.getUser()?.firstName ?: "Prénom") + " " + (preferenceHelper.getUser()?.lastName ?: "Nom")
        generateBarcode(preferenceHelper.getUser()?.cardRef ?: "0000000000")
        val textViewCardRef = requireView().findViewById<TextView>(R.id.textViewCardRef)
        textViewCardRef.text = preferenceHelper.getUser()?.cardRef ?: "0000000000"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}