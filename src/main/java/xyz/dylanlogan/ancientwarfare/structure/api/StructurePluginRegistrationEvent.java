package xyz.dylanlogan.ancientwarfare.structure.api;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * Called when StructurePluginManager tries to load plugins, for each active mod
 * To be cancelled by mod wishing to have different block/entity support in structures, other than the default
 * StructurePluginModDefault
 */
@Cancelable
public class StructurePluginRegistrationEvent extends Event {

    public final IStructurePluginRegister register;
    public final String modId;

    public StructurePluginRegistrationEvent(IStructurePluginRegister register, String mod) {
        this.register = register;
        this.modId = mod;
    }

}
