package fr.fscript98.zapette

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import fr.fscript98.zapette.MainActivity.singleton.plustard
import fr.fscript98.zapette.autre.APropos
import fr.fscript98.zapette.autre.BddRepository
import fr.fscript98.zapette.autre.ConnectivityCallback
import fr.fscript98.zapette.eleve.EtudiantQuestionnaire
import fr.fscript98.zapette.enseignant.TeacherBoard

class MainActivity : AppCompatActivity() {
    object singleton {
        var plustard=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Verification de la connection
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.registerDefaultNetworkCallback(ConnectivityCallback())
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            val connected = capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true

            if (!connected)
                Toast.makeText(this , "Connectez-vous à Internet afin d'utiliser l'application" , LENGTH_LONG).show()

            else {
                val repo = BddRepository()
                repo.updateData {

                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        setContentView(R.layout.activity_main)
                    } else {
                        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setContentView(R.layout.activity_main_land)
                        }
                    }

                    val buttonTeacher = findViewById<Button>(R.id.buttonTeacher)
                    buttonTeacher.setOnClickListener {
                        if (ContextCompat.checkSelfPermission(this ,"PERMISSION_GRANTED") == PERMISSION_GRANTED) {}
                        val intentButtonTeacher = Intent(this , TeacherBoard::class.java)
                        plustard=0
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
    }
/*
    override fun onDestroy() {
        super.onDestroy()
        cm.unregisterNetworkCallback(ConnectivityCallback())
    }
*/
}