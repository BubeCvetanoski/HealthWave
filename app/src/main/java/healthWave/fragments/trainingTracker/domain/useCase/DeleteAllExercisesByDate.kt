package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.fragments.trainingTracker.domain.repository.TrainingTrackerRepository

class DeleteAllExercisesByDate(
    val repository: TrainingTrackerRepository
) {
    suspend operator fun invoke(date: String) {
        return repository.deleteAllExercisesByDate(date)
    }
}