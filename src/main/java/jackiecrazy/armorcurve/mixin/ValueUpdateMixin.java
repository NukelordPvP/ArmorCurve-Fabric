package jackiecrazy.armorcurve.mixin;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import jackiecrazy.armorcurve.ItemStackKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.TimeUnit;

@Mixin(LivingEntity.class)
public abstract class ValueUpdateMixin {

    @Shadow public abstract AttributeContainer getAttributes();

    @Shadow public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    private static final Cache<ItemStackKey, ImmutableMultimap<EntityAttribute, EntityAttributeModifier>> cache = CacheBuilder.newBuilder().weakKeys().expireAfterAccess(1, TimeUnit.SECONDS).build();

    @Inject(at = @At("HEAD"), method = "applyArmorToDamage")
    protected void applyArmorToDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> info) {
        ((AttributeUpdater)(this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR))).invokeUpdateAttribute();
    }
}
