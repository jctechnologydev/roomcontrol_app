package pt.ipca.smartrooms.model

import android.os.Parcel
import android.os.Parcelable

class User() : Parcelable {
    var id: Int? = null
    var name: String? = null
    var userType: UserType? = null
    var course: String? = null
    var token: String? = null
    var imageUrl: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        userType = parcel.readString()?.let { UserType.valueOf(it) }
        course = parcel.readString()
        imageUrl = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(userType?.name)
        parcel.writeString(course)
        parcel.writeString(imageUrl)
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