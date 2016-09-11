package GoldBigDragon_RPG.ETC;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class NPC
{
	public String[] getScript(Player player, char ScriptType)
	{
		Object_UserData u = new Object_UserData();
		if(ScriptType == -1)
		{
			String[] script = new String[1];
			script[0] = "a";
			return script;
		}
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		String TalkSubject = "NatureTalk";

		GoldBigDragon_RPG.Config.NPCconfig NPCconfig = new GoldBigDragon_RPG.Config.NPCconfig();
		NPCconfig.PlayerNPCconfig(player, u.getNPCuuid(player));
		NPCconfig.NPCNPCconfig(u.getNPCuuid(player));

		YamlManager PlayerNPC = YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId() +".yml");
		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");

	  	if(ScriptType == 2)
	  		TalkSubject = "NatureTalk";
	  	if(ScriptType == 4)
	  		TalkSubject = "NearByNEWS";
	  	if(ScriptType == 6)
	  		TalkSubject = "AboutSkills";

	  	int Size = NPCscript.getConfigurationSection(TalkSubject).getKeys(false).toArray().length;
		
		if(Size <= 0)
		{
			byte randomScript = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 2);
			String[] script = new String[1];
			if(randomScript == 0)
				script[0] = ChatColor.GRAY + "....";
			if(randomScript == 1)
				script[0] = ChatColor.GRAY + "(할 말이 없는것 같다.)";
			if(randomScript == 2)
				script[0] = ChatColor.GRAY + "....?";
			return script;
		}

		boolean scriptget = false;
		String scriptString = "";
		
		boolean textOK = false;
		byte randomScript = 0;
		for(int counter = 1; counter < 125; counter++)
		{
			randomScript = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(1, Size);
			if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") >= NPCscript.getInt(TalkSubject + "."+randomScript+".love"))
			{
				if(NPCscript.getInt(TalkSubject + "."+randomScript+".loveMax") != 0)
				{
					if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") <= NPCscript.getInt(TalkSubject + "."+randomScript+".loveMax"))
						textOK = true;
				}
				else
					textOK = true;
				break;
			}
		}
		if(textOK)
		{
			scriptString = NPCscript.getString(TalkSubject + "."+randomScript+".Script");
			scriptget = true;
			YamlManager SkillList = YC.getNewConfig("Skill/SkillList.yml");
			String Skillname = NPCscript.getString(TalkSubject + "."+randomScript+".giveSkill");
			if(ScriptType == 6&&SkillList.contains(NPCscript.getString(TalkSubject + "."+randomScript+".giveSkill"))==true)
			{
				YamlManager JobList = YC.getNewConfig("Skill/JobList.yml");
				YamlManager PlayerSkill = YC.getNewConfig("Skill/PlayerData/" + player.getUniqueId() +".yml");
				String Categori = JobList.getString("Mabinogi.Added."+NPCscript.getString(TalkSubject + "."+randomScript+".giveSkill"));
				if(PlayerSkill.contains("Mabinogi."+Categori+"."+NPCscript.getString(TalkSubject + "."+randomScript+".giveSkill"))==false)
				{
					GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
					PlayerSkill.set("Mabinogi."+Categori+"."+Skillname, 1);
					PlayerSkill.saveConfig();
					s.SP(player, Sound.LEVEL_UP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"[새로운 스킬을 획득 하였습니다!] "+ChatColor.YELLOW+""+ChatColor.BOLD+""+ChatColor.UNDERLINE+Skillname);
				}
				else
				{
					scriptString = NPCscript.getString(TalkSubject + "."+randomScript+".AlreadyGetScript");
				}
			}
		}
		if(scriptget == false)
		{
			String[] script = new String[1];
			randomScript = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 2);
			if(randomScript == 0)
				script[0] = ChatColor.GRAY + "....";
			if(randomScript == 1)
				script[0] = ChatColor.GRAY + "(할 말이 없는것 같다.)";
			if(randomScript == 2)
				script[0] = ChatColor.GRAY + "....?";
			return script;
		}

		String[] script = scriptString.split("%enter%");
		for(byte count=0;count < script.length; count++)
			script[count] = script[count].replace("%player%", player.getName());
		return script;
	}

	public void InventoryClose_NPC(InventoryCloseEvent event)
	{
		if(event.getInventory().getSize() > 9)
			if(event.getInventory().getItem(13)!=null)
				event.getPlayer().getInventory().addItem(event.getInventory().getItem(13));
		return;
	}
}