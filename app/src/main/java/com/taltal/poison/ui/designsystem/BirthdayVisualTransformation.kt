package com.taltal.poison.ui.designsystem

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class BirthdayVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // 입력된 텍스트를 YYYY.MM.DD 형식으로 변환
        val trimmed = if (text.text.length >= 8) text.text.substring(0, 8) else text.text
        var output = ""
        for (i in trimmed.indices) {
            output += trimmed[i]
            if (i == 3 || i == 5) output += "."
        }

        val offsetMapping =
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 5) return offset + 1
                    if (offset <= 8) return offset + 2
                    return 10
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 4) return offset
                    if (offset <= 7) return offset - 1
                    if (offset <= 10) return offset - 2
                    return 8
                }
            }

        return TransformedText(AnnotatedString(output), offsetMapping)
    }
}
