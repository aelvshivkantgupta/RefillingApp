package `in`.dmart.enterprise.refilling.ui.view.activity

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.FailureHandler
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.apilibrary.model.BlankReq
import `in`.dmart.apilibrary.model.PostResponse
import `in`.dmart.enterprise.refilling.model.apimodel.login.response.UserRole
import `in`.dmart.apilibrary.util.Logger
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.databinding.ActivityBaseBinding
import `in`.dmart.enterprise.refilling.di.SharedPref
import `in`.dmart.enterprise.refilling.ui.lib.view.OnTextScanned
import `in`.dmart.enterprise.refilling.ui.view.activity.login.LoginActivity
import `in`.dmart.enterprise.refilling.util.AppUtil
import `in`.dmart.enterprise.refilling.util.FireBaseUtil
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.util.SparseIntArray
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

open class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), FailureHandler {
    private var TAG = this.javaClass.name

    protected lateinit var dataBinding: B
    protected lateinit var binding: ActivityBaseBinding
    var myFirebaseAnalytics: FirebaseAnalytics? = null
    private var mErrorString: SparseIntArray? = null
    private var showOptionsMenu = false

    @Inject lateinit var pref: SharedPref
    var webServiceClass: WebServiceClass? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        mErrorString = SparseIntArray()
        myFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        FireBaseUtil.sendEvent(myFirebaseAnalytics, FireBaseUtil.getScreenViewEvent(javaClass.simpleName), javaClass.simpleName, FireBaseUtil.EVENT_TYPE_DISPLAY)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        WebServiceClass.failureHandler = this
        val lp = window.attributes
        lp.screenBrightness = 0.80f
        window.attributes = lp
        val toolbar = binding.toolbarLayout.toolBarView
        setSupportActionBar(toolbar)
        showActionBar(true)
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /*
    Call this method before super.onCreate() in the onCreate() method of any activity
     */
    fun showOptionsMenu(show: Boolean) {
        showOptionsMenu = show
    }



    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()

    }

    fun setActionBarColor(color: Int) {
        binding.toolbarLayout.toolBarView.setBackgroundColor(color)
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (showOptionsMenu) {
            val inflater = menuInflater
            inflater.inflate(R.menu.options_menu_layout, menu)
            //menu.findItem(R.id.txtVersion).setTitle("Version ".concat(BuildConfig.VERSION_NAME));
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> webServiceClass?.postData(ApiUrls.API_POST_LOGOUT, BlankReq(), PostResponse::class.java, object :
                ApiResponse<Any,Throwable> {
                override fun onSuccess(response: Any) {
                    logout()
                }

                override fun onFailure(error: Throwable?) {
                    logout()
                }
            })
           // R.id.changePwd -> ChangePwdUtil.moveToChangePwd(this, Constant.userId)
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isDeviceLocked(context: Context): Boolean {
        val isLocked: Boolean

        // First we check the locked state
        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        @Suppress("DEPRECATION") val inKeyguardRestrictedInputMode = keyguardManager.inKeyguardRestrictedInputMode()
        isLocked = if (inKeyguardRestrictedInputMode) {
            true
        } else {
            // If password is not set in the settings, the inKeyguardRestrictedInputMode() returns false,
            // so we need to check if screen on for this case
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            return !powerManager.isInteractive
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                !powerManager.isInteractive
            } else {
                !powerManager.isScreenOn
            }*/
        }
        Logger.log(TAG, String.format("Now device is %s.", if (isLocked) "locked" else "unlocked"))
        return isLocked
    }

    fun logout() {
        FireBaseUtil.sendEvent(myFirebaseAnalytics, FireBaseUtil.EVENT_NAME_LOGOUT, this.javaClass.simpleName, FireBaseUtil.EVENT_TYPE_CLICK)
        pref.putString(Constant.USER_NAME, "")
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun showActionBar(show: Boolean) {
        if (show) {
            supportActionBar!!.show()
        } else {
            supportActionBar!!.hide()
        }
    }



    protected fun <T : ViewDataBinding?> putContentView(@LayoutRes resId: Int): T {
        return DataBindingUtil.inflate<T>(layoutInflater, resId, binding.activityContent, true)
    }


    fun setTitle(title: String?) {
        (binding.toolbarLayout.toolBarView.findViewById<View>(R.id.title) as TextView).text = title
    }

    override fun setTitle(title: Int) {
        setTitle(getString(title))
    }

    fun showManual(visibility: Int) {
            binding.toolbarLayout.toolBarView.findViewById<View>(R.id.rightMenu).visibility = visibility
    }

    fun showManual(visibility: Int, listener: View.OnClickListener) {
        showManual(visibility)
        setManualListener(listener)
    }

    fun setManualListener(listener: View.OnClickListener?) {
        val rightMenu = binding.toolbarLayout.toolBarView.findViewById<TextView>(R.id.rightMenu)
        if (listener == null) {
            rightMenu.setOnClickListener { }
        } else {
            rightMenu.setOnClickListener(listener)
        }
    }

    fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun showAlert(message: String?, title: String?) {
        val builder = AlertDialog.Builder(this, R.style.ThemeOverlay_AppCompat_Dialog)
        builder.setTitle(title)
            .setCancelable(false)
            .setNegativeButton("Close") { dialog, _ -> dialog.cancel() }
        builder.setMessage(message)
        val alert = builder.create()
        alert.show()
    }

    fun isAuthorizedUser(userRoles: List<UserRole>, resource: String?): Boolean {
        for (userRole in userRoles) {
            if (userRole.resource.equals(resource, ignoreCase = true)) {
                Constant.userRole = userRole
                return true
            }
        }
        return false
    }

    @Suppress("DEPRECATION")
    fun sound() {
        try {
            val vibe = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibe.vibrate(200)
            val uri = "android.resource://" + packageName + "/" + R.raw.beep004
            val notification = Uri.parse(uri)
            val r = RingtoneManager.getRingtone(applicationContext, notification)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                r.audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
            } else {
                r.streamType = AudioManager.STREAM_ALARM
            }
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getScanTextWithKey(key: String, scannedData: String): String {
        return try {
            val jsonObject = JSONObject(scannedData)
            if (jsonObject.has(key)) {
                val data = jsonObject[key] as String
                FireBaseUtil.sendEvent(myFirebaseAnalytics, FireBaseUtil.getScanEvent(javaClass.simpleName, Constant.SCAN_QR), javaClass.simpleName, FireBaseUtil.EVENT_TYPE_SCAN, data)
                data
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            FireBaseUtil.sendEvent(myFirebaseAnalytics, FireBaseUtil.getScanEvent(javaClass.simpleName, Constant.SCAN_BAR), javaClass.simpleName, FireBaseUtil.EVENT_TYPE_SCAN, scannedData)

            //Toast.makeText(this,"--"+e.toString(),Toast.LENGTH_LONG).show();
            if (key.isEmpty()) {
                scannedData
            } else {
                ""
            }
        }
    }

    fun getScanText(key: String, scannedData: String): String {
        return try {
            //System.out.println("scan data1::"
            //      +scannedData);
            val jsonObject = JSONObject(scannedData)
            if (jsonObject.has(key)) {
                val data = jsonObject[key] as String
                FireBaseUtil.sendEvent(myFirebaseAnalytics, FireBaseUtil.getScanEvent(javaClass.simpleName, Constant.SCAN_QR), javaClass.simpleName, FireBaseUtil.EVENT_TYPE_SCAN, data)
                data
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            FireBaseUtil.sendEvent(myFirebaseAnalytics, FireBaseUtil.getScanEvent(javaClass.simpleName, Constant.SCAN_BAR), javaClass.simpleName, FireBaseUtil.EVENT_TYPE_SCAN, scannedData)

            //Toast.makeText(this,"--"+e.toString(),Toast.LENGTH_LONG).show();
            scannedData
        }
    }

    fun showRightMenu(visiblility: Int) {
        binding.toolbarLayout.rightMenu.visibility = visiblility
    }

    fun setRightMenu(string: Int) {
        (binding.toolbarLayout.rightMenu as TextView).text = getString(string)
    }

    fun setRightMenu(string: String?) {
        (binding.toolbarLayout.rightMenu as TextView).text = string
    }

    fun setRightMenuListener(listener: View.OnClickListener?) {
        val rightMenu: TextView = binding.toolbarLayout.rightMenu
        if (listener == null) {
            rightMenu.setOnClickListener { }
        } else {
            rightMenu.setOnClickListener(listener)
        }
    }

    fun scannerInput(key: Array<String>, onTextScanned: OnTextScanned) {
        binding.etBTEInput.requestFocus()
        binding.etBTEInput.setOnKeyListener { _: View?, keyCode: Int, event: KeyEvent ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER) {
                val txtData = binding.etBTEInput.text?.toString() ?: ""
                binding.etBTEInput.setText("")
                if (txtData.trim { it <= ' ' } != "") {
                    var text: String?
                    for (k in key) {
                        text = getScanTextWithKey(k, txtData.trim { it <= ' ' })
                        if (text.isNotEmpty()) {
                            sound()
                            onTextScanned.scanResult(k, text)
                            //break;
                        }
                    }
                }
                return@setOnKeyListener true
            }
            false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permissionCheck = PackageManager.PERMISSION_GRANTED
        for (permission in grantResults) {
            permissionCheck += permission
        }
        if (grantResults.isNotEmpty() && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode)
        } else {
            Snackbar.make(findViewById(R.id.content), mErrorString!![requestCode],
                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE"
            ) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("package:$packageName")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                startActivity(intent)
            }.show()
        }
    }

    fun requestAppPermissions(requestedPermissions: Array<String?>,
                              stringId: Int, requestCode: Int) {
        mErrorString!!.put(requestCode, stringId)
        var permissionCheck = PackageManager.PERMISSION_GRANTED
        var shouldShowRequestPermissionRationale = false
        for (permission in requestedPermissions) {
            permissionCheck += ContextCompat.checkSelfPermission(this, permission!!)
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                Snackbar.make(findViewById(R.id.content), stringId,
                    Snackbar.LENGTH_INDEFINITE).setAction("GRANT"
                ) { ActivityCompat.requestPermissions(this@BaseActivity, requestedPermissions, requestCode) }.show()
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode)
            }
        } else {
            onPermissionsGranted(requestCode)
        }
    }

    private fun onPermissionsGranted(@Suppress("UNUSED_PARAMETER") requestCode: Int) {}
    override fun <T> onFailure(url: String, reqBody: Any?, t: T) {
        AppUtil.logNonFatalErrors(Constant.TAG_API_FAILURE, reqBody, url, t)
    }
}