package kr.hs.emirim.cho.taketoday2

import android.app.ActionBar
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService

import androidx.fragment.app.DialogFragment

class Popup_post : DialogFragment(){
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        var rootview: View=inflater.inflate(R.layout.activity_popup_post, container, false)
//
////        val params: ViewGroup.LayoutParams? = alertDialog?.window?.attributes
//
//        rootview.layoutParams=LinearLayout.LayoutParams(400,400)
//        //rootview.layoutParams.width = 400
//        //rootview.layoutParams.height = 400
//        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        return rootview
//
//        val dialogView = layoutInflater.inflate(R.layout.activity_popup_post, null)
//        val alertDialog = AlertDialog.Builder(Popup_post).create()
//        alertDialog.setView(dialogView)
//        alertDialog.show()
//        alertDialog.window!!.setLayout(700, WindowManager.LayoutParams.WRAP_CONTENT)
//    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.white_round);
        return inflater.inflate(R.layout.activity_popup_post, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.50).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}