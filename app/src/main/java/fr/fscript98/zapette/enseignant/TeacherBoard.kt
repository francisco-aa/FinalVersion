package fr.fscript98.zapette.enseignant

import fr.fscript98.zapette.autre.BddRepository.Singleton.chemin
import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.questionListBdd
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModelList
import fr.fscript98.zapette.autre.BddRepository
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt
import kotlin.random.Random

class TeacherBoard : AppCompatActivity() {
    //private var BackPressedTime=0L
    object Singleton {
        var myRandomInt = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_board)
        val databaseRef = FirebaseDatabase.getInstance().getReference("questionnaire")
        val editText = findViewById<EditText>(R.id.saisirTitre)

        val buttonRunQuiz = findViewById<Button>(R.id.buttonRunQuiz)
        buttonRunQuiz.setOnClickListener {
            var titreSaisi = editText.text.toString()
            if (titreSaisi == "") {
                Toast.makeText(applicationContext , myRandomInt.toString() , LENGTH_SHORT).show()
                titreSaisi = "Aucun"
            }

            question = "question"
            chemin = (questionListBdd.size + 1).toString()
            question += chemin

            databaseRef.child(question).child("A").setValue(0)
            databaseRef.child(question).child("B").setValue(0)
            databaseRef.child(question).child("C").setValue(0)
            databaseRef.child(question).child("D").setValue(0)
            databaseRef.child(question).child("E").setValue(0)
            databaseRef.child(question).child("F").setValue(0)
            databaseRef.child(question).child("G").setValue(0)
            databaseRef.child(question).child("H").setValue(0)
            databaseRef.child(question).child("I").setValue(0)
            myRandomInt = Random.nextInt(10000 , 100000)
            databaseRef.child(question).child("motdepasse").setValue(myRandomInt)
            databaseRef.child(question).child("questionTerminee").setValue("false")
            databaseRef.child(question).child("bonneReponses").setValue("A")
            databaseRef.child(question).child("titre").setValue(titreSaisi)
            val intentButtonRunQuiz = Intent(this , ResultatQuestionnaire::class.java)
            startActivity(intentButtonRunQuiz)
            finish()


        }
        val buttonBack = findViewById<ImageView>(R.id.button_backEspaceEnseignant)
        val intantBack = Intent(this , MainActivity::class.java)
        buttonBack.setOnClickListener {
            Toast.makeText(applicationContext , questionModelList.size.toString() , LENGTH_SHORT)
                .show()
            startActivity(intantBack)
            finish()
        }

        val intentResultats = Intent(this , EnseignantMesResultats::class.java)
        val resultatEnseignant = findViewById<Button>(R.id.mesResultats)
        resultatEnseignant.setOnClickListener {
            startActivity(intentResultats)

        }
    }
}





