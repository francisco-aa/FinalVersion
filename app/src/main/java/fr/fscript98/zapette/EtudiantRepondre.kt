package fr.fscript98.zapette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class EtudiantRepondre : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_repondre)

        val buttonBack = findViewById<ImageView>(R.id.button_back3)
        buttonBack.setOnClickListener{
            val intentButtonBack = Intent(this, EtudiantQuestionnaire::class.java)
            startActivity(intentButtonBack)
        }

        //recuperer le repository
        val repo = ButtonRepository()

        //creer une liste qui va stocker les buttons
        val buttonList = arrayListOf<VoteButtonModel>()

        val buttonA = findViewById<Button>(R.id.buttonA)
        buttonBack.setOnClickListener{
            repo.updateButton(buttonList[R.id.buttonA])
        }
    }
}

