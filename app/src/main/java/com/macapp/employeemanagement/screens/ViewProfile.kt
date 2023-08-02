package com.macapp.employeemanagement.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.components.BackNavigationComponent
import com.macapp.employeemanagement.navigation.AppRouter
import com.macapp.employeemanagement.navigation.Screen

@Composable
fun ViewProfile() {

    val bold = FontFamily(Font(R.font.gothica1_regular))
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 10.dp, start = 16.dp, end = 15.dp)
    ) {

        val leftArrow = createRef()
        val back = createRef()
        val profileImage = createRef()
        val profileName = createRef()
        val department = createRef()
        val dividerOne = createRef()
        val emailIcon = createRef()
        val mobileIcon = createRef()
        val email = createRef()
        val mobileNumber = createRef()
        val emailImage = createRef()
        val mobileImage = createRef()
        val dividerTwo = createRef()
        val basicInformation = createRef()
        val dividerTree = createRef()
        val dobTitle = createRef()
        val dob = createRef()
        val bgTitle = createRef()
        val bloodGroup = createRef()
        val addressTitle = createRef()
        val address = createRef()
        val guideline = createGuidelineFromStart(0.5f)
        val guidelinetwo = createGuidelineFromStart(0.7f)
        val dividerFour = createRef()
        val initialText = "Back"
        val annotatedString = buildAnnotatedString {
            append(initialText)
        }
//        BackNavigationComponent(
//            value = stringResource(id = R.string.back_arrow), onTextSelected = {
//                AppRouter.navigateTo(Screen.MyEmployeeScreen)
//            }
//        )
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "",

            modifier = Modifier.constrainAs(leftArrow) {
                start.linkTo(parent.start)
                top.linkTo(parent.top, margin = 20.dp)
            })
        Text(text = "Back", modifier = Modifier.constrainAs(back) {
            start.linkTo(leftArrow.start, margin = 25.dp)
            top.linkTo(parent.top, margin = 20.dp)
        }, style = TextStyle(

        ), color = colorResource(id = R.color.blue),
        )

//
//
//
//        )

//        ClickableText(text = annotatedString, modifier = Modifier.constrainAs(back) {
//            start.linkTo(leftArrow.start, margin = 20.dp)
//            top.linkTo(parent.top, margin = 20.dp)
//        }, onClick = {
//            Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show()
//            AppRouter.navigateTo(Screen.MyEmployeeScreen)
//        })
        Image(
            painter = painterResource(id = R.drawable.profilepic),
            contentDescription = "",
            modifier = Modifier
                .size(90.dp, 90.dp)
                .constrainAs(profileImage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 20.dp)

                })


        Text(
            text = "Tillie Jackson", modifier = Modifier.constrainAs(profileName) {
                start.linkTo(parent.start)
                top.linkTo(profileImage.bottom, margin = 20.dp)
                end.linkTo(parent.end)
            }, style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ), fontFamily = bold
        )

        Card(
            modifier = Modifier
                .padding(2.dp)
                .constrainAs(department) {
                    start.linkTo(parent.start)
                    top.linkTo(profileName.bottom, margin = 15.dp)
                    end.linkTo(parent.end)
                },
            colors = CardDefaults.cardColors(colorResource(id = R.color.login_background)),
            shape = RoundedCornerShape(14.dp)

        ) {
            Text(text = "Android Developer", modifier = Modifier.padding(8.dp))

        }

        Divider(modifier = Modifier
            .background(colorResource(id = R.color.divider_color))
            .constrainAs(dividerOne) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(department.bottom, margin = 25.dp)
            })

        Icon(
            painter = painterResource(id = R.drawable.mail_1),
            contentDescription = "",
            modifier = Modifier
                .size(19.dp, 20.dp)
                .constrainAs(emailIcon) {
                    start.linkTo(parent.start, margin = 17.dp)
                    top.linkTo(dividerOne.bottom, margin = 10.dp)
                })
        Icon(
            painter = painterResource(id = R.drawable.call_1),
            contentDescription = "",
            modifier = Modifier
                .size(19.dp, 20.dp)
                .constrainAs(mobileIcon) {
                    start.linkTo(parent.start, margin = 17.dp)
                    top.linkTo(emailIcon.bottom, margin = 13.dp)
                })
        Text(text = "Santhosh123@Gmail.com", modifier = Modifier.constrainAs(email) {
            start.linkTo(emailIcon.start, margin = 30.dp)
            top.linkTo(dividerOne.bottom, margin = 10.dp)
        }, style = TextStyle(
            fontSize = 14.sp
        )
        )
        Text(text = "123456789", modifier = Modifier.constrainAs(mobileNumber) {
            start.linkTo(emailIcon.start, margin = 30.dp)
            top.linkTo(email.bottom, margin = 13.dp)
        }, style = TextStyle(
            fontSize = 14.sp
        ))


        Image(
            painter = painterResource(id = R.drawable.mail_logo),
            contentDescription = "",
            modifier = Modifier
                .size(width = 40.dp, height = 40.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(width = 1.dp, colorResource(id = R.color.blue)),
                    CircleShape
                )
                .padding(8.dp)
                .constrainAs(emailImage) {
                    end.linkTo(parent.end, margin = 60.dp)
                    top.linkTo(dividerOne.bottom, margin = 20.dp)
                })
        Image(
            painter = painterResource(id = R.drawable.call_logo),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    BorderStroke(width = 1.dp, colorResource(id = R.color.blue)),
                    CircleShape
                )
                .size(width = 40.dp, height = 40.dp)
                .padding(8.dp)
                .constrainAs(mobileImage) {
                    start.linkTo(emailImage.start, margin = 60.dp)
                    top.linkTo(dividerOne.bottom, margin = 20.dp)
                    end.linkTo(parent.end)
                })
        Divider(
            modifier = Modifier
                .height(20.dp)
                .constrainAs(dividerTwo) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(mobileIcon.bottom, margin = 20.dp)
                }, color = colorResource(id = R.color.login_background)


        )

        Text(
            text = "Basic Information", modifier = Modifier.constrainAs(basicInformation) {
                start.linkTo(parent.start, margin = 10.dp)
                top.linkTo(dividerTwo.bottom, margin = 10.dp)

            }, style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Default
            )
        )

        Divider(modifier = Modifier
            .background(colorResource(id = R.color.divider_color))
            .constrainAs(dividerTree) {
                start.linkTo(parent.start)
                top.linkTo(basicInformation.bottom, margin = 20.dp)
                end.linkTo(parent.end)
            })

        Text(
            text = "Date of Birth", modifier = Modifier.constrainAs(dobTitle) {
                start.linkTo(parent.start)
                top.linkTo(dividerTree.bottom, margin = 10.dp)
            }, style = TextStyle(
                fontSize = 14.sp,

                )
        )
        Text(
            text = "Blood Group", modifier = Modifier.constrainAs(bgTitle) {
                start.linkTo(parent.start)
                top.linkTo(dobTitle.bottom, margin = 10.dp)
            }, style = TextStyle(
                fontSize = 14.sp,

                )
        )
        Text(
            text = "Address", modifier = Modifier.constrainAs(addressTitle) {
                start.linkTo(parent.start)
                top.linkTo(bgTitle.bottom, margin = 10.dp)
            }, style = TextStyle(
                fontSize = 14.sp,

                )
        )
        Text(
            text = "Feb 3 2000", modifier = Modifier.constrainAs(dob) {
                start.linkTo(guideline)
                top.linkTo(dividerTree.bottom, margin = 10.dp)
            }, style = TextStyle(
                fontSize = 14.sp,
                fontFamily = bold

            )
        )
        Text(
            text = "A Negative", modifier = Modifier.constrainAs(bloodGroup) {
                start.linkTo(guideline)
                top.linkTo(dob.bottom, margin = 10.dp)
            }, style = TextStyle(
                fontSize = 14.sp,
                fontFamily = bold

            )
        )
        Text(
            text = "Medavakkam", modifier = Modifier.constrainAs(address) {
                start.linkTo(guideline)
                top.linkTo(bloodGroup.bottom, margin = 10.dp)
            }, style = TextStyle(
                fontSize = 14.sp,
                fontFamily = bold

            )
        )

//        Divider(
//            modifier = Modifier
//                .fillMaxSize()
//                .constrainAs(dividerFour) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    top.linkTo(address.bottom, margin = 20.dp)
//                }, color = colorResource(id = R.color.login_background)
//        )
    }

}

@Preview
@Composable
fun DefaultViewProfile() {
    ViewProfile()
}