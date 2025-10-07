package vcmsa.projects.petcareapp.Data.Models.API

data class DosageCalculationResponse(
    val medication: String,
    val animalWeight: AnimalWeight,
    val calculatedDosage: CalculatedDosage,
    val warnings: List<String>?,
    val prescriptionRequired: Boolean?
)