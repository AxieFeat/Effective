package me.arial.effective.element.text

import me.arial.effective.element.PlateElement
import net.minecraft.client.gui.DrawContext
import ru.airdead.hudrender.HudEngine
import ru.airdead.hudrender.element.AbstractElement
import ru.airdead.hudrender.element.text.ColorTextElement
import ru.airdead.hudrender.utility.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun field(setup: TextFieldElement.() -> Unit): TextFieldElement {
    contract {
        callsInPlace(setup, InvocationKind.EXACTLY_ONCE)
    }
    return TextFieldElement().also(setup)
}

/**
 * A class representing a text input field.
 * Inherits from [AbstractElement] and provides methods for handling text input.
 */
@Suppress("MemberVisibilityCanBePrivate")
open class TextFieldElement : PlateElement() {

    /**
     * Preview text in text field. If [text] is not empty not be rendered
     */
    var preview: ColorTextElement? = null
        set(value) {
            field = value

            if (preview != null) +preview!!

            value?.enabled = text.isEmpty()
        }

    /**
     * The current text content of the input field.
     */
    var text: String = ""
        set(value) {
            field = value

            preview?.enabled = value.isEmpty()
        }

    /**
     * Cursor index location
     */
    var cursor: Int = 0

    /**
     * The maximum length of the text that can be entered.
     */
    var maxLength: Int = 15

    /**
     * Indicates whether the input field is focused and ready to accept input.
     */
    var focused: Boolean = false
        set(value) {
            field = value

            onFocusChange?.invoke(FocusChange(value))
        }

    /**
     * The color of the text.
     */
    var textColor: Color = Color.WHITE

    private var onTextInput: KeyboardHandler? = null
    private var onFocusChange: FocusHandler? = null
    private var onTextDelete: KeyboardHandler? = null

    fun onFocusChange(handler: FocusHandler) {
        this.onFocusChange = handler
    }

    fun onTextInput(handler: KeyboardHandler) {
        this.onTextInput = handler
    }

    fun onTextDelete(handler: KeyboardHandler) {
        onTextDelete = handler
    }

    /**
     * Handles key press events and updates the text content if the input field is focused.
     */
    fun handleKeyPress(char: Char, keyCode: Int) {
        if (!focused) return

        when (keyCode) {
            259 -> { // Backspace
                if (text.isNotEmpty()) {
                    text = text.substring(0, text.length - 1)
                    onTextDelete?.invoke(KeyboardContext(' '))
                }
            }
            257, 335 -> { // Enter key
                focused = false
            }
            else -> {
                if (char.isLetterOrDigit() || char.isWhitespace()) {
                    if (text.length < maxLength) {
                        text += char
                    }
                    onTextInput?.invoke(KeyboardContext(char))
                }
            }
        }
    }

    /**
     * Renders the input field, including the text, background, and border.
     */
    override fun renderContent(drawContext: DrawContext, tickDelta: Float) {
        val (x, y) = renderLocation.run { x.toInt() to y.toInt() }
        val width = size.x.toInt()
        val height = size.y.toInt()

        val textRenderer = HudEngine.clientApi.minecraft()?.textRenderer ?: return
        val textY = y + (height - textRenderer.fontHeight) / 2 + 1
        drawContext.drawText(textRenderer, text, x + 5, textY, textColor.toInt(), false)
    }

    /**
     * Handles mouse click events and focuses the input field if it is clicked.
     */
    override fun handleMouseClick(button: MouseButton, context: ClickContext) {
        super.handleMouseClick(button, context)
        if (button == MouseButton.LEFT) {
            focused = isHovered(context.mouseX, context.mouseY)
        }
    }
}