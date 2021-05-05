package fr.fscript98.zapette.enseignant

import fr.fscript98.zapette.autre.BddRepository.Singleton.chemin
import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.questionListBdd
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R

import fr.fscript98.zapette.autre.BddRepository
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.autre.SharedPreference
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt
import kotlin.random.Random

class TeacherBoard : AppCompatActivity() {
    //private var BackPressedTime=0L
    object Singleton {
        var myRandomInt = 1
        var position = -1
        var questionModelList = arrayListOf<QuestionModel>()
        val mesView = arrayListOf<View>()
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_board)
        val sharedPreference = SharedPreference(this)
        val databaseRef = FirebaseDatabase.getInstance().getReference("questionnaire")
        val editText = findViewById<EditText>(R.id.saisirTitre)

        val buttonRunQuiz = findViewById<Button>(R.id.buttonRunQuiz)
        buttonRunQuiz.setOnClickListener {
            var titreSaisi = editText.text.toString()
            if (titreSaisi == "") {

                titreSaisi = "Aucun"
            }
            myRandomInt = Random.nextInt(10000 , 100000)
            question = "question"
            chemin = (myRandomInt).toString()
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

            databaseRef.child(question).child("motdepasse").setValue(myRandomInt)
            databaseRef.child(question).child("questionTerminee").setValue("false")
            databaseRef.child(question).child("bonneReponse").setValue("")
            databaseRef.child(question).child("titre").setValue(titreSaisi)
            val intentButtonRunQuiz = Intent(this , ResultatQuestionnaire::class.java)
            startActivity(intentButtonRunQuiz)
            finish()



        }
        val buttonBack = findViewById<ImageView>(R.id.button_backEspaceEnseignant)
        val intantBack = Intent(this , MainActivity::class.java)
        buttonBack.setOnClickListener {

            startActivity(intantBack)
            finish()
        }

        val intentResultats = Intent(this , EnseignantMesResultats::class.java)
        val resultatEnseignant = findViewById<Button>(R.id.mesResultats)
        resultatEnseignant.setOnClickListener {

            sharedPreference.showSR()
            startActivity(intentResultats)

        }


    }
}





