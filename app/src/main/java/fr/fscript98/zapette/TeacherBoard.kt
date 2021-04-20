package fr.fscript98.zapette

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class TeacherBoard : AppCompatActivity() {
    object Singleton {
        var mdp=""
        var buttonsListBdd = arrayListOf<VoteButtonModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_board)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            val intentButtonBack = Intent(this, MainActivity::class.java)
            startActivity(intentButtonBack)
        }

        val repo = ButtonRepository()

        val buttonRunQuiz = findViewById<Button>(R.id.buttonRunQuiz)
        buttonRunQuiz.setOnClickListener{
            val mdp = Random.nextInt(10000 , 100000)
            repo.updateQuestion(VoteButtonModel(A=0, B=0, C=0, D=0, E=0, F=0, G=0, H=0, I=0, motdepasse = mdp))
            val intentButtonRunQuiz = Intent(this, CreateQuiz::class.java)
            startActivity(intentButtonRunQuiz)
        }
    }
}