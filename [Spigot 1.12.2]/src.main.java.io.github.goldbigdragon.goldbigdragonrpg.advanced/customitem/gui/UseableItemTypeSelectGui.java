package customitem.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import util.GuiUtil;
import util.YamlLoader;

public class UseableItemTypeSelectGui extends GuiUtil{

	private String uniqueCode = "§0§0§3§0§4§r";
	
	public void useableItemTypeSelectGui(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 27, uniqueCode + "§0소모성 아이템 타입");

		removeFlagStack("§f§l[귀환서]", 340,0,1,Arrays.asList("§7특정 위치로 신속히 이동할 수 있는","§7귀환서를 제작합니다."), 0, inv);
		removeFlagStack("§f§l[주문서]", 339,0,1,Arrays.asList("§7특별한 기운이 담긴","§7주문서를 제작합니다."), 1, inv);
		removeFlagStack("§f§l[스킬 북]", 403,0,1,Arrays.asList("§7특정 스킬을 배울 수 있는","§7스킬 북을 제작합니다.","","§c[게임 시스템이 '마비노기'여야 합니다.]"), 2, inv);
		removeFlagStack("§f§l[음식, 포션]", 297,0,1,Arrays.asList("§7퀵슬롯으로 등록이 가능한","§7음식 및 포션 류를 제작합니다."), 3, inv);
		removeFlagStack("§f§l[룬]", 381,0,1,Arrays.asList("§7무기의 능력을 올려주는","§7신비한 룬을 제작합니다."), 4, inv);
		removeFlagStack("§f§l[공구]", 145,0,1,Arrays.asList("§7장비의 숙련도 혹은","§7최대 내구도를 올려줍니다."), 5, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 18, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 26, inv);
		player.openInventory(inv);
	}

	public void useableItemTypeSelectClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 18)
				new UseableItemListGui().useableItemListGui(player, 0);
			else
			{
				String type = "";
				String lore = "";
				String displayName = "";
				short id = 267;
				short data = 0;
				
				if(slot == 0)
				{
					type = "§f[귀환서]";
					lore = "§f육체의 손실 없이 지정된 곳으로%enter%§f빠르게 이동할 수 있는 신비한%enter%§f귀환 주문서이다.";
					displayName = "§f0의 귀환 주문서";
					id = 340;
				}
				else if(slot == 1)
				{
					type = "§6[주문서]";
					lore = "§f사용시 스킬 포인트를%enter%§f5만큼 상승시켜 준다.";
					displayName ="§f스킬 포인트 5 증가 주문서";
					id = 339;
				}
				else if(slot == 2)
				{
					type = "§5[스킬북]";
					lore = "§f아직 아무것도 쓰여있지 않은%enter%§f빈 상태의 스킬 북이다.%enter% %enter%§f(아무것도 얻을 수 없을 것 같다.)";
					displayName = "§f빈 스킬 북";
					id = 403;
				}
				else if(slot == 3)
				{
					type = "§d[소비]";
					lore = "§f퀵 슬롯에 놓고, 위급시%enter%§f사용할 경우, 생명력을%enter%§f10 치료해 주는 포션이다.";
					displayName = "§f시뻘건 포션";
					id = 373;
					data = 8261;
				}
				else if(slot == 4)
				{
					type = "§9[룬]";
					lore = "§f강렬한 녹색의 룬이다.%enter%§f무기의 밸런스를 올려주는 듯 하다.";
					displayName ="§f녹색 룬";
					id = 351;
					data = 10;
				}
				else if(slot == 5)
				{
					type = "§e[공구]";
					lore = "§f손때 묻은 낡은 모루이다.%enter%§f숙련도를 올려줄 것 같다.";
					displayName ="§6숙련도 상승의 모루";
					id = 145;
					data = 2;
				}

			  	YamlLoader useableItemYaml = new YamlLoader();
				useableItemYaml.getConfig("Item/Consume.yml");
				
				int itemCounter = useableItemYaml.getKeys().size();
				useableItemYaml.set(itemCounter+".ShowType","§f[깔끔]");
				useableItemYaml.set(itemCounter+".ID",id);
				useableItemYaml.set(itemCounter+".Data",data);
				useableItemYaml.set(itemCounter+".DisplayName",displayName);
				useableItemYaml.set(itemCounter+".Lore",lore);
				useableItemYaml.set(itemCounter+".Type",type);
				useableItemYaml.set(itemCounter+".Grade","§f[일반]");
				
				if(slot == 0)
				{
					useableItemYaml.set(itemCounter+".World",player.getLocation().getWorld().getName().toString());
					useableItemYaml.set(itemCounter+".X",0);
					useableItemYaml.set(itemCounter+".Y",0);
					useableItemYaml.set(itemCounter+".Z",0);
					useableItemYaml.set(itemCounter+".Pitch",0);
					useableItemYaml.set(itemCounter+".Yaw",0);
				}
				else if(slot == 1)
				{
					useableItemYaml.set(itemCounter+".DEF",0);
					useableItemYaml.set(itemCounter+".Protect",0);
					useableItemYaml.set(itemCounter+".MaDEF",0);
					useableItemYaml.set(itemCounter+".MaProtect",0);
					useableItemYaml.set(itemCounter+".STR",0);
					useableItemYaml.set(itemCounter+".DEX",0);
					useableItemYaml.set(itemCounter+".INT",0);
					useableItemYaml.set(itemCounter+".WILL",0);
					useableItemYaml.set(itemCounter+".LUK",0);
					useableItemYaml.set(itemCounter+".Balance",0);
					useableItemYaml.set(itemCounter+".Critical",0);
					useableItemYaml.set(itemCounter+".HP",0);
					useableItemYaml.set(itemCounter+".MP",0);
					useableItemYaml.set(itemCounter+".SkillPoint",5);
					useableItemYaml.set(itemCounter+".StatPoint",0);
				}
				else if(slot == 2)
					useableItemYaml.set(itemCounter+".Skill","null");
				else if(slot == 3)
				{
					useableItemYaml.set(itemCounter+".HP",10);
					useableItemYaml.set(itemCounter+".MP",0);
					useableItemYaml.set(itemCounter+".Saturation",0);
					useableItemYaml.set(itemCounter+".Rebirth",false);
				}
				else if(slot == 4)
				{
					useableItemYaml.set(itemCounter+".MinDamage",0);
					useableItemYaml.set(itemCounter+".MaxDamage",0);
					useableItemYaml.set(itemCounter+".MinMaDamage",0);
					useableItemYaml.set(itemCounter+".MaxMaDamage",0);
					useableItemYaml.set(itemCounter+".DEF",0);
					useableItemYaml.set(itemCounter+".Protect",0);
					useableItemYaml.set(itemCounter+".MaDEF",0);
					useableItemYaml.set(itemCounter+".MaProtect",0);
					useableItemYaml.set(itemCounter+".Durability",0);
					useableItemYaml.set(itemCounter+".MaxDurability",0);
					useableItemYaml.set(itemCounter+".STR",0);
					useableItemYaml.set(itemCounter+".DEX",0);
					useableItemYaml.set(itemCounter+".INT",0);
					useableItemYaml.set(itemCounter+".WILL",0);
					useableItemYaml.set(itemCounter+".LUK",0);
					useableItemYaml.set(itemCounter+".Balance",10);
					useableItemYaml.set(itemCounter+".Critical",0);
					useableItemYaml.set(itemCounter+".HP",0);
					useableItemYaml.set(itemCounter+".MP",0);
				}
				else if(slot == 5)
				{
					useableItemYaml.set(itemCounter+".MaxDurability",0);
					useableItemYaml.set(itemCounter+".Proficiency", 100);
				}
				useableItemYaml.saveConfig();
				new UseableItemMakingGui().useableItemMakingGui(player,itemCounter);
			}
		}
	}
}
