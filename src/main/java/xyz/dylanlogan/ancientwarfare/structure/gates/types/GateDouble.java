package xyz.dylanlogan.ancientwarfare.structure.gates.types;

import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.structure.entity.EntityGate;

public class GateDouble extends Gate{
    /**
     * @param id
     * @param textureLocation
     */
    public GateDouble(int id, String textureLocation) {
        super(id, textureLocation);
    }

    @Override
    public void setInitialBounds(EntityGate gate, BlockPosition pos1, BlockPosition pos2) {
        BlockPosition min = BlockTools.getMin(pos1, pos2);
        BlockPosition max = BlockTools.getMax(pos1, pos2);
        boolean wideOnXAxis = min.x != max.x;
        float width = wideOnXAxis ? max.x - min.x + 1 : max.z - min.z + 1;
        float xOffset = wideOnXAxis ? width * 0.5f : 0.5f;
        float zOffset = wideOnXAxis ? 0.5f : width * 0.5f;
        gate.pos1 = pos1;
        gate.pos2 = pos2;
        gate.edgeMax = width * 0.5f;
        gate.setPosition(min.x + xOffset, min.y, min.z + zOffset);
    }
}
