package dev.isxander.controlify.ingame;

import dev.isxander.controlify.InputMode;
import dev.isxander.controlify.controller.Controller;
import dev.isxander.controlify.event.ControlifyEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.KeyboardInput;

public class InGameInputHandler {
    private final Controller controller;
    private final Minecraft minecraft;

    private double lookInputX, lookInputY;

    public InGameInputHandler(Controller controller) {
        this.controller = controller;
        this.minecraft = Minecraft.getInstance();

        ControlifyEvents.INPUT_MODE_CHANGED.register(mode -> {
            if (minecraft.player != null) {
                minecraft.player.input = mode == InputMode.CONTROLLER
                        ? new ControllerPlayerMovement(controller, minecraft.player)
                        : new KeyboardInput(minecraft.options);
            }
        });
    }

    public void inputTick() {
        handlePlayerLookInput();
        handleKeybinds();
    }

    protected void handleKeybinds() {
        if (Minecraft.getInstance().screen != null && !Minecraft.getInstance().screen.passEvents)
            return;

        if (controller.bindings().PAUSE.justPressed()) {
            minecraft.pauseGame(false);
        }
        if (minecraft.player != null) {
            if (controller.bindings().NEXT_SLOT.justPressed()) {
                minecraft.player.getInventory().swapPaint(-1);
            }
            if (controller.bindings().PREV_SLOT.justPressed()) {
                minecraft.player.getInventory().swapPaint(1);
            }
        }
        if (controller.bindings().TOGGLE_HUD_VISIBILITY.justPressed()) {
            minecraft.options.hideGui = !minecraft.options.hideGui;
        }
    }

    protected void handlePlayerLookInput() {
        var axes = controller.state().axes();
        if (minecraft.mouseHandler.isMouseGrabbed() && minecraft.isWindowActive()) {
            lookInputX = axes.rightStickX() * Math.abs(axes.rightStickX()) * controller.config().horizontalLookSensitivity;
            lookInputY = axes.rightStickY() * Math.abs(axes.rightStickY()) * controller.config().verticalLookSensitivity;
        } else {
            lookInputX = lookInputY = 0;
        }
    }

    public void processPlayerLook(float deltaTime) {
        if (minecraft.player != null) {
            minecraft.player.turn(lookInputX * 50f * deltaTime, lookInputY * 50f * deltaTime);
        }
    }
}
