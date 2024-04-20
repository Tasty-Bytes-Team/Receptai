package lt.tastybytes.receptaiserver.service.impl

import lt.tastybytes.receptaiserver.dto.feedback.CreateFeedbackDto
import lt.tastybytes.receptaiserver.exception.EntryAlreadyExistsException
import lt.tastybytes.receptaiserver.exception.NotFoundException
import lt.tastybytes.receptaiserver.exception.RuntimeValidationException
import lt.tastybytes.receptaiserver.model.Feedback
import lt.tastybytes.receptaiserver.model.user.User
import lt.tastybytes.receptaiserver.repository.FeedbackRepository
import lt.tastybytes.receptaiserver.service.FeedbackService
import lt.tastybytes.receptaiserver.service.RecipeService
import lt.tastybytes.receptaiserver.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

const val FEEDBACK_PER_PAGE = 20

@Service
class FeedbackServiceImpl(
    private var feedbackRepository: FeedbackRepository,
    private var recipeService: RecipeService,
    private var userService: UserService
) : FeedbackService {

    override fun getFeedbackByRecipe(recipeId: Long, pageNumber: Long): Page<Feedback> {
        val recipe = recipeService.getRecipeById(recipeId)
        val request = PageRequest.of(pageNumber.toInt(), FEEDBACK_PER_PAGE)
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

    override fun getAverageRecipeRating(recipeId: Long) {
        val recipe = recipeService.getRecipeById(recipeId)
        TODO("Not yet implemented")
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
}