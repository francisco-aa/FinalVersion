package fr.fscript98.zapette.autre

import android.content.Intent
import android.content.res.Configuration
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.isVisible
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R

class APropos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_a_propos)
        }
        else{
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                setContentView(R.layout.activity_a_propos_land)
            }
        }

        val home =  findViewById<ImageView>(R.id.home)
        home.setOnClickListener{
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}