package fr.eni.bestwine.activities

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import fr.eni.bestwine.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import fr.eni.bestwine.model.Friend
import fr.eni.bestwine.dao.FriendDao
import android.provider.ContactsContract
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.widget.Toast

/**
 * This class represent the second screen
 *
 * @author Anthony Cosson
 */
class AddActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100

    /**
     * This method allow to get all phone's contacts.
     *
     * This method use an auto closeable object.
     */
    fun getContacts(): ArrayList<Friend> {
        val friends = ArrayList<Friend>()
        contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null).use {
            while (it.moveToNext()) {
                val id = Integer.valueOf(it.getString(it.getColumnIndex(ContactsContract.Contacts._ID)))
                val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val friend = Friend(name,"",id)
                friends.add(friend)
            }
        }
        return friends
    }

    /**
     * This method allow to ask authorization.
     */
    private fun showContacts(): ArrayList<Friend> {

        var resultat = ArrayList<Friend>()

        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            resultat = getContacts()

        }
        return resultat
    }

    /**
     * Call back after user answer about authorization.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts()
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * This method create the IHM.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = showContacts()

        val adapterFriends = ArrayAdapter(this,R.layout.mon_spinner,items)
        val context = this

        verticalLayout {
            val friends = spinner {  adapter = adapterFriends  }
            val etWine = editText()

            val btnApply = button(context.getString(R.string.Apply)) {
                onClick {


                    if (etWine.text.toString().trim { it <= ' ' } == "")
                    {
                        etWine.error = "Wine is required!"
                        etWine.hint = "Please enter a etWine"
                    }
                    else
                    {
                        val friend = friends.selectedItem as Friend
                        friend.wine = etWine.text.toString()

                        alert("Are you sure ?") {
                            yesButton {
                                toast("Saving...")
                                FriendDao.insertOrUpdate(context,friend)
                                finish() }
                            noButton {}
                        }.show()

                    }
                }
            }

            btnApply.textColor = Color.WHITE
            btnApply.backgroundResource = R.color.colorPrimary
            }
        }
    }

