package fr.fscript98.zapette

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class EtudiantQuestionnaire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_questionnaire)

        val buttonBack = findViewById<ImageView>(R.id.button_back4)
        buttonBack.setOnClickListener {
            val intentButtonBack = Intent(this , MainActivity::class.java)
            startActivity(intentButtonBack)

        }

        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener {
            val intent = Intent(this , EtudiantRepondre::class.java)
            startActivity(intent)
        }
        val button_scan = findViewById<Button>(R.id.button_scan)
        button_scan.setOnClickListener() {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }
    }
    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode , resultCode , data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this , "Cancelled" , Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this , "Le code est : ${result.contents}" , Toast.LENGTH_LONG).show()
                }
            } else {
                super.onActivityResult(requestCode , resultCode , data)
            }
        }
    }
}