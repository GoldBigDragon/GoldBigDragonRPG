package GBD.GoldBigDragon_Advanced.ETC;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class NPC
{
	public String[] getScript(Player player, char ScriptType)
	{
		if(ScriptType == -1)
		{
			String[] script = new String[1];
			script[0] = "a";
			return script;
		}
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		String TalkSubject = "NatureTalk";
		int randomScript = 0;

		GBD.GoldBigDragon_Advanced.Config.NPCconfig NPCconfig = new GBD.GoldBigDragon_Advanced.Config.NPCconfig();
		NPCconfig.PlayerNPCconfig(player, Main.PlayerClickedNPCuuid.get(player));
		NPCconfig.NPCNPCconfig(Main.PlayerClickedNPCuuid.get(player));

		YamlManager PlayerNPC = GUI_YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId() +".yml");
		YamlManager NPCscript = GUI_YC.getNewConfig("NPC/NPCData/"+ Main.PlayerClickedNPCuuid.get(player) +".yml");

	  	if(ScriptType == 2)
	  		TalkSubject = "NatureTalk";
	  	if(ScriptType == 4)
	  		TalkSubject = "NearByNEWS";
	  	if(ScriptType == 6)
	  		TalkSubject = "AboutSkills";

	  	Object[] arealist = NPCscript.getConfigurationSection(TalkSubject).getKeys(false).toArray();
		
		if(arealist.length <= 0)
		{
			String[] script = new String[1];
			randomScript  = (char) new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(0, 2);
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
		
		for(int counter = 1; counter < 125; counter++)
		{
			randomScript  = new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(1, arealist.length);
			if(PlayerNPC.getInt(Main.PlayerClickedNPCuuid.get(player)+".love") >= NPCscript.getInt(TalkSubject + "."+randomScript+".love"))
			{
				scriptString = NPCscript.getString(TalkSubject + "."+randomScript+".Script");
				scriptget = true;
				YamlManager SkillList = GUI_YC.getNewConfig("Skill/SkillList.yml");
				String Skillname = NPCscript.getString(TalkSubject + "."+randomScript+".giveSkill");
				if(ScriptType == 6&&SkillList.contains(NPCscript.getString(TalkSubject + "."+randomScript+".giveSkill"))==true)
				{
					YamlManager JobList = GUI_YC.getNewConfig("Skill/JobList.yml");
					YamlManager PlayerSkill = GUI_YC.getNewConfig("Skill/PlayerData/" + player.getUniqueId() +".yml");
					String Categori = JobList.getString("Mabinogi.Added."+NPCscript.getString(TalkSubject + "."+randomScript+".giveSkill"));
					if(PlayerSkill.contains("Mabinogi."+Categori+"."+NPCscript.getString(TalkSubject + "."+randomScript+".giveSkill"))==false)
					{
						GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
						PlayerSkill.set("Mabinogi."+Categori+"."+Skillname, 1);
						PlayerSkill.saveConfig();
						s.SP(player, Sound.LEVEL_UP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"[새로운 스킬을 획득 하였습니다!] "+ChatColor.YELLOW+""+ChatColor.BOLD+""+ChatColor.UNDERLINE+Skillname);
						break;
					}
					else
					{
						scriptString = NPCscript.getString(TalkSubject + "."+randomScript+".AlreadyGetScript");
						break;
					}
				}
				break;
			}
		}
		if(scriptget == false)
		{
			String[] script = new String[1];
			randomScript  = (char) new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(0, 2);
			if(randomScript == 0)
				script[0] = ChatColor.GRAY + "....";
			if(randomScript == 1)
				script[0] = ChatColor.GRAY + "(할 말이 없는것 같다.)";
			if(randomScript == 2)
				script[0] = ChatColor.GRAY + "....?";
			return script;
		}

		String[] script = scriptString.split("%enter%");
		for(int count=0;count < script.length; count++)
		{
			script[count] = script[count].replace("%player%", player.getName());
		}
		return script;
	}

	public void InventoryClose_NPC(InventoryCloseEvent event)
	{
		if(event.getInventory().getItem(13)!=null)
			event.getPlayer().getInventory().addItem(event.getInventory().getItem(13));
		return;
	}
}