package com.lezhor.createtippedarrows.data;

import com.lezhor.createtippedarrows.registration.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ModRecipes.generateTippedArrowRecipes(output);
        ModRecipes.generateTippedArrowEmptyingRecipes(output);
        ModRecipes.generateTippedArrowWashingRecipes(output);
    }
}
