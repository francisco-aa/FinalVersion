package fr.fscript98.zapette.enseignant

import fr.fscript98.zapette.autre.BddRepository.Singleton.chemin
import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.questionListBdd
import android.content.Intent
import android.os.Bundle
import android.widget.Button

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt
import kotlin.random.Random

class TeacherBoard : AppCompatActivity() {
    //private var BackPressedTime=0L
    object Singleton{
        var myRandomInt =1
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_board)
        val databaseRef = FirebaseDatabase.getInstance().getReference("questionnaire")

        /*val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            val intentButtonBack = Intent(this, MainActivity::class.java)
            startActivity(intentButtonBack)
        }*/

        val buttonRunQuiz = findViewById<Button>(R.id.buttonRunQuiz)
        buttonRunQuiz.setOnClickListener{

            question="question"
            chemin= (questionListBdd.size+1).toString()
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
            val intentButtonRunQuiz = Intent(this, ResultatQuestionnaire::class.java)
            startActivity(intentButtonRunQuiz)
            finish()



        }
        val buttonBack= findViewById<ImageView>(R.id.button_backEspaceEnseignant)
        val intantBack= Intent(this, MainActivity::class.java)
        buttonBack.setOnClickListener{
            startActivity(intantBack)
            finish()
        }
    }
    /*override fun onBackPressed() {
    if (BackPressedTime+2000 > System.currentTimeMillis()) {
        super.onBackPressed()
    }else {
        Toast.makeText(applicationContext , "Appuyez deux fois pour quitter." , Toast.LENGTH_SHORT).show()
    }
    BackPressedTime = System.currentTimeMillis()
}*/
}