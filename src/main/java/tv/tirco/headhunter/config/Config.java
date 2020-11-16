package tv.tirco.headhunter.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

import tv.tirco.headhunter.MessageHandler;

public class Config extends AutoUpdateConfigLoader {
	private static Config instance;

	private Config() {
		super("config.yml");
		validate();
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}

		return instance;
	}

	@Override
	protected void loadKeys() {

	}

	@Override
	protected boolean validateKeys() {
		// Validate all the settings!
		List<String> reason = new ArrayList<String>();

		if (getDebug()) {
			MessageHandler.getInstance().setDebugState(true);
			MessageHandler.getInstance().debug("Debugging has been enabled.");
		}

		/* General Settings */
		if (getSaveInterval() <= 0) {
			reason.add("General.Save_Interval should be greater than 0!");
		}

		// If the reason list is empty, keys are valid.
		return noErrorsInConfig(reason);
	}

	private String getStringIncludingInts(String key) {
		String str = config.getString(key);

		if (str == null) {
			str = String.valueOf(config.getInt(key));
		}

		if (str.equals("0")) {
			str = "No value set for '" + key + "'";
		}
		return str;
	}

	// Config Getters
	/* General Settings */
	public String getLocale() {
		return config.getString("General.Locale", "en_us");
	}

	public boolean getDebug() {
		return config.getBoolean("setting.debug", false);
	}
	
	public boolean getDebugToAdmins() {
		return config.getBoolean("setting.debugtoadmins", false);
	}

	public int getSaveInterval() {
		return config.getInt("General.Save_Interval", 15);
	}

	/* Database Purging */
	public int getPurgeInterval() {
		return config.getInt("Database_Purging.Purge_Interval", -1);
	}

	public int getOldUsersCutoff() {
		return config.getInt("Database_Purging.Old_User_Cutoff", 6);
	}
}