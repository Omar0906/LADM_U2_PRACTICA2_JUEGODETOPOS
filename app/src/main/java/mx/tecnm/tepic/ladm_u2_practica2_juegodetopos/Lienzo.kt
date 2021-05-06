package mx.tecnm.tepic.ladm_u2_practica2_juegodetopos

import android.graphics.*
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.sync.Semaphore
import kotlin.random.Random

class Lienzo(p: MainActivity, Topo: ArrayList<Topo>) : View(p) {
    var pantalla: Int = 0 //Para indicar en que "pantalla" se encuentra

    //Botones
    val botonJugar: Imagen = Imagen(200f, 700f, BitmapFactory.decodeResource(this.resources, R.drawable.botonjugar), "Boton para iniciar")
    val botonreinentar: Imagen = Imagen(200f, 700f, BitmapFactory.decodeResource(this.resources, R.drawable.botonreintentar), "Boton para reintentar")

    //Fondos
    val fondoPrincipal: Imagen = Imagen(0f, 0f, BitmapFactory.decodeResource(this.resources, R.drawable.fondojuego), "El fondo del menu principal")
    val fondoJuego: Imagen = Imagen(0f, 0f, BitmapFactory.decodeResource(this.resources, R.drawable.fondojuego2), "El fondo del juego")

    //Fondo final
    val derrota: Imagen = Imagen(0f, 0f, BitmapFactory.decodeResource(this.resources, R.drawable.lose), "Se muestra cuando pierdes")
    val victoria: Imagen = Imagen(0f, 0f, BitmapFactory.decodeResource(this.resources, R.drawable.winner), "Se muestra cuando ganas")

    //El tamaño de la pantalla
    val rectPantalla: RectF = RectF(0f, 0f, 720f, 1310f)

    var Topos: ArrayList<Topo> = Topo //Arreglo con todos los topos

    var juego: hitTheTopo? = null //Objeto que maneja el juego

    //Texto
    var puntos: Int = 0

    //Strikes
    val cross: Bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.cross64)
    var strikeOffset: Float = 0f
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        var strikes: Int = 0
        strikeOffset = 0f
        val pincel = Paint()
        when (pantalla) {
            0 -> {
                c.drawBitmap(fondoPrincipal.img, null, rectPantalla, pincel)
                botonJugar.dibujar(c, pincel)
            }
            1 -> {
                c.drawBitmap(fondoJuego.img, null, rectPantalla, pincel)
                pincel.textSize = 30f
                c.drawText("${this.puntos} Topos de ${this.juego!!.topoRequired}", 10f, 100f, pincel)
                pincel.textSize = 50f
                c.drawText("Nivel actual: ${this.juego!!.dificultad}", 230f, 70f, pincel)
                for (i in this.Topos) {
                    i.dibujar(c, pincel)
                    strikes += i.error
                }
                for (cruz in 1..strikes) {
                    c.drawBitmap(cross, null, RectF(480f + strikeOffset, 80f, 480f + strikeOffset + 32f, 112f), pincel)
                    strikeOffset += 80f
                }
            }
            2 -> {
                c.drawBitmap(fondoPrincipal.img, null, rectPantalla, pincel)
                c.drawBitmap(victoria.img, null, rectPantalla, pincel)
                botonreinentar.dibujar(c, pincel)
            }
            3 -> {
                c.drawBitmap(fondoPrincipal.img, null, rectPantalla, pincel)
                c.drawBitmap(derrota.img, null, rectPantalla, pincel)
                botonreinentar.dibujar(c, pincel)
            }
        }
        if (strikes >= 3) {//Si pierdes
            pantalla = 3
            this.juego!!.jugandoNivel = false
            this.puntos = 0
        }
        if (this.puntos >= 20 && this.juego!!.dificultad == 3) { //Si ganaste
            pantalla = 2
            this.juego!!.jugandoNivel = false
            this.puntos = 0
        }
        if (this.puntos >= this.juego!!.topoRequired) { //Para subir de nivel
            juego!!.increaseDifficulty()
            this.puntos = 0
        }
        invalidate()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (botonJugar.estaEnArea(x, y) && pantalla == 0) {
                    this.pantalla = 1
                    juego!!.start()
                }
                if (botonreinentar.estaEnArea(x, y) && (pantalla in 2..3)) {
                    this.pantalla = 1
                    this.juego!!.reset()
                }
                for (i in Topos) {
                    if (i.estaEnArea(x, y)) {
                        if (i.golpear() && i.visible) {
                            this.puntos++
                        }
                    }
                }
            }
        }
        invalidate()
        return true
    }
}

class hitTheTopo {
    val lienzo: Lienzo
    var dificultad: Int
    var topoRequired: Int
    val spritesTopos: ArrayList<Bitmap>
    var Topos: ArrayList<Topo>
    var jugandoNivel: Boolean
    var inGame: Boolean

    constructor(lienzo: Lienzo, sprites: ArrayList<Bitmap>, Topos: ArrayList<Topo>) {
        this.lienzo = lienzo
        this.spritesTopos = sprites
        this.Topos = Topos
        this.dificultad = 1
        this.topoRequired = 10
        this.lienzo.juego = this
        this.jugandoNivel = true
        this.inGame = true
        this.Topos[4].visible = false
        this.Topos[5].visible = false
    }

    fun reset() {
        this.Topos = arrayListOf(Topo(10f, 700f, spritesTopos),
                Topo(350f, 480f, spritesTopos),
                Topo(350f, 1000f, spritesTopos),
                Topo(10f, 950f, spritesTopos),
                Topo(360f, 750f, spritesTopos),
                Topo(10f, 460f, spritesTopos))
        this.Topos[4].visible = false
        this.Topos[5].visible = false
        this.dificultad = 1
        this.topoRequired = 10
        this.jugandoNivel = true
        this.inGame = true
        this.lienzo.Topos = this.Topos
    }

    fun increaseDifficulty() {
        this.dificultad++
        when (dificultad) {
            1 -> {
                this.topoRequired = 10
            }
            2 -> {
                this.topoRequired = 15
                this.Topos[4].visible = true
            }
            3 -> {
                this.topoRequired = 20
                this.Topos[5].visible = true
            }
        }
        for (topo in this.Topos) {
            topo.dificultad = this.dificultad
            topo.velocidad -= 20
        }
        this.lienzo.Topos = this.Topos

    }

    fun start() {
        var semaforo = Semaphore(5)
        GlobalScope.launch(Dispatchers.IO) {
            while (inGame) {
                delay(2000)
                while (jugandoNivel) {
                    for (i in Topos) {
                        async(Dispatchers.IO) {
                            i.inicio(semaforo)
                        }
                        delay((800 + Random.nextLong(500)))
                    }
                    delay(800+Random.nextLong(1000))
                }
                delay(1000)
            }
        }
    }
}