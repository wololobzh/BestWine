package fr.eni.bestwine.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.eni.bestwine.R
import fr.eni.bestwine.dao.FriendDao
import org.jetbrains.anko.startActivity
import fr.eni.bestwine.adapters.FriendAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


/**
 * This class represent the main screen.
 *
 * @author Anthony Cosson
 */
class MainActivity : AppCompatActivity(), AnkoLogger {

    /**
     * Create IHM.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        info("Go in onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Bind the list.
     */
    override fun onResume() {
        info("Go in onResume")
        super.onResume()

        val friends = FriendDao.get(this)

        val adapter = FriendAdapter(this, R.layout.ma_ligne, friends)

        this.list_main.adapter = adapter
    }

    /**
     * Action for button "add"
     *
     * This method use an intent with ANKO.
     */
    fun onClickAdd(view: View) {
        info("Go away !!!")
        startActivity<AddActivity>("anInformation" to 5)
    }
}
