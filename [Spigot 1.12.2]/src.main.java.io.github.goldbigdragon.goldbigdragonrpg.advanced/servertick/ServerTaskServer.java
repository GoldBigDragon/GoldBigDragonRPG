package servertick;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import effect.SoundEffect;

public class ServerTaskServer
{
	public void createStructureMain(Long utc)
	{
		ServerTickObject stso = ServerTickMain.Schedule.get(utc);
		
		if(stso.getInt((byte)3)==0)
		{
			if(ServerTickMain.Schedule.get(utc).getCount()==0)
				createCommandBlock(new Location(Bukkit.getServer().getWorld(stso.getString((byte)1)), stso.getInt((byte)0), stso.getInt((byte)1), stso.getInt((byte)2)));

			String cmd = new structure.StructureMain().getCMD(stso.getString((byte)0), stso.getCount(),stso.getString((byte)2),stso.getString((byte)3));

			if(cmd.equals("null"))
			{
				ServerTickMain.Schedule.remove(utc);
				ServerTickMain.ServerTask="null";

				Location commandLoc = new Location(Bukkit.getServer().getWorld(stso.getString((byte)1)), stso.getInt((byte)0), stso.getInt((byte)1), stso.getInt((byte)2));
				Block originalBlock = commandLoc.getBlock();
				originalBlock.setTypeId(stso.getInt((byte)6));
				originalBlock.setData((byte) stso.getInt((byte)7));
				setBlock(commandLoc, originalBlock);

				if(stso.getString((byte)0).equals("CF"))
				{
					Object[] e = originalBlock.getLocation().getWorld().getNearbyEntities(originalBlock.getLocation(), 2, 2, 2).toArray();
					Entity entity;
					for(int count = 0; count < e.length; count++) {
						entity = (Entity)e[count];
						if(entity.getType()==EntityType.ARMOR_STAND &&
							entity.getCustomName() != null &&
							entity.getCustomName().equals(stso.getString((byte)2))) {
							((Entity)e[count]).setFireTicks(25565);
						}
					}
				}
				ServerTickMain.Schedule.remove(utc);
				return;
			}
			else
			{
				Location commandBlockLoc = new Location(Bukkit.getServer().getWorld(stso.getString((byte)1)), stso.getInt((byte)0), stso.getInt((byte)1), stso.getInt((byte)2));
				Block command = commandBlockLoc.getBlock();
			    CommandBlock commandBlock = (CommandBlock)command.getState();
			    commandBlock.setCommand(cmd);
			    commandBlock.update();
			    commandBlockLoc.setY(commandBlockLoc.getY()-1);
			    createRedStone(commandBlockLoc);
			    SoundEffect.playSoundLocation(commandBlockLoc, Sound.BLOCK_STONE_STEP, 1.0F, 1.0F);
			    
				stso.setInt((byte)3, 1);
				ServerTickMain.Schedule.remove(utc);
				for(int count=0;count < 32767;count++)
				{
					if( ! ServerTickMain.Schedule.containsKey(utc+(100+count)))
					{
						ServerTickMain.Schedule.put(utc+(100+count), stso);
						break;
					}
				}
			}
		}
		else
		{
			Location redStoneLoc = new Location(Bukkit.getServer().getWorld(stso.getString((byte)1)), stso.getInt((byte)0), stso.getInt((byte)1), stso.getInt((byte)2));
			redStoneLoc.setY(redStoneLoc.getY()-1);
			Block originalBlock = redStoneLoc.getBlock();
			originalBlock.setTypeId(stso.getInt((byte)6));
			originalBlock.setData((byte) stso.getInt((byte)7));
			setBlock(redStoneLoc, originalBlock);
			stso.setInt((byte)3, 0);
			stso.setCount(stso.getCount()+1);
			ServerTickMain.Schedule.remove(utc);
			for(int count=0; count < 32767;count++)
			{
				if( ! ServerTickMain.Schedule.containsKey(utc+(100+count)))
				{
					ServerTickMain.Schedule.put(utc+(100+count), stso);
					break;
				}
			}
		}
	}
	
	public void createCommandBlock(Location loc)
	{
		Block command = loc.getBlock();
		command.setType(Material.COMMAND);
		command.setData((byte)0);
	}
	
	public void createRedStone(Location loc)
	{
		Block redStone = loc.getBlock();
		redStone.setType(Material.REDSTONE_BLOCK);
		redStone.setData((byte)0);
	}

	public void setBlock(Location loc, Block block)
	{
		Block targetBlock = loc.getBlock();
		targetBlock.setType(block.getType());
		targetBlock.setData(block.getData());
	}
}