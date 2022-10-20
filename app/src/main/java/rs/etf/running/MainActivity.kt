package rs.etf.running

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import rs.etf.running.ui.elements.RunningApp
import rs.etf.running.ui.elements.theme.RunningApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RunningApplicationTheme {
                RunningApp()
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RunningApplicationTheme {
        RunningApp()
    }
}