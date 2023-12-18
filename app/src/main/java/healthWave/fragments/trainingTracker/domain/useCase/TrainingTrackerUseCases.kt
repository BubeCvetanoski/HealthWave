package healthWave.fragments.trainingTracker.domain.useCase

data class TrainingTrackerUseCases(
    val addExercise: AddExercise,
    val getExercisesByDate: GetExercisesByDate,
    val updateExerciseByNumberAndDate: UpdateExerciseByNumberAndDate,
    val deleteAllExercisesByDate: DeleteAllExercisesByDate,
    val getExerciseIdsByNumberAndDate: GetExerciseIdsByNumberAndDate,
    val deleteExercisesById: DeleteExercisesById,
    val updateExerciseNumberByIdAndDate: UpdateExerciseNumberByIdAndDate
)
