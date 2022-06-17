package xyz.dylanlogan.ancientwarfare.structure.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;

public class ItemStructureSettings {

    boolean[] setKeys = new boolean[4];
    BlockPosition pos1;
    BlockPosition pos2;
    BlockPosition key;
    int buildFace;
    String name;

    private ItemStructureSettings() {

    }

    /**
     * @param stack to extract the info from
     */
    public static ItemStructureSettings getSettingsFor(ItemStack stack) {
        ItemStructureSettings settings = new ItemStructureSettings();
        NBTTagCompound tag;
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("structData")) {
            tag = stack.getTagCompound().getCompoundTag("structData");
        } else {
            tag = new NBTTagCompound();
        }
        for (int i = 0; i < settings.setKeys.length; i++) {
            settings.setKeys[i] = false;
        }
        if (tag.hasKey("pos1")) {
            settings.pos1 = new BlockPosition(tag.getCompoundTag("pos1"));
            settings.setKeys[0] = true;
        }
        if (tag.hasKey("pos2")) {
            settings.pos2 = new BlockPosition(tag.getCompoundTag("pos2"));
            settings.setKeys[1] = true;
        }
        if (tag.hasKey("buildKey")) {
            settings.key = new BlockPosition(tag.getCompoundTag("buildKey"));
            settings.setKeys[2] = true;
            settings.buildFace = tag.getCompoundTag("buildKey").getInteger("face");
        }
        if (tag.hasKey("name")) {
            settings.name = tag.getString("name");
            settings.setKeys[3] = true;
        }
        return settings;
    }

    public static void setSettingsFor(ItemStack item, ItemStructureSettings settings) {
        NBTTagCompound tag = new NBTTagCompound();
        if (settings.setKeys[0]) {
            tag.setTag("pos1", settings.pos1.writeToNBT(new NBTTagCompound()));
        }
        if (settings.setKeys[1]) {
            tag.setTag("pos2", settings.pos2.writeToNBT(new NBTTagCompound()));
        }
        if (settings.setKeys[2]) {
            NBTTagCompound tag1 = new NBTTagCompound();
            tag1.setInteger("face", settings.buildFace);
            tag.setTag("buildKey", settings.key.writeToNBT(tag1));
        }
        if (settings.setKeys[3]) {
            tag.setString("name", settings.name);
        }
        item.setTagInfo("structData", tag);
    }

    public void setPos1(int x, int y, int z) {
        pos1 = new BlockPosition(x, y, z);
        setKeys[0] = true;
    }

    public void setPos2(int x, int y, int z) {
        pos2 = new BlockPosition(x, y, z);
        setKeys[1] = true;
    }

    public void setBuildKey(int x, int y, int z, int face) {
        key = new BlockPosition(x, y, z);
        buildFace = face;
        setKeys[2] = true;
    }

    public void setName(String name) {
        this.name = name;
        setKeys[3] = true;
    }

    public boolean hasPos1() {
        return setKeys[0];
    }

    public boolean hasPos2() {
        return setKeys[1];
    }

    public boolean hasBuildKey() {
        return setKeys[2];
    }

    public boolean hasName() {
        return setKeys[3];
    }

    public BlockPosition pos1() {
        return pos1;
    }

    public BlockPosition pos2() {
        return pos2;
    }

    public BlockPosition buildKey() {
        return key;
    }

    public int face() {
        return buildFace;
    }

    public String name() {
        return name;
    }

    public void clearSettings() {
        for (int i = 0; i < 3; i++) {
            this.setKeys[i] = false;
        }
    }

}
