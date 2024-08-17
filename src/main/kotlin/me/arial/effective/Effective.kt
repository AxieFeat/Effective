package me.arial.effective

import me.arial.effective.element.plate
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.Text
import ru.airdead.hudrenderer.HudEngine
import ru.airdead.hudrenderer.HudManager
import ru.airdead.hudrenderer.utility.CENTER
import ru.airdead.hudrenderer.utility.Color
import ru.airdead.hudrenderer.utility.menu
import ru.airdead.hudrenderer.utility.x

class Effective: ClientModInitializer {
    override fun onInitializeClient() {
        HudEngine.initialize()

        val plate = plate {
            size = 120 x 50
            align = CENTER
            origin = CENTER

            onHover {
                if (isHovered) {
                    animate(1.5) {
                        color = Color(40, 106, 212, 0.7)
                        strokeColor = Color(31, 97, 207)
                    }
                } else {
                    animate(1.5) {
                        color = Color(66, 135, 245, 0.5)
                        strokeColor = Color(46, 112, 219)
                    }
                }
            }
        }

        val menu = menu {
            +plate
        }

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                literal("effective")
                .executes { context ->
                    menu.show()
                    context.source.sendFeedback({ Text.literal("Called /effective with no arguments") }, false)
                    1
                })
        }

        HudManager.addElement(menu)
    }
}