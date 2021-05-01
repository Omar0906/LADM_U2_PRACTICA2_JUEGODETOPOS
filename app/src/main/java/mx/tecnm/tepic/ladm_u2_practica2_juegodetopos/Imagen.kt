package mx.tecnm.tepic.ladm_u2_practica2_juegodetopos

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

open class Imagen {
    var posX: Float
    var posX2: Float
    var posY: Float
    var posY2: Float
    var visible: Boolean
    var img: Bitmap
    val nombre: String

    constructor(posX: Float, posY: Float, imagen: Bitmap, nombre: String) {
        this.posX = posX
        this.posY = posY
        this.img = imagen
        this.nombre = nombre
        this.posX2 = this.posX + this.img.width
        this.posY2 = this.posY + this.img.height
        this.visible = true
    }

    fun dibujar(c: Canvas, pincel: Paint) {
        if (visible)
            c.drawBitmap(img, posX, posY, pincel)
    }

    fun mover(toqueX: Float, toqueY: Float) {
        posX = toqueX - img.width / 2
        posY = toqueY - img.height / 2
    }

    fun colision(imagenB: Imagen): Boolean {
        if (imagenB.estaEnArea(posX, posX)) return true
        if (imagenB.estaEnArea(posX2, posY)) return true
        if (imagenB.estaEnArea(posX, posY2)) return true
        if (imagenB.estaEnArea(posX2, posY2)) return true
        return false
    }

    fun estaEnArea(x: Float, y: Float): Boolean {
        if ((x in posX..posX2) && (y in posY..posY2))
            return true
        return false
    }
}