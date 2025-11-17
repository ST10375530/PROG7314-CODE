package vcmsa.projects.petcareapp.Data.Models

data class User(
    val uid: String ="",
    val fullname: String? ="",
    val email:String? ="",
    val passwordHash: String? = ""
)