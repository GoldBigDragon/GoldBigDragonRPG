package GBD.GoldBigDragon_Advanced.Event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace
{
	public void BlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		if(event.getItemInHand().hasItemMeta() == true && player.isOp() == false)
		{
			event.setCancelled(true);
			return;
		}
		if(event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("Dungeon")==true
				&&event.getPlayer().isOp()==false)
		{
			event.setCancelled(true);
		}

		GBD.GoldBigDragon_Advanced.ETC.Area A = new GBD.GoldBigDragon_Advanced.ETC.Area();
		String[] Area = A.getAreaName(event.getBlock());
		if(Area != null)
		if(A.getAreaOption(Area[0], (char) 5) == false && player.isOp() == false)
		{
			event.setCancelled(true);
			new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : " + ChatColor.YELLOW + Area[1] + ChatColor.RED + " 지역 에서는 블록 설치가 불가능합니다!");
			return;
		}
	}
}
