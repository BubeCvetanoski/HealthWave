package healthWave.fragments.trainingTracker.presentation.viewmodel

import healthWave.data.local.database.entity.Exercise

data class ExerciseState(
    val exercises: List<Exercise> = emptyList(),
    val isLoading: Boolean = false
)
