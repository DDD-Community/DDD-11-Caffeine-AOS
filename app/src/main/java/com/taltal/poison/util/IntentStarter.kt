package com.taltal.poison.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object IntentStarter {
    const val QUESTION_FORM =
        "https://docs.google.com/forms/d/e/1FAIpQLSezt40EJsJgkQI8ZInnEwLxHhcBXOnK2uIjlGEXNccJHe8w9g/viewform"
    const val USING_CONDITION_FORM =
        "https://docs.google.com/document/d/1mApEWwqF-Wan6mBUuYi-ss-e-ean8-F5fI8wtavai-w/edit?usp=sharing"
    const val PRIVACY_POLICY_FORM =
        "https://docs.google.com/document/d/1weZOPYV3gTBzhYtc1wE6mph6f2ShkCYw4sDboFkG7bw/edit?usp=sharing"
    const val REMOVE_ID =
        "https://docs.google.com/forms/d/e/1FAIpQLSc96Zqj96q5X5aY5CwZauzRzwTkFqenB6wi0ieSegMV4ERAOw/viewform?usp=sf_link"

    fun startQuestionForm(context: Context) {
        startWebIntent(context, QUESTION_FORM)
    }

    fun startUsingConditionForm(context: Context) {
        startWebIntent(context, USING_CONDITION_FORM)
    }

    fun startPrivacyPolicyForm(context: Context) {
        startWebIntent(context, PRIVACY_POLICY_FORM)
    }

    fun startRemoveIdForm(context: Context) {
        startWebIntent(context, REMOVE_ID)
    }

    private fun startWebIntent(
        context: Context,
        form: String,
    ) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(form))
        context.startActivity(intent)
    }
}
