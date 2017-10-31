package fr.eni.bestwine.dao.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import fr.eni.bestwine.dao.contracts.FriendTableContract
import org.jetbrains.anko.db.*

/**
 * This class allow to create end update database and to give an object who represent database.
 *
 * @author Anthony Cosson
 */
class BddHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 3) {
    companion object {
        private var instance: BddHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): BddHelper {
            if (instance == null) {
                instance = BddHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    /**
     * This method create the database.
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
                FriendTableContract.NAME,
                false,
                FriendTableContract.COL_ID to INTEGER + PRIMARY_KEY,
                FriendTableContract.COL_NAME to TEXT,
                FriendTableContract.COL_WINE to TEXT)
    }

    /**
     * This method update database.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FriendTableContract.NAME, true)
        onCreate(db);
    }
}

/**
 * Function extension on Context class for simplify bdd object access.
 */
val Context.database: SQLiteDatabase
    get() = BddHelper.getInstance(applicationContext).writableDatabase




