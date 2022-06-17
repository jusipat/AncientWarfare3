package xyz.dylanlogan.ancientwarfare.structure.render.gate;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.structure.entity.EntityGate;
import xyz.dylanlogan.ancientwarfare.structure.model.ModelGateBasic;
import org.lwjgl.opengl.GL11;

public final class RenderGateDouble extends RenderGateBasic {

    public RenderGateDouble() {

    }

    @Override
    protected BlockPosition getMin(EntityGate gate) {
        return BlockTools.getMin(gate.pos1, gate.pos2);
    }

    @Override
    protected BlockPosition getMax(EntityGate gate) {
        return BlockTools.getMax(gate.pos1, gate.pos2);
    }

    @Override
    protected void postRender(EntityGate gate, int x, float width, int y, float height, boolean wideOnXAxis, float axisRotation, float frame) {
        float move;
        boolean render = false;
        if (x < width * 0.5f) {
            move = -gate.edgePosition - gate.openingSpeed * (1 - frame);
            if (x + move > -0.5f) {
                render = true;
            }
        } else {
            move = gate.edgePosition + gate.openingSpeed * (1 - frame);
            if (x + move <= width - 0.475f) {
                render = true;
            }
        }
        float wallTx = wideOnXAxis ? move : 0;
        float wallTz = wideOnXAxis ? 0 : move;
        if (render) {
            GL11.glPushMatrix();
            GL11.glTranslatef(wallTx, 0, wallTz);
            model.setModelRotation(axisRotation);
            if (gate.getGateType().getModelType() == 0) {
                model.renderSolidWall();
            } else {
                model.renderBars();
            }
            GL11.glPopMatrix();
        }
    }
}
