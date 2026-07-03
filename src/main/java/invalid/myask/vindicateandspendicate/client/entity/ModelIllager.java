package invalid.myask.vindicateandspendicate.client.entity;
// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.7* - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import invalid.myask.vindicateandspendicate.client.ModelBox;
import invalid.myask.vindicateandspendicate.entity.illager.EntityPillager;

public class ModelIllager extends ModelBase {
    public static final float LEFT_LOAD_ROX = -0.45F;
    public static final float RIGHT_LOAD_ROZ = 0.628F;
    public static final float RIGHT_LOAD_ROX = -0.314F;
    private static final float LEFT_LOAD_ROZ_BASE = 0.5F;
    private static final float LEFT_LOAD_ROZ_SPAN = 0.5F;
    private static final float RIGHT_LOAD_ROY = 0;
    private static final float LEFT_LOAD_ROY_BASE = 0;
    private static final float LEFT_LOAD_ROY_SPAN = 0;

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

        setArmsFolded(false);
        setHasHatBrim(false);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		waist.render(scaleFactor);
		rightLeg.render(scaleFactor);
		leftLeg.render(scaleFactor);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

    public void setArmsFolded(boolean folded) {
        foldedArms.isHidden = !folded;
        leftArm.isHidden = folded;
        rightArm.isHidden = folded;
    }

    public void setHasHatBrim(boolean brim) {
        hatbrim.isHidden = !brim;
        hoodlayer.isHidden = brim;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        head.rotateAngleY = netHeadYaw / (180F / (float)Math.PI);
        head.rotateAngleX = headPitch / (180F / (float)Math.PI);
        rightArm.rotateAngleZ = 0.0F;
        leftArm.rotateAngleZ = 0.0F;
        if (entityIn.isRiding()) {

        } else {
            rightLeg.rotateAngleX = MathHelper.cos(limbSwing * (2 / 3F)) * 1.4F * limbSwingAmount;
            leftLeg.rotateAngleX = MathHelper.cos(limbSwing * (2 / 3F) + (float) Math.PI) * 1.4F * limbSwingAmount;
            rightLeg.rotateAngleY = 0.0F;
            leftLeg.rotateAngleY = 0.0F;
        }
        if (entityIn instanceof EntityPillager pger) {
            if (pger.getHandsAction() == EntityPillager.Action.FOLD) {
                setArmsFolded(true);
            } else {
                setArmsFolded(false);
                if (pger.getHandsAction() < 0) {
                    leftArm.rotateAngleX = MathHelper.cos(limbSwing * (2 / 3F)) * limbSwingAmount;
                    leftArm.rotateAngleY = 0;
                }
                switch (pger.getHandsAction()) {
                    case EntityPillager.Action.HOLD:
                        rightArm.rotateAngleX = MathHelper.cos(limbSwing * (2 / 3F) + (float) Math.PI) * limbSwingAmount;
                        if (entityIn.isRiding()) {
                            rightArm.rotateAngleX -= 0.625F;
                            leftArm.rotateAngleX -= 0.625F;
                        }
                        rightArm.rotateAngleY = 0;
                        break;
                    case EntityPillager.Action.AIM:
                        rightArm.rotateAngleX = head.rotateAngleX * 0.9F;
                        rightArm.rotateAngleY = head.rotateAngleY * 0.9F;
                        break;
                    case EntityPillager.Action.BRANDISH:
                        rightArm.rotateAngleX /= 2F;
                        rightArm.rotateAngleX -= 0.314F;
                        rightArm.rotateAngleY = 0;
                        break;
                    default:
                        rightArm.rotateAngleX = RIGHT_LOAD_ROX;
                        rightArm.rotateAngleY = RIGHT_LOAD_ROY;
                        rightArm.rotateAngleZ = RIGHT_LOAD_ROZ;
                        leftArm.rotateAngleX = LEFT_LOAD_ROX;
                        float fractionalTick = ((long) ageInTicks) - ageInTicks;
                        float pullFraction = (pger.getLoading() + fractionalTick) / pger.getLoadLength();
                        pullFraction = MathHelper.clamp_float(pullFraction, 0, 1);
                        leftArm.rotateAngleZ = LEFT_LOAD_ROZ_BASE + pullFraction * LEFT_LOAD_ROZ_SPAN;
                        leftArm.rotateAngleY = LEFT_LOAD_ROY_BASE + pullFraction * LEFT_LOAD_ROY_SPAN;
                }
            }
        }
    }
}
