package fr.fscript98.zapette.autre


import android.content.Context
import android.content.SharedPreferences
import android.util.Log

import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList


class SharedPreference(val context: Context) {
    val spEtudiantName = "shared_prefs"
    val sharedEnseignantName = "shared_enseignant"
    val spEtudiant: SharedPreferences =
        context.getSharedPreferences(spEtudiantName , Context.MODE_PRIVATE)
    val sharedEnseignant: SharedPreferences =
        context.getSharedPreferences(sharedEnseignantName , Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = spEtudiant.edit()
    val editorEnseignant: SharedPreferences.Editor = sharedEnseignant.edit()
    val refQuestionnaire = FirebaseDatabase.getInstance().getReference("questionnaire")

    fun saveData(key: String , value: String) {
        editor.putString(key , value)
        editor.apply()
    }

    fun loadData(key: String): String {
        return spEtudiant.getString(key , "").toString()
    }

    fun isIn(ref: String): Boolean {
        if (spEtudiant.contains(ref)) return true
        return false
    }

    fun SpToArray(ref: String): MutableList<String> {
        val str = spEtudiant.getString(ref , "")
        val list: MutableList<String> = str!!.toCharArray().map { it.toString() }.toMutableList()
        return list
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
        editorEnseignant.clear()
        editorEnseignant.apply()
    }

    fun showSR() {
        val allEntries: Map<String , *> = spEtudiant.all
        for ((key , value) in allEntries) {
            Log.d("map values" , key + ": " + value.toString())
        }
    }

    fun saveDataG() {
        val gson: Gson = Gson()
        val json: String = gson.toJson(questionModelList)
        editorEnseignant.putString("enseignant" , json)
        editorEnseignant.apply()
    }

    fun loadDataG(): ArrayList<QuestionModel>? {

        val gson: Gson = Gson()
        val json: String = sharedEnseignant.getString("enseignant" , null).toString()
        val type = object : TypeToken<ArrayList<QuestionModel>>() {}.type
        var questionModelList = gson.fromJson<ArrayList<QuestionModel>>(json , type)
        if (questionModelList == null) {
            questionModelList = ArrayList<QuestionModel>()
        }
        return questionModelList
    }

}