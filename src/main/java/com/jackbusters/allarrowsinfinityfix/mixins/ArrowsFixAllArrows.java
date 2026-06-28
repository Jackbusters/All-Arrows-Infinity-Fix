package com.jackbusters.allarrowsinfinityfix.mixins;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
    <h1>Vanilla Infinity Check</h1>
    <p>The {@link EnchantmentHelper#processAmmoUse(ServerLevel, ItemStack, ItemStack, int)}
 function returns the amount of ammo (arrows) a projectile weapon should use when fired. </p>
 <p>
 It runs the checks for the Infinity enchantment on ammo, and returns 0 if the ammo type should not be used.
 </p>

 <p>This mixin injects to the head of that function, adds a condition simply checking if the weapon has infinity, and returns 0 if it does.</p>
*/
@Mixin(EnchantmentHelper.class)
public class ArrowsFixAllArrows {
    @Inject(method = "processAmmoUse", at = @At("HEAD"), cancellable = true)
    private static void hasInfiniteInjection(ServerLevel serverLevel, ItemStack weapon, ItemStack ammo, int amount, CallbackInfoReturnable<Integer> cir){
        Holder<Enchantment> INFINITY = serverLevel.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY);
        boolean hasInfinity = EnchantmentHelper.getItemEnchantmentLevel(INFINITY, weapon) > 0;
        if (hasInfinity)
            cir.setReturnValue(0);
    }
}
