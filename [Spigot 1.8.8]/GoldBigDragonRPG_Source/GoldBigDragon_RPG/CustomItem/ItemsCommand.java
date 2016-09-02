package GoldBigDragon_RPG.CustomItem;

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

import GoldBigDragon_RPG.Command.HelpMessage;

public class ItemsCommand extends HelpMessage
{
	public void onCommand1(CommandSender talker, Command command, String string, String[] args)
	{
		Player player = (Player) talker;
		  if(talker.isOp() == true)
		  {
			  if(args.length == 0 || args.length >= 4)
			  {
					HelpMessager((Player)talker,(byte) 1);
			  		return;
			  }
			  switch(ChatColor.stripColor(args[0]))
			  {
		  			case "목록" :
		  			{
		  				GoldBigDragon_RPG.CustomItem.ItemGUI IGUI = new GoldBigDragon_RPG.CustomItem.ItemGUI();
		  				IGUI.ItemListGUI(player,0);
		  			}
		  			return;
			  }
		  }
		  else
		  {
			  talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			  new GoldBigDragon_RPG.Effect.Sound().SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			  return;
		  }
	}

	public void onCommand2(CommandSender talker, Command command, String string, String[] args)
	{
		Player player = (Player) talker;
		if(talker.isOp() == true)
		{
			String s="";
			switch(ChatColor.stripColor(args[0]))
			{
				case "태그삭제" :
				{
					ItemMeta itemMeta = player.getItemInHand().getItemMeta();
					itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
					itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
					itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
					itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
					player.getItemInHand().setItemMeta(itemMeta);
				}
				return;
				case "ID" :
				{
			  		if(args.length != 2)
					{
						HelpMessager((Player)talker,(byte) 1);
						return;
					}
				  	SettingItemMeta(player, (byte) 0, Integer.parseInt(args[1]));
				}
				return;
				case "DATA" :
				{
			  		if(args.length != 2)
					{
						HelpMessager((Player)talker,(byte) 1);
						return;
					}
				  	SettingItemMeta(player, (byte) 1, Integer.parseInt(args[1]));
				}
				return;
				case "개수" :
				{
			  		if(args.length != 2)
					{
						HelpMessager((Player)talker,(byte) 1);
						return;
					}
				  	SettingItemMeta(player, (byte) 2, Integer.parseInt(args[1]));
				}
				return;
				case "이름" :
				{
			  		if(args.length < 2)
					{
						HelpMessager((Player)talker,(byte) 1);
						return;
					}
	  				if(args.length >= 2)
	  				{
	  					s = args[1];
	  					for(byte a = 2; a<= ((args.length)-1);a++)
	  						s = s+" "+args[a];
	  				}
				  	SettingItemMeta(player, (byte) 0, s);
				}
				return;
				case "설명추가" :
				{
			  		if(args.length < 2)
					{
						HelpMessager((Player)talker,(byte) 1);
						return;
					}
	  				if(args.length >= 2)
	  				{
	  					s = args[1];
	  					for(byte a = 2; a<= ((args.length)-1);a++)
	  						s = s+" "+args[a];
	  				}
				  	SettingItemMeta(player, (byte) 1, s);
				}
				return;
				case "설명제거" :
				{
					SettingItemMeta(player, (byte) 2, null);
				}
				return;
				case "수리" :
				{
					if(player.getItemInHand().getType() == Material.AIR)
					{
						GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
						sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						player.sendMessage(ChatColor.RED + "[SYSTEM] : 손에 수리할 아이템을 쥐고 있어야 합니다!");
						return;
					}
					player.getItemInHand().setDurability((short)-player.getItemInHand().getType().getMaxDurability());
					ItemStack item = player.getInventory().getItemInHand();
					if(item.hasItemMeta() == true)
					{
						if(item.getItemMeta().hasLore() == true)
						{
							for(short count = 0; count < item.getItemMeta().getLore().size(); count++)
							{
								ItemMeta Meta = item.getItemMeta();
								if(Meta.getLore().get(count).contains("내구도") == true)
								{
									String[] Lore = Meta.getLore().get(count).split(" : ");
									String[] SubLore = Lore[1].split(" / ");
									List<String> PLore = Meta.getLore();
									PLore.set(count, Lore[0] + " : "+SubLore[1]+" / "+SubLore[1]);
									Meta.setLore(PLore);
									item.setItemMeta(Meta);
								}
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
					HelpMessager((Player)talker,(byte) 1);
				}
			  	return;
			}
		}
		else
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			new GoldBigDragon_RPG.Effect.Sound().SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
	}
	
	public void SettingItemMeta(Player player, byte type, int value)
	{
		if(player.isOp() == false)
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;
		}
		if(player.getItemInHand().getType() == Material.AIR || player.getItemInHand().getTypeId() == 0 )
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 손에 아이템을 들고 있어야 합니다!");
			return;
		}
		else
		{
			ItemStack item = player.getItemInHand();
			switch(type)
			{
			case 0:
				{
					item.setTypeId(value);
					player.setItemInHand(item);
				}
				break;
			case 1:
				{
					ItemStack it = new MaterialData(item.getTypeId(), (byte) value).toItemStack();
					it.setAmount(item.getAmount());
					if(item.hasItemMeta() == true)
						it.setItemMeta(item.getItemMeta());
					player.setItemInHand(it);
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
	
	public void SettingItemMeta(Player player, byte type, String value)
	{
		if(player.isOp() == false)
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;
		}
		if(player.getItemInHand().getType() == Material.AIR || player.getItemInHand().getTypeId() == 0 )
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 손에 아이템을 들고 있어야 합니다!");
			return;
		}
		else
		{
			ItemStack item = player.getItemInHand();
			ItemMeta itemMeta = item.getItemMeta();
			List<String> Lore = Arrays.asList();
			switch(type)
			{
			case 0:
				{
					value = ChatColor.WHITE + value;
					itemMeta.setDisplayName(value);
					item.setItemMeta(itemMeta);
					player.setItemInHand(item);
				}
				break;
			case 1:
				{
					value = ChatColor.WHITE + value;
					if(itemMeta.hasLore() == false)
						itemMeta.setLore(Arrays.asList(value));
					else
					{
						Lore = itemMeta.getLore();
						Lore.add(Lore.size(), value);
						itemMeta.setLore(Lore);
					}
					item.setItemMeta(itemMeta);
					player.setItemInHand(item);
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
						Lore = itemMeta.getLore();
						for(byte repeat = 0; repeat < 5; repeat++)
							for(byte count=0;count<Lore.size();count++)
								Lore.remove(count);
						itemMeta.setLore(Lore);
					}
					item.setItemMeta(itemMeta);
					player.setItemInHand(item);
				}
				break;
			}
		}
		return;
	}

}
