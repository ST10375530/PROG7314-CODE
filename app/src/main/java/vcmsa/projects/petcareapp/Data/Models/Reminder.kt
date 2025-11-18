package vcmsa.projects.petcareapp.Data.Models

data class Reminder(
        val id: Int,
        val type: String,
        val frequency: String,
        val petName: String,
        val notes: String,
        val hour: Int,
        val minute: Int
)