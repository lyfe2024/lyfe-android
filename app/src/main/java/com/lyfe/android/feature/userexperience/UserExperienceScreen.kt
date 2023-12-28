package com.lyfe.android.feature.userexperience

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey400
import com.lyfe.android.core.common.ui.theme.Grey800
import com.lyfe.android.core.common.ui.theme.pretenard

@Composable
fun UserExperienceScreen() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.user_experience_title),
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.W700,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.user_experience_sub_title),
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        UserExperienceContent()
    }
}

private const val MAX_LENGTH = 300

@Composable
fun UserExperienceContent() {
    Column(modifier = Modifier.fillMaxHeight()) {
        var text by remember { mutableStateOf("") }
        // 유저 경험 작성 텍스트 필드
        UserExperienceTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 192.dp),
            text = text,
            textFieldType = if (text.isEmpty()) {
                LyfeTextFieldType.TC_GREY200_BG_TRANSPARENT_SC_GREY200
            } else {
                LyfeTextFieldType.TC_BLACK_BG_WHITE_SC_GREY900
            },
            onTextChange = { text = it }
        )

        Spacer(modifier = Modifier.height(4.dp))
        // 글자수 표시 텍스트
        Text(
            modifier = Modifier.align(Alignment.End),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Grey800)) {
                    append("${text.length}")
                }
                withStyle(style = SpanStyle(color = Grey400)) {
                    append("/$MAX_LENGTH")
                }
            },
            fontWeight = FontWeight.W400,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.weight(1f))
        // 피드백 보내기 버튼
        LyfeButton(
            modifier = Modifier.fillMaxWidth(),
            verticalPadding = 12.dp,
            buttonType = if (text.isEmpty()) {
                LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
            } else {
                LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
            },
            text = stringResource(R.string.send_feedback),
            isClearIconShow = false
        ) {
            if (text.isEmpty()) return@LyfeButton
            // TODO 피드백 보내기
        }
    }
}
@Composable
fun UserExperienceTextField(
    modifier: Modifier,
    text: String,
    textFieldType: LyfeTextFieldType,
    onTextChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = textFieldType.strokeColor,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .background(color = textFieldType.bgColor, shape = RoundedCornerShape(size = 8.dp))
            .padding(all = 12.dp),
        singleLine = false,
        value = text,
        textStyle = TextStyle(
            color = textFieldType.textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 24.sp,
            fontFamily = pretenard
        ),
        onValueChange = onTextChange,
    ) { innerTextField ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (text.isEmpty()) {
                Text(
                    text = stringResource(R.string.enter_feedback),
                    style = TextStyle(
                        color = Grey200,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = pretenard
                    )
                )
            }
            innerTextField()
        }
    }

}