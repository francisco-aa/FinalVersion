package fr.fscript98.zapette

import BddRepository

import BddRepository.Singleton.question
import BddRepository.Singleton.questionListBdd
import BddRepository.Singleton.ref_questionnaire
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import fr.fscript98.zapette.ResultatQuestionnaire.Singleton.questionModel
import fr.fscript98.zapette.TeacherBoard.Singleton.myRandomInt



class ResultatQuestionnaire : AppCompatActivity() {

    object Singleton{
        lateinit var questionModel : QuestionModel
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
            val qrCode = QRCodeWriter()

            val bitMtx = qrCode.encode(
                "$myRandomInt" ,
                BarcodeFormat.QR_CODE ,
                200 ,
                200
            )

            val imageCode = findViewById<ImageView>(R.id.imageQrCode)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMtx)
            imageCode.setImageBitmap(bitmap)



            textView.text = ("$myRandomInt")



            //Récupérer les nombres de votes par reponse

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
                    if (nbE!=0){
                        textViewE.text = ("$nbE")}
                    if(nbF!=0) {
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
            val terminer = findViewById<Button>(R.id.Terminer)
            val intentTerminer = Intent(this,ResultatQuestionnaireFinal::class.java)
            terminer.setOnClickListener{
                ref_questionnaire.child(question).removeValue()
                startActivity(intentTerminer)
                finish()
            }

        }

    }
}


