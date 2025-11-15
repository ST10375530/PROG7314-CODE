package vcmsa.projects.petcareapp.UI.HealthRecord

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.persistentCacheSettings
import vcmsa.projects.petcareapp.Data.Models.MedicalRecord

class MedicalFirestore {

    //made it so the firestore can work offline with caching (Firebase, 2025):
    private val db = Firebase.firestore.apply {
        firestoreSettings  = firestoreSettings {
            //using the persistent caching setting (Firebase, 2025):
            setLocalCacheSettings(persistentCacheSettings { /* ... */ })
        }
    }

    fun addMedicalRecord(medicalRecord: MedicalRecord): Task<Void> {
        return db.collection("medicalRecords")
            .document(medicalRecord.id)
            .set(medicalRecord)
    }

    fun getMedicalRecordsByPetId(petId: String): Query {
        return db.collection("medicalRecords")
            .whereEqualTo("petId", petId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
    }

    fun updateMedicalRecord(recordId: String, updates: Map<String, Any>): Task<Void> {
        val updatesWithTimestamp = updates.toMutableMap().apply {
            put("updatedAt", com.google.firebase.Timestamp.now())
        }
        return db.collection("medicalRecords")
            .document(recordId)
            .update(updatesWithTimestamp)
    }

    fun deleteMedicalRecord(recordId: String): Task<Void> {
        return db.collection("medicalRecords")
            .document(recordId)
            .delete()
    }
}