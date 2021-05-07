package fr.fscript98.zapette.enseignant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

import fr.fscript98.zapette.R

import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.ref_questionnaire
import fr.fscript98.zapette.autre.QuestionModel

import fr.fscript98.zapette.autre.SharedPreference
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.bonnereponse
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModel


import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList


class ResultatQuestionnaireFinal() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_resultat_questionnaire_final)
        } else {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.activity_resultat_questionnaire_final_land)
            }
        }

        val sharedPreference = SharedPreference(this)
        val txtViewTotal = findViewById<TextView>(R.id.nbTotalFin)
        var save = "false"

        val barChart = findViewById<BarChart>(R.id.barChart)
        val table = ArrayList<BarEntry>()


        if (myRandomInt == questionModel.motdepasse) {
            val nbResA = questionModel.A
            val nbResB = questionModel.B
            val nbResC = questionModel.C
            val nbResD = questionModel.D
            val nbResE = questionModel.E
            val nbResF = questionModel.F
            val nbResG = questionModel.G
            val nbResH = questionModel.H
            val nbResI = questionModel.I
            val nbResTotal =
                nbResA + nbResB + nbResC + nbResD + nbResE + nbResF + nbResG + nbResH + nbResI

            val bonneReponse = findViewById<TextView>(R.id.goodAnswer)
            bonneReponse.text =  questionModel.bonneReponse

            table.add(BarEntry(1f , nbResA.toFloat()))
            table.add(BarEntry(2f , nbResB.toFloat()))
            table.add(BarEntry(3f , nbResC.toFloat()))
            table.add(BarEntry(4f , nbResD.toFloat()))
            table.add(BarEntry(5f , nbResE.toFloat()))
            table.add(BarEntry(6f , nbResF.toFloat()))
            table.add(BarEntry(7f , nbResG.toFloat()))
            table.add(BarEntry(8f , nbResH.toFloat()))
            table.add(BarEntry(9f , nbResI.toFloat()))

            var barDataSet = BarDataSet(table , "")

            var barData = BarData(barDataSet)

            barChart.setFitBars(true)
            barChart.data = barData
            barChart.setDrawBarShadow(false)
            barChart.setDrawValueAboveBar(true)
            barChart.description.isEnabled = false

            barChart.setPinchZoom(false)
            barChart.isDoubleTapToZoomEnabled = false
            barChart.setScaleEnabled(false)

            barChart.setDrawGridBackground(false)
            barChart.isClickable = false
            barChart.animateY(1000)

            //X Axis
            val labels = listOf<String>(" " , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" , "I")

            //val xAxisFormatter: ValueFormatter = IndexAxisValueFormatter(labels)
            val xAxis = barChart.xAxis
            xAxis.setLabelCount(table.size , true)
            //xAxis.valueFormatter = xAxisFormatter
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.labelCount = table.size
            xAxis.setCenterAxisLabels(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f
            xAxis.textColor = ContextCompat.getColor(this , R.color.white)
            xAxis.axisLineColor = ContextCompat.getColor(this , R.color.white)
            xAxis.textSize = 12f


            //Y Axis
            val leftAxis = barChart.axisLeft
            leftAxis.setLabelCount(0 , true)
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            leftAxis.setDrawLabels(false)
            leftAxis.axisLineColor = ContextCompat.getColor(this , R.color.white)
            leftAxis.spaceTop = 0f
            leftAxis.axisMinimum = 0f
            leftAxis.textColor = ContextCompat.getColor(this , R.color.white)
            //leftAxis.setDrawAxisLine(false)
            leftAxis.zeroLineColor = ContextCompat.getColor(this , R.color.white)

            val rightAxis = barChart.axisRight
            rightAxis.axisLineColor = ContextCompat.getColor(this , R.color.white)
            rightAxis.setDrawGridLines(false)
            rightAxis.setDrawLabels(false)
            rightAxis.setLabelCount(0 , false)
            rightAxis.spaceTop = 15f
            rightAxis.axisMinimum = 0f

            /*===============================================================*/
            /* Changer coleur des barres */
            /*
            val startColor1 = ContextCompat.getColor(this,R.color.lightblue3)
            val enColor1 = ContextCompat.getColor(this,R.color.lightblue3)

            val gradientColors: MutableList<GradientColor> = ArrayList()
            gradientColors.add(GradientColor(startColor1,enColor1))

            barDataSet.gradientColors = gradientColors
             */
            /*===============================================================*/


            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(barDataSet)

            val data = BarData(dataSets)
            data.setValueTextColor(ContextCompat.getColor(this , R.color.white))
            data.barWidth = 0.75f
            barChart.data = data

            barChart.legend.isEnabled = false

            txtViewTotal.text = ("$nbResTotal")
        }



        val quitter = findViewById<Button>(R.id.btn_quit)
        val intentQuitter = Intent(this , TeacherBoard::class.java)
        quitter.setOnClickListener {
            ref_questionnaire.child(question).removeValue()
            startActivity((intentQuitter))
            finish()
        }

        val sauvegarder = findViewById<Button>(R.id.btn_sauvegarder)
        sauvegarder.setOnClickListener {

            if (questionModelList.contains(questionModel)) {
                save = "false"

            } else {
                save = "true"
                questionModelList.clear()
                questionModelList = sharedPreference.loadDataG()!!
                questionModelList.add(questionModel)

            }
            if (save == "true") {
                sharedPreference.saveDataG()
                sharedPreference.showSR()
            }
            //Toast.makeText(applicationContext,questionModel.titre.toString(), LENGTH_SHORT).show()
        }
    }


}


