package event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import user.UserData_Object;
import user.User_Object;
import util.YamlLoader;



public class Main_PlayerJoin implements Listener
{
	@EventHandler
	private void PlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		String playerUUID = player.getUniqueId().toString();
		new User_Object(player);
	  	YamlLoader config = new YamlLoader();
		config.getConfig("config.yml");
	  	if(config.isExit("Skill/PlayerData/" + playerUUID+".yml") == false)
	  		new skill.Skill_Config().CreateNewPlayerSkill(player);
	  	else
	  		new job.Job_Main().PlayerFixAllSkillAndJobYML(player);

	  	if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			new util.Util_Player().teleportToCurrentArea(player, true);
			new dungeon.Dungeon_Main().EraseAllDungeonKey(player, false);
			main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
			main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
		}
		if(new corpse.Corpse_Main().DeathCapture(player,true))
			new corpse.Corpse_Main().CreateCorpse(player);
		
    	new main.Main_ServerOption().MagicSpellCatch();
    	new main.Main_ServerOption().CitizensCatch();

		new UserData_Object().UserDataInit(player);
		
		if(player.isOp() == true)
			new effect.SendPacket().sendTitleSubTitle(player,"\'§e/오피박스\'", "\'§eGoldBigDragonAdvanced 가이드 및 서버 설정이 가능합니다.\'", (byte)1,(byte)10, (byte)1);
		else
		{
			if(config.getInt("Event.DropChance")>=2||config.getInt("Event.Multiple_EXP_Get")>=2||config.getInt("Event.Multiple_Level_Up_StatPoint")>=2||config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
			{
				String alert = "[";
				if(config.getInt("Event.DropChance")>=2)
					alert =alert+ "드롭률 증가 "+config.getInt("Event.DropChance")+"배";
				if(config.getInt("Event.DropChance")>=2)
					alert = alert+", ";
				if(config.getInt("Event.Multiple_EXP_Get")>=2)
					alert = alert + "경험치 " + config.getInt("Event.Multiple_EXP_Get")+"배 획득";
				if(config.getInt("Event.Multiple_EXP_Get")>=2)
					alert = alert+", ";
				if(config.getInt("Event.Multiple_Level_Up_StatPoint")>=2)
					alert = alert +"스텟 포인트 "+config.getInt("Event.Multiple_Level_Up_StatPoint")+"배 획득";
				if(config.getInt("Event.Multiple_Level_Up_StatPoint")>=2)
					alert = alert+", ";
				if(config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
					alert = alert +"스킬 포인트 " +config.getInt("Event.Multiple_Level_Up_SkillPoint")+"배 획득";
				alert = alert+"]";
				new effect.SendPacket().sendTitleSubTitle(player, "\'현재 이벤트가 진행중입니다.\'", "\'"+alert+"\'", (byte)1, (byte)10, (byte)1);
			}
		}
	  	if(config.isExit("Quest/PlayerData/" + playerUUID+".yml") == false)
	  	{
	  	    new quest.Quest_Config().CreateNewPlayerConfig(player);

		  	YamlLoader newbieYaml = new YamlLoader();
			newbieYaml.getConfig("ETC/NewBie.yml");
			for(int count = 0; count < newbieYaml.getConfigurationSection("SupportItem").getKeys(false).toArray().length;count++)
				if(newbieYaml.getItemStack("SupportItem."+count) != null)
					player.getInventory().addItem(newbieYaml.getItemStack("SupportItem."+count));
			player.teleport(new Location(Bukkit.getWorld(newbieYaml.getString("TelePort.World")), newbieYaml.getInt("TelePort.X"), newbieYaml.getInt("TelePort.Y"), newbieYaml.getInt("TelePort.Z"), newbieYaml.getInt("TelePort.Yaw"), newbieYaml.getInt("TelePort.Pitch")));
	  	}
		new util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new user.Equip_GUI().FriendJoinQuitMessage(player, true);

		if(config.getString("Server.JoinMessage") != null)
			event.setJoinMessage(config.getString("Server.JoinMessage").replace("%player%",event.getPlayer().getName()));
		else
			event.setJoinMessage(null);
	}
}
