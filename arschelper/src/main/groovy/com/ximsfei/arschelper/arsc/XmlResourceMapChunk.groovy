package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class XmlResourceMapChunk extends Chunk {
    private static final int RESOURCE_SIZE = 4
    List<Integer> resources = new ArrayList<>()

    XmlResourceMapChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
    }

    @Override
    def init(ByteBuffer buffer) {
        int resourceCount = (size - headerSize) / RESOURCE_SIZE
        int offset = this.offset + headerSize

        buffer.mark()
        buffer.position(offset)

        for (int i = 0; i < resourceCount; i++) {
            resources.add(buffer.getInt())
        }
        for (i in resources.indices) {
            println("resource id = " + Integer.toHexString(resources.get(i)))
        }
        buffer.reset()
    }

    @Override
    Type getType() {
        Type.XML_RESOURCE_MAP
    }
}
