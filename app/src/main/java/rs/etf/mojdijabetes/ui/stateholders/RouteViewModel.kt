//package rs.etf.mojdijabetes.ui.stateholders
//
//import androidx.lifecycle.ViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import rs.etf.mojdijabetes.data.Route
//import rs.etf.mojdijabetes.data.staticRoutes
//import javax.inject.Inject
//
//data class RouteUiState(
//    val routes: List<Route> = staticRoutes,
//)
//
//@HiltViewModel
//class RouteViewModel @Inject constructor() : ViewModel() {
//    private val _uiState = MutableStateFlow(RouteUiState())
//    val uiState = _uiState.asStateFlow()
//}