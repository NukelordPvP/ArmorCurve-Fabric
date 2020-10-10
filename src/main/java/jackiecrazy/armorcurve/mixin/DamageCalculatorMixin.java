package jackiecrazy.armorcurve.mixin;

import net.minecraft.entity.DamageUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DamageUtil.class)
public class DamageCalculatorMixin {
    @Inject(cancellable = true, at = @At("HEAD"), method = "getDamageLeft(FFF)F")
    private static void getDamageLeft(float damage, float armor, float armorToughness, CallbackInfoReturnable<Float> info) {
        float reduction = 1 + armor / 5;
        float afterDamage=damage/reduction;
        if(armorToughness>0) {
            float cap=40 / armorToughness;
            if(afterDamage>cap)
            afterDamage-=(afterDamage-cap)/2;
        }
        info.setReturnValue(afterDamage);
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "getInflictedDamage(FF)F")
    private static void getInflictedDamage(float damage, float prot, CallbackInfoReturnable<Float> info) {
        float reduction = 1 + prot / 5;
        info.setReturnValue(damage / reduction);
    }
}
