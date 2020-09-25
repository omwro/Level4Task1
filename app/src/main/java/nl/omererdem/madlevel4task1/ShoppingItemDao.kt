package nl.omererdem.madlevel4task1

import androidx.room.*

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM shoppingitemTable")
    suspend fun getAllShoppingItmes(): List<ShoppingItem>

    @Insert
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("DELETE FROM shoppingitemTable")
    suspend fun deleteAllShoppingItems()
}