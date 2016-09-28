package GBD_RPG.Admin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Admin_Command
{
	public void onCommand(Player player, String[] args, String string)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();

		if(player.isOp() == true)
		{
			if(string.compareTo("테스트")==0)
			{
				player.sendMessage(""+Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getPlayerRootJob());
			}
			else if(string.compareTo("테스트2")==0)
			{
				//여분의 공간
			}
			else if(string.compareTo("오피박스")==0)
			{
				new UserData_Object().clearAll(player);
				s.SP(player, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Main(player,(byte) 1);
			}
			else if(string.compareTo("타입추가")==0)
			{
				if(args.length!=1)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /타입추가 [새로운 아이템 타입]");
					s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.7F);
				}
				else
				{
				  	YamlManager Target = YC.getNewConfig("Item/CustomType.yml");
			  		Target.set("["+args[0]+"]", 0);
			  		Target.saveConfig();
			  		player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 새로운 아이템 타입 추가 완료!  " + ChatColor.WHITE+args[0]);
					s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.7F);
				}
			}
			else if(string.compareTo("엔티티제거")==0)
			{
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /엔티티제거 [1~10000]");
					s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
			    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
			    short amount = 0;
			    for(short count = 0; count < entities.size(); count++)
			    {
			    	if(entities.get(count).getType() != EntityType.PLAYER &&entities.get(count).getType() != EntityType.ITEM_FRAME&&entities.get(count).getType()!= EntityType.ARMOR_STAND)
			    	{
			    		entities.get(count).remove();
			    		amount = (short) (amount+1);
			    	}
			    }
			    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"마리의 엔티티를 삭제하였습니다!");
			}
			else if(string.compareTo("아이템제거")==0)
			{
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /아이템제거 [1~10000]");
					s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
			    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
			    short amount = 0;
			    for(short count = 0; count < entities.size(); count++)
			    {
			    	if(entities.get(count).getType() == EntityType.DROPPED_ITEM)
			    	{
			    		entities.get(count).remove();
			    		amount = (short) (amount+1);
			    	}
			    }
			    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"개의 아이템을 삭제하였습니다!");
			}
			else if(string.compareTo("강제철거")==0)
			{
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /강제철거 [1~10000]");
					s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
			    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
			    short amount = 0;
			    for(short count = 0; count < entities.size(); count++)
			    {
			    	if(entities.get(count).getType() != EntityType.PLAYER)
			    	{
			    		entities.get(count).remove();
			    		amount = (short) (amount+1);
			    	}
			    }
			    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"마리의 엔티티를 강제 철거하였습니다!");
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
	}
}
