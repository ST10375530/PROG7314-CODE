package vcmsa.projects.petcareapp.Data.Repositories

import android.util.Log
import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.persistentCacheSettings
import kotlinx.coroutines.tasks.await
import vcmsa.projects.petcareapp.Data.Models.User



class AuthRepository() {
    //vars for firebase
     val firebaseAuth = FirebaseAuth.getInstance()
    //made it so the firestore can work offline with caching (Firebase, 2025):
    val db = Firebase.firestore.apply {
        firestoreSettings  = firestoreSettings {
            //using the persistent caching setting (Firebase, 2025):
            setLocalCacheSettings(persistentCacheSettings { /* ... */ })
        }
    }
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
                uid = user!!.uid,
                fullname = fullName,
                email = email,
                passwordHash = hashPassword(password).toString()
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
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val uID = firebaseAuth.currentUser?.uid ?: throw Exception("User ID is null")
            // Firestore will automatically cache this fresh data
            val test = users.document(uID).get().await()
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
                    //first-time sign-up with Google (Firebase, 2025):
                    val displayName = user.displayName ?: user.email?.substringBefore("@") ?: "User"

                    //update profile if display name is empty
                    if (user.displayName.isNullOrEmpty()) {
                        user.updateProfile(
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build()
                        ).await()
                    }

                    //create and store user (Firebase, 2025):
                    val newUser = User(
                        uid= user.uid,
                        fullname = displayName,
                        email = user.email ?: "",
                        passwordHash = ""
                    )
                   //storing with firestore (Firebase, 2025):
                    users.document(user.uid).set(newUser).await()
                }
                //existing users will already have their data in Firestore
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
                logUser.email,
                passwordHash = ""
            )
            return user
        }
        else{
            return null
        }
    }
    fun verifyPassword(password: String, hashed: String?): Boolean {
        //null check for the hash
        if (hashed.isNullOrBlank()) return false

        return try {
            val result = BCrypt.verifyer().verify(password.toCharArray(), hashed)
            result.verified
        } catch (e: Exception) {
            Log.e("PasswordVerify", "Error verifying password", e)
            false
        }
    }
    suspend fun offlineLogin(email: String, password: String): Boolean {
        return try {
            val snapshot = users
                .whereEqualTo("email", email)
                .get(Source.CACHE)
                .await()

            if (snapshot.isEmpty) {
                Log.d("OfflineLogin", "No user found in cache for email: $email")
                return false
            }
            val document = snapshot.documents.first()
            Log.d("OfflineLogin", "Document data: ${document.data}")
            Log.d("OfflineLogin", "Document ID: ${document.id}")

            // Try to convert to User object
            val user = document.toObject(User::class.java)
            if (user != null && verifyPassword(password, user.passwordHash)) {
                Log.d("OfflineLogin", "User found, hash present: ${!user.passwordHash.isNullOrBlank()}")
                true
            } else {
                Log.d("OfflineLogin", "User object is null")
                false
            }
        } catch (e: Exception) {
            Log.e("OfflineLogin", "Error during offline login", e)
            false
        }
    }
    fun hashPassword(password: String?): String? {
        if(password != null) {
            return BCrypt.withDefaults().hashToString(12, password.toCharArray())
        }
        else{
            return ""
        }
    }

}

//reference list:

//Developers. 2025. Manage network usage. [Online]. Available at: https://developer.android.com/develop/connectivity/network-ops/managing [Accessed 15 November 2025].

//Firebase. 2025. Access data offline. [Online]. Available at: https://firebase.google.com/docs/firestore/manage-data/enable-offline [Accessed 14 November 2025].

//Firebase. 2025. Authenticate with Google on Android. [Online]. Available at: https://firebase.google.com/docs/auth/android/google-signin [Accessed 27 September 2025].

//Firebase. 2025. Get started with Cloud Firestore. [Online]. Available at: https://firebase.google.com/docs/firestore/quickstart [Accessed 27 September 2025].

//Firebase. 2025. Manage Users in Firebase. [Online]. Available at: https://firebase.google.com/docs/auth/android/manage-users#get_a_users_profile [Accessed 27 September 2025]
