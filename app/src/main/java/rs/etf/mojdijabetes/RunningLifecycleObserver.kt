//package rs.etf.mojdijabetes
//
//import android.util.Log
//import androidx.lifecycle.DefaultLifecycleObserver
//import androidx.lifecycle.LifecycleOwner
//
//// https://developer.android.com/topic/libraries/architecture/lifecycle
//// https://developer.android.com/reference/androidx/lifecycle/DefaultLifecycleObserver
//class RunningLifecycleObserver : DefaultLifecycleObserver {
//
//    override fun onCreate(owner: LifecycleOwner) {
//        super.onCreate(owner)
//        Log.d(LOG_TAG, "onCreate")
//    }
//
//    override fun onStart(owner: LifecycleOwner) {
//        super.onStart(owner)
//        Log.d(LOG_TAG, "onStart")
//    }
//
//    override fun onResume(owner: LifecycleOwner) {
//        super.onResume(owner)
//        Log.d(LOG_TAG, "onResume")
//    }
//
//    override fun onPause(owner: LifecycleOwner) {
//        super.onPause(owner)
//        Log.d(LOG_TAG, "onPause")
//    }
//
//    override fun onStop(owner: LifecycleOwner) {
//        super.onStop(owner)
//        Log.d(LOG_TAG, "onStop")
//    }
//
//    override fun onDestroy(owner: LifecycleOwner) {
//        super.onDestroy(owner)
//        Log.d(LOG_TAG, "onDestroy")
//    }
//}