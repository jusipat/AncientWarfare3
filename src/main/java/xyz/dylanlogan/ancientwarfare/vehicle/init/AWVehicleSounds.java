package xyz.dylanlogan.ancientwarfare.vehicle.init;

import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;
import cpw.mods.fml.common.registry.GameRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;

@GameRegistry.ObjectHolder(AncientWarfareVehicles.MOD_ID)
@EventBusSubscriber
public class AWVehicleSounds {
//	public static final SoundEvent BALLISTA_BOLT_HIT_ENTITY = InjectionTools.nullValue();
//	public static final SoundEvent BALLISTA_BOLT_HIT_GROUND = InjectionTools.nullValue();
//	public static final SoundEvent BALLISTA_LAUNCH = InjectionTools.nullValue();
//	public static final SoundEvent BALLISTA_RELOAD = InjectionTools.nullValue();
//	public static final SoundEvent BATTERING_RAM_HIT_WOOD = InjectionTools.nullValue();
//	public static final SoundEvent BATTERING_RAM_HIT_IRON = InjectionTools.nullValue();
//	public static final SoundEvent BATTERING_RAM_HIT_STONE = InjectionTools.nullValue();
//	public static final SoundEvent BATTERING_RAM_LAUNCH = InjectionTools.nullValue();
//	public static final SoundEvent CATAPULT_LAUNCH = InjectionTools.nullValue();
//	public static final SoundEvent CATAPULT_RELOAD = InjectionTools.nullValue();
//	public static final SoundEvent GIANT_TREBUCHET_LAUNCH = InjectionTools.nullValue();
//	public static final SoundEvent TREBUCHET_LAUNCH = InjectionTools.nullValue();
//	public static final SoundEvent TREBUCHET_RELOAD = InjectionTools.nullValue();
//	public static final SoundEvent VEHICLE_MOVING = InjectionTools.nullValue();

	private AWVehicleSounds() {
	}

//	@SubscribeEvent
//	public static void register(RegistryEvent.Register<SoundEvent> event) {
//		IForgeRegistry<SoundEvent> registry = event.getRegistry();
//		registry.register(createSoundEvent("ballista_bolt_hit_entity"));
//		registry.register(createSoundEvent("ballista_bolt_hit_ground"));
//		registry.register(createSoundEvent("ballista_launch"));
//		registry.register(createSoundEvent("ballista_reload"));
//		registry.register(createSoundEvent("battering_ram_launch"));
//		registry.register(createSoundEvent("battering_ram_hit_wood"));
//		registry.register(createSoundEvent("battering_ram_hit_iron"));
//		registry.register(createSoundEvent("battering_ram_hit_stone"));
//		registry.register(createSoundEvent("catapult_launch"));
//		registry.register(createSoundEvent("catapult_reload"));
//		registry.register(createSoundEvent("giant_trebuchet_launch"));
//		registry.register(createSoundEvent("trebuchet_launch"));
//		registry.register(createSoundEvent("trebuchet_reload"));
//		registry.register(createSoundEvent("vehicle_moving"));
//	}

//	private static SoundEvent createSoundEvent(String soundName) {
//		ResourceLocation registryName = new ResourceLocation(AncientWarfareVehicles.MOD_ID, soundName);
//		return new SoundEvent(registryName).setRegistryName(registryName);
//	}

}
