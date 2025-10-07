package vcmsa.projects.petcareapp.Data.Models.API

data class CalculatedDosage(
    val min: String,
    val max: String,
    val frequency: String?,
    val dosagePerKg: DosagePerKg
)