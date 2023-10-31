package ru.netology.nework.activities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R
import ru.netology.nework.databinding.ActivityMainBinding
import ru.netology.nework.viewmodel.AuthViewModel
import ru.netology.nework.viewmodel.EventsViewModel
import ru.netology.nework.viewmodel.UsersViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val authViewModel by viewModels<AuthViewModel>()
    private val usersViewModel by viewModels<UsersViewModel>()
    private val eventsViewModel by viewModels<EventsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment_activity_app)
        bottomNavigationView.setupWithNavController(navController)
        val iconProfile = bottomNavigationView.menu.findItem(R.id.profileFragment)

        authViewModel.data.observe(this) { auth ->
            invalidateOptionsMenu()
            if (auth.id == 0L) {
                iconProfile.setIcon(R.drawable.no_avatar)

            } else {
                usersViewModel.getUserById(auth.id)
            }



            bottomNavigationView.menu.findItem(R.id.profileFragment).setOnMenuItemClickListener {
                if (!authViewModel.isAuthorized) {
                    findNavController(R.id.nav_host_fragment_activity_app).popBackStack()
                    findNavController(R.id.nav_host_fragment_activity_app).navigate(R.id.singInFragment)
                    //navController.navigate(R.id.singInFragment)
                    true
                } else {
                    //usersViewModel.getUserById(auth.id)
                    val loggedUser = usersViewModel.user.value
                    val bundle = Bundle().apply {
                        if (loggedUser != null) {
                            putLong("profileId", loggedUser.id)
                            putString("profileName", loggedUser.name)
                            putString("profileAvatar", loggedUser.avatar)

                        }
                    }

                    findNavController(R.id.nav_host_fragment_activity_app).popBackStack()
                    findNavController(R.id.nav_host_fragment_activity_app).navigate(R.id.profileFragment, bundle)
                    true
                }
            }
            navController.addOnDestinationChangedListener{ _, destination, _ ->
                if (destination.id == R.id.singInFragment || destination.id == R.id.singUpFragment) {
                    bottomNavigationView.visibility = View.GONE
                } else {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }

//            val authorised = authViewModel.isAuthorized
//
//            if (!authorised) {
//                navController.navigate(R.id.singInFragment)
//                bottomNavigationView.isVisible = false
//
//            } else {
//                bottomNavigationView.isVisible = true
//            }

        }
//        eventsViewModel.userId.observe(this, {
//
//        })

        usersViewModel.user.observe(this) {
            Glide.with(this)
                .asBitmap()
                .load("${it.avatar}")
                .transform(CircleCrop())
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        iconProfile.icon = BitmapDrawable(resources, resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
        }


    }
}