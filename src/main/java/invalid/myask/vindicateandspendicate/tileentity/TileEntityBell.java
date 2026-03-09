package invalid.myask.vindicateandspendicate.tileentity;

import java.util.List;

import org.joml.Vector3d;

import cpw.mods.fml.common.registry.IThrowableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.compat.EtFuturumWrappium;
import invalid.myask.vindicateandspendicate.compat.UndertowWrapper;
import invalid.myask.vindicateandspendicate.entity.illager.Illager;
import invalid.myask.vindicateandspendicate.util.VectorHelper;

public class TileEntityBell extends TileEntity {
    private static final double M_BELL = 10;
    private static final double K_BELL_SQUARED_TIMES_M_BELL = ((16 - 8.346666) / 16) * ((16 - 8.346666) / 16) * M_BELL;

    public Vector3d rotation, rot_v;
    protected int ticksRung, meta;
    protected boolean wasPowered;
    protected IIcon supportIcon;
    protected AxisAlignedBB ringBox;
    protected ForgeDirection ringDir;

    public TileEntityBell(World worldIn, int meta) {
        super();
        worldObj = worldIn;
        rotation = new Vector3d(0, 0,0 );
        rot_v = new Vector3d(0, 0, 0);
        ticksRung = -1;
        this.meta = meta;
        wasPowered = false;
        supportIcon = null;
        ringDir = ForgeDirection.UNKNOWN;
    }

    public void ringByShot(int x, int y, int z, Entity shot, int side) {
        if (Config.bell_swing_physics) {
            Vector3d vec = VectorHelper.createEntityPosAsVector3d(shot);
            double radius = vec.distance(x + .5, y + 14/16F, z + .5);
            vec.set(shot.motionX, shot.motionY, shot.motionZ);
            vec.cross(x + .5 - shot.posX, y + 14/16F - shot.posY, z + .5 - shot.posZ);
            vec.mul(radius * massOf(shot) / K_BELL_SQUARED_TIMES_M_BELL);
            addOrRestartSwing(vec.x, vec.y, vec.z);
        } else
            swingNormal(side);
        ring(x, y, z);
    }

    private double massOf(Entity shot) {
        if (shot instanceof EntityArrow) return 0.2;
        if (shot instanceof EntityEgg) return 0.1;
        if (shot instanceof EntitySnowball) return 0.075;
        if (shot instanceof EntityPotion) return 0.35;
        if (shot instanceof EntityExpBottle) return 0.2;
        if (shot instanceof EntityFireworkRocket) return 0.5;
        if (UndertowWrapper.instance.isTrident(shot)) return 2;
        if (shot instanceof IThrowableEntity) return 0.4;
        return 1;
    }

    public void ringByPress(int x, int y, int z, EntityPlayer player, int side) {
        if (Config.bell_swing_physics) {
            MovingObjectPosition hit = player.worldObj.getBlock(x, y, z).collisionRayTrace(player.worldObj, x, y, z,
                VectorHelper.entityPosAsVec3(player), player.getLookVec());
            Vector3d vec = new Vector3d(hit.hitVec.xCoord, hit.hitVec.yCoord, hit.hitVec.zCoord);
            double radius = vec.distance(x + .5, y + 14/16F, z + .5);
        } else
            swingNormal(side);
        ring(x, y, z);
    }

    private void ringByRedstone(int x, int y, int z) {
        if (Config.bell_redstone_swings_correctly && (meta == 0 || meta == 3))
            swingNormal(2);
        else swingNormal(4);
        ring(x, y, z);
    }

    private void swingNormal(int side) {
        if (side < 0 || side > 5) return;
        ForgeDirection direction = ForgeDirection.getOrientation(side);
        direction = direction.getRotation(ForgeDirection.DOWN);
        addOrRestartSwing(direction.offsetX, direction.offsetY, direction.offsetZ);
        ringDir = direction;
    }

    private void addOrRestartSwing(double x, double y, double z) {
        if (Config.bells_reset_rotation) {
            rotation.set(0, 0, 0);
            rot_v.set(x, y, z);
        } else rot_v.add(x, y, z);
    }

    private void ring(int x, int y, int z) {
        ticksRung = 0;
        worldObj.playSound(x + .5, y + .5, z + .5, "block.bell.use", 1, .9F + worldObj.rand.nextFloat() * .2F, false);
        List<EntityLiving> rungEntities = worldObj.getEntitiesWithinAABB(EntityLiving.class, getRingBox(x, y, z));
        for (EntityLiving e : rungEntities) {
            if (e instanceof EntityVillager villager) {
                //send them into lockdown.
                //subclass AIRestrictOpenDoor and AIMoveIndoors?
            } else if (e instanceof Illager) {
                e.playSound("block.bell.resonate", 1, .9F + e.getRNG().nextFloat() * .2F);
                if (EtFuturumWrappium.instance.glowingExists())
                    e.addPotionEffect(new PotionEffect(EtFuturumWrappium.instance.getGlowingPotionID(), 60));
            }
        }
        markDirty();
    }

    public AxisAlignedBB getRingBox(int x, int y, int z) {
        if (ringBox == null)
            ringBox = AxisAlignedBB.getBoundingBox(
                x + .5 - Config.bell_effect_radius, y + .5 - Config.bell_effect_radius, z + .5 - Config.bell_effect_radius,
                x + .5 + Config.bell_effect_radius, y + .5 + Config.bell_effect_radius, z + .5 + Config.bell_effect_radius);
        return ringBox;
    }

    public void blockupdate(World worldIn, int x, int y, int z) {
        boolean powered = worldIn.isBlockIndirectlyGettingPowered(x, y, z);
        if (Config.redstoned_bells_ring && powered && !wasPowered)
            ringByRedstone(x, y, z);
        wasPowered = powered;
        if (Config.bell_morphs_support && (meta == 0 || meta == 4) &&
            worldIn.getBlock(x, y - 1, z).isOpaqueCube() && worldIn.getBlock(x, y - 1, z).renderAsNormalBlock())
            supportIcon = worldIn.getBlock(x, y - 1, z).getIcon(worldIn, x, y - 1, z, 0);
    }

    public IIcon getSupportIconSafely() {
        return supportIcon == null ? Blocks.stone.getIcon(0, 0) : supportIcon;
    }

    @Override
    public void updateEntity() {
        if (!Config.bell_swing_physics && ticksRung > 100) ticksRung = -1;
        if (ticksRung == -1) {
            rotation.set(0, 0, 0);
            return;
        } else ticksRung++;

        if (Config.bell_swing_physics && ticksRung > 0) {
            if (rotation.maxComponent() < 0.001 && rot_v.maxComponent() < 0.001) {
                rotation.set(0, 0, 0);
                rot_v.set(0, 0, 0);
                ticksRung = -1;
            } else {
                rotation.add(rot_v);
                rot_v.mul(0.9);
                rot_v.add(-Math.sin(rotation.x) * .08, 0, -Math.sin(rotation.z) * .08);
                //normally g is .08/tick in MC
            }
        } else {
            double rot = Math.sin(ticksRung * Math.PI / 20) * Config.bell_static_swing_mag / ticksRung,
                swing = Math.cos(ticksRung * Math.PI / 20) * Config.bell_static_swing_mag / ticksRung;
            rotation.set(rot * ringDir.offsetX, 0, rot * ringDir.offsetZ);
            rot_v.set(swing * ringDir.offsetX, 0, swing * ringDir.offsetZ);
        }
    }


    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setDouble("rotation_x", rotation.x);
        compound.setDouble("rotation_y", rotation.y);
        compound.setDouble("rotation_z", rotation.z);
        compound.setDouble("rot_v_x", rot_v.x);
        compound.setDouble("rot_v_y", rot_v.y);
        compound.setDouble("rot_v_z", rot_v.z);
        compound.setInteger("ticksRung", ticksRung);
        compound.setInteger("meta", meta);
        compound.setBoolean("wasPowered", wasPowered);
        compound.setInteger("ringDir", ringDir.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        rotation.x = compound.getDouble("rotation_x");
        rotation.y = compound.getDouble("rotation_y");
        rotation.z = compound.getDouble("rotation_z");
        rot_v.x = compound.getDouble("rot_v_x");
        rot_v.y = compound.getDouble("rot_v_y");
        rot_v.z = compound.getDouble("rot_v_z");
        ticksRung = compound.getInteger("ticksRung");
        meta = compound.getInteger("meta");
        wasPowered = compound.getBoolean("wasPowered");
        ringDir = ForgeDirection.getOrientation(compound.getInteger("ringDir"));
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound wholeShebang = new NBTTagCompound();
        writeToNBT(wholeShebang);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, meta, wholeShebang);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound wholeShebang = pkt.func_148857_g();
        readFromNBT(wholeShebang);
    }
}
