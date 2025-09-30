package vcmsa.projects.petcareapp.UI.HealthRecord

import vcmsa.projects.petcareapp.R



import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VaccinationAdapter(private val vaccinations: MutableList<Vaccination>) :
    RecyclerView.Adapter<VaccinationAdapter.VaccinationViewHolder>() {

    class VaccinationViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.vaccinationName)
        val date: TextView = itemView.findViewById(R.id.vaccinationDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccinationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vaccination, parent, false)
        return VaccinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: VaccinationViewHolder, position: Int) {
        val vaccination = vaccinations[position]
        holder.name.text = vaccination.name
        holder.date.text = "Date: ${vaccination.date}"
    }

    override fun getItemCount(): Int = vaccinations.size

    // Add these missing methods
    fun addVaccination(vaccination: Vaccination) {
        vaccinations.add(vaccination)
        notifyItemInserted(vaccinations.size - 1)
    }

    fun updateVaccinations(newVaccinations: MutableList<Vaccination>) {
        vaccinations.clear()
        vaccinations.addAll(newVaccinations)
        notifyDataSetChanged()
    }

    fun getVaccinations(): List<Vaccination> = vaccinations.toList()
}