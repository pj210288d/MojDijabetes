package rs.etf.running.ui.stateholders

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import rs.etf.running.data.Route
import rs.etf.running.data.staticRoutes

data class RouteUiState(
    val routes: List<Route> = staticRoutes,
)

class RouteViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RouteUiState())
    val uiState = _uiState.asStateFlow()
}