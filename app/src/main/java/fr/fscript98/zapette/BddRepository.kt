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
    fun updateData(callback: ()-> Unit){
        val database = FirebaseDatabase.getInstance()
        val ref_questionnaire = database.getReference("questionnaire")

        //Récupérer le code saisi par l'utilisateur
        ref_questionnaire.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                questionListBdd.clear()
                for (ds in snapshot.children) {
                    var codeBDD = ds.getValue(QuestionModel::class.java)
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