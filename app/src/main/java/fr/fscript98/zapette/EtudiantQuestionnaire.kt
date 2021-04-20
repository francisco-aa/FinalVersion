package fr.fscript98.zapette

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import fr.fscript98.zapette.EtudiantQuestionnaire.Singleton.buttonsListBdd
import fr.fscript98.zapette.EtudiantQuestionnaire.Singleton.mdp


class EtudiantQuestionnaire() : AppCompatActivity() {
    object Singleton {
        var mdp=""
        var buttonsListBdd = arrayListOf<VoteButtonModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_questionnaire)

        val database = FirebaseDatabase.getInstance()
        val ref_questionnaire = database.getReference("questionnaire")
        val intent = Intent(this, MainActivity::class.java)
        val intent2 = Intent(this, EtudiantRepondre::class.java)


        val editText = findViewById<EditText>(R.id.zone_saisie_code)
        val codesaisi = editText.text.toString()



        //Récupérer le code saisi par l'utilisateur
        ref_questionnaire.get().addOnSuccessListener {
            for (ds in it.children){
                var codeBDD= ds.getValue(VoteButtonModel::class.java)
                if (codeBDD!=null){
                    buttonsListBdd.add(codeBDD)
                //Toast.makeText(applicationContext, codeBDD.motdepasse.toString(), LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener{
            Toast.makeText(applicationContext, "Ya pa de le code", LENGTH_SHORT).show()
        }
        //mettre a jour la liste de plant
        val backbutton = findViewById<ImageView>(R.id.button_back)
        backbutton.setOnClickListener{
            ref_questionnaire.child("question").child("A").setValue(0)
            ref_questionnaire.child("question").child("B").setValue(0)
            ref_questionnaire.child("question").child("C").setValue(0)
            ref_questionnaire.child("question").child("D").setValue(0)
            ref_questionnaire.child("question").child("E").setValue(0)
            ref_questionnaire.child("question").child("F").setValue(0)
            ref_questionnaire.child("question").child("G").setValue(0)
            ref_questionnaire.child("question").child("H").setValue(0)
            ref_questionnaire.child("question").child("I").setValue(0)
            ref_questionnaire.child("question").child("motdepasse").setValue(45)
            ref_questionnaire.child("question2").child("A").setValue(0)
            ref_questionnaire.child("question2").child("B").setValue(0)
            ref_questionnaire.child("question2").child("C").setValue(0)
            ref_questionnaire.child("question2").child("D").setValue(0)
            ref_questionnaire.child("question2").child("E").setValue(0)
            ref_questionnaire.child("question2").child("F").setValue(0)
            ref_questionnaire.child("question2").child("G").setValue(0)
            ref_questionnaire.child("question2").child("H").setValue(0)
            ref_questionnaire.child("question2").child("I").setValue(0)
            ref_questionnaire.child("question2").child("motdepasse").setValue(12345)
            ref_questionnaire.child("question3").child("A").setValue(0)
            ref_questionnaire.child("question3").child("B").setValue(0)
            ref_questionnaire.child("question3").child("C").setValue(0)
            ref_questionnaire.child("question3").child("D").setValue(0)
            ref_questionnaire.child("question3").child("E").setValue(0)
            ref_questionnaire.child("question3").child("F").setValue(0)
            ref_questionnaire.child("question3").child("G").setValue(0)
            ref_questionnaire.child("question3").child("H").setValue(0)
            ref_questionnaire.child("question3").child("I").setValue(0)
            ref_questionnaire.child("question3").child("motdepasse").setValue(0)

            startActivity(intent)
        }
        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener {
            val codesaisi = editText.text.toString()
            //TODO: Si codesaisi vide, redémarrer l'activity avec message d'erreur vide

            for (tu in buttonsListBdd) {
                val codesaisi = editText.text.toString()
                //Toast.makeText(applicationContext, codesaisi, LENGTH_SHORT).show()
                if (codesaisi == tu.motdepasse.toString()) {
                    startActivity(intent2)
                    mdp =tu.motdepasse.toString()
                } else {
                }
            }
        }
    }
}