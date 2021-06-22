package `in`.dmart.enterprise.refilling.model.apimodel.task.row.response

import android.os.Parcel
import android.os.Parcelable

data class Row (
    val rowId: String?,
    val rowName: String?,
    val totalArticles: String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(rowId)
        parcel.writeString(rowName)
        parcel.writeString(totalArticles)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Row> {
        override fun createFromParcel(parcel: Parcel): Row {
            return Row(parcel)
        }

        override fun newArray(size: Int): Array<Row?> {
            return arrayOfNulls(size)
        }
    }

}