package fr.fscript98.zapette.eleve

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.firebase.database.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import fr.fscript98.zapette.R
import fr.fscript98.zapette.autre.BddRepository.Singleton.motDePasseBdd
import fr.fscript98.zapette.autre.QuestionModel
import fr.fscript98.zapette.autre.SharedPreference
import fr.fscript98.zapette.eleve.EtudiantRepondre.Singleton.bitMap
import fr.fscript98.zapette.eleve.EtudiantRepondre.Singleton.reponseFournie
import fr.fscript98.zapette.eleve.EtudiantRepondre.Singleton.shouldRun

class EtudiantRepondre : AppCompatActivity() {

    object Singleton {
        lateinit var bitMap: Bitmap //QR code
        var reponseFournie = ""     //Transmis à EtudiantResultat
        var shouldRun = true        //
    }

    var rep_etudiant = ""           //reponse simple
    //var tab_rep_etudiant = mutableMapOf("A" to 0, "B" to 0, "C" to 0, "D" to 0, "E" to 0, "F" to 0, "G" to 0, "H" to 0, "I" to 0)
    var tab_rep_etudiant = mutableListOf<String>()
    var ref = ""                    //reference String de la question dans la bdd
    lateinit var listenerQuestionTerminee: ValueEventListener
    lateinit var listenerNombreReponses: ValueEventListener
    val database = FirebaseDatabase.getInstance()
    val refQuestionnaire = database.getReference("questionnaire")
    lateinit var refQuestion: DatabaseReference
    var nombreReponses = ""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_etudiant_repondre)
        }
        else{
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                setContentView(R.layout.activity_etudiant_repondre_land)
            }
        }

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


        //Incrémente buttonClique (la réponse selectionnée) dans la BDD
        //décrémente derniereRep (la réponse d'avant) dans la bdd
        fun fonction(buttonClique: String) {
            database.getReference("questionnaire").get().addOnSuccessListener {
                for (child in it.children) {
                    val questionModel = child.getValue(QuestionModel::class.java)
                    if (questionModel != null) {
                        if (motDePasseBdd == questionModel.motdepasse.toString()) {
                            if (rep_etudiant != "") { //L'étudiant a déjà répondu, donc il faut décrémenter sa dernière réponse
                                val numb1 = child.child(rep_etudiant).value.toString().toInt()
                                refQuestionnaire.child(child.ref.key.toString())
                                    .child(rep_etudiant).setValue(numb1 - 1)
                            }
                            val numb = child.child(buttonClique).value.toString().toInt()
                            refQuestionnaire.child(child.ref.key.toString())
                                .child(buttonClique).setValue(numb + 1)

                            sharedPreference.saveData(ref , buttonClique) //Sauvegarde de la dernière réponse dans le SR
                            rep_etudiant = sharedPreference.loadData(ref) //Sauvegarde en local
                            reponseFournie = buttonClique //Utile pour l'activité suivante
                        }
                    }
                }
            }
        }

        //Convertir un tableau de réponses en une chaine (format "ABCD")
        fun ArrayToString(array: MutableList<String>) : String{
            var str = ""
            for (char in array){
                str += char
            }
            return str
        }

        //verifie si le boutton a été pressé par l'étudiant
        fun ButtonIsClicked(buttonClique: String) : Boolean{
            for (button in tab_rep_etudiant){
                if (button == buttonClique) return true
            }
            return false
        }

        fun vote(buttonClique: String){
            refQuestion.get().addOnSuccessListener{
                for (child in it.children){
                    if (child.key.toString() == buttonClique){
                        val value = child.value.toString().toInt()
                        refQuestionnaire.child(ref).child(buttonClique).setValue(value+1)

                        tab_rep_etudiant.add(buttonClique)
                        val stringReponses = ArrayToString(tab_rep_etudiant)
                        sharedPreference.saveData(refQuestion.key.toString(), stringReponses)
                        sharedPreference.showSR()
                    }
                }
            }
        }

        fun devote(buttonClique: String){
            refQuestion.get().addOnSuccessListener{
                for (child in it.children){
                    if (child.key.toString() == buttonClique){
                        val value = child.value.toString().toInt()
                        refQuestionnaire.child(ref).child(buttonClique).setValue(value-1)

                        tab_rep_etudiant.remove(buttonClique)
                        val stringReponses = ArrayToString(tab_rep_etudiant)
                        sharedPreference.saveData(refQuestion.key.toString(), stringReponses)
                        sharedPreference.showSR()
                    }
                }
            }
        }

        fun setVisibility(nombreReponses: Int){
            for (i in 0..nombreReponses-1){
                buttonList[i].visibility = View.VISIBLE
                for (i in nombreReponses..8) {
                    buttonList[i].visibility = View.GONE
                }
            }
        }

        //Parcours BDD
        refQuestionnaire.get().addOnSuccessListener {
            for (question in it.children) {
                if (question.child("motdepasse").value.toString() == motDePasseBdd) {
                    ref = question.key.toString() //reference String de la question
                    refQuestion = refQuestionnaire.child(ref) //DatabaseReference de la question
                    nombreReponses = question.child("nbReponses").value.toString()

                    setVisibility(nombreReponses.toInt())

                    if (sharedPreference.isIn(ref)) //La question affichée est-elle présente dans le SP de l'étudiant?
                        rep_etudiant = sharedPreference.loadData(ref) //La derniere réponse locale devient la dernière réponse enregistrée dans le SR pour la question
                    else
                        sharedPreference.saveData(ref , "") //On a jamais participé à cette question, donc champ vide

                    listenerQuestionTerminee = //Placement d'un listener actif sur questionTerminee dans la bdd (pour savoir quand changer d'activité)
                        refQuestionnaire.child(ref).child("questionTerminee").addValueEventListener(
                            object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.value.toString() == "true") {
                                        if (shouldRun){
                                            startActivity(intent) //question terminée, démarrage de l'activité suivante
                                        }
                                        refQuestionnaire.child(ref).child("questionTerminee")
                                            .removeEventListener(this) //Détruit le listener
                                        finish()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {}
                            }
                        )

                    listenerNombreReponses =
                        refQuestionnaire.child(ref).child("nbReponses").addValueEventListener(
                            object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    setVisibility(snapshot.value.toString().toInt())
                                }
                                override fun onCancelled(error: DatabaseError) {}
                            }
                        )
                }
            }
        }

        shouldRun = true

        //Gestion des boutons A -> I
        a.setOnClickListener {
            if (ButtonIsClicked("A")){
                devote("A")
                a.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("A")
                a.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        b.setOnClickListener {
            if (ButtonIsClicked("B")){
                devote("B")
                b.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("B")
                b.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        c.setOnClickListener {
            if (ButtonIsClicked("C")){
                devote("C")
                c.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("C")
                c.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        d.setOnClickListener {
            if (ButtonIsClicked("D")){
                devote("D")
                d.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("D")
                d.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        e.setOnClickListener {
            if (ButtonIsClicked("E")){
                devote("E")
                e.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("E")
                e.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        f.setOnClickListener {
            if (ButtonIsClicked("F")){
                devote("F")
                f.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("F")
                f.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        g.setOnClickListener {
            if (ButtonIsClicked("G")){
                devote("G")
                g.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("G")
                g.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        h.setOnClickListener {
            if (ButtonIsClicked("H")){
                devote("H")
                h.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("H")
                h.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        i.setOnClickListener {
            if (ButtonIsClicked("I")){
                devote("I")
                i.setBackgroundColor(Color.WHITE)
            }
            else{
                vote("I")
                i.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
        }

        buttonBack.setOnClickListener {
            shouldRun = false //Evite tout démarrage non voulu de l'activité EtudiantResultats
            FirebaseDatabase.getInstance().getReference("questionnaire").child(ref) //destruction du listener sur questionTerminee
                .child("questionTerminee")
                .removeEventListener(listenerQuestionTerminee)
            FirebaseDatabase.getInstance().getReference("questionnaire").child(ref) //destruction du listener sur questionTerminee
                .child("nbReponses")
                .removeEventListener(listenerNombreReponses)
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
        shouldRun = false //Evite tout démarrage non voulu de l'activité EtudiantResultats
        FirebaseDatabase.getInstance().getReference("questionnaire").child(ref) //destruction du listener sur questionTerminee
            .child("questionTerminee")
            .removeEventListener(listenerQuestionTerminee)
        finish()
    }
}
