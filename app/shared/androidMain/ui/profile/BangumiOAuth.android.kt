package me.him188.ani.app.ui.profile

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.him188.ani.app.platform.LocalContext
import me.him188.ani.app.platform.currentAniBuildConfig
import me.him188.ani.app.session.BangumiAuthorizationConstants

private enum class LaunchChromeStatus {
    LAUNCHING,
    LAUNCHED,
    FAILED,
}

@Composable
actual fun BangumiOAuthRequest(
    vm: AuthViewModel,
    onFailed: (Throwable) -> Unit,
    modifier: Modifier,
) {
    var launchChromeStatus: LaunchChromeStatus by remember {
        mutableStateOf(LaunchChromeStatus.LAUNCHING)
    }
    val context = LocalContext.current
    if (launchChromeStatus == LaunchChromeStatus.LAUNCHING) {
        LaunchedEffect(key1 = true) {
            val intent = CustomTabsIntent.Builder().build()
            runCatching {
                intent.launchUrl(
                    context,
                    Uri.parse(BangumiAuthorizationConstants.makeOAuthUrl(currentAniBuildConfig.bangumiOauthClientId))
                )
                launchChromeStatus = LaunchChromeStatus.LAUNCHED
            }.onFailure {
                launchChromeStatus = LaunchChromeStatus.FAILED
            }
        }
    }

    Box(modifier, contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "请在浏览器中完成授权")

            if (launchChromeStatus == LaunchChromeStatus.LAUNCHED) {
                Button(onClick = { launchChromeStatus = LaunchChromeStatus.LAUNCHING }) {
                    Text(text = "重试")
                }
            }
        }
    }
}