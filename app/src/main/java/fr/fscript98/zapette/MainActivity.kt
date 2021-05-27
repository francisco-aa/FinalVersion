package fr.fscript98.zapette

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import fr.fscript98.zapette.MainActivity.singleton.plustard
import fr.fscript98.zapette.autre.*
import fr.fscript98.zapette.eleve.EtudiantQuestionnaire
import fr.fscript98.zapette.enseignant.TeacherBoard


class MainActivity : AppCompatActivity() {
    object singleton {
        var plustard = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main)
        } else {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.activity_main_land)
            }
        }

        //Verification de la connection
        val intentConnectionPerdue = Intent(this , ConnectionPerdue::class.java)
        val internetConnection = InternetConnection(this)
        internetConnection.observe(this , androidx.lifecycle.Observer { isConnected ->
            if (!isConnected) {
                startActivity(intentConnectionPerdue)
            }
        })

        val repo = BddRepository()
        repo.updateData {

            val buttonTeacher = findViewById<Button>(R.id.buttonTeacher)
            buttonTeacher.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        this ,
                        "PERMISSION_GRANTED"
                    ) == PERMISSION_GRANTED
                ) {
                }
                val intentButtonTeacher = Intent(this , TeacherBoard::class.java)

                startActivity(intentButtonTeacher)
            }

            val buttonStudent = findViewById<Button>(R.id.buttonStudent)
            buttonStudent.setOnClickListener {
                val intent = Intent(this , EtudiantQuestionnaire::class.java)
                startActivity(intent)
            }

            val aPropos = findViewById<LinearLayout>(R.id.linearLayoutAPropos)
            aPropos.setOnClickListener {
                val intentAPropos = Intent(this , APropos::class.java)
                startActivity(intentAPropos)
            }
        }
    }
}

/*
    override fun onDestroy() {
        super.onDestroy()
        cm.unregisterNetworkCallback(ConnectivityCallback())
    }
*/
