package npc;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import effect.SoundEffect;
import user.UserDataObject;
import util.ChatUtil;
import util.YamlLoader;

public class NpcChat extends ChatUtil
{
	public void NPCTypeChatting(AsyncPlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();

		String NPCuuid = u.getNPCuuid(player);
		if(NPCuuid == null || NPCuuid.length() < 20)
			NPCuuid = u.getString(player, (byte)3);
	  	YamlLoader npcScriptYaml = new YamlLoader();
		
	  	if(! npcScriptYaml.isExit("NPC/NPCData/"+ NPCuuid +".yml"))
	  		new npc.NpcConfig().NPCNPCconfig(NPCuuid);
	  	
		npcScriptYaml.getConfig("NPC/NPCData/"+ NPCuuid +".yml");
		npc.NpcGui NPGUI = new npc.NpcGui();
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)4))
		{
		case "SaleSetting1":
			if(isIntMinMax(Message, player, -1000, 1000))
			{
				u.setInt(player, (byte)0, Integer.parseInt(Message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.setString(player, (byte)4,"SaleSetting2");
				player.sendMessage("��3[NPC] : �� % ������ �Ͻǰǰ���? (0 ~ 100 ���� ��)");
			}
			return;
		case "SaleSetting2":
			if(isIntMinMax(Message, player, 0, 100))
			{
				npcScriptYaml.set("Sale.Enable", true);
				npcScriptYaml.set("Sale.Minlove", u.getInt(player, (byte)0));
				npcScriptYaml.set("Sale.discount", Integer.parseInt(Message));
				npcScriptYaml.saveConfig();
				new npc.NpcGui().MainGUI(player, u.getString(player, (byte)2), player.isOp());
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "PresentLove":
			if(isIntMinMax(Message, player, -1000, 1000))
			{
				npcScriptYaml.set("Present."+u.getInt(player, (byte)0)+".love", Integer.parseInt(Message));
				npcScriptYaml.saveConfig();
				new npc.NpcGui().PresentSettingGUI(player, u.getString(player, (byte)2));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "NUC"://NPC'sUpgradeCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				npcScriptYaml.set("Job.UpgradeRecipe."+u.getString(player, (byte)6),  Integer.parseInt(Message));
				npcScriptYaml.saveConfig();
				npc.NpcGui NGUI = new npc.NpcGui();
				NGUI.UpgraderGUI(player, (short) 0, u.getString(player, (byte)8));
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
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
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage("��3[���] : �׷��ٸ� �ִ� ���� ȣ������ �ʿ��Ѱ���?");
				player.sendMessage("��a(��e0��a ~ ��e"+Integer.MAX_VALUE+"��a)");
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
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
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
			player.sendMessage("��3[NPC] : ��e"+event.getMessage()+"��3 ���� ������ �̵� ����� �Է� �ϼ���!");
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
				player.sendMessage("��3[NPC] : �� NPC�� ���� �������� ��f"+ChatColor.stripColor(event.getMessage())+"%��3�� �Ǿ����ϴ�!");
				player.sendMessage("��3[NPC] : NPC�� ������ ������ 10����Ʈ�� ���� ����� �Է� �ϼ���! ");

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
				player.sendMessage("��3[NPC] : �� NPC�� ���� �������� ��f"+ChatColor.stripColor(event.getMessage())+"%��3�� �Ǿ����ϴ�!");
				player.sendMessage("��3[NPC] : NPC�� �ִ� ���� ���⸦ ������ �ּ���. ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "BuffMaxStrog");
				
			}
			return;
		case "BuffMaxStrog":
			if(isIntMinMax(Message, player, 1,100))
			{
				npcScriptYaml.set("Job.BuffMaxStrog",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				player.sendMessage("��3[NPC] : �� NPC�� �ִ� ���� ����� ��f"+ChatColor.stripColor(event.getMessage())+"��3�� �Ǿ����ϴ�!");
				player.sendMessage("��3[NPC] : NPC�� ���� �ð��� ������ �ּ���. (�� ����)");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "BuffTime");
			}
			return;
		case "BuffTime":
			if(isIntMinMax(Message, player, 1, Integer.MAX_VALUE))
			{
				npcScriptYaml.set("Job.BuffTime",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				player.sendMessage("��3[NPC] : �� NPC�� �ִ� ���� �ð��� ��f"+ChatColor.stripColor(event.getMessage())+"��3�� �Ǿ����ϴ�!");
				player.sendMessage("��3[NPC] : NPC�� ��ä ����� ������ �ּ���. ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "Deal");
			}
			return;
		case "SuccessRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				npcScriptYaml.set("Job.SuccessRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				npcScriptYaml.saveConfig();
				player.sendMessage("��3[NPC] : �� NPC�� �� ���� �������� ��f"+ChatColor.stripColor(event.getMessage())+"%��3�� �Ǿ����ϴ�!");
				player.sendMessage("��3[NPC] : NPC�� �� ���� ����� �Է� �ϼ���! ");

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
					if(a[counter].toString().equals(Message) &&!a[counter].toString().equals(configYaml.getString("Server.DefaultJob")))
						isExitJob = true;
				}
			}
			if(isExitJob)
			{
				npcScriptYaml.removeKey("Job");
				npcScriptYaml.set("Job.Type", "Master");
				npcScriptYaml.set("Job.Job", Message);
				npcScriptYaml.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)2),player.isOp());
				u.clearAll(player);
			}
			else
			{
				player.sendMessage("��d[NPC] : �� NPC�� � �������� ���� ���� �ִ� �����ΰ���?");
				for(int count = 0; count < Job.length; count++)
				{
					Object[] a = jobYaml.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
					for(int counter=0;counter<a.length;counter++)
					{
						if(!a[counter].toString().equals(configYaml.getString("Server.DefaultJob")))
							player.sendMessage("��d "+Job[count].toString()+" �� ��e"+ a[counter].toString());
					}
				}
			}
			return;
		}
		return;
	}
}
