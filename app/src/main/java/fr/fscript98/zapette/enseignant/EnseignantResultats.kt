package fr.fscript98.zapette.enseignant

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity
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
import fr.fscript98.zapette.autre.EnseignantFragment
import fr.fscript98.zapette.autre.MyDialog
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.autre.SharedPreference
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.position
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.positionSupp
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList


class EnseignantResultats() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_enseignant_resultats)
            val transaction1 = supportFragmentManager.beginTransaction()
            transaction1.replace(R.id.fragment_container , EnseignantFragment(this))
            transaction1.addToBackStack(null)
            transaction1.commit()
        }
        else{
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                setContentView(R.layout.activity_enseignant_resultats_land)
                val transaction1 = supportFragmentManager.beginTransaction()
                transaction1.replace(R.id.fragment_container , EnseignantFragment(this))
                transaction1.addToBackStack(null)
                transaction1.commit()
            }
        }





        val sharedPreference = SharedPreference(this)
        questionModelList = sharedPreference.loadDataG()!!
        position=-1
        val intentBack = Intent(this , TeacherBoard::class.java)
        val clear = findViewById<Button>(R.id.clear)
       //val clearAll = findViewById<Button>(R.id.clearAll)
        val intentReload = Intent(this , this::class.java)
        /*clearAll.setOnClickListener{
            if (questionModelList.isNotEmpty()) {

                questionModelList.clear()
                sharedPreference.killSR()
                val transaction1 = supportFragmentManager.beginTransaction()
                transaction1.replace(R.id.fragment_container , EnseignantFragment(this))
                transaction1.addToBackStack(null)
                transaction1.commit()

            }
        }*/
        var nbClear=0
        clear.setOnClickListener {
            if (position!=-1) {
                val transaction1 = supportFragmentManager.beginTransaction()
                transaction1.replace(R.id.fragment_container , EnseignantFragment(this))
                transaction1.addToBackStack(null)
                transaction1.commit()
                sharedPreference.killSR()
                val questionSupp= arrayListOf<QuestionModel>()
                for (i in positionSupp){
                    questionSupp.add(questionModelList[i])

                }
                for (question in questionSupp){
                    questionModelList.remove(question)
                }

                sharedPreference.saveDataG()
                questionModelList = sharedPreference.loadDataG()!!

                nbClear=nbClear+1
                position=-1

                if (nbClear==2 || nbClear==6){
                    Toast.makeText(applicationContext,"Pression longue pour tout supprimer", LENGTH_SHORT).show()
                }
            }

        }
        clear.setOnLongClickListener{
            if (questionModelList.isNotEmpty()) {
                questionModelList.clear()
                sharedPreference.killSR()
                val transaction1 = supportFragmentManager.beginTransaction()
                transaction1.replace(R.id.fragment_container , EnseignantFragment(this))
                transaction1.addToBackStack(null)
                transaction1.commit()
            }

            true
        }

        val back = findViewById<ImageView>(R.id.backMesResultats)
        back.setOnClickListener {

            finish()
        }

        val afficher = findViewById<Button>(R.id.afficher)
        afficher.setOnClickListener{
            if (position!=-1) {
                if (questionModelList.isNotEmpty()) {

                    val barChart = findViewById<BarChart>(R.id.barChartResultats)
                    val table = ArrayList<BarEntry>()

                    table.add(BarEntry(1f , questionModelList[position].A.toFloat() ))
                    table.add(BarEntry(3f , questionModelList[position].B.toFloat()))
                    table.add(BarEntry(5f , questionModelList[position].C.toFloat()))
                    table.add(BarEntry(7f , questionModelList[position].D.toFloat()))
                    table.add(BarEntry(9f , questionModelList[position].E.toFloat()))
                    table.add(BarEntry(11f , questionModelList[position].F.toFloat()))
                    table.add(BarEntry(13f , questionModelList[position].G.toFloat()))
                    table.add(BarEntry(15f , questionModelList[position].H.toFloat()))
                    table.add(BarEntry(17f , questionModelList[position].I.toFloat()))

                    var barDataSet = BarDataSet(table ,"")

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
                    var xAxis = barChart.xAxis
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

                }
            }

        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}