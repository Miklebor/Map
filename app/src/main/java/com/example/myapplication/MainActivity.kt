package com.example.myapplication

import android.Manifest
import android.Manifest.permission
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.ar.core.Point
import com.yandex.mapkit.Animation
import com.yandex.mapkit.Animation.Type.SMOOTH
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MainActivity : AppCompatActivity() {

    private lateinit var mapview: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("39bb32a5-de2b-4402-ab96-a064b52fa1db")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapview = findViewById(R.id.mapview)
        var mapKit: MapKit = MapKitFactory.getInstance()

        requestLocationPermission()
        var locationOnMapKit = mapKit.createUserLocationLayer(mapview.mapWindow)  //запуск слоя локации
        locationOnMapKit.isVisible=true

         /* locationManager.requestSingleUpdate(/* p0 = */ object : LocationListener){

            override fun onLocationStatusUpdated(p0: LocationStatus?) {
                println(p0?.toString() ?: "No status")
            }
            override fun onLocationUpdated(p0: Location?) {
                println(
                    "lat=${p0?.position?.latitude ?: ""} " +
                            "lon=${p0?.position?.longitude ?: ""}"
                )
            }
        })*/

       mapview.map.move(CameraPosition(com.yandex.mapkit.geometry.Point(55.751225, 37.629540),16.0f ,0.0f,30.0f),
            Animation(Animation.Type.LINEAR,3f), null)

        val imageProvider = ImageProvider.fromResource(this, R.drawable.vector_118103)
        val placemark = mapview.map.mapObjects.addPlacemark().apply {
            geometry = com.yandex.mapkit.geometry.Point(55.752225, 37.629540)
            setIcon(imageProvider)
        }
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
          ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
          ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        return
        }
    }
    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview.onStart()
    }
    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}


