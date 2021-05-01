package fr.fscript98.zapette.eleve

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R
import java.lang.Thread.sleep
import android.view.animation.AlphaAnimation as AlphaAnimation1

class EtudiantResultats : AppCompatActivity() {

    fun load(){
        val v = findViewById<TextView>(R.id.rep1)
        val anim = AlphaAnimation1(0.0f , 1.0f)
        anim.duration = 2000
        anim.repeatCount = 0
        v.startAnimation(anim)

        val v3 = findViewById<CardView>(R.id.rep1_card)
        val anim3 = AlphaAnimation1(0.0f , 1.0f)
        anim3.duration = 2000
        anim3.repeatCount = 0
        v3.startAnimation(anim3)

        sleep(1000)

        val v2 = findViewById<TextView>(R.id.rep2)
        val anim2 = AlphaAnimation1(0.0f , 1.0f)
        anim2.duration = 3000
        anim2.repeatCount = 0
        v2.startAnimation(anim2)

        val v4 = findViewById<CardView>(R.id.rep2_card)
        val anim4 = AlphaAnimation1(0.0f , 1.0f)
        anim4.duration = 3000
        anim4.repeatCount = 0
        v4.startAnimation(anim4)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_resultats)

       load()

        val backbutton = findViewById<ImageView>(R.id.button_back)
        backbutton.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}