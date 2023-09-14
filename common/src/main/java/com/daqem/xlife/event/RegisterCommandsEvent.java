package com.daqem.xlife.event;

import com.daqem.xlife.command.XLifeCommand;
import dev.architectury.event.events.common.CommandRegistrationEvent;

public class RegisterCommandsEvent {

    public static void registerEvent() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> XLifeCommand.registerCommand(dispatcher));
    }
}
