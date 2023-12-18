package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.fragments.trainingTracker.domain.repository.TrainingTrackerRepository

class GetExerciseIdsByNumberAndDate(
    val repository: TrainingTrackerRepository
) {
    suspend operator fun invoke(
        numbersOfExercises: List<String>,
        date: String
    ): List<Int> {
        return repository.getExerciseIdsByNumberAndDate(numbersOfExercises, date)
    }
}