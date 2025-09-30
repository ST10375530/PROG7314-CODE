package vcmsa.projects.petcareapp.UI.HealthRecord


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.petcareapp.R

class TreatmentAdapter(private val treatments: MutableList<Treatment>) :
    RecyclerView.Adapter<TreatmentAdapter.TreatmentViewHolder>() {

    class TreatmentViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.treatmentName)
        val date: TextView = itemView.findViewById(R.id.treatmentDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreatmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_treatment, parent, false)
        return TreatmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: TreatmentViewHolder, position: Int) {
        val treatment = treatments[position]
        holder.name.text = treatment.name
        holder.date.text = "Date: ${treatment.date}"
    }

    override fun getItemCount(): Int = treatments.size

    fun addTreatment(treatment: Treatment) {
        treatments.add(treatment)
        notifyItemInserted(treatments.size - 1)
    }

    fun updateTreatments(newTreatments: MutableList<Treatment>) {
        treatments.clear()
        treatments.addAll(newTreatments)
        notifyDataSetChanged()
    }

    fun getTreatments(): List<Treatment> = treatments.toList()
}