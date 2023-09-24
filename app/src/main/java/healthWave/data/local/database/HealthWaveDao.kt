package healthWave.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import healthWave.data.local.database.entity.Exercise
import healthWave.data.local.database.entity.FoodEntity
import healthWave.data.local.database.entity.User
import healthWave.data.local.database.entity.Water
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthWaveDao {
    //Queries for User table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query(
        """
            SELECT * 
            FROM user 
            LIMIT 1
        """
    )
    fun getUser(): Flow<User>

    @Update
    suspend fun updateUser(user: User)

    @Query(
        """
            UPDATE user 
            SET firstName = :firstName,
            lastName = :lastName
         """
    )
    suspend fun updateUserFirstAndLastName(firstName: String, lastName: String)

    //Queries for Exercise table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Query(
        """
            SELECT * 
            FROM exercise 
            WHERE date = :date
        """
    )
    fun getExercisesByDate(date: String): Flow<List<Exercise>>

    @Query(
        """
            UPDATE exercise SET 
                name = :name, 
                sets = :sets, 
                reps = :reps, 
                load = :load 
                WHERE number = :number and date = :date
        """
    )
    suspend fun updateExerciseByNumberAndDate(
        name: String,
        sets: String,
        reps: String,
        load: String,
        number: String,
        date: String
    )

    @Query(
        """
            DELETE FROM exercise
            WHERE date = :date
        """
    )
    suspend fun deleteAllExercisesByDate(date: String)

    //Queries for Food table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(foodEntity: FoodEntity)

    @Query(
        """
            DELETE FROM food
            WHERE id = :foodId
        """
    )
    suspend fun deleteFoodById(foodId: Int)

    @Query(
        """
            SELECT *
            FROM food
            WHERE date = :date
        """
    )
    fun getFoodByDate(date: String): Flow<List<FoodEntity>>

    @Query(
        """
           SELECT *
           FROM food
           WHERE name LIKE :name
           GROUP BY name
        """
    )
    fun getFoodByName(name: String): Flow<List<FoodEntity>>

    //Queries for Water table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWater(water: Water)

    @Query(
        """
            DELETE FROM water
            WHERE id = :waterId
        """
    )
    suspend fun deleteWaterById(waterId: Int)

    @Query(
        """
            SELECT *
            FROM water
            WHERE date = :date
        """
    )
    fun getWaterByDate(date: String): Flow<List<Water>>
}