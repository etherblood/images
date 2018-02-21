package com.etherblood.images.rendering;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class VertexBuffers {

    private final Map<BufferType, Object> buffers = new EnumMap<>(BufferType.class);

    @SuppressWarnings("unchecked")//false warning
    public <T> Optional<T> get(BufferType bufferType, Class<T> bufferClass) {
        Object buffer = buffers.get(bufferType);
        if (bufferClass.isInstance(buffer)) {
            return Optional.of((T) buffer);
        }
        return Optional.empty();
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getRaw(BufferType bufferType) {
        return (T) buffers.get(bufferType);
    }

    public void set(BufferType type, Object buffer) {
        buffers.put(type, buffer);
    }
    
    public VertexBuffers withBuffer(BufferType type, Object buffer) {
        set(type, buffer);
        return this;
    }

    public Set<BufferType> keys() {
        return buffers.keySet();
    }
}
