package healthWave.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import healthWave.data.local.database.Tables

@Entity(tableName = Tables.EXERCISE)
data class Exercise(
    val number: String,
    val name: String,
    val sets: String,
    val reps: String,
    val load: String,
    val date: String,
    @PrimaryKey val id: Int? = null
)
