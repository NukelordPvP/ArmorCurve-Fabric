package jackiecrazy.armorcurve.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class ValueUpdateMixin {

    @Shadow
    public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Inject(at = @At("HEAD"), method = "applyArmorToDamage")
    protected void applyArmorToDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> info) {
        ((AttributeUpdater) (this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR))).invokeUpdateAttribute();
    }
}
