package `in`.dmart.enterprise.refilling.ui.lib.view

import android.widget.EditText

interface ScanDataListener {
    fun onScanData(text: EditText, data: String)
}