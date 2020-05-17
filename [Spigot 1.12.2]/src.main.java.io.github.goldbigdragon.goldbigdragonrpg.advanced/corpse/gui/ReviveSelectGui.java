package corpse.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import corpse.CorpseAPI;
import effect.SoundEffect;
import main.MainServerOption;
import util.GuiUtil;
import util.YamlLoader;

public class ReviveSelectGui extends GuiUtil{

	private String uniqueCode = "��0��0��9��0��0��r";
	private YamlLoader configYaml = new YamlLoader();
	
	public void openReviveSelectGui(Player player)
	{
		if(!player.getOpenInventory().getTitle().equals("��0��0��9��0��0��r��0�ൿ�Ҵ� ���� -��Ȱ ��� ����-"))
		{
	        LivingEntity entity = (LivingEntity) player;
	        entity.setCollidable(false);
			Inventory inv = Bukkit.createInventory(null, 27, uniqueCode + "��0�ൿ�Ҵ� ���� -��Ȱ ��� ����-");
		  	configYaml.getConfig("config.yml");

		  	setNormal("Death.Spawn_Home.SetHealth", "100%");
		  	setNormal("Death.Spawn_Home.PenaltyEXP", "10%");
		  	setNormal("Death.Spawn_Home.PenaltyMoney", "0%");

		  	setNormal("Death.Spawn_Here.SetHealth", "1%");
		  	setNormal("Death.Spawn_Here.PenaltyEXP", "15%");
		  	setNormal("Death.Spawn_Here.PenaltyMoney", "10%");
		  	
		  	setNormal("Death.Spawn_Help.SetHealth", "1%");
		  	setNormal("Death.Spawn_Help.PenaltyEXP", "5%");
		  	setNormal("Death.Spawn_Help.PenaltyMoney", "0%");
		  	
		  	setNormal("Death.Spawn_Item.SetHealth", "100%");
		  	setNormal("Death.Spawn_Item.PenaltyEXP", "0%");
		  	setNormal("Death.Spawn_Item.PenaltyMoney", "0%");
		  	
		  	try
		  	{
				if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited()==null||main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited().equals("null"))
					removeFlagStack("��6��l[����� �������� ��Ȱ]", 345,0,1,Arrays.asList("��7�ֱ� �湮�� ������ �����ϴ�.","��7�� ����� ������ ���,","��8"+ player.getLocation().getWorld().getName()+"���忡 ������","��7�⺻ ���� �������� ��Ȱ�մϴ�.","","��a + "+configYaml.getString("Death.Spawn_Home.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Home.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Home.PenaltyMoney")+" ����"), 10, inv);
				else
					removeFlagStack("��6��l[����� �������� ��Ȱ]", 345,0,1,Arrays.asList("��e"+ main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited()+"��7���� ��Ȱ�մϴ�.","","��a + "+configYaml.getString("Death.Spawn_Home.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Home.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Home.PenaltyMoney")+" ����"), 10, inv);
		  	}
		  	catch(NullPointerException e)
		  	{
		  		removeFlagStack("��6��l[����� �������� ��Ȱ]", 345,0,1,Arrays.asList("��7�ֱ� �湮�� ������ �����ϴ�.","��7�� ����� ������ ���,","��8"+ player.getLocation().getWorld().getName()+"���忡 ������","��7�⺻ ���� �������� ��Ȱ�մϴ�.","","��a + "+configYaml.getString("Death.Spawn_Home.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Home.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Home.PenaltyMoney")+" ����"), 10, inv);
		  	}
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP()<0)
				removeFlagStack("��c��l[�ٽ� �Ͼ��]", 166,0,1,Arrays.asList("��7����ġ�� �����Ͽ� ���ڸ�","��7��Ȱ�� �Ұ��� �մϴ�."), 12, inv);
			else if(configYaml.getBoolean("Death.DistrictDirectRevive"))
				removeFlagStack("��c��l[�ٽ� �Ͼ��]", 166,0,1,Arrays.asList("��7���ڸ� ��Ȱ�� �Ұ����մϴ�."), 12, inv);
			else
				removeFlagStack("��c��l[�ٽ� �Ͼ��]", 2266,0,1,Arrays.asList("��7������ ���� �ٽ� �Ͼ�ϴ�.","��7���� ���´� ����ϱ� ����ϴ�.","","��a + "+configYaml.getString("Death.Spawn_Here.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Here.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Here.PenaltyMoney")+" ����"), 12, inv);
			
			ItemStack item = configYaml.getItemStack("Death.RescueItem");
			
			if(item == null)
				removeFlagStack("��c��l[������ ��ٸ���]", 397,3,1,Arrays.asList("��7�ٸ� ����� ������ ��û�մϴ�.","��7������ ����� �ִ��� ���캸����.","","��a + "+configYaml.getString("Death.Spawn_Help.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Help.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Help.PenaltyMoney")+" ����"), 14, inv);
			else
				removeFlagStack("��c��l[������ ��ٸ���]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList("��7�ٸ� ����� ������ ��û�մϴ�.","��7������ ����� �ִ��� ���캸����.","","��a + "+configYaml.getString("Death.Spawn_Help.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Help.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Help.PenaltyMoney")+" ����"), 14, inv);
			
			item = configYaml.getItemStack("Death.ReviveItem");
			if(item == null)
				removeFlagStack("��3��l[��Ȱ�� ���]", 399,0,1,Arrays.asList("��7���ڸ� ��Ȱ �������� ����մϴ�.","","��a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" ����"), 16, inv);
			else if(!item.hasItemMeta())
				removeFlagStack("��3��l[��Ȱ�� ���]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList("��7���ڸ� ��Ȱ �������� ����մϴ�.","","��a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" ����"), 16, inv);
			else if(!item.getItemMeta().hasDisplayName())
				removeFlagStack("��3��l[��Ȱ�� ���]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList("��7���ڸ� ��Ȱ �������� ����մϴ�.","","��a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" ����"), 16, inv);
			else
				removeFlagStack("��3��l["+item.getItemMeta().getDisplayName()+"��3��l ���]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList("��7���ڸ� ��Ȱ �������� ����մϴ�.","","��a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" �����","��c - ����ġ "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" ����","��c - ������ "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" ����"), 16, inv);
			player.openInventory(inv);	
		}
	}
	
	private void setNormal(String path, String normalValue)
	{
	  	if(configYaml.getString(path)==null)
	  	{
  			configYaml.set(path, normalValue);
  			configYaml.saveConfig();
  			return;
	  	}
	  	if(configYaml.getString(path).contains("%"))
	  	{
	  		try
	  		{
	  			byte value = Byte.parseByte(configYaml.getString(path).replace("%", ""));
	  			if(value > 100||value<0)
	  			{
		  			configYaml.set(path, normalValue);
		  			configYaml.saveConfig();
	  			}
	  		}
	  		catch(Exception e)
	  		{
	  			configYaml.set(path, normalValue);
	  			configYaml.saveConfig();
	  		}
	  	}
	}
	
	public void reviveSelectClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		CorpseAPI capi = new CorpseAPI();
		if(slot == 10)//�������� ��Ȱ
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			capi.reviveAtLastVisitedArea(player);
			capi.removeCorpse(player.getName());
    		new otherplugins.NoteBlockApiMain().Stop(player);
		}
		else if(slot == 12)//���ڸ����� ��Ȱ
		{
			if(event.getCurrentItem().getTypeId()==166)
			{
				SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
				return;
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				capi.removeCorpse(player.getName());
				capi.reviveAtDeadPoint(player);
	    		new otherplugins.NoteBlockApiMain().Stop(player);
			}
		}
		else if(slot == 14)//���� ��û
		{
			if(MainServerOption.partyJoiner.containsKey(player))
			{
				Player[] partyMember = MainServerOption.party.get(MainServerOption.partyJoiner.get(player)).getMember();
				for(int count = 0; count < partyMember.length; count++)
					if(player != partyMember[count])
					{
						SoundEffect.playSound(partyMember[count], Sound.ENTITY_VILLAGER_DEATH, 0.4F, 0.5F);
						partyMember[count].sendMessage("��d[���� ��û] : ��e"+player.getName()+"��d������ ���� ���� ��û�� ���Խ��ϴ�! (���� : "+player.getLocation().getWorld().getName() + ", XYZ : " + (int)(player.getLocation().getX())+","+(int)(player.getLocation().getY())+","+(int)(player.getLocation().getZ())+")");
					}
				SoundEffect.playSound(player, Sound.ENTITY_WITHER_SKELETON_STEP, 1.0F, 1.0F);
				player.sendMessage("��d[���� ��û] : ��Ƽ ����鿡�� ���� ��û ��ȣ�� ���½��ϴ�!");
			}
			else
			{
			  	YamlLoader friendYaml = new YamlLoader();
				friendYaml.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
				if( ! friendYaml.contains("Name"))
				{
					SoundEffect.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
					player.sendMessage("��d[���� ��û] : ���� ��û ��ȣ�� ���� �� �ִ� ģ���� �����ϴ�!");
				}
				else
				{
					String[] friendsList = friendYaml.getConfigurationSection("Friends").getKeys(false).toArray(new String[0]);
					if(friendsList.length == 0)
					{
						SoundEffect.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
						player.sendMessage("��d[���� ��û] : ���� ��û ��ȣ�� ���� �� �ִ� ģ���� �����ϴ�!");
					}
					else
					{
						boolean exitFriend = false;
						for(int count = 0; count < friendsList.length; count++)
						{
							Player friend = Bukkit.getPlayer(friendsList[count]);
							if(friend!=null)
								if(friend.isOnline())
								{
									SoundEffect.playSound(friend, Sound.ENTITY_VILLAGER_DEATH, 0.4F, 0.5F);
									friend.sendMessage("��d[���� ��û] : ��e"+player.getName()+"��d������ ���� ���� ��û�� ���Խ��ϴ�! (���� : "+player.getLocation().getWorld().getName() + ", XYZ : " + (int)(player.getLocation().getX())+","+(int)(player.getLocation().getY())+","+(int)(player.getLocation().getZ())+")");
									exitFriend = true;
								}
						}
						if(exitFriend)
						{
							SoundEffect.playSound(player, Sound.ENTITY_SKELETON_STEP, 1.0F, 1.0F);
							player.sendMessage("��d[���� ��û] : ������ ģ���鿡�� ���� ��û ��ȣ�� ���½��ϴ�!");
						}
						else
						{
							SoundEffect.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
							player.sendMessage("��d[���� ��û] : ���� ��û ��ȣ�� ���� �� �ִ� ģ���� �����ϴ�!");
						}
					}
				}
			}
		}
		else if(slot == 16)//��Ȱ�� ���
		{
			configYaml.getConfig("config.yml");
			ItemStack item = configYaml.getItemStack("Death.ReviveItem");
			if(item == null)
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("��c[SYSTEM] : ��Ȱ �������� ���� ��Ȱ�� �� �����ϴ�!");
				openReviveSelectGui(player);
			}
			else
			{
				if(new util.PlayerUtil().deleteItem(player, item, item.getAmount()))
				{
					capi.removeCorpse(player.getName());
					player.setGameMode(GameMode.SURVIVAL);
					player.closeInventory();
					Location l = player.getLocation();
					l.add(0, 1, 0);
					player.teleport(l);
					for(int countta=0;countta<210;countta++)
						new effect.ParticleEffect().PL(player.getLocation(), org.bukkit.Effect.SMOKE, new util.NumericUtil().RandomNum(0, 14));
					SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.5F, 1.8F);
		    		new otherplugins.NoteBlockApiMain().Stop(player);
		    		capi.penalty(player, configYaml.getString("Death.Spawn_Item.SetHealth"), configYaml.getString("Death.Spawn_Item.PenaltyEXP"), configYaml.getString("Death.Spawn_Item.PenaltyMoney"));
					return;
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("��c[SYSTEM] : ��Ȱ �������� �����Ͽ� ��Ȱ�� �� �����ϴ�!");
					return;
				}
			}
		}
		player.closeInventory();
		return;
	}
	
}
