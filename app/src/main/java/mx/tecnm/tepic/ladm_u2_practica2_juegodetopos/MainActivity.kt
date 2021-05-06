package mx.tecnm.tepic.ladm_u2_practica2_juegodetopos

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.sync.Semaphore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val spritesTopos  = arrayListOf(BitmapFactory.decodeResource(this.resources,R.drawable.topooculto),
                BitmapFactory.decodeResource(this.resources,R.drawable.topo1),
                BitmapFactory.decodeResource(this.resources,R.drawable.topo2),
                BitmapFactory.decodeResource(this.resources,R.drawable.topo3),
                BitmapFactory.decodeResource(this.resources,R.drawable.topo4),
                BitmapFactory.decodeResource(this.resources,R.drawable.topo1_golpeado),
                BitmapFactory.decodeResource(this.resources,R.drawable.topo2_golpeado),
                BitmapFactory.decodeResource(this.resources,R.drawable.topo3_golpeado),
                BitmapFactory.decodeResource(this.resources,R.drawable.topo4_golpeado))
        val Topos = arrayListOf(Topo(10f, 700f, spritesTopos),
                Topo(350f, 480f, spritesTopos),
                Topo(350f, 1000f, spritesTopos),
                Topo(10f, 950f, spritesTopos),
                Topo(360f, 750f, spritesTopos),
                Topo(10f, 460f, spritesTopos))
        val juego = hitTheTopo(Lienzo(this,Topos),spritesTopos,Topos)
        setContentView(juego.lienzo)
    }
}
/*
*
                Topo(360f,750f,spritesTopos),
                Topo(350f,1000f,spritesTopos)
* */