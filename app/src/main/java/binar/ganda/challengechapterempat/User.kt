package binar.ganda.challengechapterempat

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey (autoGenerate = true) var id: Int?,
    @ColumnInfo (name = "username") var username: String?,
    @ColumnInfo (name = "email") var email: String?,
    @ColumnInfo (name = "confirm password") var confirmPass: String?,
    @ColumnInfo (name = "password") var password: String?,
) : Parcelable
