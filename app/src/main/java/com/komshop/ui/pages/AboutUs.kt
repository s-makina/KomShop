package com.komshop.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.komshop.Config.API_ADDRESS
import com.komshop.Config.EMAIL
import com.komshop.Config.WHATSAPP_NUMBER
import com.komshop.R
import com.komshop.copyToClipboard
import com.komshop.sendWapMsg
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav

@Composable
fun AboutUs(navController: NavHostController) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    PageBackground(
        topBar = {
            TopNav(navController = navController, backButton = true)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "We Are Your Favourite Store", style = MaterialTheme.typography.titleLarge)
            Text(text = stringResource(id = R.string.about_description), style = MaterialTheme.typography.bodyMedium)

            Text(text = "Contact us", style = MaterialTheme.typography.titleMedium)
            Column() {
                Text(text = "Phone: +265 880 803 830", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Email: hello@Komshoponline.com", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Location: Area 24,street no: 6 Lilongwe Malawi", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Website: https://komshop.net", style = MaterialTheme.typography.bodyMedium)
            }

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = {  copyToClipboard(context, "+265880803830") }) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null)
                }

                IconButton(onClick = { sendWapMsg(context, WHATSAPP_NUMBER, "") }) {
                    Icon(imageVector = Icons.Default.Whatsapp, contentDescription = null)
                }

                IconButton(onClick = { copyToClipboard(context, EMAIL) }) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                }

                IconButton(onClick = { uriHandler.openUri(API_ADDRESS) }) {
                    Icon(imageVector = Icons.Default.Language, contentDescription = null)
                }
            }
        }
    }

}