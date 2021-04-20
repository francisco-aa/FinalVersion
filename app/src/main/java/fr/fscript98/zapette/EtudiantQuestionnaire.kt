package fr.fscript98.zapette

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator


class EtudiantQuestionnaire() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_questionnaire)

        val database = FirebaseDatabase.getInstance()
        val ref_questionnaire = database.getReference("questionnaire")
        val intent = Intent(this, MainActivity::class.java)
        val intent2 = Intent(this, EtudiantRepondre::class.java)

        var buttonsList = arrayListOf<VoteButtonModel>()
        val editText = findViewById<EditText>(R.id.zone_saisie_code)
        val codesaisi = editText.text.toString()

        //Récupérer le code saisi par l'utilisateur
        ref_questionnaire.get().addOnSuccessListener {
            for (ds in it.children){
                var codeBDD= ds.getValue(VoteButtonModel::class.java)
                if (codeBDD!=null){
                    buttonsList.add(codeBDD)
                    //Toast.makeText(applicationContext, codeBDD.motdepasse.toString(), LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener{
            Toast.makeText(applicationContext, "Ya pa de le code", LENGTH_SHORT).show()
        }
        //mettre a jour la liste de plant
        val backbutton = findViewById<ImageView>(R.id.button_back)
        backbutton.setOnClickListener{
            startActivity(intent)
        }

        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener {
            val codesaisi = editText.text.toString()
            //TODO: Si codesaisi vide, redémarrer l'activity avec message d'erreur vide

            for (tu in buttonsList) {
                val codesaisi = editText.text.toString()
                //Toast.makeText(applicationContext, codesaisi, LENGTH_SHORT).show()
                if (codesaisi == tu.motdepasse.toString()) {

                    startActivity(intent2)
                } else {
                }
            }
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
