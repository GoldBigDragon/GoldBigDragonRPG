package area.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class AreaSettingGui extends GuiUtil {

	private String uniqueCode = "§0§0§2§0§1§r";

	public void areaSettingGui (Player player, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		Inventory inv = Bukkit.createInventory(null, 45, uniqueCode + "§0영역 설정");

		if(!areaYaml.getBoolean(areaName+".BlockUse"))
			removeFlagStack("§f§l[블록 사용]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 9, inv);
		else
			removeFlagStack("§f§l[블록 사용]", 116,0,1,Arrays.asList("","§a§l[   허용   ]",""), 9, inv);

		if(!areaYaml.getBoolean(areaName+".BlockPlace"))
			removeFlagStack("§f§l[블록 설치]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 10, inv);
		else
			removeFlagStack("§f§l[블록 설치]", 2,0,1,Arrays.asList("","§a§l[   허용   ]",""), 10, inv);

		if(!areaYaml.getBoolean(areaName+".BlockBreak"))
			removeFlagStack("§f§l[블록 파괴]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 11, inv);
		else
			removeFlagStack("§f§l[블록 파괴]", 278,0,1,Arrays.asList("","§a§l[   허용   ]",""), 11, inv);

		if(!areaYaml.getBoolean(areaName+".PVP"))
			removeFlagStack("§f§l[   PVP   ]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 12, inv);
		else
			removeFlagStack("§f§l[   PVP   ]", 267,0,1,Arrays.asList("","§a§l[   허용   ]",""), 12, inv);

		if(!areaYaml.getBoolean(areaName+".MobSpawn"))
			removeFlagStack("§f§l[몬스터 스폰]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 13, inv);
		else
			removeFlagStack("§f§l[몬스터 스폰]", 52,0,1,Arrays.asList("","§a§l[   허용   ]",""), 13, inv);

		if(!areaYaml.getBoolean(areaName+".Alert"))
			removeFlagStack("§f§l[입장 메시지]", 166,0,1,Arrays.asList("","§c§l[   없음   ]",""), 14, inv);
		else
			removeFlagStack("§f§l[입장 메시지]", 340,0,1,Arrays.asList("","§a§l[   전송   ]",""), 14, inv);

		if(!areaYaml.getBoolean(areaName+".SpawnPoint"))
			removeFlagStack("§f§l[리스폰 장소]", 166,0,1,Arrays.asList("","§c§l[   불가   ]",""), 15, inv);
		else
			removeFlagStack("§f§l[리스폰 장소]", 397,3,1,Arrays.asList("","§a§l[   가능   ]",""), 15, inv);

		if(!areaYaml.getBoolean(areaName+".Music"))
			removeFlagStack("§f§l[배경음 재생]", 166,0,1,Arrays.asList("","§c§l[   중지   ]",""), 16, inv);
		else
			removeFlagStack("§f§l[배경음 재생]", 84,0,1,Arrays.asList("","§a§l[   재생   ]",""), 16, inv);

		if(areaYaml.getInt(areaName+".RegenBlock")==0)
			removeFlagStack("§f§l[블록 리젠]", 166,0,1,Arrays.asList("","§c§l[   중지   ]",""), 28, inv);
		else
			removeFlagStack("§f§l[블록 리젠]", 103,0,1,Arrays.asList("","§a§l[   활성   ]","","§3"+areaYaml.getInt(areaName+".RegenBlock")+" 초 마다 리젠","","§c[플레이어가 직접 캔 블록만 리젠 됩니다]",""), 28, inv);

		removeFlagStack("§f§l[특산품 설정]", 15,0,1,Arrays.asList("","§7현재 영역에서 블록을 캐면","§7지정된 아이템이 나오게","§7설정 합니다.","","§e[클릭시 특산품 설정]"), 19, inv);
		removeFlagStack("§f§l[낚시 아이템]", 346,0,1,Arrays.asList("","§7현재 영역에서 낚시를 하여","§7얻을 수 있는 물건을 확률별로","§7설정합니다.","§e[클릭시 낚시 아이템 설정]"), 20, inv);
		removeFlagStack("§f§l[우선순위 변경]", 384,0,1,Arrays.asList("","§7영역끼리 서로 겹칠 경우","§7우선 순위가 더 높은 영역이","§7적용됩니다.","§7이 속성을 이용하여 마을을 만들고,","§7마을 내부의 각종 상점 및","§7구역을 나눌 수 있습니다.","§9[   현재 우선 순위   ]","§f "+areaYaml.getInt(areaName+".Priority"),"","§e[클릭시 우선 순위 변경]"), 21, inv);
		removeFlagStack("§f§l[몬스터 설정]", 383,0,1,Arrays.asList("","§7현재 영역에서 자연적으로","§7스폰되는 몬스터 대신에","§7커스텀 몬스터로 변경합니다.","","§e[클릭시 커스텀 몬스터 설정]","§c[몬스터 스폰 설정시 비활성]"), 22, inv);
		removeFlagStack("§f§l[몬스터 스폰 설정]", 52,0,1,Arrays.asList("","§7현재 영역의 특정 구역에","§7특정 시각마다 몬스터를","§7스폰 합니다.","","§e[클릭시 몬스터 스폰 설정]"), 31, inv);
		removeFlagStack("§f§l[메시지 변경]", 386,0,1,Arrays.asList("","§7영역 입장 메시지를 변경합니다.","","§e[클릭시 입장 메시지 설정]"), 23, inv);
		removeFlagStack("§f§l[중심지 변경]", 138,0,1,Arrays.asList("","§7마을 귀환, 최근 방문 위치에서","§7리스폰 등의 현재 영역으로","§7텔레포트 되는 이벤트가 발생할 경우","§7현재 위치가 중심점이 됩니다.","","§3[  현재 중심지  ]","§3"+areaYaml.getString(areaName+".World")+" : "+areaYaml.getInt(areaName+".SpawnLocation.X")+","+areaYaml.getInt(areaName+".SpawnLocation.Y")+","+areaYaml.getInt(areaName+".SpawnLocation.Z"),"","§e[클릭시 현재 위치로 변경]"), 24, inv);
		
		if(areaYaml.getInt(areaName+".Restrict.MinNowLevel")+areaYaml.getInt(areaName+".Restrict.MinNowLevel")+
			areaYaml.getInt(areaName+".Restrict.MinRealLevel")+areaYaml.getInt(areaName+".Restrict.MaxRealLevel")==0)
			removeFlagStack("§a§l[입장 레벨 제한 없음]", 166,0,1,Arrays.asList("","§7레벨에 따른 입장 제한이 없습니다.",""), 34, inv);
		else
			removeFlagStack("§c§l[입장 레벨 제한]", 399,0,1,Arrays.asList("","§7레벨에 따른 입장 제한이 있습니다.",""
			,"§3[  최소 현재 레벨  ]", "  §3"+areaYaml.getInt(areaName+".Restrict.MinNowLevel")
			,"§3[  최대 현재 레벨  ]", "  §3"+areaYaml.getInt(areaName+".Restrict.MaxNowLevel")
			,"§7 ▼ 마비노기 시스템일 경우 추가 적용 ▼"
			,"§3[  최소 누적 레벨  ]", "  §3"+areaYaml.getInt(areaName+".Restrict.MinRealLevel")
			,"§3[  최대 누적 레벨  ]", "  §3"+areaYaml.getInt(areaName+".Restrict.MaxRealLevel"),""), 34, inv);
		String lore = "";
		short tracknumber = (short) areaYaml.getInt(areaName+".BGM");
		lore = " %enter%§7영역 입장시 테마 음을%enter%§7재생 시킬 수 있습니다.%enter% %enter%§9[클릭시 노트블록 사운드 설정]%enter% %enter%§3[트랙] §9"+ tracknumber+"%enter%"
		+"§3[제목] §9"+ new otherplugins.NoteBlockApiMain().getTitle(tracknumber)+"%enter%"
		+"§3[저자] §9"+new otherplugins.NoteBlockApiMain().getAuthor(tracknumber)+"%enter%§3[설명] ";
		
		String description = new otherplugins.NoteBlockApiMain().getDescription(areaYaml.getInt(areaName+".BGM"));
		String lore2="";
		short a = 0;
		for(int count = 0; count <description.toCharArray().length; count++)
		{
			lore2 = lore2+"§9"+description.toCharArray()[count];
			a++;
			if(a >= 15)
			{
				a = 0;
				lore2 = lore2+"%enter%      ";
			}
		}
		lore = lore + lore2;
		
		removeFlagStack("§f§l[영역 배경음]", 2263,0,1,Arrays.asList(lore.split("%enter%")), 25, inv);
		
		removeFlagStack("§f§l영역 이동", 368,0,1,Arrays.asList("§7현재 영역으로 빠르게 이동합니다."), 40, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7영역 목록으로 돌아갑니다."), 36, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7현재 창을 닫습니다.","§0"+areaName), 44, inv);
		
		player.openInventory(inv);
		return;
	}
	
	public void areaSettingClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 44)//창닫기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
		  	YamlLoader areaYaml = new YamlLoader();
			areaYaml.getConfig("Area/AreaList.yml");
			String areaName = ChatColor.stripColor(event.getInventory().getItem(44).getItemMeta().getLore().get(1));
			if(slot == 36)//이전 화면
				new AreaListGui().areaListGui(player,(short) 0);
			else if(slot >= 9 && slot <= 16)
			{
				if(slot == 9)//블록 사용
				{
					if(areaYaml.getBoolean(areaName+".BlockUse") == false)
						areaYaml.set(areaName+".BlockUse", true);
					else
						areaYaml.set(areaName+".BlockUse", false);
				}
				else if(slot == 10)//블록 설치
				{
					if(areaYaml.getBoolean(areaName+".BlockPlace") == false)
						areaYaml.set(areaName+".BlockPlace", true);
					else
						areaYaml.set(areaName+".BlockPlace", false);
				}
				else if(slot == 11)//블록 파괴
				{
					if(areaYaml.getBoolean(areaName+".BlockBreak") == false)
						areaYaml.set(areaName+".BlockBreak", true);
					else
						areaYaml.set(areaName+".BlockBreak", false);
				}
				else if(slot == 12)//PVP
				{
					if(areaYaml.getBoolean(areaName+".PVP") == false)
						areaYaml.set(areaName+".PVP", true);
					else
						areaYaml.set(areaName+".PVP", false);
				}
				else if(slot == 13)//몬스터 스폰
				{
					if(areaYaml.getBoolean(areaName+".MobSpawn") == false)
						areaYaml.set(areaName+".MobSpawn", true);
					else
						areaYaml.set(areaName+".MobSpawn", false);
				}
				else if(slot == 14)//입장 메시지
				{
					if(areaYaml.getBoolean(areaName+".Alert") == false)
						areaYaml.set(areaName+".Alert", true);
					else
						areaYaml.set(areaName+".Alert", false);
				}
				else if(slot == 15)//리스폰 장소
				{
					if(areaYaml.getBoolean(areaName+".SpawnPoint") == false)
						areaYaml.set(areaName+".SpawnPoint", true);
					else
						areaYaml.set(areaName+".SpawnPoint", false);
				}
				else if(slot == 16)//배경음 재생
				{
					if(areaYaml.getBoolean(areaName+".Music") == false)
						areaYaml.set(areaName+".Music", true);
					else
						areaYaml.set(areaName+".Music", false);
				}
				areaYaml.saveConfig();
				areaSettingGui(player, areaName);
			}
			else if(slot == 21)//우선 순위 변경
			{
				UserDataObject u = new UserDataObject();
				player.closeInventory();
				u.setType(player, "Area");
				u.setString(player, (byte)2, "Priority");
				u.setString(player, (byte)3, areaName);
				player.sendMessage("§a[영역] : §e"+areaName+"§a 영역의 우선 순위를 입력하세요!");
				player.sendMessage("§7(최소 0 ~ 최대 100)");
			}
			else if(slot == 23)//메시지 변경
			{
				SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.sendMessage("§6/영역 "+areaName+" 이름 <문자열>§e\n - "+areaName+" 구역의 알림 메시지에 보일 이름을 정합니다.");
				player.sendMessage("§6/영역 "+areaName+" 설명 <문자열>§e\n - "+areaName+" 구역의 알림 메시지에 보일 부가 설명을 정합니다.");
				player.sendMessage("§6%player%§f - 플레이어 지칭하기 -");
				player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
				"§3&3 §4&4 §5&5 " +
						"§6&6 §7&7 §8&8 " +
				"§9&9 §a&a §b&b §c&c " +
						"§d&d §e&e §f&f");
				player.closeInventory();
			}
			else if(slot == 24)//중심지 변경
			{
				areaYaml.set(areaName+".World", player.getLocation().getWorld().getName());
				areaYaml.set(areaName+".SpawnLocation.X", player.getLocation().getX());
				areaYaml.set(areaName+".SpawnLocation.Y", player.getLocation().getY());
				areaYaml.set(areaName+".SpawnLocation.Z", player.getLocation().getZ());
				areaYaml.set(areaName+".SpawnLocation.Pitch", player.getLocation().getPitch());
				areaYaml.set(areaName+".SpawnLocation.Yaw", player.getLocation().getYaw());
				areaYaml.saveConfig();
				areaSettingGui(player, areaName);
			}
			else if(slot == 25)//영역 배경음 설정
			{
				if(new otherplugins.NoteBlockApiMain().SoundList(player,true))
					new AreaMusicSettingGui().areaMusicSettingGui(player, 0, areaName);
				else
					SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.9F);
			}
			else if(slot == 28)//블록 리젠
			{
				if(areaYaml.getInt(areaName+".RegenBlock")==0)
				{
					player.closeInventory();
					UserDataObject u = new UserDataObject();
					areaYaml.set(areaName+".RegenBlock", 1);
					areaYaml.saveConfig();
					u.setType(player, "Area");
					u.setString(player, (byte)2, "ARR");
					u.setString(player, (byte)3, areaName);
					player.sendMessage("§a[영역] : §e"+areaName+"§a 영역의 블록 리젠 속도를 설정하세요!");
					player.sendMessage("§7(최소 1초 ~ 최대 3600초(1시간))");
				}
				else
				{
					areaYaml.set(areaName+".RegenBlock", 0);
					areaYaml.saveConfig();
					areaSettingGui(player, areaName);
				}
			}
			else if(slot == 19)//특산품 설정
				new AreaBlockSettingGui().areaBlockSettingGui(player, 0, areaName);
			else if(slot == 20)//낚시 아이템
				new AreaFishSettingGui().areaFishSettingGui(player, areaName);
			else if(slot == 22)//몬스터 설정
				new AreaMonsterSettingGui().areaMonsterSettingGui(player, 0, areaName);
			else if(slot == 31)//몬스터 스폰 룰
				new AreaMonsterSpawnSettingGui().areaMonsterSpawnSettingGui(player, 0, areaName);
			else if(slot == 34)//레벨 제한
			{
				UserDataObject u = new UserDataObject();
				player.closeInventory();
				u.setType(player, "Area");
				u.setString(player, (byte)2, "MinNLR");
				u.setString(player, (byte)3, areaName);
				player.sendMessage("§a[영역] : §e"+areaName+"§a 영역의 입장에 필요한 최소 레벨을 입력 하세요!§7 (0 입력시 제한 없음)");
			}
			else if(slot == 40)//영역 이동
			{
				player.closeInventory();
				player.teleport(new Location(Bukkit.getWorld(areaYaml.getString(areaName+".World")),areaYaml.getInt(areaName+".SpawnLocation.X"), areaYaml.getInt(areaName+".SpawnLocation.Y"),areaYaml.getInt(areaName+".SpawnLocation.Z"),areaYaml.getInt(areaName+".SpawnLocation.Yaw"),areaYaml.getInt(areaName+".SpawnLocation.Pitch")));
			}
		}
		return;
	}
}
