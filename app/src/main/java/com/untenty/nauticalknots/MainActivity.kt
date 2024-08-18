package com.untenty.nauticalknots

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.util.DisplayMetrics
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.untenty.nauticalknots.data.DataRepository
import com.untenty.nauticalknots.data.Settings
import com.untenty.nauticalknots.entity.FavoriteKnot
import com.untenty.nauticalknots.entity.Knot
import com.untenty.nauticalknots.entity.Tag
import com.untenty.nauticalknots.entity.ThemeK
import com.untenty.nauticalknots.navigation.AppNavGraph
import com.untenty.nauticalknots.navigation.Screen
import com.untenty.nauticalknots.ui.theme.NauticalknotsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//val LocalAppLocale = staticCompositionLocalOf { Locale.getDefault() }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataRepository.init(applicationContext)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setDimension(viewModel, this)

//        val locale = loadLocale(this)

        setContent {
            viewModel.setLocale(LocalContext.current, Settings.language.value.name)
//            val locale = Locale(Settings.language.value.name)
//            val currentLocale = remember { locale }
//            LocaleProvider(locale = currentLocale) {
            NauticalknotsTheme(
                darkTheme = ((Settings.theme.value == ThemeK.SYSTEM) and (isSystemInDarkTheme())) or (Settings.theme.value == ThemeK.DARK),
                dynamicColor = Settings.theme.value == ThemeK.SYSTEM
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()//, color = MaterialTheme.colorScheme.background
                ) {
                    Screens(viewModel, applicationContext)
                }
            }
//            }
        }
    }
}

//@Composable
//fun LocaleProvider(
//    locale: Locale,
//    content: @Composable () -> Unit
//) {
//    CompositionLocalProvider(LocalAppLocale provides locale) {
//        content()
//    }
//}

fun setDimension(viewModel: MainViewModel, context: ComponentActivity) {
    val displayMetrics = DisplayMetrics()
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        val metrics: WindowMetrics =
            context.getSystemService(WindowManager::class.java).currentWindowMetrics
        viewModel.heightScreen = metrics.bounds.width()
        viewModel.widthScreen = metrics.bounds.height()
    } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val display = context.display
        @Suppress("DEPRECATION")
        display?.getRealMetrics(displayMetrics)
        viewModel.heightScreen = displayMetrics.heightPixels
        viewModel.widthScreen = displayMetrics.widthPixels
    } else {
        @Suppress("DEPRECATION")
        val display = context.windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(displayMetrics)
        viewModel.heightScreen = displayMetrics.heightPixels
        viewModel.widthScreen = displayMetrics.widthPixels
    }
}

@Composable
fun Screens(viewModel: MainViewModel, context: Context) {
    val navHostController = rememberNavController()
    AppNavGraph(navHostController,
        knotsListScreenContent = { MainScreen(viewModel, navHostController) },
        knotCardScreenContent = { KnotCardScreen(viewModel, context) },
        settingsScreenContent = { SettingsScreen(context, viewModel) })
}

@Composable
fun MainScreen(viewModel: MainViewModel, navHostController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        Menu(navHostController, drawerState, scope)
    }, content = {
        Scaffold(topBar = {
            TopBar(viewModel, drawerState, scope)
        }, floatingActionButton = {

        }, floatingActionButtonPosition = FabPosition.End
        ) {
            val tabs = listOf(
                stringResource(R.string.all_title),
                stringResource(R.string.tags_title),
                stringResource(R.string.favorites_title)
            )
            val contentScreens: List<@Composable () -> Unit> = listOf(
                { KnotsList(viewModel, navHostController) },
                { TagsListScreen(viewModel, navHostController) },
                { FavoriteListScreen(viewModel, navHostController) })
            TabRowComponent(
                tabs = tabs,
                contentScreens = contentScreens,
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Gray,
                contentColor = Color.White,
                indicatorColor = Color.DarkGray,
                it, viewModel
            )
        }
    })
}

@Composable
fun TabRowComponent(
    tabs: List<String>,
    contentScreens: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Gray,
    contentColor: Color = Color.White,
    indicatorColor: Color = Color.DarkGray,
    paddings: PaddingValues, viewModel: MainViewModel
) {
    val selectedTabIndex = viewModel.selectedTabIndex.value
    Column(
        modifier = modifier.padding(paddings)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = containerColor,
            contentColor = contentColor,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = indicatorColor
                )
            },
            divider = @Composable {

            }
        ) {
            tabs.forEachIndexed { index, tabTitle ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { viewModel.selectedTabIndex.value = index }
                ) {
                    Text(text = tabTitle, modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                }
            }
        }
        contentScreens.getOrNull(selectedTabIndex)?.invoke()
    }
}

@Composable
fun KnotsList(
    viewModel: MainViewModel,
    navHostController: NavHostController
) {
    val listKnots = viewModel.getKnots()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        itemsIndexed(items = listKnots.value, key = { _, item -> item.hashCode() }) { _, item ->
            KnotItem(viewModel, item, navHostController)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KnotItem(
    viewModel: MainViewModel,
    knot: Knot,
    navHostController: NavHostController
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .combinedClickable(
                onClick = {
                    viewModel.openKnot(knot.id)
                    navHostController.navigate(Screen.KnotCard.route)
                }, onLongClick = {
                    viewModel.modeSelect(true)
                    viewModel.selectItem(knot.id)
                })
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                viewModel.getLanguageString(knot.name),
                modifier = Modifier.padding(start = 10.dp)
            )
            if (viewModel.modeSelect.value) {
                var isSelect by remember(knot.id) {
                    mutableStateOf(
                        viewModel.selectedItems.value.contains(
                            knot.id
                        )
                    )
                }
                Spacer(Modifier)
                IconToggleButton(
                    checked = isSelect,
                    onCheckedChange = {
                        isSelect = !isSelect
                        if (isSelect)
                            viewModel.selectItem(knot.id)
                        else
                            viewModel.unselectItem(knot.id)
                    }
                ) {
                    if (isSelect)
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(
                                color = Color.Red,
                                radius = size.minDimension / 4
                            )
                        }
                    else
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(
                                color = Color.White,
                                radius = size.minDimension / 4
                            )
                        }
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    knot: Knot,
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63)
) {
    var isFavorite by remember { mutableStateOf(DataRepository.isKnotFavorite(knot)) }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
            if (isFavorite)
                DataRepository.insertFavoriteKnot(
                    FavoriteKnot(
                        knot.id,
                        DataRepository.getFavoriteKnots().value.size.toLong()
                    )
                )
            else
                DataRepository.deleteFavoriteKnots(knot.id)
        }
    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    viewModel: MainViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    TopAppBar(
        title = {
            if (viewModel.showSearchText.value) {
                val focusRequester = remember { FocusRequester() }
                TextField(
                    value = viewModel.searchText.value,
                    onValueChange = {
                        viewModel.searchText.value = it
                        DataRepository.readKnots(viewModel.searchText.value)
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    trailingIcon = {
                        Icon(Icons.Default.Clear,
                            contentDescription = "clear text",
                            modifier = Modifier
                                .clickable {
                                    viewModel.showSearchText.value = false
                                    viewModel.searchText.value = ""
                                    DataRepository.readKnots()
                                }
                        )
                    }
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else
                Text(stringResource(R.string.app_title))

        },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            Row {
                IconButton(onClick = {
                    viewModel.showSearchText.value = !viewModel.showSearchText.value
                    if (!viewModel.showSearchText.value) {
                        viewModel.searchText.value = ""
                        DataRepository.readKnots()
                    }
                }) {
                    Icon(Icons.Filled.Search, contentDescription = null)
                }
                if (viewModel.modeSelect.value) {
                    IconButton(onClick = {
                        viewModel.modeSelect(false)
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "cancel select")
                    }
                    IconButton(onClick = {
                        viewModel.unfavoriteSelectItems()
                    }) {
                        Icon(
                            tint = Color(0xffE91E63),
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "delete favorite"
                        )
                    }
                    IconButton(onClick = {
                        viewModel.favoriteSelectItems()
                    }) {
                        Icon(
                            tint = Color(0xffE91E63),
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "delete favorite"
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun Menu(navHostController: NavHostController, drawerState: DrawerState, scope: CoroutineScope) {
    ModalDrawerSheet(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
    ) {
        Text(stringResource(R.string.menu_title), modifier = Modifier.padding(16.dp))
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(text = stringResource(R.string.settings_title)) },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            selected = false,
            onClick = {
                navHostController.navigate(Screen.Settings.route)
                scope.launch { drawerState.close() }
            }
        )
    }
}

@Composable
fun KnotCardScreen(
    viewModel: MainViewModel,
    context: Context
) {
    val knot = viewModel.selectedElement
    if (knot != null) {
        Column {
            val (modifier1, modifier2) = if (viewModel.showHideDescription.value) {
                Pair(Modifier.weight(1f), Modifier.weight(2f))
            } else {
                Pair(Modifier.weight(1f), Modifier)
            }
            Box(modifier = modifier1) {
                ImageKnot(knot, viewModel, context, modifier = Modifier)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) { FavoriteButton(knot) }
            }
            DescriptionKnot(knot, viewModel, modifier = modifier2)
        }
    }
}

@Composable
fun ImageKnot(knot: Knot, viewModel: MainViewModel, context: Context, modifier: Modifier) {
    val numSelect = remember { mutableIntStateOf(0) }
    val nameFile = knot.pictures[numSelect.intValue]
    val bitmap = viewModel.loadImageFromAssets(context, "knot$nameFile.png") ?: return
    val offsetX = remember { mutableFloatStateOf(0f) }
    val colorFilterInverse = ColorFilter.colorMatrix(
        ColorMatrix(
            floatArrayOf(
                -1f, 0f, 0f, 0f, 255f,
                0f, -1f, 0f, 0f, 255f,
                0f, 0f, -1f, 0f, 255f,
                0f, 0f, 0f, 1f, 0f
            )
        )
    )
    val colorFilter =
        if ((Settings.theme.value == ThemeK.DARK) or ((Settings.theme.value == ThemeK.SYSTEM) and (isSystemInDarkTheme()))) colorFilterInverse else null

    Column(modifier = Modifier.padding(10.dp)) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "",
            colorFilter = colorFilter,
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX.floatValue += dragAmount.x
                        if (offsetX.floatValue > viewModel.widthScreen / (knot.pictures.size + 1)) {
                            if (numSelect.intValue < knot.pictures.size - 1) {
                                numSelect.intValue++
                            }
                            offsetX.floatValue = 0f
                        } else if (offsetX.floatValue < -viewModel.widthScreen / (knot.pictures.size + 1)) {
                            if (numSelect.intValue > 0) {
                                numSelect.intValue--
                            }
                            offsetX.floatValue = 0f
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        if (offset.x > viewModel.widthScreen - viewModel.widthScreen / 6) {
                            if (numSelect.intValue < knot.pictures.size - 1) {
                                numSelect.intValue++
                            }
                        } else if (offset.x < viewModel.widthScreen / 6) {
                            if (numSelect.intValue > 0) {
                                numSelect.intValue--
                            }
                        }
                    }
                }
        )
        if (knot.pictures.size > 1) {
            Slider(
                value = numSelect.intValue.toFloat(),
                onValueChange = { numSelect.intValue = it.toInt() },
                steps = knot.pictures.size - 2,
                valueRange = 0f..(knot.pictures.size - 1).toFloat(),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun DescriptionKnot(knot: Knot, viewModel: MainViewModel, modifier: Modifier) {
    SelectionContainer(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = viewModel.getLanguageString(knot.name),
                    fontSize = TextUnit(28f, TextUnitType.Sp),
                    modifier = Modifier.weight(10f)
                )
                ShowHideDescriptionButton(viewModel, Modifier.weight(1f))
            }
            if (viewModel.showHideDescription.value) {
                HtmlTextField(
                    htmlText = viewModel.getLanguageString(knot.description)
                )
            }
        }
    }
}

@Composable
fun ShowHideDescriptionButton(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray
) {
    IconToggleButton(
        checked = viewModel.showHideDescription.value,
        onCheckedChange = {
            viewModel.showHideDescription.value = !viewModel.showHideDescription.value
        },
        modifier = modifier
    ) {
        Icon(
            tint = color,
            modifier = Modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (viewModel.showHideDescription.value) {
                Icons.Default.KeyboardArrowDown
            } else {
                Icons.Default.KeyboardArrowUp
            },
            contentDescription = null
        )
    }

}

@Composable
fun HtmlTextField(
    htmlText: String,
    baseSpanStyle: SpanStyle? = null,
    isHighlightLink: Boolean = false,
    onUrlClick: ((url: String) -> Unit)? = null
) {
    val formattedText = Html.fromHtml(htmlText.replace("\n", "<br>"), Html.FROM_HTML_MODE_COMPACT)
    val uriHandler = LocalUriHandler.current
    val linkColor = if (isHighlightLink) Color.Blue else Color.Unspecified
    val annotatedString =
        formattedText.toAnnotateString(baseSpanStyle = baseSpanStyle, linkColor = linkColor)
    val colorText =
//        if (Settings.theme.value == ThemeK.DARK) Color.White else Color.Black
        if (((Settings.theme.value == ThemeK.SYSTEM) and (isSystemInDarkTheme())) or (Settings.theme.value == ThemeK.DARK)) Color.White else Color.Black
    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        text = annotatedString,
        style = TextStyle(
            textAlign = TextAlign.Justify,
            color = colorText,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        ),
    ) { offset ->
        annotatedString.getStringAnnotations("url", offset, offset).firstOrNull()?.let {
            onUrlClick?.let { click -> click(it.item) } ?: uriHandler.openUri(it.item)
        }
    }
}

fun Spanned.toAnnotateString(
    baseSpanStyle: SpanStyle?, linkColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        val spanned = this@toAnnotateString
        append(spanned.toString())
        baseSpanStyle?.let { addStyle(it, 0, length) }
        getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = getSpanStart(span)
            val end = getSpanEnd(span)
            when (span) {
                is StyleSpan -> when (span.style) {
                    Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                    Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                    Typeface.BOLD_ITALIC -> addStyle(
                        SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic),
                        start,
                        end
                    )
                }

                is UnderlineSpan -> addStyle(
                    SpanStyle(textDecoration = TextDecoration.Underline), start, end
                )

                is ForegroundColorSpan -> addStyle(
                    SpanStyle(color = Color(span.foregroundColor)), start, end
                )

                is URLSpan -> {
                    addStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.Underline, color = linkColor
                        ), start, end
                    )
                    addStringAnnotation("url", span.url, start, end)
                }
            }
        }
    }
}

@Composable
fun TagsListScreen(
    viewModel: MainViewModel,
    navHostController: NavHostController
) {
    viewModel.initKnotsOfTags()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(DataRepository.getTags().value) { tag ->
            TagItem(viewModel, tag)
            viewModel.getKnotsByTag(tag)?.apply {
                for (id in this.value) {
                    DataRepository.getKnot(id)?.apply {
                        KnotItem(viewModel, this, navHostController)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TagItem(
    viewModel: MainViewModel,
    tag: Tag
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.errorContainer)
    ) {
        val lifeCycleOwner = LocalLifecycleOwner.current
        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp)
                .combinedClickable(
                    onClick = {
                        viewModel.openCloseKnotsTag(lifeCycleOwner, tag)
                    }, onLongClick = {

                    }), verticalAlignment = Alignment.CenterVertically
        ) {
            val prefix = if (viewModel.getKnotsByTag(tag)?.value?.size == 0) "+" else "-"
            Text(
                "$prefix ${viewModel.getLanguageString(tag.name)}",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 10.dp)
            )
        }
    }

}

@Composable
fun FavoriteListScreen(
    viewModel: MainViewModel,
    navHostController: NavHostController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(DataRepository.getFavoriteKnots().value) { favKnot ->
            DataRepository.getKnot(favKnot.id)?.apply {
                FavoriteKnotItem(viewModel, this, navHostController)
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoriteKnotItem(
    viewModel: MainViewModel,
    knot: Knot,
    navHostController: NavHostController
) {
    val dismissState = SwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    DataRepository.deleteFavoriteKnots(knot.id)
                }

                SwipeToDismissBoxValue.EndToStart -> {

                }

                SwipeToDismissBoxValue.Settled -> return@SwipeToDismissBoxState false
            }
            return@SwipeToDismissBoxState true
        },
        positionalThreshold = { it * .60f }, // positional threshold of 45%,
        density = Density(1f)
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground(dismissState) },
        enableDismissFromEndToStart = false,
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .combinedClickable(
                    onClick = {
                        viewModel.openKnot(knot.id)
                        navHostController.navigate(Screen.KnotCard.route)
                    }, onLongClick = {

                    })

        ) {
            KnotItem(viewModel, knot, navHostController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color(0xFFFF1744)
        SwipeToDismissBoxValue.EndToStart -> Color.Transparent
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .height(50.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "delete"
            )
            Text(
                "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            )
        }
    }
}