package vcmsa.projects.petcareapp.Data.Repositories

import vcmsa.projects.petcareapp.Data.Network.ApiClient
import vcmsa.projects.petcareapp.Data.Models.API.Medication
import vcmsa.projects.petcareapp.Data.Models.API.DosageCalculationResponse

class MedicationRepository {

    private val medicationService = ApiClient.medicationService

    suspend fun getAllMedications(): Result<List<Medication>> {
        return try {
            //calling the retrofit method setup in MedicationServices
            val response = medicationService.getAllMedications()
            //checking for a successful response (Kotlin, 2025):
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
                    //taking the data response and returning it
                    Result.success(body.data ?: emptyList())
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to load medications"))
                }
            } else {
                Result.failure(Exception("Network error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchMedications(medicationName: String): Result<List<Medication>> {
        return try {
            //calling the retrofit method setup in MedicationServices
            val response = medicationService.getMedicationsByName(medicationName)
            //checking for a successful response (Kotlin, 2025):
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
                    //taking the data response and returning it
                    Result.success(body.data ?: emptyList())
                } else {
                    Result.failure(Exception(body?.message ?: "No medications found"))
                }
            } else {
                Result.failure(Exception("Search failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun calculateDosage(
        medicationName: String,
        weight: Double,
        weightUnit: String = "kg"
    ): Result<DosageCalculationResponse> {
        return try {
            //calling the retrofit method setup in MedicationServices
            val response = medicationService.calculateDosage(medicationName, weight, weightUnit)
            //checking for a successful response (Kotlin, 2025):
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true && body.data != null) {
                    //taking the data response and returning it
                    Result.success(body.data)
                } else {
                    Result.failure(Exception(body?.message ?: "Dosage calculation failed"))
                }
            } else {
                Result.failure(Exception("Calculation error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

//Reference list:

// Kotlin. 2025. Result. [Online]. Available at: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-result/ [Accessed 6 October 2025].