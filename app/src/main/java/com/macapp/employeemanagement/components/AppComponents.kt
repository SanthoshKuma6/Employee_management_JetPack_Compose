package com.macapp.employeemanagement.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.AddEmployeeActivity
import com.macapp.employeemanagement.activity.HomeActivity
import com.macapp.employeemanagement.navigation.AppRouter
import com.macapp.employeemanagement.navigation.Screen
import com.macapp.employeemanagement.screens.AddNewEmployee
import com.macapp.employeemanagement.ui.theme.componentShapes

@Composable
fun NormalTextComponent(value: String) {
    val bold=FontFamily(Font(R.font.gothica1_regular))

    Text(
        text = value, modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .heightIn(min = 40.dp), style = TextStyle(
            fontSize = 24.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal
        ), color = Color.Black, textAlign = TextAlign.Center, fontFamily = bold
    )
}


@Composable
fun Login(value: String) {
    val bold=FontFamily(Font(R.font.gothica1_regular))

    Text(
        text = value, modifier = Modifier.padding(top = 40.dp), style = TextStyle(
            fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold, fontSize = 25.sp, fontFamily = bold
        )
    )

}

@Composable
fun LoginCredential(value: String) {

    Text(
        text = value, modifier = Modifier.padding(top = 5.dp), style = TextStyle(
            fontStyle = FontStyle.Normal, fontWeight = FontWeight.Normal, fontSize = 15.sp,
        )
    )

}

@Composable
fun NormalEmailText(value: String) {
    val bold=FontFamily(Font(R.font.gothica1_regular))

    Text(
        text = value, modifier = Modifier.padding(top = 20.dp), style = TextStyle(
            fontStyle = FontStyle.Normal,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal, fontFamily = bold
        )

    )
}

@Composable
fun TitleImageComponent() {
    Image(
        modifier = Modifier
            .size(400.dp, 140.dp)
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .padding(top = 50.dp),
        painter = painterResource(id = R.drawable.fullsize_emma_watson),
        contentDescription = "",
        alignment = Alignment.Center
    )

}

@Composable
fun ImageComponent() {

    Image(
        modifier = Modifier
            .size(400.dp, 140.dp)
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .padding(top = 0.dp),
        painter = painterResource(id = R.drawable.profilepic),
        contentDescription = "",
        alignment = Alignment.Center
    )


}

@Composable
fun MyTextFieldComponent(
    labelValue: String, painterResources: Painter,onTextSelected: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf("")
    }


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(componentShapes.small),
        placeholder = { Text(text = labelValue) },

//        label = { Text(text = labelValue) },

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            focusedLabelColor = Color.Black,
            cursorColor = Color.White,
            backgroundColor = Color.White
        ),

        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)

        },
        leadingIcon = {
            Icon(painter = painterResources, contentDescription = "")
        },

        )
}

@Composable
fun PasswordTextFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {

    val localFocusManager = LocalFocusManager.current
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        placeholder = { Text(text = labelValue) },
//        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            focusedLabelColor = Color.Blue,
            cursorColor = Color.Blue,
            backgroundColor = Color.White,

            ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {

            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (passwordVisible.value) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}


@Composable
fun ButtonComponent(value: String,onButtonClicked:()->Unit,) {
    val context = LocalContext.current

    Button(

        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .heightIn(48.dp),

        onClick = {
            val intent = Intent(context, HomeActivity()::class.java)
            context.startActivity(intent)

//            AppRouter.navigateTo(Screen.HomeScreen)
        },

        )

    {
        Text(
            text = value,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

    }


}

@Composable
fun FloatingActionButtonCompose() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp, end = 20.dp), verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
            backgroundColor = Color.Blue,
            contentColor = Color.White,
            onClick = {
                val intent = Intent(context, AddEmployeeActivity::class.java)
                context.startActivity(intent)
            }) {

            Icon(Icons.Default.Add, contentDescription = null)
        }

    }
}

@Composable
fun MyEmployeeComponent() {
    val bold=FontFamily(Font(R.font.gothica1_regular))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 16.dp, end = 30.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            androidx.compose.material3.Text(
                text = "My Employees", style = TextStyle(
                    fontSize = 30.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal,
                ), fontFamily = bold

            )
            Spacer(modifier = Modifier.heightIn(10.dp))

        }
        androidx.compose.material3.Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color.Black,
            modifier = Modifier.size(20.dp),
        )

    }

}



