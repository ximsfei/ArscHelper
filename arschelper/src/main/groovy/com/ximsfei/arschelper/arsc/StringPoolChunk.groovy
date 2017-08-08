package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log

import java.nio.ByteBuffer

/**
 * Created by ximsfei on 2017/8/8.
 */
class StringPoolChunk extends Chunk {

    enum Flags {
        SORTED_FLAG(1 << 0),
        UTF8_FLAG(1 << 8)

        int flag
        static final Map<Integer, Flags> FLAGS_MAP
        static {
            FLAGS_MAP = new HashMap<>()
            for (Flags flag : values()) {
                FLAGS_MAP.put(flag.flag, flag)
            }
        }

        Flags(int flag) {
            this.flag = flag
        }

        static Flags get(int flag) {
            FLAGS_MAP.get(flag)
        }
    }
    final int stringCount
    final int styleCount
    final Flags flags
    final int stringsStart
    final int stylesStart

    StringPoolChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        Log.d("StringPoolChunk")
        stringCount = buffer.getInt()
        Log.d("stringCount", stringCount as String)
        styleCount = buffer.getInt()
        Log.d("styleCount", styleCount as String)
        flags = Flags.get(buffer.getInt())
        Log.d("flags", flags as String)
        stringsStart = buffer.getInt()
        Log.d("stringsStart", stringsStart as String)
        stylesStart = buffer.getInt()
        Log.d("stylesStart", stylesStart as String)
    }

    @Override
    Type getType() {
        Type.STRING_POOL
    }
}
