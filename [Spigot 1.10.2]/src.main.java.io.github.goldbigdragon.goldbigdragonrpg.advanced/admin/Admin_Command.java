package admin;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import effect.SoundEffect;
import user.UserData_Object;
import util.YamlLoader;

public class Admin_Command
{
	public void onCommand(Player player, String[] args, String string)
	{
		if(player.isOp() == true)
		{
			if(string.compareTo("테스트")==0||string.compareTo("gbdtest")==0)
			{
				player.sendMessage("테스트1");
			}
			else if(string.compareTo("테스트2")==0||string.compareTo("gbdtest2")==0)
			{
				player.sendMessage("테스트2");
			}
			else if(string.compareTo("오피박스")==0||string.compareTo("opbox")==0)
			{
				new UserData_Object().clearAll(player);
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				new admin.OPbox_GUI().OPBoxGUI_Main(player,(byte) 1);
			}
			else if(string.compareTo("타입추가")==0||string.compareTo("gbdaddtype")==0)
			{
				if(args.length!=1)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /타입추가 [새로운 아이템 타입]");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.7F);
				}
				else
				{
				  	YamlLoader Target = new YamlLoader();
				  	Target.getConfig("Item/CustomType.yml");
			  		Target.set("["+args[0]+"]", 0);
			  		Target.saveConfig();
			  		player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 새로운 아이템 타입 추가 완료!  " + ChatColor.WHITE+args[0]);
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.7F);
				}
			}
			else if(string.compareTo("엔티티제거")==0||string.compareTo("gbdremoveentity")==0)
			{
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /엔티티제거 [1~10000]");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
			    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
			    short amount = 0;
			    for(int count = 0; count < entities.size(); count++)
			    {
			    	if(entities.get(count).getType() != EntityType.PLAYER &&entities.get(count).getType() != EntityType.ITEM_FRAME&&entities.get(count).getType()!= EntityType.ARMOR_STAND)
			    	{
			    		entities.get(count).remove();
			    		amount = (short) (amount+1);
			    	}
			    }
			    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"마리의 엔티티를 삭제하였습니다!");
			}
			else if(string.compareTo("아이템제거")==0||string.compareTo("gbdremoveitem")==0)
			{
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /아이템제거 [1~10000]");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
			    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
			    short amount = 0;
			    for(int count = 0; count < entities.size(); count++)
			    {
			    	if(entities.get(count).getType() == EntityType.DROPPED_ITEM)
			    	{
			    		entities.get(count).remove();
			    		amount = (short) (amount+1);
			    	}
			    }
			    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"개의 아이템을 삭제하였습니다!");
			}
			else if(string.compareTo("강제철거")==0||string.compareTo("gbdforceremove")==0)
			{
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /강제철거 [1~10000]");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
			    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
			    short amount = 0;
			    for(int count = 0; count < entities.size(); count++)
			    {
			    	if(entities.get(count).getType() != EntityType.PLAYER)
			    	{
			    		entities.get(count).remove();
			    		amount = (short) (amount+1);
			    	}
			    }
			    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"마리의 엔티티를 강제 철거하였습니다!");
			}
			else if(string.compareTo("스텟초기화권")==0||string.compareTo("gbdbacktothenewbie")==0)
			{
				ItemStack Icon = new MaterialData(340, (byte) 0).toItemStack(1);
				ItemMeta Icon_Meta = Icon.getItemMeta();
				Icon_Meta.setDisplayName("§2§3§4§3§3§l[스텟 초기화 주문서]");
				Icon_Meta.setLore(Arrays.asList("§a[주문서]",""));
				Icon.setItemMeta(Icon_Meta);
				if(args.length==1)
				{
	  				if(Bukkit.getServer().getPlayer(args[0]) != null)
	  				{
	  					Player target = Bukkit.getServer().getPlayer(args[0]);
		  				if(target.isOnline())
		  					new util.Util_Player().giveItemForce(target, Icon);
		  				else
		  				{
		  					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
		  					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  				}
	  				}
				}
				else
  					new util.Util_Player().giveItemForce(player, Icon);
			}
			else if(string.compareTo("경주")==0||string.compareTo("giveexp")==0||string.compareTo("경험치주기")==0)
			{
				if(args.length==2)
				{
  					Player target = Bukkit.getServer().getPlayer(args[0]);
	  				if(target.isOnline())
	  				{
	  					int EXP = 0;
	  					try
	  					{
	  						EXP = Integer.parseInt(args[1]);
	  					}
	  					catch(NumberFormatException e)
	  					{
	  						player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요!");
		  					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  					return;
	  					}
	  					main.Main_ServerOption.PlayerList.get(target.getUniqueId().toString()).addStat_MoneyAndEXP(0, EXP, true);
	  					player.sendMessage(ChatColor.GREEN+"[SYSTEM] : " + args[0] + "님에게 경험치 " + EXP + "을 지급하였습니다!");
	  				}
	  				else
	  				{
	  					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
	  					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  				}
				}
				else
				{
  					player.sendMessage(ChatColor.RED + "[SYSTEM] : /경주 [닉네임] [경험치]");
  					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				}
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
	}
}
