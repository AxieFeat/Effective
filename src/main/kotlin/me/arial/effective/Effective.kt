package me.arial.effective

import me.arial.effective.element.board.board
import me.arial.effective.element.button.button
import me.arial.effective.element.plate
import me.arial.effective.element.text.field
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.Text
import ru.airdead.hudrender.HudEngine
import ru.airdead.hudrender.HudManager
import ru.airdead.hudrender.utility.*

class Effective: ClientModInitializer {
    override fun onInitializeClient() {
        HudEngine.initialize()

        val plate = plate {
            size = 750 x 400
            align = CENTER
            origin = CENTER
        }

        val menu = menu {
            //+plate
//            +field {
//                align = CENTER
//                origin = CENTER
//                rounding = 2
//                size = 100 x 20
//
//                focused = true
//
//                preview = colorText {
//                    origin = CENTER
//                    align = CENTER
//
//                    text = "&7Поиск"
//                }
//            }
            +button {
                align = CENTER
                origin = CENTER
                rounding = 2
                size = 100 x 20
                text = "Пример кнопки"

                onLeftClick {
                    if (isPressed) {
                        animate(2.0) {
                            size = 110 x 25
                        }
                    } else {
                        animate(2.0) {
                            size = 100 x 20
                        }
                    }
                }
            }

//            +rectangle {
//                align = CENTER
//                origin = CENTER
//                size = 100 x 20
//                color = Color.WHITE
//
//                onLeftClick {
//                    if (isPressed) {
//                        animate(2.0) {
//                            size = 110 x 25
//                        }
//                    } else {
//                        animate(2.0) {
//                            size = 100 x 20
//                        }
//                    }
//                }
//            }
        }

        val board = board {
            enabled = false
            header = "Пример борда"
            lineCount = 6

            lines(
                "Первая линия",
                "Вторая линия",
                "Третья линия",
                "Четвертая линия",
                "Пятая линия",
                "Шестая линия"
            )
        }

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                literal("effective")
                .executes { context ->
                    menu.show()
                    board.enabled = !board.enabled
                    context.source.sendFeedback({ Text.literal("Called /effective with no arguments") }, false)
                    1
                })
        }

        HudManager.addElement(menu)

        HudManager.addElement(board)
    }
}