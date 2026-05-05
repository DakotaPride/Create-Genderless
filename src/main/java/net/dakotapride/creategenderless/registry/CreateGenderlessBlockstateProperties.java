package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.block.SilkSpoolBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class CreateGenderlessBlockstateProperties {
    public static final EnumProperty<SilkSpoolBlock.SpoolConnection> SPOOL_CONNECTION = EnumProperty.create("spool_connection", SilkSpoolBlock.SpoolConnection.class);
}
