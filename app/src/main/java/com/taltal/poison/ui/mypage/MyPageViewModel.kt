package com.taltal.poison.ui.mypage

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.taltal.poison.util.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val sharedPrefManager: SharedPrefManager,
) : ViewModel() {
    private val _gender = MutableStateFlow<String>(sharedPrefManager.getGender())
    val gender: StateFlow<String> = _gender

    private val _birth = MutableStateFlow<String>(sharedPrefManager.getBirthday())
    val birth: StateFlow<String> = _birth

    private val _weight = MutableStateFlow<String>(sharedPrefManager.getWeight())
    val weight: StateFlow<String> = _weight

    private val _height = MutableStateFlow<String>(sharedPrefManager.getHeight())
    val height: StateFlow<String> = _height

    val isProfileFullFilled =
        combine(
            listOf(_gender, _birth, _weight, _height),
        ) {
            it.all { value -> value.isNotEmpty() }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun updateGenderOption(gender: String) {
        viewModelScope.launch {
            _gender.value = gender
        }
    }

    fun updateBirth(birth: String) {
        viewModelScope.launch {
            _birth.value = birth
        }
    }

    fun updateWeight(weight: String) {
        viewModelScope.launch {
            _weight.value = weight
        }
    }

    fun updateHeight(height: String) {
        viewModelScope.launch {
            _height.value = height
        }
    }

    fun navigateToProfileSetting(navController: NavController) {
        val context = navController.context
        val intent = Intent(context, ProfileSettingActivity::class.java)
        context.startActivity(intent)
    }

    fun updateProfile(finishUpdate: () -> Unit) {
        sharedPrefManager.updateUserData(
            gender = gender.value,
            birthDay = birth.value,
            height = height.value,
            weight = weight.value,
        )
        finishUpdate.invoke()
    }
}
