package fr.epsi.projet_atelier_dev_mobile

import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator

class AccountCreationActivity : BaseActivity() {
    private val CAMERA_REQUEST_CODE = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_creation)
        setHeaderTitle("Création de compte")
        val preferenceHelper = PreferenceHelper(this)

        val scanQRButton = findViewById<ImageButton>(R.id.scanQRButton)
        scanQRButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
            } else {
                startQRScanActivity()
            }
        }

        val manualFormButton = findViewById<Button>(R.id.manualFormButton)
        manualFormButton.setOnClickListener {
            val intent = Intent(this, AccountCreationFormActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startQRScanActivity()
        }
    }


    private val QR_SCAN_REQUEST_CODE = 2001

    private fun startQRScanActivity() {
        val integrator = IntentIntegrator(this)
        integrator.captureActivity = CaptureActivityAnyOrientation::class.java
        integrator.setOrientationLocked(false)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR code")
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // Handle the case when the user cancels the QR code scanning
                Log.d("QRCodeData", "Cancelled")
            } else {
                // Handle the decoded QR code data here
                val qrcodeData = result.contents
                val intent = Intent(this, AccountCreationFormActivity::class.java)
                intent.putExtra("qrcodeData", qrcodeData)
                startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}