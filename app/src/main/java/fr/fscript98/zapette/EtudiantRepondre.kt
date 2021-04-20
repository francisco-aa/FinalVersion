package fr.fscript98.zapette

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.collection.LLRBNode
import fr.fscript98.zapette.EtudiantQuestionnaire.Singleton.buttonsListBdd
import fr.fscript98.zapette.EtudiantQuestionnaire.Singleton.mdp

class EtudiantRepondre : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_repondre)
        // view
        var buttonClique = "null"
        val a = findViewById<Button>(R.id.buttonA)
        val b = findViewById<Button>(R.id.buttonB)
        val c = findViewById<Button>(R.id.buttonC)
        val d = findViewById<Button>(R.id.buttonD)
        val e = findViewById<Button>(R.id.buttonE)
        val f = findViewById<Button>(R.id.buttonF)
        val g = findViewById<Button>(R.id.buttonG)
        val h = findViewById<Button>(R.id.buttonH)
        val i = findViewById<Button>(R.id.buttonI)
        val buttonBack = findViewById<ImageView>(R.id.button_back3)
        val buttonList = arrayListOf<Button>()
        val buttonVal = findViewById<Button>(R.id.buttonValider)
        buttonList.add(a)
        buttonList.add(b)
        buttonList.add(c)
        buttonList.add(d)
        buttonList.add(e)
        buttonList.add(f)
        buttonList.add(g)
        buttonList.add(h)
        buttonList.add(i)
        // intent
        val database = FirebaseDatabase.getInstance()
        val ref_questionnaire = database.getReference("questionnaire")

        buttonBack.setOnClickListener {
            val intentButtonBack = Intent(this , EtudiantQuestionnaire::class.java)
            startActivity(intentButtonBack)
        }
        // changer la couleur de l'écriture pour voir la sélection
        a.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            a.setTextColor(Color.parseColor("#FFBB86FC"))
            buttonClique = "A"
        }

        b.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            b.setTextColor(Color.MAGENTA)
            buttonClique = "B"
        }
        c.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            c.setTextColor(Color.MAGENTA)
            buttonClique = "C"
        }

        d.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            d.setTextColor(Color.MAGENTA)
            buttonClique = "D"
        }
        e.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            e.setTextColor(Color.MAGENTA)
            buttonClique = "E"
        }

        f.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            f.setTextColor(Color.MAGENTA)
            buttonClique = "F"
        }
        g.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            g.setTextColor(Color.MAGENTA)
            buttonClique = "G"
        }

        h.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            h.setTextColor(Color.MAGENTA)
            buttonClique = "H"
        }
        i.setOnClickListener {
            for (button in buttonList) {
                button.setTextColor(Color.BLACK)
            }
            i.setTextColor(Color.MAGENTA)
            buttonClique = "I"
        }

        buttonVal.setOnClickListener {
            if (buttonClique != "null") {
                database.getReference("questionnaire").get().addOnSuccessListener {
                    for (child in it.children) {

                        var mdpBdd = child.getValue(VoteButtonModel::class.java)
                        if (mdpBdd != null) {
                            if (mdp == mdpBdd.motdepasse.toString()) {
                                //Toast.makeText(applicationContext,child.child(buttonClique).value,LENGTH_SHORT).show()
                                val numb = child.child(buttonClique).value.toString().toInt()
                                ref_questionnaire.child(child.ref.key.toString())
                                    .child(buttonClique).setValue(numb + 1)
                                val intentButtonBack2 = Intent(this , MainActivity::class.java)
                                startActivity(intentButtonBack2)
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext ,
                    "Selectionne une réponse la con de ta mere JULIEN" ,
                    LENGTH_SHORT
                ).show()
            }
        }
        //creer une liste qui va stocker les buttons

/*
        val buttonA = findViewById<Button>(R.id.buttonA)
        buttonBack.setOnClickListener{
            repo.updateButton(buttonList[R.id.buttonA])
        }*/
    }
}


