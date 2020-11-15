package kr.hs.emirim.cho.taketoday2

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_popup_post.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Popup_post(postId:String) : DialogFragment(){
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var user_id: String
    private var tempFile: Uri? = null
    private var locationManager : LocationManager? = null
    private var currentCode: String = ""
    private var nowing: String = postId


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_popup_post, container, false)
    }

    override fun onStart() {
        super.onStart()

        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.60).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)


        mAuth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = mAuth.currentUser!!.uid



        firebaseFirestore.collection("Posts").document(nowing).get().addOnSuccessListener { document ->
            tv_location.setText((document.data?.get(key = "location")).toString()+"의")
            tv_date.setText((document.data?.get(key = "timestamp")).toString())
            tv_contents.setText((document.data?.get(key = "content")).toString())
            firebaseFirestore.collection("Category").document((document.data?.get(key = "cate")).toString()).get().addOnSuccessListener { docu ->
                var catelist:List<String> = docu.data?.get(key = "arr") as List<String>
                var hash:String = catelist[((document.data?.get(key = "hashTag")).toString().toInt())]
                tv_keyword.setText("#"+hash)
            }

            var image_path: StorageReference = storageReference.child("images").child(document.id + ".jpg")
            image_path.getBytes(1024*1024*5).addOnSuccessListener { bytes ->
                var bit: Bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                var img: BitmapDrawable = BitmapDrawable(resources,bit)
                image.setImageDrawable(img)
            }


        }
//            currentCode = document.data?.get(key = "inCate").toString()
//            firebaseFirestore.collection("Todays").whereEqualTo("cate",currentCode).whereEqualTo("user",user_id).get().addOnSuccessListener { documents ->
//                for (docu in documents){
//                    nowing = docu.data?.get(key = "now").toString().toInt()
//                    firebaseFirestore.collection("Category").document(currentCode).get().addOnSuccessListener { document2 ->
//                        var cateList:List<String> = document2.data?.get(key = "arr") as List<String>
//                        tv_keyword.text = '#'+cateList[nowing]
//
//                    }
//                }
//            }
//        }
    }

}