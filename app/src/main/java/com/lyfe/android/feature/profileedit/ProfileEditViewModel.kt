package com.lyfe.android.feature.profileedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(

) : ViewModel() {

	var uiState by mutableStateOf<ProfileEditUiState>(ProfileEditUiState.Loading)
		private set

	init {
		viewModelScope.launch {
			uiState = ProfileEditUiState.Success("https://firebasestorage.googleapis.com/v0/b/grap-b9116.appspot.com/o/profile_default%2Fprofile_artist.png?alt=media&token=c3d1236d-f4a2-4482-b66a-76267cc00038&_gl=1*uevpte*_ga*ODI2NTUwODkxLjE2ODgxMDE2Mjc.*_ga_CW55HF8NVT*MTY5Nzk2MDI2Ni40LjEuMTY5Nzk2MDU2MC42MC4wLjA.", "Liam")
		}
	}
}