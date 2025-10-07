package vcmsa.projects.petcareapp.Data.Models.API

data class DosageCalculationRequest(
    val weight: Double,
    val weightUnit: String = "kg"
)
