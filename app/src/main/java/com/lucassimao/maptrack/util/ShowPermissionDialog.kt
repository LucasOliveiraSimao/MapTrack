package com.lucassimao.maptrack.util

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.lucassimao.maptrack.R

fun Fragment.showPermissionDialog(
    positiveAction: () -> Unit,
    negativeAction: () -> Unit,
    title: String = getString(R.string.permission_required),
    messageLayoutId: Int = R.layout.custom_justified_text,
    positiveButtonText: String = "Aceito",
    negativeButtonText: String = "Não Aceito"
) {
    val inflater = LayoutInflater.from(this.requireContext())
    val messageView: View = inflater.inflate(messageLayoutId, null)


    val alertBuilder = AlertDialog.Builder(this.requireContext())
    alertBuilder.setCancelable(false)
    alertBuilder.setTitle(title)
    alertBuilder.setView(messageView)
    alertBuilder.setPositiveButton(positiveButtonText) { _, _ ->
        positiveAction()
    }
    alertBuilder.setNegativeButton(negativeButtonText) { _, _ ->
        negativeAction()
    }
    alertBuilder.create()
    alertBuilder.show()
}