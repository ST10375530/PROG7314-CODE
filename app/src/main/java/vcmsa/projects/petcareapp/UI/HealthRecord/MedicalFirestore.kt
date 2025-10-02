package vcmsa.projects.petcareapp.UI.HealthRecord

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import vcmsa.projects.petcareapp.Data.Models.MedicalRecord

class MedicalFirestore {

    private val db = FirebaseFirestore.getInstance()

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