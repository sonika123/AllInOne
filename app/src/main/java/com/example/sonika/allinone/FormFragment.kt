package com.example.sonika.allinone

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.*
import android.provider.ContactsContract
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import butterknife.BindView
import butterknife.OnClick
import com.example.sonika.allinone.ApiInterface.Companion.client
import com.example.sonika.allinone.ApiInterface.Companion.retrofit
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import kotlinx.android.synthetic.main.fragment_currency.*
import kotlinx.android.synthetic.main.fragment_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FormFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FormFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

//contacts import

class FormFragment : BaseFragment() {


    override var layoutInt: Int
        get() = R.layout.fragment_form
        set(value) {}


    @NotEmpty
    @BindView(R.id.text_input_name)
    lateinit var name_input: TextInputEditText

    @NotEmpty
    @BindView(R.id.text_input_contact_number)
    lateinit var contact_number: TextInputEditText

    @OnClick(R.id.btn_ok)
    fun submit() {
        validate()



    }

    override fun onValidationSucceeded() {

        activity?.toastmessage("hello ok is clicked")
        openFragment(CurrencyFragment())



    }

    @OnClick(R.id.btn_import_contact)
    fun importContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, Constants.PICK_CONTACT)

    }

    @OnClick(R.id.imageView_profilepicture)
    fun profilePicture() {
        imageView_profilepictureOperation()
    }

    @OnClick(R.id.btn_logout)
    fun logout() {
        val sp = (context as AppCompatActivity).getSharedPreferences("USER_LOGIN", 0)
        val editor = sp.edit()
        editor.clear()
        editor.apply()

        openFragment(LoginFragment())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle("Profile")
        val sp = (context as AppCompatActivity).getSharedPreferences("USER_LOGIN", 0)
        sp.edit().putString("loggedIn", "loggedIn").apply()
//        load spinner with retrofit data
        spinnerOperation()

//confirm permissions
        accessPermissionForAll()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.PICK_CONTACT) {

            if(resultCode == Activity.RESULT_OK) {
                activity?.toastmessage("okay")

                val contactData = data!!.data
                val c = (context as AppCompatActivity).managedQuery(contactData, null, null, null, null)

                if (c.moveToFirst()) {

                    val id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    val hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                    try {

                        if (hasPhone.equals("1", ignoreCase = true)) {

                            val phones = (context as AppCompatActivity).contentResolver.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null)

                            text_input_contact_number.setText("")
                            while (phones.moveToNext()) {
                                val cNumber = phones.getString(phones.getColumnIndex("data1"))
                                text_input_contact_number.append(cNumber + " ")
                            }
                            phones.close()

                        }
                    } catch (e: Exception) {
                        print(e)
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)
                activity?.toastmessage("cancled")


        } else if (requestCode == Constants.GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
                    imageView_profilepicture!!.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()

                    activity!!.toastmessage("failed")
                }

            }
        } else if (requestCode == Constants.CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            saveImage(thumbnail)
            imageView_profilepicture!!.setImageBitmap(thumbnail)
        }

    }

    private fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

        val destination = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)

        Log.d("fee", destination.toString())
        if (!destination.exists()) {

            destination.mkdirs()
        }
        try {
            Log.d("heel", destination.toString())
            val fileDetails = File(destination, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".jpg"))
            fileDetails.createNewFile()
            val fo = FileOutputStream(fileDetails)
            fo.write(bytes.toByteArray())
//            MediaScannerConnection.scanFile(this,
//                    arrayOf(fileDetails.getPath()),
//                    arrayOf("image/jpeg"), null)

            Log.d("TAG", "File Saved::--->" + fileDetails.getAbsolutePath())
            fo.close()

            return fileDetails.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        val IMAGE_DIRECTORY = "/DCIM/hello"
    }


    //    gives permission to access contacts
    private fun accessPermissionForAll() {

//        required permissions
        val permissionsNeeded = ArrayList<String>()

//      existing permissions
        val permissionsList = ArrayList<String>()

        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts")

        if (!addPermission(permissionsList, android.Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts")

        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera")

        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage")


        if (permissionsList.size > 0) {

            if (permissionsNeeded.size > 0) {
                var message = "You need to grant access to " + permissionsNeeded[0]
                for (i in 1 until permissionsNeeded.size)
                    message = message + ", " + permissionsNeeded[i]
                showMessageOKCancel(message,

                        DialogInterface.OnClickListener { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(permissionsList.toTypedArray(),

                                        Constants.REQUEST_MULTIPLE_PERMISSIONS)
                            }
                        })

                return

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toTypedArray(),

                        Constants.REQUEST_MULTIPLE_PERMISSIONS)
            }

            return

        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {

        AlertDialog.Builder(context as AppCompatActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }


    //adds permissions to the list if not already exist
    private fun addPermission(permissionsList: MutableList<String>, permission: String): Boolean {

        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkSelfPermission(context as AppCompatActivity, permission) != PackageManager.PERMISSION_GRANTED
                } else {
                    TODO("VERSION.SDK_INT < M")
                }) {

            permissionsList.add(permission)



            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        !shouldShowRequestPermissionRationale(permission)
                    } else {
                        TODO("VERSION.SDK_INT < M")
                    })

                return false

        }

        return true

    }


    private fun spinnerOperation(){

//        ProgressDialog
        val mProgressDialog = ProgressDialog(context)
        mProgressDialog.setMessage("Please wait")
        mProgressDialog.show()



        if (activity?.isOnline()!!) {


            client = retrofit.create(ApiInterface::class.java)
            val call = client.getOccupation()

//            val callExchangeRate = client.getexchangerate()
//            callExchangeRate.enqueue(object  : Callback<List<Currency>>{
//                override fun onFailure(call: Call<List<Currency>>?, t: Throwable?) {
//
//                    activity!!.toastmessage("failed")
//                }
//
//                override fun onResponse(call: Call<List<Currency>>?, response: Response<List<Currency>>?) {
//                   val responseCurrency = response?.body()
//
//                    recyclerview_list.adapter = CurrencyAdapter(responseCurrency)
//                     }
//
//            })

            call.enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>?, t: Throwable?) {

                    activity!!.toastmessage("failed")
                }

                override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {

                    val responseString = response!!.body()
                    val list = responseString!!.map { it.Text }
                    spinner_occupation.adapter = ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, list)
                    mProgressDialog.dismiss()
                    activity?.toastmessage("success")
                }


            })
        }
        else
        {
            mProgressDialog.dismiss()
            activity?.toastmessage("failed to load spinner!! no internet")

        }

    }

    private fun imageView_profilepictureOperation() {
        val options = arrayOf("camera", "gallery")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("CHOOSE")
        builder.setItems(options)
        { options, selection ->
            when (selection) {
                0 -> choosePhotoFromCamera()
                1 -> choosePhotoFromGallery()
            }
        }
        builder.show()
    }

    private fun choosePhotoFromGallery() {
        val intentPickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentPickPhoto, Constants.GALLERY)

    }

    private fun choosePhotoFromCamera() {
        val intentcamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intentcamera, Constants.CAMERA)
    }

}