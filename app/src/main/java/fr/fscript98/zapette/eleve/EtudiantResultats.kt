package fr.fscript98.zapette.eleve

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.FirebaseDatabase
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.BddRepository.Singleton.motDePasseBdd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

import android.view.animation.AlphaAnimation as AlphaAnimation1

class EtudiantResultats : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_etudiant_resultats)
        }
        else{
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                setContentView(R.layout.activity_etudiant_resultats_land)
            }
        }

        val rep1 = findViewById<TextView>(R.id.rep1) //Reponse etudiant
        val rep1_card = findViewById<CardView>(R.id.rep1_card)
        val rep2 = findViewById<TextView>(R.id.rep2) //Reponse correcte
        val rep2_card = findViewById<CardView>(R.id.rep2_card)
        var bonneReponse = ""
        var reponseEtudiant = ""
        val sharedPreferences = getSharedPreferences("shared_prefs" , MODE_PRIVATE)
        val refQuestionnaire = FirebaseDatabase.getInstance().getReference("questionnaire")

        val back = findViewById<ImageView>(R.id.button_back)
        back.setOnClickListener {
            finish()
        }

        val accueil = findViewById<Button>(R.id.accueil)
        accueil.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        fun oh(rep: Boolean) {
            GlobalScope.launch(context = Dispatchers.Main) {
                rep1.setText(reponseEtudiant)
                rep2.setText(bonneReponse)
                val anim = AlphaAnimation1(0.0f , 1.0f)
                anim.duration = 1000
                anim.repeatCount = 0
                anim.startOffset = 0
                rep1.startAnimation(anim)

                val anim2 = AlphaAnimation1(0.0f , 1.0f)
                anim2.duration = 1000
                anim2.repeatCount = 0
                anim2.startOffset = 0
                rep1_card.startAnimation(anim2)

                val anim3 = AlphaAnimation1(0.0f , 1.0f)
                anim3.duration = 1000
                anim3.repeatCount = 0
                anim3.startOffset = 1500
                rep2.startAnimation(anim3)

                val anim4 = AlphaAnimation1(0.0f , 1.0f)
                anim4.duration = 1000
                anim4.repeatCount = 0
                anim4.startOffset = 1500
                rep2_card.startAnimation(anim4)
            }

            if (!rep)
                GlobalScope.launch(context = Dispatchers.Main) {
                    delay(2500)
                    rep1_card.setCardBackgroundColor(Color.RED)
                }
            else
                GlobalScope.launch(context = Dispatchers.Main) {
                    val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)
                    delay(2500)
                    rep1_card.setCardBackgroundColor(Color.GREEN)

                    viewKonfetti.build()
                        .addColors(
                            Color.YELLOW ,
                            Color.GREEN ,
                            Color.MAGENTA ,
                            Color.CYAN ,
                            Color.RED ,
                            Color.BLUE
                        )
                        .setDirection(250.0 , 280.0)
                        .setSpeed(16f , 12f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square , Shape.Circle)
                        .addSizes(Size(8))
                        .setPosition(1140f , null , 2200f , null)
                        .streamFor(200 , 1000L)

                    viewKonfetti.build()
                        .addColors(
                            Color.YELLOW ,
                            Color.GREEN ,
                            Color.MAGENTA ,
                            Color.CYAN ,
                            Color.RED ,
                            Color.BLUE
                        )
                        .setDirection(270.0 , 290.0)
                        .setSpeed(16f , 12f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square , Shape.Circle)
                        .addSizes(Size(8))
                        .setPosition(-50f , null , 2200f , null)
                        .streamFor(200 , 1000L)
                }
        }

        refQuestionnaire.get().addOnSuccessListener {
            for (question in it.children) {
                if (motDePasseBdd == question.child("motdepasse").value.toString()) {
                    bonneReponse = question.child("bonneReponse").value.toString()
                    reponseEtudiant =
                        sharedPreferences.getString(question.key.toString() , "").toString()

                    if (bonneReponse == reponseEtudiant && bonneReponse != "")
                        oh(true)
                    else if (bonneReponse != "")
                        oh(false)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}