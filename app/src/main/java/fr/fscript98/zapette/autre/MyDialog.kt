package fr.fscript98.zapette.autre

import android.app.Dialog
import android.content.Context


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import fr.fscript98.zapette.MainActivity.singleton.plustard
import fr.fscript98.zapette.enseignant.EnseignantResultats

import fr.fscript98.zapette.enseignant.ResultatQuestionnaire
import fr.fscript98.zapette.enseignant.TeacherBoard
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionListAttente
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList
import java.lang.IllegalStateException

class MyDialog (context: Context): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val intentResultats = Intent(context , EnseignantResultats::class.java)
        val sharedPreference = context?.let { SharedPreference(it) }
        return activity?.let{
            val alertBuilder = AlertDialog.Builder(it)

            alertBuilder.setTitle("Sauvegarder")
            alertBuilder.setMessage("Vous avez : "+ questionListAttente.size +" résultats de questionnaire en attente de sauvegarde.\n " +
                    "Voulez-vous sauvegarder ?")


            alertBuilder.setPositiveButton("Oui", DialogInterface.OnClickListener{dialog, id ->
                Log.d("mydialoglog", "Oui Pressed")
                questionModelList.clear()
                if (sharedPreference != null) {
                    questionModelList = sharedPreference.loadDataG()!!
                }
                for (question in questionListAttente){
                    if (!questionModelList.contains(question)) {
                            questionModelList.add(question)
                    }
                }
                if (sharedPreference != null) {
                    sharedPreference.saveDataG()
                }
                questionListAttente.clear()
                if (sharedPreference != null) {
                    sharedPreference.saveDataE()
                }
                startActivity(intentResultats)



            }).setNegativeButton("Non", DialogInterface.OnClickListener{dialog, id ->
                Log.d("mydialoglog", "Non Pressed")
                questionListAttente.clear()
                startActivity(intentResultats)
            }).setNeutralButton("Plus tard", DialogInterface.OnClickListener{dialog, id ->
                Log.d("mydialoglog", "Annuler Pressed")
                plustard=1
                startActivity(intentResultats)

            })
            alertBuilder.create()
        } ?: throw IllegalStateException("Exception !! Activity is null !!")
    }
}