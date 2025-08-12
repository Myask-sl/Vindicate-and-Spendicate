package invalid.myask.vindicateandspendicate.utils;

import org.joml.Vector3d;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class VectorHelper {
    public static Vector3d createLookVec(Entity entity) {
        Vector3d result = new Vector3d(1,0,0);
        assignLookVec(entity, result);
        return result;
    }

    public static void assignLookVec(Entity entity, Vector3d result) {
        result.set(0,0,1);
        result.rotateX(entity.rotationPitch);
        result.rotateY(entity.rotationYaw);
    }

    public static Vec3 entityPosAsVec(Entity e) {
        return Vec3.createVectorHelper(e.posX, e.posY, e.posZ);
    }

    public static void setEntityV(Entity newShot, Vec3 heading) {
        newShot.motionX = heading.xCoord;
        newShot.motionY = heading.yCoord;
        newShot.motionZ = heading.zCoord;
    }

    public static void setEntityV(Entity newShot, Vector3d heading) {
        newShot.motionX = heading.x;
        newShot.motionY = heading.y;
        newShot.motionZ = heading.z;
    }

    public static void setEntityVTimes(Entity newShot, Vec3 heading, float scalar) {
        newShot.motionX = heading.xCoord * scalar;
        newShot.motionY = heading.yCoord * scalar;
        newShot.motionZ = heading.zCoord * scalar;
    }

    public static void setEntityVTimes(Entity newShot, Vector3d heading, float scalar) {
        newShot.motionX = heading.x * scalar;
        newShot.motionY = heading.y * scalar;
        newShot.motionZ = heading.z * scalar;
    }
}
