package kr.hs.emirim.cho.taketoday2

import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Upload : AppCompatActivity() {
    val REQUEST_GALLERY_TAKE = 2
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_LOCATION = 3
    private var photoURI: Uri? = null
    private var currentPhotoPath: String = ""
    private var timeStamp: String = ""
    private var hashTagTitle: String = ""
    private var currentLoca: String = ""
    val REQUEST_IMAGE_PICK = 10
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var user_id: String
    private var tempFile: Uri? = null
    private var locationManager : LocationManager? = null
    private var currentCode: String = ""
    private var nowing: Int = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        mAuth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = mAuth.currentUser!!.uid

        //nowing과 hashTag를 구하는
        firebaseFirestore.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            currentCode = document.data?.get(key = "inCate").toString()
            firebaseFirestore.collection("Todays").whereEqualTo("cate",currentCode).whereEqualTo("user",user_id).get().addOnSuccessListener { documents ->
                for (docu in documents){
                    nowing = docu.data?.get(key = "now").toString().toInt()
                    firebaseFirestore.collection("Category").document(currentCode).get().addOnSuccessListener { document2 ->
                        var cateList:List<String> = document2.data?.get(key = "arr") as List<String>

                        hashTag.text= '#'+cateList[nowing]
                    }
                }
            }
        }


        //위치
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        getLastLocation()



        btn_reload.setOnClickListener {
            getLastLocation()
        }
        btn_back.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }

        imageUp.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("당신의 하루를 찍어주세요")
            builder.setMessage("사진은 정사각형으로 잘리기 때문에 1:1 비율로 찍는 것이 좋습니다")

            builder.setNegativeButton("카메라로 찍기",
                { dialog: DialogInterface?, which: Int ->
                    if (checkPersmission()) {
                        dispatchTakePictureIntent()
                    } else {
                        requestPermission()
                    }
                })
            builder.setPositiveButton("사진 업로드하기",
                { dialog: DialogInterface?, which: Int ->
                    if (checkPersmission()) {
                        openGalleryForImage()
                    } else {
                        requestPermission()
                    }
                })
            builder.show()
        }

        var current = LocalDateTime.now()
        var formatter = DateTimeFormatter.ISO_DATE


        setup_btn.setOnClickListener {
            var contents: String = setup_content.text.toString()

            if (!TextUtils.isEmpty(contents)) {

                timeStamp = current.format(formatter)
                Toast.makeText(this, "현재시간 : " + timeStamp, Toast.LENGTH_LONG).show()

                firebaseFirestore.collection("Todays").whereEqualTo("cate",currentCode).whereEqualTo("user",user_id).get().addOnSuccessListener { documents ->
                    LodingDialogActivity(this).show()
                    for(docu in documents){
                        var now = docu.data?.get(key = "now").toString().toInt()
                        val postMap = hashMapOf<String, Any>()
                        postMap.put("content", contents.toString())
                        postMap.put("user_id", user_id) //
                        postMap.put("hashTag", now) //
                        postMap.put("timestamp", timeStamp)
                        postMap.put("location", currentLoca)
                        postMap.put("cate",currentCode) //

                        firebaseFirestore.collection("Posts").whereEqualTo("cate",currentCode).whereEqualTo("hashTag",nowing).whereEqualTo("user_id",user_id).get().addOnSuccessListener { catdoc ->
                            if (catdoc.isEmpty) {
                                firebaseFirestore.collection("Posts").document().set(postMap)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            firebaseFirestore.collection("Posts").whereEqualTo("user_id", user_id).whereEqualTo("hashTag", now).whereEqualTo("cate",currentCode).get().addOnSuccessListener { documents2 ->
                                                for(d in documents2){

                                                    var image_path: StorageReference =
                                                        storageReference.child("images").child(d.id + ".jpg")

                                                    if (photoURI!=null) {
                                                        image_path.putFile(photoURI!!).addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                Toast.makeText(this, "The Image is Uploaded", Toast.LENGTH_LONG).show()
                                                                firebaseFirestore.collection("Todays").whereEqualTo("user", user_id).whereEqualTo("cate",currentCode).get().addOnSuccessListener { documents3 ->
                                                                    for(docdoc in documents3){
                                                                        firebaseFirestore.collection("Todays").document(docdoc.id).update("remain",FieldValue.arrayRemove(nowing))
                                                                        firebaseFirestore.collection("Todays").document(docdoc.id).update("time",System.currentTimeMillis())
                                                                        if (((docdoc.data.get(key = "remain")).toString()).equals("[]")){
                                                                            firebaseFirestore.collection("Users").document(user_id).update("current",FieldValue.arrayRemove(currentCode))
                                                                        }
                                                                        LodingDialogActivity(this).dismiss()
                                                                        startActivity(Intent(this, galleryActivity::class.java))
                                                                        finish()
                                                                    }
                                                                }
                                                            } else {
                                                                var error: Exception? = task.exception
                                                                Toast.makeText(this, "Error : " + error, Toast.LENGTH_LONG).show()
                                                            }
                                                        }
                                                    }else if (tempFile != null){
                                                        image_path.putFile(tempFile!!).addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                Toast.makeText(this, "The Image is Uploaded", Toast.LENGTH_LONG).show()
                                                                firebaseFirestore.collection("Todays").whereEqualTo("user", user_id).whereEqualTo("cate",currentCode).get().addOnSuccessListener { documents3 ->
                                                                    for(docdoc in documents3) {
                                                                        firebaseFirestore.collection("Todays").document(docdoc.id).update("remain", FieldValue.arrayRemove(nowing))
                                                                        firebaseFirestore.collection("Todays").document(docdoc.id).update("time", System.currentTimeMillis())
                                                                        if (((docdoc.data.get(key = "remain")).toString()).equals("[]")) {
                                                                            firebaseFirestore.collection("Users").document(user_id).update("current", FieldValue.arrayRemove(currentCode))
                                                                        }
                                                                        LodingDialogActivity(this).dismiss()
                                                                        startActivity(Intent(this, galleryActivity::class.java))
                                                                        finish()
                                                                    }
                                                                }
                                                            } else {
                                                                var error: Exception? = task.exception
                                                                Toast.makeText(this, "Error : " + error, Toast.LENGTH_LONG).show()
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                                        }
                                    }
                            }else{
                                for(cd in catdoc){
                                    Log.d("",cd.toString())
                                    firebaseFirestore.collection("Posts").document(cd.id).set(postMap)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                firebaseFirestore.collection("Posts").whereEqualTo("user_id", user_id).whereEqualTo("hashTag", now).whereEqualTo("cate",currentCode).get().addOnSuccessListener { documents2 ->
                                                    for(d in documents2){

                                                        var image_path: StorageReference =
                                                            storageReference.child("images").child(d.id + ".jpg")

                                                        if (photoURI!=null) {
                                                            image_path.putFile(photoURI!!).addOnCompleteListener { task ->
                                                                if (task.isSuccessful) {
                                                                    Toast.makeText(this, "The Image is Uploaded", Toast.LENGTH_LONG).show()

                                                                    firebaseFirestore.collection("Todays").whereEqualTo("user", user_id).whereEqualTo("cate",currentCode).get().addOnSuccessListener { documents3 ->
                                                                        for(docdoc in documents3){
                                                                            firebaseFirestore.collection("Todays").document(docdoc.id).update("remain",FieldValue.arrayRemove(nowing))
                                                                            firebaseFirestore.collection("Todays").document(docdoc.id).update("time",System.currentTimeMillis())

                                                                            if (((docdoc.data.get(key = "remain")).toString()).equals("[]")){
                                                                                firebaseFirestore.collection("Users").document(user_id).update("current",FieldValue.arrayRemove(currentCode))
                                                                            }
                                                                            LodingDialogActivity(this).dismiss()
                                                                            startActivity(Intent(this, galleryActivity::class.java))
                                                                            finish()
                                                                        }
                                                                    }
                                                                } else {
                                                                    var error: Exception? = task.exception
                                                                    Toast.makeText(this, "Error : " + error, Toast.LENGTH_LONG).show()
                                                                }
                                                            }
                                                        }else if (tempFile != null){
                                                            image_path.putFile(tempFile!!).addOnCompleteListener { task ->
                                                                if (task.isSuccessful) {
                                                                    Toast.makeText(this, "The Image is Uploaded", Toast.LENGTH_LONG).show()

                                                                    firebaseFirestore.collection("Todays").whereEqualTo("user", user_id).whereEqualTo("cate",currentCode).get().addOnSuccessListener { documents3 ->
                                                                        for(docdoc in documents3){
                                                                            firebaseFirestore.collection("Todays").document(docdoc.id).update("remain",FieldValue.arrayRemove(nowing))
                                                                            firebaseFirestore.collection("Todays").document(docdoc.id).update("time",System.currentTimeMillis())
                                                                            if (((docdoc.data.get(key = "remain")).toString()).equals("[]")){
                                                                                firebaseFirestore.collection("Users").document(user_id).update("current",FieldValue.arrayRemove(currentCode))
                                                                            }
                                                                            LodingDialogActivity(this).dismiss()
                                                                            startActivity(Intent(this, galleryActivity::class.java))
                                                                            finish()
                                                                        }
                                                                    }
                                                                } else {
                                                                    var error: Exception? = task.exception
                                                                    Toast.makeText(this, "Error : " + error, Toast.LENGTH_LONG).show()
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(this, "모두 입력해주세요", Toast.LENGTH_LONG).show()
            }
        }
    }


    //권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            REQUEST_IMAGE_CAPTURE
        )
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.size === 1
                && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                finish()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                // 찍은 사진을 그림파일로 만들기
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("TAG", "그림파일 에러")
                        null
                    }

                // 그림파일을 성공적으로 만들었다면 onActivityForResult로 보내기
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        this, "kr.hs.emirim.cho.taketoday2.fileprovider", it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    // 카메라로 촬영한 이미지를 파일로 저장해준다
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1 -> {
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

                    // 카메라로부터 받은 데이터가 있을경우에만
                    val file = File(currentPhotoPath)
                    if (Build.VERSION.SDK_INT < 28) {
                        val options=BitmapFactory.Options()

                        options.inSampleSize=8
//                        val bitmap = MediaStore.Images.Media
//                            .getBitmap(contentResolver, Uri.fromFile(file))  //Deprecated
//                        imageUp.setImageBitmap(bitmap)

                        val bitmap=BitmapFactory.decodeFile(file.absolutePath, options)
                        imageUp.setImageBitmap(bitmap)
                    } else {
//                        val decode = ImageDecoder.createSource(
//                            this.contentResolver,
//                            Uri.fromFile(file)
//                        )

                        val options=BitmapFactory.Options()

                        options.inSampleSize=8
//                        val bitmap = ImageDecoder.decodeBitmap(decode)
//                        imageUp.setImageBitmap(bitmap)
                        val bitmap=BitmapFactory.decodeFile(file.absolutePath, options)
                        imageUp.setImageBitmap(bitmap)

                    }
                }
            }
            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE) {
                    imageUp.setImageURI(data?.data) // handle chosen image
                    val photoUri = data!!.data
                    var cursor: Cursor? = null

                    try {
                        val proj =
                            arrayOf(MediaStore.Images.Media.DATA)

                        assert(photoUri != null)
                        cursor = contentResolver.query(photoUri!!, proj, null, null, null)

                        assert(cursor != null)
                        val column_index =
                            cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                        cursor.moveToFirst()

                        tempFile = Uri.fromFile(File(cursor.getString(column_index)))
                        Log.d("TAG :========= ", tempFile.toString())
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                    //setImage();
                }

            }
        }
    }

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest


    private fun getLastLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if(locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this@Upload)

                        var list: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude,1)
                        var adre = list?.get(0)?.getAddressLine(0)
                        var arr = adre?.split(" ")
                        loca.text = (" " + (arr?.get(2)))
                        currentLoca = ("" + (arr?.get(2)))
                    }else{
                        getNewLocation()
                    }
                }
            }else{
                if ( !locationManager!!.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    val dialog =
                            androidx.appcompat.app.AlertDialog.Builder(this)
                                    .setMessage("더 나은 서비스를 위해 GPS를 활성화해주세요!")
                                    .setPositiveButton("예") { dialog, which ->
                                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                    }
                                    .setNegativeButton("아니요") { dialog, which -> }
                                    .create()
                    dialog.show()
                }else{
                    Toast.makeText(this,"다시 시도해주세요",Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),REQUEST_LOCATION
            )
        }
    }

    private fun getNewLocation(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_LOW_POWER
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 1000
        locationRequest.numUpdates = 1
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),REQUEST_LOCATION
            )
        }
    }
    private val locationCallback = object:LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation:Location = p0.lastLocation

            val geocoder = Geocoder(this@Upload)

            var list: List<Address>? = geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude,1)
            var adre = list?.get(0)?.getAddressLine(0)
            var arr = adre?.split(" ")
            loca.text = (" " + (arr?.get(2)))
            currentLoca = ("" + (arr?.get(2)))
        }

    }



    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }

    override fun onPause() {
        super.onPause()
        if (LodingDialogActivity(this) != null && LodingDialogActivity(this).isShowing()) {
            LodingDialogActivity(this).cancel()
        }
    }

//    private fun setImage() {
//        val options: BitmapFactory.Options = BitmapFactory.Options()
//        val originalBm: Bitmap = BitmapFactory.decodeFile(tempFile!!.absolutePath, options)
//        imageUp.setImageBitmap(originalBm)
//
//    }
}
private fun <K, V> Map<K, V>.put(key: K, value: K) {

}