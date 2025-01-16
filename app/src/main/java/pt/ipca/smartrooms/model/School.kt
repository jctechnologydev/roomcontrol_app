package pt.ipca.smartrooms.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class School : Parcelable {
    @SerializedName("idSchool")
    var id: Int? = null
    var name: String? = null
    var urlToImage  : String? = null

    constructor(parcel: Parcel) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        urlToImage = parcel.readString()
    }

    constructor(id: Int?, name: String?, urlToImage : String?) {
        this.id = id
        this.name = name
        this.urlToImage = urlToImage
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(urlToImage)
    }
    override fun describeContents(): Int {
        return 0;
    }

    companion object CREATOR : Parcelable.Creator<School> {
        override fun createFromParcel(parcel: Parcel): School {
            return School(parcel)
        }

        override fun newArray(size: Int): Array<School?> {
            return arrayOfNulls(size)
        }
    }
}