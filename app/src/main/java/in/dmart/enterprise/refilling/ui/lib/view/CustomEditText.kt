package `in`.dmart.enterprise.refilling.ui.lib.view

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.util.FireBaseUtil
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View.OnKeyListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class CustomEditText : TextInputEditText {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val textColor =
                attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "textColor")
            if (textColor == null || textColor.isEmpty()) {
                setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }
    }

    private fun getScanText(key: String, scannedData: String): String {
        return try {
            val jsonObject = JSONObject(scannedData)
            if (jsonObject.has(key)) {
                jsonObject[key] as String
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()

            //AppUtils.showToast("--"+e.toString());
            scannedData
        }
    }

    fun setOnScanListener(isResetText: Boolean, key: String?, scanDataListener: ScanDataListener?) {
        if (scanDataListener != null) {
            setOnKeyListener(OnKeyListener { _, keyCode, event -> // Logger.log(TAG, "inside et_BTEInput.setOnKeyListener of showSearchPopupDialog ");

                // If the event is a key-down event on the "enter" button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // Perform action on key press
                    val scanData = this@CustomEditText.text?.toString() ?: ""
                    if (scanData.isNotEmpty()) {
                        if (key != null) {
                            FireBaseUtil.sendEvent(
                                null,
                                FireBaseUtil.getScanEvent(
                                    scanDataListener.javaClass.simpleName,
                                    Constant.SCAN_QR
                                ),
                                scanDataListener.javaClass.simpleName,
                                FireBaseUtil.EVENT_TYPE_SCAN,
                                scanData
                            )
                            val textData = getScanText(key, scanData).trim { it <= ' ' }
                            if (!isResetText) {
                                this@CustomEditText.setText(textData)
                            }
                            scanDataListener.onScanData(this@CustomEditText, textData)
                        } else {
                            FireBaseUtil.sendEvent(
                                null,
                                FireBaseUtil.getScanEvent(
                                    scanDataListener.javaClass.simpleName,
                                    Constant.SCAN_BAR
                                ),
                                scanDataListener.javaClass.simpleName,
                                FireBaseUtil.EVENT_TYPE_SCAN,
                                scanData
                            )
                            scanDataListener.onScanData(
                                this@CustomEditText,
                                scanData.trim { it <= ' ' })
                        }
                        if (isResetText) {
                            this@CustomEditText.setText("")
                        }
                        sound()
                    }
                    return@OnKeyListener true
                }
                false
            })
        }
    }

    fun setOnScanListener(isResetText: Boolean, scanDataListener: ScanDataListener?) {
        setOnScanListener(isResetText, null, scanDataListener)
    }

    fun setOnScanListener(key: String?, scanDataListener: ScanDataListener?) {
        setOnScanListener(false, key, scanDataListener)
    }

    fun setOnScanListener(scanDataListener: ScanDataListener?) {
        setOnScanListener(false, null, scanDataListener)
    }

    private fun sound() {
        try {
            val context = context
            val vibe = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            //vibe.vibrate(200)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibe.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibe.vibrate(200)
            }
            val uri = "android.resource://" + context.packageName + "/" + R.raw.beep004
            val notification = Uri.parse(uri)
            val r = RingtoneManager.getRingtone(getContext(), notification)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                r.audioAttributes =
                    AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
            } else {
                @Suppress("DEPRECATION")
                r.streamType = AudioManager.STREAM_ALARM
            }
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}