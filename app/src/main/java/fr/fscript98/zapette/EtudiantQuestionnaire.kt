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

        //mettre a jour la liste de plant
        val backbutton = findViewById<ImageView>(R.id.button_back)
        backbutton.setOnClickListener{
            startActivity(intent)
        }
        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener {
            val codesaisi = editText.text.toString()
            //TODO: Si codesaisi vide, redémarrer l'activity avec message d'erreur vide
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
            for (tu in buttonsList) {
                val codesaisi = editText.text.toString()
                Toast.makeText(applicationContext, codesaisi, LENGTH_SHORT).show()
                if (codesaisi == tu.motdepasse.toString()) {

                    startActivity(intent2)
                } else {
                }
            }
        }
    }
}