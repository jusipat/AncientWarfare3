package xyz.dylanlogan.ancientwarfare.structure.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;


public class ModelGateBridge extends ModelBase {

    ModelRenderer part1;
    ModelRenderer bridgePiece;
    ModelRenderer bridgeSide1;
    ModelRenderer bridgeSide4;
    ModelRenderer bridgeCorner1;
    ModelRenderer bridgeCorner2;
    ModelRenderer bridgeTop1;
    ModelRenderer bridgeTop2;
    ModelRenderer bridgeSide3;
    ModelRenderer bridgeSide2;
    ModelRenderer bridgeCorner4;
    ModelRenderer bridgeCorner3;

    public ModelGateBridge() {
        part1 = new ModelRenderer(this, "part1");
        part1.setTextureOffset(0, 0);
        part1.setTextureSize(256, 256);
        part1.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(part1, 0.0f, 0.0f, 0.0f);
        part1.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        bridgePiece = new ModelRenderer(this, "bridgePiece");
        bridgePiece.setTextureOffset(0, 33);
        bridgePiece.setTextureSize(256, 256);
        bridgePiece.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgePiece, 0.0f, 0.0f, 0.0f);
        bridgePiece.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 8);
        bridgeSide1 = new ModelRenderer(this, "bridgeSide1");
        bridgeSide1.setTextureOffset(0, 58);
        bridgeSide1.setTextureSize(256, 256);
        bridgeSide1.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeSide1, 0.0f, 0.0f, 0.0f);
        bridgeSide1.addBox(-2.0f, -8.0f, 0.0f, 10, 16, 3);
        bridgeSide4 = new ModelRenderer(this, "bridgeSide4");
        bridgeSide4.setTextureOffset(27, 58);
        bridgeSide4.setTextureSize(256, 256);
        bridgeSide4.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeSide4, 0.0f, 0.0f, 0.0f);
        bridgeSide4.addBox(-8.0f, -8.0f, 3.0f, 6, 16, 3);
        bridgeCorner1 = new ModelRenderer(this, "bridgeCorner1");
        bridgeCorner1.setTextureOffset(49, 34);
        bridgeCorner1.setTextureSize(256, 256);
        bridgeCorner1.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeCorner1, 0.0f, 0.0f, 0.0f);
        bridgeCorner1.addBox(-8.0f, -8.0f, 0.0f, 6, 9, 3);
        bridgeCorner2 = new ModelRenderer(this, "bridgeCorner2");
        bridgeCorner2.setTextureOffset(49, 47);
        bridgeCorner2.setTextureSize(256, 256);
        bridgeCorner2.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeCorner2, 0.0f, 0.0f, 0.0f);
        bridgeCorner2.addBox(-8.0f, -8.0f, 3.0f, 10, 6, 3);
        bridgeTop1 = new ModelRenderer(this, "bridgeTop1");
        bridgeTop1.setTextureOffset(49, 58);
        bridgeTop1.setTextureSize(256, 256);
        bridgeTop1.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeTop1, 0.0f, 0.0f, 0.0f);
        bridgeTop1.addBox(-8.0f, -8.0f, 0.0f, 16, 9, 3);
        bridgeTop2 = new ModelRenderer(this, "bridgeTop2");
        bridgeTop2.setTextureOffset(49, 72);
        bridgeTop2.setTextureSize(256, 256);
        bridgeTop2.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeTop2, 0.0f, 0.0f, 0.0f);
        bridgeTop2.addBox(-8.0f, -8.0f, 3.0f, 16, 6, 3);
        bridgeSide3 = new ModelRenderer(this, "bridgeSide3");
        bridgeSide3.setTextureOffset(0, 58);
        bridgeSide3.setTextureSize(256, 256);
        bridgeSide3.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeSide3, 0.0f, 0.0f, 0.0f);
        bridgeSide3.addBox(-8.0f, -8.0f, 0.0f, 10, 16, 3);
        bridgeSide2 = new ModelRenderer(this, "bridgeSide2");
        bridgeSide2.setTextureOffset(27, 58);
        bridgeSide2.setTextureSize(256, 256);
        bridgeSide2.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeSide2, 0.0f, 0.0f, 0.0f);
        bridgeSide2.addBox(2.0f, -8.0f, 3.0f, 6, 16, 3);
        bridgeCorner4 = new ModelRenderer(this, "bridgeCorner4");
        bridgeCorner4.setTextureOffset(49, 47);
        bridgeCorner4.setTextureSize(256, 256);
        bridgeCorner4.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeCorner4, 0.0f, 0.0f, 0.0f);
        bridgeCorner4.addBox(-2.0f, -8.0f, 3.0f, 10, 6, 3);
        bridgeCorner3 = new ModelRenderer(this, "bridgeCorner3");
        bridgeCorner3.setTextureOffset(49, 34);
        bridgeCorner3.setTextureSize(256, 256);
        bridgeCorner3.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(bridgeCorner3, 0.0f, 0.0f, 0.0f);
        bridgeCorner3.addBox(2.0f, -8.0f, 0.0f, 6, 9, 3);
    }

    @Override
    public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
        super.render(entity, f1, f2, f3, f4, f5, f6);
        setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
        part1.render(f6);
        bridgePiece.render(f6);
        bridgeSide1.render(f6);
        bridgeSide4.render(f6);
        bridgeCorner1.render(f6);
        bridgeCorner2.render(f6);
        bridgeTop1.render(f6);
        bridgeTop2.render(f6);
        bridgeSide3.render(f6);
        bridgeSide2.render(f6);
        bridgeCorner4.render(f6);
        bridgeCorner3.render(f6);
    }

    public void setPieceRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderGateBlock() {
        this.part1.render(0.0625f);
    }

    public void renderTop() {
        bridgeTop1.render(0.0625f);
        bridgeTop2.render(0.0625f);
    }

    public void renderCorner() {
        bridgeCorner1.render(0.0625f);
        bridgeCorner2.render(0.0625f);
        bridgeSide1.render(0.0625f);
        bridgeSide2.render(0.0625f);
    }

    public void renderCorner2() {
        bridgeCorner3.render(0.0625f);
        bridgeCorner4.render(0.0625f);
        bridgeSide3.render(0.0625f);
        bridgeSide4.render(0.0625f);
    }

    public void renderSide1() {
        bridgeSide1.render(0.0625f);
        bridgeSide2.render(0.0625f);
    }

    public void renderSide2() {
        bridgeSide3.render(0.0625f);
        bridgeSide4.render(0.0625f);
    }

    public void renderSolidWall() {
        bridgePiece.render(0.0625f);
    }

    public void setModelRotation(float rot) {
        float val = Trig.toRadians(rot);
        part1.rotateAngleY = val;
        bridgePiece.rotateAngleY = val;
        bridgeTop1.rotateAngleY = val;
        bridgeTop2.rotateAngleY = val;
        bridgeCorner1.rotateAngleY = val;
        bridgeCorner2.rotateAngleY = val;
        bridgeCorner3.rotateAngleY = val;
        bridgeCorner4.rotateAngleY = val;
        bridgeSide1.rotateAngleY = val;
        bridgeSide2.rotateAngleY = val;
        bridgeSide3.rotateAngleY = val;
        bridgeSide4.rotateAngleY = val;
        this.modelRotation = val;
    }

    public float getModelRotation() {
        return modelRotation;
    }

    float modelRotation = 0;

}
