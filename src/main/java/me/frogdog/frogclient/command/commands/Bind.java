package me.frogdog.frogclient.command.commands;

import org.lwjgl.input.Keyboard;

import me.frogdog.frogclient.Frog;
import me.frogdog.frogclient.command.Command;

public final class Bind extends Command {

	public Bind() {
		super(new String[] {"bind", "keybind"}, "bind (module) (key)");
	}

	@Override
	public void onClientCommand(String command, String[] args) throws Exception {
		String key = args[1].toUpperCase();
		Frog.getInstance().getKeybindManager().getKeybindByLabel(args[0]).setKey(Keyboard.getKeyIndex(key));
		Command.sendClientSideMessage(args[0] + "'s bind has been set to " + key);
		
	}

}
