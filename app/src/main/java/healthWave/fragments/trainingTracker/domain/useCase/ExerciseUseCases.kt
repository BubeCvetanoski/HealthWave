package healthWave.fragments.trainingTracker.domain.useCase

data class ExerciseUseCases(
    val addExercise: AddExercise,
    val getExercisesByDate: GetExercisesByDate,
    val updateExerciseByNumberAndDate: UpdateExerciseByNumberAndDate,
    val deleteAllExercisesByDate: DeleteAllExercisesByDate
)
