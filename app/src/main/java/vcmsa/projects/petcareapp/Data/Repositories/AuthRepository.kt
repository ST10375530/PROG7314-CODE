package vcmsa.projects.petcareapp.Data.Repositories

import android.app.Activity
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import vcmsa.projects.petcareapp.Data.Models.User
import kotlin.toString


class AuthRepository() {
    //vars for firebase
     val firebaseAuth = FirebaseAuth.getInstance()
        val db = Firebase.firestore
    val users = db.collection("Users")
    // Register user with email and password
    suspend fun registerUser(fullName: String, email: String, password: String): Result<Unit> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user

            user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()
            )?.await()

            // Create new user object
            val newUser = User(
                uID = user!!.uid,
                fullname = fullName,
                email = email
            )

            // Store the user in Firestore - CORRECTED
            users.document(user.uid).set(newUser).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Sign in user with email and password
    suspend fun signInUserFirebase(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Authenticate with Google ID token
    // Sign in/up with Google
    suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()

            val user = authResult.user
            if (user != null) {
                // Check if this is a new user (first-time sign-in)
                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false

                if (isNewUser) {
                    // First-time sign-up with Google
                    val displayName = user.displayName ?: user.email?.substringBefore("@") ?: "User"

                    // Update profile if display name is empty
                    if (user.displayName.isNullOrEmpty()) {
                        user.updateProfile(
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build()
                        ).await()
                    }

                    // Create and store user in Firestore
                    val newUser = User(
                        uID= user.uid,
                        fullname = displayName,
                        email = user.email ?: ""
                    )

                    users.document(user.uid).set(newUser).await()
                }
                // Existing users will already have their data in Firestore
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
