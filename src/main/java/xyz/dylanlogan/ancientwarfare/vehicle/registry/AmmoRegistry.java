package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemAmmo;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AmmoRegistry {

	/**
	 * procedure to make new ammo type:
	 * create ammo class
	 * create static instance below (or anywhere really)
	 * register the render in renderRegistry (or register it with renderregistry during startup)
	 * add ammo to applicable vehicle type constructors
	 */

	public static IAmmo ammoBallShot;
	public static IAmmo ammoBallIronShot;
	public static IAmmo ammoStoneShot10;
	public static IAmmo ammoStoneShot15;
	public static IAmmo ammoStoneShot30;
	public static IAmmo ammoStoneShot45;
	public static IAmmo ammoFireShot10;
	public static IAmmo ammoFireShot15;
	public static IAmmo ammoFireShot30;
	public static IAmmo ammoFireShot45;
	public static IAmmo ammoExplosive10;
	public static IAmmo ammoExplosive15;
	public static IAmmo ammoExplosive30;
	public static IAmmo ammoExplosive45;
	public static IAmmo ammoHE10;
	public static IAmmo ammoHE15;
	public static IAmmo ammoHE30;
	public static IAmmo ammoHE45;
	public static IAmmo ammoNapalm10;
	public static IAmmo ammoNapalm15;
	public static IAmmo ammoNapalm30;
	public static IAmmo ammoNapalm45;
	public static IAmmo ammoClusterShot10;
	public static IAmmo ammoClusterShot15;
	public static IAmmo ammoClusterShot30;
	public static IAmmo ammoClusterShot45;
	public static IAmmo ammoPebbleShot10;
	public static IAmmo ammoPebbleShot15;
	public static IAmmo ammoPebbleShot30;
	public static IAmmo ammoPebbleShot45;
	public static IAmmo ammoIronShot5;
	public static IAmmo ammoIronShot10;
	public static IAmmo ammoIronShot15;
	public static IAmmo ammoIronShot25;
	public static IAmmo ammoCanisterShot5;
	public static IAmmo ammoCanisterShot10;
	public static IAmmo ammoCanisterShot15;
	public static IAmmo ammoCanisterShot25;
	public static IAmmo ammoGrapeShot5;
	public static IAmmo ammoGrapeShot10;
	public static IAmmo ammoGrapeShot15;
	public static IAmmo ammoGrapeShot25;
	public static IAmmo ammoArrow;
	public static IAmmo ammoBallistaBolt;
	public static IAmmo ammoBallistaBoltFlame;
	public static IAmmo ammoBallistaBoltExplosive;
	public static IAmmo ammoBallistaBoltIron;
	public static IAmmo ammoRocket;
	public static IAmmo ammoHwachaRocketFlame;
	public static IAmmo ammoHwachaRocketExplosive;
	public static IAmmo ammoHwachaRocketAirburst;

	private AmmoRegistry() {
	}

	private static Map<ResourceLocation, IAmmo> ammoInstances = new HashMap<>();
	private static Map<ResourceLocation, ItemAmmo> ammoItemInstances = new HashMap<>();

	public static void registerAmmo() {
		// Register each ammo type with a unique registry name
		ammoBallShot = registerAmmoType(new AmmoBallShot(), "ancientwarfare:ammo_ball_shot");
		ammoBallIronShot = registerAmmoType(new AmmoIronBallShot(), "ancientwarfare:ammo_ball_iron_shot");
		ammoStoneShot10 = registerAmmoType(new AmmoStoneShot(10), "ancientwarfare:ammo_stone_shot_10");
		ammoStoneShot15 = registerAmmoType(new AmmoStoneShot(15), "ancientwarfare:ammo_stone_shot_15");
		ammoStoneShot30 = registerAmmoType(new AmmoStoneShot(30), "ancientwarfare:ammo_stone_shot_30");
		ammoStoneShot45 = registerAmmoType(new AmmoStoneShot(45), "ancientwarfare:ammo_stone_shot_45");
		ammoFireShot10 = registerAmmoType(new AmmoFlameShot(10), "ancientwarfare:ammo_fire_shot_10");
		ammoFireShot15 = registerAmmoType(new AmmoFlameShot(15), "ancientwarfare:ammo_fire_shot_15");
		ammoFireShot30 = registerAmmoType(new AmmoFlameShot(30), "ancientwarfare:ammo_fire_shot_30");
		ammoFireShot45 = registerAmmoType(new AmmoFlameShot(45), "ancientwarfare:ammo_fire_shot_45");
		ammoExplosive10 = registerAmmoType(new AmmoExplosiveShot(10, false), "ancientwarfare:ammo_explosive_10");
		ammoExplosive15 = registerAmmoType(new AmmoExplosiveShot(15, false), "ancientwarfare:ammo_explosive_15");
		ammoExplosive30 = registerAmmoType(new AmmoExplosiveShot(30, false), "ancientwarfare:ammo_explosive_30");
		ammoExplosive45 = registerAmmoType(new AmmoExplosiveShot(45, false), "ancientwarfare:ammo_explosive_45");
		ammoHE10 = registerAmmoType(new AmmoExplosiveShot(10, true), "ancientwarfare:ammo_he_10");
		ammoHE15 = registerAmmoType(new AmmoExplosiveShot(15, true), "ancientwarfare:ammo_he_15");
		ammoHE30 = registerAmmoType(new AmmoExplosiveShot(30, true), "ancientwarfare:ammo_he_30");
		ammoHE45 = registerAmmoType(new AmmoExplosiveShot(45, true), "ancientwarfare:ammo_he_45");
		ammoNapalm10 = registerAmmoType(new AmmoNapalmShot(10), "ancientwarfare:ammo_napalm_10");
		ammoNapalm15 = registerAmmoType(new AmmoNapalmShot(15), "ancientwarfare:ammo_napalm_15");
		ammoNapalm30 = registerAmmoType(new AmmoNapalmShot(30), "ancientwarfare:ammo_napalm_30");
		ammoNapalm45 = registerAmmoType(new AmmoNapalmShot(45), "ancientwarfare:ammo_napalm_45");
		ammoClusterShot10 = registerAmmoType(new AmmoClusterShot(10), "ancientwarfare:ammo_cluster_shot_10");
		ammoClusterShot15 = registerAmmoType(new AmmoClusterShot(15), "ancientwarfare:ammo_cluster_shot_15");
		ammoClusterShot30 = registerAmmoType(new AmmoClusterShot(30), "ancientwarfare:ammo_cluster_shot_30");
		ammoClusterShot45 = registerAmmoType(new AmmoClusterShot(45), "ancientwarfare:ammo_cluster_shot_45");
		ammoPebbleShot10 = registerAmmoType(new AmmoPebbleShot(10), "ancientwarfare:ammo_pebble_shot_10");
		ammoPebbleShot15 = registerAmmoType(new AmmoPebbleShot(15), "ancientwarfare:ammo_pebble_shot_15");
		ammoPebbleShot30 = registerAmmoType(new AmmoPebbleShot(30), "ancientwarfare:ammo_pebble_shot_30");
		ammoPebbleShot45 = registerAmmoType(new AmmoPebbleShot(45), "ancientwarfare:ammo_pebble_shot_45");
		ammoIronShot5 = registerAmmoType(new AmmoIronShot(5, AWVehicleStatics.vehicleStats.ammoCannonBall5kgDamage), "ancientwarfare:ammo_iron_shot_5");
		ammoIronShot10 = registerAmmoType(new AmmoIronShot(10, AWVehicleStatics.vehicleStats.ammoCannonBall10kgDamage), "ancientwarfare:ammo_iron_shot_10");
		ammoIronShot15 = registerAmmoType(new AmmoIronShot(15, AWVehicleStatics.vehicleStats.ammoCannonBall15kgDamage), "ancientwarfare:ammo_iron_shot_15");
		ammoIronShot25 = registerAmmoType(new AmmoIronShot(25, AWVehicleStatics.vehicleStats.ammoCannonBall25kgDamage), "ancientwarfare:ammo_iron_shot_25");
		ammoCanisterShot5 = registerAmmoType(new AmmoCanisterShot(5), "ancientwarfare:ammo_canister_shot_5");
		ammoCanisterShot10 = registerAmmoType(new AmmoCanisterShot(10), "ancientwarfare:ammo_canister_shot_10");
		ammoCanisterShot15 = registerAmmoType(new AmmoCanisterShot(15), "ancientwarfare:ammo_canister_shot_15");
		ammoCanisterShot25 = registerAmmoType(new AmmoCanisterShot(25), "ancientwarfare:ammo_canister_shot_25");
		ammoGrapeShot5 = registerAmmoType(new AmmoGrapeShot(5), "ancientwarfare:ammo_grape_shot_5");
		ammoGrapeShot10 = registerAmmoType(new AmmoGrapeShot(10), "ancientwarfare:ammo_grape_shot_10");
		ammoGrapeShot15 = registerAmmoType(new AmmoGrapeShot(15), "ancientwarfare:ammo_grape_shot_15");
		ammoGrapeShot25 = registerAmmoType(new AmmoGrapeShot(25), "ancientwarfare:ammo_grape_shot_25");
		ammoArrow = registerAmmoType(new AmmoArrow(), "ancientwarfare:ammo_arrow");
		ammoBallistaBolt = registerAmmoType(new AmmoBallistaBolt(), "ancientwarfare:ammo_ballista_bolt");
		ammoBallistaBoltFlame = registerAmmoType(new AmmoBallistaBoltFlame(), "ancientwarfare:ammo_ballista_bolt_flame");
		ammoBallistaBoltExplosive = registerAmmoType(new AmmoBallistaBoltExplosive(), "ancientwarfare:ammo_ballista_bolt_explosive");
		ammoBallistaBoltIron = registerAmmoType(new AmmoBallistaBoltIron(), "ancientwarfare:ammo_ballista_bolt_iron");
		ammoRocket = registerAmmoType(new AmmoHwachaRocket(), "ancientwarfare:ammo_rocket");
		ammoHwachaRocketFlame = registerAmmoType(new AmmoHwachaRocketFlame(), "ancientwarfare:ammo_hwacha_rocket_flame");
		ammoHwachaRocketExplosive = registerAmmoType(new AmmoHwachaRocketExplosive(), "ancientwarfare:ammo_hwacha_rocket_explosive");
		ammoHwachaRocketAirburst = registerAmmoType(new AmmoHwachaRocketAirburst(), "ancientwarfare:ammo_hwacha_rocket_airburst");
	}


	private static IAmmo registerAmmoType(IAmmo ammo, String registryName) {
		ResourceLocation resourceLocation = new ResourceLocation(registryName);
		ammoInstances.put(resourceLocation, ammo);
		ItemAmmo item = new ItemAmmo(resourceLocation, ammo);
		ammoItemInstances.put(resourceLocation, item);
		GameRegistry.registerItem(item, resourceLocation.getResourcePath());
		return ammo;
	}

	public static Optional<IAmmo> getAmmoForStack(ItemStack stack) {
		return Optional.ofNullable(ammoInstances.get(stack.getItem().getUnlocalizedName(stack)));
	}

	public static IAmmo getAmmo(ResourceLocation registryName) {
		return ammoInstances.get(registryName);
	}

	public static ItemAmmo getItemForAmmo(IAmmo ammo) {
		return ammoItemInstances.get(ammo.getRegistryName());
	}

	public static ItemAmmo getItem(ResourceLocation ammoRegistryName) {
		return ammoItemInstances.get(ammoRegistryName);
	}
}
