package com.ort.healthyfoods.entities

import android.os.Parcel
import android.os.Parcelable

class User (idUsuario:Int, nombre:String, apellido:String, email:String, telefono:String, password:String) : Parcelable {
    var idUsuario: Int
    var nombre: String
    var apellido: String
    var email: String
    var telefono: String
    var password: String

    constructor (): this (0,"","","","","")

    constructor(parcel: Parcel) : this(
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    init {
        this.idUsuario = idUsuario
        this.nombre = nombre
        this.apellido = apellido
        this.email = email
        this.telefono = telefono
        this.password = password
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeInt(idUsuario)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(email)
        parcel.writeString(telefono)
        parcel.writeString(password)
    }

    override fun toString(): String {
        return "User(codigo = '$idUsuario', nombre = '$nombre', apellido = '$apellido', email = '$email', telefono = '$telefono', password = '$password')"
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

/* Acá permití que agregara automáticamente lo parcelable */
}