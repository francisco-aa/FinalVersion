package fr.fscript98.zapette

import BddRepository.Singleton.id
import BddRepository.Singleton.motDePasseBdd
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder



class EtudiantRepondre : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_repondre)
        // view
        var buttonClique = "aucun"
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
        val buttonValide = findViewById<Button>(R.id.buttonValider)
        buttonList.add(a)
        buttonList.add(b)
        buttonList.add(c)
        buttonList.add(d)
        buttonList.add(e)
        buttonList.add(f)
        buttonList.add(g)
        buttonList.add(h)
        buttonList.add(i)
        // intent
        val database = FirebaseDatabase.getInstance()
        val refQuestionnaire = database.getReference("questionnaire")

        buttonBack.setOnClickListener {
            val intentButtonBack = Intent(this , EtudiantQuestionnaire::class.java)
            startActivity(intentButtonBack)
            finish()
        }

        // changer la couleur de l'écriture pour voir la sélection
        a.setOnClickListener {
            // si le boutton cliqué et déjà A alors on remet le texte a noir et la valeur buttonClique a "aucun"
            if (buttonClique == "A") {
                a.setTextColor(Color.BLACK)
                buttonClique = "aucun"

            } else {
                //si on clique sur A , on change la couler de A et on remet les autres en Noir
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                a.setTextColor(Color.parseColor("#FFBB86FC"))

                buttonClique = "A"
            }
        }

        b.setOnClickListener {
            if (buttonClique == "B") {
                b.setTextColor(Color.BLACK)
                buttonClique = "aucun"
            } else {
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                b.setTextColor(Color.parseColor("#FFBB86FC"))
                buttonClique = "B"
            }
        }
        c.setOnClickListener {
            if (buttonClique == "C") {
                c.setTextColor(Color.BLACK)
                buttonClique = "aucun"
            } else {
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                c.setTextColor(Color.parseColor("#FFBB86FC"))
                buttonClique = "C"
            }
        }

        d.setOnClickListener {
            if (buttonClique == "D") {
                d.setTextColor(Color.BLACK)
                buttonClique = "aucun"
            } else {
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                d.setTextColor(Color.parseColor("#FFBB86FC"))
                buttonClique = "D"
            }
        }
        e.setOnClickListener {
            if (buttonClique == "E") {
                e.setTextColor(Color.BLACK)
                buttonClique = "aucun"
            } else {
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                e.setTextColor(Color.parseColor("#FFBB86FC"))
                buttonClique = "E"
            }
        }

        f.setOnClickListener {
            if (buttonClique == "F") {
                f.setTextColor(Color.BLACK)
                buttonClique = "aucun"
            } else {
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                f.setTextColor(Color.parseColor("#FFBB86FC"))
                buttonClique = "F"
            }
        }
        g.setOnClickListener {
            if (buttonClique == "G") {
                f.setTextColor(Color.BLACK)
                buttonClique = "aucun"
            } else {
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                g.setTextColor(Color.parseColor("#FFBB86FC"))
                buttonClique = "G"
            }
        }

        h.setOnClickListener {
            if (buttonClique == "H") {
                h.setTextColor(Color.BLACK)
                buttonClique = "aucun"
            } else {
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                h.setTextColor(Color.parseColor("#FFBB86FC"))
                buttonClique = "H"
            }
        }
        i.setOnClickListener {
            if (buttonClique == "I") {
                i.setTextColor(Color.BLACK)
                buttonClique = "aucun"
            } else {
                for (button in buttonList) {
                    button.setTextColor(Color.BLACK)
                }
                i.setTextColor(Color.parseColor("#FFBB86FC"))
                buttonClique = "I"
            }
        }

        buttonValide.setOnClickListener {
            if (buttonClique != "aucun") {
                // On va chercher a faire correspondre le motsDePasseBdd enregistrer a la page EtudiantQuestionnaire avec
                    // les mots de passes de la bdd
                database.getReference("questionnaire").get().addOnSuccessListener {
                    // On récup les enfant du chemin questionnaire
                    for (child in it.children) {
                        val questionModel = child.getValue(QuestionModel::class.java)
                        if (questionModel != null) {
                            // On compare avec le mot de passe de la page EtudiantQuestinnaire
                            if (motDePasseBdd == questionModel.motdepasse.toString()) {
                                //Toast.makeText(applicationContext,child.child(buttonClique).value,LENGTH_SHORT).show()
                                    // id correspond a la valeur du motDePasse pour la question a laquelle tu as participé

                                if (id==questionModel.motdepasse.toString()){
                                    Toast.makeText(applicationContext , "Tu as déjà participé" , LENGTH_SHORT).show()
                                }
                                else {
                                    val numb = child.child(buttonClique).value.toString().toInt()
                                    refQuestionnaire.child(child.ref.key.toString())
                                        .child(buttonClique).setValue(numb + 1)

                                    val valider = Intent(this , MainActivity::class.java)
                                    Toast.makeText(applicationContext , "Merci d'avoir voté" , LENGTH_SHORT).show()
                                    startActivity(valider)
                                    finish()
                                    //On met l'id a jour, en lui donnant le motdepasse
                                    //id = questionModel.motdepasse.toString()
                                }
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext ,
                    "Selectionne une réponse" ,
                    LENGTH_SHORT
                ).show()
            }
        }

        val qrCode = QRCodeWriter()
        val intent2= Intent(this,QrCode::class.java)
        val bitMtx = qrCode.encode(
            motDePasseBdd ,
            BarcodeFormat.QR_CODE ,
            100,
            100
        )

        val imageCode = findViewById<ImageView>(R.id.imageQrCodeEleve)
        val barcodeEncoder = BarcodeEncoder()
        val bitMap = barcodeEncoder.createBitmap(bitMtx)
        val back = Intent(this , MainActivity::class.java)
        imageCode.setImageBitmap(bitMap)
        imageCode.setOnClickListener{
            startActivity(intent2)

        }


        //creer une liste qui va stocker les buttons

/*
        val buttonA = findViewById<Button>(R.id.buttonA)
        buttonBack.setOnClickListener{
            repo.updateButton(buttonList[R.id.buttonA])
        }*/
    }
}



