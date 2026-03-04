package invalid.myask.vindicateandspendicate.util;

import invalid.myask.vindicateandspendicate.Config;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

// TODO: move to cowtools
public class FireworksBuilder { //implements Builder<ItemStack> {
    protected final ItemStack stack;
    protected byte duration = 1;
    protected boolean explosion = false;
    protected final NBTTagCompound nbt, nbtFireworks;
    protected NBTTagList nbtExplosions;

    public FireworksBuilder() {
        this(1);
    }

    public FireworksBuilder(int powder) {
        this.stack = new ItemStack(Items.fireworks);

        nbt = new NBTTagCompound();
        nbtFireworks = new NBTTagCompound();
        duration = switch (powder) {
            case 2, 3 -> (byte) powder;
            default -> 1;
        };
        nbtFireworks.setInteger("Flight", duration);

        stack.setTagCompound(nbt);
        nbt.setTag("Fireworks", nbtFireworks);
    }

    private void assureExplosions() {
        if (!explosion) {
            explosion = true;
            nbtExplosions = new NBTTagList();
            nbtFireworks.setTag("Explosions", nbtExplosions);
        }
    }

    public int dyeClampLookup(byte colorIndex) {
        if(colorIndex < 0) colorIndex = (byte) -colorIndex; //lets you easily do either direction
        if(colorIndex > 15) colorIndex = 0;
        return ItemDye.field_150922_c[colorIndex];
    }

    public FireworksBuilder withFadeExplosion(byte colorFromIndex, byte colorToIndex, byte pattern) {
        return withFadeExplosion(dyeClampLookup(colorFromIndex), dyeClampLookup(colorToIndex),  pattern);
    }

    public FireworksBuilder withFadeExplosion(int colorFrom, int colorTo, byte pattern) {
        return withFadeExplosion(colorFrom, colorTo, pattern,false, false);
    }

    public FireworksBuilder withFadeExplosion(int colorFrom, int colorTo, byte pattern, boolean twinkle, boolean trail) {
        assureExplosions();
        nbtExplosions.appendTag(addFade(anExplosion(colorFrom, pattern, twinkle, trail), colorTo));
        return this;
    }

    public FireworksBuilder withBang() {
        return withNonfadeExplosion(0, (byte) -1);
    }

    public FireworksBuilder withNonfadeExplosion(byte colorIndex, byte pattern) {
        return withNonfadeExplosion(dyeClampLookup(colorIndex), pattern);
    }

    public FireworksBuilder withNonfadeExplosion(int color, byte pattern) {
        return withNonfadeExplosion(color, pattern, false, false);
    }

    public FireworksBuilder withNonfadeExplosion(int color, byte pattern, boolean twinkle, boolean trail) {
        assureExplosions();
        nbtExplosions.appendTag(anExplosion(color, pattern, twinkle, trail));
        return this;
    }

    public static NBTTagCompound anExplosion(int color, byte pattern, boolean twinkle, boolean trail) {
        return anExplosion(color, color, pattern, twinkle, trail);
    }
    public static NBTTagCompound anExplosion(int color, int color2, byte pattern, boolean twinkle, boolean trail) {
        NBTTagCompound nbt = new NBTTagCompound();
        int[] colors = new int[] {color, color2};

        nbt.setIntArray("Colors", colors);
        nbt.setByte("Type", pattern);
        nbt.setBoolean("Flicker", twinkle);
        nbt.setBoolean("Trail", trail);

        if (Config.modern_redundant_fireworks_nbt) {
            nbt.setIntArray("colors", colors);
            nbt.setString("shape", switch(pattern) {
                case 1 -> "large_ball";
                case 2 -> "star";
                case 3 -> "creeper";
                case 4 -> "burst";
                case -1 -> "concussion";
                default -> "small_ball";
            });
            nbt.setBoolean("has_twinkle", twinkle);
            nbt.setBoolean("has_trail", trail);
        }
        return nbt;
    }

    /**
     * Note that this mod does not itself make fade-color fireworks ...work.
     * @param nbt nbt to add the fade color[s] to
     * @param fadeColor desired fade color
     * @return the nbt, to let use in a function without a temp variable
     */
    public static NBTTagCompound addFade(NBTTagCompound nbt, int fadeColor) {
        return addFade(nbt, fadeColor, fadeColor);
    }
    public static NBTTagCompound addFade(NBTTagCompound nbt, int fadeColor, int fadeColor2) {
        int[] fadeColors = new int[] {fadeColor, fadeColor2};
        nbt.setIntArray("FadeColors", fadeColors);
        if (Config.modern_redundant_fireworks_nbt)
            nbt.setIntArray("fade_colors", fadeColors);
        return nbt;
    }

    // @Override
    public ItemStack build() {
        return stack.copy(); //lt the GC drop the builder
    }
}
