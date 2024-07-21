package com.untenty.nauticalknots

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.untenty.nauticalknots.data.Repa
import com.untenty.nauticalknots.data.Settings
import com.untenty.nauticalknots.entity.LanguageK
import com.untenty.nauticalknots.entity.ThemeK


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(context: Context, viewModel: MainViewModel) {
    //var theme by remember { mutableStateOf(Settings.getAppSettings().theme) }
    //var language by remember { mutableStateOf(Settings.language) }
    var expanded by remember { mutableStateOf(false) }
    val localContext = LocalContext.current
    Column(modifier = Modifier.padding(all = 10.dp)) {
        Text(text = "${stringResource(R.string.theme_title)}:", fontSize = 20.sp)
        Column {
            ThemeK.entries.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(it.getTitle(localContext))
                    RadioButton(
                        selected = (Settings.theme.value == it),
                        onClick = {
                            Settings.theme.value = it
                            Settings.saveAppSettings(context)
                        }
                    )
                }
            }
        }
        Text(
            text = "${stringResource(R.string.lang_title)}:",
            modifier = Modifier.padding(top = 10.dp),
            fontSize = 20.sp
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = Settings.language.value.title,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                LanguageK.entries.forEach {
                    DropdownMenuItem(
                        onClick = {
                            viewModel.setLocale(localContext, it.name)
                            Settings.language.value = it
                            Settings.saveAppSettings(context)
                            Repa.readKnots()
                            expanded = false
                        },
                        text = { Text(it.title) }
                    )
                }
            }
        }
    }
}