package io.kstud

import io.kstud.gui.KGLScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

/**
 * The "main class" of the Minecraft mod.
 * In this case, it just contains the logic for the GUI keybinding.
 */
class Kgldevmc : ClientModInitializer {

	private var openGuiKeybinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
		KeyBinding(
			"key.khack.gui",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_R,
			"category.khack"
		)
	);

	override fun onInitializeClient() {
		initializeKeybindings()
	}

	private fun initializeKeybindings() {
		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick {
			while (openGuiKeybinding.wasPressed()) {
				val client = MinecraftClient.getInstance()
				if (client.player != null && client.world != null && client.currentScreen == null) {
					// if we are in the game, and we press the keybinding, then the GUI will be displayed by creating a new instance of the KGL screen class.
					client.setScreen(KGLScreen())
				}
			}
		})
	}

}