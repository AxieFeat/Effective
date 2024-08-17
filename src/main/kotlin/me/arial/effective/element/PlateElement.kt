package me.arial.effective.element

import net.minecraft.client.gui.DrawContext
import ru.airdead.hudrenderer.element.AbstractElement
import ru.airdead.hudrenderer.element.Parent
import ru.airdead.hudrenderer.element.rectangle.BaseRectangleElement
import ru.airdead.hudrenderer.utility.Color
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun plate(setup: PlateElement.() -> Unit): PlateElement {
    contract {
        callsInPlace(setup, InvocationKind.EXACTLY_ONCE)
    }
    return PlateElement().also(setup)
}

@Suppress("MemberVisibilityCanBePrivate")
open class PlateElement : BaseRectangleElement(), Parent {

    /**
     * Color for element stroke
     */
    var strokeColor: Color = Color(46, 112, 219)

    /**
     * Stroke size
     */
    var strokeSize: Int = 1

    /**
     * Main element color
     */
    override var color: Color = Color(66, 135, 245, 0.5)

    /**
     * Rounding for plate
     */
    var rounding: Int = 4

    /**
     * The list of child elements.
     */
    override val children: MutableList<AbstractElement> = mutableListOf()

    /**
     * Indicates whether masking is enabled.
     */
    var mask: Boolean = false

    /**
     * Renders the rectangle element.
     *
     * @param drawContext The draw context used for rendering.
     * @param tickDelta The delta time since the last tick.
     */
    override fun render(drawContext: DrawContext, tickDelta: Float) {
        val (x1, y1) = renderLocation.run { x.toInt() to y.toInt() }
        val (width, height) = size.run { x.toInt() to y.toInt() }

        if (width <= 0 || height <= 0) return

        texture?.let {
            drawContext.drawTexture(
                it, x1, y1, width, height, 0.0f, 0.0f,
                regionSize.x.toInt(), regionSize.y.toInt(), textureSize.x.toInt(), textureSize.y.toInt()
            )
        } ?: run {
            val color = color.toInt()
            val strokeColor = strokeColor.toInt()

            // Center element
            drawContext.fill(
                x1 + rounding + strokeSize,
                y1 + strokeSize,

                x1 + width - rounding - strokeSize,
                y1 + height - strokeSize,

                color
            )

            // Left element
            drawContext.fill(
                x1 + strokeSize,
                y1 + rounding + strokeSize,

                x1 + rounding + strokeSize,
                y1 + height - rounding - strokeSize,

                color
            )

            // Right element
            drawContext.fill(
                x1 + width - strokeSize,
                y1 + rounding + strokeSize,

                x1 + width - rounding - strokeSize,
                y1 + height - rounding - strokeSize,

                color
            )





            // STROKE below




            // side strokes

            // top
            drawContext.fill(
                x1 + rounding,
                y1,

                x1 + width - rounding,
                y1 + strokeSize,

                strokeColor
            )

            // bottom
            drawContext.fill(
                x1 + rounding,
                y1 + height - strokeSize,

                x1 + width - rounding,
                y1 + height,

                strokeColor
            )

            // left
            drawContext.fill(
                x1,
                y1 + rounding,

                x1 + strokeSize,
                y1 + height - rounding,

                strokeColor
            )

            // right
            drawContext.fill(
                x1 + width - strokeSize,
                y1 + rounding,

                x1 + width,
                y1 + height - rounding,

                strokeColor
            )

            // end side-strokes




            // Top left stroke

            drawContext.fill(
                x1 + rounding,
                y1 + strokeSize,

                x1 + rounding + strokeSize,
                y1 + rounding + strokeSize,

                strokeColor
            )

            drawContext.fill(
                x1 + strokeSize,
                y1 + rounding + strokeSize,

                x1 + rounding,
                y1 + rounding,

                strokeColor
            )

            // End top left stroke




            // Bottom left stroke

            drawContext.fill(
                x1 + rounding,
                y1 + height - strokeSize,

                x1 + rounding + strokeSize,
                y1 + height - rounding - strokeSize,

                strokeColor
            )

            drawContext.fill(
                x1 + strokeSize,
                y1 + height - rounding - strokeSize,

                x1 + rounding,
                y1 + height - rounding,

                strokeColor
            )

            // End bottom left stroke




            // Top right stroke

            drawContext.fill(
                x1 + width - rounding,
                y1 + strokeSize,

                x1 + width - rounding - strokeSize,
                y1 + rounding + strokeSize,

                strokeColor
            )

            drawContext.fill(
                x1 + width - strokeSize,
                y1 + rounding + strokeSize,

                x1 + width - rounding,
                y1 + rounding,

                strokeColor
            )

            // End top right stroke




            // Bottom right stroke

            drawContext.fill(
                x1 + width - rounding,
                y1 + height - strokeSize,

                x1 + width - rounding - strokeSize,
                y1 + height - rounding - strokeSize,

                strokeColor
            )

            drawContext.fill(
                x1 + width - strokeSize,
                y1 + height - rounding - strokeSize,

                x1 + width - rounding,
                y1 + height - rounding,

                strokeColor
            )

            // End bottom right stroke
        }


        renderContent(drawContext, tickDelta)
        renderChildren(drawContext, tickDelta)
    }

    override fun isHovered(mouseX: Double, mouseY: Double): Boolean {
        val (locX, locY) = renderLocation.run { x.toInt() to y.toInt() }
        val (width, height) = size.run { x.toInt() to y.toInt() }

        val (x1, y1) = (locX + rounding)..(locX + width - rounding) to locY..(locY + height)
        val (x2, y2) = locX..(locX + width) to (locY + rounding)..(locY + height - rounding)

        return mouseX.toInt() in x1 && mouseY.toInt() in y1 || mouseX.toInt() in x2 && mouseY.toInt() in y2
    }

    override fun renderChildren(drawContext: DrawContext, tickDelta: Float) {}

    override fun renderContent(drawContext: DrawContext, tickDelta: Float) {}
}