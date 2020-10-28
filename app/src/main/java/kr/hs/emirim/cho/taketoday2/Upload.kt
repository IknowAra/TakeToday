package kr.hs.emirim.cho.taketoday2

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.R.attr
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_popup_post.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Upload : AppCompatActivity() {
    val REQUEST_GALLERY_TAKE = 2
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath : String
    val REQUEST_IMAGE_PICK = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        imageUp.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("당신의 하루를 찍어주세요")
            builder.setMessage("사진은 정사각형으로 잘리기 때문에 1:1 비율로 찍는 것이 좋습니다")

            builder.setNegativeButton("카메라로 찍기",{dialog: DialogInterface?, which: Int -> if(checkPersmission()){ dispatchTakePictureIntent() }else{ requestPermission() } })
            builder.setPositiveButton("사진 업로드하기",{dialog: DialogInterface?, which: Int -> if(checkPersmission()){ openGalleryForImage() }else{ requestPermission() } })
            builder.show()

        }
    }
    //권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
                REQUEST_IMAGE_CAPTURE)
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Permission: " + permissions[0] + "was " + grantResults[0] + "카메라 허가 받음 예이^^")
        }else{
            Log.d("TAG","권한을 허용해주세요")
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
                    val photoURI: Uri = FileProvider.getUriForFile(
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
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
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

        when (requestCode){
            1 -> {
                if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){

                    // 카메라로부터 받은 데이터가 있을경우에만
                    val file = File(currentPhotoPath)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media
                                .getBitmap(contentResolver, Uri.fromFile(file))  //Deprecated
                        imageUp.setImageBitmap(bitmap)
                    }
                    else{
                        val decode = ImageDecoder.createSource(this.contentResolver,
                                Uri.fromFile(file))
                        val bitmap = ImageDecoder.decodeBitmap(decode)
                        imageUp.setImageBitmap(bitmap)
                    }
                }
            }
            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
                    imageUp.setImageURI(data?.data) // handle chosen image
                }

            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }



}