import BddRepository.Singleton.chemin
import BddRepository.Singleton.questionListBdd
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.fscript98.zapette.EtudiantQuestionnaire

import fr.fscript98.zapette.QuestionModel
import fr.fscript98.zapette.TeacherBoard
import java.nio.file.Files.size
import kotlin.coroutines.coroutineContext
import kotlin.random.Random


class BddRepository{

    
    object Singleton {
        var motDePasseBdd = ""
        var questionListBdd = arrayListOf<QuestionModel>()
        var id = ""
        var chemin=""
    }

    //se connecter à la reference "buttons"
    var databaseRef = FirebaseDatabase.getInstance().getReference("questionnaire")

    //update un objet button dans la bdd
    fun updateQuestion(){
        val database = FirebaseDatabase.getInstance()
        val ref_questionnaire = database.getReference("questionnaire")

        //Récupérer le code saisi par l'utilisateur
        ref_questionnaire.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    var codeBDD = ds.getValue(QuestionModel::class.java)
                    if (codeBDD != null) {
                        questionListBdd.add(codeBDD)
                        //Toast.makeText(, codeBDD.motdepasse.toString(), LENGTH_SHORT).show()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        var question = "question"
        chemin= questionListBdd.size.toString()
        databaseRef.child(chemin).child("A").setValue(0)
        databaseRef.child(chemin).child("B").setValue(0)
        databaseRef.child(chemin).child("C").setValue(0)
        databaseRef.child(chemin).child("D").setValue(0)
        databaseRef.child(chemin).child("E").setValue(0)
        databaseRef.child(chemin).child("F").setValue(0)
        databaseRef.child(chemin).child("G").setValue(0)
        databaseRef.child(chemin).child("H").setValue(0)
        databaseRef.child(chemin).child("I").setValue(0)
        var mdp = Random.nextInt(10000 , 100000)
        databaseRef.child(chemin).child("motdepasse").setValue(mdp)
    }

}