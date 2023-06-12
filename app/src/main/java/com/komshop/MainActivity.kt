package com.komshop

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.komshop.data.Session
import com.komshop.data.Session.processing
import com.komshop.navigation.Routing
import com.komshop.ui.componets.Loader
import com.komshop.ui.dialog.ProcessLoader
import com.komshop.ui.pages.GlobalUiHandler
import com.komshop.ui.theme.AuctionMobileAppTheme
import com.komshop.ui.viewmodel.BidingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            AuctionMobileAppTheme {
                val biddingViewModel: BidingViewModel = hiltViewModel()

                val color = MaterialTheme.colorScheme.primary
                LaunchedEffect(true) {
                    Session.statusColor.value = color
                }
                GlobalUiHandler(
                    modifier = Modifier.fillMaxSize(),
                    biddingViewModel
                ) {
                    PermissionCheck()
                    Routing(biddingViewModel)
                    ProcessLoader(processing)
                    Loader()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun PermissionCheck() {
        val multiplePermissionsState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            rememberMultiplePermissionsState(permissions = listOf(
//                Manifest.permission.BLUETOOTH,
//                Manifest.permission.CAMERA,
//                Manifest.permission.BLUETOOTH_ADMIN,
//                Manifest.permission.BLUETOOTH_CONNECT,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
            ))
        } else {
            rememberMultiplePermissionsState(permissions = listOf(
//                Manifest.permission.BLUETOOTH,
//                Manifest.permission.CAMERA,
//                Manifest.permission.BLUETOOTH_ADMIN,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
            ))
        }

        LaunchedEffect(key1 = true) {
            multiplePermissionsState.launchMultiplePermissionRequest()
        }

//    var hasCamPermission by remember {
//        mutableStateOf(
//            ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED
//        )
//    }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = { granted ->
//            hasCamPermission = granted
//        })
//
//    LaunchedEffect(key1 = true) {
//        launcher.launch(Manifest.permission.CAMERA)
//    }
    }

}
