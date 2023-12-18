package healthWave.fragments.trainingTracker.domain.repository

import healthWave.data.local.database.entity.Exercise
import kotlinx.coroutines.flow.Flow

interface TrainingTrackerRepository {
    suspend fun insertExercise(exercise: Exercise)
    fun getExercisesByDate(date: String): Flow<List<Exercise>>
    suspend fun updateExerciseByNumberAndDate(
        name: String,
        sets: String,
        reps: String,
        load: String,
        number: String,
        date: String
    )
    suspend fun deleteAllExercisesByDate(date: String)
    suspend fun getExerciseIdsByNumberAndDate(
        numbersOfExercises: List<String>,
        date: String
    ): List<Int>
    suspend fun deleteExercisesByIdAndDate(
        idsToBeDeleted: List<Int>,
        date: String
    )
    suspend fun updateExerciseNumberByIdAndDate(
        number: String,
        id: Int,
        date:String
    )
}