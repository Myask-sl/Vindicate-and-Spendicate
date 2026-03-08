package invalid.myask.vindicateandspendicate.client.tileentity;
// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.7 - 1.12 //(lies, editing required for 1.7)
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVillageBell extends ModelBase {
	private final ModelRenderer bone;

	public ModelVillageBell() {
		textureWidth = 32;
		textureHeight = 32;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 14.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 13, -4.0F, -10.0F, -4.0F, 8, 2, 8, 0.0F));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -8.0F, -3.0F, 6, 7, 6, 0.0F));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        bone.render(scale);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

    public void setRotationAngle(float x, float y, float z) {
        setRotationAngle(bone, x, y, z);
    }
}
