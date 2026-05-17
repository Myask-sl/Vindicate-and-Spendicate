package invalid.myask.vindicateandspendicate.client.entity;
// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.7* - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import invalid.myask.vindicateandspendicate.client.ModelBox;

public class ModelIllager extends ModelBase {
	private final ModelRenderer waist;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer nose;
	private final ModelRenderer hatbrim;
	private final ModelRenderer hoodlayer;
	private final ModelRenderer rightArm;
	//private final ModelRenderer handitem;
	private final ModelRenderer foldedArms;
	private final ModelRenderer leftArm;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;

	public ModelIllager() {
		textureWidth = 64;
		textureHeight = 64;

		waist = new ModelRenderer(this);
		waist.setRotationPoint(0.0F, 12.0F, 0.0F);


		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 6.0F, 0.0F);
		waist.addChild(body);
		body.cubeList.add(new ModelBox(body, 16, 20, -4.0F, -18.0F, -3.0F, 8, 12, 6, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 38, -4.0F, -18.0F, -3.0F, 8, 18, 6, 0.25F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -18.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F, false));

		nose = new ModelRenderer(this);
		nose.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(nose);
		nose.cubeList.add(new ModelBox(nose, 24, 0, -1.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F, false));
		nose.cubeList.add(new ModelBox(nose, 48, 0, -3.0F, 0.0F, -6.0F, 6, 4, 2, 0.0F, false));

		hatbrim = new ModelRenderer(this);
		hatbrim.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(hatbrim);
		hatbrim.cubeList.add(new ModelBox(hatbrim, 16, 0, -8.0F, -6.0F, -8.0F, 16, 0, 16, 0.0F, false));

		hoodlayer = new ModelRenderer(this);
		hoodlayer.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(hoodlayer);
		hoodlayer.cubeList.add(new ModelBox(hoodlayer, 32, 0, -4.0F, -10.0F, -4.0F, 8, 12, 8, 0.5F, false));

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(-5.0F, -16.0F, 0.0F);
		body.addChild(rightArm);
		setRotationAngle(rightArm, -0.0873F, 0.0F, 0.0F);
		rightArm.cubeList.add(new ModelBox(rightArm, 40, 46, -3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, false));

/*		handitem = new ModelRenderer(this);
		handitem.setRotationPoint(0.0F, 9.0F, 0.0F);
		rightArm.addChild(handitem);
		setRotationAngle(handitem, 2.3562F, 0.0F, 0.0F);
		handitem.cubeList.add(new ModelBox(handitem, 40, -11, -1.0F, -13.0F, -1.0F, 0, 14, 12, 0.0F, false));
*/
		foldedArms = new ModelRenderer(this);
		foldedArms.setRotationPoint(-6.0F, -16.0F, -1.5F);
		body.addChild(foldedArms);
		setRotationAngle(foldedArms, -1.0472F, 0.0F, 0.0F);
		foldedArms.cubeList.add(new ModelBox(foldedArms, 40, 38, 2.0F, 2.0F, -1.5F, 8, 4, 4, 0.0F, false));
		foldedArms.cubeList.add(new ModelBox(foldedArms, 44, 22, -2.0F, -2.0F, -1.5F, 4, 8, 4, 0.0F, false));
		foldedArms.cubeList.add(new ModelBox(foldedArms, 44, 22, 10.0F, -2.0F, -1.5F, 4, 8, 4, 0.0F, true));

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(5.0F, -10.0F, 0.0F);
		waist.addChild(leftArm);
		setRotationAngle(leftArm, 0.1309F, 0.0F, 0.0F);
		leftArm.cubeList.add(new ModelBox(leftArm, 40, 46, -1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, true));

		rightLeg = new ModelRenderer(this);
		rightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 22, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 22, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		waist.render(f5);
		rightLeg.render(f5);
		leftLeg.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
