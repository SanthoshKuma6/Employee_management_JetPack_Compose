package com.macapp.employeemanagement.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.macapp.employeemanagement.screens.BottomBarScreen
import com.macapp.employeemanagement.screens.MyEmployee
import com.macapp.employeemanagement.screens.MyProfile

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomBarScreen.MyEmployee.route ){
        composable(route= BottomBarScreen.MyEmployee.route){
           MyEmployee()
        }
        composable(route= BottomBarScreen.Profile.route){
            MyProfile()
        }
    }

}