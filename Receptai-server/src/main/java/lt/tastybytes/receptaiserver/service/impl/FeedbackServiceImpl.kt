package lt.tastybytes.receptaiserver.service.impl

import lt.tastybytes.receptaiserver.dto.feedback.CreateFeedbackDto
import lt.tastybytes.receptaiserver.exception.EntryAlreadyExistsException
import lt.tastybytes.receptaiserver.exception.NotFoundException
import lt.tastybytes.receptaiserver.exception.RuntimeValidationException
import lt.tastybytes.receptaiserver.model.Feedback
import lt.tastybytes.receptaiserver.model.recipe.Recipe
import lt.tastybytes.receptaiserver.model.user.User
import lt.tastybytes.receptaiserver.repository.FeedbackRepository
import lt.tastybytes.receptaiserver.service.FeedbackService
import lt.tastybytes.receptaiserver.service.RecipeService
import lt.tastybytes.receptaiserver.service.UserService
import lt.tastybytes.receptaiserver.utils.Pager
import lt.tastybytes.receptaiserver.utils.Sorter
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrDefault

const val FEEDBACK_PER_PAGE = 20L

@Service
class FeedbackServiceImpl(
    private var feedbackRepository: FeedbackRepository,
    private var recipeService: RecipeService,
    private var userService: UserService
) : FeedbackService {

    override fun getTotalFeedbackCount(): Number {
        return feedbackRepository.count()
    }

    override fun getFeedbackByRecipe(recipeId: Long, pager: Pager): Page<Feedback> {
        val recipe = recipeService.getRecipeById(recipeId)
        val request = pager.toPageRequest(FEEDBACK_PER_PAGE)
        return feedbackRepository.findAllByRecipe(
            recipe.orElseThrow(),
            request
        )
    }

    override fun findFeedbackByRecipeAndUser(recipeId: Long, userId: Long): Optional<Feedback> {
        val recipe = recipeService.getRecipeById(recipeId)
        val user = userService.findUserById(userId);
        return feedbackRepository.findByRecipeAndAuthor(
            recipe.orElseThrow(),
            user.orElseThrow(),
        )
    }

    override fun getFeedbackByRecipeAuthor(userId: Long, pager: Pager, sorter: Sorter): Page<Feedback> {
        val user = userService.findUserById(userId)
        var request = pager.toPageRequest(FEEDBACK_PER_PAGE)
        request = sorter.toPageRequest(request)
        return feedbackRepository.findAllByAuthor(
            user.orElseThrow(),
            request
        )
    }

    override fun getAverageRecipeRating(recipeId: Long): Double {
        val rating = feedbackRepository.averageOfRatings(recipeId)
        return rating.getOrDefault(-1.0)
    }

    override fun getAverageRecipeAuthorRating(userId: Long): Double {
        val user = userService.findUserById(userId)
        val rating = feedbackRepository.averageOfRatings(user.orElseThrow())
        return rating.getOrDefault(-1.0)
    }

    override fun leaveFeedback(recipeId: Long, author: User, dto: CreateFeedbackDto): Feedback {
        // Don't allow to create feedback if it already was created before
        if (findFeedbackByRecipeAndUser(recipeId, author.id).isPresent) {
            throw EntryAlreadyExistsException("User has already left feedback on this recipe")
        }

        val recipe = recipeService.getRecipeById(recipeId)
        if (recipe.isEmpty) {
            throw NotFoundException("Cannot leave feedback for a recipe that does not exist")
        }

        // TODO: move this check to a validator
        if (dto.rating < 1 || dto.rating > 10) {
            throw RuntimeValidationException("Rating must be an integer in ranges of 1-10")
        }

        val feedbackObj = Feedback()
        feedbackObj.recipe = recipe.orElseThrow()
        feedbackObj.author = author
        feedbackObj.rating = dto.rating
        feedbackObj.content = dto.content
        feedbackObj.dateCreated = Date()
        feedbackRepository.save(feedbackObj)
        return feedbackObj
    }

    override fun getFeedbackById(feedbackId: Long): Optional<Feedback> {
        return feedbackRepository.findById(feedbackId)
    }

    override fun deleteFeedbackById(feedbackId: Long): Boolean {
        val feedback = getFeedbackById(feedbackId)
        if (feedback.isPresent) {
            feedbackRepository.deleteById(feedbackId)
            return true
        }
        return false
    }
}