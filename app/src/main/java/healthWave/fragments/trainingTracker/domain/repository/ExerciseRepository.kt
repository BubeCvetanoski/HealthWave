package healthWave.fragments.trainingTracker.domain.repository

import healthWave.data.local.database.entity.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
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
}