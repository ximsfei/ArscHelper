package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log
import com.ximsfei.arschelper.utils.UnsignedBytes

import java.nio.ByteBuffer

import static java.nio.charset.StandardCharsets.US_ASCII

/** Describes a particular resource configuration. */
class ResourceConfiguration {

    /** The different types of configs that can be present in a {@link ResourceConfiguration}. */
    enum ConfigType {
        MCC,
        MNC,
        LANGUAGE_STRING,
        REGION_STRING,
        SCREEN_LAYOUT_DIRECTION,
        SMALLEST_SCREEN_WIDTH_DP,
        SCREEN_WIDTH_DP,
        SCREEN_HEIGHT_DP,
        SCREEN_LAYOUT_SIZE,
        SCREEN_LAYOUT_LONG,
        SCREEN_LAYOUT_ROUND,
        ORIENTATION,
        UI_MODE_TYPE,
        UI_MODE_NIGHT,
        DENSITY_DPI,
        TOUCHSCREEN,
        KEYBOARD_HIDDEN,
        KEYBOARD,
        NAVIGATION_HIDDEN,
        NAVIGATION,
        SDK_VERSION
    }

    /** The below constants are from android.content.res.Configuration. */
    private static final int DENSITY_DPI_UNDEFINED = 0
    private static final int DENSITY_DPI_LDPI = 120
    private static final int DENSITY_DPI_MDPI = 160
    private static final int DENSITY_DPI_TVDPI = 213
    private static final int DENSITY_DPI_HDPI = 240
    private static final int DENSITY_DPI_XHDPI = 320
    private static final int DENSITY_DPI_XXHDPI = 480
    private static final int DENSITY_DPI_XXXHDPI = 640
    private static final int DENSITY_DPI_ANY = 0xFFFE
    private static final int DENSITY_DPI_NONE = 0xFFFF
    private static final Map<Integer, String> DENSITY_DPI_VALUES = new HashMap<>()
    static {
        DENSITY_DPI_VALUES.put(DENSITY_DPI_UNDEFINED, "")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_LDPI, "ldpi")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_MDPI, "mdpi")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_TVDPI, "tvdpi")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_HDPI, "hdpi")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_XHDPI, "xhdpi")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_XXHDPI, "xxhdpi")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_XXXHDPI, "xxxhdpi")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_ANY, "anydpi")
        DENSITY_DPI_VALUES.put(DENSITY_DPI_NONE, "nodpi")
    }

    private static final int KEYBOARD_NOKEYS = 1
    private static final int KEYBOARD_QWERTY = 2
    private static final int KEYBOARD_12KEY = 3
    private static final Map<Integer, String> KEYBOARD_VALUES = new HashMap<>()
    static {
        KEYBOARD_VALUES.put(KEYBOARD_NOKEYS, "nokeys")
        KEYBOARD_VALUES.put(KEYBOARD_QWERTY, "qwerty")
        KEYBOARD_VALUES.put(KEYBOARD_12KEY, "12key")
    }

    private static final int KEYBOARDHIDDEN_MASK = 0x03
    private static final int KEYBOARDHIDDEN_NO = 1
    private static final int KEYBOARDHIDDEN_YES = 2
    private static final int KEYBOARDHIDDEN_SOFT = 3
    private static final Map<Integer, String> KEYBOARDHIDDEN_VALUES = new HashMap<>()
    static {
        KEYBOARDHIDDEN_VALUES.put(KEYBOARDHIDDEN_NO, "keysexposed")
        KEYBOARDHIDDEN_VALUES.put(KEYBOARDHIDDEN_YES, "keyshidden")
        KEYBOARDHIDDEN_VALUES.put(KEYBOARDHIDDEN_SOFT, "keyssoft")
    }

    private static final int NAVIGATION_NONAV = 1
    private static final int NAVIGATION_DPAD = 2
    private static final int NAVIGATION_TRACKBALL = 3
    private static final int NAVIGATION_WHEEL = 4
    private static final Map<Integer, String> NAVIGATION_VALUES = new HashMap<>()
    static {
        NAVIGATION_VALUES.put(NAVIGATION_NONAV, "nonav")
        NAVIGATION_VALUES.put(NAVIGATION_DPAD, "dpad")
        NAVIGATION_VALUES.put(NAVIGATION_TRACKBALL, "trackball")
        NAVIGATION_VALUES.put(NAVIGATION_WHEEL, "wheel")
    }

    private static final int NAVIGATIONHIDDEN_MASK = 0x0C
    private static final int NAVIGATIONHIDDEN_NO = 0x04
    private static final int NAVIGATIONHIDDEN_YES = 0x08
    private static final Map<Integer, String> NAVIGATIONHIDDEN_VALUES = new HashMap<>()
    static {
        NAVIGATIONHIDDEN_VALUES.put(NAVIGATIONHIDDEN_NO, "navexposed")
        NAVIGATIONHIDDEN_VALUES.put(NAVIGATIONHIDDEN_YES, "navhidden")
    }

    private static final int ORIENTATION_PORTRAIT = 0x01
    private static final int ORIENTATION_LANDSCAPE = 0x02
    private static final Map<Integer, String> ORIENTATION_VALUES = new HashMap()
    static {
        ORIENTATION_VALUES.put(ORIENTATION_PORTRAIT, "port")
        ORIENTATION_VALUES.put(ORIENTATION_LANDSCAPE, "land")
    }

    private static final int SCREENLAYOUT_LAYOUTDIR_MASK = 0xC0
    private static final int SCREENLAYOUT_LAYOUTDIR_LTR = 0x40
    private static final int SCREENLAYOUT_LAYOUTDIR_RTL = 0x80
    private static final Map<Integer, String> SCREENLAYOUT_LAYOUTDIR_VALUES = new HashMap()
    static {
        SCREENLAYOUT_LAYOUTDIR_VALUES.put(SCREENLAYOUT_LAYOUTDIR_LTR, "ldltr")
        SCREENLAYOUT_LAYOUTDIR_VALUES.put(SCREENLAYOUT_LAYOUTDIR_RTL, "ldrtl")
    }

    private static final int SCREENLAYOUT_LONG_MASK = 0x30
    private static final int SCREENLAYOUT_LONG_NO = 0x10
    private static final int SCREENLAYOUT_LONG_YES = 0x20
    private static final Map<Integer, String> SCREENLAYOUT_LONG_VALUES = new HashMap()
    static {
        SCREENLAYOUT_LONG_VALUES.put(SCREENLAYOUT_LONG_NO, "notlong",)
        SCREENLAYOUT_LONG_VALUES.put(SCREENLAYOUT_LONG_YES, "long")
    }

    private static final int SCREENLAYOUT_ROUND_MASK = 0x0300
    private static final int SCREENLAYOUT_ROUND_NO = 0x0100
    private static final int SCREENLAYOUT_ROUND_YES = 0x0200
    private static final Map<Integer, String> SCREENLAYOUT_ROUND_VALUES = new HashMap()
    static {
        SCREENLAYOUT_ROUND_VALUES.put(SCREENLAYOUT_ROUND_NO, "notround")
        SCREENLAYOUT_ROUND_VALUES.put(SCREENLAYOUT_ROUND_YES, "round")
    }

    private static final int SCREENLAYOUT_SIZE_MASK = 0x0F
    private static final int SCREENLAYOUT_SIZE_SMALL = 0x01
    private static final int SCREENLAYOUT_SIZE_NORMAL = 0x02
    private static final int SCREENLAYOUT_SIZE_LARGE = 0x03
    private static final int SCREENLAYOUT_SIZE_XLARGE = 0x04
    private static final Map<Integer, String> SCREENLAYOUT_SIZE_VALUES = new HashMap()
    static {
        SCREENLAYOUT_SIZE_VALUES.put(SCREENLAYOUT_SIZE_SMALL, "small")
        SCREENLAYOUT_SIZE_VALUES.put(SCREENLAYOUT_SIZE_NORMAL, "normal")
        SCREENLAYOUT_SIZE_VALUES.put(SCREENLAYOUT_SIZE_LARGE, "large")
        SCREENLAYOUT_SIZE_VALUES.put(SCREENLAYOUT_SIZE_XLARGE, "xlarge")
    }

    private static final int TOUCHSCREEN_NOTOUCH = 1
    private static final int TOUCHSCREEN_FINGER = 3
    private static final Map<Integer, String> TOUCHSCREEN_VALUES = new HashMap()
    static {
        TOUCHSCREEN_VALUES.put(TOUCHSCREEN_NOTOUCH, "notouch")
        TOUCHSCREEN_VALUES.put(TOUCHSCREEN_FINGER, "finger")
    }

    private static final int UI_MODE_NIGHT_MASK = 0x30
    private static final int UI_MODE_NIGHT_NO = 0x10
    private static final int UI_MODE_NIGHT_YES = 0x20
    private static final Map<Integer, String> UI_MODE_NIGHT_VALUES = new HashMap()
    static {
        UI_MODE_NIGHT_VALUES.put(UI_MODE_NIGHT_NO, "notnight")
        UI_MODE_NIGHT_VALUES.put(UI_MODE_NIGHT_YES, "night")
    }

    private static final int UI_MODE_TYPE_MASK = 0x0F
    private static final int UI_MODE_TYPE_DESK = 0x02
    private static final int UI_MODE_TYPE_CAR = 0x03
    private static final int UI_MODE_TYPE_TELEVISION = 0x04
    private static final int UI_MODE_TYPE_APPLIANCE = 0x05
    private static final int UI_MODE_TYPE_WATCH = 0x06
    private static final Map<Integer, String> UI_MODE_TYPE_VALUES = new HashMap()
    static {
        UI_MODE_TYPE_VALUES.put(UI_MODE_TYPE_DESK, "desk")
        UI_MODE_TYPE_VALUES.put(UI_MODE_TYPE_CAR, "car")
        UI_MODE_TYPE_VALUES.put(UI_MODE_TYPE_TELEVISION, "television")
        UI_MODE_TYPE_VALUES.put(UI_MODE_TYPE_APPLIANCE, "appliance")
        UI_MODE_TYPE_VALUES.put(UI_MODE_TYPE_WATCH, "watch")
    }

    /** The minimum size in bytes that this configuration must be to contain screen config info. */
    private static final int SCREEN_CONFIG_MIN_SIZE = 32

    /** The minimum size in bytes that this configuration must be to contain screen dp info. */
    private static final int SCREEN_DP_MIN_SIZE = 36

    /** The minimum size in bytes that this configuration must be to contain locale info. */
    private static final int LOCALE_MIN_SIZE = 48

    /** The minimum size in bytes that this config must be to contain the screenConfig extension. */
    private static final int SCREEN_CONFIG_EXTENSION_MIN_SIZE = 52

    /** The number of bytes that this resource configuration takes up. */
    private int size

    private int mcc
    private int mnc

    /** Returns a packed 2-byte language code. */
    @SuppressWarnings("mutable")
    private byte[] language

    /** Returns {@link #language} as an unpacked string representation. */
    final String languageString() {
        unpackLanguage()
    }

    /** Returns a packed 2-byte region code. */
    @SuppressWarnings("mutable")
    private byte[] region

    /** Returns {@link #region} as an unpacked string representation. */
    final String regionString() {
        unpackRegion()
    }

    private int orientation
    private int touchscreen
    private int density
    private int keyboard
    private int navigation
    private int inputFlags

    final int keyboardHidden() {
        inputFlags & KEYBOARDHIDDEN_MASK
    }

    final int navigationHidden() {
        inputFlags & NAVIGATIONHIDDEN_MASK
    }

    private int screenWidth
    private int screenHeight
    private int sdkVersion

    ResourceConfiguration(int size, int mcc, int mnc, byte[] language, byte[] region,
                          int orientation, int touchscreen, int density, int keyboard, int navigation, int inputFlags,
                          int screenWidth, int screenHeight, int sdkVersion, int minorVersion, int screenLayout, int uiMode,
                          int smallestScreenWidthDp, int screenWidthDp, int screenHeightDp, byte[] localeScript, byte[] localeVariant,
                          int screenLayout2, byte[] unknown) {
        this.size = size
        this.mcc = mcc
        this.mnc = mnc
        this.language = language
        this.region = region
        this.orientation = orientation
        this.touchscreen = touchscreen
        this.density = density
        this.keyboard = keyboard
        this.navigation = navigation
        this.inputFlags = inputFlags
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
        this.sdkVersion = sdkVersion
        this.minorVersion = minorVersion
        this.screenLayout = screenLayout
        this.uiMode = uiMode
        this.smallestScreenWidthDp = smallestScreenWidthDp
        this.screenWidthDp = screenWidthDp
        this.screenHeightDp = screenHeightDp
        this.localeScript = localeScript
        this.localeVariant = localeVariant
        this.screenLayout2 = screenLayout2
        this.unknown = unknown
    }

    /**
     * Returns a copy of this resource configuration with a different {@link #sdkVersion}, or this
     * configuration if the {@code sdkVersion} is the same.
     *
     * @param sdkVersion The SDK version of the returned configuration.
     * @A copy of this configuration with the only difference being #sdkVersion.
     */
    final ResourceConfiguration withSdkVersion(int sdkVersion) {
        if (this.sdkVersion == sdkVersion) {
            this
        }
        new ResourceConfiguration(size, mcc, mnc, language, region,
                orientation, touchscreen, density, keyboard, navigation, inputFlags,
                screenWidth, screenHeight, sdkVersion, minorVersion, screenLayout, uiMode,
                smallestScreenWidthDp, screenWidthDp, screenHeightDp, localeScript, localeVariant,
                screenLayout2, unknown)
    }

    private int minorVersion
    private int screenLayout

    final int screenLayoutDirection() {
        screenLayout & SCREENLAYOUT_LAYOUTDIR_MASK
    }

    final int screenLayoutSize() {
        screenLayout & SCREENLAYOUT_SIZE_MASK
    }

    final int screenLayoutLong() {
        screenLayout & SCREENLAYOUT_LONG_MASK
    }

    final int screenLayoutRound() {
        screenLayout & SCREENLAYOUT_ROUND_MASK
    }

    private int uiMode

    final int uiModeType() {
        uiMode & UI_MODE_TYPE_MASK
    }

    final int uiModeNight() {
        uiMode & UI_MODE_NIGHT_MASK
    }

    private int smallestScreenWidthDp
    private int screenWidthDp
    private int screenHeightDp

    /** The ISO-15924 short name for the script corresponding to this configuration. */
    @SuppressWarnings("mutable")
    private byte[] localeScript

    /** A single BCP-47 variant subtag. */
    @SuppressWarnings("mutable")
    private byte[] localeVariant

    /** An extension to {@link #screenLayout}. Contains round/notround qualifier. */
    private int screenLayout2

    /** Any remaining bytes in this resource configuration that are unaccounted for. */
    @SuppressWarnings("mutable")
    private byte[] unknown

    static ResourceConfiguration create(ByteBuffer buffer) {
        int startPosition = buffer.position()  // The starting buffer position to calculate bytes read.
        int size = buffer.getInt()
        int mcc = buffer.getShort() & 0xFFFF
        int mnc = buffer.getShort() & 0xFFFF
        byte[] language = new byte[2]
        buffer.get(language)
        byte[] region = new byte[2]
        buffer.get(region)
        int orientation = UnsignedBytes.toInt(buffer.get())
        int touchscreen = UnsignedBytes.toInt(buffer.get())
        int density = buffer.getShort() & 0xFFFF
        int keyboard = UnsignedBytes.toInt(buffer.get())
        int navigation = UnsignedBytes.toInt(buffer.get())
        int inputFlags = UnsignedBytes.toInt(buffer.get())
        buffer.get()  // 1 byte of padding
        int screenWidth = buffer.getShort() & 0xFFFF
        int screenHeight = buffer.getShort() & 0xFFFF
        int sdkVersion = buffer.getShort() & 0xFFFF
        int minorVersion = buffer.getShort() & 0xFFFF

        // At this point, the configuration's size needs to be taken into account as not all
        // configurations have all values.
        int screenLayout = 0
        int uiMode = 0
        int smallestScreenWidthDp = 0
        int screenWidthDp = 0
        int screenHeightDp = 0
        byte[] localeScript = new byte[4]
        byte[] localeVariant = new byte[8]
        int screenLayout2 = 0

        if (size >= SCREEN_CONFIG_MIN_SIZE) {
            screenLayout = UnsignedBytes.toInt(buffer.get())
            uiMode = UnsignedBytes.toInt(buffer.get())
            smallestScreenWidthDp = buffer.getShort() & 0xFFFF
        }

        if (size >= SCREEN_DP_MIN_SIZE) {
            screenWidthDp = buffer.getShort() & 0xFFFF
            screenHeightDp = buffer.getShort() & 0xFFFF
        }

        if (size >= LOCALE_MIN_SIZE) {
            buffer.get(localeScript)
            buffer.get(localeVariant)
        }

        if (size >= SCREEN_CONFIG_EXTENSION_MIN_SIZE) {
            screenLayout2 = UnsignedBytes.toInt(buffer.get())
            buffer.get()  // Reserved padding
            buffer.getShort()  // More reserved padding
        }

        // After parsing everything that's known, account for anything that's unknown.
        int bytesRead = buffer.position() - startPosition
        byte[] unknown = new byte[size - bytesRead]
        buffer.get(unknown)

        new ResourceConfiguration(size, mcc, mnc, language, region, orientation,
                touchscreen, density, keyboard, navigation, inputFlags, screenWidth, screenHeight,
                sdkVersion, minorVersion, screenLayout, uiMode, smallestScreenWidthDp, screenWidthDp,
                screenHeightDp, localeScript, localeVariant, screenLayout2, unknown)
    }

    private String unpackLanguage() {
        unpackLanguageOrRegion(language, 0x61)
    }

    private String unpackRegion() {
        unpackLanguageOrRegion(region, 0x30)
    }

    private String unpackLanguageOrRegion(byte[] value, int base) {
//        Preconditions.checkState(value.length == 2, "Language or region value must be 2 bytes.")
        if (value[0] == 0 && value[1] == 0) {
            ""
        }
        if ((UnsignedBytes.toInt(value[0]) & 0x80) != 0) {
            byte[] result = new byte[3]
            result[0] = (byte) (base + (value[1] & 0x1F))
            result[1] = (byte) (base + ((value[1] & 0xE0) >>> 5) + ((value[0] & 0x03) << 3))
            result[2] = (byte) (base + ((value[0] & 0x7C) >>> 2))
            new String(result, US_ASCII)
        }
        new String(value, US_ASCII)
    }

    /** Returns true if this is the default "any" configuration. */
    final boolean isDefault() {
        mcc == 0 && mnc == 0 && Arrays.equals(language, new byte[2]) && Arrays.equals(region, new byte[2]) && orientation == 0 && touchscreen == 0 && density == 0 && keyboard == 0 && navigation == 0 && inputFlags == 0 && screenWidth == 0 && screenHeight == 0 && sdkVersion == 0 && minorVersion == 0 && screenLayout == 0 && uiMode == 0 && smallestScreenWidthDp == 0 && screenWidthDp == 0 && screenHeightDp == 0 && Arrays.equals(localeScript, new byte[4]) && Arrays.equals(localeVariant, new byte[8]) && screenLayout2 == 0
    }
}
