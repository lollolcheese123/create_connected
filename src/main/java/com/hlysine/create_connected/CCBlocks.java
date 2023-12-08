package com.hlysine.create_connected;

import com.hlysine.create_connected.content.brassgearbox.BrassGearboxBlock;
import com.hlysine.create_connected.content.centrifugalclutch.CentrifugalClutchBlock;
import com.hlysine.create_connected.content.copycat.CopycatSlabBlock;
import com.hlysine.create_connected.content.copycat.CopycatSlabModel;
import com.hlysine.create_connected.content.fancatalyst.FanCatalystBlock;
import com.hlysine.create_connected.content.inverted_clutch.InvertedClutchBlock;
import com.hlysine.create_connected.content.inverted_gearshift.InvertedGearshiftBlock;
import com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlock;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxBlock;
import com.hlysine.create_connected.content.shearpin.ShearPinBlock;
import com.hlysine.create_connected.datagen.CCBlockStateGen;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;

import java.util.function.Function;

import static com.simibubi.create.Create.REGISTRATE;
import static com.simibubi.create.foundation.data.AssetLookup.partialBaseModel;
import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.BlockStateGen.simpleCubeAll;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;

@SuppressWarnings("removal")
public class CCBlocks {
    private static final CreateRegistrate REGISTRATE = CreateConnected.getRegistrate();

    public static final BlockEntry<ParallelGearboxBlock> PARALLEL_GEARBOX = REGISTRATE.block("parallel_gearbox", ParallelGearboxBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOrPickaxe())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(ParallelGearboxBlock.AXIS))))
            .blockstate((c, p) -> axisBlock(c, p, $ -> partialBaseModel(c, p), false))
            .item()
            .transform(customItemModel())
            .register();


    public static final BlockEntry<OverstressClutchBlock> OVERSTRESS_CLUTCH = REGISTRATE.block("overstress_clutch", OverstressClutchBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p,
                    forBoolean(c, state -> state.getValue(OverstressClutchBlock.STATE) == OverstressClutchBlock.ClutchState.UNCOUPLED, "uncoupled", p)
            ))
            .item()
            .transform(customItemModel())
            .register();


    public static final BlockEntry<ShearPinBlock> SHEAR_PIN = REGISTRATE.block("shear_pin", ShearPinBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL).forceSolidOn())
            .transform(BlockStressDefaults.setNoImpact())
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<InvertedClutchBlock> INVERTED_CLUTCH = REGISTRATE.block("inverted_clutch", InvertedClutchBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<InvertedGearshiftBlock> INVERTED_GEARSHIFT = REGISTRATE.block("inverted_gearshift", InvertedGearshiftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();


    public static final BlockEntry<CentrifugalClutchBlock> CENTRIFUGAL_CLUTCH = REGISTRATE.block("centrifugal_clutch", CentrifugalClutchBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> p.directionalBlock(c.get(), forBoolean(c, state -> state.getValue(CentrifugalClutchBlock.UNCOUPLED), "uncoupled", p)))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<BrassGearboxBlock> BRASS_GEARBOX = REGISTRATE.block("brass_gearbox", BrassGearboxBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOrPickaxe())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.BRASS_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, AllSpriteShifts.BRASS_CASING,
                    (s, f) -> f.getAxis() == s.getValue(BrassGearboxBlock.AXIS))))
            .blockstate(CCBlockStateGen.brassGearbox())
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<FanCatalystBlock> FAN_CATALYST = REGISTRATE.block("fan_catalyst", FanCatalystBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW).requiresCorrectToolForDrops().noOcclusion().lightLevel(FanCatalystBlock::getLight))
            .addLayer(() -> RenderType::translucent)
            .transform(pickaxeOnly())
            .blockstate(CCBlockStateGen.fanCatalyst())
            .color(() -> CCColorHandlers::waterTint)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_BLASTING.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_HAUNTING.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SMOKING.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SPLASHING.tag)
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<CopycatSlabBlock> COPYCAT_SLAB =
            REGISTRATE.block("copycat_slab", CopycatSlabBlock::new)
                    .transform(BuilderTransformers.copycat())
                    .onRegister(CreateRegistrate.blockModel(() -> CopycatSlabModel::new))
                    .item()
                    .recipe((c, p) -> p.stonecutting(DataIngredient.tag(AllTags.forgeItemTag("ingots/zinc")),
                            RecipeCategory.BUILDING_BLOCKS, c, 2))
                    .transform(customItemModel("copycat_base", "slab"))
                    .register();

    public static void register() {
    }

    public static Function<BlockState, ModelFile> forBoolean(DataGenContext<?, ?> ctx,
                                                             Function<BlockState, Boolean> condition,
                                                             String key,
                                                             RegistrateBlockstateProvider prov) {
        return state -> condition.apply(state) ? partialBaseModel(ctx, prov, key)
                : partialBaseModel(ctx, prov);
    }
}
