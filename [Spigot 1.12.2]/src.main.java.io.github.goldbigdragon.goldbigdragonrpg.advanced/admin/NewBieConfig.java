package admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import net.md_5.bungee.api.ChatColor;
import util.YamlLoader;



public class NewBieConfig
{
    public void createNewConfig()
	{
	  	YamlLoader newbieYaml = new YamlLoader();
    	newbieYaml.getConfig("ETC/NewBie.yml");
	  	newbieYaml.set("TelePort.World", Bukkit.getWorlds().get(0).getSpawnLocation().getWorld().getName().toString());
	  	newbieYaml.set("TelePort.X", Bukkit.getWorlds().get(0).getSpawnLocation().getX());
	  	newbieYaml.set("TelePort.Y", Bukkit.getWorlds().get(0).getSpawnLocation().getY());
	  	newbieYaml.set("TelePort.Z", Bukkit.getWorlds().get(0).getSpawnLocation().getZ());
	  	newbieYaml.set("TelePort.Pitch", Bukkit.getWorlds().get(0).getSpawnLocation().getPitch());
	  	newbieYaml.set("TelePort.Yaw", Bukkit.getWorlds().get(0).getSpawnLocation().getYaw());

		ItemStack icon = new MaterialData(340, (byte) 0).toItemStack(1);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§e§l[서버 가이드]");
		iconMeta.setLore(Arrays.asList("§9§l[일반 유저]",
				"§e§l/스텟",
				"§f 자신의 스텟을 확인합니다.",
				"§e§l/스킬",
				"§f 자신의 스킬을 확인합니다.",
				"§e§l/옵션",
				"§f 각종 옵션을 설정합니다.",
				"§e§l/퀘스트",
				"§f 현재 진행중인 퀘스트를 확인합니다.",
				"§e§l/돈",
				"§f 현재 보유중인 돈을 확인합니다.",
				"§e§l/기타",
				"§f 기타 시스템을 확인합니다.",
				"§e§l/파티",
				"§f 파티에 대한 기능을 실행합니다.",
				"",
				"§6§l[장비 구경/교환/친구 추가]",
				"§7 플레이어 우 클릭",
				""
				));
		icon.setItemMeta(iconMeta);
	  	newbieYaml.set("SupportItem.0", icon);

	  	icon = new MaterialData(340, (byte) 0).toItemStack(1);
	  	iconMeta = icon.getItemMeta();
	  	iconMeta.setDisplayName("§e§l[서버 가이드]");
	  	iconMeta.setLore(Arrays.asList("§d§l[오퍼레이터]",
				"§e§l/오피박스",
				"§f 오피 전용 설정창을 엽니다.",
				"§e§l/영역",
				"§f 영역을 지정합니다.",
				"§e§l/아이템",
				"§f 현재 손에 든 아이템에 대한",
				"§f NBT 설정을 합니다.",
				"§e§l/워프",
				"§f 워프 관련 명령어를 봅니다.",
				"§e§l/엔티티제거 [1~10000]",
				"§f 자신을 기준으로 해당 범위 내의",
				"§f 모든 엔티티를 제거합니다.",
				"§e§l/타입추가 [새로운 아이템 타입]",
				"§f 커스텀 아이템 타입을 추가합니다.",
				""
				));
	  	icon.setItemMeta(iconMeta);
	  	newbieYaml.set("SupportItem.1", icon);
	  	
	  	newbieYaml.set("SupportMoney", 1000);
	  	newbieYaml.set("FirstQuest", "null");

	  	icon = new MaterialData(340, (byte) 0).toItemStack(1);
	  	iconMeta = icon.getItemMeta();
	  	iconMeta.setDisplayName("§e§l스텟 시스템");
	  	iconMeta.setLore(Arrays.asList("§7플러그인에는 5가지 스텟이 있습니다.","§c["+main.MainServerOption.statSTR+"]","§7"+main.MainServerOption.statSTR+"은 플레이어의","§7물리적 데미지에 관여합니다.",ChatColor.GREEN + "["+main.MainServerOption.statDEX+"]","§7"+main.MainServerOption.statDEX+"는 플레이어의 밸런스 및","§7생산 성공률과 생산 품질,","§7원거리 데미지에 관여합니다.","§9["+main.MainServerOption.statINT+"]","§7"+main.MainServerOption.statINT+"은 마법방어 및 마법보호,","§7마법 공격력에 관여합니다.","§f["+main.MainServerOption.statWILL+"]","§7"+main.MainServerOption.statWILL+"는 플레이어의","§7크리티컬에 관여합니다.","§e["+main.MainServerOption.statLUK+"]","§7"+main.MainServerOption.statLUK+"은 크리티컬 및","§7럭키 피니시, 럭키 보너스 등","§7각종 '확률'에 관여합니다."));
	  	icon.setItemMeta(iconMeta);
	  	newbieYaml.set("Guide.0", icon);

		icon = new MaterialData(340, (byte) 0).toItemStack(1);
		iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§e§l단축키 시스템");
		iconMeta.setLore(Arrays.asList("§f/스킬§7 명령어를 통하여","§7현재 자신이 알고 있는 스킬을","§7열람할 수 있으며, 클릭을 통해","§7해당 스킬을 핫바에 등록 시킴으로 써","§7단축키와 같이 빠른 스킬 사용이","§7가능합니다.","§7또한 게임 내의 특정 아이템들은","§7단축키에 놓고 빠르게 사용이","§7가능하게 되어 있습니다."));
		icon.setItemMeta(iconMeta);
	  	newbieYaml.set("Guide.1", icon);
	  	newbieYaml.saveConfig();
	  	return;
	}
}
