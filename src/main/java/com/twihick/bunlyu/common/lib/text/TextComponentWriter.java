package com.twihick.bunlyu.common.lib.text;

import com.twihick.bunlyu.Main;
import net.minecraft.util.text.TranslationTextComponent;

public final class TextComponentWriter {

    public static TranslationTextComponent getTranslated(EntryType type, String name) {
        return new TranslationTextComponent(type.getPrefix() + Main.ID + "." + name);
    }

}
