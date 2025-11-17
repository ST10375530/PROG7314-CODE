package vcmsa.projects.petcareapp.Data.Network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.Flow
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class AndroidConnectivityObserver(private val context: Context): ConnectivityObserver
{
    //
    private  val connectivityManager = context.getSystemService<ConnectivityManager>()!!
    //this uses a callback flow - everytime the internet connection changes it will trigger like an observer (Lackner, 2024):
    override val isConnected: Flow<Boolean>
        get() = callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback()
            {
                //hardware wise: The device's ability to connect to the internet. Does not check routers/connected internets availability/connectivity
                //when network is working fully - hardware wise
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    //will return a boolean value of true that we can use in the UI (Lackner, 2024):
                    trySend(true)
                }

                //when the network connection is lost - hardware wise
                override fun onLost(network: Network) {
                    super.onLost(network)
                    //will return a boolean value of false that we can use in the UI (Lackner, 2024):
                    trySend(false)
                }
                //when the network is not available - hardware wise
                override fun onUnavailable() {
                    super.onUnavailable()
                    //will return a boolean value of false that we can use in the UI (Lackner, 2024):
                    trySend(false)
                }
                //checks the actual connected wifi's capabilities - not hardware related (Lackner, 2024):
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                        trySend(true)
                    }
                }
            }
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            connectivityManager.registerNetworkCallback(request, callback)

            awaitClose{
                connectivityManager.unregisterNetworkCallback(callback)
            }

        }
}

// Lackner, P. 2024. How to Observe the REAL Internet Connectivity - Android Studio Kotlin Tutorial. [video online] Available at: https://www.youtube.com/watch?v=wvDPG2iQ-OE [Accessed 17 november 2025].