package com.macapp.employeemanagement.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.activity.ui.theme.PurpleGrey80
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            EmployeeManagementTheme {
                val intent = intent
                if (intent.hasExtra("employeeList")) {
                    MyDetail(intentValue = intent)
                }
                MyDetail(intentValue = intent)
                val systemUiController = rememberSystemUiController()
                val statusBarColor = colorResource(id = R.color.white) // Change this to the desired color

                SideEffect {
                    systemUiController.setStatusBarColor(statusBarColor)
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDetail(intentValue: Intent) {
    val isLoading = mutableStateOf(false)


    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val onBackPressed = (context as ComponentActivity).onBackPressedDispatcher


    val employeeName = intentValue.extras?.getString("name").toString()
    val dateBirth = intentValue.extras?.getString("dob").toString()
    val employeeBloodGroup = intentValue.extras?.getString("blood").toString()
    val email = intentValue.extras?.getString("email").toString()
    val employeeAddress = intentValue.extras?.getString("address").toString()
    val number = intentValue.extras?.getString("number").toString()
    val department = intentValue.extras?.getString("department").toString()
    val image = intentValue.extras?.getString("image").toString()
    val employeeIdToken = intentValue.extras?.getString("token").toString()


    //Font
    val bold = FontFamily(Font(R.font.sf_pro_bold))
    val regular = FontFamily(Font(R.font.sf_pro_regular))
    val semiBold = FontFamily(Font(R.font.sf_pro_semibold))
    val medium = FontFamily(Font(R.font.sf_pro_medium))

    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            LoginRepository(ApiService.NetworkClient.apiService)
        )
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    ConstraintLayout(
        modifier = Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        val guideline = createGuidelineFromStart(fraction = 0.5f)


        val back = createRef()
        val backText = createRef()
        val edit = createRef()
        val profileImage = createRef()
        val name = createRef()
        val departmentName = createRef()
        val view = createRef()
        val mail = createRef()
        val mailText = createRef()
        val call = createRef()
        val callText = createRef()
        val viewOne = createRef()
        val basicInformation = createRef()
        val viewTwo = createRef()
        val callOne = createRef()
        val mailOne = createRef()
        val dob = createRef()
        val blood = createRef()
        val address = createRef()
        val dateOfBirth = createRef()
        val bloodGroup = createRef()
        val addressText = createRef()
        val deleteButton = createRef()


        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Back Icon",
            modifier = Modifier
                .constrainAs(back) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin = 30.dp)
                }
                .clickable {
                    onBackPressed.onBackPressed()
                }
        )

        Text(
            text = "Back",
            color = colorResource(id = R.color.blue),
            fontSize = 16.sp,
            modifier = Modifier
                .constrainAs(backText) {
                    top.linkTo(parent.top, margin = 28.dp)
                    start.linkTo(back.end, margin = 3.dp)
                }
                .clickable {
                    onBackPressed.onBackPressed()
                }
        )

        Image(
            painter = painterResource(id = R.drawable.edit_item),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(edit) {
                    end.linkTo(parent.end, margin = 20.dp)
                    top.linkTo(parent.top, margin = 30.dp)
                }
                .clickable {
                    val intent = Intent(context, EditProfileDetailsActivity::class.java)
                    intent.putExtra("name", employeeName)
                    intent.putExtra("department", department)
                    intent.putExtra("email", email)
                    intent.putExtra("dob", dateBirth)
                    intent.putExtra("number", number)
                    intent.putExtra("address", employeeAddress)
                    intent.putExtra("image", image)
                    intent.putExtra("blood", employeeBloodGroup)
                    intent.putExtra("token", employeeIdToken)
                    context.startActivity(intent)
                }
        )


        Image(
            if (image == "http://18.218.209.28:8000/storage/") {
                rememberAsyncImagePainter(R.drawable.profile)
            } else {
                rememberAsyncImagePainter(image)
            },
            contentDescription = null,
            modifier = Modifier
                .constrainAs(profileImage) {
                    top.linkTo(parent.top, margin = 35.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(width = 104.dp, height = 104.dp)
                .clip(CircleShape), contentScale = ContentScale.Crop
        )

        Text(
            text = employeeName,
            fontFamily = semiBold,
            color = Color.Black,
            fontSize = 22.sp,
            letterSpacing = 0.02.sp,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(profileImage.bottom, margin = 20.dp)
                start.linkTo(profileImage.start)
                end.linkTo(profileImage.end)
            }
        )

        Box(
            modifier = Modifier
                .constrainAs(departmentName) {
                    top.linkTo(name.bottom, margin = 10.dp)
                    start.linkTo(name.start)
                    end.linkTo(name.end)
                }
                .wrapContentSize()
                .padding(start = 15.dp, end = 15.dp)
                .clip(shape = RoundedCornerShape(14.dp))
                .background(
                    colorResource(id = R.color.card_background)
                )
        ) {
            androidx.compose.material3.Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 12.dp),
                text = department,
                fontFamily = medium,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                letterSpacing = 0.01.sp,
                color = colorResource(id = R.color.normal_text),
                maxLines = 1
            )
        }

        Divider(
            color = colorResource(id = R.color.login_background),
            thickness = 2.dp,
            modifier = Modifier.constrainAs(view) {
                top.linkTo(departmentName.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.mail_1),
            contentDescription = null,
            modifier = Modifier.constrainAs(mail) {
                top.linkTo(view.bottom, margin = 20.dp)
                start.linkTo(back.start)
            }
        )

        Text(text = email,
            color = Color.Gray,
            fontSize = 13.sp,
            fontFamily = regular,
            letterSpacing = 0.02.sp,
            modifier = Modifier.constrainAs(mailText) {
                top.linkTo(mail.top)
                start.linkTo(mail.end, margin = 10.dp)
            }
        )


        Image(
            painter = painterResource(id = R.drawable.call_1),
            contentDescription = null,
            modifier = Modifier.constrainAs(call) {
                top.linkTo(mail.bottom, margin = 10.dp)
                start.linkTo(mail.start)
            }
        )

        Text(text = number,
            color = Color.Gray,
            fontFamily = regular,
            fontSize = 13.sp,
            letterSpacing = 0.02.sp,
            modifier = Modifier.constrainAs(callText) {
                top.linkTo(mail.bottom, margin = 10.dp)
                start.linkTo(call.end, margin = 10.dp)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.call_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(callOne) {
                    top.linkTo(view.top, margin = 20.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .size(width = 40.dp, height = 40.dp)
                .border(
                    BorderStroke(width = 1.dp, colorResource(id = R.color.blue)),
                    CircleShape
                )
                .padding(8.dp)
                .clip(CircleShape)
        )


        Image(
            painter = painterResource(id = R.drawable.mail_logo),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(mailOne) {
                    top.linkTo(callOne.top)
                    bottom.linkTo(callOne.bottom)
                    end.linkTo(callOne.start, margin = 10.dp)
                }
                .size(width = 40.dp, height = 40.dp)
                .border(
                    BorderStroke(width = 1.dp, colorResource(id = R.color.blue)),
                    CircleShape
                )
                .padding(8.dp)
                .clip(CircleShape)
        )


        Divider(
            color = colorResource(id = R.color.login_background),
            thickness = 10.dp,
            modifier = Modifier.constrainAs(viewOne) {
                top.linkTo(call.bottom, margin = 20.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )

        Text(text = "Basic Information",
            color = Color.Black,
            fontSize = 15.sp,
            letterSpacing = 0.02.sp,
            fontFamily = bold,
            modifier = Modifier.constrainAs(basicInformation) {
                top.linkTo(viewOne.bottom, margin = 10.dp)
                start.linkTo(back.start)
            }
        )

        Divider(
            color = colorResource(id = R.color.login_background),
            thickness = 1.dp,
            modifier = Modifier.constrainAs(viewTwo) {
                top.linkTo(basicInformation.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )

        Text(text = "Date of Birth",
            color = Color.Gray,
            fontFamily = regular,
            fontSize = 15.sp,
            letterSpacing = 0.02.sp,
            modifier = Modifier.constrainAs(dob) {
                top.linkTo(viewTwo.bottom, margin = 8.dp)
                start.linkTo(back.start)
            }
        )

        Text(text = "Blood Group",
            color = Color.Gray,
            fontFamily = regular,
            fontSize = 15.sp,
            letterSpacing = 0.02.sp,
            modifier = Modifier.constrainAs(blood) {
                top.linkTo(dob.bottom, margin = 8.dp)
                start.linkTo(dob.start)
            }
        )

        Text(text = "Address",
            color = Color.Gray,
            fontFamily = regular,
            fontSize = 15.sp,
            letterSpacing = 0.02.sp,
            modifier = Modifier.constrainAs(address) {
                top.linkTo(blood.bottom, margin = 8.dp)
                start.linkTo(blood.start)
            }
        )

        Text(
            text = dateBirth,
            color = Color.Black,
            fontFamily = medium,
            fontSize = 14.sp,
            letterSpacing = 0.02.sp,
            modifier = Modifier.constrainAs(dateOfBirth) {
                top.linkTo(dob.top)
                start.linkTo(guideline)
            }
        )

        Text(
            text = employeeBloodGroup,
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = medium,
            letterSpacing = 0.02.sp,
            modifier = Modifier.constrainAs(bloodGroup) {
                top.linkTo(blood.top)
                start.linkTo(guideline)
            }
        )

        Text(
            text = employeeAddress,
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = medium,
            letterSpacing = 0.02.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(addressText) {
                top.linkTo(address.top)
                start.linkTo(guideline)
            }
        )

        Button(
            onClick = {

                val dialogBuilder = AlertDialog.Builder(context)
                val loginToken = DataStoredPreference(context).getUSerData()["loginToken"]
                dialogBuilder
                    .setMessage("Are sure you want to Delete")
                    .setCancelable(false)
                    .setNegativeButton(R.string.no) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.yes) { dialog, _ ->
                        coroutineScope.launch {
                            viewModel.deleteEmployee(employeeIdToken, loginToken.toString())
                            Log.d("deleteEmployee", "MyDetail: $employeeIdToken,$loginToken")
                        }
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        dialog.dismiss()
                    }
                    .show()


            },
            modifier = Modifier
                .constrainAs(deleteButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 40.dp)
                }
                .fillMaxWidth()
                .height(80.dp)
                .padding(start = 12.dp, end = 12.dp, top = 24.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)),
            shape = RoundedCornerShape(4.dp)
        ) {
            androidx.compose.material3.Text(
                text = "Delete Employee",
                color = colorResource(id = R.color.white),
                fontSize = 16.sp,
                fontFamily = bold
            )
        }
    }

}