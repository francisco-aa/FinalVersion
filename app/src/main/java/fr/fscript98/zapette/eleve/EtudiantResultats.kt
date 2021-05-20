package fr.fscript98.zapette.eleve

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.Configuration
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.BddRepository.Singleton.motDePasseBdd
import fr.fscript98.zapette.autre.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import kotlin.random.Random
import android.view.animation.AlphaAnimation as AlphaAnimation1


class EtudiantResultats : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var konfetti = true

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            konfetti = true
            setContentView(R.layout.activity_etudiant_resultats)
        }
        else{
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                konfetti = false //TODO Affichage confettis en landscape
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
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        //fonction de décision réponse juste/fausse
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
                    //audioBullshit(false)
                }
            else
                GlobalScope.launch(context = Dispatchers.Main) {
                    val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)
                    delay(2500)
                    //audioBullshit(true)
                    rep1_card.setCardBackgroundColor(Color.GREEN)
                    if (konfetti){
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
        }

        refQuestionnaire.get().addOnSuccessListener {
            for (question in it.children) {
                if (motDePasseBdd == question.child("motdepasse").value.toString()) {
                    bonneReponse = question.child("bonneReponse").value.toString()
                    reponseEtudiant =
                        sharedPreferences.getString(question.key.toString() , "").toString()

                    if (reponseEtudiant != "" && bonneReponse == ""){
                        oh(false)
                    }
                    else if (bonneReponse == reponseEtudiant && bonneReponse != "")
                        oh(true)
                    else if (bonneReponse != "")
                        oh(false)
                    else{
                        rep1.setText("")
                        rep2.setText("")
                    }
                }
            }
        }

        val sharedPreference = SharedPreference(this)
        var rep_etudiant = ""
        val intentRelance= Intent(this,EtudiantRepondre::class.java)
        refQuestionnaire.get().addOnSuccessListener {
            for (question in it.children) {
                if (question.child("motdepasse").value.toString() == motDePasseBdd) {
                    var ref = question.key.toString()
                    if (sharedPreference.isIn(ref))
                        rep_etudiant =
                            sharedPreference.loadData(ref) //La derniere réponse locale devient la dernière réponse enregistrée pour la question
                    else
                        sharedPreference.saveData(
                            ref ,
                            ""
                        ) //On a jamais participé à cette question, donc champ vide

                    var listener = //Placement d'un listener actif sur questionTerminee dans la bdd
                        refQuestionnaire.child(ref).child("questionTerminee").addValueEventListener(
                            object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.value.toString() == "false") {
                                        if (EtudiantRepondre.Singleton.shouldRun){
                                            startActivity(intentRelance) //question terminée= false, revenir a l'activité précédente
                                            sharedPreference.editor.remove(ref)
                                            sharedPreference.editor.apply() //Suppression de la réponse enregistrée car la question change
                                        }
                                        refQuestionnaire.child(ref).child("questionTerminee")
                                            .removeEventListener(this) //Détruit le listener
                                        finish()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {}
                            }
                        )
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }





    //Attention: Cettte fonction est extrêmement importante et ne doit en aucun cas être supprimée.
    fun audioBullshit(v: Boolean){
        val audioA = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build()
        val spool = SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioA).build()
        val letsgo = spool.load(this, R.raw.letsgo, 2)
        val cetaitsur = spool.load(this, R.raw.sardoche, 2)
        //val bruh = spool.load(this, R.raw.bruh, 1)
        //val wow = spool.load(this, R.raw.wow, 1)
        if (v) {
            //Toast.makeText(this, v.toString(), LENGTH_SHORT).show()
            spool.play(letsgo , 1.0F , 1.0F , 1 , 0 , 1.0F)
            //spool.play(wow , 1.0F , 1.0F , 0 , 0 , 1.0F)
        }

        else {
            spool.play(cetaitsur , 1.0F , 1.0F , 1 , 0 , 1.0F)
            //spool.play(bruh , 1.0F , 1.0F , 0 , 0 , 1.0F)
        }
    }
}