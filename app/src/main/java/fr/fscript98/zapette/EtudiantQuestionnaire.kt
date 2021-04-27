package fr.fscript98.zapette



import BddRepository.Singleton.id
import BddRepository.Singleton.motDePasseBdd
import BddRepository.Singleton.questionListBdd
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator



open class EtudiantQuestionnaire : AppCompatActivity() {
    //private var BackPressedTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bloquer en portrait
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_etudiant_questionnaire)
        val intent = Intent(this , MainActivity::class.java)
        val intent2 = Intent(this , EtudiantRepondre::class.java)


        val editText = findViewById<EditText>(R.id.zone_saisie_code)
        var codeSaisi=""


        val backbutton = findViewById<ImageView>(R.id.button_back)
        backbutton.setOnClickListener {

            startActivity(intent)
            finish()
        }
        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener {
            //Toast.makeText(applicationContext , questionListBdd.size.toString() , LENGTH_SHORT).show()
             var codeSaisi = editText.text.toString()
            //TODO: Si codesaisi vide, redémarrer l'activity avec message d'erreur vide
            if (codeSaisi != "") {
                Toast.makeText(applicationContext , "Veuillez entrer un code" , LENGTH_SHORT).show()
            }
            for (questionModel in questionListBdd) {

                //Toast.makeText(applicationContext, codesaisi, LENGTH_SHORT).show()
                if (codeSaisi == questionModel.motdepasse.toString()) {
                    if (id != questionModel.motdepasse.toString()) {
                        motDePasseBdd = questionModel.motdepasse.toString()
                        startActivity(intent2)
                        finish()
                    } else {
                        Toast.makeText(applicationContext , "Tu as déjà participé" , LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }


        val buttonScan = findViewById<Button>(R.id.button_scan)
        buttonScan.setOnClickListener() {

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
                            if (id!=questionModel.motdepasse.toString()){
                                startActivity(intent2)
                                finish()
                            }
                            else{
                                Toast.makeText(applicationContext,"Tu as déjà participé",
                                    LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                super.onActivityResult(requestCode , resultCode , data)
            }
        }
    }
}


