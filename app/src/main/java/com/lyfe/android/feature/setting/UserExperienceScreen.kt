package com.lyfe.android.feature.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeTextFieldWithBottomCount
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType

@Composable
fun UserExperienceScreen() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "사용경험",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.W700,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Lyfe에서의 경험을 편하게 말씀해주세요.\n" +
                "긍정/부정 적인 피드백 무엇이든 괜찮아요.",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        UserFeedbackContent()
    }
}

@Composable
fun UserFeedbackContent() {
    Column(modifier = Modifier.fillMaxHeight()) {
        var text by remember { mutableStateOf("") }

        LyfeTextFieldWithBottomCount(
            maxLength = 500,
            text = text,
            hintText = "피드백 내용을 입력해주세요.",
            textFieldType = LyfeTextFieldType.TC_GREY200_BG_TRANSPARENT_SC_GREY200,
            isActivateCloseIcon = false,
            onTextChange = { text = it },
            onTextClear = { text = "" },
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
            text = "피드백 보내기",
            isClearIconShow = false
        ) {
            if (text.isEmpty()) return@LyfeButton
            // TODO 피드백 보내기
        }
    }
}