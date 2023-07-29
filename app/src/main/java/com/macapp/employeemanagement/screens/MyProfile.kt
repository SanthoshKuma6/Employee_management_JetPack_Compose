package com.macapp.employeemanagement.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.macapp.employeemanagement.R

@Composable
fun MyProfile() {
    val bold = FontFamily(Font(R.font.gothica1_regular))
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 10.dp)
    ) {
        val titleProfile = createRef()
        val loginIcon = createRef()
        val titleLogin = createRef()
        val profilePicture = createRef()
        val name = createRef()
        val department = createRef()
        val divider = createRef()
        val email = createRef()
        val emailIcon = createRef()
        val mobileNumber = createRef()
        val mobileNumberIcon = createRef()
        val secondDivided = createRef()
        val basicInformation = createRef()
        val thirdDivided = createRef()
        val dateOfBirthTitle = createRef()
        val dateOfBirth = createRef()
        val bloodGroupTitle = createRef()
        val bloodGroup = createRef()
        val addressTitle = createRef()
        val address = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        val forthDivider = createRef()


        Text(
            text = "My Profile",
            modifier = Modifier
                .padding(top = 10.dp, start = 20.dp)
                .constrainAs(titleProfile)
                {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            fontFamily = bold
        )


        Text(
            text = "LogOut",
            modifier = Modifier
                .padding(top = 30.dp, start = 10.dp)
                .constrainAs(titleLogin) {
                    end.linkTo(parent.end, margin = 30.dp)

                },
            style = TextStyle(
                fontStyle = FontStyle.Normal,
                fontSize = 15.sp,
            )
        )

        Icon(
            painter = painterResource(id = R.drawable.logout_24),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(loginIcon) {
                    end.linkTo(titleLogin.start)
                    bottom.linkTo(titleLogin.bottom)
                })

        Image(
            painter = painterResource(id = R.drawable.profilepic),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(profilePicture) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(titleLogin.bottom, margin = 10.dp)
                }
                .size(90.dp, 90.dp))


        Text(
            text = "Emma Watson", modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(profilePicture.bottom, margin = 10.dp)

            }, style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Normal,

                ), fontFamily = bold
        )
        Card(
            modifier = Modifier
                .constrainAs(department) {
                    top.linkTo(name.bottom, margin = 10.dp)
                    start.linkTo(name.start)
                    end.linkTo(name.end)
                }
                .padding(2.dp), shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.off_white),
            )
        ) {
            androidx.compose.material3.Text(
                text = "Android Developer",
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp),

                )
        }





        Divider(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .background(Color.Black)
            .constrainAs(divider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(department.bottom, margin = 30.dp)
            })

        Icon(
            painter = painterResource(id = R.drawable.email_24),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(emailIcon) {
                    start.linkTo(parent.start, margin = 30.dp)
                    top.linkTo(divider.bottom, margin = 5.dp)
                })
        Text(text = "santhosh11@gamil.com", modifier = Modifier
            .padding(top = 20.dp)
            .constrainAs(email) {
                start.linkTo(emailIcon.start, margin = 30.dp)
                top.linkTo(divider.bottom)
            })
        Icon(
            painter = painterResource(id = R.drawable.call_24),
            contentDescription = "",
            modifier = Modifier.constrainAs(mobileNumberIcon) {
                start.linkTo(parent.start, margin = 30.dp)
                top.linkTo(emailIcon.bottom)
            })

        Text(text = "123456789", modifier = Modifier.constrainAs(mobileNumber) {
            start.linkTo(mobileNumberIcon.start, margin = 30.dp)
            top.linkTo(email.bottom, margin = 5.dp)
        })

        Divider(modifier = Modifier
            .height(20.dp)
            .constrainAs(secondDivided) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(mobileNumber.bottom, margin = 30.dp)
            }, color = colorResource(id = R.color.off_white)
        )

        Text(text = "Basic Information", modifier = Modifier.constrainAs(basicInformation) {
            start.linkTo(parent.start, margin = 10.dp)
            top.linkTo(secondDivided.bottom, margin = 10.dp)
        }, style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)

        Divider(modifier = Modifier.constrainAs(thirdDivided) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(basicInformation.bottom, margin = 20.dp)
        })

        Text(text = "Date of Birth", modifier = Modifier.constrainAs(dateOfBirthTitle) {
            start.linkTo(parent.start, margin = 10.dp)
            top.linkTo(thirdDivided.bottom, margin = 10.dp)
        }, style = TextStyle(fontSize = 16.sp), fontWeight = FontWeight.Normal)
        Text(text = "Blood Group", modifier = Modifier.constrainAs(bloodGroupTitle) {
            start.linkTo(parent.start, margin = 10.dp)
            top.linkTo(dateOfBirthTitle.bottom, margin = 10.dp)
        }, style = TextStyle(fontSize = 16.sp), fontWeight = FontWeight.Normal)
        Text(text = "Address", modifier = Modifier.constrainAs(addressTitle) {
            start.linkTo(parent.start, margin = 10.dp)
            top.linkTo(bloodGroupTitle.bottom, margin = 10.dp)
        }, style = TextStyle(fontSize = 16.sp), fontWeight = FontWeight.Normal)
        Text(text = "30 June 2000", modifier = Modifier.constrainAs(dateOfBirth) {
            start.linkTo(guideline)
            top.linkTo(thirdDivided.bottom, margin = 10.dp)
        }, style = TextStyle(fontSize = 16.sp), fontWeight = FontWeight.Normal, fontFamily = bold)
        Text(text = "A Negative", modifier = Modifier.constrainAs(bloodGroup) {
            start.linkTo(guideline)
            top.linkTo(dateOfBirth.bottom, margin = 10.dp)
        }, style = TextStyle(fontSize = 16.sp), fontWeight = FontWeight.Normal, fontFamily = bold)
        Text(text = "2nd Floor", modifier = Modifier.constrainAs(address) {
            start.linkTo(guideline)
            top.linkTo(bloodGroup.bottom, margin = 10.dp)

        }, style = TextStyle(fontSize = 16.sp), fontWeight = FontWeight.Normal, fontFamily = bold)


        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .constrainAs(forthDivider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(address.bottom, margin = 10.dp)
                }, color = colorResource(id = R.color.off_white)
        )
    }


//    Box(
//        modifier = Modifier
//            .background(Color.White)
//            .fillMaxSize()
//            .fillMaxWidth()
//    )
//    {
//        Column(modifier = Modifier) {
//            Row(
//                horizontalArrangement = Arrangement.Start,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp)
//            ) {
//                Text(
//                    text = "My Profile", modifier = Modifier, style = TextStyle(
//                        fontSize = 30.sp, fontWeight = FontWeight.Bold
//                    )
//                )
//                Icon(
//
//                    painter = painterResource(id = R.drawable.logout_24),
//                    contentDescription = "",
//                    modifier = Modifier
//                )
//
//            }
//
//
//        }
//
//
//    }


}


@Preview
@Composable
fun DefaultPreview() {
    MyProfile()
}