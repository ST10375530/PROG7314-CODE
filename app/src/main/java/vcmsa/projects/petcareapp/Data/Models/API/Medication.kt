package vcmsa.projects.petcareapp.Data.Models.API

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
//a serilization technique that helps with odd data types for the constructor (Developers, 2024):
@Parcelize
data class Medication(
    val _id: String? = null,
    val name: String,
    val dosage: String? = null,
    val description: List<String>? = null,
    val conditionsTreated: List<String>? = null,
    val dosageMin: Double? = null,
    val dosageMax: Double? = null,
    val dosageUnit: String? = null,
    val frequency: String? = null,
    val warnings: List<String>? = null,
    val prescriptionRequired: Boolean? = null
) : Parcelable

//Developers. 2024. https://developer.android.com/kotlin/parcelize [Accessed 7 October 2025].