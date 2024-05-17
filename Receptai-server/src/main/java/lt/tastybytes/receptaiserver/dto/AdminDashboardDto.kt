package lt.tastybytes.receptaiserver.dto

@JvmRecord
data class AdminDashboardDto(
    val totalRecipeCount: Number,
    val totalReviewCount: Number,
    val totalUserCount: Number,
)