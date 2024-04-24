package lt.tastybytes.receptaiserver.service

import lt.tastybytes.receptaiserver.dto.feedback.CreateFeedbackDto
import lt.tastybytes.receptaiserver.model.Feedback
import lt.tastybytes.receptaiserver.model.user.User
import org.springframework.data.domain.Page
import java.util.Optional

interface FeedbackService {

    /**
     * Return a page containing feedback for the specified recipe ID.
     */
    fun getFeedbackByRecipe(recipeId: Long, pageNumber: Long): Page<Feedback>

    /**
     * Returns optional Feedback object for the specified recipe made by the specified user.
     * Empty, if not found.
     */
    fun findFeedbackByRecipeAndUser(recipeId: Long, userId: Long): Optional<Feedback>

    /**
     * Returns a page of feedback for specified recipe author.
     */
    fun getFeedbackByRecipeAuthor(userId: Long, pageNumber: Long): Page<Feedback>


    /**
     * Returns average recipe rating for the specified recipe ID.
     */
    fun getAverageRecipeRating(recipeId: Long): Double

    /**
     * Leave feedback on the specified recipe ID with the specified user.
     * The feedback contents are to be provided through a DTO object.
     */
    fun leaveFeedback(recipeId: Long, author: User, dto: CreateFeedbackDto): Feedback
}