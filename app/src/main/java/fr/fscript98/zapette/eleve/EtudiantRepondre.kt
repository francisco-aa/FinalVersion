package fr.fscript98.zapette.eleve

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.BddRepository.Singleton.motDePasseBdd
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.eleve.EtudiantRepondre.Singleton.bitMap
import fr.fscript98.zapette.eleve.EtudiantRepondre.Singleton.reponseFournie
import fr.fscript98.zapette.eleve.EtudiantRepondre.Singleton.shouldRun

class EtudiantRepondre : AppCompatActivity() {

    object Singleton {
        lateinit var bitMap: Bitmap //QR code
        var reponseFournie = ""     //Transmis à EtudiantResultat
        var shouldRun = true        //
    }

    var rep_etudiant = ""
    var ref = ""                    //nom de la question format questionX
    lateinit var listener: ValueEventListener
    val database = FirebaseDatabase.getInstance()
    val refQuestionnaire = database.getReference("questionnaire")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_repondre)

        val a = findViewById<Button>(R.id.buttonA)
        val b = findViewById<Button>(R.id.buttonB)
        val c = findViewById<Button>(R.id.buttonC)
        val d = findViewById<Button>(R.id.buttonD)
        val e = findViewById<Button>(R.id.buttonE)
        val f = findViewById<Button>(R.id.buttonF)
        val g = findViewById<Button>(R.id.buttonG)
        val h = findViewById<Button>(R.id.buttonH)
        val i = findViewById<Button>(R.id.buttonI)
        val buttonBack = findViewById<ImageView>(R.id.button_back3)
        val buttonList = arrayListOf<Button>()
        buttonList.add(a)
        buttonList.add(b)
        buttonList.add(c)
        buttonList.add(d)
        buttonList.add(e)
        buttonList.add(f)
        buttonList.add(g)
        buttonList.add(h)
        buttonList.add(i)

        val intent = Intent(applicationContext , EtudiantResultats::class.java)
        val sharedPreference = SharedPreference(this)
        //Nettoyer le shared preferences
        sharedPreference.deleteDataIfNotExists()

        //Parcours BDD
        refQuestionnaire.get().addOnSuccessListener {
            for (question in it.children) {
                if (question.child("motdepasse").value.toString() == motDePasseBdd) {
                    ref = question.key.toString()
                    if (sharedPreference.isIn(ref))
                        rep_etudiant =
                            sharedPreference.loadData(ref) //La derniere réponse locale devient la dernière réponse enregistrée pour la question
                    else
                        sharedPreference.saveData(ref , "") //On a jamais participé à cette question, donc champ vide

                    listener = //Placement d'un listener actif sur la question dans la bdd
                        refQuestionnaire.child(ref).child("questionTerminee").addValueEventListener(
                            object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.value.toString() == "true") {
                                        if (shouldRun) startActivity(intent) //question terminée, démarrage de l'activité suivante
                                        refQuestionnaire.child(ref).child("questionTerminee")
                                            .removeEventListener(this) //Détruit le listener
                                        finish()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }
                            })
                }
            }
        }

        shouldRun = true

        //Incrémente buttonClique (la réponse selectionnée) dans la BDD
        //décrémente derniereRep (la réponse d'avant) dans la bdd
        fun fonction(buttonClique: String) {
            database.getReference("questionnaire").get().addOnSuccessListener {
                for (child in it.children) {
                    val questionModel = child.getValue(QuestionModel::class.java)
                    if (questionModel != null) {
                        if (motDePasseBdd == questionModel.motdepasse.toString()) {
                            if (rep_etudiant != "") {
                                val numb1 = child.child(rep_etudiant).value.toString().toInt()
                                refQuestionnaire.child(child.ref.key.toString())
                                    .child(rep_etudiant).setValue(numb1 - 1)
                            }
                            val numb = child.child(buttonClique).value.toString().toInt()
                            refQuestionnaire.child(child.ref.key.toString())
                                .child(buttonClique).setValue(numb + 1)

                            sharedPreference.saveData(ref , buttonClique)
                            rep_etudiant = sharedPreference.loadData(ref)
                            reponseFournie = buttonClique
                        }
                    }
                }
            }
        }


        a.setOnClickListener {
            if (rep_etudiant != "A") fonction("A")
            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            a.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        b.setOnClickListener {
            if (rep_etudiant != "B") fonction("B")
            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            b.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        c.setOnClickListener {
            if (rep_etudiant != "C") fonction("C")

            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            c.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        d.setOnClickListener {
            if (rep_etudiant != "D") fonction("D")

            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            d.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        e.setOnClickListener {
            if (rep_etudiant != "E") fonction("E")

            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            e.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        f.setOnClickListener {
            if (rep_etudiant != "F") fonction("F")
            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            f.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        g.setOnClickListener {
            if (rep_etudiant != "G") fonction("G")
            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            g.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        h.setOnClickListener {
            if (rep_etudiant != "H") fonction("H")
            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            h.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        i.setOnClickListener {
            //if (rep_etudiant != "I") fonction("I")
            sharedPreference.showSR()
            for (button in buttonList) {
                button.setBackgroundColor(Color.WHITE)
            }
            i.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        buttonBack.setOnClickListener {
            shouldRun = false
            sharedPreference.killSR()
            FirebaseDatabase.getInstance().getReference("questionnaire").child(ref)
                .child("questionTerminee")
                .removeEventListener(listener)
            //derniereRep = ""
            finish()
        }


        val qrCode = QRCodeWriter()
        val qrCodePage = Intent(this , QrCode::class.java)
        val bitMtx = qrCode.encode(
            motDePasseBdd ,
            BarcodeFormat.QR_CODE ,
            2000 ,
            2000
        )

        val imageCode = findViewById<ImageView>(R.id.imageQrCodeEleve)
        val barcodeEncoder = BarcodeEncoder()
        bitMap = barcodeEncoder.createBitmap(bitMtx)
        imageCode.setImageBitmap(bitMap)
        imageCode.setOnClickListener {
            startActivity(qrCodePage)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        shouldRun = false //empeche le déclenchement du listener questionTerminne dans le futur
        FirebaseDatabase.getInstance().getReference("questionnaire").child(ref)
            .child("questionTerminee")
            .removeEventListener(listener)
        finish()
    }
}





