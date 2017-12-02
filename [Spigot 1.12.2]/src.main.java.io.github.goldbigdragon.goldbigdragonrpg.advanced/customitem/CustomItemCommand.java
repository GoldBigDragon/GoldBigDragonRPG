package customitem;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import effect.SoundEffect;

public class CustomItemCommand
{
	public void onCommand1(CommandSender talker, Command command, String string, String[] args)
	{
		Player player = (Player) talker;
		  if(talker.isOp())
		  {
			  if(args.length == 0 || args.length >= 4)
			  {
					helpMessage(player);
			  		return;
			  }
			  switch(ChatColor.stripColor(args[0]))
			  {
		  			case "목록" :
		  			{
		  				customitem.CustomItemGui customItemGui = new customitem.CustomItemGui();
		  				customItemGui.itemListGui(player,0);
		  			}
		  			return;
			  }
		  }
		  else
		  {
			  talker.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			  SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			  return;
		  }
	}

	public void onCommand2(CommandSender talker, Command command, String string, String[] args)
	{
		Player player = (Player) talker;
		if(talker.isOp())
		{
			String s="";
			switch(ChatColor.stripColor(args[0]))
			{
				case "태그삭제" :
				{
					ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
					itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
					itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
					itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
					itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
					player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
				}
				return;
				case "ID" :
				{
			  		if(args.length != 2)
					{
						helpMessage(player);
						return;
					}
				  	SettingItemMeta(player, (byte) 0, Integer.parseInt(args[1]));
				}
				return;
				case "DATA" :
				{
			  		if(args.length != 2)
					{
						helpMessage(player);
						return;
					}
				  	SettingItemMeta(player, (byte) 1, Integer.parseInt(args[1]));
				}
				return;
				case "개수" :
				{
			  		if(args.length != 2)
					{
						helpMessage(player);
						return;
					}
				  	SettingItemMeta(player, (byte) 2, Integer.parseInt(args[1]));
				}
				return;
				case "이름" :
				{
			  		if(args.length < 2)
					{
						helpMessage(player);
						return;
					}
	  				if(args.length >= 2)
	  				{
	  					s = args[1];
	  					for(int a = 2; a<= ((args.length)-1);a++)
	  						s = s+" "+args[a];
	  				}
				  	settingItemMeta(player, (byte) 0, s);
				}
				return;
				case "설명추가" :
				{
			  		if(args.length < 2)
					{
						helpMessage(player);
						return;
					}
	  				if(args.length >= 2)
	  				{
	  					s = args[1];
	  					for(int a = 2; a<= ((args.length)-1);a++)
	  						s = s+" "+args[a];
	  				}
				  	settingItemMeta(player, (byte) 1, s);
				}
				return;
				case "설명제거" :
				{
					settingItemMeta(player, (byte) 2, null);
				}
				return;
				case "수리" :
				{
					if(player.getInventory().getItemInMainHand().getType() == Material.AIR)
					{
						SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						player.sendMessage("§c[SYSTEM] : 손에 수리할 아이템을 쥐고 있어야 합니다!");
						return;
					}
					player.getInventory().getItemInMainHand().setDurability((short)-player.getInventory().getItemInMainHand().getType().getMaxDurability());
					ItemStack item = player.getInventory().getItemInMainHand();
					if(item.hasItemMeta()&&item.getItemMeta().hasLore())
					{
						for(int count = 0; count < item.getItemMeta().getLore().size(); count++)
						{
							ItemMeta meta = item.getItemMeta();
							if(meta.getLore().get(count).contains("내구도"))
							{
								String[] lore = meta.getLore().get(count).split(" : ");
								String[] subLore = lore[1].split(" / ");
								List<String> pLore = meta.getLore();
								pLore.set(count, lore[0] + " : "+subLore[1]+" / "+subLore[1]);
								meta.setLore(pLore);
								item.setItemMeta(meta);
							}
						}
					}
				}
				return;
				case "포션" :
					return;
				case "인챈트" :
					return;
				default:
				{
					helpMessage(player);
				}
			  	return;
			}
		}
		else
		{
			talker.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
	}
	
	public void SettingItemMeta(Player player, byte type, int value)
	{
		if(!player.isOp())
		{
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;
		}
		if(player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInMainHand().getTypeId() == 0 )
		{
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 손에 아이템을 들고 있어야 합니다!");
			return;
		}
		else
		{
			ItemStack item = player.getInventory().getItemInMainHand();
			switch(type)
			{
			case 0:
				{
					item.setTypeId(value);
					player.getInventory().setItemInMainHand(item);
				}
				break;
			case 1:
				{
					ItemStack it = new MaterialData(item.getTypeId(), (byte) value).toItemStack();
					it.setAmount(item.getAmount());
					if(item.hasItemMeta())
						it.setItemMeta(item.getItemMeta());
					player.getInventory().setItemInMainHand(it);
				}
				break;
			case 2:
				{
					if(value >= 127) value = 127;
					if(value <= 0) value = 1;
					item.setAmount(value);
				}
				break;
			}
		}
		return;
	}
	
	public void settingItemMeta(Player player, byte type, String value)
	{
		if(!player.isOp())
		{
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;
		}
		if(player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInMainHand().getTypeId() == 0 )
		{
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 손에 아이템을 들고 있어야 합니다!");
			return;
		}
		else
		{
			ItemStack item = player.getInventory().getItemInMainHand();
			ItemMeta itemMeta = item.getItemMeta();
			List<String> lore = Arrays.asList();
			switch(type)
			{
			case 0:
				{
					value = "§f"+ value;
					itemMeta.setDisplayName(value);
					item.setItemMeta(itemMeta);
					player.getInventory().setItemInMainHand(item);
				}
				break;
			case 1:
				{
					value = "§f"+ value;
					if(itemMeta.hasLore() == false)
						itemMeta.setLore(Arrays.asList(value));
					else
					{
						lore = itemMeta.getLore();
						lore.add(lore.size(), value);
						itemMeta.setLore(lore);
					}
					item.setItemMeta(itemMeta);
					player.getInventory().setItemInMainHand(item);
				}
				break;
			case 2:
				{
					if(itemMeta.hasLore() == false)
					{
						return;
					}
					else
					{
						lore = itemMeta.getLore();
						for(int repeat = 0; repeat < 5; repeat++)
							for(int count=0;count<lore.size();count++)
								lore.remove(count);
						itemMeta.setLore(lore);
					}
					item.setItemMeta(itemMeta);
					player.getInventory().setItemInMainHand(item);
				}
				break;
			}
		}
		return;
	}

	public void helpMessage(Player player)
	{
		player.sendMessage("§e────────────[아이템 명령어]────────────");
		player.sendMessage("§6/아이템 수리§e - 손에 들고있는 아이템을 수리합니다.");
		player.sendMessage("§6/아이템 이름 <문자열>§e - 해당 아이템의 보여질 이름을 설정합니다.");
		player.sendMessage("§6/아이템 ID <숫자>§e - 해당 아이템의 ID값을 설정합니다.");
		player.sendMessage("§6/아이템 DATA <숫자>§e - 해당 아이템의 DATA값을 설정합니다.");
		player.sendMessage("§6/아이템 개수 <숫자>§e - 해당 아이템의 개수를 설정합니다.");
		player.sendMessage("§6/아이템 태그삭제§e - [1.8.0은 안됨] 다이아몬드 검의 +7 공격피해와 같은 아이템 태그를 삭제합니다.");
		//player.sendMessage("§6/아이템 포션 [효과] [강도]§e - 아이템에 포션 효과를 더합니다.");
		//player.sendMessage("§6/아이템 인챈트 [인챈트] [레벨]§e - 아이템에 인챈트 효과를 더합니다.");
		player.sendMessage("§6/아이템 설명추가 <문자열>§e - 해당 아이템의 로어 한 줄을 추가합니다.");
		player.sendMessage("§6/아이템 설명제거§e - 해당 아이템의 모든 로어를 삭제합니다.");
		player.sendMessage("§a[아래와 같은 설명을 추가할 시, 아이템에 효과가 생깁니다.]");
		player.sendMessage("§b/아이템 설명추가 "+main.MainServerOption.damage+" : 3 ~ 6§c (아이템 장착시 "+main.MainServerOption.damage+" 3 ~ 6 상승)");
		player.sendMessage("§b/아이템 설명추가 방어 : 3§c (아이템 장착시 방어 3상승)");
		player.sendMessage("§a[추가 가능한 옵션 태그]");
		player.sendMessage("§b["+main.MainServerOption.damage+" : <숫자> ~ <숫자>] [방어 : <숫자>] [보호 : <숫자>]\n"
				+ "["+main.MainServerOption.magicDamage+" : <숫자> ~ <숫자>] [마법 방어 : <숫자>] [마법 보호 : <숫자>]\n"
				+ "["+main.MainServerOption.statSTR+" : <숫자>] ["+main.MainServerOption.statDEX+" : <숫자>] ["+main.MainServerOption.statINT+" : <숫자>] ["+main.MainServerOption.statWILL+" : <숫자>] ["+main.MainServerOption.statLUK+" : <숫자>]\n"
				+ "[크리티컬 : <숫자>] [밸런스 : <숫자>] [내구도 : <숫자> / <숫자>] \n"
				+ "[업그레이드 : <숫자> / <숫자>] [생명력 : <숫자>] [마나 : <숫자>]\n§a[아이템 타입 태그] - 퀵슬롯 등록된 스킬의 무기 제한 옵션에 관여§b\n[소비] [근접 무기] [한손 검] [양손 검] [원거리 무기] [활] [석궁] [도끼] [낫] [마법 무기] [원드] [스태프]");
		player.sendMessage("§e────────────────────────────────");
	}
}
