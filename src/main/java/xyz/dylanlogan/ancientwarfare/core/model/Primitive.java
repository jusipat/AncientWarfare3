package xyz.dylanlogan.ancientwarfare.core.model;

import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Primitive {

    private float tx;//texture offsets, in texture space (0->1)
    private float ty;
    float x, y, z;//origin of this primitive, relative to parent origin and orientation
    float rx, ry, rz;//rotation of this primitive, relative to parent orientation
    public ModelPiece parent;

    public Primitive(ModelPiece parent) {
        this.parent = parent;
    }

    public final void render(float tw, float th) {
        buildDisplayList(tw, th);
    }

    protected void buildDisplayList(float tw, float th) {
        GL11.glPushMatrix();
        if (x != 0 || y != 0 || z != 0) {
            GL11.glTranslatef(x, y, z);
        }
        if (rx != 0) {
            GL11.glRotatef(rx, 1, 0, 0);
        }
        if (ry != 0) {
            GL11.glRotatef(ry, 0, 1, 0);
        }
        if (rz != 0) {
            GL11.glRotatef(rz, 0, 0, 1);
        }
        renderPrimitive(tw, th);
        GL11.glPopMatrix();
    }

    protected abstract void renderPrimitive(float tw, float th);

    public abstract Primitive copy();

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public float rx() {
        return rx;
    }

    public float ry() {
        return ry;
    }

    public float rz() {
        return rz;
    }

    public float tx() {
        return tx;
    }

    public float ty() {
        return ty;
    }

    public void setOrigin(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(float rx, float ry, float rz) {
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
    }

    public void setTx(float tx) {
        if (tx < 0) {
            tx = 0;
        }
        this.tx = tx;
    }

    public void setTy(float ty) {
        if (ty < 0) {
            ty = 0;
        }
        this.ty = ty;
    }

    public abstract void addPrimitiveLines(ArrayList<String> lines);

    public abstract void readFromLine(String[] lineBits);

    public abstract void addUVMapToImage(BufferedImage image);

    protected void setImagePixel(BufferedImage image, int x, int y, int rgb) {
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
            image.setRGB(x, y, rgb);
        }
    }

}
