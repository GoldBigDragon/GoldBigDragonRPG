package GBD.GoldBigDragon_Advanced.ETC;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class Items
{
	private static GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();

	public void SettingItemMeta(Player player, int type, int value)
	{
		if(player.isOp() == false)
		{
			s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;
		}
		if(player.getItemInHand().getType() == Material.AIR || player.getItemInHand().getTypeId() == 0 )
		{
			s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 손에 아이템을 들고 있어야 합니다!");
			return;
		}
		else
		{
			ItemStack item = player.getItemInHand();
			switch(type)
			{
			case 0:
				item.setTypeId(value);
				player.setItemInHand(item);
				break;
			case 1:
				ItemStack it = new MaterialData(item.getTypeId(), (byte) value).toItemStack();
				it.setAmount(item.getAmount());
				if(item.hasItemMeta() == true)
					it.setItemMeta(item.getItemMeta());
				player.setItemInHand(it);
				break;
			case 2:
				if(value >= 127) value = 127;
				if(value <= 0) value = 1;
				item.setAmount(value);
				break;
			}
		}
	}
	
	public void SettingItemMeta(Player player, int type, String value)
	{
		if(player.isOp() == false)
		{
			s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;
		}
		if(player.getItemInHand().getType() == Material.AIR || player.getItemInHand().getTypeId() == 0 )
		{
			s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
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
				value = ChatColor.WHITE + value;
				itemMeta.setDisplayName(value);
				item.setItemMeta(itemMeta);
				player.setItemInHand(item);
				break;
			case 1:
				value = ChatColor.WHITE + value;
				if(itemMeta.hasLore() == false)
				{
					itemMeta.setLore(Arrays.asList(value));
				}
				else
				{
					Lore = itemMeta.getLore();
					Lore.add(Lore.size(), value);
					itemMeta.setLore(Lore);
				}
				//if(value.contains("대미지 : ") == true)
				//itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				item.setItemMeta(itemMeta);
				player.setItemInHand(item);
				break;
			case 2:
				if(itemMeta.hasLore() == false)
				{
					return;
				}
				else
				{
					Lore = itemMeta.getLore();
					for(int repeat = 0; repeat < 5; repeat++)
					for(int count=0;count<Lore.size();count++)
					{
						Lore.remove(count);
					}
					itemMeta.setLore(Lore);
				}
				item.setItemMeta(itemMeta);
				player.setItemInHand(item);
				break;
			}
		}
	}
}