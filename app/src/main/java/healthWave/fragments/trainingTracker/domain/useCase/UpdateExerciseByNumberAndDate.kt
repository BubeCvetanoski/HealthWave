package healthWave.fragments.trainingTracker.domain.useCase

import healthWave.fragments.trainingTracker.domain.repository.ExerciseRepository

class UpdateExerciseByNumberAndDate(
    val repository: ExerciseRepository
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