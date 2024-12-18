package xyz.dylanlogan.ancientwarfare.vehicle.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;

@SideOnly(Side.CLIENT)
public class ModelBatteringRam extends ModelBase {

	ModelRenderer frontCrossBeam;
	ModelRenderer rightBeam;
	ModelRenderer pivotCrossBeam;
	ModelRenderer rearCrossBeam;
	ModelRenderer rearAxle;
	ModelRenderer RRWheelPivot;
	ModelRenderer RRS4;
	ModelRenderer RRS2;
	ModelRenderer RRS3;
	ModelRenderer RRw1;
	ModelRenderer RRw2;
	ModelRenderer RRw3;
	ModelRenderer RRw8;
	ModelRenderer RRw7;
	ModelRenderer RRw6;
	ModelRenderer RRw4;
	ModelRenderer RRw5;
	ModelRenderer RLWheelPivot;
	ModelRenderer RLS2;
	ModelRenderer RLS3;
	ModelRenderer RLS4;
	ModelRenderer RLw1;
	ModelRenderer RLw2;
	ModelRenderer RLw3;
	ModelRenderer RLw8;
	ModelRenderer RLw7;
	ModelRenderer RLw6;
	ModelRenderer RLw5;
	ModelRenderer RLw4;
	ModelRenderer leftBeam;
	ModelRenderer frontAxle;
	ModelRenderer FRWheelPivot;
	ModelRenderer FRS2;
	ModelRenderer FRS3;
	ModelRenderer FRS4;
	ModelRenderer frw1;
	ModelRenderer Frw4;
	ModelRenderer FLr5;
	ModelRenderer Frw6;
	ModelRenderer Frw7;
	ModelRenderer Frw8;
	ModelRenderer Frw3;
	ModelRenderer Frw2;
	ModelRenderer FLWheelPivot;
	ModelRenderer FLw3;
	ModelRenderer FLw2;
	ModelRenderer FS4;
	ModelRenderer FLS2;
	ModelRenderer FS3;
	ModelRenderer flw1;
	ModelRenderer FLw4;
	ModelRenderer FLw5;
	ModelRenderer FLw6;
	ModelRenderer FLw7;
	ModelRenderer FLw8;
	ModelRenderer chairBrace;
	ModelRenderer chairBottom;
	ModelRenderer chairBack;
	ModelRenderer ramUprightFR;
	ModelRenderer ramUprightFL;
	ModelRenderer ramUprightRR;
	ModelRenderer ramUprightRL;
	ModelRenderer ramTopRight;
	ModelRenderer ramTopLeft;
	ModelRenderer ramTopFront;
	ModelRenderer ramTopRear;
	ModelRenderer ramLeftAngle;
	ModelRenderer ramLeftAngled;
	ModelRenderer ramTopMidFront;
	ModelRenderer ramRopeFR;
	ModelRenderer ramLog;
	ModelRenderer ropeFrontBottom;
	ModelRenderer ramLogRopeRearBottom;
	ModelRenderer ramLogCapFront;
	ModelRenderer ramLogCapTop;
	ModelRenderer ramLogCapRight;
	ModelRenderer ramLogCapBottom;
	ModelRenderer ramLogCapLeft;
	ModelRenderer logCapTip;
	ModelRenderer ramRopeFL;
	ModelRenderer ramTopMidRear;
	ModelRenderer ramRopeRL;
	ModelRenderer ramRopeRR;

	public ModelBatteringRam() {
		frontCrossBeam = new ModelRenderer(this, "frontCrossBeam");
		frontCrossBeam.setTextureOffset(0, 0);
		frontCrossBeam.setTextureSize(256, 256);
		frontCrossBeam.setRotationPoint(-12.0f, -11.0f, -16.5f);
		setPieceRotation(frontCrossBeam, 0.0f, 0.0f, 0.0f);
		frontCrossBeam.addBox(0.0f, 0.0f, 0.0f, 24, 3, 3);
		rightBeam = new ModelRenderer(this, "rightBeam");
		rightBeam.setTextureOffset(0, 7);
		rightBeam.setTextureSize(256, 256);
		rightBeam.setRotationPoint(-3.0f, 0.0f, 0.0f);
		setPieceRotation(rightBeam, 0.0f, 0.0f, 0.0f);
		rightBeam.addBox(0.0f, 0.0f, -5.0f, 3, 3, 48);
		frontCrossBeam.addChild(rightBeam);
		pivotCrossBeam = new ModelRenderer(this, "pivotCrossBeam");
		pivotCrossBeam.setTextureOffset(0, 0);
		pivotCrossBeam.setTextureSize(256, 256);
		pivotCrossBeam.setRotationPoint(0.0f, 0.0f, 15.0f);
		setPieceRotation(pivotCrossBeam, 0.0f, 0.0f, 0.0f);
		pivotCrossBeam.addBox(0.0f, 0.0f, 0.0f, 24, 3, 3);
		frontCrossBeam.addChild(pivotCrossBeam);
		rearCrossBeam = new ModelRenderer(this, "rearCrossBeam");
		rearCrossBeam.setTextureOffset(0, 0);
		rearCrossBeam.setTextureSize(256, 256);
		rearCrossBeam.setRotationPoint(0.0f, 0.0f, 35.0f);
		setPieceRotation(rearCrossBeam, 0.0f, 0.0f, 0.0f);
		rearCrossBeam.addBox(0.0f, 0.0f, 0.0f, 24, 3, 3);
		rearAxle = new ModelRenderer(this, "rearAxle");
		rearAxle.setTextureOffset(0, 59);
		rearAxle.setTextureSize(256, 256);
		rearAxle.setRotationPoint(12.0f, 3.0f, 1.5f);
		setPieceRotation(rearAxle, 1.3773537E-6f, 0.0f, 0.0f);
		rearAxle.addBox(-17.0f, -0.5f, -0.5f, 34, 1, 1);
		RRWheelPivot = new ModelRenderer(this, "RRWheelPivot");
		RRWheelPivot.setTextureOffset(0, 62);
		RRWheelPivot.setTextureSize(256, 256);
		RRWheelPivot.setRotationPoint(-17.0f, 0.0f, 0.0f);
		setPieceRotation(RRWheelPivot, 0.0f, 0.0f, 0.0f);
		RRWheelPivot.addBox(-0.5f, -7.0f, -0.5f, 1, 14, 1);
		RRS4 = new ModelRenderer(this, "RRS4");
		RRS4.setTextureOffset(23, 62);
		RRS4.setTextureSize(256, 256);
		RRS4.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(RRS4, 0.0f, 0.0f, 0.0f);
		RRS4.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		RRWheelPivot.addChild(RRS4);
		RRS2 = new ModelRenderer(this, "RRS2");
		RRS2.setTextureOffset(23, 62);
		RRS2.setTextureSize(256, 256);
		RRS2.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(RRS2, 0.7853982f, 0.0f, 0.0f);
		RRS2.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		RRWheelPivot.addChild(RRS2);
		RRS3 = new ModelRenderer(this, "RRS3");
		RRS3.setTextureOffset(23, 62);
		RRS3.setTextureSize(256, 256);
		RRS3.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(RRS3, -0.7853982f, 0.0f, 0.0f);
		RRS3.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		RRWheelPivot.addChild(RRS3);
		RRw1 = new ModelRenderer(this, "RRw1");
		RRw1.setTextureOffset(0, 84);
		RRw1.setTextureSize(256, 256);
		RRw1.setRotationPoint(-1.0f, -8.0f, -3.0f);
		setPieceRotation(RRw1, 0.0f, 0.0f, 0.0f);
		RRw1.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		RRWheelPivot.addChild(RRw1);
		RRw2 = new ModelRenderer(this, "RRw2");
		RRw2.setTextureOffset(0, 75);
		RRw2.setTextureSize(256, 256);
		RRw2.setRotationPoint(-1.0f, -8.0f, 3.0f);
		setPieceRotation(RRw2, -0.7853982f, 0.0f, 0.0f);
		RRw2.addBox(0.0f, 0.0f, 0.0f, 2, 1, 7);
		RRWheelPivot.addChild(RRw2);
		RRw3 = new ModelRenderer(this, "RRw3");
		RRw3.setTextureOffset(0, 84);
		RRw3.setTextureSize(256, 256);
		RRw3.setRotationPoint(-1.0f, -3.0f, 8.0f);
		setPieceRotation(RRw3, -1.570796f, 0.0f, 0.0f);
		RRw3.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		RRWheelPivot.addChild(RRw3);
		RRw8 = new ModelRenderer(this, "RRw8");
		RRw8.setTextureOffset(0, 75);
		RRw8.setTextureSize(256, 256);
		RRw8.setRotationPoint(-1.0f, 3.0f, 8.0f);
		setPieceRotation(RRw8, 0.7853982f, 0.0f, 0.0f);
		RRw8.addBox(0.0f, -1.0f, -7.0f, 2, 1, 7);
		RRWheelPivot.addChild(RRw8);
		RRw7 = new ModelRenderer(this, "RRw7");
		RRw7.setTextureOffset(0, 84);
		RRw7.setTextureSize(256, 256);
		RRw7.setRotationPoint(-1.0f, 7.0f, -3.0f);
		setPieceRotation(RRw7, 0.0f, 0.0f, 0.0f);
		RRw7.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		RRWheelPivot.addChild(RRw7);
		RRw6 = new ModelRenderer(this, "RRw6");
		RRw6.setTextureOffset(0, 75);
		RRw6.setTextureSize(256, 256);
		RRw6.setRotationPoint(-1.0f, 3.0f, -8.0f);
		setPieceRotation(RRw6, -0.7853982f, 0.0f, 0.0f);
		RRw6.addBox(0.0f, -1.0f, 0.0f, 2, 1, 7);
		RRWheelPivot.addChild(RRw6);
		RRw4 = new ModelRenderer(this, "RRw4");
		RRw4.setTextureOffset(0, 75);
		RRw4.setTextureSize(256, 256);
		RRw4.setRotationPoint(-1.0f, -8.0f, -3.0f);
		setPieceRotation(RRw4, 0.7853982f, 0.0f, 0.0f);
		RRw4.addBox(0.0f, 0.0f, -7.0f, 2, 1, 7);
		RRWheelPivot.addChild(RRw4);
		RRw5 = new ModelRenderer(this, "RRw5");
		RRw5.setTextureOffset(0, 84);
		RRw5.setTextureSize(256, 256);
		RRw5.setRotationPoint(-1.0f, -3.0f, -8.0f);
		setPieceRotation(RRw5, 1.570796f, 0.0f, 0.0f);
		RRw5.addBox(0.0f, 0.0f, -6.0f, 2, 1, 6);
		RRWheelPivot.addChild(RRw5);
		rearAxle.addChild(RRWheelPivot);
		RLWheelPivot = new ModelRenderer(this, "RLWheelPivot");
		RLWheelPivot.setTextureOffset(0, 62);
		RLWheelPivot.setTextureSize(256, 256);
		RLWheelPivot.setRotationPoint(17.0f, 0.0f, 0.0f);
		setPieceRotation(RLWheelPivot, 0.0f, 0.0f, 0.0f);
		RLWheelPivot.addBox(-0.5f, -7.0f, -0.5f, 1, 14, 1);
		RLS2 = new ModelRenderer(this, "RLS2");
		RLS2.setTextureOffset(23, 62);
		RLS2.setTextureSize(256, 256);
		RLS2.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(RLS2, 0.7853982f, 0.0f, 0.0f);
		RLS2.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		RLWheelPivot.addChild(RLS2);
		RLS3 = new ModelRenderer(this, "RLS3");
		RLS3.setTextureOffset(23, 62);
		RLS3.setTextureSize(256, 256);
		RLS3.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(RLS3, -0.7853982f, 0.0f, 0.0f);
		RLS3.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		RLWheelPivot.addChild(RLS3);
		RLS4 = new ModelRenderer(this, "RLS4");
		RLS4.setTextureOffset(23, 62);
		RLS4.setTextureSize(256, 256);
		RLS4.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(RLS4, 0.0f, 0.0f, 0.0f);
		RLS4.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		RLWheelPivot.addChild(RLS4);
		RLw1 = new ModelRenderer(this, "RLw1");
		RLw1.setTextureOffset(0, 84);
		RLw1.setTextureSize(256, 256);
		RLw1.setRotationPoint(-1.0f, -8.0f, -3.0f);
		setPieceRotation(RLw1, 0.0f, -1.0402973E-9f, 0.0f);
		RLw1.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		RLWheelPivot.addChild(RLw1);
		RLw2 = new ModelRenderer(this, "RLw2");
		RLw2.setTextureOffset(0, 75);
		RLw2.setTextureSize(256, 256);
		RLw2.setRotationPoint(-1.0f, -8.0f, 3.0f);
		setPieceRotation(RLw2, -0.7853982f, 0.0f, 0.0f);
		RLw2.addBox(0.0f, 0.0f, 0.0f, 2, 1, 7);
		RLWheelPivot.addChild(RLw2);
		RLw3 = new ModelRenderer(this, "RLw3");
		RLw3.setTextureOffset(0, 84);
		RLw3.setTextureSize(256, 256);
		RLw3.setRotationPoint(-1.0f, -3.0f, 8.0f);
		setPieceRotation(RLw3, -1.570796f, 0.0f, 0.0f);
		RLw3.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		RLWheelPivot.addChild(RLw3);
		RLw8 = new ModelRenderer(this, "RLw8");
		RLw8.setTextureOffset(0, 75);
		RLw8.setTextureSize(256, 256);
		RLw8.setRotationPoint(-1.0f, 3.0f, 8.0f);
		setPieceRotation(RLw8, 0.7853982f, 0.0f, 0.0f);
		RLw8.addBox(0.0f, -1.0f, -7.0f, 2, 1, 7);
		RLWheelPivot.addChild(RLw8);
		RLw7 = new ModelRenderer(this, "RLw7");
		RLw7.setTextureOffset(0, 84);
		RLw7.setTextureSize(256, 256);
		RLw7.setRotationPoint(-1.0f, 7.0f, -3.0f);
		setPieceRotation(RLw7, 0.0f, 0.0f, 0.0f);
		RLw7.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		RLWheelPivot.addChild(RLw7);
		RLw6 = new ModelRenderer(this, "RLw6");
		RLw6.setTextureOffset(0, 75);
		RLw6.setTextureSize(256, 256);
		RLw6.setRotationPoint(-1.0f, 3.0f, -8.0f);
		setPieceRotation(RLw6, -0.7853982f, 0.0f, 0.0f);
		RLw6.addBox(0.0f, -1.0f, 0.0f, 2, 1, 7);
		RLWheelPivot.addChild(RLw6);
		RLw5 = new ModelRenderer(this, "RLw5");
		RLw5.setTextureOffset(0, 84);
		RLw5.setTextureSize(256, 256);
		RLw5.setRotationPoint(-1.0f, -3.0f, -8.0f);
		setPieceRotation(RLw5, 1.570796f, 0.0f, 0.0f);
		RLw5.addBox(0.0f, 0.0f, -6.0f, 2, 1, 6);
		RLWheelPivot.addChild(RLw5);
		RLw4 = new ModelRenderer(this, "RLw4");
		RLw4.setTextureOffset(0, 75);
		RLw4.setTextureSize(256, 256);
		RLw4.setRotationPoint(-1.0f, -8.0f, -3.0f);
		setPieceRotation(RLw4, 0.7853982f, 0.0f, 0.0f);
		RLw4.addBox(0.0f, 0.0f, -7.0f, 2, 1, 7);
		RLWheelPivot.addChild(RLw4);
		rearAxle.addChild(RLWheelPivot);
		rearCrossBeam.addChild(rearAxle);
		frontCrossBeam.addChild(rearCrossBeam);
		leftBeam = new ModelRenderer(this, "leftBeam");
		leftBeam.setTextureOffset(0, 7);
		leftBeam.setTextureSize(256, 256);
		leftBeam.setRotationPoint(24.0f, 0.0f, 0.0f);
		setPieceRotation(leftBeam, 0.0f, 0.0f, 0.0f);
		leftBeam.addBox(0.0f, 0.0f, -5.0f, 3, 3, 48);
		frontCrossBeam.addChild(leftBeam);
		frontAxle = new ModelRenderer(this, "frontAxle");
		frontAxle.setTextureOffset(0, 59);
		frontAxle.setTextureSize(256, 256);
		frontAxle.setRotationPoint(12.0f, 3.0f, 1.5f);
		setPieceRotation(frontAxle, 0.0f, 0.0f, 0.0f);
		frontAxle.addBox(-17.0f, -0.5f, -0.5f, 34, 1, 1);
		FRWheelPivot = new ModelRenderer(this, "FRWheelPivot");
		FRWheelPivot.setTextureOffset(0, 62);
		FRWheelPivot.setTextureSize(256, 256);
		FRWheelPivot.setRotationPoint(-17.0f, 0.0f, 0.0f);
		setPieceRotation(FRWheelPivot, 0.0f, 0.0f, 0.0f);
		FRWheelPivot.addBox(-0.5f, -7.0f, -0.5f, 1, 14, 1);
		FRS2 = new ModelRenderer(this, "FRS2");
		FRS2.setTextureOffset(23, 62);
		FRS2.setTextureSize(256, 256);
		FRS2.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(FRS2, 0.7853982f, 0.0f, 0.0f);
		FRS2.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		FRWheelPivot.addChild(FRS2);
		FRS3 = new ModelRenderer(this, "FRS3");
		FRS3.setTextureOffset(23, 62);
		FRS3.setTextureSize(256, 256);
		FRS3.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(FRS3, -0.7853982f, 0.0f, 0.0f);
		FRS3.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		FRWheelPivot.addChild(FRS3);
		FRS4 = new ModelRenderer(this, "FRS4");
		FRS4.setTextureOffset(23, 62);
		FRS4.setTextureSize(256, 256);
		FRS4.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(FRS4, 0.0f, 0.0f, 0.0f);
		FRS4.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		FRWheelPivot.addChild(FRS4);
		frw1 = new ModelRenderer(this, "frw1");
		frw1.setTextureOffset(0, 84);
		frw1.setTextureSize(256, 256);
		frw1.setRotationPoint(-1.0f, -8.0f, -3.0f);
		setPieceRotation(frw1, 0.0f, 0.0f, 0.0f);
		frw1.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		FRWheelPivot.addChild(frw1);
		Frw4 = new ModelRenderer(this, "Frw4");
		Frw4.setTextureOffset(0, 75);
		Frw4.setTextureSize(256, 256);
		Frw4.setRotationPoint(-1.0f, -8.0f, -3.0f);
		setPieceRotation(Frw4, 0.7853982f, 0.0f, 0.0f);
		Frw4.addBox(0.0f, 0.0f, -7.0f, 2, 1, 7);
		FRWheelPivot.addChild(Frw4);
		FLr5 = new ModelRenderer(this, "FLr5");
		FLr5.setTextureOffset(0, 84);
		FLr5.setTextureSize(256, 256);
		FLr5.setRotationPoint(-1.0f, -3.0f, -8.0f);
		setPieceRotation(FLr5, 1.5707958f, 0.0f, 0.0f);
		FLr5.addBox(0.0f, 0.0f, -6.0f, 2, 1, 6);
		FRWheelPivot.addChild(FLr5);
		Frw6 = new ModelRenderer(this, "Frw6");
		Frw6.setTextureOffset(0, 75);
		Frw6.setTextureSize(256, 256);
		Frw6.setRotationPoint(-1.0f, 3.0f, -8.0f);
		setPieceRotation(Frw6, -0.7853982f, 0.0f, 0.0f);
		Frw6.addBox(0.0f, -1.0f, 0.0f, 2, 1, 7);
		FRWheelPivot.addChild(Frw6);
		Frw7 = new ModelRenderer(this, "Frw7");
		Frw7.setTextureOffset(0, 84);
		Frw7.setTextureSize(256, 256);
		Frw7.setRotationPoint(-1.0f, 7.0f, -3.0f);
		setPieceRotation(Frw7, 0.0f, 0.0f, 0.0f);
		Frw7.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		FRWheelPivot.addChild(Frw7);
		Frw8 = new ModelRenderer(this, "Frw8");
		Frw8.setTextureOffset(0, 75);
		Frw8.setTextureSize(256, 256);
		Frw8.setRotationPoint(-1.0f, 3.0f, 8.0f);
		setPieceRotation(Frw8, 0.7853982f, 0.0f, 0.0f);
		Frw8.addBox(0.0f, -1.0f, -7.0f, 2, 1, 7);
		FRWheelPivot.addChild(Frw8);
		Frw3 = new ModelRenderer(this, "Frw3");
		Frw3.setTextureOffset(0, 84);
		Frw3.setTextureSize(256, 256);
		Frw3.setRotationPoint(-1.0f, -3.0f, 8.0f);
		setPieceRotation(Frw3, -1.570796f, 0.0f, 0.0f);
		Frw3.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		FRWheelPivot.addChild(Frw3);
		Frw2 = new ModelRenderer(this, "Frw2");
		Frw2.setTextureOffset(0, 75);
		Frw2.setTextureSize(256, 256);
		Frw2.setRotationPoint(-1.0f, -8.0f, 3.0f);
		setPieceRotation(Frw2, -0.7853982f, 0.0f, 0.0f);
		Frw2.addBox(0.0f, 0.0f, 0.0f, 2, 1, 7);
		FRWheelPivot.addChild(Frw2);
		frontAxle.addChild(FRWheelPivot);
		FLWheelPivot = new ModelRenderer(this, "FLWheelPivot");
		FLWheelPivot.setTextureOffset(0, 62);
		FLWheelPivot.setTextureSize(256, 256);
		FLWheelPivot.setRotationPoint(17.0f, 0.0f, 0.0f);
		setPieceRotation(FLWheelPivot, 0.0f, 0.0f, 0.0f);
		FLWheelPivot.addBox(-0.5f, -7.0f, -0.5f, 1, 14, 1);
		FLw3 = new ModelRenderer(this, "FLw3");
		FLw3.setTextureOffset(0, 84);
		FLw3.setTextureSize(256, 256);
		FLw3.setRotationPoint(-1.0f, -3.0f, 8.0f);
		setPieceRotation(FLw3, -1.570796f, 0.0f, 0.0f);
		FLw3.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		FLWheelPivot.addChild(FLw3);
		FLw2 = new ModelRenderer(this, "FLw2");
		FLw2.setTextureOffset(0, 75);
		FLw2.setTextureSize(256, 256);
		FLw2.setRotationPoint(-1.0f, -8.0f, 3.0f);
		setPieceRotation(FLw2, -0.7853982f, 0.0f, 0.0f);
		FLw2.addBox(0.0f, 0.0f, 0.0f, 2, 1, 7);
		FLWheelPivot.addChild(FLw2);
		FS4 = new ModelRenderer(this, "FS4");
		FS4.setTextureOffset(23, 62);
		FS4.setTextureSize(256, 256);
		FS4.setRotationPoint(0.0f, 0.0f, -0.0f);
		setPieceRotation(FS4, 0.0f, 0.0f, 0.0f);
		FS4.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		FLWheelPivot.addChild(FS4);
		FLS2 = new ModelRenderer(this, "FLS2");
		FLS2.setTextureOffset(23, 62);
		FLS2.setTextureSize(256, 256);
		FLS2.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(FLS2, 0.7853982f, 0.0f, 0.0f);
		FLS2.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		FLWheelPivot.addChild(FLS2);
		FS3 = new ModelRenderer(this, "FS3");
		FS3.setTextureOffset(23, 62);
		FS3.setTextureSize(256, 256);
		FS3.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(FS3, -0.7853982f, 0.0f, 0.0f);
		FS3.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 14);
		FLWheelPivot.addChild(FS3);
		flw1 = new ModelRenderer(this, "flw1");
		flw1.setTextureOffset(0, 84);
		flw1.setTextureSize(256, 256);
		flw1.setRotationPoint(-1.0f, -8.0f, -3.0f);
		setPieceRotation(flw1, 0.0f, 0.0f, 0.0f);
		flw1.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		FLWheelPivot.addChild(flw1);
		FLw4 = new ModelRenderer(this, "FLw4");
		FLw4.setTextureOffset(0, 75);
		FLw4.setTextureSize(256, 256);
		FLw4.setRotationPoint(-1.0f, -8.0f, -3.0f);
		setPieceRotation(FLw4, 0.7853982f, 0.0f, 0.0f);
		FLw4.addBox(0.0f, 0.0f, -7.0f, 2, 1, 7);
		FLWheelPivot.addChild(FLw4);
		FLw5 = new ModelRenderer(this, "FLw5");
		FLw5.setTextureOffset(0, 84);
		FLw5.setTextureSize(256, 256);
		FLw5.setRotationPoint(-1.0f, -3.0f, -8.0f);
		setPieceRotation(FLw5, 1.5707955f, 0.0f, 0.0f);
		FLw5.addBox(0.0f, 0.0f, -6.0f, 2, 1, 6);
		FLWheelPivot.addChild(FLw5);
		FLw6 = new ModelRenderer(this, "FLw6");
		FLw6.setTextureOffset(0, 75);
		FLw6.setTextureSize(256, 256);
		FLw6.setRotationPoint(-1.0f, 3.0f, -8.0f);
		setPieceRotation(FLw6, -0.7853982f, 0.0f, 0.0f);
		FLw6.addBox(0.0f, -1.0f, 0.0f, 2, 1, 7);
		FLWheelPivot.addChild(FLw6);
		FLw7 = new ModelRenderer(this, "FLw7");
		FLw7.setTextureOffset(0, 84);
		FLw7.setTextureSize(256, 256);
		FLw7.setRotationPoint(-1.0f, 7.0f, -3.0f);
		setPieceRotation(FLw7, 0.0f, 0.0f, 0.0f);
		FLw7.addBox(0.0f, 0.0f, 0.0f, 2, 1, 6);
		FLWheelPivot.addChild(FLw7);
		FLw8 = new ModelRenderer(this, "FLw8");
		FLw8.setTextureOffset(0, 75);
		FLw8.setTextureSize(256, 256);
		FLw8.setRotationPoint(-1.0f, 3.0f, 8.0f);
		setPieceRotation(FLw8, 0.7853982f, 0.0f, 0.0f);
		FLw8.addBox(0.0f, -1.0f, -7.0f, 2, 1, 7);
		FLWheelPivot.addChild(FLw8);
		frontAxle.addChild(FLWheelPivot);
		frontCrossBeam.addChild(frontAxle);
		chairBrace = new ModelRenderer(this, "chairBrace");
		chairBrace.setTextureOffset(70, 59);
		chairBrace.setTextureSize(256, 256);
		chairBrace.setRotationPoint(4.5f, 0.5f, 20.0f);
		setPieceRotation(chairBrace, 0.0f, 0.0f, 0.0f);
		chairBrace.addBox(0.0f, 0.0f, -8.0f, 3, 1, 7);
		chairBottom = new ModelRenderer(this, "chairBottom");
		chairBottom.setTextureOffset(54, 68);
		chairBottom.setTextureSize(256, 256);
		chairBottom.setRotationPoint(-3.5f, -1.5f, -1.0f);
		setPieceRotation(chairBottom, 0.1570796f, 0.0f, 0.0f);
		chairBottom.addBox(0.0f, 0.0f, -9.0f, 10, 1, 9);
		chairBrace.addChild(chairBottom);
		chairBack = new ModelRenderer(this, "chairBack");
		chairBack.setTextureOffset(49, 79);
		chairBack.setTextureSize(256, 256);
		chairBack.setRotationPoint(-3.5f, -1.0f, -1.0f);
		setPieceRotation(chairBack, -0.1745329f, 0.0f, 0.0f);
		chairBack.addBox(0.0f, -10.0f, 0.0f, 10, 10, 1);
		chairBrace.addChild(chairBack);
		frontCrossBeam.addChild(chairBrace);
		ramUprightFR = new ModelRenderer(this, "ramUprightFR");
		ramUprightFR.setTextureOffset(0, 128);
		ramUprightFR.setTextureSize(256, 256);
		ramUprightFR.setRotationPoint(-1.5f, 0.0f, 1.5f);
		setPieceRotation(ramUprightFR, 0.0f, 0.0f, 0.0f);
		ramUprightFR.addBox(-1.5f, -23.0f, -1.5f, 3, 23, 3);
		ramUprightFL = new ModelRenderer(this, "ramUprightFL");
		ramUprightFL.setTextureOffset(0, 128);
		ramUprightFL.setTextureSize(256, 256);
		ramUprightFL.setRotationPoint(27.0f, 0.0f, 0.0f);
		setPieceRotation(ramUprightFL, 0.0f, 0.0f, 0.0f);
		ramUprightFL.addBox(-1.5f, -23.0f, -1.5f, 3, 23, 3);
		ramUprightFR.addChild(ramUprightFL);
		ramUprightRR = new ModelRenderer(this, "ramUprightRR");
		ramUprightRR.setTextureOffset(0, 128);
		ramUprightRR.setTextureSize(256, 256);
		ramUprightRR.setRotationPoint(0.0f, 0.0f, 35.0f);
		setPieceRotation(ramUprightRR, 0.0f, 0.0f, 0.0f);
		ramUprightRR.addBox(-1.5f, -23.0f, -1.5f, 3, 23, 3);
		ramUprightFR.addChild(ramUprightRR);
		ramUprightRL = new ModelRenderer(this, "ramUprightRL");
		ramUprightRL.setTextureOffset(0, 128);
		ramUprightRL.setTextureSize(256, 256);
		ramUprightRL.setRotationPoint(27.0f, 0.0f, 35.0f);
		setPieceRotation(ramUprightRL, 0.0f, 0.0f, 0.0f);
		ramUprightRL.addBox(-1.5f, -23.0f, -1.5f, 3, 23, 3);
		ramUprightFR.addChild(ramUprightRL);
		ramTopRight = new ModelRenderer(this, "ramTopRight");
		ramTopRight.setTextureOffset(0, 173);
		ramTopRight.setTextureSize(256, 256);
		ramTopRight.setRotationPoint(0.0f, -23.0f, 0.0f);
		setPieceRotation(ramTopRight, 0.0f, 0.0f, 0.0f);
		ramTopRight.addBox(-1.5f, -3.0f, -1.5f, 3, 3, 38);
		ramUprightFR.addChild(ramTopRight);
		ramTopLeft = new ModelRenderer(this, "ramTopLeft");
		ramTopLeft.setTextureOffset(0, 173);
		ramTopLeft.setTextureSize(256, 256);
		ramTopLeft.setRotationPoint(27.0f, -23.0f, 0.0f);
		setPieceRotation(ramTopLeft, 0.0f, 0.0f, 0.0f);
		ramTopLeft.addBox(-1.5f, -3.0f, -1.5f, 3, 3, 38);
		ramUprightFR.addChild(ramTopLeft);
		ramTopFront = new ModelRenderer(this, "ramTopFront");
		ramTopFront.setTextureOffset(22, 128);
		ramTopFront.setTextureSize(256, 256);
		ramTopFront.setRotationPoint(3.0f, -26.0f, 0.0f);
		setPieceRotation(ramTopFront, 0.0f, 0.0f, 0.0f);
		ramTopFront.addBox(-1.5f, 0.0f, -1.5f, 24, 3, 3);
		ramUprightFR.addChild(ramTopFront);
		ramTopRear = new ModelRenderer(this, "ramTopRear");
		ramTopRear.setTextureOffset(22, 128);
		ramTopRear.setTextureSize(256, 256);
		ramTopRear.setRotationPoint(3.0f, -26.0f, 35.0f);
		setPieceRotation(ramTopRear, 0.0f, 0.0f, 0.0f);
		ramTopRear.addBox(-1.5f, 0.0f, -1.5f, 24, 3, 3);
		ramUprightFR.addChild(ramTopRear);
		ramLeftAngle = new ModelRenderer(this, "ramLeftAngle");
		ramLeftAngle.setTextureOffset(13, 128);
		ramLeftAngle.setTextureSize(256, 256);
		ramLeftAngle.setRotationPoint(0.0f, -25.0f, 0.0f);
		setPieceRotation(ramLeftAngle, 0.9599294f, 0.0f, 0.0f);
		ramLeftAngle.addBox(-1.0f, 0.0f, -2.0f, 2, 42, 2);
		ramUprightFR.addChild(ramLeftAngle);
		ramLeftAngled = new ModelRenderer(this, "ramLeftAngled");
		ramLeftAngled.setTextureOffset(13, 128);
		ramLeftAngled.setTextureSize(256, 256);
		ramLeftAngled.setRotationPoint(27.0f, -25.0f, 0.0f);
		setPieceRotation(ramLeftAngled, 0.95993006f, 0.0f, 0.0f);
		ramLeftAngled.addBox(-1.0f, 0.0f, -2.0f, 2, 42, 2);
		ramUprightFR.addChild(ramLeftAngled);
		ramTopMidFront = new ModelRenderer(this, "ramTopMidFront");
		ramTopMidFront.setTextureOffset(22, 128);
		ramTopMidFront.setTextureSize(256, 256);
		ramTopMidFront.setRotationPoint(3.0f, -26.0f, 6.0f);
		setPieceRotation(ramTopMidFront, 0.0f, 0.0f, 0.0f);
		ramTopMidFront.addBox(-1.5f, 0.0f, -1.5f, 24, 3, 3);
		ramRopeFR = new ModelRenderer(this, "ramRopeFR");
		ramRopeFR.setTextureOffset(22, 135);
		ramRopeFR.setTextureSize(256, 256);
		ramRopeFR.setRotationPoint(11.5f, 2.0f, 0.0f);
		setPieceRotation(ramRopeFR, 0.0f, 0.0f, 7.947871E-7f);
		ramRopeFR.addBox(-0.5f, 0.0f, -0.5f, 1, 18, 1);
		ramLog = new ModelRenderer(this, "ramLog");
		ramLog.setTextureOffset(87, 128);
		ramLog.setTextureSize(256, 256);
		ramLog.setRotationPoint(0.0f, 18.0f, 0.0f);
		setPieceRotation(ramLog, 0.0f, 4.44311E-6f, -3.6618464E-6f);
		ramLog.addBox(0.0f, -8.0f, -18.0f, 8, 8, 51);
		ropeFrontBottom = new ModelRenderer(this, "ropeFrontBottom");
		ropeFrontBottom.setTextureOffset(27, 135);
		ropeFrontBottom.setTextureSize(256, 256);
		ropeFrontBottom.setRotationPoint(0.0f, 0.0f, 0.0f);
		setPieceRotation(ropeFrontBottom, 0.0f, 0.0f, 0.0f);
		ropeFrontBottom.addBox(-0.5f, -0.5f, -0.5f, 9, 1, 1);
		ramLog.addChild(ropeFrontBottom);
		ramLogRopeRearBottom = new ModelRenderer(this, "ramLogRopeRearBottom");
		ramLogRopeRearBottom.setTextureOffset(27, 135);
		ramLogRopeRearBottom.setTextureSize(256, 256);
		ramLogRopeRearBottom.setRotationPoint(0.0f, 0.0f, 23.0f);
		setPieceRotation(ramLogRopeRearBottom, 0.0f, 0.0f, 0.0f);
		ramLogRopeRearBottom.addBox(-0.5f, -0.5f, -0.5f, 9, 1, 1);
		ramLog.addChild(ramLogRopeRearBottom);
		ramLogCapFront = new ModelRenderer(this, "ramLogCapFront");
		ramLogCapFront.setTextureOffset(27, 138);
		ramLogCapFront.setTextureSize(256, 256);
		ramLogCapFront.setRotationPoint(-1.0f, -9.0f, -19.0f);
		setPieceRotation(ramLogCapFront, 0.0f, 0.0f, 0.0f);
		ramLogCapFront.addBox(0.0f, 0.0f, 0.0f, 10, 10, 1);
		ramLogCapTop = new ModelRenderer(this, "ramLogCapTop");
		ramLogCapTop.setTextureOffset(27, 150);
		ramLogCapTop.setTextureSize(256, 256);
		ramLogCapTop.setRotationPoint(1.0f, 0.5f, 1.0f);
		setPieceRotation(ramLogCapTop, 0.0f, 0.0f, 0.0f);
		ramLogCapTop.addBox(0.0f, 0.0f, 0.0f, 8, 1, 10);
		ramLogCapFront.addChild(ramLogCapTop);
		ramLogCapRight = new ModelRenderer(this, "ramLogCapRight");
		ramLogCapRight.setTextureOffset(64, 135);
		ramLogCapRight.setTextureSize(256, 256);
		ramLogCapRight.setRotationPoint(0.5f, 1.0f, 1.0f);
		setPieceRotation(ramLogCapRight, 0.0f, 0.0f, 0.0f);
		ramLogCapRight.addBox(0.0f, 0.0f, 0.0f, 1, 8, 10);
		ramLogCapFront.addChild(ramLogCapRight);
		ramLogCapBottom = new ModelRenderer(this, "ramLogCapBottom");
		ramLogCapBottom.setTextureOffset(27, 150);
		ramLogCapBottom.setTextureSize(256, 256);
		ramLogCapBottom.setRotationPoint(1.0f, 8.5f, 1.0f);
		setPieceRotation(ramLogCapBottom, 0.0f, 0.0f, 0.0f);
		ramLogCapBottom.addBox(0.0f, 0.0f, 0.0f, 8, 1, 10);
		ramLogCapFront.addChild(ramLogCapBottom);
		ramLogCapLeft = new ModelRenderer(this, "ramLogCapLeft");
		ramLogCapLeft.setTextureOffset(64, 135);
		ramLogCapLeft.setTextureSize(256, 256);
		ramLogCapLeft.setRotationPoint(8.5f, 1.0f, 1.0f);
		setPieceRotation(ramLogCapLeft, 0.0f, 0.0f, 0.0f);
		ramLogCapLeft.addBox(0.0f, 0.0f, 0.0f, 1, 8, 10);
		ramLogCapFront.addChild(ramLogCapLeft);
		logCapTip = new ModelRenderer(this, "logCapTip");
		logCapTip.setTextureOffset(27, 162);
		logCapTip.setTextureSize(256, 256);
		logCapTip.setRotationPoint(1.0f, 1.0f, -1.0f);
		setPieceRotation(logCapTip, 0.0f, 0.0f, 0.0f);
		logCapTip.addBox(0.0f, 0.0f, 0.0f, 8, 8, 1);
		ramLogCapFront.addChild(logCapTip);
		ramLog.addChild(ramLogCapFront);
		ramRopeFR.addChild(ramLog);
		ramTopMidFront.addChild(ramRopeFR);
		ramRopeFL = new ModelRenderer(this, "ramRopeFL");
		ramRopeFL.setTextureOffset(22, 135);
		ramRopeFL.setTextureSize(256, 256);
		ramRopeFL.setRotationPoint(19.5f, 2.0f, 0.0f);
		setPieceRotation(ramRopeFL, 0.0f, 0.0f, 0.0f);
		ramRopeFL.addBox(-0.5f, 0.0f, -0.5f, 1, 18, 1);
		ramTopMidFront.addChild(ramRopeFL);
		ramTopMidRear = new ModelRenderer(this, "ramTopMidRear");
		ramTopMidRear.setTextureOffset(22, 128);
		ramTopMidRear.setTextureSize(256, 256);
		ramTopMidRear.setRotationPoint(0.0f, 0.0f, 23.0f);
		setPieceRotation(ramTopMidRear, 0.0f, 0.0f, 0.0f);
		ramTopMidRear.addBox(-1.5f, 0.0f, -1.5f, 24, 3, 3);
		ramRopeRL = new ModelRenderer(this, "ramRopeRL");
		ramRopeRL.setTextureOffset(22, 135);
		ramRopeRL.setTextureSize(256, 256);
		ramRopeRL.setRotationPoint(19.5f, 2.0f, 0.0f);
		setPieceRotation(ramRopeRL, 0.0f, 0.0f, 0.0f);
		ramRopeRL.addBox(-0.5f, 0.0f, -0.5f, 1, 18, 1);
		ramTopMidRear.addChild(ramRopeRL);
		ramRopeRR = new ModelRenderer(this, "ramRopeRR");
		ramRopeRR.setTextureOffset(22, 135);
		ramRopeRR.setTextureSize(256, 256);
		ramRopeRR.setRotationPoint(11.5f, 2.0f, 0.0f);
		setPieceRotation(ramRopeRR, 0.0f, 0.0f, 0.0f);
		ramRopeRR.addBox(-0.5f, 0.0f, -0.5f, 1, 18, 1);
		ramTopMidRear.addChild(ramRopeRR);
		ramTopMidFront.addChild(ramTopMidRear);
		ramUprightFR.addChild(ramTopMidFront);
		frontCrossBeam.addChild(ramUprightFR);
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
		super.render(entity, f1, f2, f3, f4, f5, f6);
		setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
		frontCrossBeam.render(f6);
	}

	public void setLogRotation(float rot) {
		this.ramRopeFL.rotateAngleX = Trig.toRadians(rot);
		this.ramRopeFR.rotateAngleX = Trig.toRadians(rot);
		this.ramRopeRR.rotateAngleX = Trig.toRadians(rot);
		this.ramRopeRL.rotateAngleX = Trig.toRadians(rot);
		this.ramLog.rotateAngleX = -Trig.toRadians(rot);
	}

	public void setWheelRotations(float fl, float fr, float rl, float rr) {
		this.FRWheelPivot.rotateAngleX = Trig.toRadians(fr);
		this.FLWheelPivot.rotateAngleX = Trig.toRadians(fl);
		this.RRWheelPivot.rotateAngleX = Trig.toRadians(rr);
		this.RLWheelPivot.rotateAngleX = Trig.toRadians(rl);
	}

	public void setPieceRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
