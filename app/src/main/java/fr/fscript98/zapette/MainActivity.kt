package fr.fscript98.zapette

import fr.fscript98.zapette.autre.BddRepository
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.eleve.EtudiantQuestionnaire
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire
import fr.fscript98.zapette.enseignant.TeacherBoard

class MainActivity : AppCompatActivity() {
    //private var BackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {

        //private var BackPressedTimer = 0L
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repo = BddRepository()
        repo.updateData {
            val buttonTeacher = findViewById<Button>(R.id.buttonTeacher)
            buttonTeacher.setOnClickListener {

                val intentButtonTeacher = Intent(this , TeacherBoard::class.java)
                startActivity(intentButtonTeacher)
            }
            val buttonStudent = findViewById<Button>(R.id.buttonStudent)
            buttonStudent.setOnClickListener {
                val intent = Intent(this , EtudiantQuestionnaire::class.java)
                startActivity(intent)
            }
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