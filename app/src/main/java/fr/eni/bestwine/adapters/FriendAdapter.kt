package fr.eni.bestwine.adapters

import android.content.Context
import android.widget.ArrayAdapter
import fr.eni.bestwine.model.Friend
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.ma_ligne.view.*


/**
 * Adapter for friends list.
 *
 * @author Anthony Cosson
 */
class FriendAdapter(context: Context?, resource: Int, objects: List<Friend>) : ArrayAdapter<Friend>(context, resource, objects) {
    private var inflater: LayoutInflater? = null
    private var maResource: Int = 0

    init
    {
        inflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        maResource = resource
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val result = convertView ?: inflater!!.inflate(maResource, parent, false)

        val item = getItem(position)

        result?.tv_name?.text = item.name
        result?.tv_wine?.text = item.wine

        return result
    }
}
