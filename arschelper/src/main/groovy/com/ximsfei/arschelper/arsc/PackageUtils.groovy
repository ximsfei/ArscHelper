package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer
import java.nio.charset.Charset

final class PackageUtils {

  public static final int PACKAGE_NAME_SIZE = 256

  private PackageUtils() {}

  static String readPackageName(ByteBuffer buffer, int offset) {
    Charset utf16 = Charset.forName("UTF-16LE")
    String str = new String(buffer.array(), offset, PACKAGE_NAME_SIZE, utf16)
    buffer.position(offset + PACKAGE_NAME_SIZE)
    return str
  }
}
