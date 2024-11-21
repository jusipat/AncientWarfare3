package xyz.dylanlogan.ancientwarfare.core.input;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import xyz.dylanlogan.ancientwarfare.core.config.AWCoreStatics;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.network.PacketItemMouseScroll;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@SideOnly(Side.CLIENT)
public class InputHandler {

	private static final String CATEGORY = "keybind.category.awCore";
	public static final KeyBinding ALT_ITEM_USE_1 = new KeyBinding(AWCoreStatics.KEY_ALT_ITEM_USE_1, Keyboard.KEY_Z, CATEGORY);
	public static final KeyBinding ALT_ITEM_USE_2 = new KeyBinding(AWCoreStatics.KEY_ALT_ITEM_USE_2, Keyboard.KEY_X, CATEGORY);
	public static final KeyBinding ALT_ITEM_USE_3 = new KeyBinding(AWCoreStatics.KEY_ALT_ITEM_USE_3, Keyboard.KEY_C, CATEGORY);
	public static final KeyBinding ALT_ITEM_USE_4 = new KeyBinding(AWCoreStatics.KEY_ALT_ITEM_USE_4, Keyboard.KEY_V, CATEGORY);
	public static final KeyBinding ALT_ITEM_USE_5 = new KeyBinding(AWCoreStatics.KEY_ALT_ITEM_USE_5, Keyboard.KEY_B, CATEGORY);

	private static final Set<InputCallbackDispatcher> keybindingCallbacks = new HashSet<>();

	static {
		MinecraftForge.EVENT_BUS.register(new InputHandler());
	}

	private InputHandler() {
	}

	public static void initKeyBindings() {
		ClientRegistry.registerKeyBinding(ALT_ITEM_USE_1);
		ClientRegistry.registerKeyBinding(ALT_ITEM_USE_2);
		ClientRegistry.registerKeyBinding(ALT_ITEM_USE_3);
		ClientRegistry.registerKeyBinding(ALT_ITEM_USE_4);
		ClientRegistry.registerKeyBinding(ALT_ITEM_USE_5);

		initCallbacks();
	}

	private static void initCallbacks() {
		registerCallBack(ALT_ITEM_USE_1, new ItemInputCallback(ItemAltFunction.ALT_FUNCTION_1));
		registerCallBack(ALT_ITEM_USE_2, new ItemInputCallback(ItemAltFunction.ALT_FUNCTION_2));
		registerCallBack(ALT_ITEM_USE_3, new ItemInputCallback(ItemAltFunction.ALT_FUNCTION_3));
		registerCallBack(ALT_ITEM_USE_4, new ItemInputCallback(ItemAltFunction.ALT_FUNCTION_4));
		registerCallBack(ALT_ITEM_USE_5, new ItemInputCallback(ItemAltFunction.ALT_FUNCTION_5));
	}

	public static void registerCallBack(KeyBinding keyBinding, IInputCallback callback) {
		Predicate<InputCallbackDispatcher> matchingKeyBinding = d -> d.getKeyBinding().equals(keyBinding);
		if (keybindingCallbacks.stream().anyMatch(matchingKeyBinding)) {
			keybindingCallbacks.stream().filter(matchingKeyBinding).findFirst().ifPresent(d -> d.addInputCallback(callback));
		} else {
			keybindingCallbacks.add(new InputCallbackDispatcher(keyBinding, callback));
		}
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent evt) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer player = minecraft.thePlayer;
		if (player == null) {
			return;
		}

		boolean state = Keyboard.isKeyDown(Keyboard.getEventKey());

		if (state) {
			keybindingCallbacks.stream().filter(k -> k.getKeyBinding().isPressed()).forEach(InputCallbackDispatcher::onKeyPressed);
		}
	}

	@SubscribeEvent
	public void onMouseEvent(MouseEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if (player.isSneaking()) {
			int delta = event.dwheel;
			if (delta != 0) {
				ItemStack stack = player.getHeldItem();

				if (stack != null && stack.getItem() instanceof IScrollableItem) {
					Item item = stack.getItem();

					if (delta > 0) {
						// Scroll up
						if (((IScrollableItem) item).onScrollUp(player.worldObj, player, stack)) {
							NetworkHandler.sendToServer(new PacketItemMouseScroll(true));
						}
					} else {
						// Scroll down
						if (((IScrollableItem) item).onScrollDown(player.worldObj, player, stack)) {
							NetworkHandler.sendToServer(new PacketItemMouseScroll(false));
						}
					}
					event.setCanceled(true);
				}
			}
		}
	}
}
