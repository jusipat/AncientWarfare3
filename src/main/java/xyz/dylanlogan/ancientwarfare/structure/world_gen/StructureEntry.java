package xyz.dylanlogan.ancientwarfare.structure.world_gen;

import net.minecraft.nbt.NBTTagCompound;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplate;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBB;

public class StructureEntry {

    public String name;
    private int value;
    public final StructureBB bb;

    public StructureEntry(int x, int y, int z, int face, StructureTemplate template) {
        name = template.name;
        bb = new StructureBB(x, y, z, face, template.xSize, template.ySize, template.zSize, template.xOffset, template.yOffset, template.zOffset);
        value = template.getValidationSettings().getClusterValue();
    }

    public StructureEntry(StructureBB bb, String name, int value) {
        this.name = name;
        this.bb = bb;
        this.value = value;
    }

    public StructureEntry() {
        bb = new StructureBB(new BlockPosition(), new BlockPosition());
    }//NBT constructor

    public void writeToNBT(NBTTagCompound tag) {
        tag.setString("name", name);
        tag.setInteger("value", value);
        tag.setIntArray("bb", new int[]{bb.min.x, bb.min.y, bb.min.z, bb.max.x, bb.max.y, bb.max.z});
    }

    public void readFromNBT(NBTTagCompound tag) {
        name = tag.getString("name");
        value = tag.getInteger("value");
        int[] datas = tag.getIntArray("bb");
        if (datas.length >= 6) {
            bb.min = new BlockPosition(datas[0], datas[1], datas[2]);
            bb.max = new BlockPosition(datas[3], datas[4], datas[5]);
        }
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public StructureBB getBB() {
        return bb;
    }

}
