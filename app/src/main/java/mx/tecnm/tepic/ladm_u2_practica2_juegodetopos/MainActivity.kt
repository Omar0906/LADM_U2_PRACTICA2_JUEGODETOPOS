package mx.tecnm.tepic.ladm_u2_practica2_juegodetopos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lienzo:Lienzo = Lienzo(this)
        setContentView(lienzo)
    }
}