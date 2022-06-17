package xyz.dylanlogan.ancientwarfare.structure.gates;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.structure.entity.EntityGate;

public interface IGateType {

    /**
     * return global ID -- used to link gate type to item
     * determines render type and model used
     */
    public int getGlobalID();

    /**
     * return the name to register for the spawning item
     */
    public String getDisplayName();

    /**
     * return the tooltip to register for the spawning item
     */
    public String getTooltip();

    /**
     * return the texture that should be used for rendering
     */
    public ResourceLocation getTexture();

    public IIcon getIconTexture();

    public void registerIcons(IIconRegister reg);

    /**
     * return the speed at which the gate opens/closes when activated
     */
    public float getMoveSpeed();

    /**
     * return the max health of this gate
     */
    public int getMaxHealth();

    /**
     * a callback from the entity to the gate-type to allow for
     * gate-type specific checks during updates
     */
    public void onUpdate(EntityGate ent);

    /**
     * called from setPosition to update gates bounding box
     */
    public void setCollisionBoundingBox(EntityGate gate);

    public void onGateStartOpen(EntityGate gate);

    public void onGateFinishOpen(EntityGate gate);

    public void onGateStartClose(EntityGate gate);

    public void onGateFinishClose(EntityGate gate);

    public void setInitialBounds(EntityGate gate, BlockPosition pos1, BlockPosition pos2);

    /**
     * a callback from the spawning item for validation of a chosen
     * pair of spawning points.  This is where the gate can reject
     * a starting position/setup if the points are not placed correctly.
     */
    public boolean arePointsValidPair(BlockPosition pos1, BlockPosition pos2);

    public boolean canActivate(EntityGate gate, boolean open);

    public boolean canSoldierActivate();

    public int getModelType();

    public ItemStack getConstructingItem();

    public ItemStack getDisplayStack();

}
