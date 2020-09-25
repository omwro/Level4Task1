package nl.omererdem.madlevel4task1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_shopping.view.*

class ShoppingItemAdapter (private val shoppingItems: List<ShoppingItem>) : RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(shoppingItem: ShoppingItem) {
            itemView.tvAmount.text = shoppingItem.amount.toString()
            itemView.tvName.text = shoppingItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_shopping, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShoppingItemAdapter.ViewHolder, position: Int) {
        holder.databind(shoppingItems[position])
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }

}