package lt.tastybytes.receptaiserver.dto

import lt.tastybytes.receptaiserver.dto.feedback.FeedbackDto
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto

@JvmRecord
data class DashboardDto(
        val createdRecipeCount: Long,
        val mostRecentRecipes: List<RecipeDto>,
        val mostRecentFeedback: List<FeedbackDto>,
        val averageRecipeRating: Double
)