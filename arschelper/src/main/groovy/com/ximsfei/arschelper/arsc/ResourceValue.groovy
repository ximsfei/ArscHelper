package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

import static com.ximsfei.arschelper.arsc.ResourceValue.ResourceType.*

/**
 * Created by ximsfei on 2017/8/11.
 */
class ResourceValue {
    enum ResourceType {
        NULL(0x00),
        REFERENCE(0x01),
        ATTRIBUTE(0x02),
        STRING(0x03),
        FLOAT(0x04),
        DIMENSION(0x05),
        FRACTION(0x06),
        DYNAMIC_REFERENCE(0x07),
        INT_DEC(0x10),
        INT_HEX(0x11),
        INT_BOOLEAN(0x12),
        INT_COLOR_ARGB8(0x1c),
        INT_COLOR_RGB8(0x1d),
        INT_COLOR_ARGB4(0x1e),
        INT_COLOR_RGB4(0x1f)

        final byte type

        static final Map<Byte, ResourceType> FROM_BYTE

        static {
            FROM_BYTE = new HashMap<>()
            for (ResourceType type : values()) {
                FROM_BYTE.put(type.get(), type)
            }
        }

        ResourceType(int type) {
            this.type = type
        }

        byte get() {
            type
        }

        static ResourceType get(byte type) {
            FROM_BYTE.get(type)
        }
    }
    static final int SIZE = 8
    int size
    ResourceType type
    int data

    ResourceValue(int size, ResourceType type, int data) {
        this.size = size
        this.type = type
        this.data = data
    }

    static ResourceValue create(ByteBuffer buffer) {
        int size = (buffer.getShort() & 0xFFFF)
        buffer.get()  // Unused
        ResourceType type = get(buffer.get())
        int data = buffer.getInt()

        new ResourceValue(size, type, data)
    }
}
