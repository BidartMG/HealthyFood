package com.ort.healthyfoods.entities

import android.os.Parcel
import android.os.Parcelable
import java.sql.Timestamp
import java.time.Instant
import java.util.*

class FoodDetail (idComida:Int, nombre:String, descripcion:String, tipoComida:String, urlImagen:String, calorias:Int, usuario: String, fechaRealizada: Date?) : Parcelable {
    var idComida: Int
    var nombre: String
    var descripcion: String
    var tipoComida: String
    var urlImagen: String
    var calorias: Int
    var usuario: String
    var fechaRealizada: Date?

    constructor (): this (0,"","","","",0,"", null)

    class Constants {
        companion object {
            val tipoConCarne = "INCLUYE CARNE"
            val tipoVegetariana = "VEGETARIANA"
            val tipoVegana = "VEGANA"
            val tipoDulce ="DULCE"
            val tipoSalada = "SALADA"
            val tipoConTACC = "CONTIENE TACC"
            val tipoSinTacc = "SIN TACC"
        }
    }
    init {
        this.idComida = idComida
        this.nombre = nombre
        this.descripcion = descripcion
        this.tipoComida = tipoComida
        this.urlImagen = urlImagen
        this.calorias = calorias
        this.usuario = usuario
        this.fechaRealizada = fechaRealizada
    }

    constructor(source: Parcel) : this (
        source.readInt()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readInt()!!,
        source.readString()!!,
        source.readDate()
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(idComida)
        writeString(nombre)
        writeString(tipoComida)
        writeString(descripcion)
        writeString(urlImagen)
        writeInt(calorias)
        writeDate(fechaRealizada)
    }

    override fun toString(): String {
        return "FoodDetail(codigo = '$idComida', nombre = '$nombre', tipo = '$tipoComida', detalle = '$descripcion', urlImagen = '$urlImagen', calorias = '$calorias')"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<FoodDetail> = object : Parcelable.Creator<FoodDetail> {
            override fun createFromParcel(source: Parcel): FoodDetail = FoodDetail(source)
            override fun newArray(size: Int): Array<FoodDetail?> = arrayOfNulls(size)
        }
    }

}

//Validacion del Date
fun Parcel.readDate(): Date? {
    val long = readLong()
    return if (long != -1L) Date(long) else null
}
fun Parcel.writeDate(date: Date?) {
    writeLong(date?.time ?: -1)
}