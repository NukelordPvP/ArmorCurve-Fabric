package jackiecrazy.armorcurve;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

public class ItemStackKey {
    private final Item i;
    private final Entity w;
    private final int d;

    public ItemStackKey(Item item, Entity wielder, int damage) {
        i = item;
        w = wielder;
        d = damage;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemStackKey)) return false;
        ItemStackKey other = (ItemStackKey) obj;
        return i == other.i && w == other.w && d == other.d;
    }
}
