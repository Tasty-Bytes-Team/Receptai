package lt.tastybytes.receptaiserver.dto.feedback

@JvmRecord
data class CreateFeedbackDto(
    val content: String,
    val rating: Int
)