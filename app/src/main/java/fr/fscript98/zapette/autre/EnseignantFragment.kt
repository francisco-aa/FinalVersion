package fr.fscript98.zapette.autre

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.fscript98.zapette.R
import fr.fscript98.zapette.enseignant.EnseignantMesResultats
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire

import fr.fscript98.zapette.enseignant.TeacherBoard
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList

class EnseignantFragment(
    private val context : EnseignantMesResultats
): Fragment() {
    override fun onCreateView(inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle?): View? {
        val view= inflater?.inflate(R.layout.enseignantfragment, container, false)


        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        verticalRecyclerView.adapter= QuestionAdapter(context,
            questionModelList ,
            R.layout.item_resultats)
        return view
    }
}