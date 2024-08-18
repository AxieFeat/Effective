package me.arial.effective.element.board

import me.arial.effective.element.PlateElement
import net.minecraft.client.gui.DrawContext
import ru.airdead.hudrender.utility.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun board(setup: ScoreBoardElement.() -> Unit): ScoreBoardElement {
    contract {
        callsInPlace(setup, InvocationKind.EXACTLY_ONCE)
    }
    return ScoreBoardElement().also(setup)
}

@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
open class ScoreBoardElement : PlateElement() {

    override var rounding: Int = 2
    override var color: Color = Color(20, 20, 20, 0.4)
    override var strokeColor: Color = Color(15, 15, 15, 0.5)
    override var align = RIGHT
    override var origin = RIGHT
    override var offset = -6 x 0

    open var width: Int = 120
        set(value) {
            field = value

            size = value x 40 + (lineCount * 10)
        }
    open var lineCount: Int = 6
        set(value) {
            field = value

            size = width x 40 + (value * 10)
        }
    override var size: V3 = width x 40 + (lineCount * 10)

    open var header: String = ""
    open var footer: String = "effective.net"
    open var lines: MutableList<String> = mutableListOf()
        set(value) {
            field = value

            val step = 5
            var last = 10

            value.forEachIndexed { index, line ->
                if (index > lineCount) {
                    return@forEachIndexed
                }

                +colorText {
                    align = TOP_LEFT
                    origin = CENTER
                    offset = 6 x last
                    text = line
                }

                last += step
            }
        }

    override fun renderContent(drawContext: DrawContext, tickDelta: Float) {
        // header
        +colorText {
            align = TOP
            origin = CENTER
            offset = 0 x 6
            text = header
        }


        // footer
        +colorText {
            align = BOTTOM
            origin = CENTER
            offset = 0 x -6
            text = footer
        }
    }

    open fun lines(vararg line: String) {
        this.lines = line.toMutableList()
    }
}