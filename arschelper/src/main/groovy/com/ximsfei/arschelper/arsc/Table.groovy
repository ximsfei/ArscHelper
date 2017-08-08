package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log

import java.nio.ByteBuffer

class Table {
    ChunkHeader header
    TabRef tabRef

    static def Table parse(ByteBuffer dexBuffer) {
        Log.d("Table parse")
        Table tabHeader = new Table()
        tabHeader.header = ChunkHeader.parse(dexBuffer)
        tabHeader.tabRef = TabRef.parse(dexBuffer)
        tabHeader
    }

    static class TabRef {
        int ident

        static def TabRef parse(ByteBuffer dexBuffer) {
            TabRef tabRef = new TabRef()
            tabRef.ident = dexBuffer.getInt()
            tabRef
        }
    }
}
