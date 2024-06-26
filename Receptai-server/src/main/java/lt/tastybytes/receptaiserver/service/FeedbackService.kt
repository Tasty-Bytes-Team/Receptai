package lt.tastybytes.receptaiserver.service

import lt.tastybytes.receptaiserver.dto.feedback.CreateFeedbackDto
import lt.tastybytes.receptaiserver.model.Feedback
import lt.tastybytes.receptaiserver.model.user.User
import lt.tastybytes.receptaiserver.utils.Pager
import lt.tastybytes.receptaiserver.utils.Sorter
import org.springframework.data.domain.Page
import java.util.*

interface FeedbackService {

    /**
     * Returns total feedback count as saved in the database.
     */
    fun getTotalFeedbackCount(): Number

    /**
     * Return a page containing feedback for the specified recipe ID.
     */
    fun getFeedbackByRecipe(recipeId: Long, pager: Pager): Page<Feedback>

    /**
     * Returns optional Feedback object for the specified recipe made by the specified user.
     * Empty, if not found.
     */
    fun findFeedbackByRecipeAndUser(recipeId: Long, userId: Long): Optional<Feedback>

    /**
     * Returns a page of feedback for specified recipe author.
     */
    fun getFeedbackByRecipeAuthor(userId: Long, pager: Pager, sorter: Sorter): Page<Feedback>


    /**
     * Returns average recipe rating for the specified recipe ID.
     */
    fun getAverageRecipeRating(recipeId: Long): Double

    /**
     * Returns average recipe rating for the specified recipe author.
     */
    fun getAverageRecipeAuthorRating(userId: Long): Double

    fun getFeedback(pager: Pager): Page<Feedback>

    /**
     * Leave feedback on the specified recipe ID with the specified user.
     * The feedback contents are to be provided through a DTO object.
     */
    fun leaveFeedback(recipeId: Long, author: User, dto: CreateFeedbackDto): Feedback

    /**
     * Returns feedback object by the specified ID.
     */
    fun getFeedbackById(feedbackId: Long): Optional<Feedback>

    /**
     * Deletes feedback object by the specified ID.
     */
    fun deleteFeedbackById(feedbackId: Long): Boolean
}