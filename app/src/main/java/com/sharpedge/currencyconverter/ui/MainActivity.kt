package com.sharpedge.currencyconverter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sharpedge.currencyconverter.ui.screen.CurrencyConversionScreen
import com.sharpedge.currencyconverter.ui.theme.CurrencyConverterTheme
import com.sharpedge.currencyconverter.ui.utils.ROUTE_HISTORY
import com.sharpedge.currencyconverter.ui.utils.ROUTE_HOME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        val currentRoute = navController.currentRoute()


        Scaffold(topBar = {
            val canPop = navController.previousBackStackEntry != null
            TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ), title = {
                Text(
                    text = when (currentRoute) {
                        ROUTE_HOME -> "Currency Converter Home"
                        ROUTE_HISTORY -> "History"
                        else -> "Currency Converter Home"
                    }
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        if (canPop) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack, "backIcon", tint = Color.White
                        )
                    }
                }

            )
        }) { innerPadding ->

            AppNavigation(Modifier.padding(innerPadding), navController)
        }
    }


    @Composable
    fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = ROUTE_HOME
        ) {
            composable(ROUTE_HOME) { CurrencyConversionScreen {
                navController.navigate(ROUTE_HISTORY)
            } }
            // History screen here
        }
    }


    @Composable
    private fun NavController.currentRoute(): String? {
        val navBackStackEntry by currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CurrencyConverterTheme {
            //Greeting("Android")
        }
    }

}