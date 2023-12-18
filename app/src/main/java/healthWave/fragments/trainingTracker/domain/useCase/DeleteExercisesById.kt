package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.fragments.trainingTracker.domain.repository.TrainingTrackerRepository

class DeleteExercisesById(
    val repository: TrainingTrackerRepository
) {
    suspend operator fun invoke(idsToBeDeleted: List<Int>, date: String) {
        return repository.deleteExercisesByIdAndDate(idsToBeDeleted, date)
    }
}