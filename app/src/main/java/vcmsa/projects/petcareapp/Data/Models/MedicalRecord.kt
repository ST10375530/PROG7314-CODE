package vcmsa.projects.petcareapp.Data.Models

import java.util.Date

data class MedicalRecord(
    val id: String = "",
    val petId: String = "",
    val allergies: String = "",
    val allergyDate: String = "",
    val treatment: String = "",
    val treatmentDate: String = "",
    val vaccination: String = "",
    val vaccinationDate: String = "",
    val medication: String = "",
    val emergencyContact: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) {
    constructor() : this("", "", "", "", "", "", "", "", "", "", Date(), Date())
}
