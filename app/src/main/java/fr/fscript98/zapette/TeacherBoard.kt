package fr.fscript98.zapette

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class TeacherBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_board)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            val intentButtonBack = Intent(this, MainActivity::class.java)
            startActivity(intentButtonBack)
        }

        val buttonRunQuiz = findViewById<Button>(R.id.buttonRunQuiz)
        buttonRunQuiz.setOnClickListener{
            val intentButtonRunQuiz = Intent(this, CreateQuiz::class.java)
            startActivity(intentButtonRunQuiz)
        }
    }
}