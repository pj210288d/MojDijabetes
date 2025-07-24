package rs.etf.mojdijabetes.domain.usecase

import javax.inject.Inject

import rs.etf.mojdijabetes.data.local.UserPreferences

class SignOutUseCase @Inject constructor(
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke() {
        userPreferences.setLoggedOut()
    }
}