package vcmsa.projects.petcareapp.UI.ApiMedical

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.petcareapp.Data.Models.API.Medication
import vcmsa.projects.petcareapp.R


//using an adapter so that we can use the recyclerView (Geeksforgeeks, 2025):
class MedicationAdapter(
    private var medications: List<Medication>,
    private val onItemClick: (Medication) -> Unit
) : RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    class MedicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //setting up what each card - each list item - must display (Geeksforgeeks, 2025):
        val nameTextView: TextView = itemView.findViewById(R.id.tvMedicationName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvMedicationDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        //we then infalte the recycleView aka make it display on the page (Geeksforgeeks, 2025):
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medication_api, parent, false) // Make sure this matches your XML filename
        return MedicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val medication = medications[position]

        holder.nameTextView.text = medication.name

        // Handle List<String> description properly
        val descriptionText = when {
            medication.description.isNullOrEmpty() -> "No description available"
            medication.description.size == 1 -> medication.description[0]
            else -> medication.description.joinToString(", ")
        }
        holder.descriptionTextView.text = descriptionText

        holder.itemView.setOnClickListener {
            onItemClick(medication)
        }
    }

    override fun getItemCount(): Int = medications.size

    fun updateData(newMedications: List<Medication>) {
        medications = newMedications
        notifyDataSetChanged()
    }
}
//Reference list:

//Geeksforgeeks. 2025. RecyclerView in Android with Example. [Online]. Available at: https://www.geeksforgeeks.org/android/android-recyclerview/ [Accessed 7 September 2025].