package invalid.myask.vindicateandspendicate.block;

import invalid.myask.vindicateandspendicate.VindicateAndSpendicate;
import invalid.myask.vindicateandspendicate.compat.EtFuturumWrappium;
import invalid.myask.vindicateandspendicate.util.VectorHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.tileentity.TileEntityBell;

import java.util.List;

public class BlockBell extends Block implements ITileEntityProvider {
    public BlockBell() {
        super(Material.iron);
        textureName = VindicateAndSpendicate.MODID + ":bell_top.png";
        setHarvestLevel("pickaxe", 0);
        useNeighborBrightness = true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBell(worldIn, meta);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, int x, int y, int z, Entity entityIn) {
        if (Config.shot_bells_ring
               && entityIn instanceof IProjectile shot
               && worldIn.getTileEntity(x, y, z) instanceof TileEntityBell bell) {
            if (!(Config.all_shots_ring_bells || entityIn instanceof EntityArrow)) return; //also includes tridents
            int side = collisionRayTrace(worldIn, x, y, z,
                VectorHelper.entityPosAsVec3(entityIn), VectorHelper.entityVAsVec3(entityIn)).sideHit;
            boolean result = switch (worldIn.getBlockMetadata(x, y, z)) {
                case 1 -> true; //can ring either way!
                case 0, 2 -> (side & 6) == 2;
                case 3, 4 -> (side & 6) == 4;
                default -> false;
            };
            if (result)
                bell.ringByShot(x, y, z, entityIn, side);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        boolean result = Config.rightclicked_bells_ring &&
          switch (worldIn.getBlockMetadata(x, y, z)) {
            case 1 -> true; //can ring either way!
            case 0, 2 -> (side & 6) == 2;
            case 3, 4 -> (side & 6) == 4;
            default -> super.onBlockActivated(worldIn, x, y, z, player, side, subX, subY, subZ);
        };
        if (result && worldIn.getTileEntity(x, y, z) instanceof TileEntityBell bell) {
            bell.ringByPress(x, y, z, player, side);
            return true;
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        if (!isBlockSupported(worldIn, x, y, z)) {
            dropBlockAsItem(worldIn, x, y, z, worldIn.getBlockMetadata(x, y, z), 0);
            worldIn.setBlock(x, y, z, Blocks.air, 0, 2);
        } else {
            if (worldIn.getTileEntity(x, y, z) instanceof TileEntityBell bell)
                bell.blockupdate(worldIn, x, y, z);
        }
    }

    public boolean isBlockSupported(World world, int x, int y, int z) {
        return switch (world.getBlockMetadata(x, y, z)) {
            case 0, 4 -> world.isSideSolid(x, y - 1, z, ForgeDirection.UP);
            case 1 -> world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN) || hangsBell(world, x, y + 1, z);
            case 2 -> world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) ||
                    world.isSideSolid(x - 1, y, z, ForgeDirection.EAST);
            case 3 -> world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) ||
                world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH);
            default -> false;
        };
    }

    protected boolean hangsBell(World world, int x, int y, int z) {
        if (EtFuturumWrappium.instance.isVerticalChain(world, x, y, z)) return true;
        AxisAlignedBB bb = getCollisionBoundingBoxFromPool(world, x, y, z);
        return bb.minY == 0 && bb.minX <= 7/16F && bb.minZ <= 7/16F && bb.maxX >= 9/16F && bb.maxZ >= 9/16F;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0 -> setBlockBounds(0, 0, 4/16F, 1, 1, 12/16F);
            case 4 -> setBlockBounds(4/16F, 0, 0, 12/16F, 1, 1);
//          case 1, 2, 3
            default -> setBlockBounds(4/16F, 4/16F, 4/16F, 12/16F, 13/16F, 12/16F);
        }
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collider) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0 -> list.add(AxisAlignedBB.getBoundingBox(0, 0, 4/16F, 1, 1, 12/16F));
            case 4 -> list.add(AxisAlignedBB.getBoundingBox(4/16F, 0, 0, 12/16F, 1, 1));
            case 2, 3 -> {
                list.add(AxisAlignedBB.getBoundingBox(4/16F, 4/16F, 4/16F, 12/16F, 6/16F, 12/16F));
                list.add(AxisAlignedBB.getBoundingBox(5/16F, 6/16F, 5/16F, 11/16F, 13/16F, 11/16F));
            }
            //case 1
            default -> setBlockBounds(4/16F, 4/16F, 4/16F, 12/16F, 13/16F, 12/16F);
        }
    }

    //Clientside


    @Override
    public void registerBlockIcons(IIconRegister reg) {}

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        if (worldIn.getTileEntity(x, y, z) instanceof TileEntityBell bell)
            return bell.getSupportIconSafely();
        return getIcon(side, 0);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (blockIcon == null) blockIcon = Blocks.planks.getIcon(0, 5); //dark oak
        return blockIcon;
    }

    @Override
    public MapColor getMapColor(int meta) {
        return MapColor.goldColor;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return super.getRenderType();
    }

    public void setBlockBoundsForRender(IBlockAccess world, int x, int y, int z, int part) {
        int meta = world.getBlockMetadata(x, y, z);
        boolean sideMinus = false, sidePlus = false;
        switch (part) {
            case 0:
                switch (meta) {
                    case 4: //down, x-facing
                        setBlockBounds(7/16F, 13/16F, 1/16F, 9/16F, 15/16F, 15/16F);
                        break;
                    case 1: //up
                        setBlockBounds(7/16F, 13/16F, 7/16F, 9/16F, 1, 9/16F);
                        break;
                    case 2: //EW
                        sideMinus = world.isSideSolid(x - 1, y, z, ForgeDirection.EAST, false);//FIXME: just stick it in meta and update on update.
                        sidePlus = world.isSideSolid(x + 1, y, z, ForgeDirection.WEST, false);
                        setBlockBounds(sideMinus ? 0 : 3/16F, 13/16F, 7/16F,
                            sidePlus ? 1 : 15/16F, 15/16F, 9/16F);
                        break;
                    case 3: //NS
                        sideMinus = world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH, false);
                        sidePlus = world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH, false);
                        setBlockBounds(7/16F, 13/16F, sideMinus ? 0 : 3/16F,
                            9/16F, 15/16F, sidePlus ? 1 : 15/16F);
                        break;
                    case 0: //down, z-facing
                    default:
                        setBlockBounds(1/16F, 13/16F, 7/16F, 15/16F, 15/16F, 9/16F);
                        break;
                }
                break;
            case 1: //left support
                if (meta == 4) { //down z-facing
                    setBlockBounds(6/16F, 0, 0, 10/16F, 1, 1/16F);
                } else setBlockBounds(0, 0, 6/16F, 1/16F, 1, 10/16F);
                break;
            case 2: //right support
                if (meta == 4) { //down z-facing
                    setBlockBounds(6/16F, 0, 15/16F, 10/16F, 1, 1);
                } else setBlockBounds(15/16F, 0, 6/16F, 1, 1, 10/16F);
                break;
        }
    }
}
