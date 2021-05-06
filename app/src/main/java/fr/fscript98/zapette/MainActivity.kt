package fr.fscript98.zapette

import fr.fscript98.zapette.autre.BddRepository
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import fr.fscript98.zapette.autre.APropos
import fr.fscript98.zapette.eleve.EtudiantQuestionnaire
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

            val aPropos = findViewById<LinearLayout>(R.id.linearLayoutAPropos)
            aPropos.setOnClickListener{
                val intentAPropos = Intent(this, APropos::class.java)
                startActivity(intentAPropos)
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