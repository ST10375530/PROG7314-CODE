package vcmsa.projects.petcareapp.Data.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceInfo(
    val id: String = "",
    val name: String = "",
    val type: PlaceType = PlaceType.VET,
    val qualification: String = "",
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val experience: String = "",
    val distance: String = "0 km",
    val price: String = "R0",
    val status: String = "CLOSED",
    val timings: String = "",
    val latitude: Double = -26.2041,
    val longitude: Double = 28.0473,
    val imageResId: Int = 0,
    val description: String = ""
) : Parcelable

enum class PlaceType {
    VET,
    PARK,
    PET_STORE
}