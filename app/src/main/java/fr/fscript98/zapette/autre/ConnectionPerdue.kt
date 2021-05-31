package fr.fscript98.zapette.autre

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.fscript98.zapette.R

class ConnectionPerdue : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_connection_perdue)
        } else {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.activity_connection_perdue_land)
            }
        }

        //Verification de la connection
        val intentConnectionPerdue = Intent(this, ConnectionPerdue::class.java)
        val internetConnection = InternetConnection(this)
        internetConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if (isConnected){
                finish()
            }
        })
    }

    override fun onBackPressed() {

    }
}