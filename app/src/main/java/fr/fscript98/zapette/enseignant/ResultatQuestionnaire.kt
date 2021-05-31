package fr.fscript98.zapette.enseignant

import fr.fscript98.zapette.autre.BddRepository

import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.questionListBdd
import fr.fscript98.zapette.autre.BddRepository.Singleton.ref_questionnaire
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.ConnectionPerdue
import fr.fscript98.zapette.autre.InternetConnection
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModel
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.bonnereponse

class ResultatQuestionnaire : AppCompatActivity() {

    object Singleton {
        lateinit var questionModel: QuestionModel
        var bonnereponse = mutableListOf<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_resultat_questionnaire)
        } else {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.activity_resultat_questionnaire_land)
            }
        }

        //Verification de la connection
        val intentConnectionPerdue = Intent(this , ConnectionPerdue::class.java)
        val internetConnection = InternetConnection(this)
        internetConnection.observe(this , androidx.lifecycle.Observer { isConnected ->
            if (!isConnected) {
                startActivity(intentConnectionPerdue)
            }
        })

        val aGood = findViewById<Button>(R.id.A)
        val bGood = findViewById<Button>(R.id.B)
        val cGood = findViewById<Button>(R.id.C)
        val dGood = findViewById<Button>(R.id.D)
        val eGood = findViewById<Button>(R.id.E)
        val fGood = findViewById<Button>(R.id.F)
        val gGood = findViewById<Button>(R.id.G)
        val hGood = findViewById<Button>(R.id.H)
        val iGood = findViewById<Button>(R.id.I)

        var mapButton = mapOf(
            (0 to (aGood to "A")) ,
            (1 to (bGood to "B")) ,
            (2 to (cGood to "C")) ,
            (3 to (dGood to "D")) ,
            (4 to (eGood to "E")) ,
            (5 to (fGood to "F")) ,
            (6 to (gGood to "G")) ,
            (7 to (hGood to "H")) ,
            (8 to (iGood to "I"))
        )

        val textViewTotal = findViewById<TextView>(R.id.nbTotal)
        val textView = findViewById<TextView>(R.id.textView2)

        fun ArrayToString(array: MutableList<String>): String {
            var str = ""
            for (char in array) {
                str += char
            }
            return str
        }

        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>? , view: View? , position: Int , id: Long) {
                val selectedValue = spinner.getItemAtPosition(position).toString()
                val iterator = selectedValue.toInt()
                ref_questionnaire.child(question).child("nbReponses")
                    .setValue(selectedValue)
                for (i in 0 until iterator) {
                    mapButton[i]!!.first.visibility = View.VISIBLE
                }
                for (j in iterator..8) {
                    mapButton[j]!!.first.visibility = View.GONE
                    mapButton[j]!!.first.setBackgroundColor(
                        ContextCompat.getColor(applicationContext , R.color.light_blue_A400)
                    )
                    bonnereponse.remove(mapButton[j]!!.second)
                }

                ref_questionnaire.child(question).child("bonneReponse")
                    .setValue(ArrayToString(bonnereponse))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        val repo1 = BddRepository()
        repo1.updateData {
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
                    table.add(BarEntry(2f , nbB.toFloat()))
                    table.add(BarEntry(3f , nbC.toFloat()))
                    table.add(BarEntry(4f , nbD.toFloat()))
                    table.add(BarEntry(5f , nbE.toFloat()))
                    table.add(BarEntry(6f , nbF.toFloat()))
                    table.add(BarEntry(7f , nbG.toFloat()))
                    table.add(BarEntry(8f , nbH.toFloat()))
                    table.add(BarEntry(9f , nbI.toFloat()))

                    //X Axis
                    val labels =
                        listOf<String>(" " , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" , "I")

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

                    val dataSets = ArrayList<IBarDataSet>()
                    dataSets.add(barDataSet)

                    val data = BarData(dataSets)
                    data.setValueTextColor(ContextCompat.getColor(this , R.color.white))
                    data.barWidth = 0.75f
                    barChart.data = data

                    barChart.legend.isEnabled = false

                    textViewTotal.text = ("$nbTotal")
                    questionModel = codeBDD
                }

                if ("A" in bonnereponse) {
                    aGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }
                if ("B" in bonnereponse) {
                    bGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }
                if ("C" in bonnereponse) {
                    cGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }
                if ("D" in bonnereponse) {
                    dGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }
                if ("E" in bonnereponse) {
                    eGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }
                if ("F" in bonnereponse) {
                    fGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }
                if ("G" in bonnereponse) {
                    gGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }
                if ("H" in bonnereponse) {
                    hGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }
                if ("I" in bonnereponse) {
                    iGood.setBackgroundColor(ContextCompat.getColor(this , R.color.teal_200))
                }


                fun ButtonIsClicked(buttonClique: String): Boolean {
                    for (button in bonnereponse) {
                        if (button == buttonClique)
                            return true
                    }
                    return false
                }

                aGood.setOnClickListener {
                    if (ButtonIsClicked("A")) {
                        aGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("A")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        aGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("A")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
                }

                bGood.setOnClickListener {
                    if (ButtonIsClicked("B")) {
                        bGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("B")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        bGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("B")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
                }

                cGood.setOnClickListener {
                    if (ButtonIsClicked("C")) {
                        cGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("C")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        cGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("C")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
                }

                dGood.setOnClickListener {
                    if (ButtonIsClicked("D")) {
                        dGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("D")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        dGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("D")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
                }

                eGood.setOnClickListener {
                    if (ButtonIsClicked("E")) {
                        eGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("E")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        eGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("E")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
                }

                fGood.setOnClickListener {
                    if (ButtonIsClicked("F")) {
                        fGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("F")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        fGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("F")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
                }

                gGood.setOnClickListener {
                    if (ButtonIsClicked("G")) {
                        gGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("G")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        gGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("G")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
                }

                hGood.setOnClickListener {
                    if (ButtonIsClicked("H")) {
                        hGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("H")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        hGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("H")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
                }

                iGood.setOnClickListener {
                    if (ButtonIsClicked("I")) {
                        iGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.light_blue_A400
                            )
                        )
                        bonnereponse.remove("I")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    } else {
                        iGood.setBackgroundColor(
                            ContextCompat.getColor(
                                this ,
                                R.color.teal_200
                            )
                        )
                        bonnereponse.add("I")
                        ref_questionnaire.child(question).child("bonneReponse")
                            .setValue(ArrayToString(bonnereponse))
                    }
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

            val terminer = findViewById<Button>(R.id.Terminer)
            val intentTerminer = Intent(this , ResultatQuestionnaireFinal::class.java)
            terminer.setOnClickListener {
                ref_questionnaire.child(question).child("questionTerminee").setValue("true")
                startActivity(intentTerminer)
                finish()
            }
        }
        bonnereponse.clear()
    }
}




