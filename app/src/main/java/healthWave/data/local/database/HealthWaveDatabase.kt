package healthWave.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import healthWave.core.util.Constants
import healthWave.data.local.database.entity.Exercise
import healthWave.data.local.database.entity.FoodEntity
import healthWave.data.local.database.entity.User
import healthWave.data.local.database.entity.Water

@Database(
    entities = [
        User::class,
        Exercise::class,
        FoodEntity::class,
        Water::class
    ],
    version = 1
)
abstract class HealthWaveDatabase : RoomDatabase() {

    abstract val healthWaveDao: HealthWaveDao

    companion object {
        const val DATABASE_NAME = Constants.DATABASE_NAME
    }
}