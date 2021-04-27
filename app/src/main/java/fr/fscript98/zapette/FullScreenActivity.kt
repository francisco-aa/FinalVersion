package fr.fscript98.zapette

import BddRepository.Singleton.motDePasseBdd
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder



class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)


        val qrCode = QRCodeWriter()

        val bitMtx = qrCode.encode(
            motDePasseBdd ,
            BarcodeFormat.QR_CODE ,
            1700 ,
            1700
        )
        val imageButton = findViewById<ImageView>(R.id.imageButton)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.createBitmap(bitMtx)
        imageButton.setImageBitmap(bitmap)
        val button2 = findViewById<ImageButton>(R.id.imageButton)
        val zoomOut = Intent(this , EtudiantRepondre::class.java)
        button2.setOnClickListener{
            startActivity(zoomOut)
            finish()
        }
        //imageCode.setImageBitmap(bitmap)
    }
}