package vcmsa.projects.petcareapp.Data.Models.API

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val count: Int? = null,
    val data: T? = null
)