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
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
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
import com.github.mikephil.charting.model.GradientColor
import com.google.gson.Gson
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.BddRepository
import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.ref_questionnaire
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.autre.SharedPreference
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.bonnereponse
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModel


import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList
import java.lang.reflect.Type
import com.google.gson.reflect.TypeToken as TypeToken

class ResultatQuestionnaireFinal() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_resultat_questionnaire_final)
        }
        else{
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                setContentView(R.layout.activity_resultat_questionnaire_final_land)
            }
        }

        val sharedPreference = SharedPreference(this)
        val txtViewTotal = findViewById<TextView>(R.id.nbTotalFin)
        var save = "false"

        var barChart = findViewById<BarChart>(R.id.barChart)
        var table = ArrayList<BarEntry>()



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

            table.add(BarEntry(1f , nbResA.toFloat()))
            table.add(BarEntry(3f , nbResB.toFloat()))
            table.add(BarEntry(5f , nbResC.toFloat()))
            table.add(BarEntry(7f , nbResD.toFloat()))
            table.add(BarEntry(9f , nbResE.toFloat()))
            table.add(BarEntry(11f , nbResF.toFloat()))
            table.add(BarEntry(13f , nbResG.toFloat()))
            table.add(BarEntry(15f , nbResH.toFloat()))
            table.add(BarEntry(17f , nbResI.toFloat()))

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
            var labels = mutableListOf<String>()

            val xAxisFormatter: ValueFormatter = IndexAxisValueFormatter(labels)
            val xAxis = barChart.xAxis
            xAxis.setLabelCount(table.size , true)
            xAxis.valueFormatter = xAxisFormatter
            xAxis.labelCount = table.size

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 2f
            xAxis.textColor = ContextCompat.getColor(this , R.color.white)
            xAxis.axisLineColor = ContextCompat.getColor(this , R.color.white)


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

            /*
            val startColor1 = ContextCompat.getColor(this,R.color.lightblue3)
            val enColor1 = ContextCompat.getColor(this,R.color.lightblue3)

            val gradientColors: MutableList<GradientColor> = ArrayList()
            gradientColors.add(GradientColor(startColor1,enColor1))

            barDataSet.gradientColors = gradientColors
             */

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(barDataSet)

            val data = BarData(dataSets)
            data.setValueTextColor(ContextCompat.getColor(this , R.color.white))
            data.barWidth = 1.5f
            barChart.data = data

            barChart.legend.isEnabled = false

            txtViewTotal.text = ("$nbResTotal")
        }


        val aGood = findViewById<TextView>(R.id.Ares)
        val bGood = findViewById<TextView>(R.id.Bres)
        val cGood = findViewById<TextView>(R.id.Cres)
        val dGood = findViewById<TextView>(R.id.Dres)
        val eGood = findViewById<TextView>(R.id.Eres)
        val fGood = findViewById<TextView>(R.id.Fres)
        val gGood = findViewById<TextView>(R.id.Gres)
        val hGood = findViewById<TextView>(R.id.Hres)
        val iGood = findViewById<TextView>(R.id.Ires)

        if (bonnereponse == "A") {
            aGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
        }
        if (bonnereponse == "B") {
            bGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
        }
        if (bonnereponse == "C") {
            cGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
        }
        if (bonnereponse == "D") {
            dGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
        }
        if (bonnereponse == "E") {
            eGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
        }
        if (bonnereponse == "F") {
            fGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
        }
        if (bonnereponse == "G") {
            gGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
        }
        if (bonnereponse == "H") {
            hGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
        }
        if (bonnereponse == "I") {
            iGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
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

            if (questionModelList.contains(questionModel)){
                save="false"

            }else{
                save="true"
                questionModelList.clear()
                questionModelList= sharedPreference.loadDataG()!!
                questionModelList.add(questionModel)

            }
            if (save=="true") {
                sharedPreference.saveDataG()
                sharedPreference.showSR()
            }
            //Toast.makeText(applicationContext,questionModel.titre.toString(), LENGTH_SHORT).show()
        }
    }




}


