package com.untenty.nauticalknots

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.untenty.nauticalknots.data.DataRepository
import com.untenty.nauticalknots.data.Settings
import com.untenty.nauticalknots.entity.LanguageEnum
import com.untenty.nauticalknots.entity.ThemeEnum


@Composable
fun SettingsScreen(context: Context, viewModel: MainViewModel) {
    Column {
        ThemeSetting(context)
        LanguageSetting(context, viewModel)
    }
}

@Composable
fun ThemeSetting(context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Text(text = "${stringResource(R.string.theme_title)}:", fontSize = 20.sp)
            ThemeEnum.entries.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    RadioButton(
                        selected = (Settings.theme.value == it),
                        onClick = {
                            Settings.theme.value = it
                            Settings.saveAppSettings(context)
                        }
                    )
                    Text(it.getTitle(LocalContext.current))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSetting(context: Context, viewModel: MainViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val localContext = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.Gray),
    ) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
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
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    LanguageEnum.entries.forEach {
                        DropdownMenuItem(
                            onClick = {
                                viewModel.setLocale(localContext, it.name)
                                Settings.language.value = it
                                Settings.saveAppSettings(context)
                                DataRepository.readKnots()
                                expanded = false
                            },
                            text = { Text(it.title) }
                        )
                    }
                }
            }
        }
    }
}