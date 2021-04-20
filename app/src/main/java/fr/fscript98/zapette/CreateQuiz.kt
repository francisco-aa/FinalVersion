package fr.fscript98.zapette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder


class CreateQuiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack2)
        buttonBack.setOnClickListener{
            val intentButtonBack = Intent(this, TeacherBoard::class.java)
            startActivity(intentButtonBack)
        }

        val myRandomInt = Random.nextInt(10000, 100000)
        val textView = findViewById<TextView>(R.id.textView2)
        val qrCode = QRCodeWriter()

        val bitMtx = qrCode.encode(
            "$myRandomInt",
            BarcodeFormat.QR_CODE,
            100,
            100
        )
        val imageCode = findViewById<ImageView>(R.id.imageQrCode)
        val buttonCode = findViewById<Button>(R.id.btnGenerate)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.createBitmap(bitMtx)
        buttonCode.setOnClickListener {
            textView.text=("$myRandomInt")
            imageCode.setImageBitmap(bitmap)
        }

    }
}