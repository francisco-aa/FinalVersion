package fr.fscript98.zapette

import BddRepository
import BddRepository.Singleton.questionListBdd
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import fr.fscript98.zapette.ResultatQuestionnaire.Singleton.imageCode
import fr.fscript98.zapette.TeacherBoard.Singleton.myRandomInt



class ResultatQuestionnaire : AppCompatActivity() {
    object Singleton{
         lateinit var imageCode: ImageView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo1= BddRepository()
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


            val buttonBack = findViewById<ImageView>(R.id.button_backQuestionnaireEnseignant)
            buttonBack.setOnClickListener {
                val intentButtonBack = Intent(this , TeacherBoard::class.java)
                startActivity(intentButtonBack)
            }

            val textView = findViewById<TextView>(R.id.textView2)
            val qrCode = QRCodeWriter()

            val bitMtx = qrCode.encode(
                "$myRandomInt" ,
                BarcodeFormat.QR_CODE ,
                200 ,
                200
            )
            imageCode = findViewById<ImageView>(R.id.imageQrCode)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMtx)
            imageCode.setImageBitmap(bitmap)



            textView.text = ("$myRandomInt")



            //Récupérer les nombres de votes par reponse

            for (codeBDD in questionListBdd) {

                if (myRandomInt == codeBDD.motdepasse) {


                    var nbA = codeBDD.A
                    var nbB = codeBDD.B
                    var nbC = codeBDD.C
                    var nbD = codeBDD.D
                    var nbE = codeBDD.E
                    var nbF = codeBDD.F
                    var nbG = codeBDD.G
                    var nbH = codeBDD.H
                    var nbI = codeBDD.I
                    var nbTotal = nbA + nbB + nbC + nbD + nbE + nbF + nbG + nbH + nbI
                    if (nbA!=0) {
                        textViewA.text = ("$nbA")
                    }
                    if (nbB!=0) {
                        textViewB.text = ("$nbB")
                    }
                    if (nbC!=0) {
                        textViewC.text = ("$nbC")
                    }
                    if(nbD!=0){
                        textViewD.text = ("$nbD")
                    }
                    if (nbE!=0){textViewE.text = ("$nbE")}
                    if(nbF!=0) {
                        textViewF.text = ("$nbF")
                    }
                    if (nbG!=0){textViewG.text = ("$nbG")}
                    if (nbH!=0){
                        textViewH.text = ("$nbH")
                    }
                    if (nbI!=0) {
                        textViewI.text = ("$nbI")
                    }
                    textViewTotal.text = ("$nbTotal")
                }
            }

        }
    }
}


