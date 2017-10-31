package fr.eni.bestwine.dao


import android.content.Context
import fr.eni.bestwine.dao.contracts.FriendTableContract
import fr.eni.bestwine.dao.utils.database
import fr.eni.bestwine.model.Friend
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

/**
 * Dao for [fr.eni.bestwine.model.Friend].
 *
 * This dao use ANKO extension.
 *
 * @author Anthony Cosson
 */
object  FriendDao
{

    /**
     * If item friend don't exist in table Friends -> Insert else Update.
     */
    fun insertOrUpdate(context: Context,item:Friend)
    {
        if(insert(context,item) == -1L)
        {
            update(context,item)
        }
    }

    /**
     * Get all friends in table Friends.
     *
     * @return A list of objects [fr.eni.bestwine.model.Friend]
     */
    fun get(context: Context) : List<Friend>
    {
        return context.database.select(FriendTableContract.NAME).parseList(parser)
    }

    /**
     * Insert a friend in table Friends.
     *
     * @return id of friend inserted.
     */
    private fun insert(context: Context,item:Friend) =
         context.database.insert(FriendTableContract.NAME,

                FriendTableContract.COL_NAME to item.name,
                FriendTableContract.COL_WINE to item.wine,
                 FriendTableContract.COL_ID to item.id)

    /**
     * Update a friend in table Friends.
     */
    private fun update(context: Context,item:Friend) =
        context.database.update(FriendTableContract.NAME,
                FriendTableContract.COL_NAME to item.name,
                FriendTableContract.COL_WINE to item.wine)
                .whereSimple("${FriendTableContract.COL_ID} = ?", item.id.toString())
                .exec()

    /**
     * Object builder : transform table row in object.
     */
    private val parser = rowParser {
        id: Int, name: String, wine: String ->
        Friend(name, wine, id)
    }
}