package fr.fscript98.zapette.autre

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fr.fscript98.zapette.R
import fr.fscript98.zapette.enseignant.EnseignantResultats
import fr.fscript98.zapette.enseignant.TeacherBoard
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList


class QuestionAdapter(private val context: EnseignantResultats ,
                      private val questionList: ArrayList<QuestionModel> ,
                      private val layoutId: Int ) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {


    class ViewHolder(view: View ) :RecyclerView.ViewHolder(view){

        init{
            view.setOnClickListener{
                TeacherBoard.Singleton.position =adapterPosition
                if (!TeacherBoard.Singleton.positionSupp.contains(adapterPosition)) {
                    TeacherBoard.Singleton.positionSupp.add(adapterPosition)
                        view.findViewById<ConstraintLayout>(R.id.constraintResultat)
                            .setBackgroundColor(Color.parseColor("#375D81"))
                }else {

                    TeacherBoard.Singleton.positionSupp.remove(adapterPosition)
                    view.findViewById<ConstraintLayout>(R.id.constraintResultat).setBackgroundColor(Color.parseColor("#1D3D67"))
                }
            }
        }

        val questionnaire = view.findViewById<TextView>(R.id.questionnaire)
        var titre = view.findViewById<TextView>(R.id.titre)
        var nombrePartcipation = view.findViewById<TextView>(R.id.nombreParticipation)
        var nombreReponses = view.findViewById<TextView>(R.id.nombreResultats)
        var lettreReponse = view.findViewById<TextView>(R.id.lettreResultats)

    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(layoutId, parent ,false)

        return ViewHolder(view )
    }

    override fun onBindViewHolder(holder: ViewHolder , position: Int) {
        val currentQuestion=questionList[position]
        holder.titre.text=currentQuestion.titre

        val tabReponsesFinal = currentQuestion.bonneReponse.toCharArray().map { it.toString() }.toMutableList()
        var bonnesReponsesFinal = ""

        if (currentQuestion.bonneReponse=="A")
            holder.nombreReponses.text= currentQuestion.A.toString()

        if (currentQuestion.bonneReponse=="B")
            holder.nombreReponses.text= currentQuestion.B.toString()
        if (currentQuestion.bonneReponse=="C")
            holder.nombreReponses.text= currentQuestion.C.toString()
        if (currentQuestion.bonneReponse=="D")
            holder.nombreReponses.text= currentQuestion.D.toString()
        if (currentQuestion.bonneReponse=="E")
            holder.nombreReponses.text= currentQuestion.E.toString()
        if (currentQuestion.bonneReponse=="F")
            holder.nombreReponses.text= currentQuestion.F.toString()
        if (currentQuestion.bonneReponse=="G")
            holder.nombreReponses.text= currentQuestion.G.toString()
        if (currentQuestion.bonneReponse=="H")
            holder.nombreReponses.text= currentQuestion.H.toString()
        if (currentQuestion.bonneReponse=="I")
            holder.nombreReponses.text= currentQuestion.I.toString()

        if (tabReponsesFinal.size > 1){
            for (char in tabReponsesFinal) {
                bonnesReponsesFinal += "$char  "
            }
            holder.lettreReponse.text = bonnesReponsesFinal
        }
        else{
            holder.lettreReponse.text=currentQuestion.bonneReponse
        }

        holder.nombrePartcipation.text=(currentQuestion.A+currentQuestion.B+currentQuestion.C+currentQuestion.D+currentQuestion.E+currentQuestion.F+currentQuestion.G+currentQuestion.H+currentQuestion.I).toString()

    }
    override fun getItemCount(): Int = questionModelList.size;

}