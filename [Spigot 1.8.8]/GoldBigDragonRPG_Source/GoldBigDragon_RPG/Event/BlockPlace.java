package GoldBigDragon_RPG.Event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class BlockPlace
{
	public void BlockPlaceE(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		if(event.getItemInHand().hasItemMeta() == true && player.isOp() == false)
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager Config =YC.getNewConfig("config.yml");
			if(Config.getBoolean("Server.CustomBlockPlace") == false)
				event.setCancelled(true);
			return;
		}
		if(event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			if(event.getBlock().getType() != Material.TORCH)
				event.setCancelled(true);
			return;
		}

		GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
		String[] Area = A.getAreaName(event.getBlock());
		if(Area != null)
		if(A.getAreaOption(Area[0], (char) 5) == false && player.isOp() == false)
		{
			event.setCancelled(true);
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
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
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager EXPexceptionBlockList =YC.getNewConfig("EXPexceptionBlock.yml");
			String Location = ((int)loc.getX()+"_"+(int)loc.getY()+"_"+(int)loc.getZ());
			EXPexceptionBlockList.set(loc.getWorld().getName()+"."+id+"."+Location+".1", null);
			EXPexceptionBlockList.saveConfig();
		}
		return;
	}

}