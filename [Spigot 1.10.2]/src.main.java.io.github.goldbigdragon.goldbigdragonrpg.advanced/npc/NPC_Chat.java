package npc;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_Chat;
import util.YamlLoader;



public class NPC_Chat extends Util_Chat
{
	public void NPCTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();

		String NPCuuid = u.getString(player, (byte)3);
	  	YamlLoader npcScriptYaml = new YamlLoader();
		
	  	if(npcScriptYaml.isExit("NPC/NPCData/"+ NPCuuid +".yml") == false)
	  	{
	  		npc.NPC_Config NPCC = new npc.NPC_Config();
	  		NPCC.NPCNPCconfig(NPCuuid);
	  	}
		npcScriptYaml.getConfig("NPC/NPCData/"+ NPCuuid +".yml");
	  	YamlLoader npcYaml = new YamlLoader();
		npcYaml.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		npc.NPC_GUI NPGUI = new npc.NPC_GUI();
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)4))
		{
		case "SaleSetting1":
			if(isIntMinMax(Message, player, -1000, 1000))
			{
				u.setInt(player, (byte)0, Integer.parseInt(Message));
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.setString(player, (byte)4,"SaleSetting2");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 몇 % 세일을 하실건가요? (0 ~ 100 사이 값)");
			}
			return;
		case "SaleSetting2":
			if(isIntMinMax(Message, player, 0, 100))
			{
				npcYaml.set("Sale.Enable", true);
				npcYaml.set("Sale.Minlove", u.getInt(player, (byte)0));
				npcYaml.set("Sale.discount", Integer.parseInt(Message));
				npcYaml.saveConfig();
				new npc.NPC_GUI().MainGUI(player, u.getString(player, (byte)2), player.isOp());
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "PresentLove":
			if(isIntMinMax(Message, player, -1000, 1000))
			{
				npcYaml.set("Present."+u.getInt(player, (byte)0)+".love", Integer.parseInt(Message));
				npcYaml.saveConfig();
				new npc.NPC_GUI().PresentSettingGUI(player, u.getString(player, (byte)2));
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "NUC"://NPC'sUpgradeCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				npcYaml.set("Job.UpgradeRecipe."+u.getString(player, (byte)6),  Integer.parseInt(Message));
				npcYaml.saveConfig();
				npc.NPC_GUI NGUI = new npc.NPC_GUI();
				NGUI.UpgraderGUI(player, (short) 0, u.getString(player, (byte)8));
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "NPC_TNL"://NPC_TalkNeedLove
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				switch(u.getString(player, (byte)5))
				{
				case "NT":
					npcScriptYaml.set("NatureTalk."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				case "NN":
					npcScriptYaml.set("NearByNEWS."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				case "AS":
					npcScriptYaml.set("AboutSkills."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				}
				npcScriptYaml.saveConfig();
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
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
					npcScriptYaml.set("NatureTalk."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				case "NN":
					npcScriptYaml.set("NearByNEWS."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				case "AS":
					npcScriptYaml.set("AboutSkills."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				}
				npcScriptYaml.saveConfig();
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));
				u.clearAll(player);
			}
			return;
		case "NPC_TS"://NPC_TalkScript
			switch(u.getString(player, (byte)5))
			{
			case "NT":
				npcScriptYaml.set("NatureTalk."+u.getString(player, (byte)6)+".Script",event.getMessage());
				break;
			case "NN":
				npcScriptYaml.set("NearByNEWS."+u.getString(player, (byte)6)+".Script", event.getMessage());
				break;
			case "AS":
				npcScriptYaml.set("AboutSkills."+u.getString(player, (byte)6)+".Script",event.getMessage());
				break;
			}
			npcScriptYaml.saveConfig();
			NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));

			u.clearAll(player);
			return;
		case "NPC_TS2"://NPC_TalkScript2
			switch(u.getString(player, (byte)5))
			{
			case "AS":
				npcScriptYaml.set("AboutSkills."+u.getString(player, (byte)6)+".AlreadyGetScript",event.getMessage());
				break;
			}
			npcScriptYaml.saveConfig();
			NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));

			u.clearAll(player);
			return;
		case "WDN"://WarpDisplayName
			npcScriptYaml.set("Job.WarpList."+u.getInt(player, (byte)4)+".DisplayName",event.getMessage());
			npcScriptYaml.saveConfig();
			player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : "+ChatColor.YELLOW+event.getMessage()+ChatColor.DARK_AQUA+" 워프 지점의 이동 비용을 입력 하세요!");
			u.setString(player, (byte)4,"WC");
			return;
		case "WC"://WarpCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				npcScriptYaml.set("Job.WarpList."+u.getInt(player, (byte)4)+".Cost",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				NPGUI.WarpMainGUI(player, 0, u.getString(player, (byte)2));

				u.clearAll(player);
			}
			return;
		case "FixRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				npcScriptYaml.set("Job.FixRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 수리 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 아이템 내구도 10포인트당 수리 비용을 입력 하세요! ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "10Point");
			}
			return;
		case "10Point":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				npcScriptYaml.set("Job.10PointFixDeal",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)2), player.isOp());

				u.clearAll(player);
			}
			return;
		case "GoodRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				npcScriptYaml.set("Job.GoodRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 버프 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 최대 버프 세기를 설정해 주세요. ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "BuffMaxStrog");
				
			}
			return;
		case "BuffMaxStrog":
			if(isIntMinMax(Message, player, 1,100))
			{
				npcScriptYaml.set("Job.BuffMaxStrog",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 최대 버프 세기는 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 버프 시간을 설정해 주세요. (초 단위)");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "BuffTime");
			}
			return;
		case "BuffTime":
			if(isIntMinMax(Message, player, 1, Integer.MAX_VALUE))
			{
				npcScriptYaml.set("Job.BuffTime",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 최대 버프 시간은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 복채 비용을 설정해 주세요. ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "Deal");
			}
			return;
		case "SuccessRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				npcScriptYaml.set("Job.SuccessRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 룬 장착 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 룬 장착 비용을 입력 하세요! ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "Deal");
			}
			return;
		case "Deal":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				npcScriptYaml.set("Job.Deal",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)2), player.isOp());

				u.clearAll(player);
			}
			return;
		case "NPCJL" ://NPC Job Lord 
			event.setCancelled(true);
		  	YamlLoader configYaml = new YamlLoader();
		  	YamlLoader jobYaml = new YamlLoader();
			jobYaml.getConfig("Skill/JobList.yml");
			configYaml.getConfig("config.yml");
			boolean isExitJob = false;
			Object[] Job = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(int count = 0; count < Job.length; count++)
			{
				Object[] a = jobYaml.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
				for(int counter=0;counter<a.length;counter++)
				{
					if(a[counter].toString().compareTo(Message)==0 && a[counter].toString().compareTo(configYaml.getString("Server.DefaultJob"))!=0)
						isExitJob = true;
				}
			}
			if(isExitJob == true)
			{
				npcScriptYaml.getConfig("NPC/NPCData/"+ u.getString(player, (byte)2) +".yml");
				npcScriptYaml.removeKey("Job");
				npcScriptYaml.set("Job.Type", "Master");
				npcScriptYaml.set("Job.Job", Message);
				npcScriptYaml.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)3),player.isOp());
				u.clearAll(player);
			}
			else
			{
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[NPC] : 이 NPC는 어떤 직업으로 전직 시켜 주는 교관인가요?");
				for(int count = 0; count < Job.length; count++)
				{
					Object[] a = jobYaml.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
					for(int counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().compareTo(configYaml.getString("Server.DefaultJob"))!=0)
							player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job[count].toString()+" ━ "+ChatColor.YELLOW + a[counter].toString());
					}
				}
			}
			return;
		}
		return;
	}

}
