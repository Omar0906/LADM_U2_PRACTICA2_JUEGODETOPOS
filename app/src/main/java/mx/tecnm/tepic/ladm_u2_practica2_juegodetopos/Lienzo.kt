package mx.tecnm.tepic.ladm_u2_practica2_juegodetopos

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet

class Lienzo(p:MainActivity): View(p){
    var pantalla:Int = 0
    val botonJugar:Imagen = Imagen(200f,700f,BitmapFactory.decodeResource(this.resources,R.drawable.botonjugar),"Boton para iniciar")
    val fondoPrincipal:Imagen = Imagen(0f,0f,BitmapFactory.decodeResource(this.resources,R.drawable.fondojuego),"El fondo del menu principal")
    val fondoJuego:Imagen = Imagen(0f,0f,BitmapFactory.decodeResource(this.resources,R.drawable.fondojuego2),"El fondo del juego")
    val rectPantalla:Rect = Rect(0,0,720,1310)
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val pincel:Paint = Paint()
        when(pantalla){
            0->{
                c.drawBitmap(fondoPrincipal.img,null,rectPantalla,pincel)
                botonJugar.dibujar(c,pincel)
            }
            1->{
                c.drawBitmap(fondoJuego.img,null,rectPantalla,pincel)
            }
        }

    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_UP->{
                if(botonJugar.estaEnArea(event.x,event.y)) {
                    pantalla = 1
                }
            }
        }
        invalidate()
        return true
    }
}