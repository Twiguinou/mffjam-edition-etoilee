package com.twihick.bunlyu.common.state;

import com.twihick.bunlyu.common.blocks.parts.LaunchPadPart;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class CustomProperties {

    /* BOOLEAN PROPERTIES */
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    /* ENUM PROPERTIES */
    public static final EnumProperty<LaunchPadPart> PART = EnumProperty.create("part", LaunchPadPart.class);

    /* MISC */
    public static final DirectionProperty ALIGNMENT = BlockStateProperties.HORIZONTAL_FACING;

}
