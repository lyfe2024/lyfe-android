package com.lyfe.android.feature.nickname

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.feature.profileedit.NicknameFormState
import com.lyfe.android.feature.profileedit.ProfileEditViewModel

@Composable
fun NicknameScreen(
	viewModel: ProfileEditViewModel = hiltViewModel()
) {
	Column(
		modifier = Modifier
			.padding(top = 40.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
			.fillMaxSize()
	) {
		Text(
			text = "닉네임 설정",
			style = TextStyle(
				fontSize = 24.sp,
				lineHeight = 36.sp,
				fontWeight = FontWeight(weight = 700),
				color = Color(color = 0xFF000000)
			)
		)

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = "원하는 닉네임을 설정해주세요.",
			style = TextStyle(
				fontSize = 14.sp,
				fontWeight = FontWeight(weight = 600),
				color = Color(color = 0xFF000000)
			)
		)

		Spacer(modifier = Modifier.height(48.dp))

		NicknameEnterContent(viewModel)
	}
}

@Composable
private fun NicknameEnterContent(
	viewModel: ProfileEditViewModel
) {
	val isNicknameEnable = remember { mutableStateOf(false) }
	val nicknameFormStateList = remember { mutableStateListOf<NicknameFormState>() }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 24.dp, bottom = 12.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			NicknameTextBox(
				viewModel = viewModel,
				isNicknameEnableStateChanged = { isNicknameEnable.value = it },
				onNicknameInvalidReasonsChanged = {
					nicknameFormStateList.clear()
					nicknameFormStateList.addAll(it)
				}
			)

			Spacer(modifier = Modifier.height(10.dp))

			NicknameConditionTextArea(
				nicknameInvalidReasons = nicknameFormStateList
			)

			Spacer(modifier = Modifier.weight(1f))

			NicknameCompleteButton(isNicknameEnable.value)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NicknameTextBox(
	viewModel: ProfileEditViewModel,
	isNicknameEnableStateChanged: (Boolean) -> Unit,
	onNicknameInvalidReasonsChanged: (List<NicknameFormState>) -> Unit
) {
	// 닉네임 변경하는 부분
	val nicknameState = remember { mutableStateOf("") }
	val nicknameFormStateList = remember { mutableStateListOf<NicknameFormState>() }
	Column {
		Column {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.border(width = 1.dp, color = Color(color = 0xFF363636), shape = RoundedCornerShape(8.dp))
					.padding(horizontal = 4.dp),
				contentAlignment = Alignment.CenterStart
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					TextField(
						modifier = Modifier.weight(1f),
						value = nicknameState.value,
						placeholder = { Text(text = "닉네임을 입력해주세요") },
						colors = TextFieldDefaults.textFieldColors(
							containerColor = Color.Transparent,
							focusedIndicatorColor = Color.Transparent,
							unfocusedIndicatorColor = Color.Transparent,
							disabledIndicatorColor = Color.Transparent
						),
						onValueChange = {
							nicknameState.value = it
							nicknameFormStateList.clear()
							// 유용한 닉네임 여부 확인
							val nicknameInvalidReasons = viewModel.getNicknameInvalidReason(it)
							isNicknameEnableStateChanged(nicknameInvalidReasons.isEmpty())
							// 닉네임 불가능 이유 설정
							if (it.isEmpty()) {
								nicknameFormStateList.clear()
							} else if (nicknameInvalidReasons.isEmpty()) {
								nicknameFormStateList.add(NicknameFormState.CORRECT) // 불가능한 이유 없을 경우 CORRECT
							} else {
								nicknameInvalidReasons.forEach { reason -> nicknameFormStateList.add(reason) }
							}
							onNicknameInvalidReasonsChanged(nicknameFormStateList)
						}
					)

					IconButton(
						modifier = Modifier
							.size(32.dp)
							.padding(6.dp),
						onClick = {
							nicknameState.value = ""
							nicknameFormStateList.clear()
							isNicknameEnableStateChanged(false)
							onNicknameInvalidReasonsChanged(nicknameFormStateList)
						}
					) {
						Icon(
							imageVector = Icons.Filled.Close,
							contentDescription = null
						)
					}
				}
			}
		}
	}
}

@Composable
private fun NicknameConditionTextArea(
	nicknameInvalidReasons: List<NicknameFormState>
) {
	var assetIndex1 = 2
	var assetIndex2 = 2
	var assetIndex3 = 2
	nicknameInvalidReasons.forEach { reason ->
		when (reason) {
			NicknameFormState.NEED_LETTER_NUMBER_COMBINATION -> assetIndex1 = 1
			NicknameFormState.CONTAIN_SPECIAL_CHAR -> assetIndex2 = 1
			NicknameFormState.NICKNAME_FORM_TOO_LONG -> assetIndex3 = 1
			NicknameFormState.CORRECT -> {
				assetIndex1 = 2; assetIndex2 = 2; assetIndex3 = 2
			}
		}
	}
	if (nicknameInvalidReasons.isEmpty()) {
		assetIndex1 = 0; assetIndex2 = 0; assetIndex3 = 0
	}
	val painterResources = listOf(
		painterResource(id = R.drawable.ic_check_gray),
		painterResource(id = R.drawable.ic_check_red),
		painterResource(id = R.drawable.ic_check_blue)
	)
	val colorResources = listOf(
		Color(color = 0xFFC6C6C6),
		Color(color = 0xFFEE483D),
		Color(color = 0xFF1F72E0)
	)

	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		NicknameConditionText(
			painterResources = painterResources[assetIndex1],
			text = "영문+숫자 조합",
			color = colorResources[assetIndex1]
		)

		NicknameConditionText(
			painterResources = painterResources[assetIndex2],
			text = "특수문자 사용 X",
			color = colorResources[assetIndex2]
		)

		NicknameConditionText(
			painterResources = painterResources[assetIndex3],
			text = "최대 10글자",
			color = colorResources[assetIndex3]
		)
	}
}

@Composable
private fun NicknameConditionText(
	painterResources: Painter,
	text: String,
	color: Color
) {
	Row {
		Image(
			painter = painterResources,
			contentDescription = "기본은 회색"
		)

		Spacer(modifier = Modifier.width(4.dp))

		Text(
			text = text,
			color = color,
			fontSize = 14.sp
		)
	}
}

@Composable
private fun NicknameCompleteButton(
	isEnableState: Boolean
) {
	val context = LocalContext.current
	// 완료 버튼
	OutlinedButton(
		modifier = Modifier
			.height(48.dp)
			.fillMaxWidth()
			.background(
				color = if (isEnableState) Color(color = 0xff202124) else Color(color = 0xfff2f3f4),
				shape = RoundedCornerShape(24.dp)
			)
			.clip(RoundedCornerShape(10.dp)),
		onClick = {
			Toast.makeText(context, "현재 닉네임 가능 여부: $isEnableState", Toast.LENGTH_SHORT).show()
		}
	) {
		Text(
			text = "완료",
			color = if (isEnableState) Color.White else Color(color = 0xff8c8c8c),
			textAlign = TextAlign.Center,
			fontSize = 16.sp
		)
	}
}