package me.arial.effective.element.text

typealias KeyboardHandler = KeyboardContext.() -> Unit
data class KeyboardContext(val char: Char)

typealias FocusHandler = FocusChange.() -> Unit
data class FocusChange(val status: Boolean)