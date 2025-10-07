package vcmsa.projects.petcareapp.Data.Repositories

import vcmsa.projects.petcareapp.Data.Network.ApiClient
import vcmsa.projects.petcareapp.Data.Models.API.Medication
import vcmsa.projects.petcareapp.Data.Models.API.DosageCalculationResponse

class MedicationRepository {

    private val medicationService = ApiClient.medicationService

    suspend fun getAllMedications(): Result<List<Medication>> {
        return try {
            val response = medicationService.getAllMedications()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
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
            val response = medicationService.getMedicationsByName(medicationName)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
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
            val response = medicationService.calculateDosage(medicationName, weight, weightUnit)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true && body.data != null) {
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