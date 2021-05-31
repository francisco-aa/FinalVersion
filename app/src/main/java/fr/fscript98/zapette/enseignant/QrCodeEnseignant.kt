package fr.fscript98.zapette.enseignant


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import fr.fscript98.zapette.R
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt

class QrCodeEnseignant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code_enseignant)
        val intent= Intent(this, ResultatQuestionnaire::class.java)
        val imageCode3 = findViewById<ImageView>(R.id.imageQrCodeEnseignant)
        val qrCode = QRCodeWriter()
        val barcodeEncoder = BarcodeEncoder()
        val bitMtx = qrCode.encode(
            "$myRandomInt" ,
            BarcodeFormat.QR_CODE ,
            5000,
            5000
        )
        val bitMap2 = barcodeEncoder.createBitmap(bitMtx)
        imageCode3.setImageBitmap(bitMap2)

        imageCode3.setOnClickListener{
            startActivity(intent)
            finish()
        }
    }
}