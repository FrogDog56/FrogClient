package me.frogdog.frogclient.events;

import me.frogdog.api.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event
{
    public ScaledResolution getResolution()
    {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

}
