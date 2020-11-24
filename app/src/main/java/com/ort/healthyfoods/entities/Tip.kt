package com.ort.healthyfoods.entities

import android.os.Parcel
import android.os.Parcelable

class Tip (idTip:String, titulo:String, descripcion:String, urlImagen:String) : Parcelable {
        var idTip: String
        var titulo: String
        var descripcion: String
        var urlImagen: String

        constructor (): this ("0","","","")

        constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!
        )
    init {
        this.idTip = idTip
        this.titulo = titulo
        this.descripcion = descripcion
        this.urlImagen = urlImagen
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeString(idTip)
        parcel.writeString(titulo)
        parcel.writeString(descripcion)
        parcel.writeString(urlImagen)
    }

    override fun toString(): String {
        return "Tip(codigo = '$idTip', titulo = '$titulo', detalle = '$descripcion', urlImagen = '$urlImagen')"
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}