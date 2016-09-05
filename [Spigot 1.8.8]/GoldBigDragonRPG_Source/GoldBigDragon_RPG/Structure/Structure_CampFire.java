package GoldBigDragon_RPG.Structure;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.GUI.GUIutil;

public class Structure_CampFire extends GUIutil
{

	public void CampFireMainGUI(Player player, String BoardCode)
	{
		BoardCode = BoardCode.replace("§", "&");
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +""+ "모닥불");
		Stack2(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"불 끄기", 326, 0, 1, Arrays.asList(ChatColor.WHITE+"모닥불의 불을 끕니다."), 3, inv);
		Stack2(ChatColor.RED+""+ChatColor.BOLD+"불 지피기", 259, 0, 1, Arrays.asList(ChatColor.WHITE+"모닥불에 불을 지핍니다.","",ChatColor.YELLOW+"[막대기 10개 필요]",ChatColor.BLACK+BoardCode), 5, inv);
		player.openInventory(inv);
		return;
	}
	
	public String CreateCampFire(int LineNumber, String StructureCode, byte Direction)
	{
		if(LineNumber <= 36)
			return "/summon ArmorStand ~0 ~0.36 ~0 {CustomName:\""+StructureCode+"\",Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1,Rotation:["+(LineNumber*10)+"f,0.0f],Equipment:[{id:stick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[40f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		else if(LineNumber == 37)
			return "/summon ArmorStand ~0 ~0.56 ~0 {CustomName:\""+StructureCode+"\",Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1}";
		else if(LineNumber == 38)
			return "/summon ArmorStand ~0 ~0.56 ~0 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1}";
		else
			return "null";
	}
	
	public void CampFireGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();
		String CampFireName = ChatColor.stripColor(event.getInventory().getItem(5).getItemMeta().getLore().get(3)).replace("&", "§");
		
		if(event.getSlot()==3||event.getSlot()==5)
		{
			if(event.getSlot()==5)
			{
				ItemStack item = new MaterialData(280, (byte) 0).toItemStack(1);
				if(new GoldBigDragon_RPG.Util.PlayerUtil().deleteItem(player, item, 10)==false)
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[SYSTEM] : 불을 지피기 위해 필요한 막대기 개수가 모자랍니다!");
					return;
				}
				else
					s.SP(player, Sound.FIRE_IGNITE, 1.0F, 1.8F);
			}
			else
				s.SP(player, Sound.FIZZ, 1.0F, 1.0F);
				
			Object[] e = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 6, 6, 6).toArray();
			for(int count = 0; count < e.length; count++)
				if(((Entity)e[count]).getType()==EntityType.ARMOR_STAND)
					if(((Entity)e[count]).getCustomName()!=null)
						if(((Entity)e[count]).getCustomName().compareTo(CampFireName)==0)
							{
								if(event.getSlot()==3)
									((Entity)e[count]).setFireTicks(0);
								if(event.getSlot()==5)
									((Entity)e[count]).setFireTicks(25565);
							}
		}
	}
}
