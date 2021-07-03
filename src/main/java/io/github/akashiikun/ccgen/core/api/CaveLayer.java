package io.github.akashiikun.ccgen.core.api;

import com.mojang.datafixers.util.Pair;
import io.github.akashiikun.ccgen.mixin.accessors.MultiNoiseBiomeSourceAccessor;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//<>

/**
 * @author LudoCrypt & BlackGear27
 */
public class CaveLayer {
	public static final Map<RegistryKey<Biome>, Biome.MixedNoisePoint> CAVE_BIOMES = new HashMap<>();
	public static final List<Biome> CAVE_BIOME_LIST = new ArrayList<>();

	public static MultiNoiseBiomeSource generateCaveBiomes(Registry<Biome> biomeRegistry, long seed) {
		CAVE_BIOME_LIST.addAll(CAVE_BIOMES.keySet().stream().map(biomeRegistry::get).collect(Collectors.toList()));
		List<Pair<Biome.MixedNoisePoint, Supplier<Biome>>> biomes = new ArrayList<>();
		CAVE_BIOMES.forEach((biomeKey, noisePoint) -> {
			Biome biome = biomeRegistry.getOrThrow(biomeKey);
			biomes.add(Pair.of(noisePoint, () -> biome));
		});

		MultiNoiseBiomeSource.NoiseParameters altitudeNoise = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 0.0D, 3.0D, 3.0D, 3.0D, 3.0D);
		MultiNoiseBiomeSource.NoiseParameters temperatureNoise = new MultiNoiseBiomeSource.NoiseParameters(-7, 1.0D, 2.0D, 4.0D, 4.0D);
		MultiNoiseBiomeSource.NoiseParameters humidityNoise = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D);
		MultiNoiseBiomeSource.NoiseParameters weirdnessNoise = new MultiNoiseBiomeSource.NoiseParameters(-8, 1.2D, 0.6D, 0.0D, 0.0D, 1.0D, 0.0D);

		return MultiNoiseBiomeSourceAccessor.createMultiNoiseBiomeSource(seed, biomes, altitudeNoise, temperatureNoise, humidityNoise, weirdnessNoise, Optional.empty());
	}

	public static void caveBiomeList() {
		CAVE_BIOMES.put(BiomeKeys.PLAINS, new Biome.MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		CAVE_BIOMES.put(BiomeKeys.LUSH_CAVES, new Biome.MixedNoisePoint(-0.2F, 0.2F, 0.325F, -0.15F, 0.0F));
		CAVE_BIOMES.put(BiomeKeys.DRIPSTONE_CAVES, new Biome.MixedNoisePoint(0.0F, -0.325F, -0.275F, 0.2F, 0.0F));
	}
}