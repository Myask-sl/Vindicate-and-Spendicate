package invalid.myask.vindicateandspendicate.client.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import invalid.myask.vindicateandspendicate.VindicateAndSpendicate;
import invalid.myask.vindicateandspendicate.block.BlockBell;
import invalid.myask.vindicateandspendicate.tileentity.TileEntityBell;

public class RenderBell extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
    public static final RenderBell instance = new RenderBell();
    public final int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
    protected final ModelVillageBell model;
    public static final ResourceLocation BELL_TEXTURE = new ResourceLocation(VindicateAndSpendicate.MODID, "textures/entity/bell.png");

    public RenderBell() {
        this.model = new ModelVillageBell();
        model.setRotationAngle(0, 0,0);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        //nope, just do item texture
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int renderID, RenderBlocks renderer) {
        boolean rendered = false;
        if (block instanceof BlockBell bell) {
            int meta = world.getBlockMetadata(x, y, z);
            renderer.setOverrideBlockTexture(bell.getIcon(0, 0));
            bell.setBlockBoundsForRender(world, x, y, z, 0);
            renderer.setRenderBoundsFromBlock(bell);
            rendered = renderer.renderStandardBlock(bell, x, y, z);
            renderer.clearOverrideBlockTexture();
            if (meta == 0 || meta == 4) { //do side supports
                bell.setBlockBoundsForRender(world, x, y, z, 1);
                renderer.setRenderBoundsFromBlock(bell);
                rendered = renderer.renderStandardBlock(bell, x, y, z) || rendered;

                bell.setBlockBoundsForRender(world, x, y, z, 2);
                renderer.setRenderBoundsFromBlock(bell);
                rendered = renderer.renderStandardBlock(bell, x, y, z) || rendered;
            }
            bell.setBlockBoundsBasedOnState(world, x, y, z);
        }
        return rendered;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return RENDER_ID;
    }
//the TE renderer

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
        if (tileEntity instanceof TileEntityBell bellTE) {
            bindTexture(BELL_TEXTURE);
            model.setRotationAngle((float) (bellTE.rotation.x + bellTE.rot_v.x * partialTick),
                (float) (bellTE.rotation.y + bellTE.rot_v.y * partialTick) + 180,
                (float) (bellTE.rotation.z + bellTE.rot_v.z * partialTick));

            GL11.glPushMatrix();
            GL11.glTranslatef((float)x + .5F, (float)y +.5F, (float)z + .5F);
            model.render(null, 0, 0,0 ,0, 0,0.0625F);
            GL11.glPopMatrix();
        }
    }
}
