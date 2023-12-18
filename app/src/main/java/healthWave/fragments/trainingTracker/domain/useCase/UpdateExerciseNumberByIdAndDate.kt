package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.fragments.trainingTracker.domain.repository.TrainingTrackerRepository

class UpdateExerciseNumberByIdAndDate(
    val repository: TrainingTrackerRepository
) {
    suspend operator fun invoke(number: String, id: Int, date:String) {
        return repository.updateExerciseNumberByIdAndDate(number, id, date)
    }
}