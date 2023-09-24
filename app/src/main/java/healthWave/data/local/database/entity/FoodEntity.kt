package healthWave.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import healthWave.data.local.database.Tables

@Entity(tableName = Tables.FOOD)
data class FoodEntity(
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val mealType: String,
    val amount: Int,
    val date: String,
    val calories: Int,
    val carbsPer100g: Int,
    val proteinPer100g: Int,
    val fatPer100g: Int,
    val caloriesPer100g: Int,
    @PrimaryKey val id: Int? = null
)
