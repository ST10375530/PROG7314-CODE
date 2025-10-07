package vcmsa.projects.petcareapp.Data.Services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vcmsa.projects.petcareapp.Data.Models.API.ApiResponse
import vcmsa.projects.petcareapp.Data.Models.API.DosageCalculationResponse
import vcmsa.projects.petcareapp.Data.Models.API.Medication


interface MedicationService {
    //routes for the API
    // Get all medications (Retrofit, 2025):
    @GET("medication")
    suspend fun getAllMedications(): Response<ApiResponse<List<Medication>>>

    // Get medications by partial name (Retrofit, 2025):
    @GET("medication/{medicationName}")
    suspend fun getMedicationsByName(
        @Path("medicationName") medicationName: String
    ): Response<ApiResponse<List<Medication>>>

    // Calculate dosage for specific medication (Retrofit, 2025):
    @GET("medication/{medicationName}/dosage")
    suspend fun calculateDosage(
        @Path("medicationName") medicationName: String,
        @Query("weight") weight: Double,
        @Query("weightUnit") weightUnit: String = "kg"
    ): Response<ApiResponse<DosageCalculationResponse>>
}

//Reference list:

//Retrofit. 2025. Introduction. [Online]. Available at: https://square.github.io/retrofit/ [Accessed 6 October 2025].