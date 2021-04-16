package fr.fscript98.zapette

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.fscript98.zapette.ButtonRepository.Singleton.buttonsList
import fr.fscript98.zapette.ButtonRepository.Singleton.databaseRef

class ButtonRepository {

    object Singleton {
        //se connecter à la reference "buttons"
        val databaseRef = FirebaseDatabase.getInstance().getReference("buttons")

        //créer une liste qui va contenir les buttons
        val buttonsList = arrayListOf<VoteButtonModel>()
    }

    //update un objet button dans la bdd
    fun updateButton(button:VoteButtonModel){
        databaseRef.child(button.id).setValue(button)
    }
}