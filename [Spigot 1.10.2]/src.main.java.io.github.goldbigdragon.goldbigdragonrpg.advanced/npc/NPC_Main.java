package npc;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import effect.SoundEffect;
import user.UserData_Object;
import util.YamlLoader;

public class NPC_Main
{
	public String[] getScript(Player player, char ScriptType)
	{
		UserData_Object u = new UserData_Object();
		if(ScriptType == -1)
		{
			String[] script = new String[1];
			script[0] = "a";
			return script;
		}
		String TalkSubject = "NatureTalk";

		npc.NPC_Config NPCconfig = new npc.NPC_Config();
		NPCconfig.PlayerNPCconfig(player, u.getNPCuuid(player));
		NPCconfig.NPCNPCconfig(u.getNPCuuid(player));

	  	YamlLoader playerNPCData = new YamlLoader();
		playerNPCData.getConfig("NPC/PlayerData/" + player.getUniqueId() +".yml");
	  	YamlLoader npcYaml = new YamlLoader();
		npcYaml.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");

	  	if(ScriptType == 2)
	  		TalkSubject = "NatureTalk";
	  	if(ScriptType == 4)
	  		TalkSubject = "NearByNEWS";
	  	if(ScriptType == 6)
	  		TalkSubject = "AboutSkills";

	  	int Size = npcYaml.getConfigurationSection(TalkSubject).getKeys(false).toArray().length;
		
		if(Size <= 0)
		{
			byte randomScript = (byte) new util.Util_Number().RandomNum(0, 2);
			String[] script = new String[1];
			if(randomScript == 0)
				script[0] = "§7....";
			if(randomScript == 1)
				script[0] = "§7(할 말이 없는것 같다.)";
			if(randomScript == 2)
				script[0] = "§7....?";
			return script;
		}

		boolean scriptget = false;
		String scriptString = "";
		
		boolean textOK = false;
		byte randomScript = 0;
		for(int counter = 1; counter < 125; counter++)
		{
			randomScript = (byte) new util.Util_Number().RandomNum(1, Size);
			if(playerNPCData.getInt(u.getNPCuuid(player)+".love") >= npcYaml.getInt(TalkSubject + "."+randomScript+".love"))
			{
				if(npcYaml.getInt(TalkSubject + "."+randomScript+".loveMax") != 0)
				{
					if(playerNPCData.getInt(u.getNPCuuid(player)+".love") <= npcYaml.getInt(TalkSubject + "."+randomScript+".loveMax"))
						textOK = true;
				}
				else
					textOK = true;
				break;
			}
		}
		if(textOK)
		{
			scriptString = npcYaml.getString(TalkSubject + "."+randomScript+".Script");
			scriptget = true;
			YamlLoader skillYaml = new YamlLoader();
			skillYaml.getConfig("Skill/SkillList.yml");
			String Skillname = npcYaml.getString(TalkSubject + "."+randomScript+".giveSkill");
			if(ScriptType == 6&&skillYaml.contains(npcYaml.getString(TalkSubject + "."+randomScript+".giveSkill"))==true)
			{
				YamlLoader jobYaml = new YamlLoader();
				jobYaml.getConfig("Skill/JobList.yml");
				YamlLoader playerSkillYaml = new YamlLoader();
				playerSkillYaml.getConfig("Skill/PlayerData/" + player.getUniqueId() +".yml");
				String Categori = jobYaml.getString("Mabinogi.Added."+npcYaml.getString(TalkSubject + "."+randomScript+".giveSkill"));
				if(playerSkillYaml.contains("Mabinogi."+Categori+"."+npcYaml.getString(TalkSubject + "."+randomScript+".giveSkill"))==false)
				{
					
					playerSkillYaml.set("Mabinogi."+Categori+"."+Skillname, 1);
					playerSkillYaml.saveConfig();
					SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
					player.sendMessage("§d§l[새로운 스킬을 획득 하였습니다!] §e§l"+ChatColor.UNDERLINE+Skillname);
				}
				else
				{
					scriptString = npcYaml.getString(TalkSubject + "."+randomScript+".AlreadyGetScript");
				}
			}
		}
		if(scriptget == false)
		{
			String[] script = new String[1];
			randomScript = (byte) new util.Util_Number().RandomNum(0, 2);
			if(randomScript == 0)
				script[0] = "§7....";
			if(randomScript == 1)
				script[0] = "§7(할 말이 없는것 같다.)";
			if(randomScript == 2)
				script[0] = "§7....?";
			return script;
		}

		String[] script = scriptString.split("%enter%");
		for(int count=0;count < script.length; count++)
			script[count] = script[count].replace("%player%", player.getName());
		return script;
	}
}