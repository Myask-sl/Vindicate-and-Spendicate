package invalid.myask.vindicateandspendicate.client;
/*
 * (c) by Myask/K.S. Lillian 2025
 * MIT license = leave the copyright notice, no warranties--this is just a summary, go find the long version yourself
 * Bridge to make BlockBench exports' use of 1.8+only ctor for ModelBox work in 1.7-.
 */
import net.minecraft.client.model.ModelRenderer;

public class ModelBox extends net.minecraft.client.model.ModelBox {
    public ModelBox(ModelRenderer body, int U, int V, float xMin, float yMin, float zMin, int xWidth, int yWidth, int zWidth, float inflate, boolean mirror) {
        super(setMirror(body, mirror), U, V, xMin, yMin, zMin, xWidth, yWidth, zWidth, inflate);
    }

    static ModelRenderer setMirror(ModelRenderer in, boolean mirrorToBe) {
        in.mirror = mirrorToBe;
        return in;
    }
}
