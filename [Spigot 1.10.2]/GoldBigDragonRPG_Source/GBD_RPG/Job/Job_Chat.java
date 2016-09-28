package GBD_RPG.Job;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_Chat;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Job_Chat extends Util_Chat
{
	public void JobTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		event.setCancelled(true);
		GBD_RPG.Effect.Effect_Sound sound = new GBD_RPG.Effect.Effect_Sound();
		Player player = event.getPlayer();
		GBD_RPG.Skill.OPboxSkill_GUI SKGUI = new GBD_RPG.Skill.OPboxSkill_GUI();
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);

		String Message = ChatColor.stripColor(event.getMessage());
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
		GBD_RPG.Job.Job_GUI JGUI = new GBD_RPG.Job.Job_GUI();

		switch(u.getString(player, (byte)1))
		{
		case "CC"://CreateCategory
			if(JobList.getConfigurationSection("Mabinogi").getKeys(false).toString().contains(Message) == false)
			{
				JobList.createSection("Mabinogi."+Message);
				JobList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 0.5F);
				JGUI.Mabinogi_ChooseCategory(player,(short) 0);

				YamlManager Config  = YC.getNewConfig("config.yml");
				Config.set("Time.LastSkillChanged", new GBD_RPG.Util.Util_Number().RandomNum(0, 100000)-new GBD_RPG.Util.Util_Number().RandomNum(0, 100000));
				Config.saveConfig();
				
				new GBD_RPG.Job.Job_Main().AllPlayerFixAllSkillAndJobYML();
				u.clearAll(player);
			}
			else
			{
				player.sendMessage(ChatColor.RED + "[카테고리] : 이미 존재하는 카테고리 이름입니다!");
  				sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
			return;
		case "CJ"://CreateJob
			JobList.set("MapleStory."+Message+"."+Message+".NeedLV",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedSTR",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedDEX",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedINT",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedWILL",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedLUK",0);
			JobList.set("MapleStory."+Message+"."+Message+".PrevJob","null");
			JobList.set("MapleStory."+Message+"."+Message+".IconID",267);
			JobList.set("MapleStory."+Message+"."+Message+".IconData",0);
			JobList.set("MapleStory."+Message+"."+Message+".IconAmount",1);
			JobList.createSection("MapleStory."+Message+"."+Message+".Skill");
			JobList.saveConfig();
			sound.SP(player, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 0.5F);
			JGUI.MapleStory_JobSetting(player,Message);
			u.clearAll(player);
			return;
		case "CJL"://CreateJobLevel (승급 만들기)
			String JobName2 = u.getString(player, (byte)2);
			short NowJobLevel = (short) JobList.getConfigurationSection("MapleStory."+JobName2).getKeys(false).size();
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedLV",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedSTR",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedDEX",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedINT",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedWILL",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedLUK",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".PrevJob","null");
			JobList.set("MapleStory."+JobName2+"."+Message+".IconID",267);
			JobList.set("MapleStory."+JobName2+"."+Message+".IconData",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".IconAmount",1);
			JobList.createSection("MapleStory."+JobName2+"."+Message+".Skill");
			JobList.saveConfig();
			sound.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
			JGUI.MapleStory_JobSetting(player, JobName2);
			u.clearAll(player);
			return;
		case "JNL"://JobNeedLevel (승급을 위한 필요 레벨)
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedLV",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNS");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.STR+"을 설정하세요.");
			}
			return;
		case "JNS" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedSTR",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JND");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.DEX+"를 설정하세요.");
			}
			return;
		case "JND" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedDEX",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNI");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.INT+"을 설정하세요.");
			}
			return;
		case "JNI" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedINT",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNW");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.WILL+"를 설정하세요.");
			}
			return;
		case "JNW" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedWILL",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNLU");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.LUK+"을 설정하세요.");
			}
			return;
		case "JNLU" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedLUK",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNPJ");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : 어떤 직업이"+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +" 로 승급 가능하게 할까요?");
				
				Object[] Job2 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(short count = 0; count < Job2.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job2[count].toString()).getKeys(false).toArray();
					for(short counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().compareTo(JobNick2)!=0)
						player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job2[count].toString()+" ━ "+ChatColor.YELLOW + a[counter].toString());
					}
				}
				player.sendMessage(ChatColor.LIGHT_PURPLE + " 이전 직업이 무엇이든 상관 없을 경우 ━ "+ChatColor.YELLOW + "없음");
			}
			return;
		case "JNPJ" : 
			String JobName3 = u.getString(player, (byte)2);
			String JobNick2 = u.getString(player, (byte)3);
			Object[] Job2 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			boolean checked = false;
			if(Message.compareTo("없음")!=0)
			{
				for(short count = 0; count < Job2.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job2[count].toString()).getKeys(false).toArray();
					for(short counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().compareTo(ChatColor.stripColor(Message))==0)
						{
							checked = true;
							break;
						}
					}
				}
			}
			else
				checked = true;
			if(checked == true)
			{
				if(Message.compareTo("없음")!=0)
					JobList.set("MapleStory."+JobName3+"."+JobNick2+".PrevJob",ChatColor.stripColor(Message));
				else
					JobList.set("MapleStory."+JobName3+"."+JobNick2+".PrevJob","null");
					
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNICONID");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +" 를 나타내는 아이콘 ID는 무엇인가요?");
			}
			else
			{
				Object[] Job1 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(short count = 0; count < Job1.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job1[count].toString()).getKeys(false).toArray();
					for(short counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().compareTo(JobNick2)!=0)
						player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job2[count].toString()+" ━ "+ChatColor.YELLOW + a[counter].toString());
					}
				}
				player.sendMessage(ChatColor.LIGHT_PURPLE + " 이전 직업이 무엇이든 상관 없을 경우 ━ "+ChatColor.YELLOW + "없음");
			}
			return;
		case "JNICONID" : 
			if(isIntMinMax(Message, player, 1, 2267))
			{
				GBD_RPG.Main_Event.Main_Interact I = new GBD_RPG.Main_Event.Main_Interact();
				if(I.SetItemDefaultName(Short.parseShort(Message),(byte)0).compareTo("지정되지 않은 아이템")==0)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
	  				sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				String JobName4 = u.getString(player, (byte)2);
				String JobNick3 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconID",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNICONDATA");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick3+ChatColor.LIGHT_PURPLE +" 를 나타내는 아이콘 DATA는 무엇인가요?");
			}
			return;
		case "JNICONDATA" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName4 = u.getString(player, (byte)2);
				String JobNick3 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconData",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNICONAMOUNT");
				sound.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick3+ChatColor.LIGHT_PURPLE +" 를 나타내는 아이콘 갯수는 몇 개인가요?");
			}
			return;
		case "JNICONAMOUNT" : 
			if(isIntMinMax(Message, player, 1, 127))
			{
				String JobName4 = u.getString(player, (byte)2);
				String JobNick3 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconAmount",Integer.parseInt(Message));
				JobList.saveConfig();
				sound.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
				JGUI.MapleStory_JobSetting(player, JobName4);
				u.clearAll(player);
			}
			return;
		}
		return;
	}

}
