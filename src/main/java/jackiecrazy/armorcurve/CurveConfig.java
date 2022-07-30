package jackiecrazy.armorcurve;

import com.udojava.evalex.Expression;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "armorcurve")
public class CurveConfig implements ConfigData {
    //that is, subtract damage by (d>40/(t+1))(d-40/(t+1))/2
    @ConfigEntry.Gui.Tooltip()
    public String armor = "damage*5/(5+armor)";
    @ConfigEntry.Gui.Tooltip()
    public String armorToughness = "damage-(damage>40/(toughness+1))(damage-40/(toughness+1))/2";
    @ConfigEntry.Gui.Tooltip()
    public String enchantments = "damage*10/(10+enchant)";
    @ConfigEntry.Gui.Tooltip()
    public String degradation = "remaining/max";

    @Override
    public void validatePostLoad() throws ValidationException {
        ArmorCurve.formulae[0] = new Expression(armor);
        try {
            ArmorCurve.formulae[0].with("damage", "1").and("armor", "10").and("toughness", "0").eval();
        } catch (Expression.ExpressionException e) {
            throw new ValidationException("invalid formula " + armor);
        }
        ArmorCurve.formulae[1] = new Expression(armorToughness);
        try {
            ArmorCurve.formulae[1].with("damage", "1").and("armor", "10").and("toughness", "0").eval();
        } catch (Expression.ExpressionException e) {
            throw new ValidationException("invalid formula " + armorToughness);
        }
        ArmorCurve.formulae[2] = new Expression(enchantments);
        try {
            ArmorCurve.formulae[2].with("damage", "1").and("enchant", "2").eval();
        } catch (Expression.ExpressionException e) {
            throw new ValidationException("invalid formula " + enchantments);
        }
        ArmorCurve.formulae[3] = new Expression(degradation);
        try {
            ArmorCurve.formulae[3].with("remaining", "100").and("max", "100").eval();
        } catch (Expression.ExpressionException e) {
            throw new ValidationException("invalid formula " + degradation);
        }
    }

}
