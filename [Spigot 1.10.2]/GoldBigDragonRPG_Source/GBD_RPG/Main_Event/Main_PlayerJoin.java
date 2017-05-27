package GBD_RPG.Main_Event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.User.User_Object;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Main_PlayerJoin implements Listener
{
	@EventHandler
	private void PlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		String playerUUID = player.getUniqueId().toString();
		new User_Object(player);
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
	  	if(YC.isExit("Skill/PlayerData/" + playerUUID+".yml") == false)
	  		new GBD_RPG.Skill.Skill_Config().CreateNewPlayerSkill(player);
	  	else
	  		new GBD_RPG.Job.Job_Main().PlayerFixAllSkillAndJobYML(player);

	  	if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			new GBD_RPG.Util.Util_Player().teleportToCurrentArea(player, true);
			new GBD_RPG.Dungeon.Dungeon_Main().EraseAllDungeonKey(player, false);
			GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
			GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
		}
		if(new GBD_RPG.Corpse.Corpse_Main().DeathCapture(player,true))
			new GBD_RPG.Corpse.Corpse_Main().CreateCorpse(player);
		
    	new GBD_RPG.Main_Main.Main_ServerOption().MagicSpellCatch();
    	new GBD_RPG.Main_Main.Main_ServerOption().CitizensCatch();

		new UserData_Object().UserDataInit(player);
		
		if(player.isOp() == true)
			new GBD_RPG.Effect.Effect_Packet().sendTitleSubTitle(player,"\'§e/오피박스\'", "\'§eGoldBigDragonAdvanced 가이드 및 서버 설정이 가능합니다.\'", (byte)1,(byte)10, (byte)1);
		else
		{
			YamlManager Config = YC.getNewConfig("config.yml");
			if(Config.getInt("Event.DropChance")>=2||Config.getInt("Event.Multiple_EXP_Get")>=2||Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2||Config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
			{
				String alert = "[";
				if(Config.getInt("Event.DropChance")>=2)
					alert =alert+ "드롭률 증가 "+Config.getInt("Event.DropChance")+"배";
				if(Config.getInt("Event.DropChance")>=2)
					alert = alert+", ";
				if(Config.getInt("Event.Multiple_EXP_Get")>=2)
					alert = alert + "경험치 " + Config.getInt("Event.Multiple_EXP_Get")+"배 획득";
				if(Config.getInt("Event.Multiple_EXP_Get")>=2)
					alert = alert+", ";
				if(Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2)
					alert = alert +"스텟 포인트 "+Config.getInt("Event.Multiple_Level_Up_StatPoint")+"배 획득";
				if(Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2)
					alert = alert+", ";
				if(Config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
					alert = alert +"스킬 포인트 " +Config.getInt("Event.Multiple_Level_Up_SkillPoint")+"배 획득";
				alert = alert+"]";
				new GBD_RPG.Effect.Effect_Packet().sendTitleSubTitle(player, "\'현재 이벤트가 진행중입니다.\'", "\'"+alert+"\'", (byte)1, (byte)10, (byte)1);
			}
		}
	  	if(YC.isExit("Quest/PlayerData/" + playerUUID+".yml") == false)
	  	{
	  	    new GBD_RPG.Quest.Quest_Config().CreateNewPlayerConfig(player);

			YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
			for(byte count = 0; count < YC.getNewConfig("ETC/NewBie.yml").getConfigurationSection("SupportItem").getKeys(false).toArray().length;count++)
				if(NewBieYM.getItemStack("SupportItem."+count) != null)
					player.getInventory().addItem(NewBieYM.getItemStack("SupportItem."+count));
			player.teleport(new Location(Bukkit.getWorld(NewBieYM.getString("TelePort.World")), NewBieYM.getInt("TelePort.X"), NewBieYM.getInt("TelePort.Y"), NewBieYM.getInt("TelePort.Z"), NewBieYM.getInt("TelePort.Yaw"), NewBieYM.getInt("TelePort.Pitch")));
	  	}
		new GBD_RPG.Util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new GBD_RPG.User.Equip_GUI().FriendJoinQuitMessage(player, true);

		if(YC.getNewConfig("config.yml").getString("Server.JoinMessage") != null)
			event.setJoinMessage(YC.getNewConfig("config.yml").getString("Server.JoinMessage").replace("%player%",event.getPlayer().getName()));
		else
			event.setJoinMessage(null);
	}
}
