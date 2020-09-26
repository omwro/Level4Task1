package nl.omererdem.madlevel4task1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
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

        fabAdd.setOnClickListener {
            onAddShoppingItem()
        }
        fabClear.setOnClickListener {
            onClearShoppingItems()
        }
    }

    private fun onAddShoppingItem() {

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
}