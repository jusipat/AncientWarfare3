package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;
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
		//ammoBallShot = registerAmmoType(new AmmoBallShot(), "ammo_ball_shot"); todo: unused?
		//ammoBallIronShot = registerAmmoType(new AmmoIronBallShot(), "ammo_iron_ball_shot");

		ammoStoneShot10 = registerWeightedAmmoType(new AmmoStoneShot(10), "ammo_stone");
		ammoStoneShot15 = registerWeightedAmmoType(new AmmoStoneShot(15),"ammo_stone");
		ammoStoneShot30 = registerWeightedAmmoType(new AmmoStoneShot(30), "ammo_stone");
		ammoStoneShot45 = registerWeightedAmmoType(new AmmoStoneShot(45), "ammo_stone");
		ammoFireShot10 = registerWeightedAmmoType(new AmmoFlameShot(10), "ammo_flame");
		ammoFireShot15 = registerWeightedAmmoType(new AmmoFlameShot(15), "ammo_flame");
		ammoFireShot30 = registerWeightedAmmoType(new AmmoFlameShot(30), "ammo_flame");
		ammoFireShot45 = registerWeightedAmmoType(new AmmoFlameShot(45), "ammo_flame");
		ammoExplosive10 = registerWeightedAmmoType(new AmmoExplosiveShot(10, false), "ammo_explosive");
		ammoExplosive15 = registerWeightedAmmoType(new AmmoExplosiveShot(15, false), "ammo_explosive");
		ammoExplosive30 = registerWeightedAmmoType(new AmmoExplosiveShot(30, false), "ammo_explosive");
		ammoExplosive45 = registerWeightedAmmoType(new AmmoExplosiveShot(45, false), "ammo_explosive");

		ammoHE10 = registerAmmoType(new AmmoExplosiveShot(10, true), "ammo_explosive_10_big"); // this sucks
		ammoHE15 = registerAmmoType(new AmmoExplosiveShot(15, true), "ammo_explosive_15_big");
		ammoHE30 = registerAmmoType(new AmmoExplosiveShot(30, true), "ammo_explosive_30_big");
		ammoHE45 = registerAmmoType(new AmmoExplosiveShot(45, true), "ammo_explosive_45_big");

		ammoNapalm10 = registerWeightedAmmoType(new AmmoNapalmShot(10), "ammo_napalm");
		ammoNapalm15 = registerWeightedAmmoType(new AmmoNapalmShot(15), "ammo_napalm");
		ammoNapalm30 = registerWeightedAmmoType(new AmmoNapalmShot(30), "ammo_napalm");
		ammoNapalm45 = registerWeightedAmmoType(new AmmoNapalmShot(45), "ammo_napalm");
		ammoClusterShot10 = registerWeightedAmmoType(new AmmoClusterShot(10), "ammo_cluster");
		ammoClusterShot15 = registerWeightedAmmoType(new AmmoClusterShot(15), "ammo_cluster");
		ammoClusterShot30 = registerWeightedAmmoType(new AmmoClusterShot(30), "ammo_cluster");
		ammoClusterShot45 = registerWeightedAmmoType(new AmmoClusterShot(45), "ammo_cluster");
		ammoPebbleShot10 = registerWeightedAmmoType(new AmmoPebbleShot(10), "ammo_pebble");
		ammoPebbleShot15 = registerWeightedAmmoType(new AmmoPebbleShot(15), "ammo_pebble");
		ammoPebbleShot30 = registerWeightedAmmoType(new AmmoPebbleShot(30), "ammo_pebble");
		ammoPebbleShot45 = registerWeightedAmmoType(new AmmoPebbleShot(45), "ammo_pebble");
		ammoIronShot5 = registerWeightedAmmoType(new AmmoIronShot(5, AWVehicleStatics.vehicleStats.ammoCannonBall5kgDamage), "ammo_iron");
		ammoIronShot10 = registerWeightedAmmoType(new AmmoIronShot(10, AWVehicleStatics.vehicleStats.ammoCannonBall10kgDamage), "ammo_iron");
		ammoIronShot15 = registerWeightedAmmoType(new AmmoIronShot(15, AWVehicleStatics.vehicleStats.ammoCannonBall15kgDamage), "ammo_iron");
		ammoIronShot25 = registerWeightedAmmoType(new AmmoIronShot(25, AWVehicleStatics.vehicleStats.ammoCannonBall25kgDamage), "ammo_iron");

		ammoCanisterShot5 = registerWeightedAmmoType(new AmmoCanisterShot(5), "ammo_canister");
		ammoCanisterShot10 = registerWeightedAmmoType(new AmmoCanisterShot(10), "ammo_canister");
		ammoCanisterShot15 = registerWeightedAmmoType(new AmmoCanisterShot(15), "ammo_canister");
		ammoCanisterShot25 = registerWeightedAmmoType(new AmmoCanisterShot(25), "ammo_canister");

		ammoGrapeShot5 = registerWeightedAmmoType(new AmmoGrapeShot(5), "ammo_grape");
		ammoGrapeShot10 = registerWeightedAmmoType(new AmmoGrapeShot(10), "ammo_grape");
		ammoGrapeShot15 = registerWeightedAmmoType(new AmmoGrapeShot(15), "ammo_grape");
		ammoGrapeShot25 = registerWeightedAmmoType(new AmmoGrapeShot(25), "ammo_grape");
		ammoArrow = registerAmmoType(new AmmoArrow(), "ammo_arrow");
		ammoBallistaBolt = registerAmmoType(new AmmoBallistaBolt(), "ammo_bolt");
		ammoBallistaBoltFlame = registerAmmoType(new AmmoBallistaBoltFlame(), "ammo_bolt_flame");
		ammoBallistaBoltExplosive = registerAmmoType(new AmmoBallistaBoltExplosive(), "ammo_bolt_explosive");
		ammoBallistaBoltIron = registerAmmoType(new AmmoBallistaBoltIron(), "ammo_bolt_iron");
		ammoRocket = registerAmmoType(new AmmoHwachaRocket(), "ammo_rocket");
		ammoHwachaRocketFlame = registerAmmoType(new AmmoHwachaRocketFlame(), "ammo_rocket_flame");
		ammoHwachaRocketExplosive = registerAmmoType(new AmmoHwachaRocketExplosive(), "ammo_rocket_explosive");
		ammoHwachaRocketAirburst = registerAmmoType(new AmmoHwachaRocketAirburst(), "ammo_rocket_airburst");
	}

	private static IAmmo registerAmmoType(IAmmo ammo, String name) {
		ammoInstances.put(ammo.getRegistryName(), ammo);
		ResourceLocation res = new ResourceLocation(AncientWarfareVehicles.MOD_ID, name);
		ItemAmmo item = new ItemAmmo(res);
		String loc = "ancientwarfare:vehicle/ammo/" + name;
		item.setTextureName(loc);
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);
		ammoItemInstances.put(ammo.getRegistryName(), item);
		return ammo;
	}

	private static IAmmo registerWeightedAmmoType(IAmmo ammo, String baseName) {
		String weightSuffix = String.valueOf((int) ammo.getAmmoWeight());
		String weightName = baseName + "_" + weightSuffix;

		ResourceLocation res = new ResourceLocation(AncientWarfareVehicles.MOD_ID, weightName);

		ammoInstances.put(ammo.getRegistryName(), ammo);

		// Create and configure the item for this ammo type
		ItemAmmo item = new ItemAmmo(res);
		String texturePath = "ancientwarfare:vehicle/ammo/" + weightName;
		item.setTextureName(texturePath);
		item.setUnlocalizedName(weightName);
		GameRegistry.registerItem(item, weightName);

		// Map the ammo's ResourceLocation to the item
		ammoItemInstances.put(ammo.getRegistryName(), item);

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
