package com.macapp.employeemanagement.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.ProfileViewActivity
import com.macapp.employeemanagement.components.MyEmployeeComponent
import com.macapp.employeemanagement.model_class.login.EmployeeList
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyEmployee() {
    val employeeList: ArrayList<EmployeeList.Data?> = ArrayList()
    val bold = FontFamily(Font(R.font.sf_pro_bold))
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
            .background(colorResource(id = R.color.login_background))
            .fillMaxSize()

    ) {

        val token = DataStoredPreference(context).getUSerData()["loginToken"]
        if (!Boolean) {
            coroutineScope.launch {
                viewModel.getEmployeeList(token.toString())
                Boolean = true
                Log.d("launch", "MyEmployee: ")
            }

        }

        Column {
            MyEmployeeComponent()
            Text(
                text = "${employeeList.size} Employees",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 16.dp, bottom = 10.dp), fontFamily = bold
            )



            val employeeState = viewModel.employeeState.collectAsStateWithLifecycle()
            when (val result = employeeState.value) {
                is Response.Loading -> {}

                is Response.Success -> {
                    result.data?.data?.let { it1 ->
                        FeaturesSection(it1)
                        employeeList.addAll(it1)
                    }
                }

                is Response.Error -> {
                    val errorMessage = result.errorMessage
                    Toast.makeText(context, "$errorMessage", Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
//            FeaturesSection(
//                listOf(
//                   com.macapp.employeemanagement.model_class.EmployeeDetails(
//                        title = "Tendulkar",
//                        domain = "Android Developer",
//                    ),
//                    com.macapp.employeemanagement.model_class.EmployeeDetails(
//                        title = "Tendulkar",
//                        domain = "Native Android")))


        }


    }
}

@Composable
fun FeaturesSection(features: List<EmployeeList.Data?>) {
    val bold = FontFamily(Font(R.font.gothica1_regular))
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(features.size) {
                FeatureItem(features = features[it])
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeatureItem(features: EmployeeList.Data?) {
    val bold = FontFamily(Font(R.font.sf_pro_bold))

    val context = LocalContext.current
    BoxWithConstraints(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()

            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, color = colorResource(id = R.color.border_color))
            .aspectRatio(0.8f)
            .background(colorResource(id = R.color.white)),
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize().padding(start = 16.dp, end = 9.dp)
                .clickable {
                    val intent = Intent(context, ProfileViewActivity::class.java)
                    context.startActivity(intent)
                },
        ) {
            rememberAsyncImagePainter(model = features?.photo)

            Image(
                painter = rememberAsyncImagePainter(model = features?.photo),
//                painter = painterResource(id = R.drawable.profilepic),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 19.dp)
                    .size(64.dp, 64.dp)
                    .clip(CircleShape)
                    .align(
                        Alignment.TopCenter
                    ), contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 92.dp, start = 2.dp)
        ) {
            Text(
                text = features?.name.toString(),
//                text=features.title,
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 16.sp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontFamily = bold, maxLines = 1,overflow = TextOverflow.Ellipsis
            )
            Card(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .align(Alignment.CenterHorizontally), colors = CardDefaults.cardColors(
                    colorResource(id = R.color.login_background),
                ), shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = features?.departmentName.toString(),

//                    text=features.domain,
                    style = TextStyle(
                        fontSize = 12.sp

                    ),
                    color = colorResource(id = R.color.light_white_text),


                    textAlign = TextAlign.Center, modifier = Modifier.padding(6.dp),
                )

            }
            Divider(
                modifier = Modifier
                    .padding(top = 8.dp, start = 10.dp, end = 10.dp),
                color = colorResource(id = R.color.divider_color)
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 12.dp, top = 10.dp)
            ) {
                Chip(
                    modifier = Modifier.padding( bottom = 17.dp),
                    onClick = {},
                    border = BorderStroke(1.dp, colorResource(id = R.color.blue)),
                    colors = ChipDefaults.outlinedChipColors(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mail_logo),
                            contentDescription = "",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.height(1.dp))

                        Text(
                            text = "Mail",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 1.dp),
                            color = colorResource(id = R.color.blue)
                        )

                    }
                }
                Chip(
                    modifier = Modifier.padding(start = 8.dp, bottom = 17.dp),
                    onClick = {},
                    border = BorderStroke(1.dp, colorResource(id = R.color.blue)),
                    colors = ChipDefaults.outlinedChipColors(),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.call_logo),
                            contentDescription = "",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "Call",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 2.dp),
                            color = colorResource(id = R.color.blue)
                        )

                    }
                }


            }


        }


//            Card(
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(end = 10.dp, start = 10.dp, bottom = 3.dp, top = 10.dp),
//                colors = CardDefaults.cardColors(colorResource(id = R.color.white))
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically,) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.call_logo),
//                        contentDescription = "",
//                        modifier = Modifier.size(15.dp,15.dp)
//                    )
//                    Text(text = "call",
//                        color = Color.Blue,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .clickable {
//
//                            }
//                            .clip(RoundedCornerShape(14.dp))
//                            .background(colorResource(id = R.color.white))
//
//                    )
//
//                }
//
//            }
//            Card(
//                modifier = Modifier
//                    .align(Alignment.BottomStart)
//                    .border(border = BorderStroke(2.dp, color = Color.Blue))
//                    .padding(end = 10.dp, start = 10.dp, bottom = 3.dp, top = 10.dp),
//                colors = CardDefaults.cardColors(colorResource(id = R.color.white),)
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(painter = painterResource(id = R.drawable.mail_logo), contentDescription = "", modifier = Modifier.size(15.dp,15.dp))
//                    Text(text = "mail",
//                        color = Color.Blue,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .clickable { }
//                            .clip(RoundedCornerShape(14.dp))
//                            .background(colorResource(id = R.color.white))
//
//                    )
//
//                }
//
//            }
    }


}


@Preview
@Composable
fun DefaultView() {
    MyEmployee()
}