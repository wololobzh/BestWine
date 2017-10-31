package fr.eni.bestwine.model

/**
 * Kdoc example
 *
 * This class represent a friend.
 *
 * Use [fr.eni.bestwine.dao.FriendDao] for work with database.
 *
 * @param name Friend's name
 * @param wine Friend's favorite wine
 * @param id Friend's id
 *
 * @author Anthony Cosson
 */
data class Friend (val name: String,var wine:String="",val id:Int = 0) {
    override fun toString(): String = name
}


