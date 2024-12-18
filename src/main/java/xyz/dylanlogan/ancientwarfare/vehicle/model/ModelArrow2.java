package xyz.dylanlogan.ancientwarfare.vehicle.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;

@SideOnly(Side.CLIENT)
public class ModelArrow2 extends ModelBase {

	ModelRenderer headPoint;
	ModelRenderer shaftCoreTop;
	ModelRenderer shaftCoreBottom;
	ModelRenderer shaftCoreLeft;
	ModelRenderer shaftCoreRight;
	ModelRenderer shaftCoreRightT1;
	ModelRenderer shaftCoreRightB1;
	ModelRenderer shaftCoreLeftB1;
	ModelRenderer shaftCoreLeftT1;
	ModelRenderer shaftCoreTopR;
	ModelRenderer shaftCoreTopL;
	ModelRenderer shaftCoreBottomL;
	ModelRenderer shaftCoreBottomR;
	ModelRenderer veinTop;
	ModelRenderer veinBottom;
	ModelRenderer veinRight;
	ModelRenderer veinLeft;
	ModelRenderer tipRight;
	ModelRenderer tipLeft;
	ModelRenderer tipTop;
	ModelRenderer tipBottom;
	ModelRenderer tipVert;
	ModelRenderer tipHoriz;
	ModelRenderer tipShaft;

	public ModelArrow2() {
		headPoint = new ModelRenderer(this, "headPoint");
		headPoint.setTextureOffset(0, 7);
		headPoint.setTextureSize(192, 36);
		headPoint.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(headPoint, 0.0f, 0.0f, 0.0f);
		headPoint.addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1);
		shaftCoreTop = new ModelRenderer(this, "shaftCoreTop");
		shaftCoreTop.setTextureOffset(0, 9);
		shaftCoreTop.setTextureSize(192, 36);
		shaftCoreTop.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreTop, 0.0f, 0.0f, 0.0f);
		shaftCoreTop.addBox(0.5f, -2.5f, -1.0f, 80, 1, 2);
		headPoint.addChild(shaftCoreTop);
		shaftCoreBottom = new ModelRenderer(this, "shaftCoreBottom");
		shaftCoreBottom.setTextureOffset(0, 9);
		shaftCoreBottom.setTextureSize(192, 36);
		shaftCoreBottom.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreBottom, 0.0f, 0.0f, 0.0f);
		shaftCoreBottom.addBox(0.5f, 1.5f, -1.0f, 80, 1, 2);
		headPoint.addChild(shaftCoreBottom);
		shaftCoreLeft = new ModelRenderer(this, "shaftCoreLeft");
		shaftCoreLeft.setTextureOffset(0, 13);
		shaftCoreLeft.setTextureSize(192, 36);
		shaftCoreLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreLeft, 0.0f, 0.0f, 0.0f);
		shaftCoreLeft.addBox(0.5f, -1.0f, -2.5f, 80, 2, 1);
		headPoint.addChild(shaftCoreLeft);
		shaftCoreRight = new ModelRenderer(this, "shaftCoreRight");
		shaftCoreRight.setTextureOffset(0, 13);
		shaftCoreRight.setTextureSize(192, 36);
		shaftCoreRight.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreRight, 0.0f, 0.0f, 0.0f);
		shaftCoreRight.addBox(0.5f, -1.0f, 1.5f, 80, 2, 1);
		headPoint.addChild(shaftCoreRight);
		shaftCoreRightT1 = new ModelRenderer(this, "shaftCoreRightT1");
		shaftCoreRightT1.setTextureOffset(0, 13);
		shaftCoreRightT1.setTextureSize(192, 36);
		shaftCoreRightT1.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreRightT1, 0.0f, 0.0f, 0.0f);
		shaftCoreRightT1.addBox(0.5f, -1.5f, 1.0f, 80, 1, 1);
		headPoint.addChild(shaftCoreRightT1);
		shaftCoreRightB1 = new ModelRenderer(this, "shaftCoreRightB1");
		shaftCoreRightB1.setTextureOffset(0, 13);
		shaftCoreRightB1.setTextureSize(192, 36);
		shaftCoreRightB1.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreRightB1, 0.0f, 0.0f, 0.0f);
		shaftCoreRightB1.addBox(0.5f, 0.5f, 1.0f, 80, 1, 1);
		headPoint.addChild(shaftCoreRightB1);
		shaftCoreLeftB1 = new ModelRenderer(this, "shaftCoreLeftB1");
		shaftCoreLeftB1.setTextureOffset(0, 13);
		shaftCoreLeftB1.setTextureSize(192, 36);
		shaftCoreLeftB1.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreLeftB1, 0.0f, 0.0f, 0.0f);
		shaftCoreLeftB1.addBox(0.5f, 0.5f, -2.0f, 80, 1, 1);
		headPoint.addChild(shaftCoreLeftB1);
		shaftCoreLeftT1 = new ModelRenderer(this, "shaftCoreLeftT1");
		shaftCoreLeftT1.setTextureOffset(0, 13);
		shaftCoreLeftT1.setTextureSize(192, 36);
		shaftCoreLeftT1.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreLeftT1, 0.0f, 0.0f, 0.0f);
		shaftCoreLeftT1.addBox(0.5f, -1.5f, -2.0f, 80, 1, 1);
		headPoint.addChild(shaftCoreLeftT1);
		shaftCoreTopR = new ModelRenderer(this, "shaftCoreTopR");
		shaftCoreTopR.setTextureOffset(0, 13);
		shaftCoreTopR.setTextureSize(192, 36);
		shaftCoreTopR.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreTopR, 0.0f, 0.0f, 0.0f);
		shaftCoreTopR.addBox(0.5f, -2.0f, 0.5f, 80, 1, 1);
		headPoint.addChild(shaftCoreTopR);
		shaftCoreTopL = new ModelRenderer(this, "shaftCoreTopL");
		shaftCoreTopL.setTextureOffset(0, 13);
		shaftCoreTopL.setTextureSize(192, 36);
		shaftCoreTopL.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreTopL, 0.0f, 0.0f, 0.0f);
		shaftCoreTopL.addBox(0.5f, -2.0f, -1.5f, 80, 1, 1);
		headPoint.addChild(shaftCoreTopL);
		shaftCoreBottomL = new ModelRenderer(this, "shaftCoreBottomL");
		shaftCoreBottomL.setTextureOffset(0, 13);
		shaftCoreBottomL.setTextureSize(192, 36);
		shaftCoreBottomL.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreBottomL, 0.0f, 0.0f, 0.0f);
		shaftCoreBottomL.addBox(0.5f, 1.0f, -1.5f, 80, 1, 1);
		headPoint.addChild(shaftCoreBottomL);
		shaftCoreBottomR = new ModelRenderer(this, "shaftCoreBottomR");
		shaftCoreBottomR.setTextureOffset(0, 13);
		shaftCoreBottomR.setTextureSize(192, 36);
		shaftCoreBottomR.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(shaftCoreBottomR, 0.0f, 0.0f, 0.0f);
		shaftCoreBottomR.addBox(0.5f, 1.0f, 0.5f, 80, 1, 1);
		headPoint.addChild(shaftCoreBottomR);
		veinTop = new ModelRenderer(this, "veinTop");
		veinTop.setTextureOffset(0, 0);
		veinTop.setTextureSize(192, 36);
		veinTop.setRotationPoint(66.0f, -2.0f, 0.0f);
		setPieceRotation(veinTop, 1.0402973E-9f, 0.0f, -0.29670483f);
		veinTop.addBox(-0.5f, -0.5f, -0.5f, 15, 5, 1);
		headPoint.addChild(veinTop);
		veinBottom = new ModelRenderer(this, "veinBottom");
		veinBottom.setTextureOffset(0, 0);
		veinBottom.setTextureSize(192, 36);
		veinBottom.setRotationPoint(66.0f, 2.0f, 0.0f);
		setPieceRotation(veinBottom, 1.0402973E-9f, 0.0f, 0.29670596f);
		veinBottom.addBox(-0.5f, -4.5f, -0.5f, 15, 5, 1);
		headPoint.addChild(veinBottom);
		veinRight = new ModelRenderer(this, "veinRight");
		veinRight.setTextureOffset(33, 0);
		veinRight.setTextureSize(192, 36);
		veinRight.setRotationPoint(66.0f, 0.0f, 2.0f);
		setPieceRotation(veinRight, -3.2249215E-8f, -0.2967058f, 0.0f);
		veinRight.addBox(-0.5f, -0.5f, -4.5f, 15, 1, 5);
		headPoint.addChild(veinRight);
		veinLeft = new ModelRenderer(this, "veinLeft");
		veinLeft.setTextureOffset(33, 0);
		veinLeft.setTextureSize(192, 36);
		veinLeft.setRotationPoint(66.0f, 0.0f, -2.0f);
		setPieceRotation(veinLeft, -3.2249215E-8f, 0.29670596f, 0.0f);
		veinLeft.addBox(-0.5f, -0.5f, -0.5f, 15, 1, 5);
		headPoint.addChild(veinLeft);
		tipRight = new ModelRenderer(this, "tipRight");
		tipRight.setTextureOffset(0, 17);
		tipRight.setTextureSize(192, 36);
		tipRight.setRotationPoint(-7.0f, 0.0f, 2.0f);
		setPieceRotation(tipRight, -3.2249215E-8f, -0.26179862f, 0.0f);
		tipRight.addBox(-0.5f, -0.5f, -3.5f, 15, 1, 4);
		headPoint.addChild(tipRight);
		tipLeft = new ModelRenderer(this, "tipLeft");
		tipLeft.setTextureOffset(0, 17);
		tipLeft.setTextureSize(192, 36);
		tipLeft.setRotationPoint(-7.0f, 0.0f, -2.0f);
		setPieceRotation(tipLeft, -3.2249215E-8f, 0.2617993f, 0.0f);
		tipLeft.addBox(-0.5f, -0.5f, -0.5f, 15, 1, 4);
		headPoint.addChild(tipLeft);
		tipTop = new ModelRenderer(this, "tipTop");
		tipTop.setTextureOffset(39, 17);
		tipTop.setTextureSize(192, 36);
		tipTop.setRotationPoint(-7.0f, -2.0f, 0.0f);
		setPieceRotation(tipTop, 1.0402973E-9f, 0.0f, -0.261798f);
		tipTop.addBox(-0.5f, -0.5f, -0.5f, 15, 4, 1);
		headPoint.addChild(tipTop);
		tipBottom = new ModelRenderer(this, "tipBottom");
		tipBottom.setTextureOffset(39, 17);
		tipBottom.setTextureSize(192, 36);
		tipBottom.setRotationPoint(-7.0f, 2.0f, 0.0f);
		setPieceRotation(tipBottom, 1.0402973E-9f, 0.0f, 0.2617993f);
		tipBottom.addBox(-0.5f, -3.5f, -0.5f, 15, 4, 1);
		headPoint.addChild(tipBottom);
		tipVert = new ModelRenderer(this, "tipVert");
		tipVert.setTextureOffset(29, 23);
		tipVert.setTextureSize(192, 36);
		tipVert.setRotationPoint(-10.0f, 0.0f, 0.0f);
		setPieceRotation(tipVert, 0.0f, 0.0f, -0.7853976f);
		tipVert.addBox(-0.5f, -0.5f, -0.5f, 4, 4, 1);
		headPoint.addChild(tipVert);
		tipHoriz = new ModelRenderer(this, "tipHoriz");
		tipHoriz.setTextureOffset(40, 23);
		tipHoriz.setTextureSize(192, 36);
		tipHoriz.setRotationPoint(-10.0f, 0.0f, 0.0f);
		setPieceRotation(tipHoriz, 0.0f, 0.7853982f, 0.0f);
		tipHoriz.addBox(-0.5f, -0.5f, -0.5f, 4, 1, 4);
		headPoint.addChild(tipHoriz);
		tipShaft = new ModelRenderer(this, "tipShaft");
		tipShaft.setTextureOffset(0, 23);
		tipShaft.setTextureSize(192, 36);
		tipShaft.setRotationPoint(-7.0f, 0.0f, 0.0f);
		setPieceRotation(tipShaft, 0.7853982f, 0.0f, -8.530438E-8f);
		tipShaft.addBox(-0.5f, -2.0f, -2.0f, 10, 4, 4);
		headPoint.addChild(tipShaft);

		headPoint.rotateAngleY = Trig.toRadians(90.f);
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
		super.render(entity, f1, f2, f3, f4, f5, f6);
		setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
		headPoint.render(f6);
	}

	public void setPieceRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
