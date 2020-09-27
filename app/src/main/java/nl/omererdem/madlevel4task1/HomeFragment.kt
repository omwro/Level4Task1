package nl.omererdem.madlevel4task1

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_add_shopping_item.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    private lateinit var shoppingItemRepository: ShoppingItemRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val shoppingItems = arrayListOf<ShoppingItem>()
    private val shoppingItemAdapter = ShoppingItemAdapter(shoppingItems)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoppingItemRepository = ShoppingItemRepository(requireContext())
        getShoppingListFromDatabase()

        createItemTouchHelper().attachToRecyclerView(rvShippingList)

        fabAdd.setOnClickListener {
            showAddShoppingItemDialog()
        }
        fabClear.setOnClickListener {
            onClearShoppingItems()
        }
    }

    private fun showAddShoppingItemDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.add_shopping_item_dialog_title))

        val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_shopping_item, null)
        val shoppingItemName = dialogLayout.etDialogName.text.toString()
        val shoppingItemAmount = dialogLayout.etDialogNumber.text.toString()

        builder.setView(dialogLayout)
        builder.setPositiveButton(R.string.ok) {
            _: DialogInterface, _: Int ->
            if (shoppingItemName.isNotBlank() && shoppingItemAmount.isNotBlank()) {
                addShoppingItem(shoppingItemName, shoppingItemAmount.toInt())
            } else {
                Toast.makeText(activity, "Please fill in the fields correct", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addShoppingItem(name: String, amount: Int) {
        mainScope.launch {
            val shoppingItem = ShoppingItem(amount, name)
            withContext(Dispatchers.IO) {
                shoppingItemRepository.insertShoppingItem(shoppingItem)
            }
            getShoppingListFromDatabase()
        }
    }


    private fun onClearShoppingItems() {

    }

    private fun getShoppingListFromDatabase() {
        mainScope.launch {
            val shoppingList = withContext(Dispatchers.IO) {
                shoppingItemRepository.getAllShoppingItems()
            }
            this@HomeFragment.shoppingItems.clear()
            this@HomeFragment.shoppingItems.addAll(shoppingList)
            this@HomeFragment.shoppingItemAdapter.notifyDataSetChanged()
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val shoppingItemToDelete = shoppingItems[position]

                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        shoppingItemRepository.deleteShoppingItem(shoppingItemToDelete)
                    }
                    getShoppingListFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}