package fr.fscript98.zapette

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
import com.google.firebase.database.FirebaseDatabase
import fr.fscript98.zapette.EtudiantQuestionnaire.Singleton.buttonsListBdd

import com.google.zxing.integration.android.IntentIntegrator
import fr.fscript98.zapette.EtudiantQuestionnaire.Singleton.id
import fr.fscript98.zapette.EtudiantQuestionnaire.Singleton.motDePasseBdd


open class EtudiantQuestionnaire() : AppCompatActivity() {
    object Singleton {
        var motDePasseBdd = ""
        var buttonsListBdd = arrayListOf<VoteButtonModel>()
        var id=""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //bloquer en portrait
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_etudiant_questionnaire)

        val database = FirebaseDatabase.getInstance()
        val refQuestionnaire = database.getReference("questionnaire")
        val intent = Intent(this , MainActivity::class.java)
        val intent2 = Intent(this , EtudiantRepondre::class.java)


        val editText = findViewById<EditText>(R.id.zone_saisie_code)
        var codeSaisi =""


        //Récupérer le code saisi par l'utilisateur
        refQuestionnaire.get().addOnSuccessListener {
            buttonsListBdd.clear()
            for (ds in it.children) {
                var codeBDD = ds.getValue(VoteButtonModel::class.java)
                if (codeBDD != null) {
                    buttonsListBdd.add(codeBDD)
                    //Toast.makeText(applicationContext, codeBDD.motdepasse.toString(), LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext , "Ya pa de le code" , LENGTH_SHORT).show()
        }
        //mettre a jour la liste de plant
        val backbutton = findViewById<ImageView>(R.id.button_back)
        backbutton.setOnClickListener {
            refQuestionnaire.child("question").child("A").setValue(0)
            refQuestionnaire.child("question").child("B").setValue(0)
            refQuestionnaire.child("question").child("C").setValue(0)
            refQuestionnaire.child("question").child("D").setValue(0)
            refQuestionnaire.child("question").child("E").setValue(0)
            refQuestionnaire.child("question").child("F").setValue(0)
            refQuestionnaire.child("question").child("G").setValue(0)
            refQuestionnaire.child("question").child("H").setValue(0)
            refQuestionnaire.child("question").child("I").setValue(0)
            refQuestionnaire.child("question").child("motdepasse").setValue(45)
            refQuestionnaire.child("question2").child("A").setValue(0)
            refQuestionnaire.child("question2").child("B").setValue(0)
            refQuestionnaire.child("question2").child("C").setValue(0)
            refQuestionnaire.child("question2").child("D").setValue(0)
            refQuestionnaire.child("question2").child("E").setValue(0)
            refQuestionnaire.child("question2").child("F").setValue(0)
            refQuestionnaire.child("question2").child("G").setValue(0)
            refQuestionnaire.child("question2").child("H").setValue(0)
            refQuestionnaire.child("question2").child("I").setValue(0)
            refQuestionnaire.child("question2").child("motdepasse").setValue(12345)
            refQuestionnaire.child("question3").child("A").setValue(0)
            refQuestionnaire.child("question3").child("B").setValue(0)
            refQuestionnaire.child("question3").child("C").setValue(0)
            refQuestionnaire.child("question3").child("D").setValue(0)
            refQuestionnaire.child("question3").child("E").setValue(0)
            refQuestionnaire.child("question3").child("F").setValue(0)
            refQuestionnaire.child("question3").child("G").setValue(0)
            refQuestionnaire.child("question3").child("H").setValue(0)
            refQuestionnaire.child("question3").child("I").setValue(0)
            refQuestionnaire.child("question3").child("motdepasse").setValue(0)



            startActivity(intent)
            finish()
        }
        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener {
            Toast.makeText(applicationContext , buttonsListBdd.size.toString() , LENGTH_SHORT).show()
            codeSaisi = editText.text.toString()
            //TODO: Si codesaisi vide, redémarrer l'activity avec message d'erreur vide
            if (codeSaisi==""){
                //Toast.makeText(applicationContext , "Veuillez entrer un code" , LENGTH_SHORT).show()
            }
            for (questionModel in buttonsListBdd) {

                //Toast.makeText(applicationContext, codesaisi, LENGTH_SHORT).show()
                if (codeSaisi == questionModel.motdepasse.toString()) {
                    if (id !=questionModel.motdepasse.toString()) {
                        motDePasseBdd = questionModel.motdepasse.toString()
                        startActivity(intent2)
                    }else {
                        Toast.makeText(applicationContext , "Tu as déjà participé" , LENGTH_SHORT)
                            .show()
                    }
                } else {
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
                        Toast.makeText(
                            this ,
                            "Le code est : ${result.contents}" ,
                            Toast.LENGTH_LONG
                        ).show()
                        val intent2 = Intent(this , EtudiantRepondre::class.java)
                        startActivity(intent2)
                        finish()
                    }
                } else {
                    super.onActivityResult(requestCode , resultCode , data)
                }
            }
        }

}
