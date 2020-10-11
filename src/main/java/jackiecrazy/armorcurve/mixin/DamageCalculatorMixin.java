package jackiecrazy.armorcurve.mixin;

import jackiecrazy.armorcurve.ArmorCurve;
import net.minecraft.entity.DamageUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@Mixin(DamageUtil.class)
public class DamageCalculatorMixin {
    @Inject(cancellable = true, at = @At("HEAD"), method = "getDamageLeft(FFF)F")
    private static void getDamageLeft(float damage, float armor, float armorToughness, CallbackInfoReturnable<Float> info) {
        if (ArmorCurve.config == null || Arrays.stream(ArmorCurve.formulae).anyMatch(Objects::isNull)){
            System.out.println("?");
            return;
        }
        BigDecimal ret = ArmorCurve.formulae[0].with("d", new BigDecimal(damage)).and("a", new BigDecimal(armor)).and("t", new BigDecimal(armorToughness)).eval();
        ret = ArmorCurve.formulae[1].with("d", ret).and("a", new BigDecimal(armor)).and("t", new BigDecimal(armorToughness)).eval();
//        float reduction = 1 + armor / 5;
//        float afterDamage = damage / reduction;
//
//        if (armorToughness > 0) {
//            float cap = 40 / armorToughness;
//            if (afterDamage > cap)
//                afterDamage -= (afterDamage - cap) / 2;
//        }
        info.setReturnValue(ret.floatValue());
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "getInflictedDamage(FF)F")
    private static void getInflictedDamage(float damage, float prot, CallbackInfoReturnable<Float> info) {
        if (ArmorCurve.config == null || Arrays.stream(ArmorCurve.formulae).allMatch(Objects::isNull)) return;
        BigDecimal ret = ArmorCurve.formulae[2].with("d", new BigDecimal(damage)).and("e", new BigDecimal(prot)).eval();

        //float reduction = 1 + prot / 5;
        info.setReturnValue(ret.floatValue());
    }
}
