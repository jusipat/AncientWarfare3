package xyz.dylanlogan.ancientwarfare.core.model.crafting_table;

import net.minecraft.client.model.ModelRenderer;

public class ModelEngineeringStation extends ModelCraftingBase {

    public ModelEngineeringStation() {
        ModelRenderer paperLarge = new ModelRenderer(this, "paperLarge");
        paperLarge.setTextureOffset(65, 0);
        paperLarge.rotationPointY = -14.02f;
        paperLarge.rotateAngleY = -0.19198619f;
        paperLarge.addBox(-6, 0, -6, 12, 0, 12);
        addPiece(paperLarge);
        addHammer();
    }
}
