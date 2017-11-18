package npc;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import effect.PottionBuff;
import effect.SoundEffect;
import main.MainServerOption;
import user.UserDataObject;
import util.UtilGui;
import util.UtilNumber;
import util.YamlLoader;



public class NpcGui extends UtilGui
{
	public void MainGUI(Player player, String NPCname, boolean isOP)
	{
		String UniqueCode = "§0§0§7§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0[NPC] "+ChatColor.stripColor(NPCname));
		UserDataObject u = new UserDataObject();
		Stack2("§f§l대화를 한다", 340,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7에게","§7대화를 합니다."), 10, inv);
		Stack2("§f§l거래를 한다", 371,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7에게","§7거래를 요청합니다."), 12, inv);
		Stack2("§f§l퀘스트", 386,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7에게","§7도울 일이 없는지","§7물어봅니다."), 14, inv);

		YamlLoader playerNPCYaml = new YamlLoader();
    	if(playerNPCYaml.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
    	{
    		playerNPCYaml.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    		playerNPCYaml.set(u.getNPCuuid(player)+".love", 0);
    		playerNPCYaml.set(u.getNPCuuid(player)+".Career", 0);
    		playerNPCYaml.saveConfig();
    	}
    	else
    		playerNPCYaml.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		Stack2("§f§l선물하기", 54,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7에게","§7자신이 가지고 있는","§7아이템을 선물합니다.","§7(NPC와의 호감도 상승)","",
				"§d[현재 호감도]","§c§l ♥ §f§l"+playerNPCYaml.getInt(u.getNPCuuid(player)+".love") + " / 1000"), 16, inv);
		Stack2("§f§l나가기", 324,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7와의","§7대화를 종료합니다.","§0"+ u.getNPCuuid(player)), 26, inv);
		if(player.isOp())
			Stack2("§f§lGUI 비 활성화", 166,0,1,Arrays.asList("§7이 NPC는 GoldBigDragonRPG의","§7NPC GUI 화면을 사용하지 않게 합니다.",""), 8, inv);

		YamlLoader NPCscript = new YamlLoader();
		NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player)  +".yml");
		if(NPCscript.contains("Job") == false)
		{
		  	NPCscript.set("Job.Type", "null");
			NPCscript.saveConfig();
		}
		if(NPCscript.contains("NatureTalk") == false)
		{
    	  	NPCscript.set("NPCuuid", "NPC's uuid");
    	  	NPCscript.set("KoreaLanguage(UTF-8)->JavaEntityLanguage", "http://itpro.cz/juniconv/");
    	  	NPCscript.set("NatureTalk.1.love", 0);
    	  	NPCscript.set("NatureTalk.1.Script", "§f안녕, §e%player%?");
    	  	NPCscript.set("NatureTalk.2.love", 0);
    	  	NPCscript.set("NatureTalk.2.Script", "§f띄워 쓰기는%enter%§f이렇게 하지.");
    	  	NPCscript.set("NatureTalk.3.love", 0);
    	  	NPCscript.set("NatureTalk.3.Script", "§1색깔은 §4이렇게 §f넣을 수 있어!");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("NearByNEWS") == false)
		{
    	  	NPCscript.set("NearByNEWS.1.love", 0);
    	  	NPCscript.set("NearByNEWS.1.Script", "§f너희집에서 어제 다이아몬드를 본 것 같은데...");
    	  	NPCscript.set("NearByNEWS.2.love", 0);
    	  	NPCscript.set("NearByNEWS.2.Script", "§f조심하는게 좋아.%enter%§f어제 §4히로빈 §f이 이 근처에 있었거든...");
    	  	NPCscript.set("NearByNEWS.3.love", 0);
    	  	NPCscript.set("NearByNEWS.3.Script", "§f음...");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("AboutSkills") == false)
		{
    	  	NPCscript.set("AboutSkills.1.love", 0);
    	  	NPCscript.set("AboutSkills.1.giveSkill", "null");
    	  	NPCscript.set("AboutSkills.1.AlreadyGetScript", "null");
    	  	NPCscript.set("AboutSkills.1.Script", "§f나는 §e채광 스킬§f이 있지!%enter%§f뭐? 너도 있다고?");
    	  	NPCscript.set("AboutSkills.2.love", 0);
    	  	NPCscript.set("AboutSkills.2.giveSkill", "null");
    	  	NPCscript.set("AboutSkills.2.AlreadyGetScript", "null");
    	  	NPCscript.set("AboutSkills.2.Script", "§f달리기는 너의 건강에도 좋지만%enter%§f생존에도 좋지.");
    	  	NPCscript.set("AboutSkills.3.love", 0);
    	  	NPCscript.set("AboutSkills.3.giveSkill", "null");
    	  	NPCscript.set("AboutSkills.3.AlreadyGetScript", "null");
    	  	NPCscript.set("AboutSkills.3.Script", "§f너에게 가르쳐 줄만한%enter%§f기술이 없는것 같은데...");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("Shop.Sell") == false)
		{
		  	NPCscript.createSection("Shop.Sell");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("Shop.Buy") == false)
		{
		  	NPCscript.createSection("Shop.Buy");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("Quest") == false)
		{
		  	NPCscript.createSection("Quest");
    	  	NPCscript.saveConfig();
		}
		switch(NPCscript.getString("Job.Type"))
		{
		case "BlackSmith":
				Stack2("§6§l대장장이", 145,0,1,Arrays.asList("§7손에 든 무기 및 도구, 방어구를","§7정해진 확률과 가격에 수리 해 줍니다.","","§3수리 성공률 : §f" +NPCscript.getInt("Job.FixRate")+"§3 %","§a내구도 10 당 가격 : §e"+NPCscript.getInt("Job.10PointFixDeal")+" "+ChatColor.GREEN+MainServerOption.money,"","§e[좌 클릭시 무기 수리화면 이동]","§c[일반 아이템 수리 실패시]","§c - 수리 성공과 상관 없이 골드 소모","§c[커스텀 아이템 수리 실패시]","§c - 최대 내구도 감소"), 4, inv);
			break;
		case "Shaman":
			Stack2("§6§l주술사",381,0,1,Arrays.asList("§7플레이어에게 랜덤한 포션 효과를","§7지정 금액을 받고 부여해 줍니다.","§b버프 성공률 : §f" + NPCscript.getInt("Job.GoodRate") + "§3%","§b버프 시간 : §f" + NPCscript.getInt("Job.BuffTime")+"§3 초","§a복채 비용 : §f"+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+MainServerOption.money,"§e[좌 클릭시 점 치기]","§c[버프 실패시]","§c - 성공과 상관없이 골드 소모","§c - 버프 시간동안 너프 효과"), 4, inv);
		break;
		case "Healer":
			Stack2("§6§l힐러", 373,8261,1,Arrays.asList("§7힐러는 지정 금액을 받고","§7플레이어의 생명력 회복 및","§7각종 너프 효과를 제거해 줍니다.","§a치료 비용 : §f"+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+MainServerOption.money,"§e[좌 클릭시 치료 받기]"), 4, inv);
		break;
		case "Master":
			YamlLoader JobList = new YamlLoader();
			JobList.getConfig("Skill/JobList.yml");
			boolean isExitJob = false;
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(int count = 0; count < Job.length; count++)
			{
				Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
				for(int counter=0;counter<a.length;counter++)
				{
					if(a[counter].toString().equalsIgnoreCase(NPCscript.getString("Job.Job"))==true)
						isExitJob = true;
				}
			}
			if(isExitJob == false)
			{
				NPCscript.removeKey("Job");
				NPCscript.set("Job.Type","null");
				NPCscript.saveConfig();
			}
			for(int count = 0; count < Job.length; count++)
			{
				String[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray(new String[0]);
				for(int counter=0;counter<a.length;counter++)
				{
					if(a[counter].equals(NPCscript.getString("Job.Job")))
					{
						YamlLoader PlayerJob = new YamlLoader();
						PlayerJob.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
						short ID = (short) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".IconID");
						byte DATA = (byte) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".IconData");
						byte Amount = (byte) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".IconAmount");
						int NeedLV = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".NeedLV");
						int NeedSTR = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".NeedSTR");
						int NeedDEX = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".NeedDEX");
						int NeedINT = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".NeedINT");
						int NeedWILL = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".NeedWILL");
						int NeedLUK = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter]+".NeedLUK");
						String PrevJob = JobList.getString("MapleStory."+Job[count].toString()+"."+a[counter]+".PrevJob");

						String lore = "%enter%§9§l   ["+NPCscript.getString("Job.Job")+" 전직]   %enter%§7%enter%§7     [전직 조건]     %enter%%enter%";

						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()<NeedLV)
							lore = lore + "§c필요 레벨 : "+NeedLV+"%enter%";
						else
							lore = lore + "§b필요 레벨 : "+NeedLV+"%enter%";
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()<NeedSTR)
							lore = lore + "§c필요 "+MainServerOption.statSTR+" : "+NeedSTR+"%enter%";
						else
							lore = lore + "§b필요 "+MainServerOption.statSTR+" : "+NeedSTR+"%enter%";
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()<NeedDEX)
							lore = lore + "§c필요 "+MainServerOption.statDEX+" : "+NeedDEX+"%enter%";
						else
							lore = lore + "§b필요 "+MainServerOption.statDEX+" : "+NeedDEX+"%enter%";
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()<NeedINT)
							lore = lore + "§c필요 "+MainServerOption.statINT+" : "+NeedINT+"%enter%";
						else
							lore = lore + "§b필요 "+MainServerOption.statINT+" : "+NeedINT+"%enter%";
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()<NeedWILL)
							lore = lore + "§c필요 "+MainServerOption.statWILL+" : "+NeedWILL+"%enter%";
						else
							lore = lore + "§b필요 "+MainServerOption.statWILL+" : "+NeedWILL+"%enter%";
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()<NeedLUK)
							lore = lore + "§c필요 "+MainServerOption.statLUK+" : "+NeedLUK+"%enter%";
						else
							lore = lore + "§b필요 "+MainServerOption.statLUK+" : "+NeedLUK+"%enter%";
						if(PrevJob.equalsIgnoreCase("null")==false)
						{
							if(PlayerJob.getString("Job.Type").equalsIgnoreCase(PrevJob)==false)
								lore = lore + "§c이전 직업 : "+PrevJob+"%enter%";
							else
								lore = lore + "§b이전 직업 : "+PrevJob+"%enter%";
						}
						
						lore = lore + "%enter%§e[좌 클릭시 전직]";
						
						String[] scriptA = lore.split("%enter%");
						for(int county = 0; county < scriptA.length; county++)
							scriptA[county] =  scriptA[county];
						
						Stack2("§6§l전직 교관", ID,DATA,Amount,Arrays.asList(scriptA), 4, inv);
					}
				}
			}
			break;
		case "Warper":
			Stack2("§6§l공간 이동술사", 368,0,1,Arrays.asList("§7공간 이동술사는 일정 금액을 받고","§7플레이어를 특정 지역으로 이동시켜 줍니다.","§a등록된 지역 : §f"+NPCscript.getConfigurationSection("Job.WarpList").getKeys(false).size()+"§a 구역","","§e[좌 클릭시 워프 목록 열람]"), 4, inv);
		break;
		case "Upgrader":
			Stack2("§6§l개조 장인", 417,0,1,Arrays.asList("§7개조 장인은 일정 금액을 받고","§7개조 장인만이 알고 있는","§7개조 레시피를 참고하여","§7현재 손에 든 무기를","§7개조 시켜 줍니다.","§a이용 가능한 개조식 : §f"+NPCscript.getConfigurationSection("Job.UpgradeRecipe").getKeys(false).size()+"§a 가지","","§e[좌 클릭시 개조 레시피 열람]"), 4, inv);
		break;
		case "Rune":
			Stack2("§6§l룬 세공사", 351,10,1,Arrays.asList("§7룬 세공사는 일정 금액을 받고","§7가지고 있는 룬을 세공하여","§7현재 손에 든 무기에","§7비어있는 룬 소켓이 있다면","§7룬을 장착 시켜 줍니다.","","§a룬 세공 성공률 : §f" +NPCscript.getInt("Job.SuccessRate")+"§3%","§a룬 세공 가격 : §e"+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+MainServerOption.money,"","§e[좌 클릭시 룬 세공]"), 4, inv);
		break;
		}
		
		if(isOP == true)
		{
			switch(NPCscript.getString("Job.Type"))
			{
			case "null":
				Stack2("§6§l직업 설정", 403,0,1,Arrays.asList("§7이 NPC에게는 아직 직업이 없습니다!","§7직업을 가지면 다양한 기능을 제공합니다.","","§e[클릭시 직업 설정]"), 4, inv);
				break;
			case "BlackSmith":
					Stack2("§6§l대장장이", 145,0,1,Arrays.asList("§7플레이어의 무기 및 도구, 방어구를","§7정해진 확률과 가격에 수리 해 줍니다.","","§3수리 성공률 : §f" +NPCscript.getInt("Job.FixRate")+"§3 %","§a내구도 10 당 가격 : §e"+NPCscript.getInt("Job.10PointFixDeal")+" "+MainServerOption.money,"","§c[일반 아이템 수리 실패시]","§c - 수리 성공과 상관 없이 "+MainServerOption.money+"§c 소모","§c[커스텀 아이템 수리 실패시]","§c - 최대 내구도 감소","","§e[좌 클릭시 무기 수리화면 이동]","§c[우 클릭시 직업 변경]"), 4, inv);
				break;
			case "Shaman":
				Stack2("§6§l주술사",381,0,1,Arrays.asList("§7플레이어에게 랜덤한 포션 효과를","§7지정 금액을 받고 부여해 줍니다.","","§b버프 성공률 : §f" + NPCscript.getInt("Job.GoodRate") + "§3%","§b버프 시간 : §f" + NPCscript.getInt("Job.BuffTime")+"§3 초","§a복채 비용 : §f"+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+MainServerOption.money,"§c[버프 실패시]","§c - 성공과 상관없이 골드 소모","§c - 버프 시간동안 너프 효과","","§e[좌 클릭시 점 치기]","§c[우 클릭시 직업 변경]"), 4, inv);
			break;
			case "Healer":
				Stack2("§6§l힐러", 373,8261,1,Arrays.asList("§7힐러는 지정 금액을 받고","§7플레이어의 생명력 회복 및","§7각종 너프 효과를 제거해 줍니다.","§a치료 비용 : §f"+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+MainServerOption.money,"","§e[좌 클릭시 치료 받기]","§c[우 클릭시 직업 변경]"), 4, inv);
			break;
			case "Master":
				YamlLoader JobList = new YamlLoader();
				JobList.getConfig("Skill/JobList.yml");
				String[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
				String PrevJob = null;
				String lore = null;
				for(int count = 0; count < Job.length; count++)
				{
					String[] a = JobList.getConfigurationSection("MapleStory."+Job[count]).getKeys(false).toArray(new String[0]);
					for(int counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(NPCscript.getString("Job.Job"))==true)
						{
							YamlLoader PlayerJob = new YamlLoader();
							PlayerJob.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
							short ID = (short) JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".IconID");
							byte DATA = (byte) JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".IconData");
							byte Amount = (byte) JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".IconAmount");
							int NeedLV = JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".NeedLV");
							int NeedSTR = JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".NeedSTR");
							int NeedDEX = JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".NeedDEX");
							int NeedINT = JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".NeedINT");
							int NeedWILL = JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".NeedWILL");
							int NeedLUK = JobList.getInt("MapleStory."+Job[count]+"."+a[counter]+".NeedLUK");
							PrevJob = JobList.getString("MapleStory."+Job[count]+"."+a[counter]+".PrevJob");						
							lore = "%enter%§9§l   ["+NPCscript.getString("Job.Job")+" 전직]   %enter%§7%enter%§7     [전직 조건]     %enter%%enter%";
							
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()<NeedLV)
								lore = lore + "§c필요 레벨 : "+NeedLV+"%enter%";
							else
								lore = lore + "§b필요 레벨 : "+NeedLV+"%enter%";
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()<NeedSTR)
								lore = lore + "§c필요 "+MainServerOption.statSTR+" : "+NeedSTR+"%enter%";
							else
								lore = lore + "§b필요 "+MainServerOption.statSTR+" : "+NeedSTR+"%enter%";
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()<NeedDEX)
								lore = lore + "§c필요 "+MainServerOption.statDEX+" : "+NeedDEX+"%enter%";
							else
								lore = lore + "§b필요 "+MainServerOption.statDEX+" : "+NeedDEX+"%enter%";
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()<NeedINT)
								lore = lore + "§c필요 "+MainServerOption.statINT+" : "+NeedINT+"%enter%";
							else
								lore = lore + "§b필요 "+MainServerOption.statINT+" : "+NeedINT+"%enter%";
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()<NeedWILL)
								lore = lore + "§c필요 "+MainServerOption.statWILL+" : "+NeedWILL+"%enter%";
							else
								lore = lore + "§b필요 "+MainServerOption.statWILL+" : "+NeedWILL+"%enter%";
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()<NeedLUK)
								lore = lore + "§c필요 "+MainServerOption.statLUK+" : "+NeedLUK+"%enter%";
							else
								lore = lore + "§b필요 "+MainServerOption.statLUK+" : "+NeedLUK+"%enter%";

							if(PrevJob.equalsIgnoreCase("null")==false)
							{
								if(PlayerJob.getString("Job.Type").equalsIgnoreCase(PrevJob)==false)
									lore = lore + "§c이전 직업 : "+PrevJob+"%enter%";
								else
									lore = lore + "§b이전 직업 : "+PrevJob+"%enter%";
							}
							
							lore = lore + "%enter%§e[좌 클릭시 전직]%enter%§c[우 클릭시 직업 변경]";
							
							String[] scriptA = lore.split("%enter%");
							for(int county = 0; county < scriptA.length; county++)
								scriptA[county] =  scriptA[county];
							
							Stack2("§6§l전직 교관", ID,DATA,Amount,Arrays.asList(scriptA), 4, inv);
						}
					}
				}
				break;
			case "Warper":
				Stack2("§6§l공간 이동술사", 368,0,1,Arrays.asList("§7공간 이동술사는 일정 금액을 받고","§7플레이어를 특정 지역으로 이동시켜 줍니다.","§a등록된 지역 : §f"+NPCscript.getConfigurationSection("Job.WarpList").getKeys(false).size()+"§a 구역","","§e[좌 클릭시 워프 목록 열람]","§c[우 클릭시 직업 변경]"), 4, inv);
			break;
			case "Upgrader":
				Stack2("§6§l개조 장인", 417,0,1,Arrays.asList("§7개조 장인은 일정 금액을 받고","§7개조 장인만이 알고 있는","§7개조 레시피를 참고하여","§7현재 손에 든 무기를","§7개조 시켜 줍니다.","§a이용 가능한 개조식 : §f"+NPCscript.getConfigurationSection("Job.UpgradeRecipe").getKeys(false).size()+"§a 가지","","§e[좌 클릭시 개조 레시피 열람]","§c[우 클릭시 직업 변경]"), 4, inv);
			break;
			case "Rune":
				Stack2("§6§l룬 세공사", 351,10,1,Arrays.asList("§7룬 세공사는 일정 금액을 받고","§7가지고 있는 룬을 세공하여","§7현재 손에 든 무기에","§7비어있는 룬 소켓이 있다면","§7룬을 장착 시켜 줍니다.","","§a룬 세공 성공률 : §f" +NPCscript.getInt("Job.SuccessRate")+"§3%","§a룬 세공 가격 : §e"+NPCscript.getInt("Job.Deal")+" "+MainServerOption.money,"","§e[좌 클릭시 룬 세공]","§c[우 클릭시 직업 변경]"), 4, inv);
			break;
			}
			Stack2("§6§l대화 수정", 403,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7와 대화를 할 시","§7플레이어에게 보일 대사를","§7추가/삭제/수정 합니다."), 19, inv);
			Stack2("§6§l거래 수정", 403,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7의","§7거래 품목을 좌클릭시","§7해당 아이템을 지급 받으며,","§7거래 품목을 우클릭시","§7해당 아이템은 구입/판매 중지됩니다.","","§f[구매/판매할 아이템 등록 명령어]","§6§l/상점 [구매/판매] [가격]"), 21, inv);
			Stack2("§6§l퀘스트 수정", 403,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7의","§7퀘스트 내용을 수정합니다."), 23, inv);
			Stack2("§6§l선물 수정", 403,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7에게 플레이어가","§7주는 선물에 따라 상승하는","§7호감도 상승 수치를 수정합니다."), 25, inv);
			if(NPCscript.getBoolean("Sale.Enable"))
				Stack2("§6§l세일 설정", 38,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7와의 친밀도가 높을 경우","§7상점 판매 물품을 세일 해 줍니다.","","§a호감도 "+NPCscript.getInt("Sale.Minlove") + "이상시 " +NPCscript.getInt("Sale.discount")+"% 세일","", "§c[Shift + 우 클릭시 제거]"), 7, inv);
			else
				Stack2("§6§l세일 설정", 38,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7와의 친밀도가 높을 경우","§7상점 판매 물품을 세일 해 줍니다.","","§e[좌 클릭시 설정]"), 7, inv);
				
		}
		
		player.openInventory(inv);
	}
	
	public void ShopGUI(Player player, String NPCname, short page, boolean Buy, boolean isEditMode)
	{
		UserDataObject u = new UserDataObject();
		String UniqueCode = "§0§0§7§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[NPC] "+ChatColor.stripColor(NPCname));
		
		YamlLoader NPCscript = new YamlLoader();
	  	if(NPCscript.isExit("NPC/NPCData/"+ u.getNPCuuid(player)  +".yml") == false)
	  	{
	  		npc.NpcConfig NPCC = new npc.NpcConfig();
	  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
	  	}
		NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
		ItemStack item;
		ItemMeta IM = null;
		short a = 0;
		if(Buy == true)
		{
			a = (short) NPCscript.getConfigurationSection("Shop.Sell").getKeys(false).size();
			
			if(isEditMode == false)
				Stack2("§b     [구입]     ", 160,11,1,null, 0, inv);
			else
				Stack2("§b     [구입]     ", 160,11,1,Arrays.asList("§0-1"), 0, inv);
				
			Stack2("§b     [구입]     ", 160,11,1,null, 1, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 2, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 3, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 4, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 5, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 6, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 7, inv);
			Stack2("§b     [구입]     ", 160,11,1,Arrays.asList("§0"+page), 8, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 9, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 18, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 17, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 26, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 27, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 36, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 35, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 36, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 37, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 38, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 39, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 40, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 41, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 42, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 43, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 44, inv);
			Stack2("§c   [물품 판매]   ", 160,14,1,null, 49, inv);
			byte loc=0;
			boolean Sale = NPCscript.getBoolean("Sale.Enable");
			int discount = 0;
			if(Sale)
			{
				YamlLoader PlayerNPC = new YamlLoader();
			  	PlayerNPC = null;
		    	if(PlayerNPC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
		    	{
		    		PlayerNPC.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    		PlayerNPC.set(u.getNPCuuid(player)+".love", 0);
		    		PlayerNPC.set(u.getNPCuuid(player)+".Career", 0);
		    		PlayerNPC.saveConfig();
		    	}
		    	else
		    		PlayerNPC.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    	if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") >= NPCscript.getInt("Sale.Minlove"))
					discount = NPCscript.getInt("Sale.discount");
		    	else
		    		Sale = false;
			}
			for(int count = page*21; count < a; count++)
			{
				if(count > a || loc >= 21) break;
				item = NPCscript.getItemStack("Shop.Sell."+count + ".item");
				IM = item.getItemMeta();
				long price = NPCscript.getLong("Shop.Sell."+count+".price");
				if(Sale)
					price = price - ((price/100) * discount);
				if(item.hasItemMeta() == true)
				{
					if(item.getItemMeta().hasLore() == true)
					{
						String[] lore = new String[IM.getLore().size()+3];
						for(int counter=0; counter < lore.length-3;counter++)
							lore[counter] = IM.getLore().get(counter);
						lore[lore.length-3] = "";

						if(isEditMode == false)
						{
							if(price >= main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
								lore[lore.length-2] = "§c[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							else
								lore[lore.length-2] = "§b[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							lore[lore.length-1] = "§f[소지금 : " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(MainServerOption.money)+"]";
						}
						else
						{
							lore[lore.length-2] = "§f[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							lore[lore.length-1] = "§0"+ count;
						}
						IM.setLore(Arrays.asList(lore));
					}
					else
					{
						String[] lore = new String[3];
						lore[0] = "";
						if(isEditMode == false)
						{
							if(price >= main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
								lore[1] = "§c[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							else
								lore[1] = "§b[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							lore[2] = "§f[소지금 : " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(MainServerOption.money)+"]";
						}
						else
						{
							lore[1] = "§f[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							lore[2] = "§0"+ count;
						}
						IM.setLore(Arrays.asList(lore));
					}
				}
				else
				{
					if(isEditMode == false)
					{
						if(price >= main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
						{
							List<String> l = Arrays.asList("","§c[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]","§f[소지금 : " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(MainServerOption.money)+"]");
							IM.setLore(l);
						}
						else
						{
							List<String> l = Arrays.asList("","§b[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]","§f[소지금 : " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(MainServerOption.money)+"]");
							IM.setLore(l);
						}
					}
					else
					{
						List<String> l = Arrays.asList("","§f[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]","§0"+ count);
						IM.setLore(l);
					}
				}
				item.setItemMeta(IM);
				if(loc >=0 && loc <= 6)
					inv.setItem(loc+10, item);
				if(loc >=7 && loc <= 13)
					inv.setItem(loc+12, item);
				if(loc >=14 && loc <= 21)
					inv.setItem(loc+14, item);
					
				loc++;
			}
			if(a-(page*20)>21)
				Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다.", "§7페이지 : " + (page+2)), 50, inv);
				if(page!=0)
				Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다.", "§7페이지 : " + (page)), 48, inv);
			
		}
		else
		{
			a = (short) NPCscript.getConfigurationSection("Shop.Buy").getKeys(false).toArray().length;

			if(isEditMode == false)
				Stack2("§c     [판매]     ", 160,14,1,null, 0, inv);
			else
				Stack2("§c     [판매]     ", 160,14,1,Arrays.asList("§0-1"), 0, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 1, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 2, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 3, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 4, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 5, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 6, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 7, inv);
			Stack2("§c     [판매]     ", 160,14,1,Arrays.asList("§0"+page), 8, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 9, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 18, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 17, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 26, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 27, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 36, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 35, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 36, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 37, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 38, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 39, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 40, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 41, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 42, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 43, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 44, inv);
			Stack2("§b   [물품 구매]   ", 160,11,1,null, 49, inv);
			byte loc=0;
			for(int count = page*21; count < a; count++)
			{
				if(count > a || loc >= 21) break;
				item = NPCscript.getItemStack("Shop.Buy."+count + ".item");
				IM = item.getItemMeta();
				long price = NPCscript.getLong("Shop.Buy."+count+".price");
				
				if(item.hasItemMeta() == true)
				{
					if(item.getItemMeta().hasLore() == true)
					{
						String[] lore = new String[IM.getLore().size()+3];
						for(int counter=0; counter < lore.length-3;counter++)
							lore[counter] = IM.getLore().get(counter);
						lore[lore.length-3] = "";

						if(isEditMode == false)
						{
							lore[lore.length-2] = "§b[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							lore[lore.length-1] = "§f[소지금 : " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(MainServerOption.money)+"]";
						}
						else
						{
							lore[lore.length-2] = "§f[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							lore[lore.length-1] = "§0"+ count;
						}
						IM.setLore(Arrays.asList(lore));
					}
					else
					{
						String[] lore = new String[3];
						lore[0] = "";
						
						if(isEditMode == false)
						{
							lore[1] = "§b[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							lore[2] = "§f[소지금 : " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(MainServerOption.money)+"]";
						}
						else
						{
							lore[1] = "§f[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]";
							lore[2] = "§0"+ count;
						}
						IM.setLore(Arrays.asList(lore));
					}
				}
				else
				{
					List<String> l =null;
					if(isEditMode == false)
						l = Arrays.asList("","§b[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]","§f[소지금 : " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(MainServerOption.money)+"]");
					else
						l = Arrays.asList("","§f[가격 : " + price + " "+ChatColor.stripColor(MainServerOption.money)+"]","§0"+ count);
					IM.setLore(l);
				}
				item.setItemMeta(IM);
				if(loc >=0 && loc <= 6)
					inv.setItem(loc+10, item);
				if(loc >=7 && loc <= 13)
					inv.setItem(loc+12, item);
				if(loc >=14 && loc <= 21)
					inv.setItem(loc+14, item);
					
				loc++;
			}
			if(a-(page*20)>21)
				Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다.", "§7페이지 : " + (page+2)), 50, inv);
				if(page!=0)
				Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다.", "§7페이지 : " + (page)), 48, inv);
		}

	
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ u.getNPCuuid(player)), 53, inv);

		player.openInventory(inv);
	}

	public void TalkGUI(Player player, String NPCname, String[] strings, char TalkType)
	{
		UserDataObject u = new UserDataObject();
		String UniqueCode = "§0§0§7§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0[NPC] "+ChatColor.stripColor(NPCname));

		Stack2("§f§l개인적인 대화", 340,0,1,Arrays.asList("§7사소한 이야깃 거리를 물어봅니다."), 2, inv);
		Stack2("§f§l근처의 소문", 340,0,1,Arrays.asList("§7최근 들리는 소문에 대해 물어봅니다."), 4, inv);
		Stack2("§f§l스킬에 대하여", 340,0,1,Arrays.asList("§7스킬에 대하여 물어봅니다."), 6, inv);
		if(TalkType != -1)
		if(strings != null)
			Stack2("§e§l "+NPCname, 386,0,1,Arrays.asList(new npc.NpcMain().getScript(player, TalkType)),(int) TalkType, inv);
		Stack2("§f§l이전 메뉴", 323,0,1,Arrays.asList("§7이전 메뉴로 돌아갑니다."), 0, inv);
		Stack2("§f§l나가기", 324,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7와의","§7대화를 종료합니다.","§0"+ u.getNPCuuid(player)), 8, inv);
		
		player.openInventory(inv);
	}
	
	public void QuestAddGUI(Player player, short page)
	{
		UserDataObject u = new UserDataObject();
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

	  	if(QuestList.isExit("NPC/NPCData/"+ u.getNPCuuid(player) +".yml") == false)
	  	{
	  		npc.NpcConfig NPCC = new npc.NpcConfig();
	  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
	  	}
		YamlLoader NPCscript = new YamlLoader();
		NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");

		String UniqueCode = "§0§0§7§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0등록 가능 퀘스트 목록 : " + (page+1));

		Object[] a= QuestList.getKeys().toArray();
		Object[] c =  NPCscript.getConfigurationSection("Quest").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = a[count].toString();
			Set<String> QuestFlow= QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false);

			boolean isExit =false;
			for(int counter = 0; counter < c.length; counter ++)
			{
				if(NPCscript.getString("Quest."+counter).equals(QuestName))
				{
					isExit = true;
					break;
				}
			}
			
				
			if(count > a.length || loc >= 45) break;

			String[] Temp = new String[12];
			Temp[0] = "§f퀘스트 구성 요소 : "+QuestFlow.size()+"개";
			int lorecount = 2;
			if(QuestList.getInt(QuestName + ".Need.LV")!=0)
			{
				Temp[lorecount] = "§c레벨 제한 : " + QuestList.getInt(QuestName + ".Need.LV") + " 이상";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.Love")!=0)
			{
				Temp[lorecount] = "§c친밀도 제한 : " + QuestList.getInt(QuestName + ".Need.Love") + " 이상";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.STR")!=0)
			{
				Temp[lorecount] = "§c"+MainServerOption.statSTR+" 제한 : " + QuestList.getInt(QuestName + ".Need.STR") + " 이상";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.DEX")!=0)
			{
				Temp[lorecount] = "§c"+MainServerOption.statDEX+" 제한 : " + QuestList.getInt(QuestName + ".Need.DEX") + " 이상";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.INT")!=0)
			{
				Temp[lorecount] = "§c"+MainServerOption.statINT+" 제한 : " + QuestList.getInt(QuestName + ".Need.INT") + " 이상";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.WILL")!=0)
			{
				Temp[lorecount] = "§c"+MainServerOption.statWILL+" 제한 : " + QuestList.getInt(QuestName + ".Need.WILL") + " 이상";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.LUK")!=0)
			{
				Temp[lorecount] = "§c"+MainServerOption.statLUK+" 제한 : " + QuestList.getInt(QuestName + ".Need.LUK" )+ " 이상";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Server.Limit")==-1)
			{
				Temp[lorecount] = "§c[퀘스트 수행인원 마감]";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Server.Limit")==0)
			{
				Temp[lorecount] = "§a[퀘스트 수행에 제한 없음]";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Server.Limit")>0)
			{
				Temp[lorecount] = "§3퀘스트 수행 가능 인원 : " + QuestList.getInt(QuestName + ".Server.Limit")+ " 명 남음";
				lorecount = lorecount+1;
			}
			if(QuestList.getString(QuestName + ".Need.PrevQuest") != null && QuestList.getString(QuestName + ".Need.PrevQuest").equalsIgnoreCase("null") == false)
			{
				if(QuestList.contains(QuestList.getString(QuestName + ".Need.PrevQuest")) == false)
				{
					QuestList.set(QuestName + ".Need.PrevQuest","null");
					QuestList.saveConfig();
				}
				else
				{
					Temp[lorecount] = "§c필수 완료 퀘스트 : " + QuestList.getString(QuestName + ".Need.PrevQuest");
					lorecount = lorecount+1;
				}
			}
			
			Temp[lorecount] = "";
			lorecount = lorecount+1;
			if(isExit == true)
				Temp[lorecount] = "§a[등록 된 퀘스트]";
			else
				Temp[lorecount] = "§c[등록 되지 않은 퀘스트]";
			lorecount = lorecount+1;
			
			String[] lore = new String[lorecount];
			for(int counter = 0; counter < lorecount; counter++)
				lore[counter] = Temp[counter];
			
			String QuestType = QuestList.getString(QuestName + ".Type");
			switch(QuestType)
			{
			case "N" :
				lore[1] = "§3퀘스트 타입 : 일반 퀘스트";
				Stack2("§f§l" + QuestName, 340,0,1,Arrays.asList(lore), loc, inv);
				break;
			case "R" :
				lore[1] ="§3퀘스트 타입 : 반복 퀘스트";
				Stack2("§f§l" + QuestName, 386,0,1,Arrays.asList(lore), loc, inv);
				break;
			case "D" :
				lore[1] ="§3퀘스트 타입 : 일일 퀘스트";
				Stack2("§f§l" + QuestName, 403,0,1,Arrays.asList(lore), loc, inv);
				break;
			case "W" :
				lore[1] ="§3퀘스트 타입 : 일주 퀘스트";
				Stack2("§f§l" + QuestName, 403,0,7,Arrays.asList(lore), loc, inv);
				break;
			case "M" :
				lore[1] ="§3퀘스트 타입 : 한달 퀘스트";
				Stack2("§f§l" + QuestName, 403,0,31,Arrays.asList(lore), loc, inv);
				break;
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		//Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ u.getNPCuuid(player)), 53, inv);
		player.openInventory(inv);
	}
	
	public void QuestListGUI(Player player, short page)
	{
		UserDataObject u = new UserDataObject();
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

	  	if(QuestList.isExit("NPC/NPCData/"+ u.getNPCuuid(player) +".yml") == false)
	  	{
	  		npc.NpcConfig NPCC = new npc.NpcConfig();
	  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
	  	}
		YamlLoader NPCscript = new YamlLoader();
		NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");

		String UniqueCode = "§0§0§7§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0진행 가능한 퀘스트 목록 : " + (page+1));

		Object[] a = NPCscript.getConfigurationSection("Quest").getKeys(false).toArray();

		YamlLoader PlayerQuestList = new YamlLoader();
		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
		
    	if(PlayerQuestList.isExit("Quest/PlayerData/"+player.getUniqueId()+".yml")==false)
    	{
    		PlayerQuestList.set("PlayerName", player.getName());
    		PlayerQuestList.set("PlayerUUID", player.getUniqueId().toString());
    		PlayerQuestList.createSection("Started");
    		PlayerQuestList.createSection("Ended");
    		PlayerQuestList.saveConfig();
    	}

		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
    	
		Set<String> PlayerHas = PlayerQuestList.getConfigurationSection("Started").getKeys(false);
		Set<String> PlayerFinished = PlayerQuestList.getConfigurationSection("Ended").getKeys(false);

		YamlLoader PlayerNPC = new YamlLoader();
    	if(PlayerNPC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
    	{
    		PlayerNPC.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    		PlayerNPC.set(u.getNPCuuid(player)+".love", 0);
    		PlayerNPC.set(u.getNPCuuid(player)+".Career", 0);
    		PlayerNPC.saveConfig();
    	}

		PlayerNPC.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = NPCscript.getString("Quest."+a[count]);
			if(count > a.length || loc >= 45) break;

			if(QuestList.contains(QuestName) == true)
			{
				if(QuestList.getString(QuestName + ".Need.PrevQuest") != null && QuestList.getString(QuestName + ".Need.PrevQuest").equalsIgnoreCase("null") == false)
				{
					if(QuestList.contains(QuestList.getString(QuestName + ".Need.PrevQuest")) == false)
					{
						QuestList.set(QuestName + ".Need.PrevQuest","null");
						QuestList.saveConfig();
					}
				}
				boolean gogogo = false;
				if(QuestList.getString(QuestName + ".Need.PrevQuest") == null || QuestList.getString(QuestName + ".Need.PrevQuest").equalsIgnoreCase("null") == true)
				{
					gogogo = true;
				}
				if(QuestList.getString(QuestName + ".Need.PrevQuest") != null && QuestList.getString(QuestName + ".Need.PrevQuest").equalsIgnoreCase("null") == false)
				{
					if(PlayerQuestList.contains("Ended."+QuestList.getString(QuestName + ".Need.PrevQuest")) == true)
						gogogo = true;
				}
				else
				{
					gogogo = true;
				}
				if(gogogo==true)
				{
					String[] Temp = new String[12];
					Temp[0]="";
					byte lorecount = 1;
					if(QuestList.getInt(QuestName + ".Need.LV")!=0)
					{
						YamlLoader Config = new YamlLoader();
					  	Config.getConfig("config.yml");
						if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
						{
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel() >= QuestList.getInt(QuestName + ".Need.LV"))
								Temp[lorecount] = "§a레벨 제한 : " + QuestList.getInt(QuestName + ".Need.LV") + " 이상";
							else
								Temp[lorecount] = "§c레벨 제한 : " + QuestList.getInt(QuestName + ".Need.LV") + " 이상";
						}
						else
						{
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level() >= QuestList.getInt(QuestName + ".Need.LV"))
								Temp[lorecount] = "§a레벨 제한 : " + QuestList.getInt(QuestName + ".Need.LV") + " 이상";
							else
								Temp[lorecount] = "§c레벨 제한 : " + QuestList.getInt(QuestName + ".Need.LV") + " 이상";
						}
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.Love")!=0)
					{
						if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") >= QuestList.getInt(QuestName + ".Need.Love"))
							Temp[lorecount] = "§a호감도 제한 : " + QuestList.getInt(QuestName + ".Need.Love") + " 이상";
						else
							Temp[lorecount] = "§c호감도 제한 : " + QuestList.getInt(QuestName + ".Need.Love") + " 이상";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.STR")!=0)
					{
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() >= QuestList.getInt(QuestName + ".Need.STR"))
							Temp[lorecount] = "§a"+MainServerOption.statSTR+" 제한 : " + QuestList.getInt(QuestName + ".Need.STR") + " 이상";
						else
							Temp[lorecount] = "§c"+MainServerOption.statSTR+" 제한 : " + QuestList.getInt(QuestName + ".Need.STR") + " 이상";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.DEX")!=0)
					{
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() >= QuestList.getInt(QuestName + ".Need.DEX"))
							Temp[lorecount] = "§a"+MainServerOption.statDEX+" 제한 : " + QuestList.getInt(QuestName + ".Need.DEX") + " 이상";
						else
							Temp[lorecount] = "§c"+MainServerOption.statDEX+" 제한 : " + QuestList.getInt(QuestName + ".Need.DEX") + " 이상";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.INT")!=0)
					{
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() >= QuestList.getInt(QuestName + ".Need.INT"))
							Temp[lorecount] = "§a"+MainServerOption.statINT+" 제한 : " + QuestList.getInt(QuestName + ".Need.INT") + " 이상";
						else
							Temp[lorecount] = "§c"+MainServerOption.statINT+" 제한 : " + QuestList.getInt(QuestName + ".Need.INT") + " 이상";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.WILL")!=0)
					{
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() >= QuestList.getInt(QuestName + ".Need.WILL"))
							Temp[lorecount] = "§a"+MainServerOption.statWILL+" 제한 : " + QuestList.getInt(QuestName + ".Need.WILL") + " 이상";
						else
							Temp[lorecount] = "§c"+MainServerOption.statWILL+" 제한 : " + QuestList.getInt(QuestName + ".Need.WILL") + " 이상";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.LUK")!=0)
					{
						if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() >= QuestList.getInt(QuestName + ".Need.LUK"))
							Temp[lorecount] = "§a"+MainServerOption.statLUK+" 제한 : " + QuestList.getInt(QuestName + ".Need.LUK" )+ " 이상";
						else
							Temp[lorecount] = "§c"+MainServerOption.statLUK+" 제한 : " + QuestList.getInt(QuestName + ".Need.LUK" )+ " 이상";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Server.Limit")!=0)
					{
						if(QuestList.getInt(QuestName + ".Server.Limit")==-1)
							Temp[lorecount] = "§c더이상 퀘스트를 수행할 수 없습니다.";
						else
							Temp[lorecount] = "§a퀘스트 수행 가능 인원 : " + QuestList.getInt(QuestName + ".Server.Limit")+ " 명 남음";
						lorecount++;
					}
					
					Temp[lorecount] = "";
					lorecount++;
					
					String[] lore = new String[lorecount];
					for(int counter = 0; counter < lorecount; counter++)
						lore[counter] = Temp[counter];
					
					
					switch(QuestList.getString(QuestName+".Type"))
					{
					case "N" :
						if(PlayerHas.toString().contains(QuestName) == false && PlayerFinished.toString().contains(QuestName) == false)
						{
							lore[0] ="§f[일반 퀘스트]";
							Stack2("§f§l" + QuestName, 340,0,1,Arrays.asList(lore), loc, inv);
							loc++;
						}
						break;
					case "R" :
						if(PlayerHas.toString().contains(QuestName) == false)
						{
							lore[0] ="§f[반복 퀘스트]";
							Stack2("§f§l" + QuestName, 386,0,1,Arrays.asList(lore), loc, inv);
							loc++;
						}
						break;
					case "D" :
						if(PlayerHas.toString().contains(QuestName) == false)
						{
							if(PlayerFinished.contains(QuestName) == true)
							{
								util.ETC ETC = new util.ETC();
								Object e[] = PlayerFinished.toArray();
								for(int counter=0; counter < e.length; counter++)
								{
									if(e[counter].toString().equalsIgnoreCase(QuestName))
									{
										Long ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + (86400000) ;
										if(ClearTime < ETC.getNowUTC())
										{
											lore[0] ="§f[일일 퀘스트]";
											Stack2("§f§l" + QuestName, 403,0,1,Arrays.asList(lore), loc, inv);
											loc++;
										}
										else
										{
											ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + (86400000) - ETC.getNowUTC() ;
											ClearTime = ClearTime/1000;
											lore[0] ="§f[일일 퀘스트]";
											String[] timelore = new String[lore.length+3];
											for(int counter2=0;counter2 < lore.length;counter2++)
												timelore[counter2] = lore[counter2];
											byte hour = 0;
											byte min = 0;
											
											hour = (byte) (ClearTime/3600);
											ClearTime = ClearTime-(3600*hour);
											
											min = (byte)(ClearTime/60);
											ClearTime = ClearTime-(60*min);
											
											timelore[lore.length]="";
											timelore[lore.length+1] = "§c[퀘스트 대기 시간]";
											if(hour>0)
												timelore[lore.length+2] = "§c"+hour+" 시간 " ;
											if(min>0)
												timelore[lore.length+2] =timelore[lore.length+2] + min+" 분 " ;
											if(ClearTime>0)
												timelore[lore.length+2] =timelore[lore.length+2] + ClearTime+" 초 " ;
											
											Stack2("§f§l" + QuestName, 403,0,1,Arrays.asList(timelore), loc, inv);
											loc++;
										}
									}
								}
							}
							else
							{
								lore[0] ="§f[일일 퀘스트]";
								Stack2("§f§l" + QuestName, 403,0,1,Arrays.asList(lore), loc, inv);
								loc++;
							}
						}
						break;
					case "W" :
						if(PlayerHas.toString().contains(QuestName) == false)
						{
							if(PlayerFinished.contains(QuestName) == true)
							{
								util.ETC ETC = new util.ETC();
								Object e[] = PlayerFinished.toArray();
								for(int counter=0; counter < e.length; counter++)
								{
									if(e[counter].toString().equalsIgnoreCase(QuestName))
									{
										Long ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + (604800000) ;
										if(ClearTime < ETC.getNowUTC())
										{
											lore[0] ="§f[주간 퀘스트]";
											Stack2("§f§l" + QuestName, 403,0,7,Arrays.asList(lore), loc, inv);
											loc++;
										}
										else
										{
											ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + (604800000) - ETC.getNowUTC() ;
											ClearTime = ClearTime/1000;
											lore[0] ="§f[주간 퀘스트]";
											String[] timelore = new String[lore.length+3];
											for(int counter2=0;counter2 < lore.length;counter2++)
												timelore[counter2] = lore[counter2];
											byte day = 0;
											byte hour = 0;
											byte min = 0;
											
											day = (byte) (ClearTime/86400);
											ClearTime = ClearTime-(86400*day);
											
											hour = (byte) (ClearTime/3600);
											ClearTime = ClearTime-(3600*hour);
											
											min = (byte)(ClearTime/60);
											ClearTime = ClearTime-(60*min);
											
											timelore[lore.length]="";
											timelore[lore.length+1] = "§c[퀘스트 대기 시간]";
											if(hour>0)
												timelore[lore.length+2] = "§c"+day+" 일 " ;
											if(hour>0)
												timelore[lore.length+2] = timelore[lore.length+2] +""+hour+" 시간 " ;
											if(min>0)
												timelore[lore.length+2] =timelore[lore.length+2] + min+" 분 " ;
											if(ClearTime>0)
												timelore[lore.length+2] =timelore[lore.length+2] + ClearTime+" 초 " ;
											
											Stack2("§f§l" + QuestName, 403,0,7,Arrays.asList(timelore), loc, inv);
											loc++;
										}
									}
								}
							}
							else
							{
								lore[0] ="§f[주간 퀘스트]";
								Stack2("§f§l" + QuestName, 403,0,7,Arrays.asList(lore), loc, inv);
								loc++;
							}
						}
						break;
					case "M" :
						if(PlayerHas.toString().contains(QuestName) == false)
						{
							if(PlayerFinished.contains(QuestName) == true)
							{
								util.ETC ETC = new util.ETC();
								Object e[] = PlayerFinished.toArray();
								for(int counter=0; counter < e.length; counter++)
								{
									if(e[counter].toString().equalsIgnoreCase(QuestName))
									{
										Long ClearTime = (PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + Long.parseLong("2678400000")) ;
										if(ClearTime < ETC.getNowUTC())
										{
											lore[0] ="§f[월간 퀘스트]";
											Stack2("§f§l" + QuestName, 403,0,31,Arrays.asList(lore), loc, inv);
											loc++;
										}
										else
										{
											ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + Long.parseLong("2678400000") - ETC.getNowUTC() ;
											ClearTime = ClearTime/1000;
											lore[0] ="§f[월간 퀘스트]";
											String[] timelore = new String[lore.length+3];
											for(int counter2=0;counter2 < lore.length;counter2++)
												timelore[counter2] = lore[counter2];
											byte day = 0;
											byte hour = 0;
											byte min = 0;
											
											day = (byte) (ClearTime/86400);
											ClearTime = ClearTime-(86400*day);
											
											hour = (byte) (ClearTime/3600);
											ClearTime = ClearTime-(3600*hour);
											
											min = (byte)(ClearTime/60);
											ClearTime = ClearTime-(60*min);
											
											timelore[lore.length]="";
											timelore[lore.length+1] = "§c[퀘스트 대기 시간]";
											if(hour>0)
												timelore[lore.length+2] = "§c"+day+" 일 " ;
											if(hour>0)
												timelore[lore.length+2] = timelore[lore.length+2] +""+hour+" 시간 " ;
											if(min>0)
												timelore[lore.length+2] =timelore[lore.length+2] + min+" 분 " ;
											if(ClearTime>0)
												timelore[lore.length+2] =timelore[lore.length+2] + ClearTime+" 초 " ;
											
											Stack2("§f§l" + QuestName, 403,0,31,Arrays.asList(timelore), loc, inv);
											loc++;
										}
									}
								}
							}
							else
							{
								lore[0] ="§f[월간 퀘스트]";
								Stack2("§f§l" + QuestName, 403,0,31,Arrays.asList(lore), loc, inv);
								loc++;
							}
						}
						break;
					}
				}
			}
			else
			{
				NPCscript.removeKey("Quest."+a[count]);
				NPCscript.saveConfig();
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		//Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ u.getNPCuuid(player)), 53, inv);
		player.openInventory(inv);
	}
	
	public void NPCjobGUI(Player player,String NPCname)
	{
		UserDataObject u = new UserDataObject();
		String UniqueCode = "§0§0§7§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0NPC 직업 선택");

		Stack2("§f§l직업 없음", 397,3,1,Arrays.asList("§7NPC의 직업을 없앱니다."), 1, inv);
		Stack2("§f§l대장장이", 145,0,1,Arrays.asList("§7무기, 도구, 방어구 등등","§7금속으로 제작된 물건을 고칩니다."), 2, inv);
		Stack2("§f§l주술사", 116,0,1,Arrays.asList("§7플레이어에게 랜덤 버프를 겁니다."), 3, inv);
		Stack2("§f§l힐러", 373,8261,1,Arrays.asList("§7플레이어를 빠르게 치료해 줍니다."), 4, inv);
		Stack2("§f§l전직 교관", 314,0,1,Arrays.asList("§7플레이어가 전직 조건에 부합할 경우","§7플레이어를 특정 직업으로 전직 시켜줍니다.","","§c이 기능은 서버 시스템이","§c메이플 스토리일 경우만 사용 가능합니다."), 5, inv);
		Stack2("§f§l공간 이동술사", 368,0,1,Arrays.asList("§7특정 위치로 텔레포트 시켜줍니다."), 6, inv);
		Stack2("§f§l개조 장인", 417,0,1,Arrays.asList("§7아이템을 개조 해 줍니다."), 7, inv);
		Stack2("§f§l룬 세공사", 351,10,1,Arrays.asList("§7아이템에 룬을 장착 시켜줍니다."), 10, inv);

		Stack2("§f§l이전 메뉴", 323,0,1,Arrays.asList("§7이전 메뉴로 돌아갑니다.","§0"+ NPCname), 18, inv);
		Stack2("§f§l나가기", 324,0,1,Arrays.asList("§e"+ChatColor.stripColor(NPCname)+"§7와의","§7대화를 종료합니다.","§0"+ u.getNPCuuid(player)), 26, inv);
		
		player.openInventory(inv);
	}
	
	public void WarpMainGUI(Player player, int page,String NPCname)
	{
		UserDataObject u = new UserDataObject();
		YamlLoader NPCConfig = new YamlLoader();
		NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		String UniqueCode = "§0§0§7§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC 워프 가능 목록 : " + (page+1));
		Object[] WarpList= NPCConfig.getConfigurationSection("Job.WarpList").getKeys(false).toArray();
		YamlLoader AreaConfig = new YamlLoader();
		AreaConfig.getConfig("Area/AreaList.yml");

		Object[] AreaList = null;
		boolean isExit = false;
		for(int c = 0; c < 5; c++)
		{
			AreaList = AreaConfig.getKeys().toArray();
			for(int count = 0; count < WarpList.length;count++)
			{
				isExit = false;
				for(int counter = 0; counter < AreaList.length; counter++)
				{
					if(AreaList[counter].equals(NPCConfig.getString("Job.WarpList."+count+".Area"))==true)
					{
						isExit = true;
						break;
					}
				}
				if(isExit == false)
				{
					short Acount =  (short) (WarpList.length-1);
					for(int counter = count;counter <Acount;counter++)
						NPCConfig.set("Job.WarpList."+counter, NPCConfig.get("Job.WarpList."+(counter+1)));
					NPCConfig.removeKey("Job.WarpList."+Acount);
				}
			}
			NPCConfig.saveConfig();
		}
		WarpList= NPCConfig.getConfigurationSection("Job.WarpList").getKeys(false).toArray();
		byte loc=0;
		for(int count = page*45; count < WarpList.length;count++)
		{
			if(count > WarpList.length || loc >= 45) break;

			if(player.isOp() == true)
			Stack2("§f§l" + NPCConfig.getString("Job.WarpList."+count+".DisplayName"), 368,0,1,Arrays.asList("",
					"§e워프 비용 : §f" +NPCConfig.getInt("Job.WarpList."+count+".Cost")+" "+MainServerOption.money
					,"","§e[좌 클릭시 해당 지역으로 이동]","§c[Shift + 우클릭시 영역 삭제]"), loc, inv);
			else
				Stack2("§f§l" + NPCConfig.getString("Job.WarpList."+count+".DisplayName"), 368,0,1,Arrays.asList("",
						"§e워프 비용 : §f" +NPCConfig.getInt("Job.WarpList."+count+".Cost") +" "+MainServerOption.money
						,"","§e[좌 클릭시 해당 지역으로 이동]"), loc, inv);
			loc++;
		}
		
		if(WarpList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		if(player.isOp() == true)
			Stack2("§f§l새 워프", 381,0,1,Arrays.asList("§7새로운 워프 지점을 생성합니다.","","§e[영역을 설정한 지역만 등록 가능합니다.]"), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void WarperGUI(Player player, short page, String NPCname)
	{
		YamlLoader AreaConfig = new YamlLoader();
		AreaConfig.getConfig("Area/AreaList.yml");

		String UniqueCode = "§0§0§7§0§7§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC 워프 등록 : " + (page+1));

		Object[] AreaList= AreaConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < AreaList.length;count++)
		{
			String AreaName = AreaList[count].toString();
			
			if(count > AreaList.length || loc >= 45) break;
			String world = AreaConfig.getString(AreaName+".World");
			int MinXLoc = AreaConfig.getInt(AreaName+".X.Min");
			short MinYLoc = (short) AreaConfig.getInt(AreaName+".Y.Min");
			int MinZLoc = AreaConfig.getInt(AreaName+".Z.Min");
			int MaxXLoc = AreaConfig.getInt(AreaName+".X.Max");
			short MaxYLoc = (short) AreaConfig.getInt(AreaName+".Y.Max");
			int MaxZLoc = AreaConfig.getInt(AreaName+".Z.Max");
			Stack2("§f§l" + AreaName, 395,0,1,Arrays.asList(
					"§3월드 : "+world,"§3X 영역 : "+MinXLoc+" ~ " + MaxXLoc
					,"§3Y 영역 : "+MinYLoc+" ~ " + MaxYLoc
					,"§3Z 영역 : "+MinZLoc+" ~ " + MaxZLoc
					,"","§e[좌 클릭시 워프 추가]"), loc, inv);
			
			loc++;
		}
		
		if(AreaList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void UpgraderGUI(Player player, short page, String NPCname)
	{
		UserDataObject u = new UserDataObject();
		YamlLoader NPCConfig = new YamlLoader();
		NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		YamlLoader UpgradeRecipe = new YamlLoader();
		UpgradeRecipe.getConfig("Item/Upgrade.yml");

		String UniqueCode = "§0§0§7§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC 개조 장인 : " + (page+1));

		Object[] UpgradeAbleList = NPCConfig.getConfigurationSection("Job.UpgradeRecipe").getKeys(false).toArray();
		long playerMoney = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
		byte loc=0;
		for(int count = page*45; count < UpgradeAbleList.length;count++)
		{
			String RecipeName = UpgradeAbleList[count].toString();
			
			String Lore=null;
			if(UpgradeRecipe.getString(RecipeName+".Only").equals("null"))
				Lore = "§f[모든 장비]%enter%%enter%";
			else
				Lore = UpgradeRecipe.getString(RecipeName+".Only")+"%enter%%enter%";

			if(UpgradeRecipe.getInt(RecipeName+".MaxDurability") > 0)
				Lore = Lore+"§3 ▲ 최대 내구도 : "+UpgradeRecipe.getInt(RecipeName+".MaxDurability")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaxDurability") < 0)
				Lore = Lore+"§c ▼ 최대 내구도 : "+UpgradeRecipe.getInt(RecipeName+".MaxDurability")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MinDamage") > 0)
				Lore = Lore+"§3 ▲ 최소 "+MainServerOption.damage+" : "+UpgradeRecipe.getInt(RecipeName+".MinDamage")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MinDamage") < 0)
				Lore = Lore+"§c ▼ 최소 "+MainServerOption.damage+" : "+UpgradeRecipe.getInt(RecipeName+".MinDamage")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MaxDamage") > 0)
				Lore = Lore+"§3 ▲ 최대 "+MainServerOption.damage+" : "+UpgradeRecipe.getInt(RecipeName+".MaxDamage")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaxDamage") < 0)
				Lore = Lore+"§c ▼ 최대 "+MainServerOption.damage+" : "+UpgradeRecipe.getInt(RecipeName+".MaxDamage")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MinMaDamage") > 0)
				Lore = Lore+"§3 ▲ 최소 "+MainServerOption.magicDamage+" : "+UpgradeRecipe.getInt(RecipeName+".MinMaDamage")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MinMaDamage") < 0)
				Lore = Lore+"§c ▼ 최소 "+MainServerOption.magicDamage+" : "+UpgradeRecipe.getInt(RecipeName+".MinMaDamage")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MaxMaDamage") > 0)
				Lore = Lore+"§3 ▲ 최대 "+MainServerOption.magicDamage+" : "+UpgradeRecipe.getInt(RecipeName+".MaxMaDamage")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaxMaDamage") < 0)
				Lore = Lore+"§c ▼ 최대 "+MainServerOption.magicDamage+" : "+UpgradeRecipe.getInt(RecipeName+".MaxMaDamage")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".DEF") > 0)
				Lore = Lore+"§3 ▲ 방어 : "+UpgradeRecipe.getInt(RecipeName+".DEF")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".DEF") < 0)
				Lore = Lore+"§c ▼ 방어 : "+UpgradeRecipe.getInt(RecipeName+".DEF")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".Protect") > 0)
				Lore = Lore+"§3 ▲ 보호 : "+UpgradeRecipe.getInt(RecipeName+".Protect")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".Protect") < 0)
				Lore = Lore+"§c ▼ 보호 : "+UpgradeRecipe.getInt(RecipeName+".Protect")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MaDEF") > 0)
				Lore = Lore+"§3 ▲ 마법 방어 : "+UpgradeRecipe.getInt(RecipeName+".MaDEF")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaDEF") < 0)
				Lore = Lore+"§c ▼ 마법 방어 : "+UpgradeRecipe.getInt(RecipeName+".MaDEF")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MaProtect") > 0)
				Lore = Lore+"§3 ▲ 마법 보호 : "+UpgradeRecipe.getInt(RecipeName+".MaProtect")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaProtect") < 0)
				Lore = Lore+"§c ▼ 마법 보호 : "+UpgradeRecipe.getInt(RecipeName+".MaProtect")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".Balance") > 0)
				Lore = Lore+"§3 ▲ 밸런스 : "+UpgradeRecipe.getInt(RecipeName+".Balance")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".Balance") < 0)
				Lore = Lore+"§c ▼ 밸런스 : "+UpgradeRecipe.getInt(RecipeName+".Balance")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".Critical") > 0)
				Lore = Lore+"§3 ▲ 크리티컬 : "+UpgradeRecipe.getInt(RecipeName+".Critical")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".Critical") < 0)
				Lore = Lore+"§c ▼ 크리티컬 : "+UpgradeRecipe.getInt(RecipeName+".Critical")+"%enter%";
			
			Lore = Lore+"%enter%"+UpgradeRecipe.getString(RecipeName+".Lore")+"%enter%%enter%";

			Lore = Lore+"§e ▶ 개조 횟수 : §f"+UpgradeRecipe.getInt(RecipeName+".UpgradeAbleLevel")+"§e 회째 개조 가능%enter%";
			Lore = Lore+"§e ▶ 필요 숙련도 : §f"+UpgradeRecipe.getInt(RecipeName+".DecreaseProficiency")+"%enter% ";

			Lore = Lore+"%enter%§c§l개조 비용 : "+NPCConfig.getInt("Job.UpgradeRecipe."+RecipeName)+" "+MainServerOption.money+"%enter% " ;

			if(playerMoney < NPCConfig.getInt("Job.UpgradeRecipe."+RecipeName+".Cost"))
				Lore = Lore+"§c§l현재 금액 : " +playerMoney+" "+MainServerOption.money+"%enter%";
			else
				Lore = Lore+"§b§l현재 금액 : " +playerMoney+" "+MainServerOption.money+"%enter%";
				
			if(player.isOp() == true)
				Lore = Lore+"%enter%§c[Shift + 우 클릭시 개조식 제거]%enter% ";
			
			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			
			if(count > UpgradeAbleList.length || loc >= 45) break;
			Stack2("§f§l" + RecipeName, 395,0,1,Arrays.asList(scriptA), loc, inv);
			
			loc++;
		}
		
		if(UpgradeAbleList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		if(player.isOp() == true)
			Stack2("§f§l개조식 추가", 386,0,1,Arrays.asList("§7현재 개조 장인이 새로운","§7개조 레시피를 알게 합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void SelectUpgradeRecipeGUI(Player player, short page, String NPCname)
	{
		YamlLoader RecipeList = new YamlLoader();
		RecipeList.getConfig("Item/Upgrade.yml");

		String UniqueCode = "§0§0§7§0§9§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC 개조식 추가 : " + (page+1));

		Object[] a= RecipeList.getKeys().toArray();

		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			String ItemName =a[count].toString();
			String Lore=null;
			if(RecipeList.getString(ItemName+".Only").equals("null"))
				Lore = "§f[모든 장비]%enter%%enter%";
			else
				Lore = RecipeList.getString(ItemName+".Only")+"%enter%%enter%";

			if(RecipeList.getInt(ItemName+".MaxDurability") > 0)
				Lore = Lore+"§3 ▲ 최대 내구도 : "+RecipeList.getInt(ItemName+".MaxDurability")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxDurability") < 0)
				Lore = Lore+"§c ▼ 최대 내구도 : "+RecipeList.getInt(ItemName+".MaxDurability")+"%enter%";
			if(RecipeList.getInt(ItemName+".MinDamage") > 0)
				Lore = Lore+"§3 ▲ 최소 "+MainServerOption.damage+" : "+RecipeList.getInt(ItemName+".MinDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MinDamage") < 0)
				Lore = Lore+"§c ▼ 최소 "+MainServerOption.damage+" : "+RecipeList.getInt(ItemName+".MinDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaxDamage") > 0)
				Lore = Lore+"§3 ▲ 최대 "+MainServerOption.damage+" : "+RecipeList.getInt(ItemName+".MaxDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxDamage") < 0)
				Lore = Lore+"§c ▼ 최대 "+MainServerOption.damage+" : "+RecipeList.getInt(ItemName+".MaxDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MinMaDamage") > 0)
				Lore = Lore+"§3 ▲ 최소 "+MainServerOption.magicDamage+" : "+RecipeList.getInt(ItemName+".MinMaDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MinMaDamage") < 0)
				Lore = Lore+"§c ▼ 최소 "+MainServerOption.magicDamage+" : "+RecipeList.getInt(ItemName+".MinMaDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaxMaDamage") > 0)
				Lore = Lore+"§3 ▲ 최대 "+MainServerOption.magicDamage+" : "+RecipeList.getInt(ItemName+".MaxMaDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxMaDamage") < 0)
				Lore = Lore+"§c ▼ 최대 "+MainServerOption.magicDamage+" : "+RecipeList.getInt(ItemName+".MaxMaDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".DEF") > 0)
				Lore = Lore+"§3 ▲ 방어 : "+RecipeList.getInt(ItemName+".DEF")+"%enter%";
			else if(RecipeList.getInt(ItemName+".DEF") < 0)
				Lore = Lore+"§c ▼ 방어 : "+RecipeList.getInt(ItemName+".DEF")+"%enter%";
			if(RecipeList.getInt(ItemName+".Protect") > 0)
				Lore = Lore+"§3 ▲ 보호 : "+RecipeList.getInt(ItemName+".Protect")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Protect") < 0)
				Lore = Lore+"§c ▼ 보호 : "+RecipeList.getInt(ItemName+".Protect")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaDEF") > 0)
				Lore = Lore+"§3 ▲ 마법 방어 : "+RecipeList.getInt(ItemName+".MaDEF")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaDEF") < 0)
				Lore = Lore+"§c ▼ 마법 방어 : "+RecipeList.getInt(ItemName+".MaDEF")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaProtect") > 0)
				Lore = Lore+"§3 ▲ 마법 보호 : "+RecipeList.getInt(ItemName+".MaProtect")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaProtect") < 0)
				Lore = Lore+"§c ▼ 마법 보호 : "+RecipeList.getInt(ItemName+".MaProtect")+"%enter%";
			if(RecipeList.getInt(ItemName+".Balance") > 0)
				Lore = Lore+"§3 ▲ 밸런스 : "+RecipeList.getInt(ItemName+".Balance")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Balance") < 0)
				Lore = Lore+"§c ▼ 밸런스 : "+RecipeList.getInt(ItemName+".Balance")+"%enter%";
			if(RecipeList.getInt(ItemName+".Critical") > 0)
				Lore = Lore+"§3 ▲ 크리티컬 : "+RecipeList.getInt(ItemName+".Critical")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Critical") < 0)
				Lore = Lore+"§c ▼ 크리티컬 : "+RecipeList.getInt(ItemName+".Critical")+"%enter%";
			
			Lore = Lore+"%enter%"+RecipeList.getString(ItemName+".Lore")+"%enter%%enter%";

			Lore = Lore+"§e ▶ 개조 횟수 : §f"+RecipeList.getInt(ItemName+".UpgradeAbleLevel")+"§e 회째 개조 가능%enter%";
			Lore = Lore+"§e ▶ 필요 숙련도 : §f"+RecipeList.getInt(ItemName+".DecreaseProficiency")+"%enter% ";

			Lore = Lore+"%enter%§e[좌 클릭시 개조식 등록]%enter%";

			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			Stack("§f"+ItemName, 395, 0, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void RuneEquipGUI(Player player, String NPCname)
	{
		UserDataObject u = new UserDataObject();
		String UniqueCode = "§1§0§7§0§a§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0NPC 룬 장착");

		YamlLoader NPCscript = new YamlLoader();
		NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player)  +".yml");
		Stack2("§9", 160,7,1,null, 0, inv);
		Stack2("§9", 160,7,1,null, 1, inv);
		Stack2("§9", 160,7,1,null, 2, inv);
		Stack2("§9", 160,7,1,null, 9, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 10, inv);
		Stack2("§9", 160,7,1,null,11, inv);
		Stack2("§9", 160,7,1,null,18, inv);
		Stack2("§9", 160,7,1,null,19, inv);
		Stack2("§9", 160,7,1,null,20, inv);
		
		Stack2("§9§l[룬을 올려 놓으세요]", 160,11,1,null, 3, inv);
		Stack2("§9§l[룬을 올려 놓으세요]", 160,11,1,null, 4, inv);
		Stack2("§9§l[룬을 올려 놓으세요]", 160,11,1,null, 5, inv);
		Stack2("§9§l[룬을 올려 놓으세요]", 160,11,1,null, 12, inv);
		Stack2("§9§l[룬을 올려 놓으세요]", 160,11,1,null,14, inv);
		Stack2("§9§l[룬을 올려 놓으세요]", 160,11,1,null,21, inv);
		Stack2("§9§l[룬을 올려 놓으세요]", 160,11,1,null,22, inv);
		Stack2("§9§l[룬을 올려 놓으세요]", 160,11,1,null,23, inv);
		
		Stack2("§9", 160,7,1,null, 6, inv);
		Stack2("§9", 160,7,1,null, 7, inv);
		Stack2("§9", 160,7,1,null, 8, inv);
		Stack2("§9", 160,7,1,null, 15, inv);
		Stack2("§b§l[     룬 장착     ]", 145,0,1,Arrays.asList("","§a룬 세공 성공률 : §f" +NPCscript.getInt("Job.SuccessRate")+"§a%","§a룬 세공 가격 : §e"+NPCscript.getInt("Job.Deal")+MainServerOption.money), 16, inv);
		Stack2("§9", 160,7,1,null,17, inv);
		Stack2("§9", 160,7,1,null,24, inv);
		Stack2("§9", 160,7,1,null,25, inv);
		Stack2("§9", 160,7,1,Arrays.asList("§0"+NPCname),26, inv);
		player.openInventory(inv);
	}
	
	public void NPCTalkGUI(Player player, short page, String NPCname,String TalkType)
	{
		UserDataObject u = new UserDataObject();
		YamlLoader NPCConfig = new YamlLoader();
		NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");

		String UniqueCode = "§0§0§7§0§b§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC 일반 대사 : " + (page+1));
		switch(TalkType)
		{
		case "NT"://NatureTalk
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC [개인적인 대화] 대사 : " + (page+1));
			break;
		case "NN"://NearbyNews
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC [근처의 소문] 대사 : " + (page+1));
			break;
		case "AS"://AboutSkill
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC [스킬에 관하여] 대사 : " + (page+1));
			break;
		}
		Object[] TalkList = NPCConfig.getConfigurationSection("NatureTalk").getKeys(false).toArray();
		switch(TalkType)
		{
		case "NT"://NatureTalk
			TalkList = NPCConfig.getConfigurationSection("NatureTalk").getKeys(false).toArray();
			break;
		case "NN"://NearbyNews
			TalkList = NPCConfig.getConfigurationSection("NearByNEWS").getKeys(false).toArray();
			break;
		case "AS"://AboutSkill
			TalkList = NPCConfig.getConfigurationSection("AboutSkills").getKeys(false).toArray();
			break;
		}
		int loc=0;
		for(int count = page*45; count < TalkList.length;count++)
		{
			if(count > TalkList.length || loc >= 45) break;
			String Lore = "";
			switch(TalkType)
			{
			case "NT"://NatureTalk
				Lore = "%enter%§d[필요 호감도]%enter%§f"+NPCConfig.getInt("NatureTalk."+(count+1)+".love")+" ~ "+NPCConfig.getInt("NatureTalk."+(count+1)+".loveMax")+"%enter%%enter%§e[등록된 대사]%enter%§f"+NPCConfig.getString("NatureTalk."+(count+1)+".Script");
				break;
			case "NN"://NearbyNews
				Lore = "%enter%§d[필요 호감도]%enter%§f"+NPCConfig.getInt("NearByNEWS."+(count+1)+".love")+" ~ "+NPCConfig.getInt("NearByNEWS."+(count+1)+".loveMax")+"%enter%%enter%§e[등록된 대사]%enter%§f"+NPCConfig.getString("NearByNEWS."+(count+1)+".Script");
				break;
			case "AS"://AboutSkill
				Lore = "%enter%§d[필요 호감도]%enter%§f"+NPCConfig.getInt("AboutSkills."+(count+1)+".love")+" ~ "+NPCConfig.getInt("AboutSkills."+(count+1)+".loveMax")+"%enter%%enter%§e[스킬 전수 대사]%enter%§f"+NPCConfig.getString("AboutSkills."+(count+1)+".Script")+"%enter%%enter%§e[전수 이후 대사]%enter%§f"+NPCConfig.getString("AboutSkills."+(count+1)+".AlreadyGetScript")+"%enter%%enter%§e[배울 수 있는 스킬]%enter%§f"+NPCConfig.getString("AboutSkills."+(count+1)+".giveSkill")+"%enter%§c[서버 시스템이 마비노기 일 경우만 습득 합니다.]";
				break;
			}
			
			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			Stack2("§f§l"+ (count+1), 386,0,1,Arrays.asList(scriptA), loc, inv);
			
			loc=loc+1;
		}
		
		if(TalkList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		Stack2("§f§l[대사 추가]", 403,0,1,Arrays.asList("§7대사를 추가 시킵니다."), 49, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		switch(TalkType)
		{
		case "NT"://NatureTalk
			Stack2("§f§l[근처의 소문]", 340,0,1,Arrays.asList("§7다른 주제의 대사를 설정합니다."), 46, inv);
			Stack2("§f§l[스킬에 관하여]", 340,0,1,Arrays.asList("§7다른 주제의 대사를 설정합니다."), 52, inv);
			break;
		case "NN"://NearbyNews
			Stack2("§f§l[스킬에 관하여]", 340,0,1,Arrays.asList("§7다른 주제의 대사를 설정합니다."), 46, inv);
			Stack2("§f§l[개인적인 대화]", 340,0,1,Arrays.asList("§7다른 주제의 대사를 설정합니다."), 52, inv);
			break;
		case "AS"://AboutSkill
			Stack2("§f§l[개인적인 대화]", 340,0,1,Arrays.asList("§7다른 주제의 대사를 설정합니다."), 46, inv);
			Stack2("§f§l[근처의 소문]", 340,0,1,Arrays.asList("§7다른 주제의 대사를 설정합니다."), 52, inv);
			break;
		}
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+TalkType), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void TalkSettingGUI(Player player,String NPCname, String TalkType, short TalkNumber)
	{
		UserDataObject u = new UserDataObject();
		YamlLoader NPCConfig = new YamlLoader();
		NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");

		String UniqueCode = "§0§0§7§0§c§r";
		Inventory inv = Bukkit.createInventory(null, 36, UniqueCode + "§0NPC 대사 설정");
		String Lore = "";
		switch(TalkType)
		{
		case "NT"://NatureTalk
			Lore = "%enter%§d[필요 호감도]%enter%§f"+NPCConfig.getInt("NatureTalk."+TalkNumber+".love")+" ~ "+NPCConfig.getInt("NatureTalk."+TalkNumber+".loveMax")+"%enter%%enter%§e[등록된 대사]%enter%§f"+NPCConfig.getString("NatureTalk."+TalkNumber+".Script");
			break;
		case "NN"://NearbyNews
			Lore = "%enter%§d[필요 호감도]%enter%§f"+NPCConfig.getInt("NearByNEWS."+TalkNumber+".love")+" ~ "+NPCConfig.getInt("NearByNEWS."+TalkNumber+".loveMax")+"%enter%%enter%§e[등록된 대사]%enter%§f"+NPCConfig.getString("NearByNEWS."+TalkNumber+".Script");
			break;
		case "AS"://AboutSkill
			Lore = "%enter%§d[필요 호감도]%enter%§f"+NPCConfig.getInt("AboutSkills."+TalkNumber+".love")+" ~ "+NPCConfig.getInt("AboutSkills."+TalkNumber+".loveMax")+"%enter%%enter%§e[스킬 전수 대사]%enter%§f"+NPCConfig.getString("AboutSkills."+TalkNumber+".Script")+"%enter%%enter%§e[전수 이후 대사]%enter%§f"+NPCConfig.getString("AboutSkills."+TalkNumber+".AlreadyGetScript")+"%enter%%enter%§e[배울 수 있는 스킬]%enter%§f"+NPCConfig.getString("AboutSkills."+TalkNumber+".giveSkill")+"%enter%§c[서버 시스템이 마비노기 일 경우만 습득 합니다.]";
			break;
		}
			
			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];

			Stack2("§b[     대사     ]", 160,3,1,null, 0, inv);
			Stack2("§b[     대사     ]", 160,3,1,null, 1, inv);
			Stack2("§b[     대사     ]", 160,3,1,null, 2, inv);
			Stack2("§b[     대사     ]", 160,3,1,null, 9, inv);
			Stack2("§f§l"+ TalkNumber, 386,0,1,Arrays.asList(scriptA), 10, inv);
			Stack2("§b[     대사     ]", 160,3,1,null, 11, inv);
			Stack2("§b[     대사     ]", 160,3,1,null, 18, inv);
			Stack2("§b[     대사     ]", 160,3,1,null, 19, inv);
			Stack2("§b[     대사     ]", 160,3,1,null, 20, inv);
			

			Stack2("§7[     옵션     ]", 160,7,1,null, 3, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 4, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 5, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 6, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 7, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 8, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 12, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 17, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 21, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 22, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 23, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 24, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 25, inv);
			Stack2("§7[     옵션     ]", 160,7,1,null, 26, inv);
			

			switch(TalkType)
			{
			case "NT"://NatureTalk
			case "NN"://NearbyNews
				Stack2("§d[     호감도     ]", 38,0,1,null, 13, inv);
				Stack2("§f[     대 사     ]", 386,0,1,null, 14, inv);
				break;
			case "AS"://AboutSkill
				Stack2("§d[     호감도     ]", 38,0,1,null, 13, inv);
				Stack2("§f[     대 사 1     ]", 386,0,1,null, 14, inv);
				Stack2("§3[     스킬     ]", 403,0,1,null, 15, inv);
				Stack2("§f[     대 사 2     ]", 386,0,1,null, 16, inv);
				break;
			}
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+TalkType), 27, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 35, inv);
		player.openInventory(inv);
	}
	
	public void AddAbleSkillsGUI(Player player, short page, String NPCname,int TalkNumber)
	{
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/JobList.yml");
		YamlLoader RealSkills = new YamlLoader();
		RealSkills.getConfig("Skill/SkillList.yml");

		String UniqueCode = "§0§0§7§0§d§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0NPC 가르칠 스킬 선택 : " + (page+1));
		Object[] Skills = SkillList.getConfigurationSection("Mabinogi.Added").getKeys(false).toArray();

		byte loc=0;
		for(int count = page*45; count < Skills.length;count++)
		{
			if(count > Skills.length || loc >= 45) break;
			if(RealSkills.contains(Skills[count].toString())==true)
			{
				Stack2("§f§l"+ Skills[count], RealSkills.getInt(Skills[count].toString()+".ID"),RealSkills.getInt(Skills[count].toString()+".DATA"),RealSkills.getInt(Skills[count].toString()+".Amount"),Arrays.asList("","§a[최대 랭크]","§f"+RealSkills.getConfigurationSection(Skills[count].toString()+".SkillRank").getKeys(false).size()), loc, inv);
				loc++;
			}
		}
		
		if(Skills.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+TalkNumber), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 53, inv);
		player.openInventory(inv);
	}

	public void ItemBuy(Player player, ItemStack item, int value, String NPCname, boolean isItemBuy, int count)
	{
		Inventory inv = null;
		String itemName = null;
		String UniqueCode = "§0§0§7§0§e§r";
		if(item.hasItemMeta()&&item.getItemMeta().hasDisplayName())
			itemName = item.getItemMeta().getDisplayName();
		else
			itemName = new event.EventInteract().SetItemDefaultName((short)item.getTypeId(), item.getData().getData());
		if(isItemBuy)
		{
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[NPC]§9§l 물품 구매");
			Stack2("§b     [구입]     ", 160,11,1,Arrays.asList("§0"+value), 0, inv);
			Stack2("§b     [구입]     ", 160,11,1,Arrays.asList("§0"+count), 1, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 2, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 3, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 4, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 5, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 6, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 7, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 8, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 9, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 18, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 17, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 26, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 27, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 36, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 35, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 36, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 37, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 38, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 39, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 40, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 41, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 42, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 43, inv);
			Stack2("§b     [구입]     ", 160,11,1,null, 44, inv);
			Stack2("§b§l[물품 구매]", 54,0,1,Arrays.asList(itemName+"§f§l×"+item.getAmount()+"§f 아이템을","§f총 §e"+ChatColor.BOLD+count +"§f 개 구매합니다."
					,"","§f가격 : §e"+ value*count + " §6"+ MainServerOption.money, "§f소지금 : §f"+ChatColor.BOLD+MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+" "+MainServerOption.money), 49, inv);
		}
		else
		{
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0[NPC]§c§l 물품 판매");
			Stack2("§c     [판매]     ", 160,14,1,Arrays.asList("§0"+value), 0, inv);
			Stack2("§c     [판매]     ", 160,14,1,Arrays.asList("§0"+count), 1, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 2, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 3, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 4, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 5, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 6, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 7, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 8, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 9, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 18, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 17, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 26, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 27, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 36, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 35, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 36, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 37, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 38, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 39, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 40, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 41, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 42, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 43, inv);
			Stack2("§c     [판매]     ", 160,14,1,null, 44, inv);
			Stack2("§c§l[물품 판매]", 54,0,1,Arrays.asList(itemName+"§f§l×"+item.getAmount()+"§f 아이템을","§f총 §e"+ChatColor.BOLD+count +"§f 개 판매합니다."
					,"","§f가격 : §e"+ value*count + " §6"+ MainServerOption.money, "§f소지금 : §f"+ChatColor.BOLD+MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+" "+MainServerOption.money), 49, inv);
		}

		Stack2("§c§l     [개수 - 64]     ", 374,0,64,Arrays.asList("","§f현재 총 §e"+ChatColor.BOLD+count +"§f 개"), 19, inv);
		Stack2("§c§l     [개수 - 10]     ", 374,0,10,Arrays.asList("","§f현재 총 §e"+ChatColor.BOLD+count +"§f 개"), 20, inv);
		Stack2("§c§l     [개수 - 1]     ", 374,0,1,Arrays.asList("","§f현재 총 §e"+ChatColor.BOLD+count +"§f 개"), 21, inv);

		Stack2("§c§l     [최소한]     ", 325,0,1,Arrays.asList("","§f현재 총 §e"+ChatColor.BOLD+count +"§f 개"), 13, inv);
		ItemStackStack(item, 22, inv);
		Stack2("§b§l     [최대한]     ", 326,0,1,Arrays.asList("","§f현재 총 §e"+ChatColor.BOLD+count +"§f 개"), 31, inv);
		Stack2("§b§l     [개수 + 1]     ", 373,0,1,Arrays.asList("","§f현재 총 §e"+ChatColor.BOLD+count +"§f 개"), 23, inv);
		Stack2("§b§l     [개수 + 10]     ", 373,0,10,Arrays.asList("","§f현재 총 §e"+ChatColor.BOLD+count +"§f 개"), 24, inv);
		Stack2("§b§l     [개수 + 64]     ", 373,0,64,Arrays.asList("","§f현재 총 §e"+ChatColor.BOLD+count +"§f 개"), 25, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void ItemFix(Player player, String NPCname, int successRate, int value)
	{
		String UniqueCode = "§0§0§7§0§f§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0[NPC]§0§l 수리할 장비를 선택 하세요.");
		Stack2("§f§l이전 목록§0§e"+ChatColor.RED, 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+successRate,"§0"+value), 0, inv);
		Stack2("§f§l닫기§0§e"+ChatColor.RED, 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 8, inv);

		Stack2("§0§e"+ChatColor.RED, 265,0,1,null, 1, inv);
		Stack2("§0§e"+ChatColor.RED, 265,0,1,null, 2, inv);
		Stack2("§0§e"+ChatColor.RED, 265,0,1,null, 3, inv);
		Stack2("§6§l[장비 수리]§0§e"+ChatColor.RED, 145,0,1,Arrays.asList("§7인벤토리 속 내구도가 닳은 장비를","§7정해진 확률과 가격에 수리 해 줍니다.","","§3수리 성공률 : §f" +successRate+"§3 %","§a내구도 10 당 가격 : §e"+value+" "+ChatColor.GREEN+MainServerOption.money,"","§c[일반 아이템 수리 실패시]","§c - 수리 성공과 상관 없이 골드 소모","§c[커스텀 아이템 수리 실패시]","§c - 최대 내구도 감소"), 4, inv);
		Stack2("§0§e"+ChatColor.RED, 265,0,1,null, 5, inv);
		Stack2("§0§e"+ChatColor.RED, 265,0,1,null, 6, inv);
		Stack2("§0§e"+ChatColor.RED, 265,0,1,null, 7, inv);
		player.openInventory(inv);
	}
	
	public void PresentSettingGUI(Player player, String NPCname)
	{
		String UniqueCode = "§0§0§7§1§0§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0[NPC] 선물 가능 아이템 목록");
		Stack2("§f§l이전 목록§0§e"+ChatColor.RED, 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);
		Stack2("§f§l닫기§0§e"+ChatColor.RED, 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 8, inv);

		YamlLoader NPCConfig = new YamlLoader();
		NPCConfig.getConfig("NPC/NPCData/"+new UserDataObject().getNPCuuid(player)+".yml");
		
		Stack2("§f기타 아이템", 138,0,1,Arrays.asList("§7정해진 아이템 외의 다른","§7아이템을 주었을 때의","§7호감도 상승량을 설정합니다.","","§a호감도 : " + NPCConfig.getInt("Present.1.love")), 1, inv);

		for(int count = 2; count < 8; count++)
		{
			if(NPCConfig.getItemStack("Present."+count+".item") == null)
				Stack2("§c[정해지지 않은 선물]", 166,0,1,Arrays.asList("§e§l[클릭시 선물 지정]"), count, inv);
			else
			{
				ItemStack item = NPCConfig.getItemStack("Present."+count+".item");
				ItemMeta im = item.getItemMeta();
				if(im.hasLore() == false)
					im.setLore(Arrays.asList("","§a호감도 : " + NPCConfig.getInt("Present."+count+".love")));
				else
				{
					List<String> lore = item.getItemMeta().getLore();
					lore.add(lore.size(), "");
					lore.add(lore.size(),"§a호감도 : " + NPCConfig.getInt("Present."+count+".love"));
					im.setLore(lore);
				}
				item.setItemMeta(im);
				ItemStackStack(item, count, inv);
			}
		}
		player.openInventory(inv);
	}

	public void PresentGiveGUI(Player player, String NPCname, boolean isSettingMode, int number)
	{
		String UniqueCode = "§1§0§7§1§1§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0[NPC] 선물 아이템을 올려 주세요");
		if(isSettingMode)
		{
			Stack2("§f§l선물 등록§0§e"+ChatColor.RED, 389,0,1,Arrays.asList("§7이 아이템으로 설정합니다.","§0"+isSettingMode), 0, inv);

			YamlLoader NPCConfig = new YamlLoader();
			NPCConfig.getConfig("NPC/NPCData/"+new UserDataObject().getNPCuuid(player)+".yml");
			ItemStack item = NPCConfig.getItemStack("Present."+number+".item");
			if(item != null)
				ItemStackStack(item, 4, inv);
			Stack2("§f§l닫기§0§e"+ChatColor.RED, 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+NPCname), 8, inv);
		}
		else
		{
			Stack2("§f§l이전 목록§0§e"+ChatColor.RED, 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+isSettingMode), 0, inv);
			Stack2("§f§l선물 주기§0§e"+ChatColor.RED, 54,0,1,Arrays.asList("§7올려둔 아이템을 선물합니다.","§0"+NPCname), 8, inv);
		}
		Stack2("§0§e"+ChatColor.RED, 160,5,1,Arrays.asList("§0"+number), 1, inv);
		Stack2("§0§e"+ChatColor.RED, 160,5,1,null, 2, inv);
		Stack2("§0§e"+ChatColor.RED, 160,5,1,null, 3, inv);
		Stack2("§0§e"+ChatColor.RED, 160,5,1,null, 5, inv);
		Stack2("§0§e"+ChatColor.RED, 160,5,1,null, 6, inv);
		Stack2("§0§e"+ChatColor.RED, 160,5,1,null, 7, inv);
		player.openInventory(inv);
	}

	
	
	
	public void QuestAddGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
			if(slot == 48)//이전 페이지
				QuestAddGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//다음 페이지
				QuestAddGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				YamlLoader NPCscript = new YamlLoader();
				UserDataObject u = new UserDataObject();
				NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
				
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				Set<String> NPChasQuest = NPCscript.getConfigurationSection("Quest").getKeys(false);
				Object[] a = NPChasQuest.toArray();
				
				boolean isExit=false;
				for(int count = 0; count < a.length;count++)
				{
					if(NPCscript.getString("Quest."+a[count]).equalsIgnoreCase(QuestName) == true)
					{
						isExit = true;
						NPCscript.removeKey("Quest."+count);
					}
					if(isExit == true)
					{
						if(count < a.length-1)
						NPCscript.set("Quest."+count, NPCscript.getString("Quest."+(count+1)));
					}
				}
				if(isExit == true)
				{
					NPCscript.removeKey("Quest."+ (a.length-1));
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 0.8F);
					player.sendMessage("§c[SYSTEM] : 퀘스트 제거 완료!");
				}
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
					player.sendMessage("§a[SYSTEM] : 퀘스트 등록 완료!");
					NPCscript.set("Quest."+a.length, QuestName);
				}
				NPCscript.saveConfig();
				QuestAddGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
			}
		}
	}
	
	public void QuestListGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(slot == 48)//이전 페이지
				QuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//다음 페이지
				QuestListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlLoader PlayerQuest = new YamlLoader();
				PlayerQuest.getConfig("Quest/PlayerData/"+ player.getUniqueId().toString() +".yml");
				YamlLoader QuestList = new YamlLoader();
				QuestList.getConfig("Quest/QuestList.yml");
				YamlLoader Config = new YamlLoader();
				Config.getConfig("config.yml");
				for(int counter = 0; counter < event.getCurrentItem().getItemMeta().getLore().size();counter ++)
				{
					if(event.getCurrentItem().getItemMeta().getLore().get(counter).contains("대기") == true)
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
						player.sendMessage("§c[퀘스트] : 오늘은 더이상 퀘스트를 진행할 수 없습니다!");
						return;
					}
				}
				if(QuestList.getInt(QuestName+".Server.Limit") == -1)
				{
					SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
					player.sendMessage("§c[퀘스트] : 더이상 이 퀘스트는 수행 할 수 없습니다!");
					return;
				}
				else
				{
					int NeedLevel = QuestList.getInt(QuestName+".Need.LV");
					int NeedLove = QuestList.getInt(QuestName+".Need.Love");
					int NeedSTR = QuestList.getInt(QuestName+".Need.STR");
					int NeedDEX = QuestList.getInt(QuestName+".Need.DEX");
					int NeedINT = QuestList.getInt(QuestName+".Need.INT");
					int NeedWILL = QuestList.getInt(QuestName+".Need.WILL");
					int NeedLUK = QuestList.getInt(QuestName+".Need.LUK");
					String PrevQuest = QuestList.getString(QuestName+".Need.PrevQuest");
					
					int PLV = 0;
					if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
						PLV = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
					else
						PLV = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
					if(NeedLevel <= PLV)
					{
						if(NeedSTR <= main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()&&
						NeedDEX <= main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()&&
						NeedINT <= main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()&&
						NeedWILL <= main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()&&
						NeedLUK <= main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK())
						{
							UserDataObject u = new UserDataObject();
							YamlLoader PlayerNPC = new YamlLoader();
							PlayerNPC.getConfig("NPC/PlayerData/" + player.getUniqueId()+".yml");
							if(NeedLove <= PlayerNPC.getInt(u.getNPCuuid(player)+".love"))
							{
								if(PrevQuest.equalsIgnoreCase("null")||PlayerQuest.contains("Ended."+PrevQuest) == true)
								{
									if(QuestList.getInt(QuestName+".Server.Limit") != 0)
									{
										if(QuestList.getInt(QuestName+".Server.Limit") == 1|| QuestList.getInt(QuestName+".Server.Limit") < -1)
										{
											QuestList.set(QuestName+".Server.Limit", -1);
										}
										else
										{
											QuestList.set(QuestName+".Server.Limit", QuestList.getInt(QuestName+".Server.Limit")-1);
										}
										QuestList.saveConfig();
									}
									player.closeInventory();
									SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
									String message = Config.getString("Quest.AcceptMessage").replace("%QuestName%", QuestName);
									player.sendMessage(message);

									PlayerQuest.set("Started."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+".Flow", 0);
									PlayerQuest.set("Started."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+".Type", QuestList.getString(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+".FlowChart."+0+".Type"));
									
									PlayerQuest.saveConfig();
									
									new quest.QuestGui().QuestRouter(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
								}
								else
								{
									SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
									player.sendMessage("§c[퀘스트] : 이전 퀘스트를 진행하지 않아 퀘스트를 수행할 수 없습니다!");
									return;
								}
							}
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
								player.sendMessage("§c[퀘스트] : 호감도가 부족하여 퀘스트를 수행할 수 없습니다!");
								return;
							}
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
							player.sendMessage("§c[퀘스트] : 스텟이 부족하여 퀘스트를 수행할 수 없습니다!");
							return;
						}
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
						player.sendMessage("§c[퀘스트] : 수행 가능한 레벨이 아닙니다!");
						return;
					}
				}
			}
		}
	}
	
	public void MainGUIClick(InventoryClickEvent event, String NPCname)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(event.getClickedInventory().getType()!=InventoryType.PLAYER)
		{
			if(slot == 26)//나가기
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else if(slot == 8 && player.isOp())//GUI 비 활성화
			{
				SoundEffect.SP(player, Sound.ENTITY_VILLAGER_HURT, 0.8F, 1.0F);
				YamlLoader DNPC = new YamlLoader();
				DNPC.getConfig("NPC/DistrictNPC.yml");

				UserDataObject u = new UserDataObject();
				DNPC.set(u.getNPCuuid(player).toString(), true);
				DNPC.saveConfig();
				player.closeInventory();
				player.sendMessage("§e[NPC] : 해당 NPC는 GUI화면을 사용하지 않게 되었습니다!");
				player.sendMessage("§6/gui사용§f 명령어 입력 후, NPC 클릭시, 다시 활성화 됩니다.");
			}
			else if(slot == 4)//직업 아이콘
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				String Case = (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				if(Case.equals("대장장이") || Case.equals("룬 세공사") || Case.equals("주술사") ||
						Case.equals("힐러") || Case.equals("전직 교관") || Case.equals("공간 이동술사") ||
						Case.equals("개조 장인"))
				{
					if(event.getClick().isLeftClick() == true)
					{
						YamlLoader NPCscript = new YamlLoader();
						UserDataObject u = new UserDataObject();
					  	if(NPCscript.isExit("NPC/NPCData/"+ u.getNPCuuid(player) +".yml") == false)
					  	{
					  		npc.NpcConfig NPCC = new npc.NpcConfig();
					  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
					  	}
						NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
						util.UtilNumber n = new util.UtilNumber();

						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
						if(Case.equals("룬 세공사"))
							RuneEquipGUI(player, NPCname);
						else if(Case.equals("개조 장인"))
							UpgraderGUI(player, (short) 0,NPCname);
						else if(Case.equals("공간 이동술사"))
							WarpMainGUI(player, 0,NPCname);
						else if(Case.equals("개조 장인"))
							UpgraderGUI(player, (short) 0,NPCname);
						else if(Case.equals("대장장이"))
							ItemFix(player, NPCname, Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(3).split(" ")[3])), Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(4).split(" ")[5])));
						else if(Case.equals("주술사"))
						{
							util.ETC ETC = new util.ETC();
							YamlLoader Config = new YamlLoader();
							Config.getConfig("config.yml");

							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_BuffCoolTime()+(Config.getInt("NPC.Shaman.BuffCoolTime")*1000) > ETC.getNowUTC())
							{
								SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage("§c[SYSTEM] : §f"+((main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_BuffCoolTime()+(Config.getInt("NPC.Shaman.BuffCoolTime")*1000) -ETC.getNowUTC())/1000)+"§c초 후 이용 가능합니다!");
								return;
							}
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() < NPCscript.getInt("Job.Deal"))
							{
								SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage("§c[SYSTEM] : 복채 비용이 부족합니다!");
								return;
							}
							else
							{
								main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setETC_BuffCoolTime(ETC.getNowUTC());
								if(n.RandomNum(0, 100) <= NPCscript.getInt("Job.GoodRate"))
								{
									switch(n.RandomNum(1, 8))
									{
									case 1:
										SoundEffect.SP(player,Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
										player.sendMessage("§f§l[居安思危] 준비된 자의 견고함은 몸을 단단하게 할지니...");
										PottionBuff.givePotionEffect(player,PotionEffectType.DAMAGE_RESISTANCE, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										break;
									case 2:
										SoundEffect.SP(player,Sound.BLOCK_GRAVEL_HIT, 1.5F, 1.0F);
										player.sendMessage("§f§l[能小能大] 장인의 손은 작은 일과 큰 일을 가리지 않을지니...");
										PottionBuff.givePotionEffect(player,PotionEffectType.FAST_DIGGING, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										break;
									case 3:
										SoundEffect.SP(player,Sound.BLOCK_FIRE_EXTINGUISH, 1.5F, 1.0F);
										player.sendMessage("§f§l[明若觀火] 불을 꿰뚫어 본다면 더이상 불이 두렵지 않으리니...");
										PottionBuff.givePotionEffect(player,PotionEffectType.FIRE_RESISTANCE, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										break;
									case 4:
										SoundEffect.SP(player,Sound.ENTITY_PLAYER_LEVELUP, 1.5F, 0.8F);
										player.sendMessage("§f§l[鼓腹擊壤] 몸도 마음도 모두 풍요로우니 이 어찌 기쁘지 아니한가...");
										PottionBuff.givePotionEffect(player,PotionEffectType.HEAL, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										PottionBuff.givePotionEffect(player,PotionEffectType.SATURATION, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										PottionBuff.givePotionEffect(player,PotionEffectType.REGENERATION, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										PottionBuff.givePotionEffect(player,PotionEffectType.HEALTH_BOOST, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										break;
									case 5:
										SoundEffect.SP(player,Sound.ENTITY_MINECART_INSIDE, 1.0F, 1.0F);
										player.sendMessage("§f§l[東奔西走] 내 원래 이리 저리 돌아다니길 좋아하니, 역마가 낀들 어떠하리...");
										PottionBuff.givePotionEffect(player,PotionEffectType.SPEED, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										PottionBuff.givePotionEffect(player,PotionEffectType.JUMP, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										break;
									case 6:
										SoundEffect.SP(player,Sound.BLOCK_ANVIL_LAND, 1.0F, 1.8F);
										player.sendMessage("§f§l[單刀直入] 흔들리지 않는 신념은 적의 살을 베어내고, 철근같은 "+MainServerOption.statWILL+"는 적의 뼈를 바스러 뜨리리...");
										PottionBuff.givePotionEffect(player,PotionEffectType.INCREASE_DAMAGE, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										break;
									case 7:
										SoundEffect.SP(player,Sound.ENTITY_ENDERDRAGON_GROWL, 1.0F, 1.0F);
										player.sendMessage("§f§l[康衢煙月] 내 눈은 밝은 달빛이요, 내 발길 닿는 곳이 길이니...");
										PottionBuff.givePotionEffect(player,PotionEffectType.NIGHT_VISION, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										break;
									case 8:
										SoundEffect.SP(player,Sound.BLOCK_WATER_AMBIENT, 1.5F, 1.0F);
										player.sendMessage("§f§l[明鏡止水] 거울만치 물이 맑으니 누가 들어가기를 마다하리오...");
										PottionBuff.givePotionEffect(player,PotionEffectType.WATER_BREATHING, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
										break;
									}
								}
								else
								{
									switch(n.RandomNum(1, 8))
									{
									case 1:
										SoundEffect.SP(player,Sound.ENTITY_BLAZE_AMBIENT, 1.0F, 1.0F);
										player.sendMessage("§c§l[螳螂拒轍] 거만이 몸에 베어 적장을 한낱 개미로 바라보리니...");
										PottionBuff.givePotionEffect(player,PotionEffectType.WEAKNESS, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
										break;
									case 2:
										SoundEffect.SP(player,Sound.AMBIENT_CAVE, 1.0F, 1.0F);
										player.sendMessage("§c§l[群盲撫象] 한치 앞도 보이지 않거늘...");
										PottionBuff.givePotionEffect(player,PotionEffectType.BLINDNESS, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
										break;
									case 3:
										SoundEffect.SP(player,Sound.ENTITY_ENDERDRAGON_GROWL, 0.8F,0.5F);
										player.sendMessage("§c§l[竿頭之勢] 절벽 끝에 선 자의 느낌이란...");
										PottionBuff.givePotionEffect(player,PotionEffectType.CONFUSION, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
										break;
									case 4:
										SoundEffect.SP(player,Sound.ENTITY_ZOMBIE_DEATH, 0.8F,0.5F);
										player.sendMessage("§c§l[簞食豆羹] 내 모습이 마치 아귀와 같이 앙상하니...");
										PottionBuff.givePotionEffect(player,PotionEffectType.HUNGER, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
										break;
									case 5:
										SoundEffect.SP(player,Sound.ENTITY_ZOMBIE_HORSE_DEATH, 0.8F,0.5F);
										player.sendMessage("§c§l[累卵之勢] 흐트러진 기가 몸 전체를 감싸 돌아 안으니 위태롭기가 짝이 없도다...");
										PottionBuff.givePotionEffect(player,PotionEffectType.POISON, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
										break;
									case 6:
										SoundEffect.SP(player,Sound.ENTITY_ITEM_BREAK, 0.8F,0.5F);
										player.sendMessage("§c§l[曠日彌久] 쓸데없는 잡상을 하여도, 시간은 기다려 주지 않을지니...");
										PottionBuff.givePotionEffect(player,PotionEffectType.SLOW_DIGGING, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
										break;
									case 7:
										SoundEffect.SP(player,Sound.ENTITY_SPIDER_AMBIENT, 0.8F,0.5F);
										player.sendMessage("§c§l[姑息之計] 오늘 걸으니 내일은 뛰어야 할지니...");
										PottionBuff.givePotionEffect(player,PotionEffectType.SLOW, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
										break;
									case 8:
										SoundEffect.SP(player,Sound.ENTITY_WITHER_HURT, 0.8F,0.5F);
										player.sendMessage("§c§l[骨肉相爭] 뼈와 살이 서로 곪아가니 악취가 나는구나...");
										PottionBuff.givePotionEffect(player,PotionEffectType.WITHER, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
										break;
									}
								}
						  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * NPCscript.getInt("Job.Deal"), 0, false);
							}
							NPCscript.set("Job.Type","Shaman");
							NPCscript.set("Job.GoodRate",50);
							NPCscript.set("Job.BuffMaxStrog",2);
							NPCscript.set("Job.BuffTime",60);
							NPCscript.set("Job.Deal",500);
						}
						else if(Case.equals("힐러"))
						{
							Damageable getouter = (Damageable)player;
							int a = (int)getouter.getHealth();
							if(player.getHealthScale()== a&&(player.hasPotionEffect(PotionEffectType.BLINDNESS)||
									player.hasPotionEffect(PotionEffectType.CONFUSION)||player.hasPotionEffect(PotionEffectType.HARM)||
									player.hasPotionEffect(PotionEffectType.HUNGER)||player.hasPotionEffect(PotionEffectType.POISON)||
									player.hasPotionEffect(PotionEffectType.SLOW)||player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)||
									player.hasPotionEffect(PotionEffectType.WEAKNESS)||player.hasPotionEffect(PotionEffectType.WITHER))==false)
							{
								SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage("§3[SYSTEM] : 당신은 치료받을 필요가 없습니다!");
								return;
							}
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() < NPCscript.getInt("Job.Deal"))
							{
								SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage("§c[SYSTEM] : 치료 비용이 부족합니다!");
							}
							else
							{
								SoundEffect.SP(player,Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
								Damageable p = player;
								p.setHealth(p.getMaxHealth());
								player.removePotionEffect(PotionEffectType.BLINDNESS);
								player.removePotionEffect(PotionEffectType.CONFUSION);
								player.removePotionEffect(PotionEffectType.HARM);
								player.removePotionEffect(PotionEffectType.HUNGER);
								player.removePotionEffect(PotionEffectType.POISON);
								player.removePotionEffect(PotionEffectType.SLOW);
								player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
								player.removePotionEffect(PotionEffectType.WEAKNESS);
								player.removePotionEffect(PotionEffectType.WITHER);
						  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * NPCscript.getInt("Job.Deal"), 0, false);
								player.sendMessage("§3[SYSTEM] : 당신은 깨끗이 치료되었습니다!");
							}
						}
						else if(Case.equals("전직 교관"))
						{
							YamlLoader JobList = new YamlLoader();
							JobList.getConfig("Skill/JobList.yml");
							YamlLoader PlayerJob = new YamlLoader();
							Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
							for(int count = 0; count < Job.length; count++)
							{
								Object[] q = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
								for(int counter=0;counter<q.length;counter++)
								{
									if(q[counter].toString().equals(NPCscript.getString("Job.Job")))
									{
										PlayerJob.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
										int NeedLV = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedLV");
										int NeedSTR = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedSTR");
										int NeedDEX = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedDEX");
										int NeedINT = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedINT");
										int NeedWILL = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedWILL");
										int NeedLUK = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedLUK");
										String PrevJob = JobList.getString("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".PrevJob");
										
										if((main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()>=NeedLV)&&
										(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()>=NeedSTR)&&
										(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()>=NeedDEX)&&
										(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()>=NeedINT)&&
										(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()>=NeedWILL)&&
										(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()>=NeedLUK))
										{
											if(!PrevJob.equals("null"))
											{
												if(!PlayerJob.getString("Job.Type").equals(PrevJob))
												{
													player.sendMessage("§c[전직] : 당신의 직업으로는 전직 할 수 없는 대상입니다.");
													SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
													return;	
												}
											}
											if(!NPCscript.getString("Job.Job").equals(PlayerJob.getString("Job.Type")))
											{
												//플레이어 전직함
												PlayerJob.set("Job.Root", Job[count].toString());
												PlayerJob.set("Job.Type", NPCscript.getString("Job.Job"));
												PlayerJob.createSection("MapleStory."+NPCscript.getString("Job.Job")+".Skill");
												PlayerJob.saveConfig();
												MainServerOption.PlayerList.get(player.getUniqueId().toString()).setPlayerRootJob(Job[count].toString());
												job.JobMain J = new job.JobMain();
												J.FixPlayerJobList(player);
												player.closeInventory();
												Bukkit.broadcastMessage("§a§l[§e§l"+player.getName()+"§a§l님께서 §e§l"+NPCscript.getString("Job.Job")+"§a§l 승급에 성공 하셨습니다!]");
											}
											else
											{
												player.sendMessage("§c[전직] : 현재 직업으로 전직 할 수 없습니다!");
												SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
											}
										}
										else
										{
											player.sendMessage("§c[전직] : 당신의 스텟은 전직 요건에 맞지 않습니다.");
											SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
										}
									}
								}
							}
						}
					}
					else if(event.getClick().isRightClick()&& player.isOp())
					{
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						NPCjobGUI(player,NPCname);
					}
				}
				else//직업 설정
					NPCjobGUI(player,NPCname);
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(slot == 7 && player.isOp())//세일 설정
				{
					UserDataObject u = new UserDataObject();
					YamlLoader NPCConfig = new YamlLoader();
					NPCConfig.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
					if(event.isShiftClick()&&event.isRightClick())
					{
						NPCConfig.set("Sale.Enable", false);
						NPCConfig.saveConfig();
						SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						MainGUI(player, NPCname, player.isOp());
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						u.setType(player, "NPC");
						u.setString(player, (byte)2,NPCname);
						u.setString(player, (byte)3,u.getNPCuuid(player));
						u.setString(player, (byte)4,"SaleSetting1");
						player.sendMessage("§3[NPC] : 세일을 시작 할 최소 호감도를 입력 해 주세요! (-1000 ~ 1000 사이 값)");
						player.closeInventory();
					}
				}
				else if(slot==10)//대화를 한다
					TalkGUI(player,NPCname,null,(char)-1);
				else if(slot==12)//거래를 한다
					ShopGUI(player,NPCname,(short) 0,true,false);
				else if(slot == 14)//퀘스트
					QuestListGUI(player, (short) 0);
				else if(slot == 16)//선물하기
					PresentGiveGUI(player, NPCname, false, -1);
				else if(player.isOp())
				{
					if(slot == 19)//대화 수정
						NPCTalkGUI(player, (short) 0, NPCname,"NT");
					else if(slot == 21)//거래 수정
						ShopGUI(player,NPCname,(short) 0,true,true);
					else if(slot == 23)//퀘스트 수정
						QuestAddGUI(player, (short) 0);
					else if(slot == 25)//선물 수정
						PresentSettingGUI(player, NPCname);
				}
			}	
		}
		return;
	}

	public void TalkGUIClick(InventoryClickEvent event, String NPCname)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		if(slot > 0 && slot < 8)
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			TalkGUI(player,NPCname, new npc.NpcMain().getScript(player,(char)-1),(char)slot);
		}
		else
		{
			if(slot == 0)
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				MainGUI(player,NPCname,player.isOp());
			}
			else
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
		}
	}
	
	public void ShopGUIClick(InventoryClickEvent event, String NPCname)
	{
		
		Player player = (Player) event.getWhoClicked();
		if(event.getClickedInventory().getType() == InventoryType.PLAYER)
			return;
		switch(event.getSlot())
		{
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 17:
			case 18:
			case 26:
			case 27:
			case 35:
			case 36:
			case 37:
			case 38:
			case 39:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
				return;
			case 45:
				MainGUI(player,NPCname,player.isOp());
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				break;
			case 48:
			{
				int showingPage = Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1).split("페이지 : ")[1]));
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(event.getInventory().getItem(0).getItemMeta().hasLore() == false)
				{
					if(event.getInventory().getItem(0).getData().getData() ==(byte)14)
						ShopGUI(player, NPCname, (short) (showingPage-1) , false,false);
					else
						ShopGUI(player, NPCname, (short) (showingPage-1), true,false);
				}
				else
				{
					if(event.getInventory().getItem(0).getData().getData() ==(byte)14)
						ShopGUI(player, NPCname, (short) (showingPage-1) , false,true);
					else
						ShopGUI(player, NPCname, (short) (showingPage-1), true,true);
				}
				break;
			}
			case 49:
				SoundEffect.SP(player, Sound.BLOCK_CHEST_OPEN, 0.8F, 1.0F);
				if(event.getInventory().getItem(0).getItemMeta().hasLore() == false)
				{
					if(event.getCurrentItem().getData().getData() ==(byte)14)
						ShopGUI(player, NPCname, (short) 0, false,false);
					else
						ShopGUI(player, NPCname, (short) 0, true,false);
				}
				else
				{
					if(event.getCurrentItem().getData().getData() ==(byte)14)
						ShopGUI(player, NPCname, (short) 0, false,true);
					else
						ShopGUI(player, NPCname, (short) 0, true,true);
				}
				break;
			case 50:
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				int showingPage2 = Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1).split("페이지 : ")[1]));
				if(event.getInventory().getItem(0).getItemMeta().hasLore() == false)
				{
					if(event.getInventory().getItem(0).getData().getData() ==(byte)14)
						ShopGUI(player, NPCname, (short) (showingPage2-1), false,false);
					else
						ShopGUI(player, NPCname, (short) (showingPage2-1), true,false);
				}
				else
				{
					if(event.getInventory().getItem(0).getData().getData() ==(byte)14)
						ShopGUI(player, NPCname, (short) (showingPage2-1), false,true);
					else
						ShopGUI(player, NPCname, (short) (showingPage2-1), true,true);
				}
				break;
			case 53:
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			default:
			{
				ItemStack item = event.getCurrentItem();
				boolean isBuy = true;
				if(event.getInventory().getItem(0).getData().getData() == (byte)14)
					isBuy=false;
				if(event.getInventory().getItem(0).getItemMeta().hasLore())
				{
					if(event.getClick().isRightClick() == true)
					{
						UserDataObject u = new UserDataObject();
						String Type=null;
						if(isBuy == true)
							Type = "Sell";
						else
							Type = "Buy";
						
						byte num = Byte.parseByte(ChatColor.stripColor(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1)));
						YamlLoader NPCscript = new YamlLoader();
						NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");

						Set<String> a = NPCscript.getConfigurationSection("Shop."+Type).getKeys(false);
						
						for(int count = 0; count < a.size(); count++)
						{
							if(count == num)
							{
								for(int counter =count; counter < a.size()-1; counter++)
								{
									NPCscript.set("Shop."+Type+"."+counter + ".item", NPCscript.getItemStack("Shop."+Type+"."+(counter+1) + ".item"));
									NPCscript.set("Shop."+Type+"."+counter + ".price", NPCscript.getLong("Shop."+Type+"."+(counter+1) + ".price"));
								}
								NPCscript.removeKey("Shop."+Type+"."+(a.size()-1) + ".item");
								NPCscript.removeKey("Shop."+Type+"."+(a.size()-1) + ".price");
								NPCscript.removeKey("Shop."+Type+"."+(a.size()-1));
								NPCscript.saveConfig();
							}
						}
					}
					else
					{
						if(item.getItemMeta().getLore().size() < 4)
						{
							ItemMeta Icon_Meta = item.getItemMeta();
							Icon_Meta.setLore(null);
							item.setItemMeta(Icon_Meta);
							player.getInventory().addItem(item);
						}
						else
						{
							ItemMeta Icon_Meta = item.getItemMeta();
							String[] l = new String[item.getItemMeta().getLore().size()-3];
							for(int count =0;count <l.length;count++)
								l[count] = (item.getItemMeta().getLore().get(count));
							Icon_Meta.setLore(Arrays.asList(l));
							item.setItemMeta(Icon_Meta);
							player.getInventory().addItem(item);
						}
					}
					SoundEffect.SP(player, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
					int showingPage3 = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(0)));
					if(isBuy == true)
						ShopGUI(player, NPCname, (short) showingPage3, true,true);
					else
						ShopGUI(player, NPCname, (short) showingPage3, false,true);
				}
				else if(item.hasItemMeta())
				{
					int value = Integer.parseInt(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-2).split(" ")[2]);
					if(item.getItemMeta().getLore().size() < 4)
					{
						ItemMeta Icon_Meta = item.getItemMeta();
						Icon_Meta.setLore(null);
						item.setItemMeta(Icon_Meta);
					}
					else
					{
						ItemMeta Icon_Meta = item.getItemMeta();
						String[] l = new String[item.getItemMeta().getLore().size()-3];
						for(int count =0;count <l.length;count++)
							l[count] = (item.getItemMeta().getLore().get(count));
						Icon_Meta.setLore(Arrays.asList(l));
						item.setItemMeta(Icon_Meta);
					}
					SoundEffect.SP(player, Sound.BLOCK_IRON_TRAPDOOR_OPEN, 0.8F, 0.5F);
					if(event.getInventory().getItem(0).getData().getData() == (byte)14)
						ItemBuy(player, item, value, NPCname, isBuy, 0);
					else
						ItemBuy(player, item, value, NPCname, isBuy, 0);
				}
			}
		}
	}
	
	public void NPCjobGUIClick(InventoryClickEvent event, String NPCname)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
			UserDataObject u = new UserDataObject();
			YamlLoader NPCscript = new YamlLoader();
			NPCscript.getConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
			if(slot == 18)
				MainGUI(player,NPCname,player.isOp());
			else
			{
				NPCscript.removeKey("Job");
				if(slot == 1)//직업 없음
				{
					NPCscript.set("Job.Type","null");
					NPCscript.saveConfig();
					MainGUI(player,NPCname,player.isOp());
				}
				else if(slot == 6)//공간 이동술사
				{
					NPCscript.set("Job.Type","Warper");
					NPCscript.createSection("Job.WarpList");
					NPCscript.saveConfig();
					WarpMainGUI(player, 0,NPCname);
				}
				else if(slot == 7)//개조 장인
				{
					NPCscript.set("Job.Type","Upgrader");
					NPCscript.createSection("Job.UpgradeRecipe");
					NPCscript.saveConfig();
					MainGUI(player, NPCname, player.isOp());
				}
				else
				{
					player.closeInventory();
					u.setType(player, "NPC");
					u.setString(player, (byte)2,NPCname);
					u.setString(player, (byte)3,u.getNPCuuid(player));
					if(slot == 2)//대장장이
					{
						NPCscript.set("Job.Type","BlackSmith");
						NPCscript.set("Job.FixRate",50);
						NPCscript.set("Job.10PointFixDeal",500);
						NPCscript.saveConfig();
						u.setString(player, (byte)4,"FixRate");
						player.sendMessage("§3[NPC] : 이 NPC의 수리 성공률을 입력 해 주세요! (1~100 사이 값)");
					}
					else if(slot == 3)//주술사
					{
						NPCscript.set("Job.Type","Shaman");
						NPCscript.set("Job.GoodRate",50);
						NPCscript.set("Job.BuffMaxStrog",2);
						NPCscript.set("Job.BuffTime",60);
						NPCscript.set("Job.Deal",500);
						NPCscript.saveConfig();
						u.setString(player, (byte)4,"GoodRate");
						player.sendMessage("§3[NPC] : 이 NPC의 버프 성공률을 입력 해 주세요! (1~100 사이 값)");
					}
					else if(slot == 4)//힐러
					{
						NPCscript.set("Job.Type","Healer");
						NPCscript.set("Job.Deal",500);
						NPCscript.saveConfig();
						u.setString(player, (byte)4,"Deal");
						player.sendMessage("§3[NPC] : 이 NPC의 치료 비용을 입력 해 주세요!");
					}
					else if(slot == 5)//전직 교관
					{
						YamlLoader Config = new YamlLoader();
						Config.getConfig("config.yml");
						if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
						{
							YamlLoader JobList = new YamlLoader();
							JobList.getConfig("Skill/JobList.yml");
							Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
							if(Job.length == 1)
							{
								u.clearAll(player);
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage("§c[NPC] : 전직 가능한 직업이 없습니다! §e/오피박스§c 명령어를 사용하여 직업군을 만드십시요!");
								return;
							}
							u.setString(player, (byte)4,"NPCJL");

							player.sendMessage("§d[NPC] : 이 NPC는 어떤 직업으로 전직 시켜 주는 교관인가요?");
							for(int count = 0; count < Job.length; count++)
							{
								Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
								for(int counter=0;counter<a.length;counter++)
								{
									if(a[counter].toString().equalsIgnoreCase(Config.getString("Server.DefaultJob"))==false)
										player.sendMessage("§d "+Job[count].toString()+" ━ §e"+ a[counter].toString());
								}
							}
						}
						else
						{
							u.clearAll(player);
							SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage("§c[NPC] : 직업 기능을 사용하시려면§e /오피박스§c 에서 게임 시스템을 '메이플 스토리'로 변경해 주시길 바랍니다.");
						}
					}
					else if(slot == 10)//룬 세공사
					{
						NPCscript.set("Job.Type","Rune");
						NPCscript.set("Job.SuccessRate",50);
						NPCscript.set("Job.Deal",5000);
						NPCscript.saveConfig();
						u.setString(player, (byte)4,"SuccessRate");
						player.sendMessage("§3[NPC] : 이 NPC의 룬 장착 성공률을 입력 해 주세요! (1~100 사이 값)");
					}
				}
			}
		}
	}
	
	public void WarpMainGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		

		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			if(slot == 45)//이전 목록으로
				MainGUI(player, NPCname, player.isOp());
			else if(slot == 48)//이전 페이지
				WarpMainGUI(player,page-1,NPCname);
			else if(slot == 50)//다음 페이지
				WarpMainGUI(player,page+1,NPCname);
			else if(slot == 49)//새 워프
				WarperGUI(player, (short) 0, NPCname);
			else
			{
				if(event.isLeftClick()==true&&event.isShiftClick()==false)
				{
					util.ETC ETC = new util.ETC();
					if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 >= ETC.getSec())
					{
						player.sendMessage("§c[이동 불가] : §e"+((main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 - ETC.getSec())/1000)+"§c 초 후에 이동 가능합니다!");
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						return;
					}
					UserDataObject u = new UserDataObject();
					YamlLoader NPCConfig = new YamlLoader();
					NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
					if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >= NPCConfig.getInt("Job.WarpList."+((page*45)+event.getSlot())+".Cost"))
					{
				  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * NPCConfig.getInt("Job.WarpList."+((page*45)+event.getSlot())+".Cost"), 0, false);
						String AreaName = NPCConfig.getString("Job.WarpList."+((page*45)+event.getSlot())+".Area");
						YamlLoader AreaConfig = new YamlLoader();
						AreaConfig.getConfig("Area/AreaList.yml");
						SoundEffect.SL(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
						player.teleport(new Location(Bukkit.getWorld(AreaConfig.getString(AreaName+".World")), AreaConfig.getInt(AreaName+".SpawnLocation.X"), AreaConfig.getInt(AreaName+".SpawnLocation.Y"), AreaConfig.getInt(AreaName+".SpawnLocation.Z")));
						SoundEffect.SL(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[워프] : 텔레포트 비용이 부족합니다!");
					}
				}
				else if(event.isRightClick()==true&&event.isShiftClick()==true&&player.isOp()==true)
				{
					UserDataObject u = new UserDataObject();
					YamlLoader NPCConfig = new YamlLoader();
					NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
					int number = ((page*45)+event.getSlot());
					int Acount =  NPCConfig.getConfigurationSection("Job.WarpList").getKeys(false).toArray().length-1;

					for(int counter = number;counter <Acount;counter++)
						NPCConfig.set("Job.WarpList."+counter, NPCConfig.get("Job.WarpList."+(counter+1)));
					NPCConfig.removeKey("Job.WarpList."+Acount);
					NPCConfig.saveConfig();
					WarpMainGUI(player, page, NPCname);
				}
			}
		}
	}

	public void WarperGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			if(slot == 45)//이전 목록으로
				WarpMainGUI(player, 0, NPCname);
			else if(slot == 48)//이전 페이지
				WarperGUI(player, (short) (page-1), NPCname);
			else if(slot == 50)//다음 페이지
				WarperGUI(player, (short) (page+1), NPCname);
			else
			{
				UserDataObject u = new UserDataObject();
				YamlLoader NPCConfig = new YamlLoader();
				NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				int number = NPCConfig.getConfigurationSection("Job.WarpList").getKeys(false).size();
				NPCConfig.set("Job.WarpList."+number+".Area",ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				NPCConfig.set("Job.WarpList."+number+".DisplayName","워프지점");
				NPCConfig.set("Job.WarpList."+number+".Cost",1000);
				NPCConfig.saveConfig();
				u.setType(player, "NPC");
				u.setString(player, (byte)4,"WDN");
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)2,NPCname);
				u.setInt(player, (byte)4, number);
				player.sendMessage("§3[NPC] : 해당 워프 지점의 이름을 설정해 주세요!");
				player.closeInventory();
			}
		}
	}

	public void UpgraderGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		
		if(slot == 53)
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

			if(slot == 45)//이전 목록으로
				MainGUI(player, NPCname,player.isOp());
			else if(slot == 48)//이전 페이지
				UpgraderGUI(player, (short) (page-1), NPCname);
			else if(slot == 49)//새 개조식
			{
				if(player.isOp())
					SelectUpgradeRecipeGUI(player, (short) 0,NPCname);
			}
			else if(slot == 50)//다음 페이지
				UpgraderGUI(player, (short) (page+1), NPCname);
			else
			{
				UserDataObject u = new UserDataObject();
				YamlLoader NPCConfig = new YamlLoader();
				NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");

				if(event.isLeftClick() == true && event.isShiftClick()==false)
				{
					if(player.getInventory().getItemInMainHand() != null)
					{
						if(player.getInventory().getItemInMainHand().hasItemMeta() == true)
						{
							if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()==true)
							{
								List<String> Lore = event.getCurrentItem().getItemMeta().getLore();
								int Cost = 0;
								int NeedProficiency = 100;
								int UpgradeLevel = 0;
								for(int counter = 0; counter < Lore.size();counter++)
								{
									if(Lore.get(counter).contains("비용") == true)
									{
										if(Lore.get(counter).contains(" : ") == true)
										{
											String RawString = ChatColor.stripColor(Lore.get(counter)).split(" : ")[1];
											Cost = Integer.parseInt(RawString.split(" ")[0]);
										}
									}
									else if(Lore.get(counter).contains("숙련도") == true)
									{
										if(Lore.get(counter).contains(" : ") == true)
											NeedProficiency = Integer.parseInt(ChatColor.stripColor(Lore.get(counter)).split(" : ")[1]);
									}
									else if(Lore.get(counter).contains("횟수") == true)
									{
										if(Lore.get(counter).contains(" : ") == true)
										{
											String RawString = ChatColor.stripColor(Lore.get(counter)).split(" : ")[1];
											UpgradeLevel = Integer.parseInt(RawString.split(" ")[0]);
										}
									}
								}
								float playerProficiency=0.0F;
								int playerNowUpgradeLevel = -1;
								int playerMaxUpgradeLevel = -1;
								String PlayerWeaponType = "null";

								Lore = player.getInventory().getItemInMainHand().getItemMeta().getLore();
								for(int counter = 0; counter < Lore.size();counter++)
								{
									if(Lore.get(counter).length()!=0)
									{
										if(Lore.get(counter).contains("숙련도") == true)
										{
											if(Lore.get(counter).contains(" : ") == true)
											{
												String RawString = ChatColor.stripColor(Lore.get(counter)).split(" : ")[1];
												playerProficiency = Float.parseFloat(RawString.split("%")[0]);
											}
										}
										else if(Lore.get(counter).contains("개조") == true)
										{
											if(Lore.get(counter).contains(" : ") == true)
											{
												playerNowUpgradeLevel = Integer.parseInt(ChatColor.stripColor(Lore.get(counter)).split(" : ")[1].split(" / ")[0]);
												playerMaxUpgradeLevel =  Integer.parseInt(ChatColor.stripColor(Lore.get(counter)).split(" : ")[1].split(" / ")[1]);
											}
										}
										else if(ChatColor.stripColor(Lore.get(counter)).charAt(0)=='['&&Lore.get(counter).contains("]") == true)
										{
											PlayerWeaponType = "";
											if(Lore.get(counter).contains("[근접") == true)
												PlayerWeaponType="[근접 무기]";
											else if(Lore.get(counter).contains("[한손") == true)
												PlayerWeaponType="[한손 검]";
											else if(Lore.get(counter).contains("[양손") == true)
												PlayerWeaponType="[양손 검]";
											else if(Lore.get(counter).contains("[도끼]") == true)
												PlayerWeaponType="[도끼]";
											else if(Lore.get(counter).contains("[낫]") == true)
												PlayerWeaponType="[낫]";
											else if(Lore.get(counter).contains("[원거리") == true)
												PlayerWeaponType="[원거리 무기]";
											else if(Lore.get(counter).contains("[활]") == true)
												PlayerWeaponType="[활]";
											else if(Lore.get(counter).contains("[석궁]") == true)
												PlayerWeaponType="[석궁]";
											else if(Lore.get(counter).contains("[마법") == true)
												PlayerWeaponType="[마법 무기]";
											else if(Lore.get(counter).contains("[원드]") == true)
												PlayerWeaponType="[원드]";
											else if(Lore.get(counter).contains("[스태프]") == true)
												PlayerWeaponType="[스태프]";
											else if(Lore.get(counter).contains("[방어구]") == true)
												PlayerWeaponType="[방어구]";
											else if(Lore.get(counter).contains("[기타]") == true)
												PlayerWeaponType="[기타]";
											else
											{
												String LoreLine = ChatColor.stripColor(Lore.get(counter));
												for(int count = 0; count < LoreLine.length(); count++)
												{
													if(LoreLine.charAt(count)!=']')
														PlayerWeaponType = PlayerWeaponType+LoreLine.charAt(count);
													else
														break;
												}
												PlayerWeaponType = PlayerWeaponType+"]";
											}
										}	
									}
								}
						  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * NPCConfig.getInt("Job.WarpList."+((page*45)+event.getSlot())+".Cost"), 0, false);
								if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >=  Cost)
								{
									String RecipeName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
									YamlLoader UpgradeRecipe = new YamlLoader();
									UpgradeRecipe.getConfig("Item/Upgrade.yml");
									String Type = ChatColor.stripColor(UpgradeRecipe.getString(RecipeName+".Only"));
									if(Type.equals(PlayerWeaponType)||
											((Type.equals("[근접 무기]")&&PlayerWeaponType.equals("[한손 검]"))||(Type.equals("[근접 무기]")&&PlayerWeaponType.equals("[양손 검]"))||
											(Type.equals("[근접 무기]")&&PlayerWeaponType.equals("[도끼]"))||(Type.equals("[원거리 무기]")&&PlayerWeaponType.equals("[활]"))||
											(Type.equals("[원거리 무기]")&&PlayerWeaponType.equals("[석궁]"))||(Type.equals("[마법 무기]")&&PlayerWeaponType.equals("[원드]"))||
											(Type.equals("[마법 무기]")&&PlayerWeaponType.equals("[스태프]"))))
									{
										if(playerMaxUpgradeLevel!=-1&&playerNowUpgradeLevel!=-1&&UpgradeLevel==playerNowUpgradeLevel)
										{
											if(!(playerMaxUpgradeLevel==playerNowUpgradeLevel))
											{
												if(playerProficiency >= NeedProficiency)
												{
											  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * Cost, 0, false);
													ItemStack item = player.getInventory().getItemInMainHand();
													if(UpgradeRecipe.getInt(RecipeName+".MaxDurability")!=0)
														item = Calculator(item, "Durability", UpgradeRecipe.getInt(RecipeName+".MaxDurability"));
													if(UpgradeRecipe.getInt(RecipeName+".MinDamage")!=0)
														item = Calculator(item, "MinDamage", UpgradeRecipe.getInt(RecipeName+".MinDamage"));
													if(UpgradeRecipe.getInt(RecipeName+".MaxDamage")!=0)
														item = Calculator(item, "MaxDamage", UpgradeRecipe.getInt(RecipeName+".MaxDamage"));
													if(UpgradeRecipe.getInt(RecipeName+".MinMaDamage")!=0)
														item = Calculator(item, "MinMaDamage", UpgradeRecipe.getInt(RecipeName+".MinMaDamage"));
													if(UpgradeRecipe.getInt(RecipeName+".MaxMaDamage")!=0)
														item = Calculator(item, "MaxMaDamage", UpgradeRecipe.getInt(RecipeName+".MaxMaDamage"));
													if(UpgradeRecipe.getInt(RecipeName+".DEF")!=0)
														item = Calculator(item, "DEF", UpgradeRecipe.getInt(RecipeName+".DEF"));
													if(UpgradeRecipe.getInt(RecipeName+".Protect")!=0)
														item = Calculator(item, "Protect", UpgradeRecipe.getInt(RecipeName+".Protect"));
													if(UpgradeRecipe.getInt(RecipeName+".MaDEF")!=0)
														item = Calculator(item, "MaDEF", UpgradeRecipe.getInt(RecipeName+".MaDEF"));
													if(UpgradeRecipe.getInt(RecipeName+".MaProtect")!=0)
														item = Calculator(item, "MaProtect", UpgradeRecipe.getInt(RecipeName+".MaProtect"));
													if(UpgradeRecipe.getInt(RecipeName+".Critical")!=0)
														item = Calculator(item, "Critical", UpgradeRecipe.getInt(RecipeName+".Critical"));
													if(UpgradeRecipe.getInt(RecipeName+".Balance")!=0)
														item = Calculator(item, "Balance", UpgradeRecipe.getInt(RecipeName+".Balance"));
													if(UpgradeRecipe.getInt(RecipeName+".DecreaseProficiency")!=0)
														item = Calculator(item, "Proficiency", UpgradeRecipe.getInt(RecipeName+".DecreaseProficiency"));
													item = Calculator(item, "UpgradeLevelIncrease", 1);
													//개조 구문
													
													player.getInventory().setItemInMainHand(item);
													player.closeInventory();
													SoundEffect.SP(player, Sound.BLOCK_ANVIL_USE, 1.0F, 0.8F);
													player.sendMessage("§3[개조] : 개조가 완료되었습니다!");
												}
												else
												{
													SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
													player.sendMessage("§c[개조] : 숙련도가 부족합니다!");
												}
											}
											else
											{
												SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
												player.sendMessage("§c[개조] : 더이상 개조할 수 없습니다!");
											}
										}
										else
										{
											SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
											player.sendMessage("§c[개조] : 개조 레벨이 맞지 않습니다!");
										}
									}
									else
									{
										SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
										player.sendMessage("§c[개조] : 개조 가능한 무기 타입이 아닙니다!");
									}
								}
								else
								{
									SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
									player.sendMessage("§c[개조] : 개조 비용이 부족합니다!");
								}
							}
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage("§c[개조] : 현재 손에 들고 있는 아이템은  개조가 불가능 합니다!");
							}
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage("§c[개조] : 현재 손에 들고 있는 아이템은  개조가 불가능 합니다!");
						}
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[개조] : 아이템을 손에 장착하고 있어야 합니다!");
					}
				}
				else if(event.isRightClick()==true && event.isShiftClick()==true&&player.isOp()==true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.2F, 1.0F);
					NPCConfig.removeKey("Job.UpgradeRecipe."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					NPCConfig.saveConfig();
					UpgraderGUI(player, page, NPCname);
				}
			}
		}
	}

	public void SelectUpgradeRecipeGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
			if(slot == 45)//이전 목록
				UpgraderGUI(player, (short) 0, NPCname);
			else if(slot == 48)//이전 페이지
				SelectUpgradeRecipeGUI(player, (short) (page-1),NPCname);
			else if(slot == 50)//다음 페이지
				SelectUpgradeRecipeGUI(player, (short) (page+1),NPCname);
			else
			{
				UserDataObject u = new UserDataObject();
				YamlLoader NPCConfig = new YamlLoader();
				NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				String RecipeName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				NPCConfig.set("Job.UpgradeRecipe."+RecipeName, 2147483647);
				player.sendMessage("[NPC] : 선택한 개조식의 개조 비용을 입력 해 주세요!");
				player.sendMessage("§a(§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
				u.setType(player, "NPC");
				u.setString(player, (byte)4,"NUC");
				u.setString(player, (byte)6,RecipeName);
				u.setString(player, (byte)8,NPCname);
				player.closeInventory();
			}
		}
	}
	
	public ItemStack Calculator(ItemStack item, String WeaponOption, int PlusOption)
	{
		String Option = "null";
		switch(WeaponOption)
		{
		case "Durability":
			Option = "내구도";
			break;
		case "MinDamage":
			Option = "소댐";
			break;
		case "MaxDamage":
			Option = "맥댐";
			break;
		case "MinMaDamage":
			Option = "마소댐";
			break;
		case "MaxMaDamage":
			Option = "마맥댐";
			break;
		case "DEF":
			Option = "방어";
			break;
		case "Protect":
			Option = "보호";
			break;
		case "MaDEF":
			Option = "마법 방어";
			break;
		case "MaProtect":
			Option = "마법 보호";
			break;
		case "Critical":
			Option = "크리티컬";
			break;
		case "Balance":
			Option = "밸런스";
			break;
		case "Proficiency":
			Option = "숙련도";
			break;
		case "UpgradeLevelIncrease":
			Option = "개조";
			break;
		case "NowDura":
			Option = "현재내구도";
			break;
		case "HP":
			Option = "생명력";
			break;
		case "MP":
			Option = "마나";
			break;
		case "STR":
			Option = ""+MainServerOption.statSTR+"";
			break;
		case "DEX":
			Option = ""+MainServerOption.statDEX+"";
			break;
		case "INT":
			Option = ""+MainServerOption.statINT+"";
			break;
		case "WILL":
			Option = ""+MainServerOption.statWILL+"";
			break;
		case "LUK":
			Option = ""+MainServerOption.statLUK+"";
			break;
		}
		ItemMeta IM = item.getItemMeta();
		List<String> Lore = item.getItemMeta().getLore();
		for(int count = 0; count < Lore.size(); count++)
		{
			String SliceOfLore = Lore.get(count);
			if(SliceOfLore.contains(Option) == true||(SliceOfLore.contains(MainServerOption.damage)&&!(SliceOfLore.contains("마법"))&&(Option.equals("소댐")))||
					(SliceOfLore.contains(MainServerOption.damage)&&!(SliceOfLore.contains("마법"))&&(Option.equals("맥댐")))||(SliceOfLore.contains(MainServerOption.magicDamage)&&(Option.equals("마소댐")))||
					(SliceOfLore.contains(MainServerOption.magicDamage)&&(Option.equals("마맥댐")))
					||(Option.equals("현재내구도")&&SliceOfLore.contains("내구도")))
			{
				String WillBeLore=" ";
				if(SliceOfLore.contains(" : ") == true)
				{
					switch(Option)
					{
					case "현재내구도":
						if(SliceOfLore.contains("내구도")&&!SliceOfLore.contains(MainServerOption.damage))
						{
							int low = Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[0]));
							int max = Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1]));
							if((low+PlusOption)  <= max)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+(low+ PlusOption) + " / "+ max;
							else
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+max + " / "+ max;
						}
						break;
					case "내구도":
						if(SliceOfLore.contains("내구도")&&!SliceOfLore.contains(MainServerOption.damage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[0])) <= (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])) + PlusOption))
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[0])) + " / "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])) + PlusOption);
							else if((Integer.parseInt(SliceOfLore.split(" : ")[1].split(" / ")[1]) + PlusOption) != 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])) + PlusOption) + " / "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])) + PlusOption);
							else
								WillBeLore = SliceOfLore.split(" : ")[0] + "§f : 0 / 0";
						}
						break;
					case "소댐":
						if(SliceOfLore.contains(MainServerOption.damage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + PlusOption > 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0]))+ PlusOption)+ " ~ "+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1]));
							else
								WillBeLore = SliceOfLore.split(" : ")[0] +"§f : 0 ~ "+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1]));
						}
						break;
					case "마소댐":
						if(SliceOfLore.contains(MainServerOption.magicDamage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + PlusOption > 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0]))+ PlusOption)+ " ~ "+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1]));
							else
								WillBeLore = SliceOfLore.split(" : ")[0] +"§f : 0 ~ "+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1]));
						}
						break;
					case "맥댐":
						if(SliceOfLore.contains(MainServerOption.damage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1])) + PlusOption > 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + " ~ "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1])) + PlusOption);
							else
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + " ~ 0";
						}
						break;
					case "마맥댐":
						if(SliceOfLore.contains(MainServerOption.magicDamage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1])) + PlusOption > 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + " ~ "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1])) + PlusOption);
							else
								WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + " ~ 0";
						}
						break;
					case "숙련도":
						DecimalFormat format = new DecimalFormat(".##");
						String str = "0";
						if((Float.parseFloat(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split("%")[0]))-PlusOption) > 0)
							str = format.format((Float.parseFloat(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split("%")[0]))-PlusOption));
						else
							str = "0.0";
						WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+str +"%§f";
						break;
					case "개조":
						WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[0]))+ 1) + " / "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])));
						break;
					default:
						if(!SliceOfLore.contains("내구도"))
							WillBeLore = SliceOfLore.split(" : ")[0] + " : §f"+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1])) + PlusOption);
						break;
					}
					Lore.set(count, WillBeLore);
					IM.setLore(Lore);
					item.setItemMeta(IM);
					return item;
				}
			}
		}
		switch(Option)
		{
		case "소댐":
			Lore.add(2, "§f "+MainServerOption.damage+" : " + PlusOption + " ~ 0");
			break;
		case "마소댐":
			Lore.add(3, "§f "+MainServerOption.magicDamage+" : " + PlusOption + " ~ 0");
			break;
		case "맥댐":
			Lore.add(2, "§f "+MainServerOption.damage+" : 0 ~ "+PlusOption);
			break;
		case "마맥댐":
			Lore.add(3, "§f "+MainServerOption.magicDamage+" : 0 ~ "+PlusOption);
			break;
		default:
			Lore.add(4, " §f"+ Option + " : " + PlusOption);
			break;
		}
		IM.setLore(Lore);
		item.setItemMeta(IM);
		return item;
	}
	
	public void RuneEquipGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		

		if(!event.getClickedInventory().getTitle().equals("container.inventory"))
		{
			if(slot != 13)
				event.setCancelled(true);
			if(slot == 10)//이전 목록으로
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
				String NPCname = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(0));
				MainGUI(player, NPCname, player.isOp());
			}
			else if(slot == 16)//룬 장착
			{
				UserDataObject u = new UserDataObject();
				YamlLoader NPCConfig = new YamlLoader();
				NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				int SuccessRate = NPCConfig.getInt("Job.SuccessRate");
				boolean Success = false;
				if(event.getInventory().getItem(13)!=null)
				{
					if(event.getInventory().getItem(13).hasItemMeta()==true)
					{
						if(event.getInventory().getItem(13).getItemMeta().hasLore()==true)
						{
							if(event.getInventory().getItem(13).getItemMeta().getLore().toString().contains("[룬]"))
							{
								if(player.getInventory().getItemInMainHand()!=null)
								{
									if(player.getInventory().getItemInMainHand().hasItemMeta()==true)
									{
										if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()==true)
										{
											int Pay = NPCConfig.getInt("Job.Deal");
											if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >= Pay)
											{
												ItemStack item = player.getInventory().getItemInMainHand();
												int MinDamage = 0;
												int MaxDamage = 0;
												int MinMaDamage = 0;
												int MaxMaDamage = 0;
												int DEF = 0;
												int Protect = 0;
												int MaDEF = 0;
												int MaProtect = 0;
												int Balance = 0;
												int Critical = 0;
												int NowDura = 0;
												int MaxDura = 0;
												int HP = 0;
												int MP = 0;
												int STR = 0;
												int DEX = 0;
												int INT = 0;
												int WILL = 0;
												int LUK = 0;
												for(int count = 0; count < event.getInventory().getItem(13).getItemMeta().getLore().size();count++)
												{
													if(event.getInventory().getItem(13).getItemMeta().getLore().get(count).contains(" : "))
													{
														String Rune = event.getInventory().getItem(13).getItemMeta().getLore().get(count);
														if(Rune.contains("공격력"))
														{
															if(Rune.contains("마법"))
															{
																if(Rune.contains("최소"))
																	MinMaDamage = Integer.parseInt(Rune.split(" : ")[1]);
																else
																	MaxMaDamage = Integer.parseInt(Rune.split(" : ")[1]);
															}
															else
															{
																if(Rune.contains("최소"))
																	MinDamage = Integer.parseInt(Rune.split(" : ")[1]);
																else
																	MaxDamage = Integer.parseInt(Rune.split(" : ")[1]);
															}
														}
														else if(Rune.contains("방어"))
														{
															if(Rune.contains("마법"))
																MaDEF = Integer.parseInt(Rune.split(" : ")[1]);
															else
																DEF = Integer.parseInt(Rune.split(" : ")[1]);
														}
														else if(Rune.contains("보호"))
														{
															if(Rune.contains("마법"))
																MaProtect = Integer.parseInt(Rune.split(" : ")[1]);
															else
																Protect = Integer.parseInt(Rune.split(" : ")[1]);
														}
														else if(Rune.contains("내구도"))
														{
															if(Rune.contains("현재"))
																NowDura = Integer.parseInt(Rune.split(" : ")[1]);
															else
																MaxDura = Integer.parseInt(Rune.split(" : ")[1]);
														}
														else if(Rune.contains("밸런스"))
															Balance = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains("크리티컬"))
															Critical = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains("생명력"))
															HP = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains("마나"))
															MP = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+MainServerOption.statSTR+""))
															STR = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+MainServerOption.statDEX+""))
															DEX = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+MainServerOption.statINT+""))
															INT = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+MainServerOption.statWILL+""))
															WILL = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+MainServerOption.statLUK+""))
															LUK = Integer.parseInt(Rune.split(" : ")[1]);
													}
												}
												
												for(int count = 0; count < item.getItemMeta().getLore().size();count++)
												{
													if(item.getItemMeta().getLore().get(count).contains("룬") == true)
													{
														if(item.getItemMeta().getLore().get(count).contains(" : ") == true)
														{
															if(item.getItemMeta().getLore().get(count).contains("○") == true)
															{
																String Lore = item.getItemMeta().getLore().get(count).split(" : ")[1];
																int EmptyCircle = 0;
																String Circle = "";
																for(int k = 0; k < Lore.split(" ").length;k++)
																{
																	if(Lore.split(" ")[k].contains("○")==true)
																	{
																		EmptyCircle = k;
																		break;
																	}
																}
																UtilNumber n = new UtilNumber();
																if(n.RandomNum(1, 100)<= SuccessRate)
																{
																	for(int k = 0; k < Lore.split(" ").length;k++)
																	{
																		if(k != EmptyCircle)
																			Circle = Circle+Lore.split(" ")[k]+" ";
																		else
																			Circle = Circle+"§9● ";
																	}
																	Lore =  item.getItemMeta().getLore().get(count).split(" : ")[0] + " : " + Circle;
																	player.sendMessage("§9[룬 세공] : 아이템에 룬을 장착하였습니다!");
																	SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
																	Success = true;
																}
																else
																{
																	for(int k = 0; k < Lore.split(" ").length;k++)
																	{
																		if(k != EmptyCircle)
																			Circle = Circle+Lore.split(" ")[k]+" ";
																		else
																			Circle = Circle+"§c× ";
																	}
																	Lore =  item.getItemMeta().getLore().get(count).split(" : ")[0] + " : " + Circle;
																	player.sendMessage("§c[룬 세공] : 룬 세공 실패!!!");
																	player.sendMessage("§c[아이템의 룬 슬롯이 파괴되었습니다!]");
																	SoundEffect.SP(player, Sound.BLOCK_ANVIL_BREAK, 1.0F, 1.1F);
																}
																if(event.getInventory().getItem(13).getAmount()==1)
																	event.getInventory().setItem(13, new ItemStack(0));
																else
																	event.getInventory().getItem(13).setAmount(event.getInventory().getItem(13).getAmount()-1);
														  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * Pay, 0, false);

																ItemStack ii = item;
																ItemMeta IM = item.getItemMeta();
																String LL = "";
																for(int k = 0; k < item.getItemMeta().getLore().size();k++)
																{
																	if(item.getItemMeta().getLore().get(k).contains("룬") == true&&item.getItemMeta().getLore().get(k).contains(" : ") == true)
																	{
																		if(k < item.getItemMeta().getLore().size()-1)
																			LL = LL+Lore+"%enter%";
																		else
																			LL = LL+Lore;
																	}
																	else
																		LL = LL+item.getItemMeta().getLore().get(k) + "%enter%";
																}
																String[] scriptA = LL.split("%enter%");
																IM.setLore(Arrays.asList(scriptA));
																ii.setItemMeta(IM);
																player.getInventory().setItemInMainHand(ii);

																if(Success == true)
																{
																	ii = player.getInventory().getItemInMainHand();
																	if(MaxDura!=0)
																		ii = Calculator(ii, "Durability", MaxDura);
																	if(MinDamage!=0)
																		ii = Calculator(ii, "MinDamage", MinDamage);
																	if(MaxDamage!=0)
																		ii = Calculator(ii, "MaxDamage", MaxDamage);
																	if(MinMaDamage!=0)
																		ii = Calculator(ii, "MinMaDamage", MinMaDamage);
																	if(MaxMaDamage!=0)
																		ii = Calculator(ii, "MaxMaDamage", MaxMaDamage);
																	if(DEF!=0)
																		ii = Calculator(ii, "DEF", DEF);
																	if(Protect!=0)
																		ii = Calculator(ii, "Protect", Protect);
																	if(MaDEF!=0)
																		ii = Calculator(ii, "MaDEF", MaDEF);
																	if(MaProtect!=0)
																		ii = Calculator(ii, "MaProtect", MaProtect);
																	if(Critical!=0)
																		ii = Calculator(ii, "Critical", Critical);
																	if(Balance!=0)
																		ii = Calculator(ii, "Balance", Balance);
																	if(HP!=0)
																		ii = Calculator(ii, "HP", HP);
																	if(MP!=0)
																		ii = Calculator(ii, "MP", MP);
																	if(STR!=0)
																		ii = Calculator(ii, "STR", STR);
																	if(DEX!=0)
																		ii = Calculator(ii, "DEX", DEX);
																	if(INT!=0)
																		ii = Calculator(ii, "INT", INT);
																	if(WILL!=0)
																		ii = Calculator(ii, "WILL", WILL);
																	if(LUK!=0)
																		ii = Calculator(ii, "LUK", LUK);
																	if(NowDura!=0)
																		ii = Calculator(ii, "NowDura", NowDura);
																}
																return;
															}
														}
													}
												}
												player.sendMessage("§c[룬 세공] : 룬을 장착 시킬 수 있는 여유 슬롯이 없습니다!");
												SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
											}
											else
											{
												player.sendMessage("§c[룬 세공] : 소지금이 부족합니다!");
												SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
											}
										}
										else
										{
											player.sendMessage("§c[룬 세공] : 룬을 장착시킬 수 없는 아이템 입니다!");
											SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
										}
										
									}
									else
									{
										player.sendMessage("§c[룬 세공] : 룬을 장착시킬 수 없는 아이템 입니다!");
										SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
									}
								}
								else
								{
									player.sendMessage("§c[룬 세공] : 손에 아이템을 장착하고 있어야 합니다!");
									SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								}
							}
							else
							{
								player.sendMessage("§c[룬 세공] : 재료로 올린 아이템은 룬이 아닙니다!");
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							}
						}
						else
						{
							player.sendMessage("§c[룬 세공] : 재료로 올린 아이템은 룬이 아닙니다!");
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						}
					}
					else
					{
						player.sendMessage("§c[룬 세공] : 재료로 올린 아이템은 룬이 아닙니다!");
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					}
				}
				else
				{
					player.sendMessage("§c[룬 세공] : 장착 시킬 룬을 올려 주세요!");
					SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				}
			}
		}
	}

	public void TalkGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		
		if(slot == 53)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.8F);
			player.closeInventory();
		}
		else
		{
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			String TalkType = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(slot == 45)//이전 목록
				MainGUI(player, NPCname, player.isOp());
			else if(slot == 46)//대화 타입 변경
			{
				if(TalkType.equals("NT"))
					NPCTalkGUI(player, (short) 0, NPCname, "NN");
				else if(TalkType.equals("NN"))
					NPCTalkGUI(player, (short) 0, NPCname, "AS");
				else
					NPCTalkGUI(player, (short) 0, NPCname, "NT");
			}
			else if(slot == 48)//이전 목록
				NPCTalkGUI(player, (short) (page-1), NPCname, TalkType);
			else if(slot == 49)//대사 추가
			{
				short number = 1;
				UserDataObject u = new UserDataObject();
				YamlLoader NPCConfig = new YamlLoader();
				NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				if(TalkType.equals("NT"))
				{
					number = (short) (NPCConfig.getConfigurationSection("NatureTalk").getKeys(false).toArray().length+1);
					NPCConfig.set("NatureTalk."+number+".love", 0);
					NPCConfig.set("NatureTalk."+number+".Script", "§f대사 없음");
				}
				else if(TalkType.equals("NN"))
				{
					number = (short) (NPCConfig.getConfigurationSection("NearByNEWS").getKeys(false).toArray().length+1);
					NPCConfig.set("NearByNEWS."+number+".love", 0);
					NPCConfig.set("NearByNEWS."+number+".Script", "§f대사 없음");
				}
				else
				{
					number = (short) (NPCConfig.getConfigurationSection("AboutSkills").getKeys(false).toArray().length+1);
					NPCConfig.set("AboutSkills."+number+".love", 0);
					NPCConfig.set("AboutSkills."+number+".giveSkill", "null");
					NPCConfig.set("AboutSkills."+number+".Script", "§f대사 없음");
					NPCConfig.set("AboutSkills."+number+".AlreadyGetScript", "대사 없음");
				}
				NPCConfig.saveConfig();
				NPCTalkGUI(player, page, NPCname, TalkType);
			}
			else if(slot == 50)//다음 목록
				NPCTalkGUI(player, (short) (page+1), NPCname, TalkType);
			else if(slot == 52)//타입 변경
			{
				if(TalkType.equals("NT"))
					NPCTalkGUI(player, (short) 0, NPCname, "AS");
				else if(TalkType.equals("NN"))
					NPCTalkGUI(player, (short) 0, NPCname, "NT");
				else
					NPCTalkGUI(player, (short) 0, NPCname, "NN");
			}
			else
			{
				UserDataObject u = new UserDataObject();
				YamlLoader NPCConfig = new YamlLoader();
				NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				short Number = Short.parseShort(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				if(event.isRightClick()&&event.isShiftClick())
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
					Short Acount = 0;
					switch(TalkType)
					{
					case "NT"://NatureTalk
						Acount =  (short) NPCConfig.getConfigurationSection("NatureTalk").getKeys(false).toArray().length;
						for(int counter = Number;counter <Acount;counter++)
						{
							NPCConfig.set("NatureTalk."+counter+".love", NPCConfig.getInt("NatureTalk."+(counter+1)+".love"));
							NPCConfig.set("NatureTalk."+counter+".Script", NPCConfig.getString("NatureTalk."+(counter+1)+".Script"));
						}
						NPCConfig.removeKey("NatureTalk."+Acount);
						NPCConfig.saveConfig();
						NPCTalkGUI(player, page, NPCname, TalkType);
						break;
					case "NN"://NearbyNews
						Acount =  (short) NPCConfig.getConfigurationSection("NearByNEWS").getKeys(false).toArray().length;
						for(int counter = Number;counter <Acount;counter++)
						{
							NPCConfig.set("NearByNEWS."+counter+".love", NPCConfig.getInt("NearByNEWS."+(counter+1)+".love"));
							NPCConfig.set("NearByNEWS."+counter+".Script", NPCConfig.getString("NearByNEWS."+(counter+1)+".Script"));
						}
						NPCConfig.removeKey("NearByNEWS."+Acount);
						NPCConfig.saveConfig();
						NPCTalkGUI(player, page, NPCname, TalkType);
						break;
					case "AS"://AboutSkill
						Acount =  (short) NPCConfig.getConfigurationSection("AboutSkills").getKeys(false).toArray().length;
						for(int counter = Number;counter <Acount;counter++)
						{
							NPCConfig.set("AboutSkills."+counter+".love", NPCConfig.getInt("AboutSkills."+(counter+1)+".love"));
							NPCConfig.set("AboutSkills."+counter+".giveSkill", NPCConfig.getInt("AboutSkills."+(counter+1)+".giveSkill"));
							NPCConfig.set("AboutSkills."+counter+".Script", NPCConfig.getString("AboutSkills."+(counter+1)+".Script"));
							NPCConfig.set("AboutSkills."+counter+".AlreadyGetScript", NPCConfig.getString("AboutSkills."+(counter+1)+".AlreadyGetScript"));
						}
						NPCConfig.removeKey("AboutSkills."+Acount);
						NPCConfig.saveConfig();
						NPCTalkGUI(player, page, NPCname, TalkType);
						break;
					}
				}
				else
					TalkSettingGUI(player, NPCname, TalkType, Number);
			}
		}
	}
	
	public void TalkSettingGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 35)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String TalkType = ChatColor.stripColor(event.getInventory().getItem(27).getItemMeta().getLore().get(1));
			String NPCname = ChatColor.stripColor(event.getInventory().getItem(35).getItemMeta().getLore().get(1));
			short number =  Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getDisplayName()));
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			
			if(slot == 27)
				NPCTalkGUI(player,(short) (number/45), NPCname, TalkType);
			else if(slot == 15)//스킬
			{
				YamlLoader Config = new YamlLoader();
				Config.getConfig("config.yml");
				if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
				{
					YamlLoader SkillList = new YamlLoader();
					SkillList.getConfig("Skill/JobList.yml");
					if(SkillList.getConfigurationSection("Mabinogi").getKeys(false).toArray().length >= 0)
					{
						if(SkillList.getConfigurationSection("Mabinogi.Added").getKeys(false).toArray().length >= 0)
						{
							AddAbleSkillsGUI(player, (short) 0, NPCname,number);
							return;
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
							player.sendMessage("§c[SYSTEM] : 등록 가능한 스킬이 없습니다!");
						}
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
						player.sendMessage("§c[SYSTEM] : 등록 가능한 스킬이 없습니다!");
					}
				}
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage("§c[SYSTEM] : 서버 시스템이 마비노기 형식이 아닙니다!");
				}
			}
			else if(slot==13||slot==14||slot==16)
			{
				player.closeInventory();
				UserDataObject u = new UserDataObject();
				u.setType(player, "NPC");
				u.setString(player, (byte)2,NPCname);
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)5,TalkType);
				u.setString(player, (byte)6,number+"");
				if(slot == 13)//호감도
				{
					player.sendMessage("§3[대사] : 이 대사를 보기 위해서는 최소 몇의 호감도가 필요한가요?");
					player.sendMessage("§a(§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
					u.setString(player, (byte)4,"NPC_TNL");
				}
				else
				{

					if(slot == 14)//대사1
					{
						player.sendMessage("§3[대사] : NPC가 할 대사를 입력해 주세요!");
						u.setString(player, (byte)4,"NPC_TS");
					}
					else if(slot == 16)//대사2
					{
						player.sendMessage("§3[대사] : 플레이어에게 스킬을 전수한 뒤, NPC가 할 대사를 입력해 주세요!");
						u.setString(player, (byte)4,"NPC_TS2");
					}
					player.sendMessage("§6%enter%§f - 한줄 띄워 쓰기 -");
					player.sendMessage("§6%player%§f - 플레이어 지칭하기 -");
					player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
					"§3&3 §4&4 §5&5 " +
							"§6&6 §7&7 §8&8 " +
					"§9&9 §a&a §b&b §c&c " +
							"§d&d §e&e §f&f");
				}
			}
		}
	}
	
	public void AddAbleSkillsGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			String TalkType = "AS";
			short number =  Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			
			if(slot == 45)//이전 메뉴
				TalkSettingGUI(player, NPCname, TalkType, number);
			else if(slot == 48)//이전 페이지
				AddAbleSkillsGUI(player, (short) (page-1), NPCname, number);
			else if(slot == 50)//다음 페이지
				AddAbleSkillsGUI(player, (short) (page+1), NPCname, number);
			else
			{
				UserDataObject u = new UserDataObject();
				YamlLoader NPCConfig = new YamlLoader();
				NPCConfig.getConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				NPCConfig.set("AboutSkills."+number+".giveSkill", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				NPCConfig.saveConfig();
				TalkSettingGUI(player, NPCname, TalkType,number);
			}
		}
	}

	public void ItemBuyGuiClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			boolean isBuy = false;
			isBuy = event.getInventory().getName().contains("구매");
			String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				ShopGUI(player, NPCname, (short)0, isBuy, false);
			else
			{
				ItemStack item = event.getInventory().getItem(22);
				int value = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(0)));
				int count = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(1).getItemMeta().getLore().get(0)));

				if(isBuy)
				{
					if(slot == 49 && count != 0)
					{
						if(MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >= value*count)
						{
							for(int counter = 0; counter < count; counter++)
							{
								if(new util.UtilPlayer().giveItem(player, item)==false)
								{
									SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.8F);
									player.sendMessage("§c[구매 실패] : 인벤토리가 부족하여 "+(count-counter)+"개를 구매하지 못하였습니다!");
									MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Money(MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() - (counter*value));
									return;
								}
							}
							SoundEffect.SP(player, Sound.ENTITY_SHULKER_OPEN, 0.8F, 1.0F);
							MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Money(MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() - (count*value));
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.8F);
							player.sendMessage("§c[구매 실패] : "+MainServerOption.money+"§c가 부족하여 구매할 수 없습니다!");
						}
					}
					else if(slot == 31)
					{
						SoundEffect.SP(player, Sound.ITEM_ARMOR_EQUIP_DIAMOND, 0.8F, 0.5F);
						count = (int) (MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() / value);
					}
					else if(slot == 13)
					{
						SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.6F, 1.8F);
						count = 1;
					}
					else if(slot>=19&&slot<=21)
					{
						if(slot==19)
						{
							SoundEffect.SP(player, Sound.ENTITY_SHULKER_CLOSE, 0.8F, 1.6F);
							if(count-64 > 0)
								count -= 64;
							else
								count = 1;
						}
						else if(slot==20)
						{
							SoundEffect.SP(player, Sound.ENTITY_ITEMFRAME_REMOVE_ITEM, 0.8F, 1.4F);
							if(count-10 > 0)
								count -= 10;
							else
								count = 1;
						}
						else if(slot==21)
						{
							SoundEffect.SP(player, Sound.ENTITY_ITEMFRAME_ROTATE_ITEM, 0.8F, 1.4F);
							if(count-1 > 0)
								count -= 1;
						}
					}
					else if(slot >= 23 && slot <= 25)
					{
						int TempCount = count;
						if(slot == 23)
						{
							SoundEffect.SP(player, Sound.ITEM_ARMOR_EQUIP_IRON, 0.8F, 1.8F);
							TempCount += 1;
						}
						else if(slot == 24)
						{
							SoundEffect.SP(player, Sound.ITEM_ARMOR_EQUIP_CHAIN, 0.8F, 1.2F);
							TempCount += 10;
						}
						else if(slot == 25)
						{
							SoundEffect.SP(player, Sound.ITEM_ARMOR_EQUIP_GOLD, 0.8F, 0.5F);
							TempCount += 64;
						}
						if(value * TempCount <= MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
							count = TempCount;
						else if(slot != 23)
							count = (int) (MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() / value);
					}
					ItemBuy(player, item, value, NPCname, isBuy, count);
				}
				else
				{
					int ItemHave = new util.UtilPlayer().getItemAmount(player, item);

					if(slot == 49 && count != 0)
					{
						if(new util.UtilPlayer().deleteItem(player, item, count)==false)
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.8F);
							player.sendMessage("§c[판매 실패] : 물품이 부족하여 판매하지 못하였습니다!");
							return;
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.0F);
							MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(value*count, 0, false);
						}
					}
					
					if(slot == 31)
					{
						SoundEffect.SP(player, Sound.ITEM_ARMOR_EQUIP_DIAMOND, 0.8F, 0.5F);
						count = ItemHave;
					}
					else if(slot == 13)
					{
						SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.6F, 1.8F);
						if(ItemHave > 0)
							count = 1;
						else
							count = 0;
					}
					else if(slot>=19&&slot<=21)
					{
						if(slot==19)
						{
							SoundEffect.SP(player, Sound.ENTITY_SHULKER_CLOSE, 0.8F, 1.6F);
							if(count-64 > 0)
								count -= 64;
							else
								count = 1;
						}
						else if(slot==20)
						{
							SoundEffect.SP(player, Sound.ENTITY_ITEMFRAME_REMOVE_ITEM, 0.8F, 1.4F);
							if(count-10 > 0)
								count -= 10;
							else
								count = 1;
						}
						else if(slot==21)
						{
							SoundEffect.SP(player, Sound.ENTITY_ITEMFRAME_ROTATE_ITEM, 0.8F, 1.4F);
							if(count-1 > 0)
								count -= 1;
						}
					}
					else if(slot >= 23 && slot <= 25)
					{
						int TempCount = count;
						if(slot == 23)
						{
							SoundEffect.SP(player, Sound.ITEM_ARMOR_EQUIP_IRON, 0.8F, 1.8F);
							TempCount += 1;
						}
						else if(slot == 24)
						{
							SoundEffect.SP(player, Sound.ITEM_ARMOR_EQUIP_CHAIN, 0.8F, 1.2F);
							TempCount += 10;
						}
						else if(slot == 25)
						{
							SoundEffect.SP(player, Sound.ITEM_ARMOR_EQUIP_GOLD, 0.8F, 0.5F);
							TempCount += 64;
						}
						if(TempCount <= ItemHave)
							count = TempCount;
						else
							count = ItemHave;
					}
					ItemBuy(player, item, value, NPCname, isBuy, count);
				}
			}
		}
	}

	public void ItemFixGuiClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		if(event.getClickedInventory().getTitle().equals("container.inventory"))
		{
			ItemStack clickedItem = event.getCurrentItem();
			short ItemID = (short) clickedItem.getTypeId();
			boolean HasCustomDurability = false;
			if(clickedItem.hasItemMeta()==true)
			{
				if(clickedItem.getItemMeta().hasLore()==true)
				{
					for(int count = 0; count < clickedItem.getItemMeta().getLore().size(); count++)
					{
						if(clickedItem.getItemMeta().getLore().get(count).contains("내구도") == true)
						{
							HasCustomDurability= true;
							break;
						}
					}
				}
			}
			if((ItemID>=256&&ItemID<=259)||(ItemID==261)||(ItemID>=267&&ItemID<=279)||(ItemID>=283&&ItemID<=286)||(ItemID>=290&&ItemID<=294)
					||(ItemID>=298&&ItemID<=317)||ItemID==346||ItemID==359||ItemID==398||ItemID==442||ItemID==443||HasCustomDurability==true)
			{
			  	long playerMoney = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
			  	long FixPrice = Long.parseLong(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(2)));
			  	byte FixRate = (byte)Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1)));
				if(HasCustomDurability == false)
				{
					short nowDurability = (short) (clickedItem.getDurability());
					
					if(nowDurability == 0)
					{
						SoundEffect.SP(player,Sound.BLOCK_ANVIL_LAND, 0.8F, 1.6F);
						player.sendMessage("§3[수리] : 해당 무기는 수리받을 필요가 없습니다.");
						return;
					}
					
					int point10need = (nowDurability/10);
					int point1need = (nowDurability%10);

					int point10success = 0;
					int point1success = 0;
					
					if(playerMoney < point10need*FixPrice ||playerMoney < ((point1success*FixPrice)/10))
					{
						SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[SYSTEM] : 수리 비용이 부족합니다!");
						return;
					}
					
					for(int count = 0; count < point10need;count++)
					{
						if(new Random().nextInt(101) <= FixRate)
							point10success = (point10success+1);
					}
					for(int count = 0; count < point1need;count++)
					{
						if(new Random().nextInt(101) <= FixRate)
							point1success = (point1success+1);
					}
					if(point10success==0 && point1success==0)
					{
						player.sendMessage("§c[수리] : 완전 수리 실패!");
						SoundEffect.SP(player,Sound.BLOCK_ANVIL_BREAK, 1.2F, 1.0F);
						return;
					}
					SoundEffect.SP(player,Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
					if(point10success == point10need && point1success ==point1need)
						player.sendMessage("§3[수리] : 수리 대성공!");
					if(point10success != point10need || point1success !=point1need)
						player.sendMessage("§e[수리] : §f"+((point10success*10)+point1success)+"§e 포인트 수리 성공, §f"+((point10need-(point10success))*10+(point1need-point1success))+"§e 포인트 수리 실패 ");

					clickedItem.setDurability((short) (clickedItem.getDurability()-((point10success*10)+point1success)));
			  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * ((point10need*FixPrice)+((point1need*FixPrice)/10)), 0, false);
				}
				else//커스텀 내구도 있을 경우
				{
					int Maxdurability = 0;
					int nowDurability =0;
					for(int count = 0; count < clickedItem.getItemMeta().getLore().size();count++)
					{
						if(clickedItem.getItemMeta().getLore().get(count).contains("내구도") == true)
						{
							Maxdurability = Integer.parseInt(ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(count)).split(" : ")[1].split(" / ")[1]);
							nowDurability = Integer.parseInt(ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(count)).split(" : ")[1].split(" / ")[0]);
							break;
						}
					}
					
					if(nowDurability == Maxdurability)
					{
						SoundEffect.SP(player,Sound.BLOCK_ANVIL_LAND, 0.8F, 1.6F);
						player.sendMessage("§3[수리] : 해당 무기는 수리받을 필요가 없습니다.");
						return;
					}
					
					int point10need = (Maxdurability-nowDurability)/10;
					int point1need = (Maxdurability-nowDurability)%10;

					int point10success = 0;
					int point1success = 0;
					
					if(playerMoney < point10need*FixPrice ||playerMoney < ((point1success*FixPrice)/10))
					{
						SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[SYSTEM] : 수리 비용이 부족합니다!");
						return;
					}
					
					for(int count = 0; count < point10need;count++)
					{
						if(new Random().nextInt(101) <= FixRate)
							point10success = (point10success+1);
					}
					for(int count = 0; count < point1need;count++)
					{
						if(new Random().nextInt(101) <= FixRate)
							point1success = (point1success+1);
					}
					if(point10success==0 && point1success==0)
					{
						player.sendMessage("§c[수리] : 완전 수리 실패!");
						SoundEffect.SP(player,Sound.BLOCK_ANVIL_BREAK, 1.2F, 1.0F);
					}
					SoundEffect.SP(player,Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
					if(point10success == point10need && point1success ==point1need)
						player.sendMessage("§3[수리] : 수리 대성공!");
					if((point10success != point10need || point1success !=point1need)&&(point10success!=0 || point1success!=0))
						player.sendMessage("§e[수리] : §f"+((point10success*10)+point1success)+"§e 포인트 수리 성공, §f"+((point10need-(point10success))*10+(point1need-point1success))+"§e 포인트 수리 실패 ");

					if(clickedItem.hasItemMeta() == true)
					{
						if(clickedItem.getItemMeta().hasLore() == true)
						{
							for(int count = 0; count < clickedItem.getItemMeta().getLore().size(); count++)
							{
								ItemMeta Meta = clickedItem.getItemMeta();
								if(Meta.getLore().get(count).contains("내구도") == true)
								{
									String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
									String[] SubLore = Lore[1].split(" / ");
									List<String> PLore = Meta.getLore();
									PLore.set(count,"§f"+ Lore[0] + " : "+(Integer.parseInt(SubLore[0])+((point10success*10)+point1success))+" / "+(Integer.parseInt(SubLore[1])-(((point10need-point10success)*10)+(point1need-point1success))));
									Meta.setLore(PLore);
									clickedItem.setItemMeta(Meta);
								}
							}
						}
					}
			  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * ((point10need*FixPrice)+((point1need*FixPrice)/10)), 0, false);
				}
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8F, 1.0F);
				player.sendMessage("§c[SYSTEM] : 수리 할 수 없는 물건입니다!");
			}
		}
		else
		{
			int slot = event.getSlot();
			if(slot == 8)
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(slot == 0)
				{
					String NPCname = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1));
					MainGUI(player, NPCname, player.isOp());
				}
			}
		}
	}
	
	public void PresentGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		
		
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1)); 
		if(ChatColor.stripColor(event.getInventory().getName()).equals("[NPC] 선물 아이템을 올려 주세요"))
		{
			int number = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(1).getItemMeta().getLore().get(0)));
			boolean isSettingMode = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1)));

			if(event.getSlot()==4)
			{
				event.setCancelled(false);
				return;
			}
			String DisplayName = null;
			if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName())
			{
				DisplayName = event.getCurrentItem().getItemMeta().getDisplayName();
				byte size = (byte) DisplayName.length();
				if(size >= 6 && DisplayName.charAt(size-1)=='c'&&DisplayName.charAt(size-2)=='§'&&DisplayName.charAt(size-3)=='e'&&DisplayName.charAt(size-4)=='§'&& DisplayName.charAt(size-5)=='0'&&DisplayName.charAt(size-6)=='§')
				{
					event.setCancelled(true);
					YamlLoader NPCConfig = new YamlLoader();
					NPCConfig.getConfig("NPC/NPCData/"+new UserDataObject().getNPCuuid(player)+".yml");
					if(event.getSlot()==0)//선물 설정
					{
						if(isSettingMode)
						{
							ItemStack item = NPCConfig.getItemStack("Present."+number+".item");
							if(event.getInventory().getItem(4) != null)
								NPCConfig.set("Present."+number+".item", event.getInventory().getItem(4));
							else
							{
								NPCConfig.set("Present."+number+".item", null);
								NPCConfig.saveConfig();
								SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
								PresentSettingGUI(player, NPCname);
								return;
							}
							NPCConfig.set("Present."+number+".love", 0);
							NPCConfig.saveConfig();
							SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
							UserDataObject u = new UserDataObject();
							u.setType(player, "NPC");
							u.setString(player, (byte)2,NPCname);
							u.setString(player, (byte)3,u.getNPCuuid(player));
							u.setString(player, (byte)4,"PresentLove");
							u.setInt(player, (byte)0, number);
							player.sendMessage("§3[NPC] : 해당 아이템을 줄 때 상승하는 호감도 수치를 입력 해 주세요! (-1000 ~ 1000 사이 값)");
							player.closeInventory();
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
							MainGUI(player, NPCname, player.isOp());
						}
					}
					else if(event.getSlot() == 8)//닫닫(선물 주기)
					{
						if(isSettingMode)
						{
							SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
							player.closeInventory();
							return;
						}
						YamlLoader PlayerNPC = new YamlLoader();
				    	if(PlayerNPC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
				    	{
							UserDataObject u = new UserDataObject();
				    		PlayerNPC.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
				    		PlayerNPC.set(u.getNPCuuid(player)+".love", 0);
				    		PlayerNPC.set(u.getNPCuuid(player)+".Career", 0);
				    		PlayerNPC.saveConfig();
				    	}
				    	else
				    		PlayerNPC.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
						if(event.getInventory().getItem(4) != null)
						{
					    	ItemStack Present = new ItemStack(event.getInventory().getItem(4));
					    	int Amount = Present.getAmount();
					    	Present.setAmount(1);

							UserDataObject u = new UserDataObject();
							for(int count = 2; count < 8; count++)
							{
								if(NPCConfig.getItemStack("Present."+count+".item") != null)
								{
									ItemStack LoveItem = new ItemStack(NPCConfig.getItemStack("Present."+count+".item"));
									int LoveItemAmount = LoveItem.getAmount();
									LoveItem.setAmount(1);
									if(LoveItem.equals(Present))
									{
										event.getInventory().setItem(4, null);
										int LoveValue = NPCConfig.getInt("Present."+count+".love");
										int love = 0;
										if(LoveValue >= 0)
										{
											if(Amount / LoveItemAmount == 0)
												love = 1;
											else
												love = LoveValue * (Amount/LoveItemAmount);
										}
										else
											love = LoveValue;
										if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love >= 1000)
											PlayerNPC.set(u.getNPCuuid(player)+".love", 1000);
										else if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love <= -1000)
											PlayerNPC.set(u.getNPCuuid(player)+".love", -1000);
										else
											PlayerNPC.set(u.getNPCuuid(player)+".love", PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love);
										PlayerNPC.saveConfig();
										if(love >= 0)
										{
											SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
											player.sendMessage("§a[SYSTEM] : §e"+NPCname+"§a의 호감도가 §e"+love+"§a 상승하였습니다!");
										}
										else
										{
											SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.8F);
											player.sendMessage("§c[SYSTEM] : §e"+NPCname+"§c의 호감도가 §e"+(love*-1)+"§c 하락 하였습니다!");
										}
										return;
									}
								}
							}
							if(NPCConfig.getInt("Present.1.love")!=0)
							{
								event.getInventory().setItem(4, null);
								int LoveValue = NPCConfig.getInt("Present.1.love");
								int love = 0;
								if(LoveValue >= 0)
									love = LoveValue * Amount;
								else
									love = LoveValue;
								if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love >= 1000)
									PlayerNPC.set(u.getNPCuuid(player)+".love", 1000);
								else if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love <= -1000)
									PlayerNPC.set(u.getNPCuuid(player)+".love", -1000);
								else
									PlayerNPC.set(u.getNPCuuid(player)+".love", PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love);
								PlayerNPC.saveConfig();
								if(love >= 0)
								{
									SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
									player.sendMessage("§a[SYSTEM] : §e"+NPCname+"§a의 호감도가 §e"+love+"§a 상승하였습니다!");
								}
								else
								{
									SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.8F);
									player.sendMessage("§c[SYSTEM] : §e"+NPCname+"§c의 호감도가 §e"+(love*-1)+"§c 하락 하였습니다!");
								}
								return;
							}
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_ITEMFRAME_REMOVE_ITEM, 1.0F, 1.8F);
								player.sendMessage("§e[SYSTEM] : §6"+NPCname+"§e는 선물을 사양하였다.");
							}
						}
						else
							SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					return;
				}
			}
		}
		else//[NPC] 선물 가능 아이템 목록
		{
			if(event.getSlot()==0)
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				MainGUI(player, NPCname, player.isOp());
			}
			else if(event.getSlot() == 8)
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(event.getSlot()==1)
				{
					UserDataObject u = new UserDataObject();
					u.setType(player, "NPC");
					u.setString(player, (byte)2,NPCname);
					u.setString(player, (byte)3,u.getNPCuuid(player));
					u.setString(player, (byte)4,"PresentLove");
					u.setInt(player, (byte)0, 1);
					player.sendMessage("§3[NPC] : 아무 아이템이나 줄 때 상승하는 호감도 수치를 입력 해 주세요! (-1000 ~ 1000 사이 값)");
					player.closeInventory();
				}
				else
					PresentGiveGUI(player, NPCname, true, event.getSlot());
			}
		}
		return;
	}
	

	
	public void RuneEquipGUIClose(InventoryCloseEvent event)
	{
		if(event.getInventory().getSize() > 9)
			if(event.getInventory().getItem(13)!=null)
				event.getPlayer().getInventory().addItem(event.getInventory().getItem(13));
		return;
	}
	
	public void PresentInventoryClose(InventoryCloseEvent event)
	{
		if(event.getInventory().getItem(4)!=null)
			event.getPlayer().getInventory().addItem(event.getInventory().getItem(4));
		return;
	}
}