import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import cn.losefeather.compose.MainActivity

class StartupTracker  constructor() {
    internal var startTime: Long = 0
    
    private val lifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (activity is MainActivity) {
                startTime = System.nanoTime()
            }
        }

        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }
    
    companion object {
        fun init(application: Application) {
            val tracker = StartupTracker()
            application.registerActivityLifecycleCallbacks(tracker.lifecycleCallbacks)
        }
    }
}

@Composable
fun TrackFirstComposition() {
    val startupTracker = remember { StartupTracker() }
    val view = LocalView.current
    
    DisposableEffect(view) {
        val viewTreeObserver = view.viewTreeObserver
        val preDrawListener = ViewTreeObserver.OnPreDrawListener {
            val endTime = System.nanoTime()
            val startupTime = (endTime - startupTracker.startTime) / 1_000_000
            Log.d("Performance", "First frame rendered in: ${startupTime}ms")
            true
        }
        
        viewTreeObserver.addOnPreDrawListener(preDrawListener)
        onDispose {
            viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        }
    }
} 