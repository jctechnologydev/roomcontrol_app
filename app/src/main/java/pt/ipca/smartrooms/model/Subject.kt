package pt.ipca.smartrooms.model

import android.os.Parcel
import android.os.Parcelable

data class Subject(
    val idSubject: Int,
    val idUser: Int,
    val subject: String,
    val ects: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idSubject)
        parcel.writeInt(idUser)
        parcel.writeString(subject)
        parcel.writeInt(ects)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Subject> {
        override fun createFromParcel(parcel: Parcel): Subject {
            return Subject(parcel)
        }

        override fun newArray(size: Int): Array<Subject?> {
            return arrayOfNulls(size)
        }
    }
}