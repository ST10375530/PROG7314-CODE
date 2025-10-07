package vcmsa.projects.petcareapp.Data.Models.API

data class Medication(
    val _id: String? = null,
    val name: String,
    val dosage: String? = null,
    val description: String? = null,
    val dosageMin: Double? = null,
    val dosageMax: Double? = null,
    val dosageUnit: String? = null,
    val frequency: String? = null,
    val warnings: List<String>? = null,
    val prescriptionRequired: Boolean? = null
)