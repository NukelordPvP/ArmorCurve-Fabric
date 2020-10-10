package jackiecrazy.armorcurve;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ArmorCurve implements ModInitializer {
	@Override
	public void onInitialize() {
		Logger.getLogger("armorcurve").log(Level.INFO, "armor curve loaded!");
	}
}
