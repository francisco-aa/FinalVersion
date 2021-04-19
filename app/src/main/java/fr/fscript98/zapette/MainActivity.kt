package fr.fscript98.zapette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val repo=ButtonRepository()

        //mettre a jour la liste de plant
        //repo.updateData ()

        val buttonTeacher = findViewById<Button>(R.id.buttonTeacher)
        buttonTeacher.setOnClickListener {

            val intentButtonTeacher = Intent(this , TeacherBoard::class.java)
            startActivity(intentButtonTeacher)
        }
        val buttonStudent = findViewById<Button>(R.id.buttonStudent)
        buttonStudent.setOnClickListener {
            val intent = Intent(this , EtudiantQuestionnaire::class.java)
            startActivity(intent)


            //charger le ButtonRepository


            //update la liste de buttons
            //repo.updateData()
        }
    }

}