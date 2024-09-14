package com.taltal.poison.ui.onboard

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taltal.poison.data.repository.PoisonCoffeeRepository
import com.taltal.poison.ui.navigation.NavRoute
import com.taltal.poison.util.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
    @Inject
    constructor(
        private val coffeeRepository: PoisonCoffeeRepository,
    ) : ViewModel() {
        private val _nickname = MutableStateFlow<String>("")
        val nickname: StateFlow<String> = _nickname

        private val _isNicknameValid = MutableStateFlow<Boolean>(false)
        val isNicknameValid: StateFlow<Boolean> = _isNicknameValid

        private val _errorText = MutableStateFlow<String>("")
        val errorText: StateFlow<String> = _errorText

        private val _gender = MutableStateFlow<String>("")
        val gender: StateFlow<String> = _gender

        private val _birth = MutableStateFlow<String>("")
        private val _weight = MutableStateFlow<String>("")
        private val _height = MutableStateFlow<String>("")
        val isProfileFullFilled =
            combine(
                listOf(_gender, _birth, _weight, _height),
            ) {
                it.all { value -> value.isNotEmpty() }
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

        private val _chosenPurpose = MutableStateFlow<String>("")
        val chosenPurpose: StateFlow<String> = _chosenPurpose

        private val _chosenGoal = MutableStateFlow<String>("")
        val chosenGoal: StateFlow<String> = _chosenGoal

        private val _dailyGoalNumber = MutableStateFlow<Int>(2)
        val dailyGoalNumber: StateFlow<Int> = _dailyGoalNumber

        private val _weeklyGoalNumber = MutableStateFlow<Int>(2)
        val weeklyGoalNumber: StateFlow<Int> = _weeklyGoalNumber

        val pagerState =
            PagerState(0) {
                5
            }

        private val _navigator = MutableSharedFlow<NavRoute>()
        val navigator: SharedFlow<NavRoute> get() = _navigator.asSharedFlow()

        fun updateNickname(newNickname: String) {
            viewModelScope.launch {
                _nickname.value = newNickname
                val isNicknameValid = checkNickNameValidation(newNickname)
                _isNicknameValid.value = isNicknameValid
                if (isNicknameValid) {
                    _errorText.value = ""
                } else {
                    _errorText.value = "잘못된 입력입니다."
                }
            }
        }

        fun moveToNextPage() {
            viewModelScope.launch {
                val nextPage = pagerState.currentPage + 1
                if (nextPage < pagerState.pageCount) {
                    pagerState.scrollToPage(nextPage)
                }
            }
        }

        fun delayAndMoveToNextPage() {
            viewModelScope.launch {
                delay(4000)
                _dailyGoalNumber.value = getRecommendCaffeineIntake()
                moveToNextPage()
            }
        }

        fun moveToPreviousPage() {
            viewModelScope.launch {
                val previousPage =
                    if (pagerState.currentPage != 4) {
                        pagerState.currentPage - 1
                    } else {
                        pagerState.currentPage - 2
                    }
                if (previousPage >= 0) {
                    pagerState.scrollToPage(previousPage)
                }
            }
        }

        fun checkNickNameValidation(nickname: String): Boolean {
            val regex = "^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ0-9]{1,8}$".toRegex()
            return regex.matches(nickname)
        }

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

        fun updateTargetPurpose(purpose: String) {
            viewModelScope.launch {
                _chosenPurpose.value = purpose
            }
        }

        fun updateGoal(goal: String) {
            viewModelScope.launch {
                _chosenGoal.value = goal
            }
        }

        fun updateDailyGoalNumber(number: Int) {
            viewModelScope.launch {
                _dailyGoalNumber.value = number
            }
        }

        fun updateWeeklyGoalNumber(number: Int) {
            viewModelScope.launch {
                _weeklyGoalNumber.value = number
            }
        }

        suspend fun getRecommendCaffeineIntake(): Int =
            coffeeRepository.getRecommendCaffeineIntake(
                birth = _birth.value.toInt(),
                gender = if (gender.value == "여성") "FEMALE" else "MALE",
                height = _height.value.toInt(),
                weight = _weight.value.toInt(),
            )

        fun checkUserNicknameDuplicate() {
            viewModelScope.launch {
                val isDuplicate =
                    coffeeRepository.checkNicknameDuplicate(nickname.value) || nickname.value == "test"
                if (isDuplicate) {
                    _errorText.value = "이미 사용중인 닉네임입니다."
                    _isNicknameValid.value = false
                } else {
                    _errorText.value = ""
                    moveToNextPage()
                }
            }
        }

        fun uploadUserData(sharedPrefManager: SharedPrefManager) {
            viewModelScope.launch {
                val userId =
                    coffeeRepository
                        .uploadUserStatus(
                            nickname = nickname.value,
                            height = _height.value.toInt(),
                            weight = _weight.value.toInt(),
                            purpose = if (chosenPurpose.value == "카페인 기록") "CAFFEINE_LOG" else "CAFFEINE_REDUCE",
                            gender = if (gender.value == "여성") "FEMALE" else "MALE",
                            birth = _birth.value,
                            targetNum = dailyGoalNumber.value,
                        ).id
                sharedPrefManager.setUserData(
                    id = userId,
                    nickname = nickname.value,
                    goalNumber = dailyGoalNumber.value,
                    forLogging = chosenPurpose.value == "카페인 기록",
                    gender = gender.value,
                    birthDay = _birth.value,
                    height = _height.value,
                    weight = _weight.value,
                )
                _navigator.emit(NavRoute.Home)
            }
        }
    }
