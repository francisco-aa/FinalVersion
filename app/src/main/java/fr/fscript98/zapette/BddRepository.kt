
import BddRepository.Singleton.questionListBdd
import BddRepository.Singleton.ref_questionnaire

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import fr.fscript98.zapette.QuestionModel

class BddRepository{


    object Singleton {
        var motDePasseBdd = ""
        var questionListBdd = arrayListOf<QuestionModel>()

        var chemin=""

        val database = FirebaseDatabase.getInstance()
        val ref_questionnaire = database.getReference("questionnaire")
        var question = "question"
    }

    //se connecter à la reference "buttons"


    //update un objet button dans la bdd
    fun updateData(callback: ()-> Unit){


        //Récupérer le code saisi par l'utilisateur
        ref_questionnaire.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                questionListBdd.clear()
                for (ds in snapshot.children) {
                    val codeBDD = ds.getValue(QuestionModel::class.java)
                    if (codeBDD != null) {
                        questionListBdd.add(codeBDD)
                        //Toast.makeText(, codeBDD.motdepasse.toString(), LENGTH_SHORT).show()
                    }
                }
                callback()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}