package fr.fscript98.zapette.enseignant

import fr.fscript98.zapette.autre.BddRepository

import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.questionListBdd
import fr.fscript98.zapette.autre.BddRepository.Singleton.ref_questionnaire
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import fr.fscript98.zapette.R
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModel
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.bonnereponse
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.questionModelList


class ResultatQuestionnaire : AppCompatActivity() {

    object Singleton {
        lateinit var questionModel: QuestionModel

        var bonnereponse=""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo1 = BddRepository()
        repo1.updateData {
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                setContentView(R.layout.activity_resultat_questionnaire)
            }
            else{
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                    setContentView(R.layout.activity_resultat_questionnaire_land)
                }
            }

            val textViewTotal = findViewById<TextView>(R.id.nbTotal)
            val textView = findViewById<TextView>(R.id.textView2)

            for (codeBDD in questionListBdd) {
                if (myRandomInt == codeBDD.motdepasse) {
                    val nbA = codeBDD.A
                    val nbB = codeBDD.B
                    val nbC = codeBDD.C
                    val nbD = codeBDD.D
                    val nbE = codeBDD.E
                    val nbF = codeBDD.F
                    val nbG = codeBDD.G
                    val nbH = codeBDD.H
                    val nbI = codeBDD.I
                    val nbTotal = nbA + nbB + nbC + nbD + nbE + nbF + nbG + nbH + nbI

                    val barChart = findViewById<BarChart>(R.id.barChart)
                    val table = ArrayList<BarEntry>()

                    table.add(BarEntry(1f , nbA.toFloat()))
                    table.add(BarEntry(3f , nbB.toFloat()))
                    table.add(BarEntry(5f , nbC.toFloat()))
                    table.add(BarEntry(7f , nbD.toFloat()))
                    table.add(BarEntry(9f , nbE.toFloat()))
                    table.add(BarEntry(11f , nbF.toFloat()))
                    table.add(BarEntry(13f , nbG.toFloat()))
                    table.add(BarEntry(15f , nbH.toFloat()))
                    table.add(BarEntry(17f , nbI.toFloat()))

                    val barDataSet = BarDataSet(table , "")

                    val barData = BarData(barDataSet)

                    barChart.setFitBars(true)
                    barChart.data = barData
                    //barChart.animateY(1000)
                    barChart.setDrawBarShadow(false)
                    barChart.setDrawValueAboveBar(true)
                    barChart.description.isEnabled = false

                    barChart.setPinchZoom(false)
                    barChart.isDoubleTapToZoomEnabled = false
                    barChart.setScaleEnabled(false)


                    barChart.setDrawGridBackground(false)
                    barChart.isClickable = false

                    //X Axis
                    val labels = mutableListOf<String>()

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

                    textViewTotal.text = ("$nbTotal")
                    questionModel = codeBDD

                }

                val aGood = findViewById<TextView>(R.id.A)
                val bGood = findViewById<TextView>(R.id.B)
                val cGood = findViewById<TextView>(R.id.C)
                val dGood = findViewById<TextView>(R.id.D)
                val eGood = findViewById<TextView>(R.id.E)
                val fGood = findViewById<TextView>(R.id.F)
                val gGood = findViewById<TextView>(R.id.G)
                val hGood = findViewById<TextView>(R.id.H)
                val iGood = findViewById<TextView>(R.id.I)

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


                val answersList = arrayListOf<TextView>()
                answersList.add(aGood)
                answersList.add(bGood)
                answersList.add(cGood)
                answersList.add(dGood)
                answersList.add(eGood)
                answersList.add(fGood)
                answersList.add(gGood)
                answersList.add(hGood)
                answersList.add(iGood)

                aGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    aGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    bonnereponse = "A"
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)
                }

                bGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    bGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    bonnereponse = "B"
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)
                }

                cGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    cGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    bonnereponse = "C"
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)
                }

                dGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    dGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    bonnereponse = "D"
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)
                }

                eGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    bonnereponse = "E"
                    eGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)

                }

                fGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    bonnereponse = "F"
                    fGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)
                }

                gGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    bonnereponse = "G"
                    gGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)
                }

                hGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    bonnereponse = "H"
                    hGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)

                }

                iGood.setOnClickListener {
                    for (answer in answersList) {
                        answer.setTextColor(Color.WHITE)
                    }
                    bonnereponse = "I"
                    iGood.setTextColor(ContextCompat.getColor(this , R.color.teal_200))
                    ref_questionnaire.child(question).child("bonneReponse").setValue(bonnereponse)

                }
            }
            val qrCode = QRCodeWriter()
            val intent = Intent(this , QrCodeEnseignant::class.java)
            val bitMtx = qrCode.encode(
                "$myRandomInt" ,
                BarcodeFormat.QR_CODE ,
                100 ,
                100
            )

            val imageCode = findViewById<ImageView>(R.id.imageQrCode)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMtx)
            imageCode.setImageBitmap(bitmap)
            imageCode.setOnClickListener {
                startActivity(intent)
            }
            textView.text = ("$myRandomInt")
            //Récupérer les nombres de votes par reponse

            val terminer = findViewById<Button>(R.id.Terminer)
            val intentTerminer = Intent(this , ResultatQuestionnaireFinal::class.java)
            terminer.setOnClickListener {
                ref_questionnaire.child(question).child("questionTerminee").setValue("true")
                bonnereponse=""

                startActivity(intentTerminer)
                finish()
            }
        }


    }
}



