package kr.hs.emirim.cho.taketoday2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_test.*


class Test : AppCompatActivity() {
    private var locationManager : LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val geocoder = Geocoder(this)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        testbtn.setOnClickListener { view ->
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@Test, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
            } else {
                val location = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                var list: List<Address>? = geocoder.getFromLocation(location!!.latitude, location!!.longitude,1)
                var adre = list?.get(0)?.getAddressLine(0)
                var arr = adre?.split(" ")
                texbt.text = ("" + (arr?.get(2)))


            }

        }
    }



}