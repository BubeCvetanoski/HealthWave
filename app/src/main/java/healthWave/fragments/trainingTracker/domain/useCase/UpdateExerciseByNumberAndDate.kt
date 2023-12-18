package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.fragments.trainingTracker.domain.repository.TrainingTrackerRepository

class UpdateExerciseByNumberAndDate(
    val repository: TrainingTrackerRepository
) {
    suspend operator fun invoke(
        name: String,
        sets: String,
        reps: String,
        load: String,
        number: String,
        date: String
    ) {
        return repository.updateExerciseByNumberAndDate(
            name,
            sets,
            reps,
            load,
            number,
            date
        )
    }
}