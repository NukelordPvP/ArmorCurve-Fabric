package jackiecrazy.armorcurve;

import com.udojava.evalex.Expression;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ArmorCurve implements ModInitializer {
	public static CurveConfig config=null;
	public static Expression[]formulae=new Expression[3];
	@Override
	public void onInitialize() {
		Logger.getLogger("armorcurve").log(Level.INFO, "armor curve loaded!");
		AutoConfig.register(CurveConfig.class, Toml4jConfigSerializer::new);
		config=AutoConfig.getConfigHolder(CurveConfig.class).getConfig();
	}
}
