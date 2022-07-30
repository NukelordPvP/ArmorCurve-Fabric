package jackiecrazy.armorcurve.mixin;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import jackiecrazy.armorcurve.ArmorCurve;
import jackiecrazy.armorcurve.ItemStackKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Mixin(ItemStack.class)
public abstract class ArmorDegradationMixin {

    private static final Cache<ItemStackKey, ImmutableMultimap<EntityAttribute, EntityAttributeModifier>> cache = CacheBuilder.newBuilder().weakKeys().expireAfterAccess(1, TimeUnit.SECONDS).build();

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean isEmpty();

    @Shadow
    public abstract int getDamage();

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    public abstract Entity getHolder();

    @Inject(cancellable = true, at = @At("RETURN"), method = "getAttributeModifiers", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getAttributeModifiers(EquipmentSlot equipmentSlot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> info, Multimap<EntityAttribute, EntityAttributeModifier> m) {
        if (!this.isEmpty() && (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) && this.getItem().isDamageable()) {
            ItemStackKey isk = new ItemStackKey(this.getItem(), this.getHolder(), this.getDamage());
            ImmutableMultimap<EntityAttribute, EntityAttributeModifier> cached = cache.getIfPresent(isk);
            if (cached != null) info.setReturnValue(cached);
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> copy = ImmutableMultimap.builder();
            float degrade = ArmorCurve.formulae[3].with("remaining", new BigDecimal(this.getMaxDamage() - this.getDamage())).and("max", new BigDecimal(this.getMaxDamage())).eval().floatValue();
            for (EntityAttribute e : m.keySet())
                for (EntityAttributeModifier eam : m.get(e)) {
                    EntityAttributeModifier degradedEAM = new EntityAttributeModifier(eam.getId(), eam.getName(), (degrade) * eam.getValue(), eam.getOperation());
                    copy.put(e, degradedEAM);
                }
            cached = copy.build();
            cache.put(isk, cached);
            info.setReturnValue(cached);
        }
    }
}
