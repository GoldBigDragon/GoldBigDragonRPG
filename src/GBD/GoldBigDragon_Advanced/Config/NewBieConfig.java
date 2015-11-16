package GBD.GoldBigDragon_Advanced.Config;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;
import net.md_5.bungee.api.ChatColor;

public class NewBieConfig
{
    public void CreateNewConfig()
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
    	YamlManager YM = Event_YC.getNewConfig("ETC/NewBie.yml");
	  	YM.set("TelePort.World", Bukkit.getWorlds().get(0).getSpawnLocation().getWorld().getName().toString());
	  	YM.set("TelePort.X", Bukkit.getWorlds().get(0).getSpawnLocation().getX());
	  	YM.set("TelePort.Y", Bukkit.getWorlds().get(0).getSpawnLocation().getY());
	  	YM.set("TelePort.Z", Bukkit.getWorlds().get(0).getSpawnLocation().getZ());
	  	YM.set("TelePort.Pitch", Bukkit.getWorlds().get(0).getSpawnLocation().getPitch());
	  	YM.set("TelePort.Yaw", Bukkit.getWorlds().get(0).getSpawnLocation().getYaw());

		ItemStack Icon = new MaterialData(340, (byte) 0).toItemStack(1);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"[서버 가이드]");
		Icon_Meta.setLore(Arrays.asList(ChatColor.BLUE+""+ChatColor.BOLD+"[일반 유저]",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/스텟",
				ChatColor.WHITE+" 자신의 스텟을 확인합니다.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/스킬",
				ChatColor.WHITE+" 자신의 스킬을 확인합니다.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/옵션",
				ChatColor.WHITE+" 각종 옵션을 설정합니다.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/퀘스트",
				ChatColor.WHITE+" 현재 진행중인 퀘스트를 확인합니다.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/돈",
				ChatColor.WHITE+" 현재 보유중인 돈을 확인합니다.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/기타",
				ChatColor.WHITE+" 기타 시스템을 확인합니다.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/파티",
				ChatColor.WHITE+" 파티에 대한 기능을 실행합니다."
				));
		Icon.setItemMeta(Icon_Meta);
	  	YM.set("SupportItem.0", Icon);

	  	Icon = new MaterialData(340, (byte) 0).toItemStack(1);
	  	Icon_Meta = Icon.getItemMeta();
	  	Icon_Meta.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"[서버 가이드]");
	  	Icon_Meta.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"[오퍼레이터]",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/오피박스",
				ChatColor.WHITE+" 오피 전용 설정창을 엽니다.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/영역",
				ChatColor.WHITE+" 영역을 지정합니다.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/엔티티제거 [1~10000]",
				ChatColor.WHITE+" 자신을 기준으로 해당 범위 내의",
				ChatColor.WHITE+" 모든 엔티티를 제거합니다."
				));
	  	Icon.setItemMeta(Icon_Meta);
	  	YM.set("SupportItem.1", Icon);
	  	
	  	YM.set("SupportMoney", 1000);
	  	YM.set("FirstQuest", "null");

	  	GBD.GoldBigDragon_Advanced.Main.ServerOption SO = new GBD.GoldBigDragon_Advanced.Main.ServerOption();
	  	
	  	Icon = new MaterialData(340, (byte) 0).toItemStack(1);
	  	Icon_Meta = Icon.getItemMeta();
	  	Icon_Meta.setDisplayName(ChatColor.YELLOW +""+ ChatColor.BOLD + "스텟 시스템");
	  	Icon_Meta.setLore(Arrays.asList(ChatColor.GRAY+ "플러그인에는 5가지 스텟이 있습니다.",ChatColor.RED +"["+SO.STR+"]",ChatColor.GRAY+""+SO.STR+"은 플레이어의",ChatColor.GRAY+"물리적 데미지에 관여합니다.",ChatColor.GREEN +  "["+SO.DEX+"]",ChatColor.GRAY+""+SO.DEX+"는 플레이어의 밸런스 및",ChatColor.GRAY+"생산 성공률과 생산 품질,",ChatColor.GRAY+"원거리 데미지에 관여합니다.",ChatColor.BLUE+"["+SO.INT+"]",ChatColor.GRAY+""+SO.INT+"은 마법방어 및 마법보호,",ChatColor.GRAY+"마법 공격력에 관여합니다.",ChatColor.WHITE+"["+SO.WILL+"]",ChatColor.GRAY + ""+SO.WILL+"는 플레이어의",ChatColor.GRAY + "크리티컬에 관여합니다.",ChatColor.YELLOW + "["+SO.LUK+"]",ChatColor.GRAY + ""+SO.LUK+"은 크리티컬 및",ChatColor.GRAY +"럭키 피니시, 럭키 보너스 등",ChatColor.GRAY +"각종 '확률'에 관여합니다."));
	  	Icon.setItemMeta(Icon_Meta);
	  	YM.set("Guide.0", Icon);

		Icon = new MaterialData(340, (byte) 0).toItemStack(1);
		Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(ChatColor.YELLOW +""+ ChatColor.BOLD + "단축키 시스템");
		Icon_Meta.setLore(Arrays.asList(ChatColor.WHITE+ "/스킬"+ChatColor.GRAY+" 명령어를 통하여",ChatColor.GRAY+"현재 자신이 알고 있는 스킬을",ChatColor.GRAY+"열람할 수 있으며, 클릭을 통해",ChatColor.GRAY+"해당 스킬을 핫바에 등록 시킴으로 써",ChatColor.GRAY+"단축키와 같이 빠른 스킬 사용이",ChatColor.GRAY+"가능합니다.",ChatColor.GRAY+"또한 게임 내의 특정 아이템들은",ChatColor.GRAY+"단축키에 놓고 빠르게 사용이",ChatColor.GRAY+"가능하게 되어 있습니다."));
		Icon.setItemMeta(Icon_Meta);
	  	YM.set("Guide.1", Icon);
	  	YM.saveConfig();
	  	return;
	}
}
