package edu.ucne.jugadorestictactoe.presentation.Navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menú",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Jugadores") },
                    selected = currentRoute == Screen.JugadorApiList::class.qualifiedName,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.JugadorApiList)
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Jugadores") }
                )

                NavigationDrawerItem(
                    label = { Text("Partidas") },
                    selected = currentRoute == Screen.PartidaList::class.qualifiedName,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.PartidaList)
                              },
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Partidas") }
                )

                NavigationDrawerItem(
                    label = { Text("Tecnico") },
                    selected = currentRoute == Screen.PartidaList::class.qualifiedName,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.TecnicoList)
                    },
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Tecnico") }
                )

                NavigationDrawerItem(
                    label = { Text("Logros") },
                    selected = currentRoute == Screen.LogroList::class.qualifiedName,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.LogroList)
                    },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Logros") }
                )

                NavigationDrawerItem(
                    label = { Text("Jugar") },
                    selected = currentRoute == Screen.GameScreen::class.qualifiedName,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.GameScreen)
                    },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Jugar") }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = { Text("Aplicada 2") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { paddingValues ->
            HostNavigation(
                navHostController = navController,
                modifier = Modifier.padding(paddingValues))
        }
    }
}
