package fr.fscript98.zapette.eleve

import fr.fscript98.zapette.autre.BddRepository.Singleton.motDePasseBdd
import fr.fscript98.zapette.autre.BddRepository.Singleton.questionListBdd
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.Capture
import fr.fscript98.zapette.autre.ConnectionPerdue
import fr.fscript98.zapette.autre.InternetConnection
import fr.fscript98.zapette.eleve.EtudiantQuestionnaire.Singleton.fromQuestionnaire

open class EtudiantQuestionnaire : AppCompatActivity() {

    object Singleton {
        var fromQuestionnaire = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_etudiant_questionnaire)
        }
        else{
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                setContentView(R.layout.activity_etudiant_questionnaire_land)
            }
        }

        //Verification de la connection
        val intentConnectionPerdue = Intent(this, ConnectionPerdue::class.java)
        val internetConnection = InternetConnection(this)
        internetConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if (!isConnected){
                startActivity(intentConnectionPerdue)
            }
        })

        val intent2 = Intent(this , EtudiantRepondre::class.java)
        val intent3 = Intent(this , EtudiantResultats::class.java)
        val editText = findViewById<EditText>(R.id.zone_saisie_code)
        var codeSaisi: String


        val backbutton = findViewById<ImageView>(R.id.button_back)
        backbutton.setOnClickListener {
            finish()
        }

        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener {
            codeSaisi = editText.text.toString()
            for (questionModel in questionListBdd) {
                if (codeSaisi == questionModel.motdepasse.toString()) {
                    motDePasseBdd = questionModel.motdepasse.toString()
                    if (questionModel.questionTerminee == "true") {
                        fromQuestionnaire = true
                        startActivity(intent3)
                    } else {
                        startActivity(intent2)
                    }
                }
            }
        }

        val buttonScan = findViewById<Button>(R.id.button_scan)
        buttonScan.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            scanner.setBeepEnabled(false)
            scanner.setOrientationLocked(false)
            scanner.setCaptureActivity(Capture::class.java)
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
                    val intent2 = Intent(this , EtudiantRepondre::class.java)
                    for (questionModel in questionListBdd) {
                        if (questionModel.motdepasse == result.contents.toInt()) {
                            motDePasseBdd = questionModel.motdepasse.toString()
                            startActivity(intent2)
                        }
                    }
                }
            } else {
                super.onActivityResult(requestCode , resultCode , data)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}


