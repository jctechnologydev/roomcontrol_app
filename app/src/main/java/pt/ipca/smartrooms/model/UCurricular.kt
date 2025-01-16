package pt.ipca.smartrooms.model

import android.os.Parcel
import android.os.Parcelable


class UCurricular : Parcelable  {
    var id: Int? = null
    var name : String? = null
    var room : Room? = null


    constructor(parcel: Parcel) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        room = parcel.readValue(Room::class.java.classLoader) as Room?
    }

    constructor(id: Int?, name: String?, room: Room?) {
        this.id = id
        this.name = name
        this.room = room
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(room)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UCurricular> {
        override fun createFromParcel(parcel: Parcel): UCurricular {
            return UCurricular(parcel)
        }

        override fun newArray(size: Int): Array<UCurricular?> {
            return arrayOfNulls(size)
        }
    }

}

