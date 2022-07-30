# Armor Curve


Did you know that, by Minecraft's damage formulae, iron to diamond effectively doubles your health?

Did you know that leather to iron is 8 points in difference, yet it doesn't hold a candle to iron to diamond in terms of effective health increase?

Did you know that it's possible to survive almost 200 points of damage in vanilla, with full prot diamond and resistance?

Did you know that armor doesn't obey the basic law of diminishing marginal utility?

That's what inspired me to make this. Armor effectiveness is adjusted so that the more armor you have, the less every extra armor point adds to your total protection. Due to how meaninglessly complicated armor toughness is, it has been removed as a direct mechanic; as compensation, armor values have been slightly lowered. Leather provides roughly 40% protection, iron gives 60%, and diamond gives 66%. Protection enchantments have been similarly nerfed to 66% max, so full protection diamond only nets you 89% rather than 96% reduction. Instead of toughness acting directly on arbitrary armor damage reduction, however, an extra layer of calculations have been added, which (by default) greatly decreases any damage over 40 points according to your armor toughness.  These are configurable, see below.

Armor degrades. Low durability decreases the attributes of that specific piece of armor, so a half-broken helmet will drop your armor less than a half-broken chestplate. This is also configurable.

The config is structured such that you can input a formula (with parsing provided by EvalEx) and it'll work off that. Available variables for "armor" and "toughness" are "armor", "damage", and "toughness"; for "enchantments", variables are "damage" and "enchant", and for degradation it's "remaining" and "max". For instance, you could disable degradation by setting the formula to "1", replicate the vanilla formula with a little math, or multiply the damage by armor so everything hits harder the more you wear! Fun stuff.
