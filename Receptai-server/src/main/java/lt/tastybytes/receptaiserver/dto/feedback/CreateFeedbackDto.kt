package lt.tastybytes.receptaiserver.dto.feedback

data class CreateFeedbackDto(
    val content: String,
    val rating: Int
)