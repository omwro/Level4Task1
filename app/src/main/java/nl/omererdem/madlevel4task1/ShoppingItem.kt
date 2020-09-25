package nl.omererdem.madlevel4task1

import androidx.room.*

@Entity(tableName = "shoppingitemTable")
data class ShoppingItem (
    @ColumnInfo(name="amount")
    var amount: Int,
    @ColumnInfo(name="name")
    var name: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long? = null
)