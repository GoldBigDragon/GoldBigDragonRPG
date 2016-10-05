package GBD_RPG.Job;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import GBD_RPG.Admin.OPbox_GUI;
import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_GUI;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Job_GUI extends Util_GUI
{
	public void ChooseSystemGUI(Player player)
	{
		String UniqueCode = "§0§0§6§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0시스템 선택");
		
		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "마비노기", 346,0,1,Arrays.asList(ChatColor.GRAY + "메이플 스토리와는 다르게",ChatColor.GRAY + "자유롭게 스킬을 습득 할 수 있습니다.",ChatColor.GRAY+"직업 개념이 없기 때문에",ChatColor.GRAY+"카테고리별로 스킬을 나눕니다.","",ChatColor.GREEN+"자유도 : "+ChatColor.YELLOW+"||||||||||||||||||||",ChatColor.GREEN+"노가다 : "+ChatColor.YELLOW+"||||||||||||||||||||","",ChatColor.RED+"[게임 성향이 마비노기일 경우만 적용]"), 12, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "메이플 스토리", 40,0,1,Arrays.asList(ChatColor.GRAY + "마비노기와는 다르게",ChatColor.GRAY + "직업별로 스킬이 고정되어 있습니다.",ChatColor.GRAY+"직업 개념이 존재하기 때문에",ChatColor.GRAY+"직업별 및 승급별로 스킬을 나눕니다.","",ChatColor.GREEN+"자유도 : "+ChatColor.YELLOW+"||||||"+ChatColor.GRAY+"||||||||||||||",ChatColor.GREEN+"노가다 : "+ChatColor.YELLOW+"|||||||||||"+ChatColor.GRAY+"|||||||||","",ChatColor.RED+"[게임 성향이 메이플 스토리일 경우만 적용]"), 14, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 18, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 26, inv);
		player.openInventory(inv);
	}

	public void MapleStory_ChooseJob(Player player, short page)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
		YamlManager Config = YC.getNewConfig("config.yml");

		String UniqueCode = "§0§0§6§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[MapleStory] 전체 직업 목록 : " + (page+1));

		Object[] a = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String JobName = a[count].toString();
			Set<String> JobLevel= JobList.getConfigurationSection("MapleStory."+JobName).getKeys(false);
			
			if(count > a.length || loc >= 45) break;
			
			if(JobName.equalsIgnoreCase(Config.getString("Server.DefaultJob")))
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + JobName, 403,0,1,Arrays.asList(ChatColor.DARK_AQUA+"최대 승급 : " + ChatColor.WHITE+JobLevel.size()+ChatColor.DARK_AQUA+"차 승급","",ChatColor.YELLOW+"[좌클릭시 승급 설정]",ChatColor.YELLOW+""+ChatColor.BOLD+"[서버 기본 직업]"), loc, inv);
			else
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + JobName, 340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"최대 승급 : " + ChatColor.WHITE+JobLevel.size()+ChatColor.DARK_AQUA+"차 승급","",ChatColor.YELLOW+"[좌클릭시 승급 설정]",ChatColor.YELLOW+"[Shift + 좌클릭시 서버 기본 직업 설정]",ChatColor.RED+"[Shift + 우클릭시 직업 삭제]",ChatColor.RED+"플레이어 수가 많을수록 렉이 심해집니다."), loc, inv);
			
			loc++;
		}
		
		if(a.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 직업", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 직업을 생성합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void MapleStory_JobSetting(Player player, String JobName)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");

		String UniqueCode = "§0§0§6§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0[MapleStory] 직업 설정");

		Object[] a = JobList.getConfigurationSection("MapleStory."+JobName).getKeys(false).toArray();

		for(short count = 0; count < a.length;count++)
		{
			String JobNick =  a[count].toString();
			short JobSkillList= (short) JobList.getConfigurationSection("MapleStory."+JobName+"."+JobNick+".Skill").getKeys(false).size();
			
			short IconID = (short) JobList.getInt("MapleStory."+JobName+"."+JobNick+".IconID");
			byte IconData = (byte) JobList.getInt("MapleStory."+JobName+"."+JobNick+".IconData");
			byte IconAmount = (byte) JobList.getInt("MapleStory."+JobName+"."+JobNick+".IconAmount");

			int NeedLevel = JobList.getInt("MapleStory."+JobName+"."+JobNick+".NeedLV");
			int NeedSTR = JobList.getInt("MapleStory."+JobName+"."+JobNick+".NeedSTR");
			int NeedDEX = JobList.getInt("MapleStory."+JobName+"."+JobNick+".NeedDEX");
			int NeedINT = JobList.getInt("MapleStory."+JobName+"."+JobNick+".NeedINT");
			int NeedWILL = JobList.getInt("MapleStory."+JobName+"."+JobNick+".NeedWILL");
			int NeedLUK = JobList.getInt("MapleStory."+JobName+"."+JobNick+".NeedLUK");
			String PrevJob = JobList.getString("MapleStory."+JobName+"."+JobNick+".PrevJob");
			if(PrevJob.equalsIgnoreCase("null") == true)
				PrevJob = "없음";
			if(count == 0)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + JobNick , IconID,IconData,IconAmount,Arrays.asList(ChatColor.DARK_AQUA+"등록된 스킬 수 : "+ChatColor.WHITE+JobSkillList+ ChatColor.DARK_AQUA+"개",ChatColor.GREEN+"승급 필요 레벨 : "+ChatColor.WHITE+NeedLevel+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.STR+" : "+ChatColor.WHITE+NeedSTR+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.DEX+" : "+ChatColor.WHITE+NeedDEX+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.INT+" : "+ChatColor.WHITE+NeedINT+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.WILL+" : "+ChatColor.WHITE+NeedWILL+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.LUK+" : "+ChatColor.WHITE+NeedLUK+ChatColor.GREEN+" 이상"	,ChatColor.GREEN+"이전 승급 단계 : "+ChatColor.WHITE+PrevJob,"",ChatColor.YELLOW+"[좌 클릭시 직업 스킬 등록]",ChatColor.YELLOW+"[우 클릭시 직업 스킬 확인]",ChatColor.YELLOW+"[Shift + 좌 클릭시 승급 제한 설정]",ChatColor.YELLOW+""+ChatColor.BOLD+"[기본 클래스]"), count, inv);
			else
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + JobNick, IconID,IconData,IconAmount,Arrays.asList(ChatColor.DARK_AQUA+"등록된 스킬 수 : "+ChatColor.WHITE+JobSkillList+ ChatColor.DARK_AQUA+"개",ChatColor.GREEN+"승급 필요 레벨 : "+ChatColor.WHITE+NeedLevel+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.STR+" : "+ChatColor.WHITE+NeedSTR+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.DEX+" : "+ChatColor.WHITE+NeedDEX+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.INT+" : "+ChatColor.WHITE+NeedINT+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.WILL+" : "+ChatColor.WHITE+NeedWILL+ChatColor.GREEN+" 이상",ChatColor.GREEN+"승급 필요 "+GBD_RPG.Main_Main.Main_ServerOption.LUK+" : "+ChatColor.WHITE+NeedLUK+ChatColor.GREEN+" 이상"	,ChatColor.GREEN+"이전 승급 단계 : "+ChatColor.WHITE+PrevJob,"",ChatColor.YELLOW+"[좌 클릭시 직업 스킬 등록]",ChatColor.YELLOW+"[우 클릭시 직업 스킬 확인]",ChatColor.YELLOW+"[Shift + 좌 클릭시 승급 제한 설정]",ChatColor.RED+"[Shift + 우클릭시 승급 삭제]",ChatColor.RED+"플레이어가 많을수록 렉이 심해집니다."), count, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 승급", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 승급 클래스를 만듭니다."), 22, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+JobName), 26, inv);
		player.openInventory(inv);
	}
	
	public void Mabinogi_ChooseCategory(Player player, short page)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");

		String UniqueCode = "§0§0§6§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[Mabinogi] 전체 카테고리 목록 : " + (page+1));

		Object[] a = JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String CategoryName = a[count].toString();
			if(CategoryName.compareTo("Added")!=0)
			{
				if(count > a.length || loc >= 45) break;
				short SkillNumber = (short) JobList.getConfigurationSection("Mabinogi."+CategoryName).getKeys(false).size();
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + CategoryName, 403,0,1,Arrays.asList(ChatColor.DARK_AQUA+"등록된 스킬 : " +ChatColor.WHITE+SkillNumber+ChatColor.DARK_AQUA+" 개","",ChatColor.YELLOW+"[좌 클릭시 스킬 등록]",ChatColor.YELLOW+"[Shift + 좌 클릭시 스킬 관리]",ChatColor.RED+"[Shift + 우클릭시 카테고리 삭제]"), loc, inv);
				loc++;
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 카테고리", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 카테고리를 생성합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void Mabinogi_SkillSetting(Player player, short page, String CategoriName)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
	  	YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");

		String UniqueCode = "§0§0§6§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[Mabinogi] 스킬 관리 목록 : " + (page+1));

		Set<String> b= JobList.getConfigurationSection("Mabinogi."+CategoriName).getKeys(false);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		Object[] a = b.toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String SkillName = a[count].toString();
			boolean SkillPublic = JobList.getBoolean("Mabinogi."+CategoriName+"."+SkillName);
			if(count > a.length || loc >= 45) break;

			short IconID = (short) SkillList.getInt(SkillName+".ID");
			byte IconData = (byte) SkillList.getInt(SkillName+".DATA");
			byte IconAmount = (byte) SkillList.getInt(SkillName+".Amount");
			
			if(SkillPublic == true)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName, IconID,IconData,IconAmount,Arrays.asList("",ChatColor.DARK_AQUA+"[   기본 스킬   ]",ChatColor.GRAY+"서버 접속시 기본적으로",ChatColor.GRAY+"주어지는 스킬입니다.","",ChatColor.YELLOW+"[좌 클릭시 히든 스킬 전환]",ChatColor.RED+"[Shift + 우클릭시 스킬 삭제]","",ChatColor.RED+"     ※  주의  ※     ",ChatColor.RED+"히든 스킬으로 전환 하더라도",ChatColor.RED+"원래 해당 스킬을 알고 있던",ChatColor.RED+"유저의 스킬은 삭제되지 않으며,",ChatColor.RED+"신규 유저들의 스킬 자동 습득만",ChatColor.RED+"불가능 하게 됩니다."), loc, inv);
			else
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName, IconID,IconData,IconAmount,Arrays.asList("",ChatColor.DARK_PURPLE+"[   히든 스킬   ]",ChatColor.GRAY+"책을 읽거나 퀘스트 수행을 통하여",ChatColor.GRAY+"얻을 수 있는 스킬입니다.","",ChatColor.YELLOW+"[좌 클릭시 기본 스킬 전환]",ChatColor.RED+"[Shift + 우클릭시 스킬 삭제]","",ChatColor.RED+"     ※  주의  ※     ",ChatColor.RED+"기본 스킬으로 전환 시킬 경우",ChatColor.RED+"현재 접속한 모든 인원에게,",ChatColor.RED+"그리고 전환 이후에 들어오는 모든",ChatColor.RED+"신규 유저들에게 해당 스킬이 주어집니다.",ChatColor.RED+"[현재 접속자 수에 비례한 렉 발생]"), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + CategoriName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AddedSkillsListGUI(Player player, int page, String JobName, String JobNick)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
	  	YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");

		String UniqueCode = "§0§0§6§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0등록된 스킬 목록 : " + (page+1));

		Object[] a = JobList.getConfigurationSection("MapleStory."+JobName+"."+JobNick+".Skill").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(),  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList("",ChatColor.RED+"[Shift + 우 클릭시 스킬 제거]"), loc, inv);

			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+JobNick), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+JobName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	public void ChooseSystemGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 18)//이전 목록
				new OPbox_GUI().OPBoxGUI_Main(player,(byte) 2);
			else if(slot == 12)//마비노기 타입 카테고리 목록
				Mabinogi_ChooseCategory(player,(short) 0);
			else if(slot == 14)//메이플스토리 타입 직업 목록
				MapleStory_ChooseJob(player,(short) 0);
		}
	}

	public void MapleStory_ChooseJobClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				ChooseSystemGUI(player);
			else if(slot == 48)//이전 페이지
				MapleStory_ChooseJob(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 49)//새 직업
			{
				player.closeInventory();
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[직업] : 새로운 직업 이름을 설정해 주세요!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "CJ");
			}
			else if(slot == 50)//다음 페이지
				MapleStory_ChooseJob(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				if(event.isLeftClick()==true&&event.isRightClick()==false)
					MapleStory_JobSetting(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				if(event.getCurrentItem().getItemMeta().getLore().toString().contains("설정")==true&&event.isShiftClick()==true
				&&event.isLeftClick()==true)
				{
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager Config = YC.getNewConfig("config.yml");
					Config.set("Server.DefaultJob", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					Config.saveConfig();
					MapleStory_ChooseJob(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
				}
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("설정")==true&&event.isShiftClick()==true
				&&event.isRightClick()==true)
				{
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager Config  = YC.getNewConfig("config.yml");
					if(Config.getString("Server.DefaultJob").equalsIgnoreCase(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
					{
						s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[직업] : 서버 기본 직업은 삭제할 수 없습니다!");
					}
					else
					{
						s.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						YamlManager SkillList  = YC.getNewConfig("Skill/JobList.yml");
						SkillList.removeKey("MapleStory."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						SkillList.saveConfig();
						MapleStory_ChooseJob(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
						Config.set("Time.LastSkillChanged", new GBD_RPG.Util.Util_Number().RandomNum(0, 100000)-new GBD_RPG.Util.Util_Number().RandomNum(0, 100000));
						Config.saveConfig();
						new GBD_RPG.Job.Job_Main().AllPlayerFixAllSkillAndJobYML();
					}
				}
			}
		}
	}

	public void MapleStory_JobSettingClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String JobName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
			YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager SkillList  = YC.getNewConfig("Skill/JobList.yml");
			
			if(slot == 18)//이전 목록
				MapleStory_ChooseJob(player, (short) 0);
			else if(slot == 22)//새 승급
			{
				short NowJobLV = (short) SkillList.getConfigurationSection("MapleStory."+JobName).getKeys(false).size();
				if(NowJobLV == 18)
				{
					s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED + "[직업] : 최대 승급은 18차 까지 입니다!");
					return;
				}
				else
				{
					player.closeInventory();
					player.sendMessage(ChatColor.LIGHT_PURPLE+"[직업] : "+ChatColor.YELLOW + JobName+ChatColor.LIGHT_PURPLE+"의 새 승급 형태 이름을 설정해 주세요!");
					UserData_Object u = new UserData_Object();
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
					UserData_Object u = new UserData_Object();
					u.setType(player, "Job");
					u.setString(player, (byte)2, JobNick);
					u.setString(player, (byte)3, JobName);
					new GBD_RPG.Skill.OPboxSkill_GUI().AllSkillsGUI(player,(short) 0, true,"Maple");
				}
				else if(event.isShiftClick()==true&&event.isLeftClick()==true)
				{
					player.closeInventory();
					player.sendMessage(ChatColor.LIGHT_PURPLE+"[직업] : "+ChatColor.YELLOW + JobNick+ChatColor.LIGHT_PURPLE+"의 승급 필요 레벨을 입력 하세요!");

					UserData_Object u = new UserData_Object();
					u.setType(player, "Job");
					u.setString(player, (byte)1, "JNL");
					u.setString(player, (byte)2, JobName);
					u.setString(player, (byte)3, JobNick);
					
				}
				else if(event.isShiftClick()==false&&event.isRightClick()==true)
				{
					s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					AddedSkillsListGUI(player, 0, JobName, JobNick);
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					if(event.getSlot() == 0)
					{
						s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[직업] : 기본 클래스는 삭제할 수 없습니다!");
						return;
					}
					else
					{
						s.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						SkillList.removeKey("MapleStory."+JobName+"."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						SkillList.saveConfig();
						MapleStory_JobSetting(player, JobName);
						YamlManager Config  = YC.getNewConfig("config.yml");
						Config.set("Time.LastSkillChanged", new GBD_RPG.Util.Util_Number().RandomNum(0, 100000)-new GBD_RPG.Util.Util_Number().RandomNum(0, 100000));
						Config.saveConfig();
						new GBD_RPG.Job.Job_Main().AllPlayerFixAllSkillAndJobYML();
					}
				}
			}
		}
	}

	public void AddedSkillsListGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
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
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
					s.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					JobList.removeKey("MapleStory."+JobName+"."+JobNick+".Skill."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					JobList.saveConfig();
					AddedSkillsListGUI(player, page, JobName, JobNick);
					YamlManager Config  = YC.getNewConfig("config.yml");
					Config.set("Time.LastSkillChanged", new GBD_RPG.Util.Util_Number().RandomNum(0, 100000)-new GBD_RPG.Util.Util_Number().RandomNum(0, 100000));
					Config.saveConfig();
					new GBD_RPG.Job.Job_Main().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
	public void Mabinogi_ChooseCategoryClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				ChooseSystemGUI(player);
			else if(slot == 48)//이전 페이지
				Mabinogi_ChooseCategory(player,(short) (page-1));
			else if(slot == 50)//다음 페이지
				Mabinogi_ChooseCategory(player,(short) (page+1));
			else if(slot == 49)//새 카테고리
			{
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[카테고리] : 새 카테고리의 이름을 설정해 주세요!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "CC");
			}
			else
			{
				String CategoriName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				if(event.isLeftClick()==true)
				{
					if(event.isShiftClick() == false)
					{
						GBD_RPG.Skill.OPboxSkill_GUI OGUI = new GBD_RPG.Skill.OPboxSkill_GUI();
						OGUI.AllSkillsGUI(player, (short) 0, true, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
					else
						Mabinogi_SkillSetting(player, (short) 0, CategoriName);
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					s.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager SkillList  = YC.getNewConfig("Skill/JobList.yml");
					Object[] AddedSkillList = SkillList.getConfigurationSection("Mabinogi.Added").getKeys(false).toArray();
					for(short count = 0; count <AddedSkillList.length;count++)
					{
						if(SkillList.getString("Mabinogi.Added."+AddedSkillList[count]).equals(CategoriName))
							SkillList.removeKey("Mabinogi.Added."+AddedSkillList[count]);
					}
					SkillList.removeKey("Mabinogi."+CategoriName);
					SkillList.saveConfig();
					Mabinogi_ChooseCategory(player,page);
					YamlManager Config  = YC.getNewConfig("config.yml");
					Config.set("Time.LastSkillChanged", new GBD_RPG.Util.Util_Number().RandomNum(0, 100000)-new GBD_RPG.Util.Util_Number().RandomNum(0, 100000));
					Config.saveConfig();
					new GBD_RPG.Job.Job_Main().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
	public void Mabinogi_SkillSettingClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();

		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
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
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager SkillList  = YC.getNewConfig("Skill/JobList.yml");
				if(event.isLeftClick()==true)
				{
					if(SkillList.getBoolean("Mabinogi."+CategoriName+"."+SkillName) == true)
					{
						SkillList.set("Mabinogi."+CategoriName+"."+SkillName, false);
						SkillList.saveConfig();
						Mabinogi_SkillSetting(player,(short) page,CategoriName);
					}
					else
					{
						SkillList.set("Mabinogi."+CategoriName+"."+SkillName, true);
						SkillList.saveConfig();
						Mabinogi_SkillSetting(player,(short) page,CategoriName);
						YamlManager Config  = YC.getNewConfig("config.yml");
						Config.set("Time.LastSkillChanged", new GBD_RPG.Util.Util_Number().RandomNum(0, 100000)-new GBD_RPG.Util.Util_Number().RandomNum(0, 100000));
						Config.saveConfig();
						new GBD_RPG.Job.Job_Main().AllPlayerFixAllSkillAndJobYML();
					}
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					s.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					SkillList.removeKey("Mabinogi.Added."+SkillName);
					SkillList.removeKey("Mabinogi."+CategoriName+"."+SkillName);
					SkillList.saveConfig();
					Mabinogi_SkillSetting(player, (short) page, CategoriName);
					YamlManager Config  = YC.getNewConfig("config.yml");
					Config.set("Time.LastSkillChanged", new GBD_RPG.Util.Util_Number().RandomNum(0, 100000)-new GBD_RPG.Util.Util_Number().RandomNum(0, 100000));
					Config.saveConfig();
					new GBD_RPG.Job.Job_Main().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
}