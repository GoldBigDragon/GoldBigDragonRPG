package customitem.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import customitem.CustomItemAPI;
import effect.SoundEffect;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class EquipItemForgingGui extends GuiUtil{

	private String uniqueCode = "��0��0��3��0��1��r";
	
	public void itemForgingGui(Player player, int number)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "��0������ �� ����");
		String itemName = itemYaml.getString(number+".DisplayName");
		short itemID = (short) itemYaml.getInt(number+".ID");
		byte itemData = (byte) itemYaml.getInt(number+".Data");

		String type = "";
		String grade = "";
		CustomItemAPI ciapi = new CustomItemAPI();
		
		for(int counter =0;counter < 13 - itemYaml.getString(number+".Type").length();counter++ )
			type = type +" ";
		type = type +itemYaml.getString(number+".Type");
		grade = itemYaml.getString(number+".Grade");
		
		removeFlagStack("��3[    �����    ]", 145,0,1,null, 9, inv);
		removeFlagStack("��3[    �����    ]", 145,0,1,null, 10, inv);
		removeFlagStack("��3[    �����    ]", 145,0,1,null, 11, inv);
		removeFlagStack("��3[    �����    ]", 145,0,1,null, 18, inv);
		removeFlagStack("��3[    �����    ]", 145,0,1,null, 20, inv);
		removeFlagStack("��3[    �����    ]", 145,0,1,null, 27, inv);
		removeFlagStack("��3[    �����    ]", 145,0,1,null, 28, inv);
		removeFlagStack("��3[    �����    ]", 145,0,1,null, 29, inv);
		removeFlagStack(itemName, itemID,itemData,1,Arrays.asList(ciapi.loreCreater(number)), 19, inv);
		
		removeFlagStack("��3[    ���� ����    ]", 421,0,1,Arrays.asList("��f������ ����â��","��f�����մϴ�.","","��f[    ���� ����    ]","       "+ itemYaml.getString(number+".ShowType"),""), 37, inv);
		removeFlagStack("��3[    �̸� ����    ]", 421,0,1,Arrays.asList("��f�������� �̸���","��f�����մϴ�.",""), 13, inv);
		removeFlagStack("��3[    ���� ����    ]", 386,0,1,Arrays.asList("��f�������� ������","��f�����մϴ�.",""), 14, inv);
		removeFlagStack("��3[    Ÿ�� ����    ]", 61,0,1,Arrays.asList("��f�������� Ÿ����","��f�����մϴ�.","","��f[    ���� Ÿ��    ]",type,""), 15, inv);
		removeFlagStack("��3[    ��� ����    ]", 266,0,1,Arrays.asList("��f�������� �����","��f�����մϴ�.","","��f[    ���� ���    ]","       "+grade,""), 16, inv);
		removeFlagStack("��3[        ID        ]", 405,0,1,Arrays.asList("��f�������� ID����","��f�����մϴ�.",""), 22, inv);
		removeFlagStack("��3[       DATA       ]", 336,0,1,Arrays.asList("��f�������� DATA����","��f�����մϴ�.",""), 23, inv);
		removeFlagStack("��3[       "+main.MainServerOption.damage+"       ]", 267,0,1,Arrays.asList("��f�������� "+main.MainServerOption.damage+"��","��f�����մϴ�.",""), 24, inv);
		removeFlagStack("��3[     "+main.MainServerOption.magicDamage+"     ]", 403,0,1,Arrays.asList("��f�������� "+main.MainServerOption.magicDamage+"��","��f�����մϴ�.",""), 25, inv);
		removeFlagStack("��3[        ���        ]", 307,0,1,Arrays.asList("��f�������� ������","��f�����մϴ�.",""), 31, inv);
		removeFlagStack("��3[        ��ȣ        ]", 306,0,1,Arrays.asList("��f�������� ��ȣ��","��f�����մϴ�.",""), 32, inv);
		removeFlagStack("��3[      ���� ���      ]", 311,0,1,Arrays.asList("��f�������� ���� ��","��f�����մϴ�.",""), 33, inv);
		removeFlagStack("��3[      ���� ��ȣ      ]", 310,0,1,Arrays.asList("��f�������� ���� ��ȣ��","��f�����մϴ�.",""), 34, inv);
		removeFlagStack("��3[        ����        ]", 399,0,1,Arrays.asList("��f�������� ���ʽ� ������","��f�����մϴ�.",""), 40, inv);
		removeFlagStack("��3[       ������       ]", 145,2,1,Arrays.asList("��f�������� ��������","��f�����մϴ�.","","��c[0 ������ �Ϲ� ������ ������ ���]",""), 41, inv);
		removeFlagStack("��3[        ����        ]", 145,0,1,Arrays.asList("��f�������� �ִ� ���� Ƚ����","��f�����մϴ�.","","��c[0 ������ ���� �Ұ���]",""), 42, inv);
		removeFlagStack("��3[         ��         ]", 381,0,1,Arrays.asList("��f�������� �ִ� ������","��f�����մϴ�.","","��f�ִ� "+itemYaml.getInt(number+".MaxSocket")+" ��","","��c[0 ������ �� ���� �Ұ���]",""), 43, inv);
		removeFlagStack("��3[      ���� ����      ]", 166,0,1,Arrays.asList("��f������ ������ ������","��f�ɾ�Ӵϴ�.",""), 49, inv);
		removeFlagStack("��3[      ���� ����      ]", 397,3,1,Arrays.asList("��f������ ������ ������","��f�ɾ�Ӵϴ�.","��c[�� Ŭ���� ����]",""), 50, inv);
		
		boolean removeDelay = itemYaml.getBoolean(number+".NoAttackDelay");
		if(removeDelay)
			removeFlagStack("��c[  ���� ������ ����      ]", 166,0,1,Arrays.asList("��f������ �������� �����մϴ�.",""), 51, inv);
		else
			removeFlagStack("��3[  ���� ������ ����      ]", 347,0,1,Arrays.asList("��f���� �����̿� ����", "��f�������� �޶����ϴ�.",""), 51, inv);
		
		removeFlagStack("��f��l���� ���", 323,0,1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�."), 45, inv);
		removeFlagStack("��f��l�ݱ�", 324,0,1,Arrays.asList("��7â�� �ݽ��ϴ�.","��0"+number), 53, inv);
		player.openInventory(inv);
	}

	public void itemForgingClick(InventoryClickEvent event)
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
			if(slot == 45)//���� ȭ��
				new EquipItemListGui().itemListGui(player, 0);
			else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29))||event.getSlot()==51)
			{
				int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			  	YamlLoader itemYaml = new YamlLoader();
				itemYaml.getConfig("Item/ItemList.yml");
				if(itemYaml.getString(itemnumber+"")==null)
				{
					player.sendMessage("��c[SYSTEM] : �ٸ� OP�� �������� �����Ͽ� �ݿ����� �ʾҽ��ϴ�!");
					SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				}
				if(slot==37)//�� Ÿ��
				{
					if(itemYaml.getString(itemnumber+".ShowType").contains("[���]"))
						itemYaml.set(itemnumber+".ShowType","��e[�÷�]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[�÷�]"))
						itemYaml.set(itemnumber+".ShowType","��d[��ȣ]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[��ȣ]"))
						itemYaml.set(itemnumber+".ShowType","��9[�з�]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[�з�]"))
						itemYaml.set(itemnumber+".ShowType","��f[���]");
					itemYaml.saveConfig();
					itemForgingGui(player, itemnumber);
				}
				else if(slot==15)//Ÿ�� ����
				{
					YamlLoader customTypeYaml = new YamlLoader();
				  	customTypeYaml.getConfig("Item/CustomType.yml");
				  	String[] weaponCustomType = customTypeYaml.getKeys().toArray(new String[0]);
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				  	if(weaponCustomType.length == 0)
				  	{
						if(itemYaml.getString(itemnumber+".Type").contains("[���� ����]"))
							itemYaml.set(itemnumber+".Type","��c[�Ѽ� ��]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[�Ѽ� ��]"))
							itemYaml.set(itemnumber+".Type","��c[��� ��]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[��� ��]"))
							itemYaml.set(itemnumber+".Type","��c[����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[����]"))
							itemYaml.set(itemnumber+".Type","��c[��]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[��]"))
							itemYaml.set(itemnumber+".Type","��2[���Ÿ� ����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[���Ÿ� ����]"))
							itemYaml.set(itemnumber+".Type","��2[Ȱ]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[Ȱ]"))
							itemYaml.set(itemnumber+".Type","��2[����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[����]"))
							itemYaml.set(itemnumber+".Type","��3[���� ����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[���� ����]"))
							itemYaml.set(itemnumber+".Type","��3[����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[����]"))
							itemYaml.set(itemnumber+".Type","��3[������]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[������]"))
							itemYaml.set(itemnumber+".Type","��f[��]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[��]"))
							itemYaml.set(itemnumber+".Type","��7[��Ÿ]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[��Ÿ]"))
							itemYaml.set(itemnumber+".Type","��c[���� ����]");
				  	}
				  	else
				  	{
						if(itemYaml.getString(itemnumber+".Type").contains("[���� ����]"))
							itemYaml.set(itemnumber+".Type","��c[�Ѽ� ��]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[�Ѽ� ��]"))
							itemYaml.set(itemnumber+".Type","��c[��� ��]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[��� ��]"))
							itemYaml.set(itemnumber+".Type","��c[����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[����]"))
							itemYaml.set(itemnumber+".Type","��c[��]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[��]"))
							itemYaml.set(itemnumber+".Type","��2[���Ÿ� ����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[���Ÿ� ����]"))
							itemYaml.set(itemnumber+".Type","��2[Ȱ]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[Ȱ]"))
							itemYaml.set(itemnumber+".Type","��2[����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[����]"))
							itemYaml.set(itemnumber+".Type","��3[���� ����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[���� ����]"))
							itemYaml.set(itemnumber+".Type","��3[����]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[����]"))
							itemYaml.set(itemnumber+".Type","��3[������]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[������]"))
							itemYaml.set(itemnumber+".Type","��f[��]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[��]"))
							itemYaml.set(itemnumber+".Type","��7[��Ÿ]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[��Ÿ]"))
							itemYaml.set(itemnumber+".Type","��f"+weaponCustomType[0]);
						else
						{
							for(int count = 0; count < weaponCustomType.length; count++)
							{
								if((itemYaml.getString(itemnumber+".Type").contains(weaponCustomType[count])))
								{
									if(count+1 == weaponCustomType.length)
										itemYaml.set(itemnumber+".Type","��c[���� ����]");
									else
										itemYaml.set(itemnumber+".Type","��f"+weaponCustomType[(count+1)]);
									break;
								}
							}
						}
				  	}
					itemYaml.saveConfig();
					itemForgingGui(player, itemnumber);
				}
				else if(slot==16)//��� ����
				{
					if(itemYaml.getString(itemnumber+".Grade").contains("[�Ϲ�]"))
						itemYaml.set(itemnumber+".Grade","��a[���]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[���]"))
						itemYaml.set(itemnumber+".Grade","��9[����]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[����]"))
						itemYaml.set(itemnumber+".Grade","��e[����]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[����]"))
						itemYaml.set(itemnumber+".Grade","��5[����]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[����]"))
						itemYaml.set(itemnumber+".Grade","��6[����]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[����]"))
						itemYaml.set(itemnumber+".Grade","��4��l[��6��l�ʡ�2��l����1��l]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("��"))
						itemYaml.set(itemnumber+".Grade","��7[�ϱ�]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[�ϱ�]"))
						itemYaml.set(itemnumber+".Grade","��f[�Ϲ�]");
					itemYaml.saveConfig();
					itemForgingGui(player, itemnumber);
				}
				else if(slot==43)//�ִ� ���� ����
				{
					if(itemYaml.getInt(itemnumber+".MaxSocket") < 5)
						itemYaml.set(itemnumber+".MaxSocket",itemYaml.getInt(itemnumber+".MaxSocket")+1);
					else if(itemYaml.getInt(itemnumber+".MaxSocket") == 5)
							itemYaml.set(itemnumber+".MaxSocket", 0);
					itemYaml.saveConfig();
					itemForgingGui(player, itemnumber);
				}
				else if(slot==50)//���� ����
				{
					if(event.isLeftClick())
						new RestrictJobSelectGui().restrictJobSelectGui(player, (short)0, itemnumber);
					else if(event.isRightClick())
					{
						SoundEffect.playSound(player, Sound.ITEM_SHIELD_BREAK, 0.8F, 1.0F);
						itemYaml.set(itemnumber+".JOB", "����");
						itemYaml.saveConfig();
						itemForgingGui(player, itemnumber);
					}
				}
				else if(slot==51)//���� ������
				{
					boolean removeDelay = itemYaml.getBoolean(itemnumber+".NoAttackDelay");
					itemYaml.set(itemnumber+".NoAttackDelay", !removeDelay);
					itemYaml.saveConfig();
					SoundEffect.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.8F, 1.0F);
					itemForgingGui(player, itemnumber);
				}
				else
				{
					UserDataObject u = new UserDataObject();
					player.closeInventory();
					u.setType(player, "Item");
					u.setInt(player, (byte)3, itemnumber);
					if(slot == 13 || slot == 14)
					{
						if(slot == 13)
						{
							player.sendMessage("��3[������] : �������� �̸��� �Է��� �ּ���!");
							u.setString(player, (byte)1, "DisplayName");
						}
						else
						{
							player.sendMessage("��3[������] : �������� ������ �Է��� �ּ���!");
							player.sendMessage("��6%enter%��f - ���� ��� ���� -");
							u.setString(player, (byte)1, "Lore");
						}
						player.sendMessage("��f��l&l ��0&0 ��1&1 ��2&2 "+
						"��3&3 ��4&4 ��5&5 " +
								"��6&6 ��7&7 ��8&8 " +
						"��9&9 ��a&a ��b&b ��c&c " +
								"��d&d ��e&e ��f&f");
					}
					else if(slot == 22)//ID����
					{
						player.sendMessage("��3[������] : �������� ID ���� �Է��� �ּ���!");
						u.setString(player, (byte)1, "ID");
					}
					else if(slot == 23)//DATA����
					{
						player.sendMessage("��3[������] : �������� DATA ���� �Է��� �ּ���!");
						u.setString(player, (byte)1, "Data");
					}
					else if(slot == 24)//����� ����
					{
						player.sendMessage("��3[������] : �������� �ּ� "+main.MainServerOption.damage+"�� �Է��� �ּ���!");
						player.sendMessage("��3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MinDamage");
					}
					else if(slot == 25)//���� ����� ����
					{
						player.sendMessage("��3[������] : �������� �ּ� "+main.MainServerOption.magicDamage+"�� �Է��� �ּ���!");
						player.sendMessage("��3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MinMaDamage");
					}
					else if(slot == 31)//����
					{
						player.sendMessage("��3[������] : �������� ������ �Է��� �ּ���!");
						player.sendMessage("��3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "DEF");
					}
					else if(slot == 32)//��ȣ����
					{
						player.sendMessage("��3[������] : �������� ��ȣ�� �Է��� �ּ���!");
						player.sendMessage("��3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "Protect");
					}
					else if(slot == 33)//���� ��� ����
					{
						player.sendMessage("��3[������] : �������� ���� ������ �Է��� �ּ���!");
						player.sendMessage("��3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaDEF");
					}
					else if(slot == 34)//���� ��ȣ ����
					{
						player.sendMessage("��3[������] : �������� ���� ��ȣ�� �Է��� �ּ���!");
						player.sendMessage("��3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaProtect");
					}
					else if(slot == 40)//���� ���ʽ�
					{
						player.sendMessage("��3[������] : �������� ����� ���ʽ��� �Է��� �ּ���!");
						player.sendMessage("��3(-127 ~ 127)");
						u.setString(player, (byte)1, "HP");
					}
					else if(slot == 41)//������ ����
					{
						player.sendMessage("��3[������] : �������� �ִ� �������� �Է��� �ּ���!");
						player.sendMessage("��3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaxDurability");
					}
					else if(slot == 42)//���� Ƚ�� ����
					{
						player.sendMessage("��3[������] : �������� �ִ� ���� Ƚ���� �Է��� �ּ���!");
						player.sendMessage("��3(0 ~ 127)");
						u.setString(player, (byte)1, "MaxUpgrade");
					}
					else if(slot == 49)//���� ����
					{
						player.sendMessage("��3[������] : �������� ���� ������ �Է� �� �ּ���!");
						player.sendMessage("��3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MinLV");
					}
				}
			}
		}
	}
}
