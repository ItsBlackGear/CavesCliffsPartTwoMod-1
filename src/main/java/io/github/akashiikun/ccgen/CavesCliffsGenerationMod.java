package io.github.akashiikun.ccgen;

import io.github.akashiikun.ccgen.core.api.CaveLayer;
import io.github.akashiikun.ccgen.core.api.WorldGen;
import net.fabricmc.api.ModInitializer;

public class CavesCliffsGenerationMod implements ModInitializer {
    public static final String MOD_ID = "ccgen";

    @Override
    public void onInitialize() {
        CaveLayer.caveBiomeList();

        WorldGen.init();
    }
}
