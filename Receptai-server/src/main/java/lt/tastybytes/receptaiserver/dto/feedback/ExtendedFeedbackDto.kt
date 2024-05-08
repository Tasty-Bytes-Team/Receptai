package lt.tastybytes.receptaiserver.dto.feedback

import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto
import lt.tastybytes.receptaiserver.dto.user.PublicUserDto
import java.util.Date

@JvmRecord
data class ExtendedFeedbackDto(
    val user: PublicUserDto,
    val content: String,
    val rating: Int,
    val dateCreated: Date,
    val recipe: RecipeDto
)