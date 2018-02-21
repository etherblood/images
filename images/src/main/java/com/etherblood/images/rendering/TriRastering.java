package com.etherblood.images.rendering;

import com.etherblood.images.rendering.shaders.Shader;
import com.etherblood.images.rendering.blending.Blender;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class TriRastering {

    private final Shader shader;
    private final Blender blender;
    private final int[] indices;
    private final Vector3f[] vertices;
    private final Vector3f[] textureCoords;
    private final IntSetSparse containerTris = new IntSetSparse(100);

    public TriRastering(Shader shader, Blender blender, VertexBuffers buffers) {
        this.shader = shader;
        this.blender = blender;
        indices = buffers.get(BufferType.INDEX, int[].class).get();
        vertices = buffers.get(BufferType.VERTEX, Vector3f[].class).get();
        textureCoords = buffers.get(BufferType.TEXTURE, Vector3f[].class).orElse(null);
    }

    public void rasterTo(Vector4fImage output) {
        for (int y = 0; y < output.height(); y++) {
            List<Vector3f> bounds = new ArrayList<>();
            for (int i = 0; i < indices.length; i += 3) {
                Vector3f v = new Vector3f();
                if (intersections(y, v, i)) {
                    v.z = i;
                    bounds.add(v);
                }
            }
            for (int x = 0; x < output.width(); x++) {
                containerTris.clear();
                for (Vector3f bound : bounds) {
                    if (bound.x <= x && x < bound.y) {
                        containerTris.toggle((int) bound.z);
                    }
                }

                //TODO: rendering back to front, skip hidden pixels
                for (int i = 0; i < containerTris.size(); i++) {
                    int triIndex = containerTris.get(i);
                    Vector3f a = vertices[indices[triIndex]];
                    Vector3f b = vertices[indices[triIndex + 1]];
                    Vector3f c = vertices[indices[triIndex + 2]];
                    float det = (b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y);
                    float num0 = (b.y - c.y) * (x - c.x) + (c.x - b.x) * (y - c.y);
                    float num1 = (c.y - a.y) * (x - c.x) + (a.x - c.x) * (y - c.y);
                    float num2 = (a.y - b.y) * (x - c.x) + (b.x - a.x) * (y - c.y);
                    num0 /= det;
                    num1 /= det;
                    num2 /= det;
                    assert 0 <= num0 && num0 <= 1;
                    assert 0 <= num1 && num1 <= 1;
                    assert 0 <= num2 && num2 <= 1;
                    assert Math.abs((1 - num0 - num1) - num2) < 0.00001f;
                    num2 = 1 - num0 - num1;
                    Vector3f barycentric = new Vector3f(num0, num1, num2);
                    float z = num0 * a.z + num1 * b.z + num2 * c.z;

                    //TODO: cull pixels that would be drawn behind the canvas as soon as we have a depth buffer
                    
                    Vector3f tex = null;
                    if (textureCoords != null) {
                        Vector3f texA = textureCoords[indices[triIndex]];
                        Vector3f texB = textureCoords[indices[triIndex + 1]];
                        Vector3f texC = textureCoords[indices[triIndex + 2]];
                        tex = new Vector3f(
                                num0 * texA.x + num1 * texB.x + num2 * texC.x,
                                num0 * texA.y + num1 * texB.y + num2 * texC.y,
                                num0 * texA.z + num1 * texB.z + num2 * texC.z);
                    }
                    Vector4f resultColor = new Vector4f();
                    shader.process(new Vector3f(x, y, z), null, null, tex, resultColor);
                    Vector4f outputVertex = output.get(x, y);
                    blender.blend(resultColor, outputVertex, outputVertex);
                }
            }
        }
    }

    private boolean intersections(float y, Vector3f out, int triIndex) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            int j = (i + 1) % 3;

            Vector3f a = vertices[indices[triIndex + i]];
            Vector3f b = vertices[indices[triIndex + j]];
            if (a.y == b.y) {
                continue;
            }
            // TODO: verify commented code below is not required
//            if(b.y < a.y) {
//                Vector3f tmp = a;
//                a = b;
//                b= tmp;
//            }
            float t = (y - a.y) / (b.y - a.y);
            if (t < 0 || 1 <= t) {
                continue;
            }
            float x = a.x + t * (b.x - a.x);
            if (count != 0) {
                if (x < out.x) {
                    out.y = out.x;
                    out.x = x;
                } else {
                    out.y = x;
                }
            } else {
                out.x = x;
            }
            count++;
        }
        assert (count & 1) == 0;
        return count == 2;
    }
}
