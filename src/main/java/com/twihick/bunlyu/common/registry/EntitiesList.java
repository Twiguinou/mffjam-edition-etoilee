package com.twihick.bunlyu.common.registry;

import com.twihick.bunlyu.Main;
import com.twihick.bunlyu.common.entities.rocket.*;
import com.twihick.bunlyu.common.items.RocketPartItem;
import com.twihick.bunlyu.common.lib.TypeHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = Main.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitiesList {

    private static final Set<EntityType<?>> ENTITIES = new HashSet<>();

    public static final EntityType<EngineSmall> ENGINE_SMALL = buildRocketPart(EngineSmall::new, "engine_small");
    public static final EntityType<FuelTankSmall> FUEL_TANK_SMALL = buildRocketPart(FuelTankSmall::new, "fuel_tank_small");
    public static final EntityType<CapsuleSmall> CAPSULE_SMALL = buildRocketPart(CapsuleSmall::new, "capsule_small");
    public static final EntityType<ParachuteSmall> PARACHUTE_SMALL = buildRocketPart(ParachuteSmall::new, "parachute_small");

    private static <T extends Entity> EntityType<T> buildEntity(BiFunction<EntityType<T>, World, T> function, IAssemblyType properties, String label) {
        Vec2f size = properties.getSize();
        // EntityType<T> holder, actually, using #spawnEntity.getEntity().getType()# make strange things.
        final TypeHolder<EntityType<T>> savedType = new TypeHolder<>();
        EntityType.Builder<T> builder = EntityType.Builder.<T>create((type, world) -> {
            T entity = function.apply(type, world);
            savedType.lockAndSet(type);
            return entity;
        }, properties.getClassification())
                .size(size.x, size.y)
                .setCustomClientFactory((spawnEntity, world) -> function.apply(savedType.get(), world))
                .setTrackingRange(properties.getTrackingRange())
                .setUpdateInterval(properties.getUpdateInterval());
        if(properties.isImmuneToFire())
            builder.immuneToFire();
        final String id = Main.ID + ":" + label;
        EntityType<T> entity = builder.build(id);
        entity.setRegistryName(id);
        ENTITIES.add(entity);
        return entity;
    }

    private static <T extends ARocketPart> EntityType<T> buildRocketPart(BiFunction<EntityType<T>, World, T> function, String label) {
        EntityType<T> entity = buildEntity(function, IAssemblyType.Default.getInstance(0.0F, 0.0F), label);
        ItemsList.buildItem(new RocketPartItem(new Item.Properties().group(Main.GROUP).maxStackSize(1), entity), label);
        return entity;
    }

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ENTITIES.forEach(entity -> event.getRegistry().register(entity));
        ENTITIES.clear();
    }

}
