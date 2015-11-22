package GBD.GoldBigDragon_Advanced.Event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class BlockPlace
{
	public void BlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		if(event.getItemInHand().hasItemMeta() == true && player.isOp() == false)
		{
			YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
			YamlManager Config =GUI_YC.getNewConfig("config.yml");
			if(Config.getBoolean("Server.CustomBlockPlace") == false)
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
		if(player.isOp()==false)
			EXPexceptionBlock(event.getBlock().getTypeId(),event.getBlock().getLocation());
		return;
	}

	public void EXPexceptionBlock(int id,Location loc)
	{
		if((id >= 14&&id <= 17)||id==21||id==56||id==129||id==73||id==153)
		{
			YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
			YamlManager EXPexceptionBlockList =GUI_YC.getNewConfig("EXPexceptionBlock.yml");
			String Location = ((int)loc.getX()+"_"+(int)loc.getY()+"_"+(int)loc.getZ());
			EXPexceptionBlockList.set(loc.getWorld().getName()+"."+id+"."+Location+".1", null);
			EXPexceptionBlockList.saveConfig();
		}
		return;
	}

}