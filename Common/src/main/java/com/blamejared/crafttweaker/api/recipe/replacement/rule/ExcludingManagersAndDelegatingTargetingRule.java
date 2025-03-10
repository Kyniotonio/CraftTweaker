package com.blamejared.crafttweaker.api.recipe.replacement.rule;

import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.Util;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ExcludingManagersAndDelegatingTargetingRule implements ITargetingRule {
    
    private final ITargetingRule delegate;
    private final Collection<IRecipeManager<?>> exclusions;
    
    private ExcludingManagersAndDelegatingTargetingRule(final ITargetingRule delegate, final Collection<IRecipeManager<?>> exclusions) {
        
        this.delegate = delegate;
        this.exclusions = exclusions;
    }
    
    public static ExcludingManagersAndDelegatingTargetingRule of(final ITargetingRule delegate, final Collection<IRecipeManager<?>> exclusions) {
        
        Objects.requireNonNull(delegate);
        if(exclusions.isEmpty()) {
            throw new IllegalArgumentException("Unable to create an exclusion for managers without any manager to exclude");
        }
        if(delegate instanceof ExcludingManagersAndDelegatingTargetingRule) {
            final ExcludingManagersAndDelegatingTargetingRule delegatingRule = (ExcludingManagersAndDelegatingTargetingRule) delegate;
            return of(delegatingRule.delegate, Util.make(new HashSet<>(exclusions), it -> it.addAll(delegatingRule.exclusions)));
        }
        return new ExcludingManagersAndDelegatingTargetingRule(delegate, exclusions);
    }
    
    public static ExcludingManagersAndDelegatingTargetingRule of(final ITargetingRule delegate, final IRecipeManager<?>... exclusions) {
        
        return of(delegate, new HashSet<>(Arrays.asList(exclusions)));
    }
    
    @Override
    public boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager) {
        
        return !this.exclusions.contains(manager) && this.delegate.shouldBeReplaced(recipe, manager);
    }
    
    @Override
    public String describe() {
        
        return String.format(
                "%s, but excluding managers {%s}",
                this.delegate.describe(),
                this.exclusions.stream()
                        .map(IRecipeManager::getCommandString)
                        .collect(Collectors.joining(", "))
        );
    }
    
}
