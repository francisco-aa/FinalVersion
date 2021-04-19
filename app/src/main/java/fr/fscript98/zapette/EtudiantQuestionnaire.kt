package fr.fscript98.zapette

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity


class EtudiantQuestionnaire() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_questionnaire)

        //val database = FirebaseDatabase.getInstance()
        //val ref_questionnaire = database.getReference("questionnaire")
        val intent = Intent(this, MainActivity::class.java)
        val intent2 = Intent(this, EtudiantRepondre::class.java)
        var codeUtilisateur = "12345"





        //Récupérer le code saisi par l'utilisateur


        //mettre a jour la liste de plant

        val backbutton = findViewById<ImageView>(R.id.button_back)
        backbutton.setOnClickListener{
            startActivity(intent)
        }
        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener {

            val editText = findViewById<EditText>(R.id.zone_saisie_code)
            val codesaisi = editText.text.toString()

                //TODO: Si codesaisi vide, redémarrer l'activity avec message d'erreur vide
                /*ref_questionnaire.child("question").get().addOnSuccessListener {
                codeBDD = it.value.toString()
                Toast.makeText(applicationContext, codeBDD, LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(applicationContext, "Ya pa de le code", LENGTH_SHORT).show()
            }*/




            if (codesaisi == codeUtilisateur) {
                Toast.makeText(applicationContext , "oui" , LENGTH_SHORT).show()
                startActivity(intent2)
            } else {
                            Toast.makeText(applicationContext , "non" , LENGTH_SHORT).show()
            }



        }


    }

}