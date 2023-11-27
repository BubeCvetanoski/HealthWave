package healthWave.fragments.calorieTracker.presentation.state

data class OverviewState(
    val isLoading: Boolean = false,
    val overallCalories: Int = 0,
    val breakfastCalories: Int = 0,
    val lunchCalories: Int = 0,
    val dinnerCalories: Int = 0,
    val snackCalories: Int = 0,
    val overallCarbs: Int = 0,
    val overallProteins: Int = 0,
    val overallFats: Int = 0,
    val overallWaterIntake: Int = 0
)
