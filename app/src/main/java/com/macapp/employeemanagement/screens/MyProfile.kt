package com.macapp.employeemanagement.screens

import android.app.AlertDialog
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.LoginActivity
import com.macapp.employeemanagement.preference.DataStoredPreference

@Composable
fun MyProfile() {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = colorResource(id = R.color.white)
    systemUiController.setStatusBarColor(statusBarColor)
    val bold = FontFamily(Font(R.font.gothica1_regular))
    val regular = FontFamily(Font(R.font.sf_pro_regular))
    val semiBold = FontFamily(Font(R.font.sf_pro_semibold))
    val medium = FontFamily(Font(R.font.sf_pro_medium))
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White).padding(top = 30.dp)
        
    ) {
        val titleProfile = createRef()
        val logoutText = createRef()
        val logoutIcon = createRef()
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
            text = "My Profile", color = Color.Black,
            modifier = Modifier
                .constrainAs(titleProfile)
                {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, )
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
                .constrainAs(logoutText) {
                    end.linkTo(parent.end, margin = 20.dp)
                    bottom.linkTo(profilePicture.bottom)
                    top.linkTo(profilePicture.top)
                }
                .clickable {

                    val dialog = AlertDialog.Builder(context)
                    dialog.setMessage("Are you sure want exit?")
                    dialog.setCancelable(false)
                    dialog.setNegativeButton(R.string.no) { dialog, _ ->
                        dialog.dismiss()
                    }
                    dialog
                        .setPositiveButton(R.string.yes) { dialog, _ ->
                            DataStoredPreference(context).saveUSerData("")
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                            dialog.dismiss()
                        }
                        .show()

                },
            style = TextStyle(
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
            ),
            color = colorResource(id = R.color.blue), fontFamily = regular,
        )

            Image(modifier=Modifier.constrainAs(logoutIcon){
                top.linkTo(logoutText.top, margin = 13.dp)
                bottom.linkTo(logoutText.bottom)
                end.linkTo(logoutText.start, margin = 10.dp)
            },
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "",
            )






        Image(
            painter = painterResource(id = R.drawable.emma_watson),
            contentDescription = "",

            modifier = Modifier
                .clip(CircleShape)
                .constrainAs(profilePicture) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(logoutIcon.bottom, margin = 26.dp)
                }
                .size(90.dp, 90.dp), contentScale = ContentScale.Crop)


        Text(
            text = "Tillie Jackson", modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(profilePicture.bottom, margin = 16.dp)

            }, style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Normal,

                ), fontFamily = semiBold
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
                containerColor = colorResource(id = R.color.login_background),
            )
        ) {
            Text(
                text = "Android Developer",
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp),

                color = colorResource(id = R.color.light_white_text),
                fontFamily = medium
            )
        }

        Divider(
            modifier = Modifier
                .padding()
                .constrainAs(divider) {
                    start.linkTo(parent.start,margin = 26.dp)
                    end.linkTo(parent.end,margin = 26.dp)
                    top.linkTo(department.bottom, margin = 21.dp)
                }, color = colorResource(id = R.color.login_background)
        )

        Icon(
            painter = painterResource(id = R.drawable.mail_1),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 25.dp)
                .constrainAs(emailIcon) {
                    start.linkTo(parent.start, margin = 30.dp)
                    top.linkTo(divider.bottom,)
                })
        Text(
            text = "santhosh11@gamil.com",
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(email) {
                    start.linkTo(emailIcon.start, margin = 30.dp)
                    top.linkTo(divider.bottom)
                },
            color = colorResource(id = R.color.light_white_text),
            fontFamily = regular
        )
        Icon(
            painter = painterResource(id = R.drawable.call_1),
            contentDescription = "",
            modifier = Modifier.constrainAs(mobileNumberIcon) {
                start.linkTo(parent.start, margin = 30.dp)
                top.linkTo(emailIcon.bottom, margin = 13.dp)
            })

        Text(
            text = "123456789",
            modifier = Modifier.constrainAs(mobileNumber) {
                start.linkTo(mobileNumberIcon.start, margin = 30.dp)
                top.linkTo(email.bottom, margin = 10.dp)
            },
            color = colorResource(id = R.color.light_white_text),
            fontFamily = regular
        )

        Divider(
            modifier = Modifier
                .height(10.dp)
                .constrainAs(secondDivided) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(mobileNumber.bottom, margin = 26.dp)
                }, color = colorResource(id = R.color.login_background)
        )

        Text(
            text = "Basic Information", modifier = Modifier.constrainAs(basicInformation) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(secondDivided.bottom, margin = 10.dp)
            }, style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Normal
            ), fontFamily = bold
        )

        Divider(
            modifier = Modifier
                .constrainAs(thirdDivided) {
                    start.linkTo(parent.start,margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(basicInformation.bottom, margin = 14.dp)
                }, color = colorResource(id = R.color.login_background)
        )

        Text(
            text = "Date of Birth",
            modifier = Modifier.constrainAs(dateOfBirthTitle) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(thirdDivided.bottom, margin = 10.dp)
            },
            color = colorResource(id = R.color.light_white_text),
            style = TextStyle(fontSize = 16.sp),
            fontWeight = FontWeight.Normal,fontFamily = regular
        )
        Text(
            text = "Blood Group",
            modifier = Modifier.constrainAs(bloodGroupTitle) {
                start.linkTo(parent.start,margin = 16.dp)
                top.linkTo(dateOfBirthTitle.bottom, margin = 10.dp)
            },
            color = colorResource(id = R.color.light_white_text),
            style = TextStyle(fontSize = 16.sp),
            fontWeight = FontWeight.Normal,fontFamily = regular
        )
        Text(
            text = "Address",
            modifier = Modifier.constrainAs(addressTitle) {
                start.linkTo(parent.start,margin = 16.dp)
                top.linkTo(bloodGroupTitle.bottom, margin = 10.dp)
            },
            color = colorResource(id = R.color.light_white_text),
            style = TextStyle(fontSize = 16.sp),
            fontWeight = FontWeight.Normal,fontFamily = regular
        )
        Text(
            text = "30 June 2000", modifier = Modifier.constrainAs(dateOfBirth) {
                start.linkTo(guideline)
                top.linkTo(thirdDivided.bottom, margin = 10.dp)
            }, color = colorResource(id = R.color.black_bold), style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Normal
            ), fontFamily = bold,
        )
        Text(
            text = "A Negative", modifier = Modifier.constrainAs(bloodGroup) {
                start.linkTo(guideline)
                top.linkTo(dateOfBirth.bottom, margin = 10.dp)
            }, color = colorResource(id = R.color.black_bold), style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Normal
            ), fontFamily = bold
        )
        Text(
            text = "785 DuwudRiver,\nKacohwe,Minnesota,\nBurundi- 21087",
            modifier = Modifier.constrainAs(address) {
                start.linkTo(guideline)
                top.linkTo(bloodGroup.bottom, margin = 10.dp)

            },

            color = colorResource(id = R.color.black_bold),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Normal
            ),
            fontFamily = bold
        )


        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .constrainAs(forthDivider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(address.bottom, margin = 40.dp)
                }, color = colorResource(id = R.color.login_background)
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