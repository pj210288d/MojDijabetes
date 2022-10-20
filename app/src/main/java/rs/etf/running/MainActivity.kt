package rs.etf.running

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import rs.etf.running.ui.elements.RunningApp
import rs.etf.running.ui.elements.theme.RunningApplicationTheme

const val LOG_TAG = "running-app"

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MainLifecycleObserver())
        setContent {
            RunningApplicationTheme {
                // https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes
                RunningApp(calculateWindowSizeClass(activity = this))
            }
        }
    }
}