package GBD.GoldBigDragon_Advanced.Config;

import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class StatConfig
{
    private YamlManager YM;
    public void CreateNewStats(Player player)
	{
	    YamlManager Config;
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
	    Config=Config_YC.getNewConfig("config.yml");
	  	YM = Config_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		YamlManager NewBieYM = Config_YC.getNewConfig("ETC/NewBie.yml");
		
	  	YM.set("Player.Name", player.getName());
	  	YM.set("Player.UUID", player.getUniqueId().toString());
	  	
	  	YM.set("Stat.Level", 1);
	  	YM.set("Stat.RealLevel", 1);
	  	YM.set("Stat.SkillPoint", Config.getInt("DefaultStat.SkillPoint"));
	  	YM.set("Stat.StatPoint", Config.getInt("DefaultStat.StatPoint"));
	  	YM.set("Stat.EXP", 0);
	  	YM.set("Stat.MaxEXP", Config.getInt("DefaultStat.MaxEXP"));
	  	YM.set("Stat.Money", NewBieYM.getInt("SupportMoney"));
	  	YM.set("Stat.HP", Config.getInt("DefaultStat.HP"));
	  	YM.set("Stat.MAXHP", Config.getInt("DefaultStat.HP"));
	  	YM.set("Stat.Wond", Config.getInt("DefaultStat.Wond"));
	  	YM.set("Stat.MP", Config.getInt("DefaultStat.MP"));
	  	YM.set("Stat.MAXMP", Config.getInt("DefaultStat.HP"));
	  	YM.set("Stat.STR", Config.getInt("DefaultStat.STR"));
	  	YM.set("Stat.DEX", Config.getInt("DefaultStat.DEX"));
	  	YM.set("Stat.INT", Config.getInt("DefaultStat.INT"));
	  	YM.set("Stat.WILL", Config.getInt("DefaultStat.WILL"));
	  	YM.set("Stat.LUK", Config.getInt("DefaultStat.LUK"));
	  	YM.set("Stat.Balance", Config.getInt("DefaultStat.Balance"));
	  	YM.set("Stat.Critical", Config.getInt("DefaultStat.Critical"));
	  	YM.set("Stat.DEF", Config.getInt("DefaultStat.DEF"));
	  	YM.set("Stat.DEFcrash", Config.getInt("DefaultStat.DEFcrash"));
	  	YM.set("Stat.Protect", Config.getInt("DefaultStat.Protect"));
	  	YM.set("Stat.Magic_DEF", Config.getInt("DefaultStat.Magic_DEF"));
	  	YM.set("Stat.MagicDEFcrash", Config.getInt("DefaultStat.MagicDEFcrash"));
	  	YM.set("Stat.Magic_Protect", Config.getInt("DefaultStat.Magic_Protect"));
	  	YM.set("Stat.AttackTime", 0);
	  	YM.set("Stat.BowPull", 0);

	  	YM.set("Alert.Damage", true);
	  	YM.set("Alert.MobHealth", true);
	  	YM.set("Alert.Critical", true);
	  	YM.set("Alert.AttackDelay", true);
	  	YM.set("Alert.ItemPickUp", true);
	  	YM.set("Alert.EXPget", true);

	  	YM.set("Option.EquipLook", true);
	  	YM.set("Option.ChattingType", 0);
	  	YM.set("Option.HotBarSound", true);
	  	
	  	YM.set("ETC.Party", -1);
	  	YM.set("ETC.Death", false);
	  	YM.set("ETC.Dungeon", false);
	  	YM.set("ETC.CurrentArea", "null");
	  	YM.set("ETC.LastVisited", "null");
	  	YM.set("ETC.BuffCoolTime", 0);
	  	
	  	YM.saveConfig();
	}
}
