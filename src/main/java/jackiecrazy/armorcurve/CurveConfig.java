package jackiecrazy.armorcurve;

import com.udojava.evalex.Expression;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "armorcurve")
public class CurveConfig implements ConfigData {
    //that is, subtract damage by (d>40/(t+1))(d-40/(t+1))/2
    @ConfigEntry.Gui.Tooltip()
    public String armor = "d*5/(5+a)";
    @ConfigEntry.Gui.Tooltip()
    public String armorToughness = "d-(d>40/(t+1))(d-40/(t+1))/2";
    @ConfigEntry.Gui.Tooltip()
    public String enchantments = "d*10/(10+e)";
    //TODO use EvalEx to make a fully custom damage scaling formula!

    @Override
    public void validatePostLoad() throws ValidationException {
        ArmorCurve.formulae[0] = new Expression(armor);
        try {
            ArmorCurve.formulae[0].with("d", "1").and("a", "10").and("t", "0").eval();
        } catch (Expression.ExpressionException e) {
            throw new ValidationException("invalid formula " + armor);
        }
        ArmorCurve.formulae[1] = new Expression(armorToughness);
        try {
            ArmorCurve.formulae[1].with("d", "1").and("a", "10").and("t", "0").eval();
        } catch (Expression.ExpressionException e) {
            throw new ValidationException("invalid formula " + armorToughness);
        }
        ArmorCurve.formulae[2] = new Expression(enchantments);
        try {
            ArmorCurve.formulae[2].with("d", "1").and("e", "2").eval();
        } catch (Expression.ExpressionException e) {
            throw new ValidationException("invalid formula " + enchantments);
        }
    }

}
