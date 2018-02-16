package com.etherblood.images;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class FloatImage {

    private final Map<ChannelType, FloatChannel> channels = new EnumMap<>(ChannelType.class);
    private final int width, height;

    public FloatImage(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public FloatChannel getChannel(ChannelType type) {
        return channels.get(type);
    }
    
    public void setChannel(ChannelType type, FloatChannel channel) {
        if(channel.width() != width || channel.height() != height) {
            throw new IllegalArgumentException("channel must have same dimensions as image");
        }
        channels.put(type, channel);
    }
    
    public Set<ChannelType> getChannelKeys() {
        return channels.keySet();
    }
    
    public int width() {
        return width;
    }
    
    public int height() {
        return height;
    }
}
