package hu.bme.aut.android.shoppinglist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.data.ShoppingItem
import hu.bme.aut.android.shoppinglist.databinding.ItemShoppingListBinding

class ShoppingAdapter(private val listener: ShoppingItemClickListener) :
    RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    private val items = mutableListOf<ShoppingItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShoppingViewHolder(
        ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    /** Interface-en keresztűl jelezzük az alkalmazás többi részének, hogy változás történt a lista egyik elemén. **/
    interface ShoppingItemClickListener {
        fun onItemChanged(item: ShoppingItem)
        fun onItemDeleted(item: ShoppingItem)
    }


    /** ViewHolder belső osztály **/
    inner class ShoppingViewHolder(val binding: ItemShoppingListBinding) : RecyclerView.ViewHolder(binding.root)


    /**FELÜLÍRT OSZTÁLYOK**/

    /** A megjelenítendő view és ViewHolder összekötése **/
    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val shoppingItem = items[position]

        holder.binding.ivIcon.setImageResource(getImageResource(shoppingItem.category))
        holder.binding.cbIsBought.isChecked = shoppingItem.isBought
        holder.binding.tvName.text = shoppingItem.name
        holder.binding.tvDescription.text = shoppingItem.description
        holder.binding.tvCategory.text = shoppingItem.category.name
        holder.binding.tvPrice.text = "${shoppingItem.estimatedPrice} Ft"

        holder.binding.cbIsBought.setOnCheckedChangeListener { buttonView, isChecked ->
            shoppingItem.isBought = isChecked
            listener.onItemChanged(shoppingItem)
        }

        /** Remove gomb listener. Eltávolítja a gombot a RecyclerViewből **/
        holder.binding.ibRemove.setOnClickListener{
            removeItem(shoppingItem)
            listener.onItemDeleted(shoppingItem)
        }

    }

    /** Az elemek számát adja vissza **/
    override fun getItemCount(): Int = items.size

    /** Enumhoz társítja a megfelelő képeket. **/
    @DrawableRes()
    private fun getImageResource(category: ShoppingItem.Category): Int {
        return when (category) {
            ShoppingItem.Category.FOOD -> R.drawable.groceries
            ShoppingItem.Category.ELECTRONIC -> R.drawable.lightning
            ShoppingItem.Category.BOOK -> R.drawable.open_book
        }
    }



    /** 1-1 elem hozzá adása és a lista frissítése. **/
    fun addItem(item: ShoppingItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<ShoppingItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    fun removeItem(item: ShoppingItem) {
        items.remove(item)
        notifyDataSetChanged()
    }

}