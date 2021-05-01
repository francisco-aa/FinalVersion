package fr.fscript98.zapette.enseignant


import android.content.Intent
import android.os.Bundle

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


import fr.fscript98.zapette.R

import fr.fscript98.zapette.autre.EnseignantFragment


class EnseignantMesResultats (): AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enseignant_resultats)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EnseignantFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()
        val intentBack= Intent(this,TeacherBoard::class.java)
        val back = findViewById<ImageView>(R.id.backMesResultats)
        back.setOnClickListener{
            startActivity(intentBack)
            finish()
        }
    }
}