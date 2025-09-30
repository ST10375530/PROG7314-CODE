package vcmsa.projects.petcareapp.UI.HealthRecord

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.petcareapp.R

class MedicationAdapter(private val medications: MutableList<Medication>) :
    RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    class MedicationViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.medicationName)
        val date: TextView = itemView.findViewById(R.id.medicationDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medication, parent, false)
        return MedicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val medication = medications[position]
        holder.name.text = medication.name
        holder.date.text = "Prescribed: ${medication.date}"
    }

    override fun getItemCount(): Int = medications.size

    fun addMedication(medication: Medication) {
        medications.add(medication)
        notifyItemInserted(medications.size - 1)
    }

    fun updateMedications(newMedications: MutableList<Medication>) {
        medications.clear()
        medications.addAll(newMedications)
        notifyDataSetChanged()
    }

    fun getMedications(): List<Medication> = medications.toList()
}