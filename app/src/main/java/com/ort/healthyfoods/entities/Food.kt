package com.ort.healthyfoods.entities

import android.os.Parcel
import android.os.Parcelable

class Food (idComida:Int, nombre:String, descripcion:String, tipoComida:String, urlImagen:String, calorias:Int) : Parcelable {
    var idComida: Int
    var nombre: String
    var descripcion: String
    var tipoComida: String
    var urlImagen: String
    var calorias: Int

    constructor (): this (0,"","","","",0)

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
    }

    constructor(source: Parcel) : this (
        source.readInt()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readInt()!!
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(idComida)
        writeString(nombre)
        writeString(tipoComida)
        writeString(descripcion)
        writeString(urlImagen)
        writeInt(calorias)
    }

    override fun toString(): String {
        return "Food(codigo = '$idComida', nombre = '$nombre', tipo = '$tipoComida', detalle = '$descripcion', urlImagen = '$urlImagen', calorias = '$calorias')"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Food> = object : Parcelable.Creator<Food> {
            override fun createFromParcel(source: Parcel): Food = Food(source)
            override fun newArray(size: Int): Array<Food?> = arrayOfNulls(size)
        }

    }
}