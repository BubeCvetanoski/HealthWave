package healthWave.data.local.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import healthWave.data.local.database.Tables
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Tables.USER)
data class User(
    var firstName: String = "",
    var lastName: String = "",
    var gender: String = "",
    var age: String = "",
    var height: String = "",
    var weight: String = "",
    var activity: String = "",
    var calories: String = "",
    var goal: String = "",
    @PrimaryKey val id: Int? = null
): Parcelable