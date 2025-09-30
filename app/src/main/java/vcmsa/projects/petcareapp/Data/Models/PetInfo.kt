package vcmsa.projects.petcareapp.Data.Models


data class PetInfo(
    val name: String = "",
    val petType: String = "",
    val age: Int = 0,
    val colour: String = "",
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val ownerId: String = "",
    val petId: String = ""
)