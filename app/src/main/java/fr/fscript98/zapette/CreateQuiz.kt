package fr.fscript98.zapette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class CreateQuiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack2)
        buttonBack.setOnClickListener{
            val intentButtonBack = Intent(this, TeacherBoard::class.java)
            startActivity(intentButtonBack)
        }
    }
}