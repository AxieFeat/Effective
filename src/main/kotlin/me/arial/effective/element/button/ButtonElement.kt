package me.arial.effective.element.button

import me.arial.effective.element.PlateElement
import net.minecraft.client.gui.DrawContext
import ru.airdead.hudrender.utility.CENTER
import ru.airdead.hudrender.utility.Color
import ru.airdead.hudrender.utility.colorText
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun button(setup: ButtonElement.() -> Unit): ButtonElement {
    contract {
        callsInPlace(setup, InvocationKind.EXACTLY_ONCE)
    }
    return ButtonElement().also(setup)
}

@Suppress("MemberVisibilityCanBePrivate")
open class ButtonElement : PlateElement() {

    open var text: String = ""

    override fun renderContent(drawContext: DrawContext, tickDelta: Float) {

        onHover {
            if (isHovered) {
                animate(0.3) {
                    color = Color(93, 151, 245, 0.7)
                    strokeColor = Color(105, 159, 245)
                }
            } else {
                animate(0.3) {
                    color = Color(66, 135, 245, 0.5)
                    strokeColor = Color(46, 112, 219)
                }
            }
        }

        +colorText {
            align = CENTER
            origin = CENTER
            size = this@ButtonElement.size
            text = this@ButtonElement.text
        }
    }
}