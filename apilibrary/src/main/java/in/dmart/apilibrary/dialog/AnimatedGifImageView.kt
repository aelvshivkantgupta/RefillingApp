package `in`.dmart.apilibrary.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Movie
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import java.io.*

@SuppressLint("AppCompatCustomView")
class AnimatedGifImageView : ImageView {
    var animatedGifImage = false
    var p: Paint? = null
    private var `is`: InputStream? = null
    private var mMovie: Movie? = null
    private var mMovieStart: Long = 0
    private var mType = TYPE.FIT_CENTER
    private var mScaleH = 1f
    private var mScaleW = 1f
    private var mMeasuredMovieWidth = 0
    private var mMeasuredMovieHeight = 0
    private var mLeft = 0f
    private var mTop = 0f

    constructor(context: Context?, attrs: AttributeSet?,
                defStyle: Int) : super(context, attrs, defStyle) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?) : super(context) {}

    fun setAnimatedGif(rawResourceId: Int, streachType: TYPE) {
        setImageBitmap(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        mType = streachType
        animatedGifImage = true
        `is` = context.resources.openRawResource(rawResourceId)
        mMovie = try {
            Movie.decodeStream(`is`)
        } catch (e: Exception) {
            Log.e(LOG_TAG, e.message, e)
            val array = streamToBytes(`is`!!)
            Movie.decodeByteArray(array, 0, array.size)
        }
        p = Paint()
    }

    @Throws(FileNotFoundException::class)
    fun setAnimatedGif(filePath: String?, streachType: TYPE) {
        setImageBitmap(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        mType = streachType
        animatedGifImage = true
        val is1: InputStream
        try {
            mMovie = Movie.decodeFile(filePath)
        } catch (e: Exception) {
            Log.e(LOG_TAG, e.message, e)
            is1 = FileInputStream(filePath)
            val array = streamToBytes(is1)
            mMovie = Movie.decodeByteArray(array, 0, array.size)
        }
        p = Paint()
    }

    @Throws(FileNotFoundException::class)
    fun setAnimatedGif(byteArray: ByteArray, streachType: TYPE) {
        setImageBitmap(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        mType = streachType
        animatedGifImage = true
        try {
            mMovie = Movie.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            Log.e(LOG_TAG, e.message, e)
        }
        p = Paint()
    }

    override fun setImageResource(resId: Int) {
        animatedGifImage = false
        super.setImageResource(resId)
    }

    override fun setImageURI(uri: Uri?) {
        animatedGifImage = false
        super.setImageURI(uri)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        animatedGifImage = false
        super.setImageDrawable(drawable)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mMovie != null) {
            val movieWidth = mMovie!!.width()
            val movieHeight = mMovie!!.height()
            /*
             * Calculate horizontal scaling
			 */
            val measureModeWidth = MeasureSpec.getMode(widthMeasureSpec)
            var scaleW = 1f
            var scaleH = 1f
            if (measureModeWidth != MeasureSpec.UNSPECIFIED) {
                val maximumWidth = MeasureSpec.getSize(widthMeasureSpec)
                scaleW = if (movieWidth > maximumWidth) {
                    movieWidth.toFloat() / maximumWidth.toFloat()
                } else {
                    maximumWidth.toFloat() / movieWidth.toFloat()
                }
            }

            /*
			 * calculate vertical scaling
			 */
            val measureModeHeight = MeasureSpec.getMode(heightMeasureSpec)
            if (measureModeHeight != MeasureSpec.UNSPECIFIED) {
                val maximumHeight = MeasureSpec.getSize(heightMeasureSpec)
                scaleH = if (movieHeight > maximumHeight) {
                    movieHeight.toFloat() / maximumHeight.toFloat()
                } else {
                    maximumHeight.toFloat() / movieHeight.toFloat()
                }
            }
            when (mType) {
                TYPE.FIT_CENTER -> {
                    mScaleW = Math.min(scaleH, scaleW)
                    mScaleH = mScaleW
                }
                TYPE.AS_IS -> {
                    mScaleW = 1f
                    mScaleH = mScaleW
                }
                TYPE.STREACH_TO_FIT -> {
                    mScaleH = scaleH
                    mScaleW = scaleW
                }
                else -> {
                }
            }
            mMeasuredMovieWidth = (movieWidth * mScaleW).toInt()
            mMeasuredMovieHeight = (movieHeight * mScaleH).toInt()
            setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight)
        } else {
            setMeasuredDimension(suggestedMinimumWidth,
                    suggestedMinimumHeight)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        mLeft = (width - mMeasuredMovieWidth) / 2f
        mTop = (height - mMeasuredMovieHeight) / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (animatedGifImage) {
            val now = SystemClock.uptimeMillis()
            if (mMovieStart == 0L) { // first time
                mMovieStart = now
            }
            if (mMovie != null) {
                p!!.isAntiAlias = true
                var dur = mMovie!!.duration()
                if (dur == 0) {
                    dur = 1000
                }
                val relTime = ((now - mMovieStart) % dur).toInt()
                mMovie!!.setTime(relTime)
                canvas.save()
                canvas.scale(mScaleW, mScaleH)
                mMovie!!.draw(canvas, mLeft / mScaleW, mTop / mScaleH)
                canvas.restore()
                invalidate()
            }
        }
    }

    enum class TYPE {
        FIT_CENTER, STREACH_TO_FIT, AS_IS
    }

    companion object {
        private const val LOG_TAG = "AnimatedGifImageView"
        private fun streamToBytes(`is`: InputStream): ByteArray {
            val os = ByteArrayOutputStream(1024)
            val buffer = ByteArray(1024)
            var len: Int
            try {
                while (`is`.read(buffer).also { len = it } >= 0) {
                    os.write(buffer, 0, len)
                }
            } catch (e: IOException) {
                Log.e(LOG_TAG, e.message, e)
            }
            return os.toByteArray()
        }
    }
}