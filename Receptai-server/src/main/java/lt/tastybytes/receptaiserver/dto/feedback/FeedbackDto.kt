package lt.tastybytes.receptaiserver.dto.feedback

import lt.tastybytes.receptaiserver.dto.user.PublicUserDto
import java.util.Date

@JvmRecord
data class FeedbackDto(
    val user: PublicUserDto,
    val content: String,
    val rating: Int,
    val dateCreated: Date
)