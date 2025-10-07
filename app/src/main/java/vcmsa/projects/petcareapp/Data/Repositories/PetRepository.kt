package vcmsa.projects.petcareapp.Data.Repositories

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import vcmsa.projects.petcareapp.Data.Models.PetInfo

class PetRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val petsCollection = db.collection("pets")
    private val usersCollection = db.collection("Users")

    suspend fun addPet(petInfo: PetInfo): Result<Unit> {
        return try {
            //getting the current user (Firebase, 2025):
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                return Result.failure(Exception("User not authenticated"))
            }

            // Generate a unique ID for the pet (Firebase, 2025):
            val petId = petsCollection.document().id

            // Create pet with owner ID and pet ID
            val petWithIds = petInfo.copy(
                ownerId = currentUser.uid,
                petId = petId
            )

            // Add pet to pets collection (Firebase, 2025):
            petsCollection.document(petId).set(petWithIds).await()

            // Also add reference to user's pets subcollection (Firebase, 2025):
            usersCollection.document(currentUser.uid)
                .collection("user_pets")
                .document(petId)
                .set(mapOf("petId" to petId))
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserPets(): Result<List<PetInfo>> {
        return try {
            //getting the current user (Firebase, 2025):
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                return Result.failure(Exception("User not authenticated"))
            }
            //fetching all their pets with a dataSnapshot (Firebase, 2025):
            val querySnapshot = petsCollection
                .whereEqualTo("ownerId", currentUser.uid)
                .get()
                .await()

            val pets = querySnapshot.documents.map { document ->
                document.toObject(PetInfo::class.java)!!
            }

            Result.success(pets)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    //this function hasnt been implemented yet
    suspend fun updatePet(petId: String, updatedPetInfo: PetInfo): Result<Unit> {
        return try {
            //getting the current firebase user (Firebase, 2025):
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                return Result.failure(Exception("User not authenticated"))
            }

            // Verify the pet belongs to current user (Firebase, 2025):
            val petDoc = petsCollection.document(petId).get().await()
            if (petDoc.exists() && petDoc.get("ownerId") == currentUser.uid) {
                petsCollection.document(petId).set(updatedPetInfo.copy(petId = petId, ownerId = currentUser.uid)).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Pet not found or access denied"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

//Reference List:

//Firebase. 2025. Get started with Cloud Firestore. [Online]. Available at: https://firebase.google.com/docs/firestore/quickstart [Accessed 27 September 2025].