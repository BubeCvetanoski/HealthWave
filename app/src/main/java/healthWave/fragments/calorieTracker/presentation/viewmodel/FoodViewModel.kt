package healthWave.fragments.calorieTracker.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthwave.R
import dagger.hilt.android.lifecycle.HiltViewModel
import healthWave.core.util.HelperFunctions.Companion.getCurrentDateAsString
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.data.local.database.entity.Water
import healthWave.fragments.calorieTracker.domain.model.CalorieAndNutrientSummary
import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo
import healthWave.fragments.calorieTracker.domain.model.MealType
import healthWave.fragments.calorieTracker.domain.useCase.FoodUseCases
import healthWave.fragments.calorieTracker.presentation.event.CalorieTrackerEvent
import healthWave.fragments.calorieTracker.presentation.state.FoodNutrimentsInfoState
import healthWave.fragments.calorieTracker.presentation.state.FoodState
import healthWave.fragments.calorieTracker.presentation.state.OverviewState
import healthWave.fragments.calorieTracker.presentation.state.WaterState
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodUseCases: FoodUseCases
) : ViewModel() {

    private val _foodState = MutableStateFlow(FoodState())
    val foodState get() = _foodState.asStateFlow()

    private val _overviewState = MutableStateFlow(OverviewState())
    val overviewState get() = _overviewState.asStateFlow()

    private var shouldEmitValue by mutableStateOf(true)
    // A flag to prevent emitting a new value after insert in database

    private var getFoodByDateJob: Job? = null
    private var getFoodByNameJob: Job? = null
    private var getWaterByDateJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getFoodOverviewByDate(date = getCurrentDateAsString())
    }

    fun onEvent(event: CalorieTrackerEvent) {
        when (event) {
            is CalorieTrackerEvent.CalculateCaloriesAndNutrients -> calculateCaloriesAndNutrients(
                foods = event.foods
            )

            is CalorieTrackerEvent.ChangeFoodAmount -> changeFoodAmount(
                food = event.food,
                amount = event.amount
            )

            is CalorieTrackerEvent.ChangeWaterMilliliters -> changeWaterMilliliters(
                newMilliliters = event.newMilliliters
            )

            is CalorieTrackerEvent.DeleteFood -> deleteFood(
                date = event.date,
                food = event.food
            )

            is CalorieTrackerEvent.DeleteWater -> deleteWater(
                date = event.date,
                water = event.water
            )

            is CalorieTrackerEvent.FoodItemHeaderExpanded -> foodItemHeaderExpanded(
                food = event.food
            )

            is CalorieTrackerEvent.GetFoodByDate -> getFoodByDate(
                date = event.date,
                mealType = event.mealType
            )

            is CalorieTrackerEvent.GetFoodOverviewByDate -> getFoodOverviewByDate(
                date = event.date
            )

            is CalorieTrackerEvent.GetWaterByDate -> getWaterByDate(
                date = event.date
            )

            is CalorieTrackerEvent.InsertFood -> insertFood(
                date = event.date,
                food = event.food,
                mealType = event.mealType
            )

            is CalorieTrackerEvent.InsertWater -> insertWater(
                date = event.date,
                water = event.water
            )

            is CalorieTrackerEvent.QueryChange -> onQueryChange(
                query = event.query
            )

            is CalorieTrackerEvent.ResetRememberedState -> resetRememberedState(
                flag = event.flag
            )

            is CalorieTrackerEvent.SetEatingOccasionItemCardExpanded -> setEatingOccasionItemCardExpanded(
                flag = event.flag
            )

            is CalorieTrackerEvent.ViewMyMeals -> viewMyMeals(
                date = event.date,
                mealType = event.mealType
            )

            CalorieTrackerEvent.Search -> onSearch()
            CalorieTrackerEvent.EatingOccasionItemExpanded -> eatingOccasionItemExpanded()
        }
    }

    private fun getFoodOverviewByDate(
        date: String
    ) {
        _overviewState.value = overviewState.value.copy(isLoading = true)

        val foodFlow = foodUseCases.getFoodByDate(date)
        val waterFlow = foodUseCases.getWaterByDate(date)

        foodFlow.combine(waterFlow) { foods, waters ->
            val summary = calculateCaloriesAndNutrients(foods)

            val overallCalories = summary.calorieMap.values.sum()
            val overallCarbs = summary.nutrientMap["carbs"] ?: 0
            val overallProteins = summary.nutrientMap["proteins"] ?: 0
            val overallFats = summary.nutrientMap["fats"] ?: 0

            val breakfastCalories = summary.calorieMap[MealType.Breakfast] ?: 0
            val lunchCalories = summary.calorieMap[MealType.Lunch] ?: 0
            val dinnerCalories = summary.calorieMap[MealType.Dinner] ?: 0
            val snackCalories = summary.calorieMap[MealType.Snack] ?: 0

            val overallWaterIntake = waters.sumOf { it.milliliters.toInt() }

            _overviewState.value = overviewState.value.copy(
                isLoading = false,
                overallCalories = overallCalories,
                breakfastCalories = breakfastCalories,
                lunchCalories = lunchCalories,
                dinnerCalories = dinnerCalories,
                snackCalories = snackCalories,
                overallCarbs = overallCarbs,
                overallProteins = overallProteins,
                overallFats = overallFats,
                overallWaterIntake = overallWaterIntake
            )
        }.catch { exception ->
            exception.localizedMessage?.let { Log.d("Test", it) }
        }.launchIn(viewModelScope)
    }

    private fun calculateCaloriesAndNutrients(
        foods: List<Food>
    ): CalorieAndNutrientSummary {
        val calorieMap = foods
            .groupBy { it.mealType }
            .mapValues { (_, foods) -> foods.sumOf { it.calories } }

        val nutrientMap = mutableMapOf<String, Int>()

        foods.forEach { food ->
            nutrientMap["proteins"] = (nutrientMap["proteins"] ?: 0) + food.protein
            nutrientMap["fats"] = (nutrientMap["fats"] ?: 0) + food.fat
            nutrientMap["carbs"] = (nutrientMap["carbs"] ?: 0) + food.carbs
        }

        return CalorieAndNutrientSummary(calorieMap, nutrientMap)
    }

    private fun getWaterByDate(
        date: String
    ) {
        _foodState.value = foodState.value.copy(
            isLoading = true,
            waterIntakeInfo = emptyList()
        )

        getWaterByDateJob = foodUseCases
            .getWaterByDate(date)
            .onEach { waters ->
                _foodState.value = foodState.value.copy(
                    isLoading = false,
                    waterIntakeInfo = waters.map { water ->
                        WaterState(
                            id = water.id,
                            milliliters = water.milliliters
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }

    private fun deleteWater(
        date: String,
        water: WaterState
    ) {
        viewModelScope.launch {
            water.id?.let { foodUseCases.deleteWaterById(it) }

            val updatedWaterIntakeInfo = _foodState.value.waterIntakeInfo.filter {
                it.id != water.id
            }
            _foodState.value = foodState.value.copy(waterIntakeInfo = updatedWaterIntakeInfo)
            _uiEvent.send(
                UiEvent.ShowToast(
                    UiText.StringResource(resId = R.string.successfully_deleted)
                )
            )
            getFoodOverviewByDate(date = date)
        }
    }

    private fun insertWater(
        date: String,
        water: WaterState
    ) {
        viewModelScope.launch {

            foodUseCases.insertWater(
                water = Water(
                    milliliters = water.milliliters.takeIf { it.isNotEmpty() } ?: return@launch,
                    date = date
                )
            )
            getFoodOverviewByDate(date = date)
            _uiEvent.send(
                UiEvent.ShowToast(
                    UiText.StringResource(resId = R.string.successfully_added)
                )
            )
        }
    }

    private fun getFoodByDate(
        date: String,
        mealType: MealType = MealType.fromString("")
    ) {
        getWaterByDateJob?.cancel()
        _foodState.value = foodState.value.copy(
            isLoading = true,
            foodNutrimentsInfo = emptyList()
        )

        getFoodByDateJob = foodUseCases
            .getFoodByDate(date)
            .onEach { foods ->
                val filteredFoods =
                    if (mealType.name != "" && mealType.name != "All meals") {
                        foods.filter { it.mealType.name == mealType.name }
                    } else {
                        foods
                    }
                if (shouldEmitValue) {
                    _foodState.value = foodState.value.copy(
                        foodNutrimentsInfo = filteredFoods.map { food ->
                            FoodNutrimentsInfoState(
                                food = FoodNutrimentsInfo(
                                    id = food.id,
                                    name = food.name,
                                    imageUrl = food.imageUrl,
                                    calories100g = food.calories,
                                    carbs100g = food.carbs,
                                    protein100g = food.protein,
                                    fat100g = food.fat
                                ),
                                amount = food.amount.toString()
                            )
                        },
                        isLoading = false,
                        query = ""
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun onQueryChange(query: String) {
        _foodState.value = foodState.value.copy(query = query)
    }

    private fun eatingOccasionItemExpanded() {
        _foodState.value = foodState.value.copy(
            isEatingOccasionItemCardExpanded = !foodState.value.isEatingOccasionItemCardExpanded
        )
    }

    private fun resetRememberedState(flag: Boolean) {
        if (flag) {
            _foodState.value = foodState.value.copy(
                foodNutrimentsInfo = emptyList(),
                viewMyMeals = false
            )
        }
    }

    private fun setEatingOccasionItemCardExpanded(flag: Boolean) {
        _foodState.value = foodState.value.copy(
            isEatingOccasionItemCardExpanded = flag
        )
    }

    private fun foodItemHeaderExpanded(
        food: FoodNutrimentsInfo
    ) {
        _foodState.value = foodState.value.copy(
            foodNutrimentsInfo = _foodState.value.foodNutrimentsInfo.map {
                if (it.food == food) {
                    it.copy(
                        isFoodItemHeaderExpanded = !it.isFoodItemHeaderExpanded
                    )
                } else it
            }
        )
    }

    private fun changeFoodAmount(
        food: FoodNutrimentsInfo,
        amount: String
    ) {
        _foodState.value = foodState.value.copy(
            foodNutrimentsInfo = _foodState.value.foodNutrimentsInfo.map {
                if (it.food == food) {
                    it.copy(amount = amount)
                } else it
            }
        )
    }

    private fun changeWaterMilliliters(
        newMilliliters: String
    ) {
        _foodState.value = foodState.value.copy(
            takeWaterIntake = _foodState.value.takeWaterIntake.copy(
                milliliters = newMilliliters
            )
        )
    }

    private fun viewMyMeals(
        date: String,
        mealType: MealType
    ) {
        shouldEmitValue = true
        _foodState.value = foodState.value.copy(
            viewMyMeals = true
        )
        getFoodByDate(
            date = date,
            mealType = mealType
        )
    }

    private fun deleteFood(
        date: String,
        food: FoodNutrimentsInfo
    ) {
        viewModelScope.launch {
            food.id?.let { foodUseCases.deleteFoodById(it) }

            val updatedFoodNutrimentsInfo = _foodState.value.foodNutrimentsInfo.filter {
                it.food.id != food.id
            }
            _foodState.value = foodState.value.copy(foodNutrimentsInfo = updatedFoodNutrimentsInfo)
            _uiEvent.send(
                UiEvent.ShowToast(
                    UiText.StringResource(resId = R.string.successfully_deleted)
                )
            )
            getFoodOverviewByDate(date = date)
            shouldEmitValue = false
        }
    }

    private fun insertFood(
        date: String,
        food: FoodNutrimentsInfo,
        mealType: MealType
    ) {
        viewModelScope.launch {
            val uiState = _foodState.value.foodNutrimentsInfo.find { it.food == food }

            foodUseCases.insertFood(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = mealType,
                date = date
            )

            _uiEvent.send(
                UiEvent.ShowToast(
                    UiText.StringResource(resId = R.string.successfully_added)
                )
            )
        }
        shouldEmitValue = false
    }

    private fun onSearch() {
        viewModelScope.launch {
            _foodState.value = foodState.value.copy(
                isLoading = true,
                viewMyMeals = false,
                foodNutrimentsInfo = emptyList()
            )
            foodUseCases
                .searchFood(_foodState.value.query)
                .onSuccess { foods ->
                    if (foods.isEmpty()) {
                        _uiEvent.send(
                            UiEvent.ShowToast(
                                UiText.StringResource(resId = R.string.no_food_found)
                            )
                        )
                    }
                    _foodState.value = foodState.value.copy(
                        foodNutrimentsInfo = foods.map {
                            FoodNutrimentsInfoState(it)
                        },
                        isLoading = false,
                        query = ""
                    )
                }
                .onFailure {
                    shouldEmitValue = true
                    getFoodByNameJob?.cancel()
                    getFoodByNameJob = foodUseCases
                        .getFoodByName(_foodState.value.query)
                        .onEach { foods ->
                            if (shouldEmitValue) {
                                if (foods.isEmpty()) {
                                    _foodState.value = foodState.value.copy(
                                        isLoading = false,
                                        query = ""
                                    )
                                    _uiEvent.send(
                                        UiEvent.ShowToast(
                                            UiText.StringResource(resId = R.string.check_your_internet)
                                        )
                                    )
                                    return@onEach
                                }
                                _foodState.value = foodState.value.copy(
                                    foodNutrimentsInfo = foods.map { food ->
                                        FoodNutrimentsInfoState(
                                            food = FoodNutrimentsInfo(
                                                id = food.id,
                                                name = food.name,
                                                imageUrl = food.imageUrl,
                                                calories100g = food.caloriesPer100g,
                                                carbs100g = food.carbsPer100g,
                                                protein100g = food.proteinPer100g,
                                                fat100g = food.fatPer100g
                                            )
                                        )
                                    },
                                    isLoading = false,
                                    query = ""
                                )
                            } else {
                                _foodState.value = foodState.value.copy(
                                    isLoading = false,
                                    query = ""
                                )
                            }
                        }.launchIn(viewModelScope)
                }
        }
    }
}