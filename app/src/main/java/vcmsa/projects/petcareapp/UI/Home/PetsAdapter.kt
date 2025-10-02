import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.petcareapp.Data.Models.PetInfo
import vcmsa.projects.petcareapp.R

class PetsAdapter(
    private val onPetClick: (PetInfo) -> Unit = {}
) : RecyclerView.Adapter<PetsAdapter.PetViewHolder>() {

    private var pets: List<PetInfo> = emptyList()

    class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPetPhoto: ImageView = itemView.findViewById(R.id.ivPetPhoto)
        val tvPetName: TextView = itemView.findViewById(R.id.tvPetName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pet, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = pets[position]
        holder.tvPetName.text = pet.name
        // Set image and background based on your PetInfo properties
        holder.ivPetPhoto.setImageResource(R.drawable.dog1)
        holder.ivPetPhoto.setBackgroundResource(R.drawable.circle_pet)

        holder.itemView.setOnClickListener {
            onPetClick(pet)
        }
    }

    override fun getItemCount() = pets.size

    fun submitList(newPets: List<PetInfo>) {
        pets = newPets
        notifyDataSetChanged()
    }
}