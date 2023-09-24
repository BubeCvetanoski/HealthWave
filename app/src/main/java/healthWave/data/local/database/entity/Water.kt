package healthWave.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import healthWave.data.local.database.Tables

@Entity(tableName = Tables.WATER)
data class Water(
    val date: String,
    val milliliters: String,
    @PrimaryKey val id: Int? = null
)
