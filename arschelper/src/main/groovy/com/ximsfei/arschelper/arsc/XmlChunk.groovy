package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class XmlChunk extends ChunkWithChunks {
    XmlChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
    }

    @Override
    Type getType() {
        Type.XML
    }
}
