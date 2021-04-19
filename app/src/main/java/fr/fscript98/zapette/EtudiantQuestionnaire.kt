package fr.fscript98.zapette

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class EtudiantQuestionnaire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etudiant_questionnaire)

        val buttonBack = findViewById<ImageView>(R.id.button_back4)
        buttonBack.setOnClickListener{
            val intentButtonBack = Intent(this, MainActivity::class.java)
            startActivity(intentButtonBack)

        }

        val buttonRejoindre = findViewById<Button>(R.id.button_rejoindre)
        buttonRejoindre.setOnClickListener{
            val intent = Intent(this, EtudiantRepondre::class.java)
            startActivity(intent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(context , permissions)) {
                Toast.makeText(context , "permission are already provided" , Toast.LENGTH_SHORT)
                    .show()
            } else {
                requestPermissions(permissions , PERMISSION_REQUEST)

            }
        } else {
            Toast.makeText(context , "permission are already provided" , Toast.LENGTH_SHORT).show()
        }
    }

    fun checkPermission(context: Context, permissionArray Array<string>): Boolean {

        var allSuccess = true

        for (i in permissionArray.indices){

            if(checkCallingOrSelfPermission(permissionArray[i])-PackageManager PERMISSION_DENTED) {

                allSuccess = false
            }
        }
        return allSuccess
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for(i in permissions.indices){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED{
                        allSuccess = false
                        var requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])}
                    if (requestAgain) {
                        Toast.makeText(context , "permission denied" , Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        Toast.makeText(context,"go to settings and enable the permission",Toast.LENGTH_SHORT).show()
                    }
            }
            if (allSuccess)
                Toast.makeText(context,"permissions grante",Toast.LENGTH_SHORT).show()
        }
    }
}

    }
}