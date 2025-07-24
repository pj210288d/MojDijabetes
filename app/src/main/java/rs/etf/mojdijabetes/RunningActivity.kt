//package rs.etf.mojdijabetes
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
//import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
//import dagger.hilt.android.AndroidEntryPoint
//import rs.etf.mojdijabetes.ui.elements.RunningApp
//import rs.etf.mojdijabetes.ui.elements.theme.RunningApplicationTheme
//
//const val LOG_TAG = "running-app"
//
//@AndroidEntryPoint
//class RunningActivity : ComponentActivity() {
//    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        lifecycle.addObserver(RunningLifecycleObserver())
//        setContent {
//            RunningApplicationTheme {
//                RunningApp(calculateWindowSizeClass(activity = this))
//            }
//        }
//    }
//}