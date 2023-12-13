package com.lucassimao.maptrack.util

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lucassimao.maptrack.R

fun Fragment.showAlertDialog(
    positiveAction: () -> Unit,
    negativeAction: () -> Unit,
    title: String? = null,
    messageLayoutId: Int = R.layout.custom_justified_text,
    message: String? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null
) {
    val alertDialogView = createAlertDialogView(messageLayoutId, title, message)

    val alertBuilder = buildAlertDialog(alertDialogView)

    configurePositiveButton(alertBuilder, positiveButtonText, positiveAction)
    configureNegativeButton(alertBuilder, negativeButtonText, negativeAction)

    alertBuilder.show()
}

private fun Fragment.createAlertDialogView(layoutId: Int, title: String?, message: String?): View {
    val inflater = LayoutInflater.from(requireContext())
    val alertDialogView = inflater.inflate(layoutId, null)

    alertDialogView.findViewById<TextView>(R.id.alert_title)?.text = title
    alertDialogView.findViewById<TextView>(R.id.alert_message)?.text = message

    return alertDialogView
}

private fun Fragment.buildAlertDialog(view: View): AlertDialog.Builder {
    return AlertDialog.Builder(requireContext())
        .setCancelable(false)
        .setView(view)
}

private fun configurePositiveButton(builder: AlertDialog.Builder, buttonText: String?, action: () -> Unit) {
    builder.setPositiveButton(buttonText) { _, _ -> action() }
}

private fun configureNegativeButton(builder: AlertDialog.Builder, buttonText: String?, action: () -> Unit) {
    builder.setNegativeButton(buttonText) { _, _ -> action() }
}