package fr.fscript98.zapette

import BddRepository
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //private var BackPressedTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        //private var BackPressedTimer = 0L
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repo = BddRepository()

        //mettre a jour la liste de plant
        repo.updateData {

            val buttonTeacher = findViewById<Button>(R.id.buttonTeacher)
            buttonTeacher.setOnClickListener {

                val intentButtonTeacher = Intent(this , TeacherBoard::class.java)
                startActivity(intentButtonTeacher)
                finish()
            }
            val buttonStudent = findViewById<Button>(R.id.buttonStudent)
            buttonStudent.setOnClickListener {
                val intent = Intent(this , EtudiantQuestionnaire::class.java)
                startActivity(intent)
                finish()


                //charger le ButtonRepository


                //update la liste de buttons
                //repo.updateData()

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