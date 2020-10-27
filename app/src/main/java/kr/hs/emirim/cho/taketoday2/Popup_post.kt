package kr.hs.emirim.cho.taketoday2

import android.app.ActionBar
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService

import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_popup_post.*
import java.util.jar.Manifest

class Popup_post : DialogFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.white_round);

        set_image.setOnClickListener{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(checkSelfPermission((activity as Popup_post).requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED){
                    android.widget.Toast.makeText((activity as Popup_post).requireContext(), "Permission Denied", android.widget.Toast.LENGTH_SHORT).show();
                    androidx.core.app.ActivityCompat.requestPermissions((activity as Popup_post).requireActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
            }
        }

        return inflater.inflate(R.layout.activity_popup_post, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.50).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}