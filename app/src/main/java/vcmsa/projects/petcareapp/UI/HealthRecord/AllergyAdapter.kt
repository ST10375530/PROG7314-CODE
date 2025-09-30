package vcmsa.projects.petcareapp.UI.HealthRecord

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.petcareapp.R

class AllergyAdapter(private val allergies: MutableList<Allergy>) :
    RecyclerView.Adapter<AllergyAdapter.AllergyViewHolder>() {

    class AllergyViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.allergyName)
        val date: TextView = itemView.findViewById(R.id.allergyDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllergyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_allergy, parent, false)
        return AllergyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllergyViewHolder, position: Int) {
        val allergy = allergies[position]
        holder.name.text = allergy.name
        holder.date.text = "Diagnosed: ${allergy.date}"
    }

    override fun getItemCount(): Int = allergies.size

    fun addAllergy(allergy: Allergy) {
        allergies.add(allergy)
        notifyItemInserted(allergies.size - 1)
    }

    fun updateAllergies(newAllergies: MutableList<Allergy>) {
        allergies.clear()
        allergies.addAll(newAllergies)
        notifyDataSetChanged()
    }

    fun getAllergies(): List<Allergy> = allergies.toList()
}
