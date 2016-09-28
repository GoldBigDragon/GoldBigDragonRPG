package GBD_RPG.NPC;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_Chat;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class NPC_Chat extends Util_Chat
{
	public void NPCTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		GBD_RPG.Effect.Effect_Sound sound = new GBD_RPG.Effect.Effect_Sound();
		Player player = event.getPlayer();

		String NPCuuid = u.getString(player, (byte)3);
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		
	  	if(YC.isExit("NPC/NPCData/"+ NPCuuid +".yml") == false)
	  	{
	  		GBD_RPG.NPC.NPC_Config NPCC = new GBD_RPG.NPC.NPC_Config();
	  		NPCC.NPCNPCconfig(NPCuuid);
	  	}
		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ NPCuuid +".yml");
		GBD_RPG.NPC.NPC_GUI NPGUI = new GBD_RPG.NPC.NPC_GUI();
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)4))
		{
		case "SaleSetting1":
			if(isIntMinMax(Message, player, -1000, 1000))
			{
				u.setInt(player, (byte)0, Integer.parseInt(Message));
				sound.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.setString(player, (byte)4,"SaleSetting2");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 몇 % 세일을 하실건가요? (0 ~ 100 사이 값)");
			}
			return;
		case "SaleSetting2":
			if(isIntMinMax(Message, player, 0, 100))
			{
				YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				NPCConfig.set("Sale.Enable", true);
				NPCConfig.set("Sale.Minlove", u.getInt(player, (byte)0));
				NPCConfig.set("Sale.discount", Integer.parseInt(Message));
				NPCConfig.saveConfig();
				new GBD_RPG.NPC.NPC_GUI().MainGUI(player, u.getString(player, (byte)2), player.isOp());
				sound.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "PresentLove":
			if(isIntMinMax(Message, player, -1000, 1000))
			{
				YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				NPCConfig.set("Present."+u.getInt(player, (byte)0)+".love", Integer.parseInt(Message));
				NPCConfig.saveConfig();
				new GBD_RPG.NPC.NPC_GUI().PresentSettingGUI(player, u.getString(player, (byte)2));
				sound.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "NUC"://NPC'sUpgradeCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				NPCConfig.set("Job.UpgradeRecipe."+u.getString(player, (byte)6),  Integer.parseInt(Message));
				NPCConfig.saveConfig();
				GBD_RPG.NPC.NPC_GUI NGUI = new GBD_RPG.NPC.NPC_GUI();
				NGUI.UpgraderGUI(player, (short) 0, u.getString(player, (byte)8));
				sound.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "NPC_TNL"://NPC_TalkNeedLove
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				switch(u.getString(player, (byte)5))
				{
				case "NT":
					NPCscript.set("NatureTalk."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				case "NN":
					NPCscript.set("NearByNEWS."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				case "AS":
					NPCscript.set("AboutSkills."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				}
				NPCscript.saveConfig();
				sound.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[대사] : 그렇다면 최대 몇의 호감도가 필요한가요?");
				player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				u.setType(player, "NPC");
				u.setString(player, (byte)4,"NPC_TNL2");
			}
			return;
		case "NPC_TNL2"://NPC_TalkNeedLove
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				switch(u.getString(player, (byte)5))
				{
				case "NT":
					NPCscript.set("NatureTalk."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				case "NN":
					NPCscript.set("NearByNEWS."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				case "AS":
					NPCscript.set("AboutSkills."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				}
				NPCscript.saveConfig();
				sound.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));
				u.clearAll(player);
			}
			return;
		case "NPC_TS"://NPC_TalkScript
			switch(u.getString(player, (byte)5))
			{
			case "NT":
				NPCscript.set("NatureTalk."+u.getString(player, (byte)6)+".Script",event.getMessage());
				break;
			case "NN":
				NPCscript.set("NearByNEWS."+u.getString(player, (byte)6)+".Script", event.getMessage());
				break;
			case "AS":
				NPCscript.set("AboutSkills."+u.getString(player, (byte)6)+".Script",event.getMessage());
				break;
			}
			NPCscript.saveConfig();
			NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));

			u.clearAll(player);
			return;
		case "NPC_TS2"://NPC_TalkScript2
			switch(u.getString(player, (byte)5))
			{
			case "AS":
				NPCscript.set("AboutSkills."+u.getString(player, (byte)6)+".AlreadyGetScript",event.getMessage());
				break;
			}
			NPCscript.saveConfig();
			NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));

			u.clearAll(player);
			return;
		case "WDN"://WarpDisplayName
			NPCscript.set("Job.WarpList."+u.getInt(player, (byte)4)+".DisplayName",event.getMessage());
			NPCscript.saveConfig();
			player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : "+ChatColor.YELLOW+event.getMessage()+ChatColor.DARK_AQUA+" 워프 지점의 이동 비용을 입력 하세요!");
			u.setString(player, (byte)4,"WC");
			return;
		case "WC"://WarpCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.WarpList."+u.getInt(player, (byte)4)+".Cost",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.WarpMainGUI(player, 0, u.getString(player, (byte)2));

				u.clearAll(player);
			}
			return;
		case "FixRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.FixRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 수리 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 아이템 내구도 10포인트당 수리 비용을 입력 하세요! ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "10Point");
			}
			return;
		case "10Point":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.10PointFixDeal",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)2), player.isOp());

				u.clearAll(player);
			}
			return;
		case "GoodRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.GoodRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 버프 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 최대 버프 세기를 설정해 주세요. ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "BuffMaxStrog");
				
			}
			return;
		case "BuffMaxStrog":
			if(isIntMinMax(Message, player, 1,100))
			{
				NPCscript.set("Job.BuffMaxStrog",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 최대 버프 세기는 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 버프 시간을 설정해 주세요. (초 단위)");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "BuffTime");
			}
			return;
		case "BuffTime":
			if(isIntMinMax(Message, player, 1, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.BuffTime",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 최대 버프 시간은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 복채 비용을 설정해 주세요. ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "Deal");
			}
			return;
		case "SuccessRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.SuccessRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 룬 장착 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 룬 장착 비용을 입력 하세요! ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "Deal");
			}
			return;
		case "Deal":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.Deal",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)2), player.isOp());

				u.clearAll(player);
			}
			return;
		case "NPCJL" ://NPC Job Lord 
			event.setCancelled(true);
			YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
			YamlManager Config = YC.getNewConfig("config.yml");
			boolean isExitJob = false;
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short count = 0; count < Job.length; count++)
			{
				Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
				for(short counter=0;counter<a.length;counter++)
				{
					if(a[counter].toString().compareTo(Message)==0 && a[counter].toString().compareTo(Config.getString("Server.DefaultJob"))!=0)
						isExitJob = true;
				}
			}
			if(isExitJob == true)
			{
				NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getString(player, (byte)2) +".yml");
				NPCscript.removeKey("Job");
				NPCscript.set("Job.Type", "Master");
				NPCscript.set("Job.Job", Message);
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)3),player.isOp());
				u.clearAll(player);
			}
			else
			{
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[NPC] : 이 NPC는 어떤 직업으로 전직 시켜 주는 교관인가요?");
				for(short count = 0; count < Job.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
					for(short counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().compareTo(Config.getString("Server.DefaultJob"))!=0)
							player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job[count].toString()+" ━ "+ChatColor.YELLOW + a[counter].toString());
					}
				}
			}
			return;
		}
		return;
	}

}
