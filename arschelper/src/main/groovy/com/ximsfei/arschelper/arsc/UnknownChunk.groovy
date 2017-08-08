package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class UnknownChunk extends Chunk {

    final Type type;

    final byte[] header;

    final byte[] payload;

    UnknownChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        type = Type.get(buffer.getShort(offset));
        header = new byte[headerSize - SIZE];
        payload = new byte[size - headerSize];
        buffer.get(header);
        buffer.get(payload);
    }
}
