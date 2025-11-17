package vcmsa.projects.petcareapp.Data.Network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
}

// Lackner, P. 2024. How to Observe the REAL Internet Connectivity - Android Studio Kotlin Tutorial. [video online] Available at: https://www.youtube.com/watch?v=wvDPG2iQ-OE [Accessed 17 november 2025].