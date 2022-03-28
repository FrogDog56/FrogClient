package me.frogdog.hecks.module.modules.combat;

import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;
import me.frogdog.hecks.property.NumberProperty;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class AutoLog extends ToggleableModule {
    private final NumberProperty<Integer> health = new NumberProperty<>(8, 0, 20, "Health");

    public AutoLog() {
        super("AutoLog", new String[] {"AutoLog"}, ModuleType.COMBAT);
        this.offerProperties(this.health, this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (mc.player.getHealth() < health.getValue()) {
            //mc.getConnection().onDisconnect(new TextComponentString("[Hecks.exe] Disconnected because health was " + mc.player.getHealth()));
            mc.world.sendQuittingDisconnectingPacket();
            this.toggle();
        }
    }
}
