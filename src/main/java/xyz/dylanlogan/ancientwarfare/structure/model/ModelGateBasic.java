package xyz.dylanlogan.ancientwarfare.structure.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;


public class ModelGateBasic extends ModelBase {

    ModelRenderer part1;
    ModelRenderer top1;
    ModelRenderer top4;
    ModelRenderer top3;
    ModelRenderer top2;
    ModelRenderer barsTop;
    ModelRenderer barsLeft1;
    ModelRenderer barsLeft3;
    ModelRenderer barsLeft5;
    ModelRenderer barsRight1;
    ModelRenderer barsRight3;
    ModelRenderer barsRight5;
    ModelRenderer barsBottom;
    ModelRenderer cornerTop1;
    ModelRenderer cornerTop2;
    ModelRenderer cornerSide2;
    ModelRenderer cornerSide1;
    ModelRenderer cornerTop3;
    ModelRenderer cornerTop4;
    ModelRenderer cornerSide3;
    ModelRenderer cornerSide4;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer side3;
    ModelRenderer side4;

    public ModelGateBasic() {
        part1 = new ModelRenderer(this, "part1");
        part1.setTextureOffset(0, 0);
        part1.setTextureSize(256, 256);
        part1.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(part1, 0.0f, 0.0f, 0.0f);
        part1.addBox(-1.0f, -16.0f, -8.0f, 2, 16, 16);
        top1 = new ModelRenderer(this, "top1");
        top1.setTextureOffset(41, 26);
        top1.setTextureSize(256, 256);
        top1.setRotationPoint(0.0f, -16.0f, 0.0f);
        setPieceRotation(top1, 0.0f, 0.0f, 0.0f);
        top1.addBox(4.0f, 0.0f, -8.0f, 2, 6, 16);
        top4 = new ModelRenderer(this, "top4");
        top4.setTextureOffset(39, 0);
        top4.setTextureSize(256, 256);
        top4.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(top4, 0.0f, 0.0f, 0.0f);
        top4.addBox(1.0f, 0.0f, -8.0f, 3, 8, 16);
        top1.addChild(top4);
        top3 = new ModelRenderer(this, "top3");
        top3.setTextureOffset(39, 0);
        top3.setTextureSize(256, 256);
        top3.setRotationPoint(-4.0f, 0.0f, 0.0f);
        setPieceRotation(top3, 0.0f, 0.0f, 0.0f);
        top3.addBox(0.0f, 0.0f, -8.0f, 3, 8, 16);
        top1.addChild(top3);
        top2 = new ModelRenderer(this, "top2");
        top2.setTextureOffset(41, 26);
        top2.setTextureSize(256, 256);
        top2.setRotationPoint(-6.0f, 0.0f, 0.0f);
        setPieceRotation(top2, 0.0f, 0.0f, 0.0f);
        top2.addBox(0.0f, 0.0f, -8.0f, 2, 6, 16);
        top1.addChild(top2);
        barsTop = new ModelRenderer(this, "barsTop");
        barsTop.setTextureOffset(0, 75);
        barsTop.setTextureSize(256, 256);
        barsTop.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(barsTop, 0.0f, 0.0f, 0.0f);
        barsTop.addBox(-1.0f, -13.0f, -8.0f, 2, 2, 16);
        barsLeft1 = new ModelRenderer(this, "barsLeft1");
        barsLeft1.setTextureOffset(0, 52);
        barsLeft1.setTextureSize(256, 256);
        barsLeft1.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(barsLeft1, 0.0f, 0.0f, 0.0f);
        barsLeft1.addBox(-1.0f, -16.0f, -5.0f, 2, 3, 2);
        barsTop.addChild(barsLeft1);
        barsLeft3 = new ModelRenderer(this, "barsLeft3");
        barsLeft3.setTextureOffset(0, 58);
        barsLeft3.setTextureSize(256, 256);
        barsLeft3.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(barsLeft3, 0.0f, 0.0f, 0.0f);
        barsLeft3.addBox(-1.0f, -11.0f, -5.0f, 2, 6, 2);
        barsTop.addChild(barsLeft3);
        barsLeft5 = new ModelRenderer(this, "barsLeft5");
        barsLeft5.setTextureOffset(0, 67);
        barsLeft5.setTextureSize(256, 256);
        barsLeft5.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(barsLeft5, 0.0f, 0.0f, 0.0f);
        barsLeft5.addBox(-1.0f, -3.0f, -5.0f, 2, 3, 2);
        barsTop.addChild(barsLeft5);
        barsRight1 = new ModelRenderer(this, "barsRight1");
        barsRight1.setTextureOffset(27, 52);
        barsRight1.setTextureSize(256, 256);
        barsRight1.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(barsRight1, 0.0f, 0.0f, 0.0f);
        barsRight1.addBox(-1.0f, -16.0f, 3.0f, 2, 3, 2);
        barsTop.addChild(barsRight1);
        barsRight3 = new ModelRenderer(this, "barsRight3");
        barsRight3.setTextureOffset(27, 58);
        barsRight3.setTextureSize(256, 256);
        barsRight3.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(barsRight3, 0.0f, 0.0f, 0.0f);
        barsRight3.addBox(-1.0f, -11.0f, 3.0f, 2, 6, 2);
        barsTop.addChild(barsRight3);
        barsRight5 = new ModelRenderer(this, "barsRight5");
        barsRight5.setTextureOffset(27, 67);
        barsRight5.setTextureSize(256, 256);
        barsRight5.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(barsRight5, 0.0f, 0.0f, 0.0f);
        barsRight5.addBox(-1.0f, -3.0f, 3.0f, 2, 3, 2);
        barsTop.addChild(barsRight5);
        barsBottom = new ModelRenderer(this, "barsBottom");
        barsBottom.setTextureOffset(0, 33);
        barsBottom.setTextureSize(256, 256);
        barsBottom.setRotationPoint(0.0f, 0.0f, 0.0f);
        setPieceRotation(barsBottom, 0.0f, 0.0f, 0.0f);
        barsBottom.addBox(-1.0f, -5.0f, -8.0f, 2, 2, 16);
        barsTop.addChild(barsBottom);
        cornerTop1 = new ModelRenderer(this, "cornerTop1");
        cornerTop1.setTextureOffset(39, 49);
        cornerTop1.setTextureSize(256, 256);
        cornerTop1.setRotationPoint(0.0f, -16.0f, 0.0f);
        setPieceRotation(cornerTop1, 0.0f, 2.1014006E-7f, 0.0f);
        cornerTop1.addBox(1.0f, 0.0f, 0.0f, 3, 8, 8);
        cornerTop2 = new ModelRenderer(this, "cornerTop2");
        cornerTop2.setTextureOffset(39, 66);
        cornerTop2.setTextureSize(256, 256);
        cornerTop2.setRotationPoint(4.0f, 0.0f, -2.0f);
        setPieceRotation(cornerTop2, 0.0f, 0.0f, 0.0f);
        cornerTop2.addBox(0.0f, 0.0f, 0.0f, 2, 6, 10);
        cornerTop1.addChild(cornerTop2);
        cornerSide2 = new ModelRenderer(this, "cornerSide2");
        cornerSide2.setTextureOffset(62, 83);
        cornerSide2.setTextureSize(256, 256);
        cornerSide2.setRotationPoint(4.0f, 0.0f, -8.0f);
        setPieceRotation(cornerSide2, 0.0f, 0.0f, 0.0f);
        cornerSide2.addBox(0.0f, 0.0f, 0.0f, 2, 16, 6);
        cornerTop1.addChild(cornerSide2);
        cornerSide1 = new ModelRenderer(this, "cornerSide1");
        cornerSide1.setTextureOffset(39, 83);
        cornerSide1.setTextureSize(256, 256);
        cornerSide1.setRotationPoint(1.0f, 0.0f, -8.0f);
        setPieceRotation(cornerSide1, 0.0f, 0.0f, 0.0f);
        cornerSide1.addBox(0.0f, 0.0f, 0.0f, 3, 16, 8);
        cornerTop1.addChild(cornerSide1);
        cornerTop3 = new ModelRenderer(this, "cornerTop3");
        cornerTop3.setTextureOffset(62, 49);
        cornerTop3.setTextureSize(256, 256);
        cornerTop3.setRotationPoint(-4.0f, 0.0f, 0.0f);
        setPieceRotation(cornerTop3, 0.0f, 0.0f, 0.0f);
        cornerTop3.addBox(0.0f, 0.0f, 0.0f, 3, 8, 8);
        cornerTop1.addChild(cornerTop3);
        cornerTop4 = new ModelRenderer(this, "cornerTop4");
        cornerTop4.setTextureOffset(64, 66);
        cornerTop4.setTextureSize(256, 256);
        cornerTop4.setRotationPoint(-6.0f, 0.0f, -2.0f);
        setPieceRotation(cornerTop4, 0.0f, 0.0f, 0.0f);
        cornerTop4.addBox(0.0f, 0.0f, 0.0f, 2, 6, 10);
        cornerTop1.addChild(cornerTop4);
        cornerSide3 = new ModelRenderer(this, "cornerSide3");
        cornerSide3.setTextureOffset(39, 108);
        cornerSide3.setTextureSize(256, 256);
        cornerSide3.setRotationPoint(-4.0f, 0.0f, -8.0f);
        setPieceRotation(cornerSide3, 0.0f, 0.0f, 0.0f);
        cornerSide3.addBox(0.0f, 0.0f, 0.0f, 3, 16, 8);
        cornerTop1.addChild(cornerSide3);
        cornerSide4 = new ModelRenderer(this, "cornerSide4");
        cornerSide4.setTextureOffset(62, 108);
        cornerSide4.setTextureSize(256, 256);
        cornerSide4.setRotationPoint(-6.0f, 0.0f, -8.0f);
        setPieceRotation(cornerSide4, 0.0f, 0.0f, 0.0f);
        cornerSide4.addBox(0.0f, 0.0f, 0.0f, 2, 16, 6);
        cornerTop1.addChild(cornerSide4);
        side1 = new ModelRenderer(this, "side1");
        side1.setTextureOffset(39, 83);
        side1.setTextureSize(256, 256);
        side1.setRotationPoint(0.0f, -16.0f, 0.0f);
        setPieceRotation(side1, 0.0f, 0.0f, 0.0f);
        side1.addBox(1.0f, 0.0f, -8.0f, 3, 16, 8);
        side2 = new ModelRenderer(this, "side2");
        side2.setTextureOffset(62, 83);
        side2.setTextureSize(256, 256);
        side2.setRotationPoint(4.0f, 0.0f, 0.0f);
        setPieceRotation(side2, 0.0f, 0.0f, 0.0f);
        side2.addBox(0.0f, 0.0f, -8.0f, 2, 16, 6);
        side1.addChild(side2);
        side3 = new ModelRenderer(this, "side3");
        side3.setTextureOffset(39, 108);
        side3.setTextureSize(256, 256);
        side3.setRotationPoint(-4.0f, 0.0f, 0.0f);
        setPieceRotation(side3, 0.0f, 0.0f, 0.0f);
        side3.addBox(0.0f, 0.0f, -8.0f, 3, 16, 8);
        side1.addChild(side3);
        side4 = new ModelRenderer(this, "side4");
        side4.setTextureOffset(62, 108);
        side4.setTextureSize(256, 256);
        side4.setRotationPoint(-6.0f, 0.0f, 0.0f);
        setPieceRotation(side4, 0.0f, 0.0f, 0.0f);
        side4.addBox(0.0f, 0.0f, -8.0f, 2, 16, 6);
        side1.addChild(side4);
    }

    @Override
    public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
        super.render(entity, f1, f2, f3, f4, f5, f6);
        setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
        part1.render(f6);
        top1.render(f6);
        barsTop.render(f6);
        cornerTop1.render(f6);
        side1.render(f6);
    }

    public void setPieceRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderTop() {
        top1.render(0.0625f);
    }

    public void renderCorner() {
        cornerTop1.render(0.0625f);
    }

    public void renderSide() {
        side1.render(0.0625f);
    }

    public void renderSolidWall() {
        part1.render(0.0625f);
    }

    public void renderBars() {
        barsTop.render(0.0625f);
    }

    public void setModelRotation(float rot) {
        float val = Trig.toRadians(rot);
        top1.rotateAngleY = val;
        cornerTop1.rotateAngleY = val;
        side1.rotateAngleY = val;
        part1.rotateAngleY = val;
        barsTop.rotateAngleY = val;
        this.modelRotation = val;
    }

    public float getModelRotation() {
        return modelRotation;
    }

    float modelRotation = 0;

}
