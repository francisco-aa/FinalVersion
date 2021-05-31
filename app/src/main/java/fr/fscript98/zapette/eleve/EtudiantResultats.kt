package fr.fscript98.zapette.eleve

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.Configuration
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.fscript98.zapette.MainActivity
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.BddRepository.Singleton.motDePasseBdd
import fr.fscript98.zapette.autre.ConnectionPerdue
import fr.fscript98.zapette.autre.InternetConnection
import fr.fscript98.zapette.autre.SharedPreference
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
        var konfetti = true

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            konfetti = true
            setContentView(R.layout.activity_etudiant_resultats)
        } else {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                konfetti = false //TODO Affichage confettis en landscape
                setContentView(R.layout.activity_etudiant_resultats_land)
            }
        }

        //Verification de la connection
        val intentConnectionPerdue = Intent(this, ConnectionPerdue::class.java)
        val internetConnection = InternetConnection(this)
        internetConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if (!isConnected){
                startActivity(intentConnectionPerdue)
            }
        })

        var bonneReponse = ""
        //var reponseEtudiant = ""
        var tabReponses = listOf("" , "" , "" , "" , "" , "" , "" , "" , "")
        var tabBonneReponses = listOf("" , "" , "" , "" , "" , "" , "" , "" , "")
        var refQuestionString = ""
        val sharedPreference = SharedPreference(this)
        val refQuestionnaire = FirebaseDatabase.getInstance().getReference("questionnaire")

        val etudiant_rep1 = findViewById<TextView>(R.id.etudiant_rep1)
        val etudiant_rep1_card = findViewById<CardView>(R.id.etudiant_rep1_card)
        val etudiant_rep2 = findViewById<TextView>(R.id.etudiant_rep2)
        val etudiant_rep2_card = findViewById<CardView>(R.id.etudiant_rep2_card)
        val etudiant_rep3 = findViewById<TextView>(R.id.etudiant_rep3)
        val etudiant_rep3_card = findViewById<CardView>(R.id.etudiant_rep3_card)
        val etudiant_rep4 = findViewById<TextView>(R.id.etudiant_rep4)
        val etudiant_rep4_card = findViewById<CardView>(R.id.etudiant_rep4_card)
        val etudiant_rep5 = findViewById<TextView>(R.id.etudiant_rep5)
        val etudiant_rep5_card = findViewById<CardView>(R.id.etudiant_rep5_card)
        val etudiant_rep6 = findViewById<TextView>(R.id.etudiant_rep6)
        val etudiant_rep6_card = findViewById<CardView>(R.id.etudiant_rep6_card)
        val etudiant_rep7 = findViewById<TextView>(R.id.etudiant_rep7)
        val etudiant_rep7_card = findViewById<CardView>(R.id.etudiant_rep7_card)
        val etudiant_rep8 = findViewById<TextView>(R.id.etudiant_rep8)
        val etudiant_rep8_card = findViewById<CardView>(R.id.etudiant_rep8_card)
        val etudiant_rep9 = findViewById<TextView>(R.id.etudiant_rep9)
        val etudiant_rep9_card = findViewById<CardView>(R.id.etudiant_rep9_card)

        val prof_rep1 = findViewById<TextView>(R.id.prof_rep1)
        val prof_rep1_card = findViewById<CardView>(R.id.prof_rep1_card)
        val prof_rep2 = findViewById<TextView>(R.id.prof_rep2)
        val prof_rep2_card = findViewById<CardView>(R.id.prof_rep2_card)
        val prof_rep3 = findViewById<TextView>(R.id.prof_rep3)
        val prof_rep3_card = findViewById<CardView>(R.id.prof_rep3_card)
        val prof_rep4 = findViewById<TextView>(R.id.prof_rep4)
        val prof_rep4_card = findViewById<CardView>(R.id.prof_rep4_card)
        val prof_rep5 = findViewById<TextView>(R.id.prof_rep5)
        val prof_rep5_card = findViewById<CardView>(R.id.prof_rep5_card)
        val prof_rep6 = findViewById<TextView>(R.id.prof_rep6)
        val prof_rep6_card = findViewById<CardView>(R.id.prof_rep6_card)
        val prof_rep7 = findViewById<TextView>(R.id.prof_rep7)
        val prof_rep7_card = findViewById<CardView>(R.id.prof_rep7_card)
        val prof_rep8 = findViewById<TextView>(R.id.prof_rep8)
        val prof_rep8_card = findViewById<CardView>(R.id.prof_rep8_card)
        val prof_rep9 = findViewById<TextView>(R.id.prof_rep9)
        val prof_rep9_card = findViewById<CardView>(R.id.prof_rep9_card)

        val mapCardViewEtudiant = mapOf(
            0 to (etudiant_rep1 to etudiant_rep1_card) ,
            1 to (etudiant_rep2 to etudiant_rep2_card) ,
            2 to (etudiant_rep3 to etudiant_rep3_card) ,
            3 to (etudiant_rep4 to etudiant_rep4_card) ,
            4 to (etudiant_rep5 to etudiant_rep5_card) ,
            5 to (etudiant_rep6 to etudiant_rep6_card) ,
            6 to (etudiant_rep7 to etudiant_rep7_card) ,
            7 to (etudiant_rep8 to etudiant_rep8_card) ,
            8 to (etudiant_rep9 to etudiant_rep9_card)
        )

        val mapCardViewProf = mapOf(
            0 to (prof_rep1 to prof_rep1_card) ,
            1 to (prof_rep2 to prof_rep2_card) ,
            2 to (prof_rep3 to prof_rep3_card) ,
            3 to (prof_rep4 to prof_rep4_card) ,
            4 to (prof_rep5 to prof_rep5_card) ,
            5 to (prof_rep6 to prof_rep6_card) ,
            6 to (prof_rep7 to prof_rep7_card) ,
            7 to (prof_rep8 to prof_rep8_card) ,
            8 to (prof_rep9 to prof_rep9_card)
        )

        //fonction de décision réponse juste/fausse
        fun affichage() {
            tabReponses = tabReponses.sorted()
            tabBonneReponses = tabBonneReponses.sorted()

            for (i in 0..tabReponses.size - 1) {
                mapCardViewEtudiant[i]!!.second!!.setCardBackgroundColor(Color.RED)
                for (j in 0..tabBonneReponses.size - 1) {
                    if (tabReponses[i] == tabBonneReponses[j]) {
                        mapCardViewEtudiant[i]!!.second!!.setCardBackgroundColor(Color.GREEN)
                    }
                }
            }

            //Cacher les cardview vides
            for (i in 0..8) {
                mapCardViewEtudiant[i]!!.second!!.visibility = View.GONE
            }
            for (i in 0..8) {
                mapCardViewProf[i]!!.second!!.visibility = View.GONE
            }



            GlobalScope.launch(context = Dispatchers.Main) {
                for (i in 0..tabReponses.size - 1) {
                    val anim = AlphaAnimation1(0.0f , 1.0f)
                    anim.duration = 700
                    anim.repeatCount = 0
                    anim.startOffset = 0
                    mapCardViewEtudiant[i]!!.first!!.setText(tabReponses[i])
                    mapCardViewEtudiant[i]!!.first!!.startAnimation(anim)
                    mapCardViewEtudiant[i]!!.second!!.startAnimation(anim)
                    mapCardViewEtudiant[i]!!.second!!.visibility = View.VISIBLE
                    delay(700L)
                }
            }
            GlobalScope.launch(context = Dispatchers.Main) {
                for (i in 0..tabBonneReponses.size - 1) {
                    val anim = AlphaAnimation1(0.0f , 1.0f)
                    anim.duration = 1000
                    anim.repeatCount = 0
                    anim.startOffset = 0
                    mapCardViewProf[i]!!.first!!.setText(tabBonneReponses[i])
                    mapCardViewProf[i]!!.first!!.startAnimation(anim)
                    mapCardViewProf[i]!!.second!!.startAnimation(anim)
                    mapCardViewProf[i]!!.second!!.visibility = View.VISIBLE
                    delay(700L)
                }
            }

            if (tabReponses.equals(tabBonneReponses)) {
                GlobalScope.launch(context = Dispatchers.Main)
                {
                    val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)
                    delay(1000)
                    //audioBullshit(true)
                    if (konfetti) {
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
        }

        //parcours BDD
        refQuestionnaire.get().addOnSuccessListener {
            for (question in it.children) {
                if (motDePasseBdd == question.child("motdepasse").value.toString()) {
                    refQuestionString = question.key.toString()
                    bonneReponse = question.child("bonneReponse").value.toString()
                    tabBonneReponses =
                        bonneReponse.toCharArray().map { it.toString() }.toMutableList()
                    tabReponses = sharedPreference.SpToArray(refQuestionString)

                    affichage()

                    val intentRelance = Intent(this , EtudiantRepondre::class.java)
                    val listener = //Placement d'un listener actif sur questionTerminee dans la bdd
                        refQuestionnaire.child(refQuestionString).child("questionTerminee")
                            .addValueEventListener(
                                object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.value.toString() == "false") {
                                            if (EtudiantRepondre.Singleton.shouldRun) {
                                                startActivity(intentRelance) //question terminée= false, revenir a l'activité précédente
                                                sharedPreference.editor.remove(refQuestionString)
                                                sharedPreference.editor.apply() //Suppression de la réponse enregistrée car la question change
                                            }
                                            refQuestionnaire.child(refQuestionString)
                                                .child("questionTerminee")
                                                .removeEventListener(this) //Détruit le listener
                                            finish()
                                        }
                                        if (snapshot.value.toString() == "Termine"){
                                            refQuestionnaire.child(refQuestionString)
                                                .child("questionTerminee")
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


        val back = findViewById<ImageView>(R.id.button_back)
        back.setOnClickListener {
            finish()
        }

        val accueil = findViewById<Button>(R.id.accueil)
        accueil.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            //intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            //intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            //startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    //Attention: Cettte fonction est extrêmement importante et ne doit en aucun cas être supprimée.
    fun audioBullshit(v: Boolean) {
        val audioA = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_GAME).build()
        val spool = SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioA).build()
        val letsgo = spool.load(this , R.raw.letsgo , 2)
        val cetaitsur = spool.load(this , R.raw.sardoche , 2)
        //val bruh = spool.load(this, R.raw.bruh, 1)
        //val wow = spool.load(this, R.raw.wow, 1)
        if (v) {
            //Toast.makeText(this, v.toString(), LENGTH_SHORT).show()
            spool.play(letsgo , 1.0F , 1.0F , 1 , 0 , 1.0F)
            //spool.play(wow , 1.0F , 1.0F , 0 , 0 , 1.0F)
        } else {
            spool.play(cetaitsur , 1.0F , 1.0F , 1 , 0 , 1.0F)
            //spool.play(bruh , 1.0F , 1.0F , 0 , 0 , 1.0F)
        }
    }
}