package fr.fscript98.zapette


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlin.random.Random


class CreateQuiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack2)
        buttonBack.setOnClickListener {
            val intentButtonBack = Intent(this , TeacherBoard::class.java)
            startActivity(intentButtonBack)
        }
        val textView = findViewById<android.widget.TextView>(R.id.textView2)
        val imageView = findViewById<ImageView>(R.id.imageQrCode)
        val buttonCode = findViewById<Button>(R.id.btnGenerate)
        buttonCode.setOnClickListener {
            val myRandomInt = Random.nextInt(10000 , 100000)
            textView.text = ("$myRandomInt")
        }
        val myRandomInt = Random.nextInt(10000, 100000)
        //val qrCode = QRCodeWriter()
        val qrCode = MultiFormatWriter()

        val bitMtx = qrCode.encode(
            "$myRandomInt",
            BarcodeFormat.QR_CODE,
            100,
            100
        )
        val imageCode = findViewById<ImageView>(R.id.imageQrCode)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.createBitmap(bitMtx)
        buttonCode.setOnClickListener {
            textView.text=("$myRandomInt")
            //MatrixToImageWriter.(bitMtx, "PNG", imageCode)
            imageCode.setImageBitmap(bitmap)
        }
    }
}
