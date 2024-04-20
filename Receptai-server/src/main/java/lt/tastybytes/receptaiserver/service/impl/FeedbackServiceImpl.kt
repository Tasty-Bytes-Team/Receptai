package lt.tastybytes.receptaiserver.service.impl

import lt.tastybytes.receptaiserver.dto.feedback.CreateFeedbackDto
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
        TODO("Not yet implemented")
    }

    override fun leaveFeedback(recipeId: Long, author: User, dto: CreateFeedbackDto): Feedback {
        TODO("Not yet implemented")
    }
}