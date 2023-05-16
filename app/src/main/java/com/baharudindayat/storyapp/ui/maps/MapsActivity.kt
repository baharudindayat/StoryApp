package com.baharudindayat.storyapp.ui.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.baharudindayat.storyapp.R
import com.baharudindayat.storyapp.data.StoryResult
import com.baharudindayat.storyapp.data.local.preferences.User
import com.baharudindayat.storyapp.data.local.preferences.UserPreferences
import com.baharudindayat.storyapp.databinding.ActivityMapsBinding
import com.baharudindayat.storyapp.ui.main.viewmodel.MainViewModel
import com.baharudindayat.storyapp.ui.maps.viewmodel.MapsViewModel
import com.baharudindayat.storyapp.utils.ViewModelFactory

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var mapsViewModel: MapsViewModel
    private var userModel: User = User()
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        mapsViewModel = ViewModelProvider(this, factory)[MapsViewModel::class.java]

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()
        token = userModel.token.toString()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapStyle()

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true


        mapsViewModel.getStoriesLocation(token).observe(this) { result ->
            when(result) {
                is StoryResult.Success -> {
                    if (result.data.isNotEmpty()) {
                        val dataStories = result.data
                        for(i in dataStories) {
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(i.lat,i.lon))
                                    .title(i.name)
                                    .snippet(i.description)
                                    .alpha(0.8f)
                            )
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(i.lat,i.lon), 15f))
                        }
                    }
                }
                is StoryResult.Loading -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is StoryResult.Error -> {
                    Toast.makeText(this, getString(R.string.mapsfailed), Toast.LENGTH_SHORT).show()
                }
            }
        }






        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    private fun mapStyle(){
        try {
            val success = mMap.setMapStyle(
                com.google.android.gms.maps.model.MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.style_map
                )
            )
            if (!success) {
                Toast.makeText(this, "Failed to load map style", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to load map style", Toast.LENGTH_SHORT).show()
        }
    }
}