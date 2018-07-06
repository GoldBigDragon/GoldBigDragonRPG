package customitem.gui;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import util.GuiUtil;
import util.PlayerUtil;

public class ApplyToolGui extends GuiUtil{

	private String uniqueCode = "§0§0§3§0§7§r";
	
	public void applyToolGui(Player player, ItemStack item)
	{
		Inventory inv = Bukkit.createInventory(null, 9, uniqueCode + "§0개조할 아이템 선택");

		inv.setItem(4, item);
		
		player.openInventory(inv);
	}

	public void applyToolClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		ItemStack toolItem = event.getInventory().getItem(4);
		int maxDurability = 0;
		int proficiency = 0;
		
		for(int counter = 0; counter < toolItem.getItemMeta().getLore().size();counter++)
		{
			String nowlore=ChatColor.stripColor(toolItem.getItemMeta().getLore().get(counter));
			if(nowlore.contains(" : "))
			{
				if(nowlore.contains("최대 내구도 증가"))
					maxDurability = Integer.parseInt(nowlore.split(" : ")[1]);
				else if(nowlore.contains("숙련도 증가"))
					proficiency = Integer.parseInt(nowlore.split(" : ")[1]);
			}
		}
		
		Inventory inv = event.getClickedInventory();
		event.setCancelled(true);
		if(inv.getType() == InventoryType.PLAYER)
		{
			ItemStack item = event.getCurrentItem();
			if(item != null && item.hasItemMeta())
			{
				ItemMeta im = item.getItemMeta();
				if(im.hasLore())
				{
					List<String> lore = im.getLore();
					String loreToString = lore.toString();
					if(loreToString.contains("내구도") || loreToString.contains("숙련도"))
					{
						if(!(loreToString.contains("[주문서]")||loreToString.contains("[룬]")||loreToString.contains("[소비]")||loreToString.contains("[공구]")))
						{
							for(int count = 0; count < lore.size(); count++)
							{
								String nowlore = ChatColor.stripColor(lore.get(count));
								if(nowlore.contains(" : "))
								{
									if(nowlore.contains("내구도") && nowlore.contains(" / "))
									{
										String[] stat = ChatColor.stripColor(im.getLore().get(count)).split(" : ");
										String[] subLore = stat[1].split(" / ");
										lore.set(count,"§f"+  stat[0] + " : "+ subLore[0] +" / "+ (Integer.parseInt(subLore[1])+maxDurability));
										im.setLore(lore);
										item.setItemMeta(im);
									}
									else if(nowlore.contains("숙련도"))
									{
										String[] stat = ChatColor.stripColor(nowlore).split(" : ");
										String[] subLore = stat[1].split("%");
										DecimalFormat format = new DecimalFormat(".##");
										String str = format.format((Float.parseFloat(subLore[0])+proficiency));
										if(str.charAt(0)=='.')
											str = "0"+str;
										if((Float.parseFloat(subLore[0])+proficiency) >= 100.0F)
											lore.set(count,"§f"+  stat[0] + " : "+ 100.0 +"%§f");
										else
											lore.set(count,"§f"+  stat[0] + " : "+ str +"%§f");
										im.setLore(lore);
										item.setItemMeta(im);
									}
								}
							}
							event.setCurrentItem(null);
							new PlayerUtil().giveItemDrop(player, item, player.getLocation());
							player.getOpenInventory().getTopInventory().setItem(4, null);
							effect.SoundEffect.playSound(player, Sound.BLOCK_ANVIL_USE, 0.9F, 1.4F);
							player.closeInventory();
							return;
						}
					}
				}
			}
			effect.SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.8F);
			player.sendMessage("§c[ X ] 해당 아이템에는 사용할 수 없습니다!");
		}
	}

	public void applyToolClose(InventoryCloseEvent event)
	{
		ItemStack item = event.getInventory().getItem(4);
		if(item != null)
		{
			Player player = (Player)event.getPlayer();
			new PlayerUtil().giveItemDrop(player, item, player.getLocation());
		}
	}
}
