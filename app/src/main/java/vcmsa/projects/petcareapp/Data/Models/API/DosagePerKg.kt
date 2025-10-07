package vcmsa.projects.petcareapp.Data.Models.API

data class DosagePerKg(
    val min: Double,
    val max: Double,
    val unit: String?
)