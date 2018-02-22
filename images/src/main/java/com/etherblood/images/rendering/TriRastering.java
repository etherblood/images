package com.etherblood.images.rendering;

import com.etherblood.images.processing.FloatChannel;
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
    private final Vector3f[] normals;
    private final Vector4f[] colors;
    private final IntSetSparse containerTris = new IntSetSparse(100);

    public TriRastering(Shader shader, Blender blender, VertexBuffers buffers) {
        this.shader = shader;
        this.blender = blender;
        this.indices = buffers.get(BufferType.INDEX, int[].class).get();
        this.vertices = buffers.get(BufferType.POSITION, Vector3f[].class).get();
        this.textureCoords = buffers.get(BufferType.TEXTURE, Vector3f[].class).orElse(null);
        this.normals = buffers.get(BufferType.NORMAL, Vector3f[].class).orElse(null);
        this.colors = buffers.get(BufferType.NORMAL, Vector4f[].class).orElse(null);
    }

    public void raster(FloatChannel depth, Vector4fImage output) {
        for (int y = 0; y < output.height(); y++) {
            //TODO: update active triangles incrementally
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
                    float z = num0 * a.z + num1 * b.z + num2 * c.z;//TODO: often z bounds (minZ, maxZ) will suffice, compute barycentric & z lazily

                    //TODO: save data to collection and do remaining stuff in separate loop after ordering and filtering by z
                    
                    //TODO: cull pixels that would be drawn behind the canvas as soon as we have a depth buffer
                    Vector3f tex = null;
                    if (textureCoords != null) {
                        Vector3f texA = textureCoords[indices[triIndex]];
                        Vector3f texB = textureCoords[indices[triIndex + 1]];
                        Vector3f texC = textureCoords[indices[triIndex + 2]];
                        tex = fromBarycentric(num0, texA, num1, texB, num2, texC, new Vector3f());
                    }
                    Vector3f normal = null;
                    if (normals != null) {
                        Vector3f normA = normals[indices[triIndex]];
                        Vector3f normB = normals[indices[triIndex + 1]];
                        Vector3f normC = normals[indices[triIndex + 2]];
                        normal = fromBarycentric(num0, normA, num1, normB, num2, normC, new Vector3f());
                    }
                    Vector4f color = null;
                    if (colors != null) {
                        Vector4f colorA = colors[indices[triIndex]];
                        Vector4f colorB = colors[indices[triIndex + 1]];
                        Vector4f colorC = colors[indices[triIndex + 2]];
                        color = fromBarycentric(num0, colorA, num1, colorB, num2, colorC, new Vector4f());
                    }
                    Vector4f resultColor = new Vector4f();
                    shader.process(new Vector3f(x, y, z), normal, color, tex, resultColor);
                    Vector4f outputVertex = output.get(x, y);
                    blender.blend(resultColor, outputVertex, outputVertex);
                }
            }
        }
    }

    private static Vector3f fromBarycentric(float weight0, Vector3f v0, float weight1, Vector3f v1, float weight2, Vector3f v2, Vector3f output) {
        return output.set(
                weight0 * v0.x + weight1 * v1.x + weight2 * v2.x,
                weight0 * v0.y + weight1 * v1.y + weight2 * v2.y,
                weight0 * v0.z + weight1 * v1.z + weight2 * v2.z);
    }

    private static Vector4f fromBarycentric(float weight0, Vector4f v0, float weight1, Vector4f v1, float weight2, Vector4f v2, Vector4f output) {
        return output.set(
                weight0 * v0.x + weight1 * v1.x + weight2 * v2.x,
                weight0 * v0.y + weight1 * v1.y + weight2 * v2.y,
                weight0 * v0.z + weight1 * v1.z + weight2 * v2.z,
                weight0 * v0.w + weight1 * v1.w + weight2 * v2.w);
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
