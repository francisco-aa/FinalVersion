package fr.fscript98.zapette.eleve

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.FirebaseDatabase
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R
import fr.fscript98.zapette.eleve.EtudiantQuestionnaire.Singleton.fromQuestionnaire
import fr.fscript98.zapette.eleve.EtudiantQuestionnaire.Singleton.mdp
import fr.fscript98.zapette.eleve.EtudiantRepondre.Singleton.reponseFournie

import android.view.animation.AlphaAnimation as AlphaAnimation1


class EtudiantResultats : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_resultats)

        val rep1 = findViewById<TextView>(R.id.rep1) //Reponse etudiant
        val rep1_card = findViewById<CardView>(R.id.rep1_card)
        val rep2 = findViewById<TextView>(R.id.rep2) //Reponse correcte
        val rep2_card = findViewById<CardView>(R.id.rep2_card)

        rep2_card.setCardBackgroundColor(Color.GREEN)
        rep1_card.setCardBackgroundColor(Color.RED)

        var bonneReponse = ""
        val sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
        val refQuestionnaire = FirebaseDatabase.getInstance().getReference("questionnaire")
        var reponseEtudiant = reponseFournie

        if (fromQuestionnaire)
        refQuestionnaire.get().addOnSuccessListener {
            for (question in it.children){
                if (mdp == question.child("motdepasse").value.toString()){
                    bonneReponse = question.child("bonneReponse").toString()
                    reponseEtudiant = sharedPreferences.getString(question.key.toString(), "").toString()
                    rep1.setText(reponseEtudiant)
                }
            }
        }

        rep1.setText(reponseEtudiant)
        rep2.setText(bonneReponse)

        rep1_card.setCardBackgroundColor(Color.GREEN)

        val anim = AlphaAnimation1(0.0f , 1.0f)
        anim.duration = 2000
        anim.repeatCount = 0
        rep1.startAnimation(anim)

        val anim3 = AlphaAnimation1(0.0f , 1.0f)
        anim3.duration = 2000
        anim3.repeatCount = 0
        rep1_card.startAnimation(anim3)

        val anim2 = AlphaAnimation1(0.0f , 1.0f)
        anim2.duration = 3000
        anim2.repeatCount = 0
        rep2.startAnimation(anim2)

        val anim4 = AlphaAnimation1(0.0f , 1.0f)
        anim4.duration = 3000
        anim4.repeatCount = 0
        rep2_card.startAnimation(anim4)

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