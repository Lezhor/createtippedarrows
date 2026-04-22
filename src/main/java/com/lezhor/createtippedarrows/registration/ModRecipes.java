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
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class ModRecipes {

    /**
     * This method iterates through all potions and generates a Spout filling recipe for each.
     */
    public static void generateTippedArrowRecipes(RecipeOutput consumer) {
        int amount = Config.TIPPED_ARROW_REQUIRED_FILL_AMOUNT.get();

        BuiltInRegistries.POTION.asHolderIdMap().forEach(potionHolder -> {
            PotionContents contents = new PotionContents(potionHolder);
            
            ItemStack output = new ItemStack(Items.TIPPED_ARROW);
            output.set(net.minecraft.core.component.DataComponents.POTION_CONTENTS, contents);

            FluidStack fluid = PotionFluid.of(amount, contents, PotionFluid.BottleType.REGULAR);

            ResourceLocation potionId = potionHolder.getRegisteredName() != null 
                ? ResourceLocation.parse(potionHolder.getRegisteredName()) 
                : ResourceLocation.fromNamespaceAndPath(CreateTippedArrows.MODID, "dummy_" + potionHolder.hashCode());

            // consumer = datagen
            new StandardProcessingRecipe.Builder<>(FillingRecipe::new, 
                ResourceLocation.fromNamespaceAndPath(CreateTippedArrows.MODID, "tipping_" + potionId.getPath()))
                .withItemIngredients(Ingredient.of(Items.ARROW))
                .withFluidIngredients(SizedFluidIngredient.of(fluid))
                .withSingleItemOutput(output)
                .build(consumer);
        });
    }
}
