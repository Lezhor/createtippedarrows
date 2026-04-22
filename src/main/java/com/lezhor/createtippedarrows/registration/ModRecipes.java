package com.lezhor.createtippedarrows.registration;

import com.lezhor.createtippedarrows.Config;
import com.lezhor.createtippedarrows.CreateTippedArrows;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.fluids.crafting.DataComponentFluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class ModRecipes {
/**
 * This method iterates through all potions and generates a Spout filling recipe for each.
 */
public static void generateTippedArrowRecipes(RecipeOutput consumer) {
    // In DataGen, the config might not be loaded yet. We use a fallback if it's not available.
    int amount = 25;
    try {
        amount = Config.TIPPED_ARROW_REQUIRED_FILL_AMOUNT.get();
    } catch (IllegalStateException e) {
        // Config not loaded, use default
    }

    final int finalAmount = amount;
    BuiltInRegistries.POTION.asHolderIdMap().forEach(potionHolder -> {
        PotionContents contents = new PotionContents(potionHolder);

        // Create the output Tipped Arrow
        ItemStack output = new ItemStack(Items.TIPPED_ARROW);
        output.set(net.minecraft.core.component.DataComponents.POTION_CONTENTS, contents);

        // Define the fluid
        FluidStack fluid = PotionFluid.of(finalAmount, contents, PotionFluid.BottleType.REGULAR);

            ResourceLocation potionId = potionHolder.getRegisteredName() != null 
                ? ResourceLocation.parse(potionHolder.getRegisteredName()) 
                : ResourceLocation.fromNamespaceAndPath(CreateTippedArrows.MODID, "dummy_" + potionHolder.hashCode());

            // consumer = datagen
            new StandardProcessingRecipe.Builder<>(FillingRecipe::new, 
                ResourceLocation.fromNamespaceAndPath(CreateTippedArrows.MODID, "tipping_" + potionId.getPath()))
                .withItemIngredients(Ingredient.of(Items.ARROW))
                .withFluidIngredients(new SizedFluidIngredient(DataComponentFluidIngredient.of(false, fluid), fluid.getAmount()))
                .withSingleItemOutput(output)
                .build(consumer);
        });
    }

    /**
     * This method iterates through all potions and generates an Item Drain emptying recipe for each.
     */
    public static void generateTippedArrowEmptyingRecipes(RecipeOutput consumer) {
        int amount = 25;
        try {
            amount = Config.TIPPED_ARROW_REQUIRED_FILL_AMOUNT.get();
        } catch (IllegalStateException e) {
            // Config not loaded, use default
        }

        final int finalAmount = amount;
        BuiltInRegistries.POTION.asHolderIdMap().forEach(potionHolder -> {
            PotionContents contents = new PotionContents(potionHolder);

            // Create the input Tipped Arrow
            ItemStack inputStack = new ItemStack(Items.TIPPED_ARROW);
            inputStack.set(net.minecraft.core.component.DataComponents.POTION_CONTENTS, contents);

            // Define the fluid output
            FluidStack fluid = PotionFluid.of(finalAmount, contents, PotionFluid.BottleType.REGULAR);

            ResourceLocation potionId = potionHolder.getRegisteredName() != null 
                ? ResourceLocation.parse(potionHolder.getRegisteredName()) 
                : ResourceLocation.fromNamespaceAndPath(CreateTippedArrows.MODID, "dummy_" + potionHolder.hashCode());

            // consumer = datagen
            new StandardProcessingRecipe.Builder<>(EmptyingRecipe::new, 
                ResourceLocation.fromNamespaceAndPath(CreateTippedArrows.MODID, "emptying_" + potionId.getPath()))
                .withItemIngredients(DataComponentIngredient.of(false, inputStack))
                .withSingleItemOutput(new ItemStack(Items.ARROW))
                .withFluidOutputs(fluid)
                .build(consumer);
        });
    }

    /**
     * This method generates a washing (splashing) recipe to clean tipped arrows.
     */
    public static void generateTippedArrowWashingRecipes(RecipeOutput consumer) {
        java.util.List<Ingredient> ingredients = new java.util.ArrayList<>();
        BuiltInRegistries.POTION.asHolderIdMap().forEach(potion -> {
            ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
            stack.set(net.minecraft.core.component.DataComponents.POTION_CONTENTS, new PotionContents(potion));
            ingredients.add(DataComponentIngredient.of(false, stack));
        });

        new StandardProcessingRecipe.Builder<>(SplashingRecipe::new, 
            ResourceLocation.fromNamespaceAndPath(CreateTippedArrows.MODID, "washing_tipped_arrow"))
            .withItemIngredients(CompoundIngredient.of(ingredients.toArray(Ingredient[]::new)))
            .withSingleItemOutput(new ItemStack(Items.ARROW))
            .build(consumer);
    }
}
