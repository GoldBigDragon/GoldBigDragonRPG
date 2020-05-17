package customitem.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import admin.OPboxGui;
import customitem.CustomItemAPI;
import effect.SoundEffect;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;
import util.GuiUtil;
import util.YamlLoader;

public class EquipItemListGui extends GuiUtil{

	private String uniqueCode = "��0��0��3��0��0��r";
	
	
	public ItemStack tunning(ItemStack nowItem, String attributeName, String name, double amount) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(nowItem);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new net.minecraft.server.v1_12_R1.NBTTagCompound();
		NBTTagList am = (net.minecraft.server.v1_12_R1.NBTTagList) compound.get("AttributeModifiers");
		if (am == null)
		    am = new NBTTagList();
		
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.set("AttributeName", new NBTTagString(attributeName));
		nbtTag.set("Name", new NBTTagString(name));
		nbtTag.set("Amount", new NBTTagDouble(amount));
		nbtTag.set("Operation", new NBTTagInt(0));
		nbtTag.set("UUIDLeast", new NBTTagInt(894654));
		nbtTag.set("UUIDMost", new NBTTagInt(2872));
		
		am.add(nbtTag);
		compound.set("AttributeModifiers", am);
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	
	public void itemListGui(Player player, int page)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "��0������ ��� : " + (page+1));

		int itemAmounts = itemYaml.getKeys().size();

		byte loc=0;
		CustomItemAPI ciapi = new CustomItemAPI();
		for(int count = page*45; count < itemAmounts;count++)
		{
			int number = ((page*45)+loc);
			if(count > itemAmounts || loc >= 45) break;
			String itemName = itemYaml.getString(number+".DisplayName");
			int itemId = itemYaml.getInt(number+".ID");
			int itemData = itemYaml.getInt(number+".Data");
			boolean removeAttackDelay = itemYaml.getBoolean(number+".NoAttackDelay");
			removeFlagStack(itemName, itemId, itemData, 1,Arrays.asList(ciapi.loreCreater(number)), loc, inv);
			if(removeAttackDelay) {
				inv.setItem(loc, tunning(inv.getItem(loc), "generic.attackSpeed", "generic.attackSpeed", 32767));
			}
			loc++;
		}
		
		if(itemAmounts-(page*44)>45)
		removeFlagStack("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		removeFlagStack("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 48, inv);

		removeFlagStack("��f��l�� ������", 145,0,1,Arrays.asList("��7���ο� �������� �����մϴ�."), 49, inv);
		
		removeFlagStack("��f��l���� ���", 323,0,1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�."), 45, inv);
		removeFlagStack("��f��l�ݱ�", 324,0,1,Arrays.asList("��7â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void itemListClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//������
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page = (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//���� ���
				new OPboxGui().opBoxGuiMain(player,(byte) 1);
			else if(slot == 48)//���� ������
				itemListGui(player,page-1);
			else if(slot == 50)//���� ������
				itemListGui(player,page+1);
			else
			{
			  	YamlLoader itemYaml = new YamlLoader();
				itemYaml.getConfig("Item/ItemList.yml");
				if(slot == 49)//�� ������
				{
					int itemCounter = itemYaml.getKeys().size();
					itemYaml.set(itemCounter+".ShowType","��f[���]");
					itemYaml.set(itemCounter+".ID",267);
					itemYaml.set(itemCounter+".Data",0);
					itemYaml.set(itemCounter+".DisplayName","��f��� ���� ������ ��");
					itemYaml.set(itemCounter+".Lore","��f��� ������� �����̴�.%enter%��f������ ����������...");
					itemYaml.set(itemCounter+".Type","��c[���� ����]");
					itemYaml.set(itemCounter+".Grade","��f[�Ϲ�]");
					itemYaml.set(itemCounter+".MinDamage",1);
					itemYaml.set(itemCounter+".MaxDamage",7);
					itemYaml.set(itemCounter+".MinMaDamage",0);
					itemYaml.set(itemCounter+".MaxMaDamage",0);
					itemYaml.set(itemCounter+".DEF",0);
					itemYaml.set(itemCounter+".Protect",0);
					itemYaml.set(itemCounter+".MaDEF",0);
					itemYaml.set(itemCounter+".MaProtect",0);
					itemYaml.set(itemCounter+".Durability",256);
					itemYaml.set(itemCounter+".MaxDurability",256);
					itemYaml.set(itemCounter+".STR",0);
					itemYaml.set(itemCounter+".DEX",0);
					itemYaml.set(itemCounter+".INT",0);
					itemYaml.set(itemCounter+".WILL",0);
					itemYaml.set(itemCounter+".LUK",0);
					itemYaml.set(itemCounter+".Balance",10);
					itemYaml.set(itemCounter+".Critical",5);
					itemYaml.set(itemCounter+".MaxUpgrade",5);
					itemYaml.set(itemCounter+".MaxSocket",3);
					itemYaml.set(itemCounter+".HP",0);
					itemYaml.set(itemCounter+".MP",0);
					itemYaml.set(itemCounter+".JOB","����");
					itemYaml.set(itemCounter+".MinLV",0);
					itemYaml.set(itemCounter+".MinRLV",0);
					itemYaml.set(itemCounter+".MinSTR",0);
					itemYaml.set(itemCounter+".MinDEX",0);
					itemYaml.set(itemCounter+".MinINT",0);
					itemYaml.set(itemCounter+".MinWILL",0);
					itemYaml.set(itemCounter+".MinLUK",0);
					itemYaml.saveConfig();
					new EquipItemForgingGui().itemForgingGui(player, itemCounter);
				}
				else
				{
					int number = ((page*45)+event.getSlot());
					if(event.isLeftClick()&&!event.isShiftClick())
					{
						player.sendMessage("��a[SYSTEM] : Ŭ���� �������� ���� �޾ҽ��ϴ�!");
						player.getInventory().addItem(event.getCurrentItem());
					}
					if(event.isLeftClick()&&event.isShiftClick())
						new EquipItemForgingGui().itemForgingGui(player, number);
					else if(event.isRightClick()&&event.isShiftClick())
					{
						short acount =  (short) (itemYaml.getKeys().toArray().length-1);
						for(int counter = (short) number;counter <acount;counter++)
							itemYaml.set(counter+"", itemYaml.get((counter+1)+""));
						itemYaml.removeKey(acount+"");
						itemYaml.saveConfig();
						itemListGui(player, page);
					}
				}
			}
		}
	}
}
