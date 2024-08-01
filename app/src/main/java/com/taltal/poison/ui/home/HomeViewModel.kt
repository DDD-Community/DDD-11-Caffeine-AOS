package com.taltal.poison.ui.home

import androidx.lifecycle.ViewModel
import com.taltal.poison.data.api.PoisonApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    poisonApi: PoisonApi
) : ViewModel() {


}
