package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.fragments.trainingTracker.domain.repository.ExerciseRepository

class DeleteAllExercisesByDate(
    val repository: ExerciseRepository
) {
    suspend operator fun invoke(date: String) {
        return repository.deleteAllExercisesByDate(date)
    }
}