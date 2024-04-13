package lt.tastybytes.receptaiserver.dto.feedback

import lt.tastybytes.receptaiserver.dto.user.PublicUserDto

@JvmRecord
data class FeedbackDto(
    val user: PublicUserDto,
    val content: String?,
    val rating: Number
)