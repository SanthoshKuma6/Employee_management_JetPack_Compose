package com.macapp.employeemanagement.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.HomeActivity
import com.macapp.employeemanagement.ui.theme.componentShapes

@Composable
fun AddEmployeeFieldComponent(
    labelValue: String
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
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it

        },


        )
}


@Composable
fun AdEmployeeButtonComponent() {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Blue),
        onClick = {
            val intent = Intent(context, HomeActivity()::class.java)
            context.startActivity(intent)
        },
    ) {
        Text(
            text = "Add Employee",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }


}


@Composable
fun BackNavigationComponent(value: String,onTextSelected: (String) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .heightIn(20.dp)
    ) {
        Icon(painter = painterResource(id = R.drawable.back_arrow_24), contentDescription = "")
       ClickableText(value = value, onTextSelected )
    }

}


@Composable
fun ClickableText(value: String, onTextSelected: (String) -> Unit) {
    val annotatedString = buildAnnotatedString {
        append(value)
    }


    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span1 ->
                if ((span1.item == value)) {
                    onTextSelected(span1.item)
                }
            }

    })

}

