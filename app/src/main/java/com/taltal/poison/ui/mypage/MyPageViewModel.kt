package com.taltal.poison.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.taltal.poison.util.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val sharedPrefManager: SharedPrefManager,
    private val navController: NavController,
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

    fun navigateToUp() {
        navController.navigateUp()
    }

    fun navigateToProfileSetting() {
        navController.navigate(MYPAGE.PROFILE)
    }
}
