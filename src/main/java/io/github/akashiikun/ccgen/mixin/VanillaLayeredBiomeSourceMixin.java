package io.github.akashiikun.ccgen.mixin;

import io.github.akashiikun.ccgen.core.api.CaveLayer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

/**
 * source: CaveBiomeAPI
 */
@Mixin(VanillaLayeredBiomeSource.class)
public class VanillaLayeredBiomeSourceMixin {
    @Shadow @Final private BiomeLayerSampler biomeSampler;
    @Shadow @Final private Registry<Biome> biomeRegistry;

    @Unique
    private MultiNoiseBiomeSource caveBiomeSource;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void cba$initialize(long seed, boolean legacyBiomeInitLayer, boolean largeBiomes, Registry<Biome> biomeRegistry, CallbackInfo ci) {
        this.caveBiomeSource = CaveLayer.generateCaveBiomes(biomeRegistry, seed);
    }

    /**
     * @author BlackGear27
     * @reason collecting the surface biomes and injecting them along the underground biomes.
     */
    @Overwrite
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        if (y <= 12) {
            return this.caveBiomeSource.getBiomeForNoiseGen(x, 0, z);
        }
        return this.biomeSampler.sample(this.biomeRegistry, x, z);
    }
}