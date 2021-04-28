package fr.fscript98.zapette.enseignant

import fr.fscript98.zapette.autre.BddRepository

import fr.fscript98.zapette.autre.BddRepository.Singleton.question
import fr.fscript98.zapette.autre.BddRepository.Singleton.questionListBdd
import fr.fscript98.zapette.autre.BddRepository.Singleton.ref_questionnaire
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import fr.fscript98.zapette.R
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModel
import fr.fscript98.zapette.enseignant.TeacherBoard.Singleton.myRandomInt
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.enseignant.ResultatQuestionnaire.Singleton.questionModelList


class ResultatQuestionnaire : AppCompatActivity() {

    object Singleton {
        lateinit var questionModel: QuestionModel
        var questionModelList = arrayListOf<QuestionModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val repo1 = BddRepository()
        repo1.updateData {
            setContentView(R.layout.activity_resultat_questionnaire)
            val textViewA = findViewById<TextView>(R.id.nbreponseA)
            val textViewB = findViewById<TextView>(R.id.nbreponseB)
            val textViewC = findViewById<TextView>(R.id.nbreponseC)
            val textViewD = findViewById<TextView>(R.id.nbreponseD)
            val textViewE = findViewById<TextView>(R.id.nbreponseE)
            val textViewF = findViewById<TextView>(R.id.nbreponseF)
            val textViewG = findViewById<TextView>(R.id.nbreponseG)
            val textViewH = findViewById<TextView>(R.id.nbreponseH)
            val textViewI = findViewById<TextView>(R.id.nbreponseI)
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
                    if (nbA != 0) {
                        textViewA.text = ("$nbA")
                    }
                    if (nbB != 0) {
                        textViewB.text = ("$nbB")
                    }
                    if (nbC != 0) {
                        textViewC.text = ("$nbC")
                    }
                    if (nbD != 0) {
                        textViewD.text = ("$nbD")
                    }
                    if (nbE != 0) {
                        textViewE.text = ("$nbE")
                    }
                    if (nbF != 0) {
                        textViewF.text = ("$nbF")
                    }


                    if (nbE != 0) {
                        textViewE.text = ("$nbE")
                    }

                    if (nbF != 0) {
                        textViewF.text = ("$nbF")
                    }
                    if (nbG != 0) {
                        textViewG.text = ("$nbG")
                    }
                    if (nbH != 0) {
                        textViewH.text = ("$nbH")
                    }
                    if (nbI != 0) {
                        textViewI.text = ("$nbI")
                    }
                    textViewTotal.text = ("$nbTotal")
                    questionModel = codeBDD

                }
            }
            val qrCode = QRCodeWriter()
            val intent= Intent(this, QrCodeEnseignant::class.java)
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
            imageCode.setOnClickListener{
                startActivity(intent)
            }
            textView.text = ("$myRandomInt")
            //Récupérer les nombres de votes par reponse

            val terminer = findViewById<Button>(R.id.Terminer)
            val intentTerminer = Intent(this , ResultatQuestionnaireFinal::class.java)
            terminer.setOnClickListener {
                ref_questionnaire.child(question).child("questionTerminee").setValue(true)
                startActivity(intentTerminer)
                finish()
            }
        }


    }
}



