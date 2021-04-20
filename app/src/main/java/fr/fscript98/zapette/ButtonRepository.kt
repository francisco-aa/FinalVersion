package fr.fscript98.zapette

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ButtonRepository {


    //se connecter à la reference "buttons"
    var databaseRef = FirebaseDatabase.getInstance().reference


    //créer une liste qui va contenir les buttons
    val buttonsList = arrayListOf<VoteButtonModel>()



    var getdata = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot){
            for (ds in snapshot.children){
                val code= ds.getValue(VoteButtonModel::class.java)
                if(code!=null){
                    buttonsList.add(code)
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }




    //update un objet button dans la bdd
    /*fun updateButton(button:VoteButtonModel){
        databaseRef.child(button.id).setValue(button.nbVotes++)
    }*/
}
