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
import java.util.logging.Level;
import java.util.logging.Logger;

@Mixin(DamageUtil.class)
public class DamageCalculatorMixin {
    @Inject(cancellable = true, at = @At("HEAD"), method = "getDamageLeft(FFF)F")
    private static void getDamageLeft(float damage, float armor, float armorToughness, CallbackInfoReturnable<Float> info) {
        if (ArmorCurve.config == null || Arrays.stream(ArmorCurve.formulae).anyMatch(Objects::isNull)) {
            Logger.getLogger("armorcurve").log(Level.WARNING, "armor formulae loaded incorrectly! Double check your config!");
            return;
        }
        BigDecimal ret = ArmorCurve.formulae[0].with("damage", new BigDecimal(damage)).and("armor", new BigDecimal(armor)).and("toughness", new BigDecimal(armorToughness)).eval();
        ret = ArmorCurve.formulae[1].with("damage", ret).and("armor", new BigDecimal(armor)).and("toughness", new BigDecimal(armorToughness)).eval();
        info.setReturnValue(ret.floatValue());
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "getInflictedDamage(FF)F")
    private static void getInflictedDamage(float damage, float prot, CallbackInfoReturnable<Float> info) {
        if (ArmorCurve.config == null || Arrays.stream(ArmorCurve.formulae).allMatch(Objects::isNull)) return;
        BigDecimal ret = ArmorCurve.formulae[2].with("damage", new BigDecimal(damage)).and("enchant", new BigDecimal(prot)).eval();

        info.setReturnValue(ret.floatValue());
    }
}
