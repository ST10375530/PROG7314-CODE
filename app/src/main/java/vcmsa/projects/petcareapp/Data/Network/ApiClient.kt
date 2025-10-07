package vcmsa.projects.petcareapp.Data.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vcmsa.projects.petcareapp.Data.Services.MedicationService

object ApiClient {
    //Our base URL for the API
    private const val BASE_URL = "https://petnest-api.onrender.com/api/"
    //setting up the retrofit config (Retrofit, 2025):
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create the medication service (we do this for all serives that have routes)
    val medicationService: MedicationService = retrofit.create(MedicationService::class.java)
}

//Reference list:

//Retrofit. 2025. Configuration. [online]. Avaliable at: https://square.github.io/retrofit/configuration/ [Accessed 7 October 2025].