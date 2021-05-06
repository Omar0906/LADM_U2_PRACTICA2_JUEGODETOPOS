package mx.tecnm.tepic.ladm_u2_practica2_juegodetopos

import android.graphics.Bitmap
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlin.random.Random

class Topo : Imagen {
    val imagenes: ArrayList<Bitmap>
    var modo: Int = 0
    var dificultad: Int
    var golpeable: Boolean
    var estaGolpeado: Boolean
    var ejecutando: Boolean
    var error:Int
    var velocidad:Int
    constructor(posX: Float, posY: Float, imagenes: ArrayList<Bitmap>) : super(posX, posY, imagenes[0], "Un topo") {
        this.imagenes = imagenes
        cambiarImagen(this.modo)
        this.estaGolpeado = false
        this.golpeable = false
        this.dificultad = 1
        this.ejecutando = false
        this.error = 0
        this.velocidad = 120
    }
    fun cambiarImagen(modo: Int) {
        this.modo = modo
        when (modo) {
            0 -> {
                this.img = imagenes[0]
            }
            1 -> {
                this.img = imagenes[1]
            }
            2 -> {
                this.img = imagenes[2]
            }
            3 -> {
                this.img = imagenes[3]
            }
            4 -> {
                this.img = imagenes[4]
            }
            5 -> {
                this.img = imagenes[5]
            }
            6 -> {
                this.img = imagenes[6]
            }
            7 -> {
                this.img = imagenes[7]
            }
            8 -> {
                this.img = imagenes[8]
            }
        }
    }

    fun golpear():Boolean {
        if (this.golpeable) {
            this.estaGolpeado = true
            return true
        }
        return false
    }

    suspend fun inicio(s: Semaphore) {
        this.ejecutando = true
        delay(400)
        s.acquire()
        delay(400)
        movimiento()
        s.release()
        this.ejecutando = false
    }

    suspend fun movimiento() {
        this.golpeable = false
        cambiarImagen(1)
        delay(this.velocidad.toLong())
        cambiarImagen(2)
        delay(this.velocidad.toLong())
        this.golpeable = true
        cambiarImagen(3)
        delay(this.velocidad.toLong())
        cambiarImagen(4)
        //delay((1200 / this.dificultad).toLong()+ Random.nextLong(200))
        for(i in 0..3){
            if (this.estaGolpeado) {
                ocultarse_Golpe()
                break
            }
            delay((360-(80*dificultad)).toLong())
        }
        if(!estaGolpeado && this.visible){
            ocultarse();
            this.error++
        }
        this.estaGolpeado = false
        cambiarImagen(0)
    }
    suspend fun ocultarse(){
        cambiarImagen(4)
        delay(this.velocidad.toLong())
        cambiarImagen(3)
        delay(this.velocidad.toLong())
        cambiarImagen(2)
        delay(this.velocidad.toLong())
        cambiarImagen(1)
        delay(this.velocidad.toLong())
    }
    suspend fun ocultarse_Golpe(){
        this.golpeable = false
        cambiarImagen(8)
        delay(this.velocidad.toLong())
        cambiarImagen(7)
        delay(this.velocidad.toLong())
        cambiarImagen(6)
        delay(this.velocidad.toLong())
        cambiarImagen(5)
        delay(this.velocidad.toLong())
    }
}