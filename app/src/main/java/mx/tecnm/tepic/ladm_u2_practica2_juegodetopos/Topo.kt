package mx.tecnm.tepic.ladm_u2_practica2_juegodetopos

import android.graphics.BitmapFactory

class Topo : Imagen{

    constructor(posX:Float,posY:Float,lienzo:Lienzo):super(posX,posY,BitmapFactory.decodeResource(lienzo.resources,R.drawable.topooculto),"Un topo"){

    }
}