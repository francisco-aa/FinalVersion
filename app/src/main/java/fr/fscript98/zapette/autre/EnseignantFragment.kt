package fr.fscript98.zapette.autre


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.fscript98.zapette.R

import fr.fscript98.zapette.enseignant.EnseignantResultats


import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList

class EnseignantFragment(
    private val context : EnseignantResultats
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