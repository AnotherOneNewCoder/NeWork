package ru.netology.nework.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import ru.netology.nework.R

class SingInDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.only_for_autorized_users_would_you_like_to_sing_in))
            .setNegativeButton("Later") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Go") { dialog, _ ->
                findNavController().navigate(R.id.singInFragment)
                dialog.dismiss()
            }
            .create()
}