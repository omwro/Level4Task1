package nl.omererdem.madlevel4task1

import android.content.Context

class ShoppingItemRepository(context: Context) {
    private val shoppingItemDao: ShoppingItemDao

    init {
        val database = ShoppingListRoomDatabase.getDatabase(context)
        shoppingItemDao = database!!.shoppingItemDao()
    }

    suspend fun getAllShoppingItems(): List<ShoppingItem> {
        return shoppingItemDao.getAllShoppingItmes()
    }

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItemDao.insertShoppingItem(shoppingItem)
    }

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItemDao.deleteShoppingItem(shoppingItem)
    }

    suspend fun deleteAllShoppingItems() {
        shoppingItemDao.deleteAllShoppingItems()
    }
}