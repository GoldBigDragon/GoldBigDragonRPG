package job;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import admin.OPboxGui;
import effect.SoundEffect;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class JobGUI extends UtilGui
{
	public void ChooseSystemGUI(Player player)
	{
		String UniqueCode = "§0§0§6§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0시스템 선택");
		
		removeFlagStack("§6§l마비노기", 346,0,1,Arrays.asList("§7메이플 스토리와는 다르게","§7자유롭게 스킬을 습득 할 수 있습니다.","§7직업 개념이 없기 때문에","§7카테고리별로 스킬을 나눕니다.","","§a자유도 : §e||||||||||||||||||||","§a노가다 : §e||||||||||||||||||||","","§c[게임 성향이 마비노기일 경우만 적용]"), 12, inv);
		removeFlagStack("§c§l메이플 스토리", 40,0,1,Arrays.asList("§7마비노기와는 다르게","§7직업별로 스킬이 고정되어 있습니다.","§7직업 개념이 존재하기 때문에","§7직업별 및 승급별로 스킬을 나눕니다.","","§a자유도 : §e||||||§7||||||||||||||","§a노가다 : §e|||||||||||§7|||||||||","","§c[게임 성향이 메이플 스토리일 경우만 적용]"), 14, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 18, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7작업 관리자 창을 닫습니다."), 26, inv);
		player.openInventory(inv);
	}

	public void MapleStory_ChooseJob(Player player, short page)
	{
	  	YamlLoader jobYaml = new YamlLoader();
	  	YamlLoader configYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
		configYaml.getConfig("config.yml");

		String UniqueCode = "§0§0§6§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[MapleStory] 전체 직업 목록 : " + (page+1));

		String[] jobList = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < jobList.length;count++)
		{
			int jobMaxLevel = jobYaml.getConfigurationSection("MapleStory."+jobList[count]).getKeys(false).size();
			
			if(count > jobList.length || loc >= 45) break;
			
			if(jobList[count].equalsIgnoreCase(configYaml.getString("Server.DefaultJob")))
				removeFlagStack("§f§l" + jobList[count], 403,0,1,Arrays.asList("§3최대 승급 : §f"+jobMaxLevel+"§3차 승급","","§e[좌클릭시 승급 설정]","§e§l[서버 기본 직업]"), loc, inv);
			else
				removeFlagStack("§f§l" + jobList[count], 340,0,1,Arrays.asList("§3최대 승급 : §f"+jobMaxLevel+"§3차 승급","","§e[좌클릭시 승급 설정]","§e[Shift + 좌클릭시 서버 기본 직업 설정]","§c[Shift + 우클릭시 직업 삭제]","§c플레이어 수가 많을수록 렉이 심해집니다."), loc, inv);
			
			loc++;
		}
		
		if(jobList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l새 직업", 386,0,1,Arrays.asList("§7새로운 직업을 생성합니다."), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void MapleStory_JobSetting(Player player, String JobName)
	{
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");

		String UniqueCode = "§0§0§6§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0[MapleStory] 직업 설정");

		String[] jobList = jobYaml.getConfigurationSection("MapleStory."+JobName).getKeys(false).toArray(new String[0]);

		for(int count = 0; count < jobList.length;count++)
		{
			short JobSkillList= (short) jobYaml.getConfigurationSection("MapleStory."+JobName+"."+jobList[count]+".Skill").getKeys(false).size();
			
			short IconID = (short) jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".IconID");
			byte IconData = (byte) jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".IconData");
			byte IconAmount = (byte) jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".IconAmount");

			int NeedLevel = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedLV");
			int NeedSTR = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedSTR");
			int NeedDEX = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedDEX");
			int NeedINT = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedINT");
			int NeedWILL = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedWILL");
			int NeedLUK = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedLUK");
			String PrevJob = jobYaml.getString("MapleStory."+JobName+"."+jobList[count]+".PrevJob");
			if(PrevJob.equalsIgnoreCase("null") == true)
				PrevJob = "없음";
			if(count == 0)
				removeFlagStack("§f§l" + jobList[count] , IconID,IconData,IconAmount,Arrays.asList("§3등록된 스킬 수 : §f"+JobSkillList+ "§3개","§a승급 필요 레벨 : §f"+NeedLevel+"§a 이상","§a승급 필요 "+main.MainServerOption.statSTR+" : §f"+NeedSTR+"§a 이상","§a승급 필요 "+main.MainServerOption.statDEX+" : §f"+NeedDEX+"§a 이상","§a승급 필요 "+main.MainServerOption.statINT+" : §f"+NeedINT+"§a 이상","§a승급 필요 "+main.MainServerOption.statWILL+" : §f"+NeedWILL+"§a 이상","§a승급 필요 "+main.MainServerOption.statLUK+" : §f"+NeedLUK+"§a 이상"	,"§a이전 승급 단계 : §f"+PrevJob,"","§e[좌 클릭시 직업 스킬 등록]","§e[우 클릭시 직업 스킬 확인]","§e[Shift + 좌 클릭시 승급 제한 설정]","§e§l[기본 클래스]"), count, inv);
			else
				removeFlagStack("§f§l" + jobList[count], IconID,IconData,IconAmount,Arrays.asList("§3등록된 스킬 수 : §f"+JobSkillList+ "§3개","§a승급 필요 레벨 : §f"+NeedLevel+"§a 이상","§a승급 필요 "+main.MainServerOption.statSTR+" : §f"+NeedSTR+"§a 이상","§a승급 필요 "+main.MainServerOption.statDEX+" : §f"+NeedDEX+"§a 이상","§a승급 필요 "+main.MainServerOption.statINT+" : §f"+NeedINT+"§a 이상","§a승급 필요 "+main.MainServerOption.statWILL+" : §f"+NeedWILL+"§a 이상","§a승급 필요 "+main.MainServerOption.statLUK+" : §f"+NeedLUK+"§a 이상"	,"§a이전 승급 단계 : §f"+PrevJob,"","§e[좌 클릭시 직업 스킬 등록]","§e[우 클릭시 직업 스킬 확인]","§e[Shift + 좌 클릭시 승급 제한 설정]","§c[Shift + 우클릭시 승급 삭제]","§c플레이어가 많을수록 렉이 심해집니다."), count, inv);
		}
		
		removeFlagStack("§f§l새 승급", 386,0,1,Arrays.asList("§7새로운 승급 클래스를 만듭니다."), 22, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 18, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+JobName), 26, inv);
		player.openInventory(inv);
	}
	
	public void Mabinogi_ChooseCategory(Player player, short page)
	{
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");

		String UniqueCode = "§0§0§6§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[Mabinogi] 전체 카테고리 목록 : " + (page+1));

		String[] jobList = jobYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < jobList.length;count++)
		{
			if(!jobList[count].equals("Added"))
			{
				if(count > jobList.length || loc >= 45) break;
				short SkillNumber = (short) jobYaml.getConfigurationSection("Mabinogi."+jobList[count]).getKeys(false).size();
				removeFlagStack("§f§l" + jobList[count], 403,0,1,Arrays.asList("§3등록된 스킬 : §f"+SkillNumber+"§3 개","","§e[좌 클릭시 스킬 등록]","§e[Shift + 좌 클릭시 스킬 관리]","§c[Shift + 우클릭시 카테고리 삭제]"), loc, inv);
				loc++;
			}
		}
		
		if(jobList.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l새 카테고리", 386,0,1,Arrays.asList("§7새로운 카테고리를 생성합니다."), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void Mabinogi_SkillSetting(Player player, short page, String CategoriName)
	{
	  	YamlLoader jobYaml = new YamlLoader();
	  	jobYaml.getConfig("Skill/JobList.yml");

		String UniqueCode = "§0§0§6§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[Mabinogi] 스킬 관리 목록 : " + (page+1));

	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");
		String[] mabinogiCategoriList = jobYaml.getConfigurationSection("Mabinogi."+CategoriName).getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < mabinogiCategoriList.length;count++)
		{
			boolean SkillPublic = jobYaml.getBoolean("Mabinogi."+CategoriName+"."+mabinogiCategoriList[count]);
			if(count > mabinogiCategoriList.length || loc >= 45) break;

			short IconID = (short) skillYaml.getInt(mabinogiCategoriList[count]+".ID");
			byte IconData = (byte) skillYaml.getInt(mabinogiCategoriList[count]+".DATA");
			byte IconAmount = (byte) skillYaml.getInt(mabinogiCategoriList[count]+".Amount");
			
			if(SkillPublic == true)
				removeFlagStack("§f§l" + mabinogiCategoriList[count], IconID,IconData,IconAmount,Arrays.asList("","§3[   기본 스킬   ]","§7서버 접속시 기본적으로","§7주어지는 스킬입니다.","","§e[좌 클릭시 히든 스킬 전환]","§c[Shift + 우클릭시 스킬 삭제]","","§c     ※  주의  ※     ","§c히든 스킬으로 전환 하더라도","§c원래 해당 스킬을 알고 있던","§c유저의 스킬은 삭제되지 않으며,","§c신규 유저들의 스킬 자동 습득만","§c불가능 하게 됩니다."), loc, inv);
			else
				removeFlagStack("§f§l" + mabinogiCategoriList[count], IconID,IconData,IconAmount,Arrays.asList("","§5[   히든 스킬   ]","§7책을 읽거나 퀘스트 수행을 통하여","§7얻을 수 있는 스킬입니다.","","§e[좌 클릭시 기본 스킬 전환]","§c[Shift + 우클릭시 스킬 삭제]","","§c     ※  주의  ※     ","§c기본 스킬으로 전환 시킬 경우","§c현재 접속한 모든 인원에게,","§c그리고 전환 이후에 들어오는 모든","§c신규 유저들에게 해당 스킬이 주어집니다.","§c[현재 접속자 수에 비례한 렉 발생]"), loc, inv);
			loc++;
		}
		
		if(mabinogiCategoriList.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ CategoriName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AddedSkillsListGUI(Player player, int page, String JobName, String JobNick)
	{
		YamlLoader jobYaml = new YamlLoader();
		YamlLoader skillYaml = new YamlLoader();
	  	skillYaml.getConfig("Skill/SkillList.yml");
		jobYaml.getConfig("Skill/JobList.yml");

		String UniqueCode = "§0§0§6§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0등록된 스킬 목록 : " + (page+1));

		String[] skillList = jobYaml.getConfigurationSection("MapleStory."+JobName+"."+JobNick+".Skill").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < skillList.length;count++)
		{
			if(count > skillList.length || loc >= 45) break;
			removeFlagStack("§f§l" + skillList[count].toString(),  skillYaml.getInt(skillList[count]+".ID"),skillYaml.getInt(skillList[count]+".DATA"),skillYaml.getInt(skillList[count]+".Amount"),Arrays.asList("","§c[Shift + 우 클릭시 스킬 제거]"), loc, inv);

			loc++;
		}
		
		if(skillList.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+JobNick), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+JobName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	public void ChooseSystemGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 18)//이전 목록
				new OPboxGui().opBoxGuiMain(player,(byte) 2);
			else if(slot == 12)//마비노기 타입 카테고리 목록
				Mabinogi_ChooseCategory(player,(short) 0);
			else if(slot == 14)//메이플스토리 타입 직업 목록
				MapleStory_ChooseJob(player,(short) 0);
		}
	}

	public void MapleStory_ChooseJobClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				ChooseSystemGUI(player);
			else if(slot == 48)//이전 페이지
				MapleStory_ChooseJob(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 49)//새 직업
			{
				player.closeInventory();
				player.sendMessage("§d[직업] : 새로운 직업 이름을 설정해 주세요!");
				UserDataObject u = new UserDataObject();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "CJ");
			}
			else if(slot == 50)//다음 페이지
				MapleStory_ChooseJob(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				if(event.isLeftClick()==true&&event.isRightClick()==false)
					MapleStory_JobSetting(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				if(event.getCurrentItem().getItemMeta().getLore().toString().contains("설정")==true&&event.isShiftClick()==true
				&&event.isLeftClick()==true)
				{
					configYaml.set("Server.DefaultJob", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					configYaml.saveConfig();
					MapleStory_ChooseJob(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
				}
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("설정")==true&&event.isShiftClick()==true
				&&event.isRightClick()==true)
				{
					if(configYaml.getString("Server.DefaultJob").equalsIgnoreCase(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
					{
						SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[직업] : 서버 기본 직업은 삭제할 수 없습니다!");
					}
					else
					{
						YamlLoader jobYaml = new YamlLoader();
						SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						jobYaml.getConfig("Skill/JobList.yml");
						jobYaml.removeKey("MapleStory."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						jobYaml.saveConfig();
						MapleStory_ChooseJob(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
						configYaml.set("Time.LastSkillChanged", new util.UtilNumber().RandomNum(0, 100000)-new util.UtilNumber().RandomNum(0, 100000));
						configYaml.saveConfig();
						new job.JobMain().AllPlayerFixAllSkillAndJobYML();
					}
				}
			}
		}
	}

	public void MapleStory_JobSettingClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String JobName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
			YamlLoader jobYaml = new YamlLoader();
			jobYaml.getConfig("Skill/JobList.yml");
			
			if(slot == 18)//이전 목록
				MapleStory_ChooseJob(player, (short) 0);
			else if(slot == 22)//새 승급
			{
				short NowJobLV = (short) jobYaml.getConfigurationSection("MapleStory."+JobName).getKeys(false).size();
				if(NowJobLV == 18)
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[직업] : 최대 승급은 18차 까지 입니다!");
					return;
				}
				else
				{
					player.closeInventory();
					player.sendMessage("§d[직업] : §e"+ JobName+"§d의 새 승급 형태 이름을 설정해 주세요!");
					UserDataObject u = new UserDataObject();
					u.setType(player, "Job");
					u.setString(player, (byte)1, "CJL");
					u.setString(player, (byte)2, JobName);
				}
			}
			else
			{
				String JobNick = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				if(event.isLeftClick()==true&&event.isShiftClick()==false)
				{
					UserDataObject u = new UserDataObject();
					u.setType(player, "Job");
					u.setString(player, (byte)2, JobNick);
					u.setString(player, (byte)3, JobName);
					new skill.OPboxSkillGui().AllSkillsGUI(player,(short) 0, true,"Maple");
				}
				else if(event.isShiftClick()==true&&event.isLeftClick()==true)
				{
					player.closeInventory();
					player.sendMessage("§d[직업] : §e"+ JobNick+"§d의 승급 필요 레벨을 입력 하세요!");

					UserDataObject u = new UserDataObject();
					u.setType(player, "Job");
					u.setString(player, (byte)1, "JNL");
					u.setString(player, (byte)2, JobName);
					u.setString(player, (byte)3, JobNick);
					
				}
				else if(event.isShiftClick()==false&&event.isRightClick()==true)
				{
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					AddedSkillsListGUI(player, 0, JobName, JobNick);
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					if(event.getSlot() == 0)
					{
						SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[직업] : 기본 클래스는 삭제할 수 없습니다!");
						return;
					}
					else
					{
						SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						jobYaml.removeKey("MapleStory."+JobName+"."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						jobYaml.saveConfig();
						MapleStory_JobSetting(player, JobName);
						YamlLoader configYaml = new YamlLoader();
						configYaml.getConfig("config.yml");
						configYaml.set("Time.LastSkillChanged", new util.UtilNumber().RandomNum(0, 100000)-new util.UtilNumber().RandomNum(0, 100000));
						configYaml.saveConfig();
						new job.JobMain().AllPlayerFixAllSkillAndJobYML();
					}
				}
			}
		}
	}

	public void AddedSkillsListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String JobName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			String JobNick = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			
			if(slot == 45)//이전 목록
				MapleStory_JobSetting(player, JobName);
			else if(slot == 48)//이전 페이지
				AddedSkillsListGUI(player, page-1, JobName, JobNick);
			else if(slot == 50)//다음 페이지
				AddedSkillsListGUI(player, page+1, JobName, JobNick);
			else
			{
				if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					YamlLoader jobYaml = new YamlLoader();
					jobYaml.getConfig("Skill/JobList.yml");
					SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					jobYaml.removeKey("MapleStory."+JobName+"."+JobNick+".Skill."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					jobYaml.saveConfig();
					AddedSkillsListGUI(player, page, JobName, JobNick);
					YamlLoader configYaml = new YamlLoader();
					configYaml.getConfig("config.yml");
					configYaml.set("Time.LastSkillChanged", new util.UtilNumber().RandomNum(0, 100000)-new util.UtilNumber().RandomNum(0, 100000));
					configYaml.saveConfig();
					new job.JobMain().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
	public void Mabinogi_ChooseCategoryClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				ChooseSystemGUI(player);
			else if(slot == 48)//이전 페이지
				Mabinogi_ChooseCategory(player,(short) (page-1));
			else if(slot == 50)//다음 페이지
				Mabinogi_ChooseCategory(player,(short) (page+1));
			else if(slot == 49)//새 카테고리
			{
				player.sendMessage("§d[카테고리] : 새 카테고리의 이름을 설정해 주세요!");
				UserDataObject u = new UserDataObject();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "CC");
				player.closeInventory();
			}
			else
			{
				String CategoriName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				if(event.isLeftClick()==true)
				{
					if(event.isShiftClick() == false)
					{
						skill.OPboxSkillGui OGUI = new skill.OPboxSkillGui();
						OGUI.AllSkillsGUI(player, (short) 0, true, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
					else
						Mabinogi_SkillSetting(player, (short) 0, CategoriName);
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					YamlLoader jobYaml = new YamlLoader();
					jobYaml.getConfig("Skill/JobList.yml");
					String[] addedSkillList = jobYaml.getConfigurationSection("Mabinogi.Added").getKeys(false).toArray(new String[0]);
					for(int count = 0; count <addedSkillList.length;count++)
					{
						if(jobYaml.getString("Mabinogi.Added."+addedSkillList[count]).equals(CategoriName))
							jobYaml.removeKey("Mabinogi.Added."+addedSkillList[count]);
					}
					jobYaml.removeKey("Mabinogi."+CategoriName);
					jobYaml.saveConfig();
					Mabinogi_ChooseCategory(player,page);
					YamlLoader configYaml = new YamlLoader();
					configYaml.getConfig("config.yml");
					configYaml.set("Time.LastSkillChanged", new util.UtilNumber().RandomNum(0, 100000)-new util.UtilNumber().RandomNum(0, 100000));
					configYaml.saveConfig();
					new job.JobMain().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
	public void Mabinogi_SkillSettingClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String CategoriName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			if(slot == 45)//이전 목록
				Mabinogi_ChooseCategory(player,(short) 0);
			else if(slot == 48)//이전 페이지
				Mabinogi_SkillSetting(player,(short) page,CategoriName);
			else if(slot == 50)//다음 페이지
				Mabinogi_SkillSetting(player,(short) page,CategoriName);
			else
			{
				String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlLoader jobYaml = new YamlLoader();
				YamlLoader configYaml = new YamlLoader();
				jobYaml.getConfig("Skill/JobList.yml");
				configYaml.getConfig("config.yml");
				if(event.isLeftClick()==true)
				{
					if(jobYaml.getBoolean("Mabinogi."+CategoriName+"."+SkillName) == true)
					{
						jobYaml.set("Mabinogi."+CategoriName+"."+SkillName, false);
						jobYaml.saveConfig();
						Mabinogi_SkillSetting(player,(short) page,CategoriName);
					}
					else
					{
						jobYaml.set("Mabinogi."+CategoriName+"."+SkillName, true);
						jobYaml.saveConfig();
						Mabinogi_SkillSetting(player,(short) page,CategoriName);
						configYaml.set("Time.LastSkillChanged", new util.UtilNumber().RandomNum(0, 100000)-new util.UtilNumber().RandomNum(0, 100000));
						configYaml.saveConfig();
						new job.JobMain().AllPlayerFixAllSkillAndJobYML();
					}
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					jobYaml.removeKey("Mabinogi.Added."+SkillName);
					jobYaml.removeKey("Mabinogi."+CategoriName+"."+SkillName);
					jobYaml.saveConfig();
					Mabinogi_SkillSetting(player, (short) page, CategoriName);
					configYaml.set("Time.LastSkillChanged", new util.UtilNumber().RandomNum(0, 100000)-new util.UtilNumber().RandomNum(0, 100000));
					configYaml.saveConfig();
					new job.JobMain().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
}