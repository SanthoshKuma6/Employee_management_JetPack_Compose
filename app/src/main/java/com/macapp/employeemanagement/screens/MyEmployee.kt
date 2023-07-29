package com.macapp.employeemanagement.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.AddEmployeeActivity
import com.macapp.employeemanagement.activity.ui.theme.ProfileViewActivity
import com.macapp.employeemanagement.components.MyEmployeeComponent
import com.macapp.employeemanagement.components.NormalTextComponent
import com.macapp.employeemanagement.model_class.EmployeeDetails

@Composable
fun MyEmployee() {
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.off_white))
            .fillMaxSize()
    ) {

    Column() {
        MyEmployeeComponent()
        FeaturesSection(
            listOf(
                EmployeeDetails(
                    title = "Emma Watson",
                    domain = "Android Developer",
                ),
                EmployeeDetails(
                    title = "Smriti Mandhana",
                    domain = "Native Android"
                ),
                EmployeeDetails(
                    title = "Jennifer",
                    domain = "UI/Ux"
                ),
                EmployeeDetails(
                    title = "Penelope",
                    domain = "Android Developer"
                ),
                EmployeeDetails(
                    title = "Gal Gadot",
                    domain = "Android Developer"
                ),
                EmployeeDetails(
                    title = "Saniya Mersa",
                    domain = "Android Developer"
                ),
                EmployeeDetails(
                    title = "Kaur",
                    domain = "Android Developer"
                ),
                EmployeeDetails(
                    title = "Smriti Mandhana",
                    domain = "Android Developer"
                ),
                EmployeeDetails(
                    title = "Gal Gadot",
                    domain = "Android Developer"
                ),
                EmployeeDetails(
                    title = "Jennifer",
                    domain = "Android Developer"
                ),
                EmployeeDetails(
                    title = "Emma Watson",
                    domain = "Android Developer"
                ),

                )
        )


}

    }
}
@Composable
fun FeaturesSection(features: List<EmployeeDetails>) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "36 Employees", style = TextStyle(), modifier = Modifier.padding(12.dp))
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



@Composable
fun FeatureItem(features: EmployeeDetails) {
    val bold=FontFamily(Font(R.font.gothica1_regular))

    val context= LocalContext.current
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.dp)
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(Color.White),

    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clickable {
                    val intent = Intent(context, ProfileViewActivity::class.java)
                    context.startActivity(intent)
                }
        ) {


            Image(
                painter = painterResource(id = R.drawable.profilepic),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp,50.dp)
                    .align(
                        Alignment.TopCenter
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 10.dp, start = 2.dp)
            ) {
                Text(
                    text = features.title,
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontFamily = bold
                )
                Card(modifier = Modifier
                    .align(Alignment.CenterHorizontally), colors = CardDefaults.cardColors(
                    colorResource(id = R.color.off_white))
                )  {
                    Text(
                        text = features.domain,
                        style = TextStyle(),
                        textAlign = TextAlign.Center, modifier = Modifier.padding(4.dp)
                    )

                }


            }
            Text(text = "Mail",

                color = Color
                    .Blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {

                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.off_white))
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
            Text(text = "call",
                color = Color
                    .Blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {

                    }
                    .align(Alignment.BottomStart)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.off_white))
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }

}


@Preview
@Composable
fun DefaultView() {
    MyEmployee()
}