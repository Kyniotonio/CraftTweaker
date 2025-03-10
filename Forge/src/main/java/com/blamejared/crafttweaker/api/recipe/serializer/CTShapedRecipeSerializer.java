package com.blamejared.crafttweaker.api.recipe.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.function.RecipeFunctionMatrix;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import javax.annotation.Nullable;

public class CTShapedRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements ICTShapedRecipeBaseSerializer {
    
    public static final CTShapedRecipeSerializer INSTANCE = new CTShapedRecipeSerializer();
    
    public CTShapedRecipeSerializer() {
        
        setRegistryName(CraftTweakerConstants.rl("shaped"));
    }
    
    @Override
    public CTShapedRecipeBase makeRecipe(ResourceLocation recipeId, IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunctionMatrix function) {
        
        return new CTShapedRecipe(recipeId.getPath(), output, ingredients, mirrorAxis, function);
    }
    
}