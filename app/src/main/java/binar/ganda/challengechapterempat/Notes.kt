package binar.ganda.challengechapterempat

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Notes(
    @PrimaryKey (autoGenerate = true) var id: Int?,
    @ColumnInfo (name = "title") var title: String?,
    @ColumnInfo (name = "description") var desc: String?,
) : Parcelable
