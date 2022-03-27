package me.frogdog.hecks.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.command.Command;
import me.frogdog.hecks.module.Module;
import me.frogdog.hecks.module.ToggleableModule;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventProcessor {

    public static final EventProcessor INSTANCE = new EventProcessor();

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (Hecks.getInstance().mc.world != null && Hecks.getInstance().mc.player != null) {
            Hecks.getInstance().getModuleManager().update(event);
        }
    }

    @SubscribeEvent
    public void onChatSend(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith(Command.getPrefix())) {
            event.setCanceled(true);
            try {
                Hecks.getInstance().mc.ingameGUI.getChatGUI().addToSentMessages(message);
                Hecks.getInstance().getCommandManager().callClientCommand(message.substring(1));
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendClientSideMessage(ChatFormatting.DARK_GREEN + "Error: " + e.getMessage());
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(!(Keyboard.isRepeatEvent()) && !(Keyboard.isKeyDown(Keyboard.getEventKey()))) {
            me.frogdog.hecks.event.events.InputEvent inputEvent = new me.frogdog.hecks.event.events.InputEvent(me.frogdog.hecks.event.events.InputEvent.Type.KEYBOARD_KEY_PRESS);

            Hecks.getInstance().getKeybindManager().update(inputEvent);
        }
    }

    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent event) {
        if (Hecks.getInstance().mc.world != null) {
            Hecks.getInstance().getHudManager().renderHud(event);
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        if (Hecks.getInstance().mc.world != null) {
            Hecks.getInstance().getModuleManager().render(event);
        }
    }


}
