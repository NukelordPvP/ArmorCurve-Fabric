package jackiecrazy.armorcurve;

//adds EvalEx dependency and more

import com.udojava.evalex.Expression;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ArmorCurve implements ModInitializer {
    public static String MOD_ID = "armorcurve";
    public static CurveConfig config = null;
    public static Expression[] formulae = new Expression[4];
    public static Logger LOGGER = LogManager.getLogger("ArmorCurve");

    @Override
    public void onInitialize() {
        LOGGER.info("Armor curve loaded!");
        AutoConfig.register(CurveConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(CurveConfig.class).getConfig();
    }
}
