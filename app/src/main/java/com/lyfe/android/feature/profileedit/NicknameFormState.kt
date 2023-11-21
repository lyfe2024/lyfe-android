package com.lyfe.android.feature.profileedit

enum class NicknameFormState(
	val message: String
) {
	CORRECT("사용할 수 있는 닉네임입니다."),
	NICKNAME_FORM_TOO_LONG("닉네임은 최대 10글자까지 설정 가능합니다."),
	CONTAIN_SPECIAL_CHAR("닉네임은 특수문자를 포함할 수 없습니다."),
	NEED_LETTER_NUMBER_COMBINATION("닉네임은 한글/영문+숫자 조합으로 이루어져야 합니다.")
}