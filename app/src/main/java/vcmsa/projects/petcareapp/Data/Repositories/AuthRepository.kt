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
    // Register user with email and password (Firebase, 2025)
    suspend fun registerUser(fullName: String, email: String, password: String): Result<Unit> {
        return try {
            //getting the response from firebase (firebase, 2025):
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            //fetching user data from firebase
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

            // Store the user in Firestore  (Firebase, 2025):
            users.document(user.uid).set(newUser).await()
            //sending verification email
            user.sendEmailVerification()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Sign in user with email and password
    suspend fun signInUserFirebase(email: String, password: String): Result<Unit> {
        return try {
            //Signing in with firebase auth (Firebase, 2025):
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Authenticate with Google ID token
    // Sign in/up with Google (Firebase
    suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        return try {
            //setting up the google + firebase auth providers (Firebase, 2025):
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()

            val user = authResult.user
            if (user != null) {
                // Check if this is a new user (first-time sign-in)
                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
                //sending verification email
                user.sendEmailVerification()
                if (isNewUser) {
                    // First-time sign-up with Google (Firebase, 2025):
                    val displayName = user.displayName ?: user.email?.substringBefore("@") ?: "User"

                    // Update profile if display name is empty
                    if (user.displayName.isNullOrEmpty()) {
                        user.updateProfile(
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build()
                        ).await()
                    }

                    // Create and store user (Firebase, 2025):
                    val newUser = User(
                        uID= user.uid,
                        fullname = displayName,
                        email = user.email ?: ""
                    )
                   //storing with firestore (Firebase, 2025):
                    users.document(user.uid).set(newUser).await()
                }
                // Existing users will already have their data in Firestore
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //fun to get current user (Firebase, 2025):
    fun getCurrentUser(): User?
    {
        val logUser = firebaseAuth.currentUser
        if(logUser != null) {
            val user = User(
                logUser.uid,
                logUser.displayName ,
                logUser.email
            )
            return user
        }
        else{
            return null
        }
    }
}

//reference list:

//Firebase. 2025. Authenticate with Google on Android. [Online]. Available at: https://firebase.google.com/docs/auth/android/google-signin [Accessed 27 September 2025].

//Firebase. 2025. Get started with Cloud Firestore. [Online]. Available at: https://firebase.google.com/docs/firestore/quickstart [Accessed 27 September 2025].

//Firebase. 2025. Manage Users in Firebase. [Online]. Available at: https://firebase.google.com/docs/auth/android/manage-users#get_a_users_profile [Accessed 27 September 2025]
