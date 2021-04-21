package fr.fscript98.zapette

import BddRepository.Singleton.chemin
import BddRepository.Singleton.questionListBdd
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import fr.fscript98.zapette.TeacherBoard.Singleton.myRandomInt
import kotlin.random.Random

class TeacherBoard : AppCompatActivity() {
    object Singleton{
    var myRandomInt =1
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_board)
        var databaseRef = FirebaseDatabase.getInstance().getReference("questionnaire")

        /*val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            val intentButtonBack = Intent(this, MainActivity::class.java)
            startActivity(intentButtonBack)
        }*/

        val buttonRunQuiz = findViewById<Button>(R.id.buttonRunQuiz)
        buttonRunQuiz.setOnClickListener{

            var question = "question"
            chemin= questionListBdd.size.toString()
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
            val intentButtonRunQuiz = Intent(this, ResultatQuestionnaire::class.java)
            startActivity(intentButtonRunQuiz)


        }
    }
}