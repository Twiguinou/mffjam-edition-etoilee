package com.twihick.bunlyu.client.renderer.model;


import com.twihick.bunlyu.Main;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.Validate;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Main.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelGatherer {

    private static final Set<IAssociativeModel> MODELS = new HashSet<>();

    public static IAssociativeModel ENGINE_SMALL = new DefaultAssociativeModel("engine_small", Directory.CUSTOMS);
    public static IAssociativeModel FUEL_TANK_SMALL = new DefaultAssociativeModel("fuel_tank_small", Directory.CUSTOMS);
    public static IAssociativeModel CAPSULE_SMALL = new DefaultAssociativeModel("capsule_small", Directory.CUSTOMS);
    public static IAssociativeModel PARACHUTE_SMALL = new DefaultAssociativeModel("parachute_small", Directory.CUSTOMS);
    public static IAssociativeModel METAL_RAMP = new DefaultAssociativeModel("metal_ramp", Directory.CUSTOMS);

    static void add(IAssociativeModel model) {
        Validate.notNull(model.getLocation());
        MODELS.add(model);
    }

    @SubscribeEvent
    public static void registerModels(final ModelRegistryEvent event) {
        MODELS.forEach(model -> ModelLoader.addSpecialModel(model.getLocation()));
        MODELS.clear();
    }

}
