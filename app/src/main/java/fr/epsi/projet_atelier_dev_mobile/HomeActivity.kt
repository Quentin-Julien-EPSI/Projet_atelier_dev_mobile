package fr.epsi.projet_atelier_dev_mobile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix


class HomeActivity : BaseActivity() {
    private fun generateBarcode(fidelityCardNumber: String) {
        val imageView = findViewById<ImageView>(R.id.barcodeImageView)
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
        setContentView(R.layout.activity_home)
        setHeaderTitle("Logo EPSI")
        val preferenceHelper = PreferenceHelper(this)

        val clearSharedPreferencesButton = findViewById<Button>(R.id.clearsharedpreferences)
        clearSharedPreferencesButton.setOnClickListener {
//            clear the shared preferences
            preferenceHelper.clearSharedPreferences()
//            restart the app
            val ctx = applicationContext
            val pm = ctx.packageManager
            val intent = pm.getLaunchIntentForPackage(ctx.packageName)
            val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
            ctx.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }

        val textViewNames = findViewById<TextView>(R.id.textViewNames)
        textViewNames.text = (preferenceHelper.getUser()?.firstName ?: "Prénom") + " " + (preferenceHelper.getUser()?.lastName ?: "Nom")
        generateBarcode(preferenceHelper.getUser()?.cardRef ?: "0000000000")
        val textViewCardRef = findViewById<TextView>(R.id.textViewCardRef)
        textViewCardRef.text = preferenceHelper.getUser()?.cardRef ?: "0000000000"
    }
}