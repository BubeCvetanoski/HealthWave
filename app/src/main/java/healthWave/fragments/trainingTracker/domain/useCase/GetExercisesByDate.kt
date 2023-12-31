package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.data.local.database.entity.Exercise
import healthWave.fragments.trainingTracker.domain.repository.TrainingTrackerRepository
import kotlinx.coroutines.flow.Flow

class GetExercisesByDate(
    val repository: TrainingTrackerRepository
) {
    operator fun invoke(date: String): Flow<List<Exercise>> {
        return repository.getExercisesByDate(date)
    }
}