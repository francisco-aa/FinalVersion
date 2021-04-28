package fr.fscript98.zapette.enseignant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
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
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.BddRepository
import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.ref_questionnaire
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModel
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModelList
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt

class ResultatQuestionnaireFinal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultat_questionnaire_final)

        val txtViewTotal = findViewById<TextView>(R.id.nbTotalFin)

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
            val nbResTotal = nbResA + nbResB + nbResC + nbResD + nbResE + nbResF + nbResG + nbResH + nbResI

            table.add(BarEntry(1f,nbResA.toFloat()))
            table.add(BarEntry(3f,nbResB.toFloat()))
            table.add(BarEntry(5f,nbResC.toFloat()))
            table.add(BarEntry(7f,nbResD.toFloat()))
            table.add(BarEntry(9f,nbResE.toFloat()))
            table.add(BarEntry(11f,nbResF.toFloat()))
            table.add(BarEntry(13f,nbResG.toFloat()))
            table.add(BarEntry(15f,nbResH.toFloat()))
            table.add(BarEntry(17f,nbResI.toFloat()))

            var barDataSet = BarDataSet(table,"")

            var barData = BarData(barDataSet)

            barChart.setFitBars(true)
            barChart.data = barData
            //barChart.animateY(1000)
            barChart.setDrawBarShadow(false)
            barChart.setDrawValueAboveBar(true)
            barChart.description.isEnabled=false

            barChart.setPinchZoom(false)
            barChart.isDoubleTapToZoomEnabled = false
            barChart.setScaleEnabled(false)

            barChart.setDrawGridBackground(false)
            barChart.isClickable=false

            //X Axis
            var labels = mutableListOf<String>()

            val xAxisFormatter: ValueFormatter = IndexAxisValueFormatter(labels)
            val xAxis = barChart.xAxis
            xAxis.setLabelCount(table.size,true)
            xAxis.valueFormatter = xAxisFormatter
            xAxis.labelCount = table.size

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 2f
            xAxis.textColor = ContextCompat.getColor(this,R.color.white)
            xAxis.axisLineColor = ContextCompat.getColor(this,R.color.white)


            //Y Axis
            val leftAxis = barChart.axisLeft
            leftAxis.setLabelCount(0, true)
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            leftAxis.setDrawLabels(false)
            leftAxis.axisLineColor = ContextCompat.getColor(this,R.color.white)
            leftAxis.spaceTop = 0f
            leftAxis.axisMinimum = 0f
            leftAxis.textColor = ContextCompat.getColor(this, R.color.white)
            //leftAxis.setDrawAxisLine(false)
            leftAxis.zeroLineColor = ContextCompat.getColor(this, R.color.white)

            val rightAxis = barChart.axisRight
            rightAxis.axisLineColor = ContextCompat.getColor(this,R.color.white)
            rightAxis.setDrawGridLines(false)
            rightAxis.setDrawLabels(false)
            rightAxis.setLabelCount(0, false)
            rightAxis.spaceTop = 15f
            rightAxis.axisMinimum = 0f


            val startColor1 = ContextCompat.getColor(this,R.color.lightblue3)
            val enColor1 = ContextCompat.getColor(this,R.color.lightblue3)

            val gradientColors: MutableList<GradientColor> = ArrayList()
            gradientColors.add(GradientColor(startColor1,enColor1))

            barDataSet.gradientColors = gradientColors

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(barDataSet)

            val data = BarData(dataSets)
            data.setValueTextColor(ContextCompat.getColor(this,R.color.white))
            data.barWidth = 1.5f
            barChart.data = data

            barChart.legend.isEnabled=false

            txtViewTotal.text = ("$nbResTotal")

        }

        val quitter = findViewById<Button>(R.id.btn_quit)
        val intentQuitter = Intent(this , TeacherBoard::class.java)
        quitter.setOnClickListener {
            ref_questionnaire.child(question).removeValue()
            startActivity(intentQuitter)
            finish()
        }

        val sauvegarder = findViewById<Button>(R.id.btn_sauvegarder)
        sauvegarder.setOnClickListener {
            if (questionModelList.contains(questionModel)) {
                Toast.makeText(applicationContext , "Déjà sauvegardé" , LENGTH_SHORT).show()
            } else {
                questionModelList.add(questionModel)
                Toast.makeText(applicationContext , "Sauvegardé" , LENGTH_SHORT).show()

            }

        }
    }
}