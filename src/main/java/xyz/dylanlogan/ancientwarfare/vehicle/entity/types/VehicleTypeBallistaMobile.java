package xyz.dylanlogan.ancientwarfare.vehicle.entity.types;

import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;

public class VehicleTypeBallistaMobile extends VehicleTypeBallista {
	public VehicleTypeBallistaMobile(int typeNum) {
		super(typeNum);
		this.configName = "ballista_mobile";
		this.baseMissileVelocityMax = 42.f;//stand versions should have higher velocity, as should fixed version--i.e. mobile turret should have the worst of all versions
		this.width = 2.7f;
		this.height = 1.4f;

		this.armorBaySize = 3;
		this.upgradeBaySize = 3;

		this.baseStrafeSpeed = 1.7f;
		this.baseForwardSpeed = 4.2f * 0.05f;
		this.turretForwardsOffset = 1.f;
		this.turretVerticalOffset = 1.2f;
		this.riderForwardsOffset = -1.2f;
		this.riderVerticalOffset = 0.55f;
		this.riderSits = true;

		this.drivable = true;//adjust based on isMobile or not
		this.yawAdjustable = false;//adjust based on hasTurret or not
		this.turretRotationMax = 0.f;//adjust based on mobile/stand fixed (0), stand fixed(90'), or mobile or stand turret (360)
		this.displayName = "item.vehicleSpawner.6";
		this.displayTooltip.add("item.vehicleSpawner.tooltip.torsion");
		this.displayTooltip.add("item.vehicleSpawner.tooltip.mobile");
		this.displayTooltip.add("item.vehicleSpawner.tooltip.noturret");

		this.validUpgrades.add(UpgradeRegistry.speedUpgrade);
	}

	@Override
	public ResourceLocation getTextureForMaterialLevel(int level) {
		switch (level) {
			case 0:
				return new ResourceLocation(AncientWarfareCore.MOD_ID, "textures/model/vehicle/ballista_mobile_1.png");
			case 1:
				return new ResourceLocation(AncientWarfareCore.MOD_ID, "textures/model/vehicle/ballista_mobile_2.png");
			case 2:
				return new ResourceLocation(AncientWarfareCore.MOD_ID, "textures/model/vehicle/ballista_mobile_3.png");
			case 3:
				return new ResourceLocation(AncientWarfareCore.MOD_ID, "textures/model/vehicle/ballista_mobile_4.png");
			case 4:
				return new ResourceLocation(AncientWarfareCore.MOD_ID, "textures/model/vehicle/ballista_mobile_5.png");
			default:
				return new ResourceLocation(AncientWarfareCore.MOD_ID, "textures/model/vehicle/ballista_mobile_1.png");
		}
	}

}
