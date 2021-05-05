package fr.fscript98.zapette.autre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R

class APropos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_propos)

        val home =  findViewById<ImageView>(R.id.home)
        home.setOnClickListener{
            finish()
        }
    }
}