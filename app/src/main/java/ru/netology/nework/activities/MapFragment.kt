package ru.netology.nework.activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentMapBinding
import ru.netology.nework.utils.CommonUtils

class MapFragment : Fragment(), UserLocationObjectListener, InputListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: Map
    private lateinit var userLocation: UserLocationLayer
    private lateinit var mapView: MapView
    private lateinit var placeMarkMapObject: PlacemarkMapObject

    private var newPoint = Point(55.751999, 37.617734)

    companion object {
        private const val ZOOM_STEP = 1f
        private val SMOOTH_ANIMATION = Animation(Animation.Type.SMOOTH, 0.4f)

        // временные точки для теста
        private val START_ANIMATION = Animation(Animation.Type.LINEAR, 1f)
        private val START_POSITION = CameraPosition(Point(55.751999, 37.617734), 10f, 0f, 0f)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(inflater, container, false)

        val lat = arguments?.getDouble("mapLat")
        val long = arguments?.getDouble("mapLong")
        val action = arguments?.getString("action")
        val fragment = arguments?.getString("fragment")

        mapView = binding.mapView
        val mapWindow = mapView.mapWindow
        map = mapWindow.map
        val mapKit: MapKit = MapKitFactory.getInstance()
        requestLocationPermission()
        userLocation = mapKit.createUserLocationLayer(mapWindow)
        userLocation.setObjectListener(this)

//        if (lat != null && long != null) {
        if (lat != 0.00000000000000000000 && long != 0.00000000000000000000 && lat != null
            && long != null
        ) {
            newPoint = Point(lat, long)
            createPlaceMark(newPoint)
            map.move(
                CameraPosition(newPoint, 15f, 0f, 0f),
                SMOOTH_ANIMATION
            ) {
                Toast.makeText(
                    requireContext(),
                    "Move to point Event",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            map.move(
                START_POSITION,
                START_ANIMATION
            ) {
                Toast.makeText(
                    requireContext(),
                    "Move to start location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        var onOffUserLocation = false





        binding.apply {

            minus.setOnClickListener { changeZoomByStep(-ZOOM_STEP) }
            plus.setOnClickListener { changeZoomByStep(ZOOM_STEP) }
            when (action) {

                "new" -> {
                    map.addInputListener(this@MapFragment)
                    saveLocation.visibility = View.VISIBLE
                    saveLocation.setOnClickListener {
                        val bundle = Bundle().apply {
                            putDouble("map_lat", newPoint.latitude)
                            putDouble("map_long", newPoint.longitude)
                        }

                        when (fragment) {
                            "event" -> {
                                findNavController().navigate(R.id.newEventFragment, bundle)
                            }

                            "post" -> {
                                findNavController().navigate(R.id.newPostFragment, bundle)
                            }

                            else -> Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                        }
                    }


                }

                else -> {
                    saveLocation.visibility = View.GONE
                }


            }

            location.setOnClickListener {
                when (onOffUserLocation) {
                    true -> {
                        userLocation.isVisible = false
                        userLocation.isHeadingEnabled = false
                        onOffUserLocation = false
                        map.move(
                            CameraPosition(newPoint, 15f, 0f, 0f),
                            START_ANIMATION
                        ) {
                            Toast.makeText(
                                requireContext(),
                                "Move to point Event",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    false -> {
                        userLocation.isVisible = true
                        userLocation.isHeadingEnabled = true
                        onOffUserLocation = true
                        val target = userLocation.cameraPosition()
                        if (target != null) {
                            map.move(
                                CameraPosition(
                                    target.target,
                                    target.zoom,
                                    target.azimuth,
                                    target.tilt,

                                    ),
                                START_ANIMATION
                            ) {
                                Toast.makeText(
                                    requireContext(),
                                    "Move to user location",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                }
            }
        }

        return binding.root
    }


    // запрос разрешений
    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            return
        }
    }

    private fun createPlaceMark(point: Point) {
        placeMarkMapObject = map.mapObjects.addPlacemark(
            point,
            ImageProvider.fromBitmap(
                CommonUtils.createBitmapFromVector(
                    requireContext(),
                    R.drawable.ic_coords
                )
            ),
            IconStyle().apply { anchor = PointF(0.5f, 1.0f) }
        ).apply {
            isDraggable = true
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    private fun changeZoomByStep(value: Float) {
        with(map.cameraPosition) {
            map.move(
                CameraPosition(target, zoom + value, azimuth, tilt),
                SMOOTH_ANIMATION,
                null,
            )
        }
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocation.setAnchor(
            PointF(mapView.width * 0.5F, mapView.height * 0.5F),
            PointF(mapView.width * 0.5F, mapView.height * 0.5F),
        )
    }

    override fun onObjectRemoved(userLocationView: UserLocationView) {
        userLocationView.isValid
        with(map.cameraPosition) {
            map.move(
                START_POSITION,
                START_ANIMATION
            ) { Toast.makeText(requireContext(), "Initial move", Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }

    override fun onMapTap(p0: Map, p1: Point) {
        Toast.makeText(requireContext(), "Lat: ${p1.latitude} Long: ${p1.longitude}", Toast.LENGTH_SHORT).show()
    }

    override fun onMapLongTap(map: Map, point: Point) {
        mapView.map.mapObjects.clear()
//        createPlaceMark(point)
        newPoint = point
        createPlaceMark(point)

    }
}