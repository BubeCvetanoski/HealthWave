package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.data.local.database.entity.Exercise
import healthWave.fragments.trainingTracker.domain.repository.TrainingTrackerRepository

class AddExercise(
    private val repository: TrainingTrackerRepository
) {
    suspend operator fun invoke(exercise: Exercise) {
        repository.insertExercise(exercise)
    }
}