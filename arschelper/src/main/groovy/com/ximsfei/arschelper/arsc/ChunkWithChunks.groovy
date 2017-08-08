package com.ximsfei.arschelper.arsc;

import java.nio.ByteBuffer;

/**
 * Created by ximsfei on 2017/8/8.
 */
abstract class ChunkWithChunks extends Chunk {
    final Map<Integer, Chunk> chunks = new LinkedHashMap<>();

    public ChunkWithChunks(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent);
        chunks.clear();
        int start = this.offset + headerSize
        int offset = start
        int end = this.offset + size
        int position = buffer.position();
        buffer.position(start)
        while (offset < end) {
            Chunk chunk = create(buffer, this)
            chunks.put(offset, chunk)
            offset += chunk.size
        }
        buffer.position(position)
    }
}
