package xyz.dylanlogan.ancientwarfare.vehicle.input;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Tuple;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.joml.Vector3d;
import xyz.dylanlogan.ancientwarfare.core.input.InputHandler;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketVehicleInput;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class VehicleInputHandler {
	private static final String CATEGORY = "keybind.category.awVehicles";
	private static final KeyBinding FORWARD = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_FORWARD,  Keyboard.KEY_W,
			CATEGORY);
	private static final KeyBinding REVERSE = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_REVERSE,  Keyboard.KEY_S,
			CATEGORY);
	private static final KeyBinding LEFT = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_LEFT,  Keyboard.KEY_A, CATEGORY);
	private static final KeyBinding RIGHT = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_RIGHT,  Keyboard.KEY_D, CATEGORY);
	private static final KeyBinding ASCEND_AIM_UP = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_ASCEND_AIM_UP, 
			Keyboard.KEY_R, CATEGORY);
	private static final KeyBinding DESCEND_AIM_DOWN = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_DESCEND_AIM_DOWN, 
			Keyboard.KEY_F, CATEGORY);
	private static final KeyBinding FIRE = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_FIRE,  Keyboard.KEY_SPACE, CATEGORY);
	private static final KeyBinding AMMO_PREV = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_AMMO_PREV,  Keyboard.KEY_T,
			CATEGORY);
	private static final KeyBinding AMMO_NEXT = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_AMMO_NEXT,  Keyboard.KEY_G,
			CATEGORY);
	private static final KeyBinding TURRET_LEFT = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_TURRET_LEFT,  Keyboard.KEY_Z,
			CATEGORY);
	private static final KeyBinding TURRET_RIGHT = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_TURRET_RIGHT,  Keyboard.KEY_X,
			CATEGORY);
	private static final KeyBinding MOUSE_AIM = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_MOUSE_AIM,  Keyboard.KEY_C,
			CATEGORY);
	private static final KeyBinding AMMO_SELECT = new KeyBinding(AWVehicleStatics.KEY_VEHICLE_AMMO_SELECT,  Keyboard.KEY_V,
			CATEGORY);

	private static final Set<Integer> releaseableKeys = new HashSet<>();
	private static boolean trackedKeyReleased = false;
	private static final Set<IVehicleMovementHandler> vehicleMovementHandlers = new HashSet<>();

	static {
		MinecraftForge.EVENT_BUS.register(new VehicleInputHandler());
	}

	private VehicleInputHandler() {
	}

	public static void initKeyBindings() {
		ClientRegistry.registerKeyBinding(FORWARD);
		ClientRegistry.registerKeyBinding(REVERSE);
		ClientRegistry.registerKeyBinding(LEFT);
		ClientRegistry.registerKeyBinding(RIGHT);
		ClientRegistry.registerKeyBinding(ASCEND_AIM_UP);
		ClientRegistry.registerKeyBinding(DESCEND_AIM_DOWN);
		ClientRegistry.registerKeyBinding(FIRE);
		ClientRegistry.registerKeyBinding(AMMO_PREV);
		ClientRegistry.registerKeyBinding(AMMO_NEXT);
		ClientRegistry.registerKeyBinding(TURRET_LEFT);
		ClientRegistry.registerKeyBinding(TURRET_RIGHT);
		ClientRegistry.registerKeyBinding(MOUSE_AIM);
		ClientRegistry.registerKeyBinding(AMMO_SELECT);

		//initCallbacks();
		initReleaseableKeys();
	}

	private static void initReleaseableKeys() {
		vehicleMovementHandlers.add(new IVehicleMovementHandler.Impl(FORWARD, REVERSE, p -> p.setForwardInput((byte) 1)));
		vehicleMovementHandlers.add(new IVehicleMovementHandler.Impl(REVERSE, FORWARD, p -> p.setForwardInput((byte) -1)));
		vehicleMovementHandlers.add(new IVehicleMovementHandler.Impl(RIGHT, LEFT, p -> p.setTurnInput((byte) 1)));
		vehicleMovementHandlers.add(new IVehicleMovementHandler.Impl(LEFT, RIGHT, p -> p.setTurnInput((byte) -1)));
		vehicleMovementHandlers.add(new IVehicleMovementHandler.Impl(ASCEND_AIM_UP, DESCEND_AIM_DOWN, p -> p.setPowerInput((byte) 1)));
		vehicleMovementHandlers.add(new IVehicleMovementHandler.Impl(DESCEND_AIM_DOWN, ASCEND_AIM_UP, p -> p.setPowerInput((byte) -1)));
		vehicleMovementHandlers.add(new IVehicleMovementHandler.Impl(TURRET_RIGHT, TURRET_LEFT, p -> p.setRotationInput((byte) 1)));
		vehicleMovementHandlers.add(new IVehicleMovementHandler.Impl(TURRET_LEFT, TURRET_RIGHT, p -> p.setRotationInput((byte) -1)));

		releaseableKeys.add(FORWARD.getKeyCode());
		releaseableKeys.add(REVERSE.getKeyCode());
		releaseableKeys.add(LEFT.getKeyCode());
		releaseableKeys.add(RIGHT.getKeyCode());
		releaseableKeys.add(ASCEND_AIM_UP.getKeyCode());
		releaseableKeys.add(DESCEND_AIM_DOWN.getKeyCode());
		releaseableKeys.add(TURRET_LEFT.getKeyCode());
		releaseableKeys.add(TURRET_RIGHT.getKeyCode());
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent evt) {
		if (!Keyboard.getEventKeyState()) {
			trackReleasedKeys();
		}
	}

	private static void trackReleasedKeys() {
		int key = Keyboard.getEventKey();
		if (releaseableKeys.contains(key)) {
			trackedKeyReleased = true;
		}
	}

//	private static void initCallbacks() {
//		InputHandler.registerCallBack(MOUSE_AIM,
//				() -> AWVehicleStatics.clientSettings.enableMouseAim = !AWVehicleStatics.clientSettings.enableMouseAim); //TODO add code to update config once mouseAim is made into config setting
//		InputHandler.registerCallBack(FIRE, new VehicleCallback(VehicleInputHandler::handleFireAction));
//		InputHandler.registerCallBack(ASCEND_AIM_UP, new VehicleCallback(v -> v.firingHelper.handleAimKeyInput(-1, 0)));
//		InputHandler.registerCallBack(DESCEND_AIM_DOWN, new VehicleCallback(v -> v.firingHelper.handleAimKeyInput(1, 0)));
//		InputHandler.registerCallBack(TURRET_LEFT, new VehicleCallback(v -> v.firingHelper.handleAimKeyInput(0, -1)));
//		InputHandler.registerCallBack(TURRET_RIGHT, new VehicleCallback(v -> v.firingHelper.handleAimKeyInput(0, 1)));
//		InputHandler.registerCallBack(AMMO_NEXT, new VehicleCallback(v -> v.ammoHelper.setNextAmmo()));
//		InputHandler.registerCallBack(AMMO_PREV, new VehicleCallback(v -> v.ammoHelper.setPreviousAmmo()));
//		InputHandler.registerCallBack(AMMO_SELECT, new VehicleCallback(VehicleInputHandler::handleAmmoSelectAction));
//	}

	private static void handleAmmoSelectAction(VehicleBase vehicle) {
		if (!vehicle.isAmmoLoaded()) {
			//Minecraft.getMinecraft().thePlayer.sendStatusMessage(new TextComponentTranslation("gui.ancientwarfarevehicles.ammo.no_ammo"), true);
			return;
		}

		if (!vehicle.vehicleType.getValidAmmoTypes().isEmpty()) {
			//NetworkHandler.INSTANCE.openGui(Minecraft.getMinecraft().thePlayer, NetworkHandler.GUI_VEHICLE_AMMO_SELECTION, vehicle.getEntityId());
		}
	}

	private static void handleFireAction(VehicleBase vehicle) {
		String configName = vehicle.vehicleType.getConfigName();
		if (!vehicle.isAmmoLoaded() && !(configName.equals("battering_ram") || configName.equals("boat_transport") || configName.equals("chest_cart"))) {
			//Minecraft.getMinecraft().thePlayer.sendStatusMessage(new TextComponentTranslation("gui.ancientwarfarevehicles.ammo.no_ammo"), true);
		}
		if (vehicle.isAimable()) {
			vehicle.firingHelper.handleFireInput();
		}
	}

	private static final float MAX_RANGE = 140;

	private static MovingObjectPosition getPlayerLookTargetClient(EntityPlayer player, Entity excludedEntity) {
		Vec3 playerEyesPos = player.getLookVec();
		Vec3 lookVector = player.getLook(0);
		Vec3 endVector = playerEyesPos.addVector(lookVector.xCoord * MAX_RANGE, lookVector.yCoord * MAX_RANGE, lookVector.zCoord * MAX_RANGE);

		// Ray trace to check for block collisions
		MovingObjectPosition blockHit = player.worldObj.rayTraceBlocks(playerEyesPos, endVector);

		// Get closest collided entity
		Tuple closestEntityFound = getClosestCollidedEntity(excludedEntity, playerEyesPos, lookVector, endVector);

		// If an entity is closer than the block hit, use the entity hit
		if (closestEntityFound != null && (blockHit == null || (Double) closestEntityFound.getFirst() < blockHit.hitVec.distanceTo(playerEyesPos))) {
			Entity hitEntity = (Entity) closestEntityFound.getSecond();
			blockHit = new MovingObjectPosition(hitEntity,  Vec3.createVectorHelper(hitEntity.posX, hitEntity.posY + hitEntity.height * 0.65d, hitEntity.posZ));
		}

		return blockHit;
	}



	private static Tuple getClosestCollidedEntity(Entity excludedEntity, Vec3 playerEyesPos, Vec3 lookVector, Vec3 endVector) {
		Minecraft mc = Minecraft.getMinecraft();

		// Get all possible hit entities within the bounding box expanded by lookVector
		List<Entity> possibleHitEntities = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity,
				mc.renderViewEntity.boundingBox.expand(lookVector.xCoord * MAX_RANGE, lookVector.yCoord * MAX_RANGE, lookVector.zCoord * MAX_RANGE)
						.expand(1, 1, 1));

		double closestDistance = Double.MAX_VALUE;
		Entity closestEntity = null;

		// Iterate through entities and find the closest one
		for (Entity entity : possibleHitEntities) {
			if (entity != excludedEntity && entity.canBeCollidedWith()) {
				double distance = getDistanceToCollidedEntity(entity, playerEyesPos, endVector);
				if (distance < closestDistance) {
					closestDistance = distance;
					closestEntity = entity;
				}
			}
		}

		// If a closest entity was found, return it in a Tuple, otherwise return null
		if (closestEntity != null) {
			return new Tuple(closestDistance, closestEntity);
		} else {
			return null; // No entity found
		}
	}


	private static double getDistanceToCollidedEntity(Entity entity, Vec3 startVector, Vec3 endVector) {
		float borderSize = entity.getCollisionBorderSize();
		AxisAlignedBB entBB = entity.boundingBox.expand(borderSize, borderSize, borderSize);
		MovingObjectPosition MovingObjectPosition = entBB.calculateIntercept(startVector, endVector);

		return MovingObjectPosition != null ? startVector.distanceTo(MovingObjectPosition.hitVec) : Double.MAX_VALUE;
	}

	@SubscribeEvent
	public void onTickEnd(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) {
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();
		if (mc.thePlayer != null && mc.thePlayer.ridingEntity instanceof VehicleBase) {
			VehicleBase vehicle = (VehicleBase) mc.thePlayer.ridingEntity;
			handleTickInput(vehicle);
			if (AWVehicleStatics.clientSettings.enableMouseAim) {
				handleMouseAimUpdate(vehicle);
			}
		}
	}

	private void handleMouseAimUpdate(VehicleBase vehicle) {
		if (vehicle.ticksExisted % 5 == 0) {
			return;
		}
		Minecraft mc = Minecraft.getMinecraft();
		MovingObjectPosition pos = getPlayerLookTargetClient(mc.thePlayer, vehicle);
		if (pos != null) {
			vehicle.firingHelper.handleAimInput(pos.hitVec);
		}
	}

	private static void handleTickInput(VehicleBase vehicle) {
		if (trackedKeyReleased || vehicle.ticksExisted % 20 == 0) {
			trackedKeyReleased = false;

			PacketVehicleInput pkt = new PacketVehicleInput(vehicle);

			vehicleMovementHandlers.stream().filter(h -> h.getKeyBinding().getIsKeyPressed() && !h.getReverseKeyBinding().getIsKeyPressed())
					.forEach(h -> h.updatePacket(pkt));

			NetworkHandler.sendToServer(pkt);
		}
	}

}
