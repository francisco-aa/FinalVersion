package fr.fscript98.zapette.enseignant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.BddRepository
import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.ref_questionnaire
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModel
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt

class ResultatQuestionnaireFinal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultat_questionnaire_final)

        val txtViewA = findViewById<TextView>(R.id.nbreponseAFin)
        val txtViewB = findViewById<TextView>(R.id.nbreponseBFin)
        val txtViewC = findViewById<TextView>(R.id.nbreponseCFin)
        val txtViewD = findViewById<TextView>(R.id.nbreponseDFin)
        val txtViewE = findViewById<TextView>(R.id.nbreponseEFin)
        val txtViewF = findViewById<TextView>(R.id.nbreponseFFin)
        val txtViewG = findViewById<TextView>(R.id.nbreponseGFin)
        val txtViewH = findViewById<TextView>(R.id.nbreponseHFin)
        val txtViewI = findViewById<TextView>(R.id.nbreponseIFin)
        val txtViewTotal = findViewById<TextView>(R.id.nbTotalFin)

        if (myRandomInt == questionModel.motdepasse) {
            val nbResA = questionModel.A
            val nbResB = questionModel.B
            val nbResC = questionModel.C
            val nbResD = questionModel.D
            val nbResE = questionModel.E
            val nbResF = questionModel.F
            val nbResG = questionModel.G
            val nbResH = questionModel.H
            val nbResI = questionModel.I
            val nbResTotal = nbResA + nbResB + nbResC + nbResD + nbResE + nbResF + nbResG + nbResH + nbResI

            if (nbResA!=0) {
                txtViewA.text = ("$nbResA")
            }
            if (nbResB!=0) {
                txtViewB.text = ("$nbResB")
            }
            if (nbResC!=0) {
                txtViewC.text = ("$nbResC")
            }
            if(nbResD!=0){
                txtViewD.text = ("$nbResD")
            }
            if (nbResE!=0){
                txtViewE.text = ("$nbResE")
            }
            if(nbResF!=0) {
                txtViewF.text = ("$nbResF")
            }
            if (nbResG!=0){
                txtViewG.text = ("$nbResG")
            }
            if (nbResH!=0){
                txtViewH.text = ("$nbResH")
            }
            if (nbResI!=0) {
                txtViewI.text = ("$nbResI")
            }
            txtViewTotal.text = ("$nbResTotal")

            }

        val quitter = findViewById<Button>(R.id.btn_quit)
        val intentQuitter = Intent(this, TeacherBoard::class.java)
        quitter.setOnClickListener{
           ref_questionnaire.child(question).removeValue()
            startActivity(intentQuitter)
            finish()
        }

    }
}