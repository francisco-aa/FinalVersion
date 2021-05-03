package fr.fscript98.zapette.autre


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.database.FirebaseDatabase


class SharedPreference(val context: Context) {
    val spEtudiantName = "shared_prefs"
    val spEtudiant : SharedPreferences = context.getSharedPreferences(spEtudiantName, Context.MODE_PRIVATE)
    val editor : SharedPreferences.Editor = spEtudiant.edit()
    val refQuestionnaire = FirebaseDatabase.getInstance().getReference("questionnaire")

    fun saveData(key: String , value: String) {
        editor.putString(key , value)
        editor.apply()
    }

    fun loadData(key: String): String {
        return spEtudiant.getString(key , "").toString()
    }

    fun isIn(ref: String) : Boolean{
        if (spEtudiant.contains(ref)) return true
        return false
    }

    fun deleteDataIfNotExists() {
        val allEntries: Map<String , *> = spEtudiant.all
        var destroy = true

        refQuestionnaire.get().addOnSuccessListener {
            for ((key , _) in allEntries) {
                for (child in it.children) {
                    if (key == child.key.toString())
                        destroy = false
                }
                if (destroy) {
                    editor.remove(key)
                    editor.apply()
                }
                destroy = true
            }
        }
    }

    fun killSR() {
        val editor = spEtudiant.edit()
        editor.clear()
        editor.apply()
    }

    fun showSR() {
        val allEntries: Map<String , *> = spEtudiant.all
        for ((key , value) in allEntries) {
            Log.d("map values" , key + ": " + value.toString())
        }
    }
}