package healthWave.fragments.calorieTracker.presentation.event

import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo
import healthWave.fragments.calorieTracker.domain.model.MealType
import healthWave.fragments.calorieTracker.state.WaterState

sealed class CalorieTrackerEvent {

    data class GetFoodOverviewByDate(
        val date: String
    ) : CalorieTrackerEvent()

    data class CalculateCaloriesAndNutrients(
        val foods: List<Food>
    ) : CalorieTrackerEvent()

    data class GetWaterByDate(
        val date: String
    ) : CalorieTrackerEvent()

    data class DeleteWater(
        val date: String,
        val water: WaterState
    ) : CalorieTrackerEvent()

    data class InsertWater(
        val date: String,
        val water: WaterState
    ) : CalorieTrackerEvent()

    data class GetFoodByDate(
        val date: String,
        val mealType: MealType = MealType.fromString("")
    ) : CalorieTrackerEvent()

    data class OnQueryChange(
        val query: String
    ) : CalorieTrackerEvent()

    data class ResetRememberedState(
        val flag: Boolean
    ) : CalorieTrackerEvent()

    data class SetEatingOccasionItemCardExpanded(
        val flag: Boolean
    ) : CalorieTrackerEvent()

    data class FoodItemHeaderExpanded(
        val food: FoodNutrimentsInfo
    ) : CalorieTrackerEvent()

    data class ChangeFoodAmount(
        val food: FoodNutrimentsInfo,
        val amount: String
    ) : CalorieTrackerEvent()

    data class ChangeWaterMilliliters(
        val newMilliliters: String
    ) : CalorieTrackerEvent()

    data class ViewMyMeals(
        val date: String,
        val mealType: MealType
    ) : CalorieTrackerEvent()

    data class DeleteFood(
        val date: String,
        val food: FoodNutrimentsInfo
    ) : CalorieTrackerEvent()

    data class InsertFood(
        val date: String,
        val food: FoodNutrimentsInfo,
        val mealType: MealType
    ) : CalorieTrackerEvent()

    data object OnSearch : CalorieTrackerEvent()
    data object EatingOccasionItemExpanded : CalorieTrackerEvent()
}
