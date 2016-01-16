package GoldBigDragon_RPG.Config;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class SkillConfig
{
    public void CreateNewPlayerSkill(Player player)
	{
		YamlController Config_YC = GoldBigDragon_RPG.Main.Main.YC_1;
		YamlManager PlayerSkillYML = Config_YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId()+".yml");
		YamlManager Config = Config_YC.getNewConfig("config.yml");
		YamlManager JobList  = Config_YC.getNewConfig("Skill/JobList.yml");
		
		PlayerSkillYML.set("Job.Type",Config.getString("Server.DefaultJob"));
		PlayerSkillYML.set("Job.LV",1);
		PlayerSkillYML.set("MapleStory.LV",null);
		PlayerSkillYML.set("MapleStory."+Config.getString("Server.DefaultJob")+".Skill.1",null);
		
		Object[] DefaultSkills = JobList.getConfigurationSection("MapleStory."+Config.getString("Server.DefaultJob")+"."+Config.getString("Server.DefaultJob")+".Skill").getKeys(false).toArray();

		for(int count = 0; count < DefaultSkills.length;count++)
			PlayerSkillYML.set("MapleStory."+Config.getString("Server.DefaultJob")+".Skill."+DefaultSkills[count],1);
		PlayerSkillYML.set("Mabinogi.LV",null);
		PlayerSkillYML.saveConfig();
	}
    
    public void CreateNewJobList()
	{
		YamlController Config_YC = GoldBigDragon_RPG.Main.Main.YC_1;
		YamlManager SkillList  = Config_YC.getNewConfig("Skill/JobList.yml");
		
		SkillList.set("Mabinogi.Added.1",null);
		SkillList.set("MapleStory.초보자.초보자.NeedLV",0);
		SkillList.set("MapleStory.초보자.초보자.NeedSTR",0);
		SkillList.set("MapleStory.초보자.초보자.NeedDEX",0);
		SkillList.set("MapleStory.초보자.초보자.NeedINT",0);
		SkillList.set("MapleStory.초보자.초보자.NeedWILL",0);
		SkillList.set("MapleStory.초보자.초보자.NeedLUK",0);
		SkillList.set("MapleStory.초보자.초보자.PrevJob","null");
		SkillList.set("MapleStory.초보자.초보자.IconID",268);
		SkillList.set("MapleStory.초보자.초보자.IconData",0);
		SkillList.set("MapleStory.초보자.초보자.IconAmount",1);
		SkillList.set("MapleStory.초보자.초보자.Skill.1",null);
		SkillList.saveConfig();
	}
    
    public void CreateNewSkillList()
	{
		YamlController Config_YC = GoldBigDragon_RPG.Main.Main.YC_1;
		YamlManager SkillList  = Config_YC.getNewConfig("Skill/SkillList.yml");
		SkillList.set("CloudKill.ID",151);
		SkillList.set("CloudKill.DATA",0);
		SkillList.set("CloudKill.Amount",1);
		SkillList.set("CloudKill.SkillRank."+(int)1+".Command","/weather clear 9999");
		SkillList.set("CloudKill.SkillRank."+(int)1+".BukkitPermission",true);
		SkillList.set("CloudKill.SkillRank."+(int)1+".MagicSpells","null");
		SkillList.set("CloudKill.SkillRank."+(int)1+".Lore",ChatColor.GRAY + "     [설명 없음]     ");
		SkillList.set("CloudKill.SkillRank."+(int)1+".AffectStat","없음");
		SkillList.set("CloudKill.SkillRank."+(int)1+".DistrictWeapon","없음");
		SkillList.saveConfig();
	}
}
