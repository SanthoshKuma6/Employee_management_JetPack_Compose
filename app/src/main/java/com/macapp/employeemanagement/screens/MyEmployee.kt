package com.macapp.employeemanagement.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.AddEmployeeActivity
import com.macapp.employeemanagement.activity.ProfileDetailsActivity
import com.macapp.employeemanagement.model_class.login.EmployeeList
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyEmployee() {
    val employeeList: ArrayList<EmployeeList.Data?> = ArrayList()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            LoginRepository(ApiService.NetworkClient.apiService)
        )
    )
    var Boolean by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxSize()

    ) {

        val token = DataStoredPreference(context).getUSerData()["loginToken"]
        if (!Boolean) {
            coroutineScope.launch {
                viewModel.getEmployeeList(token.toString())
                Boolean = true
            }

        }

            val employeeState = viewModel.employeeState.collectAsStateWithLifecycle()
            when (val result = employeeState.value) {
                is Response.Loading -> {}

                is Response.Success -> {
                    result.data?.data?.let { it1 ->

                        HomeMainScreen(it1)
                        employeeList.clear()
                        employeeList.addAll(it1)
                    }
                }

                is Response.Error -> {
                    val errorMessage = result.errorMessage
                    Toast.makeText(context, "$errorMessage", Toast.LENGTH_LONG).show()
                }

                else -> {}
            }

        }


    }



@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeatureItem(data: EmployeeList.Data?) {
    val bold = FontFamily(Font(R.font.sf_pro_bold))

    val context = LocalContext.current
//New code base

    Box(
        modifier = Modifier
            .background(color = colorResource(id = R.color.white))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_color),
                shape = RoundedCornerShape(4.dp)
            )

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(19.dp))
            Image(
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(model = data?.photo),
                contentDescription = "",
            )
            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = data?.name.toString(),
                maxLines = 1,
                fontSize = 15.sp,

                )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier

                    .wrapContentSize()
                    .padding(start = 15.dp, end = 15.dp)
                    .clip(shape = RoundedCornerShape(14.dp))
                    .background(
                        colorResource(id = R.color.card_background)
                    )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 12.dp),
                    text = data?.departmentName.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    letterSpacing = 0.01.sp,
                    color = colorResource(id = R.color.light_white_text),
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Divider(
                thickness = 1.dp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                color = colorResource(id = R.color.divider_color)
            )
            Spacer(modifier = Modifier.height(11.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            )
            {

                Box(modifier = Modifier
                    .padding(end = 2.dp)
                    .height(28.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(CircleShape)
                    .background(
                        colorResource(
                            id = R.color.white
                        ), shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        1.dp, colorResource(
                            id = R.color.blue
                        ), shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mail_logo),
                            contentDescription = "description",
                            modifier = Modifier
                                .height(15.dp)
                                .width(18.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            text = "Mail",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = colorResource(
                                id = R.color.blue
                            )
                        )

                    }

                }

                Box(modifier = Modifier
                    .padding(start = 2.dp)
                    .height(28.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(CircleShape)
                    .background(
                        colorResource(
                            id = R.color.white
                        ), shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        1.dp, colorResource(
                            id = R.color.blue
                        ), shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { }
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.call_logo),
                            contentDescription = "description",
                            modifier = Modifier
                                .height(15.dp)
                                .width(18.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            text = "Call",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.blue)
                        )

                    }
                }

            }


        }


        ////OLf Code base
//    BoxWithConstraints(
//        modifier = Modifier
//            .height(214.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(4.dp))
//            .padding(end = 9.dp)
//            .border(1.dp, color = colorResource(id = R.color.border_color))
//            .aspectRatio(0.8f)
//            .background(colorResource(id = R.color.white)),
//    ) {
//
//
//        rememberAsyncImagePainter(model = data?.photo)
//
//        Image(
//            painter = rememberAsyncImagePainter(model = data?.photo),
//            contentDescription = "",
//            modifier = Modifier
//                .padding(top = 19.dp)
//                .size(64.dp, 64.dp)
//                .clip(CircleShape)
//                .align(
//                    Alignment.TopCenter
//                ), contentScale = ContentScale.Crop
//        )
//
//        Column(
//            modifier = Modifier
////                .align(Alignment.Center)
//                .padding(top = 92.dp, start = 2.dp)
//        ) {
//            Text(
//                text = data?.name.toString(),
////                text=features.title,
//                color = Color.Black,
//                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 16.sp),
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally),
//                fontFamily = bold, maxLines = 1, overflow = TextOverflow.Ellipsis
//            )
//            Card(
//                modifier = Modifier
//                    .padding(top = 6.dp)
//                    .align(Alignment.CenterHorizontally), colors = CardDefaults.cardColors(
//                    colorResource(id = R.color.login_background),
//                ), shape = RoundedCornerShape(14.dp)
//            ) {
//                Text(
//                    text = data?.departmentName.toString(),
//                    style = TextStyle(
//                        fontSize = 12.sp
//                    ),
//                    color = colorResource(id = R.color.light_white_text),
//                    textAlign = TextAlign.Center, modifier = Modifier.padding(6.dp),
//                )
//
//            }
//            Divider(
//                modifier = Modifier
//                    .padding(top = 8.dp, start = 10.dp, end = 10.dp),
//                color = colorResource(id = R.color.divider_color)
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center,
//
//                ) {
//                Chip(
//                    modifier = Modifier.padding(bottom = 17.dp),
//                    onClick = {},
//                    border = BorderStroke(1.dp, colorResource(id = R.color.blue)),
//                    colors = ChipDefaults.outlinedChipColors(),
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceEvenly
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.mail_logo),
//                            contentDescription = "",
//                            modifier = Modifier.size(14.dp)
//                        )
//                        Spacer(modifier = Modifier.width(1.dp))
//
//                        Text(
//                            text = "Mail",
//                            fontSize = 14.sp,
//                            modifier = Modifier.padding(start = 1.dp),
//                            color = colorResource(id = R.color.blue)
//                        )
//
//                    }
//                }
//                Spacer(modifier = Modifier.width(4.dp))
//
//                Chip(
//                    modifier = Modifier.padding(bottom = 17.dp),
//
//                    onClick = {},
//                    border = BorderStroke(1.dp, colorResource(id = R.color.blue)),
//                    colors = ChipDefaults.outlinedChipColors(),
//                ) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Image(
//                            painter = painterResource(id = R.drawable.call_logo),
//                            contentDescription = "",
//                            modifier = Modifier.size(14.dp)
//                        )
//                        Spacer(modifier = Modifier.width(1.dp))
//                        Text(
//                            text = "Call",
//                            fontSize = 14.sp,
//                            modifier = Modifier.padding(start = 2.dp),
//                            color = colorResource(id = R.color.blue)
//                        )
//
//                    }
//                }
//
//
//            }
//
//        }
//
//
    }


}


@SuppressLint("ResourceType")
@Composable
fun HomeMainScreen(data: List<EmployeeList.Data?>) {
//        val systemUiController = rememberSystemUiController()
//        val statusBarColor = colorResource(id = R.color.white)
//        systemUiController.setStatusBarColor(statusBarColor)
    val bold = FontFamily(Font(R.font.sf_pro_bold))
    val context = LocalContext.current

    Box() {
        var isBoxVisible by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .background(colorResource(id = R.color.login_background))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "My Employees",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                        fontSize = 32.sp,
                        fontFamily = bold
                    )
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Search",
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { isBoxVisible = !isBoxVisible }
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "${data.size}Employees",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp),
                    letterSpacing = 0.02.sp,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = bold
                )
                employeeListView(data, isBoxVisible)

            }

        }
        Spacer(modifier = Modifier.height(16.dp))
     FloatingActionButton(
            onClick = {
               val intent=Intent(context,AddEmployeeActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 86.dp, end = 22.dp)
                .width(64.dp)
                .height(64.dp),
            shape = RoundedCornerShape(100),
            backgroundColor = colorResource(id = R.color.blue)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Add FAB",
                tint = Color.White,
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun employeeListView(employeeListData: List<EmployeeList.Data?>, isVisibility: Boolean) {

    val context = LocalContext.current
    val regular = FontFamily(Font(R.font.sf_pro_regular))

    Column(
        modifier = Modifier.padding(top = 14.dp, start = 14.dp, end = 14.dp)
    ) {

        var searchQuery by remember { mutableStateOf("") }
        if (isVisibility) {

            OutlinedTextField(
                value = searchQuery,
                singleLine = true,
                placeholder = {
                    Text(
                        "Search Employee",
                        fontFamily = regular,
                        fontSize = 15.sp,
                        letterSpacing = 0.1.sp,
                        color = colorResource(id = R.color.border_color),
                        textAlign = TextAlign.Start
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp, bottom = 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { searchQuery = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedBorderColor = colorResource(id = R.color.divider_color),
                    unfocusedBorderColor = colorResource(id = R.color.divider_color)
                )
            )
        }
        val filteredEmployeeList = if (searchQuery.isNotBlank()) {
            employeeListData.filter {
                it?.name?.contains(searchQuery, ignoreCase = true) == true
            }
        } else {
            employeeListData
        }


        LazyVerticalGrid(
            GridCells.Fixed(2)
        ) {
            items(filteredEmployeeList) { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 12.dp)
                ) {
                    dataItems(item) { selectedData ->
                        searchQuery = ""
                        val intent = Intent(context, ProfileDetailsActivity::class.java)
                        intent.putExtra("name", selectedData?.name)
                        intent.putExtra("department", selectedData?.departmentName)
                        intent.putExtra("email", selectedData?.email)
                        intent.putExtra("dob", selectedData?.dateOfBirth)
                        intent.putExtra("number", selectedData?.mobileNumber)
                        intent.putExtra("address", selectedData?.address)
                        intent.putExtra("image", selectedData?.photo)
                        intent.putExtra("blood", selectedData?.bloodGroup)
                        intent.putExtra("token", selectedData?.token)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun dataItems(item: EmployeeList.Data?, onItemClick: (EmployeeList.Data?) -> Unit) {
    val regular = FontFamily(Font(R.font.sf_pro_regular))
    val semiBold = FontFamily(Font(R.font.sf_pro_semibold))
    val medium = FontFamily(Font(R.font.sf_pro_medium))

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.white), shape = RoundedCornerShape(7.dp))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.card_background),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onItemClick(item) }

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        ) {
            Image(
                painter = if (item?.photo.equals("http://18.218.209.28:8000/storage/")) {
                    rememberAsyncImagePainter(R.drawable.profile)
                } else {
                    rememberAsyncImagePainter(item?.photo)
                },
                contentDescription = null,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(CircleShape),contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item?.name!!.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() },
                maxLines = 1,
                fontSize = 15.sp,
                fontFamily = semiBold,
                letterSpacing = 0.02.sp,
                color = colorResource(
                    id = R.color.black
                ),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 15.dp, end = 15.dp)
                    .clip(shape = RoundedCornerShape(14.dp))
                    .background(
                        colorResource(id = R.color.card_background)
                    )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 12.dp),
                    text = "${item.departmentName} Team",
                    fontFamily = medium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    letterSpacing = 0.01.sp,
                    color = colorResource(id = R.color.normal_text),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            androidx.compose.material.Divider(
                color = colorResource(id = R.color.divider_color),
                thickness = 1.dp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(11.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp))
            {


                Box(modifier = Modifier
                    .padding(end = 2.dp)
                    .height(28.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(CircleShape)
                    .background(
                        colorResource(
                            id = R.color.white
                        ), shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        1.dp, colorResource(
                            id = R.color.blue
                        ), shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { }
                ){

                    Row(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.mail_logo),
                            contentDescription = "description",
                            modifier = Modifier
                                .height(15.dp)
                                .width(18.dp)
                                .padding(end = 4.dp)
                        )
                        Text(text = "Mail",fontFamily= regular, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = colorResource(
                            id = R.color.blue
                        ))

                    }
                }

                val regular = FontFamily(Font(R.font.sf_pro_regular))

                Box(modifier = Modifier
                    .padding(start = 2.dp)
                    .height(28.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(CircleShape)
                    .background(
                        colorResource(
                            id = R.color.white
                        ), shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        1.dp, colorResource(
                            id = R.color.blue
                        ), shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { }
                ){

                    Row(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.call_logo),
                            contentDescription = "description",
                            modifier = Modifier
                                .height(15.dp)
                                .width(18.dp)
                                .padding(end = 4.dp)
                        )
                        Text(text = "Call", fontFamily = regular, fontSize = 14.sp, color = colorResource(id = R.color.blue))

                    }
                }
            }
        }
    }
}
