package GoldBigDragon_RPG.Command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class SystemCommand
{
	public void onCommand(Player player, String[] args, String string)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		switch(string)
		{
			case "타입추가":
				if(player.isOp() == true)
				{
					if(args.length!=1)
					{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : /타입추가 [새로운 아이템 타입]");
						s.SP(player, org.bukkit.Sound.ORB_PICKUP, 1.0F, 1.7F);
					}
					else
					{
					  	YamlManager Target = YC.getNewConfig("Item/CustomType.yml");
				  		Target.set("["+args[0]+"]", 0);
				  		Target.saveConfig();
				  		player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 새로운 아이템 타입 추가 완료!  " + ChatColor.WHITE+args[0]);
						s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.7F);
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 1.0F, 1.7F);
				}
				return;
			case "수락":
			{
				if(ServerTickMain.PlayerTaskList.containsKey(player.getName()))
				{
					long UTC = Long.parseLong(ServerTickMain.PlayerTaskList.get(player.getName()));
					GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = ServerTickMain.Schedule.get(UTC);
					String taskType = STSO.getType();
					
					switch(taskType)
					{
						case "P_EC"://Player Exchange
						{
							if(STSO.getString((byte)1).compareTo(player.getName())==0)
							{
								s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.7F);
								s.SP(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Sound.HORSE_ARMOR, 1.0F, 1.7F);
								new GoldBigDragon_RPG.GUI.EquipGUI().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)0)), Bukkit.getServer().getPlayer(STSO.getString((byte)1)),null,false,false);
								new GoldBigDragon_RPG.GUI.EquipGUI().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Bukkit.getServer().getPlayer(STSO.getString((byte)0)),null,false,false);
								ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)0));
								ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)1));
								ServerTickMain.Schedule.remove(UTC);
							}
						}
						break;
					}
				}
			}
			return;
			case "거절":
			{
				if(ServerTickMain.PlayerTaskList.containsKey(player.getName()))
				{
					GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = ServerTickMain.Schedule.get(Long.parseLong(ServerTickMain.PlayerTaskList.get(player.getName())));
					String taskType = STSO.getType();
					switch(taskType)
					{
						case "P_EC"://Player Exchange
						{
							if(STSO.getString((byte)1).compareTo(player.getName())==0)
							{
								GoldBigDragon_RPG.ServerTick.ServerTask_Player SP = new GoldBigDragon_RPG.ServerTick.ServerTask_Player();
								SP.Cancel(STSO.getTick(), (short) 0);
							}
						}
						break;
					}
				}
			}
			return;
			case"테스트":
			if(player.isOp() == true)
			{
				
			}
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			return;
			case"테스트2":
			if(player.isOp() == true)
			{
			}
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			return;
			case "돈":
	  		{
	  			long Money = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
	  			if(args.length == 0)
	  			{
				 	s.SP(player, org.bukkit.Sound.LAVA_POP, 0.8F, 1.8F);
				 	player.sendMessage(ChatColor.YELLOW + "[현재 소지 금액] " + ChatColor.WHITE+ChatColor.BOLD +"" +Money + " "+ServerOption.Money);
				 	player.sendMessage(ChatColor.GOLD + "/돈 꺼내기 [금액]"+ChatColor.WHITE+" 해당 금액 만큼 돈을 아이템으로 꺼냅니다.");
	  				if(player.isOp()==true)
		  				player.sendMessage(ChatColor.AQUA + "/돈 주기 [금액] [플레이어]"+ChatColor.WHITE+" 해당 금액 만큼 플레이어에게 돈을 줍니니다."+ChatColor.AQUA+""+ChatColor.BOLD+"(관리자)");
	  			}
	  			else if(args.length == 2&&args[0].compareTo("꺼내기")==0)
	  			{
					try
					{
						if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
						{
							if(Money >= Integer.parseInt(args[1]))
							{
								for(byte count = 0; count < 36;count++)
								{
									if(player.getInventory().getItem(count)==null)
									{
										int money = Integer.parseInt(args[1]);
										GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * money, 0, false);
										ItemStack Icon;
										if(money <= 50)
											Icon = new MaterialData(ServerOption.Money1ID, (byte) ServerOption.Money1DATA).toItemStack();
										else if(money <= 100)
											Icon = new MaterialData(ServerOption.Money2ID, (byte) ServerOption.Money2DATA).toItemStack();
										else if(money <= 1000)
											Icon = new MaterialData(ServerOption.Money3ID, (byte) ServerOption.Money3DATA).toItemStack();
										else if(money <= 10000)
											Icon = new MaterialData(ServerOption.Money4ID, (byte) ServerOption.Money4DATA).toItemStack();
										else if(money <= 50000)
											Icon = new MaterialData(ServerOption.Money5ID, (byte) ServerOption.Money5DATA).toItemStack();
										else
											Icon = new MaterialData(ServerOption.Money6ID, (byte) ServerOption.Money6DATA).toItemStack();
										Icon.setAmount(1);
										ItemMeta Icon_Meta = Icon.getItemMeta();
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
										Icon_Meta.setDisplayName(ServerOption.Money);
										StringBuffer MoneyString = new StringBuffer();
										short Mok = 0;
										if(money==100000000||money==10000000||money==1000000||
											money==100000||money==10000||money==1000||money==100||
											money==10)
										{
											switch(money)
											{
												case 100000000:
													MoneyString.append("1억");
													break;
												case 10000000:
													MoneyString.append("1천만");
													break;
												case 1000000:
													MoneyString.append("1백만");
													break;
												case 100000:
													MoneyString.append("1십만");
													break;
												case 10000:
													MoneyString.append("1만");
													break;
												case 1000:
													MoneyString.append("1천");
													break;
												case 100:
													MoneyString.append("1백");
													break;
												case 10:
													MoneyString.append("1십");
													break;
											}
										}
										else
										{
											if(money >= 10000000)
											{
												Mok = (short) (money / 10000000);
												MoneyString.append(Mok+"천");
												money = money-(Mok*10000000);
											}
											if(money >= 1000000)
											{
												Mok = (short) (money / 1000000);
												MoneyString.append(Mok+"백");
												money = money-(Mok*1000000);
											}
											if(money >= 100000)
											{
												Mok = (short) (money / 100000);
												MoneyString.append(Mok+"십");
												money = money-(Mok*100000);
											}
											if(money >= 10000)
											{
												Mok = (short) (money / 10000);
												MoneyString.append(Mok+"만 ");
												money = money-(Mok*10000);
											}
											else if(Integer.parseInt(args[1]) >= 10000)
											{
												MoneyString.append("만 ");
											}
											if(money >= 1000)
											{
												Mok = (short) (money / 1000);
												MoneyString.append(Mok+"천");
												money = money-(Mok*1000);
											}
											if(money >= 100)
											{
												Mok = (short) (money / 100);
												MoneyString.append(Mok+"백");
												money = money-(Mok*100);
											}
											if(money >= 10)
											{
												Mok = (short) (money / 10);
												MoneyString.append(Mok+"십");
												money = money-(Mok*10);
											}
											if(money >= 1)
											{
												Mok = (short) (money / 1);
												MoneyString.append(Mok);
											}
										}
										Icon_Meta.setLore(Arrays.asList(ChatColor.YELLOW+"[돈]             "+ChatColor.WHITE+"[일반]",ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money,ChatColor.GRAY+"("+MoneyString.toString()+" "+ChatColor.stripColor(ServerOption.Money)+")","",ChatColor.GRAY+"우 클릭시 내 계좌로",ChatColor.GRAY+"입금됩니다."));
										Icon.setItemMeta(Icon_Meta);
										player.getInventory().addItem(Icon);
										s.SP(player, org.bukkit.Sound.LAVA_POP, 2.0F, 1.7F);
										player.sendMessage(ChatColor.GREEN + "[System] : "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money+ChatColor.GREEN+" 을(를) 꺼냈습니다!");
										return;
									}
								}
								s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
								player.sendMessage(ChatColor.RED + "[System] : 인벤토리 공간이 부족합니다!");
							}
							else
							{
								player.sendMessage(ChatColor.RED + "[SYSTEM] : 현재 보유 금액을 초과하는 값입니다!");
								s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+1+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+100000000+ChatColor.RED+" 이하의 숫자를 입력하세요!");
							s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						}
					}
					catch(NumberFormatException e)
					{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+1+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+100000000+ChatColor.RED+")");
						s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					}
	  			}
	  			else if(args.length == 3&&args[0].compareTo("주기")==0&&player.isOp())
	  			{
	  				if(Bukkit.getServer().getPlayer(args[2]) != null)
	  				{
	  					Player target = Bukkit.getServer().getPlayer(args[2]);
		  				if(target.isOnline())
		  				{
							try
							{
								if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
								{
									for(byte count = 0; count < 36;count++)
									{
										if(target.getInventory().getItem(count)==null)
										{
											int money = Integer.parseInt(args[1]);
											ItemStack Icon;
											if(money <= 50)
												Icon = new MaterialData(ServerOption.Money1ID, (byte) ServerOption.Money1DATA).toItemStack();
											else if(money <= 100)
												Icon = new MaterialData(ServerOption.Money2ID, (byte) ServerOption.Money2DATA).toItemStack();
											else if(money <= 1000)
												Icon = new MaterialData(ServerOption.Money3ID, (byte) ServerOption.Money3DATA).toItemStack();
											else if(money <= 10000)
												Icon = new MaterialData(ServerOption.Money4ID, (byte) ServerOption.Money4DATA).toItemStack();
											else if(money <= 50000)
												Icon = new MaterialData(ServerOption.Money5ID, (byte) ServerOption.Money5DATA).toItemStack();
											else
												Icon = new MaterialData(ServerOption.Money6ID, (byte) ServerOption.Money6DATA).toItemStack();
											Icon.setAmount(1);
											ItemMeta Icon_Meta = Icon.getItemMeta();
											Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
											Icon_Meta.setDisplayName(ServerOption.Money);
											StringBuffer MoneyString = new StringBuffer();
											short Mok = 0;
											if(money==100000000||money==10000000||money==1000000||
												money==100000||money==10000||money==1000||money==100||
												money==10)
											{
												switch(money)
												{
													case 100000000:
														MoneyString.append("1억");
														break;
													case 10000000:
														MoneyString.append("1천만");
														break;
													case 1000000:
														MoneyString.append("1백만");
														break;
													case 100000:
														MoneyString.append("1십만");
														break;
													case 10000:
														MoneyString.append("1만");
														break;
													case 1000:
														MoneyString.append("1천");
														break;
													case 100:
														MoneyString.append("1백");
														break;
													case 10:
														MoneyString.append("1십");
														break;
												}
											}
											else
											{
												if(money >= 10000000)
												{
													Mok = (short) (money / 10000000);
													MoneyString.append(Mok+"천");
													money = money-(Mok*10000000);
												}
												if(money >= 1000000)
												{
													Mok = (short) (money / 1000000);
													MoneyString.append(Mok+"백");
													money = money-(Mok*1000000);
												}
												if(money >= 100000)
												{
													Mok = (short) (money / 100000);
													MoneyString.append(Mok+"십");
													money = money-(Mok*100000);
												}
												if(money >= 10000)
												{
													Mok = (short) (money / 10000);
													MoneyString.append(Mok+"만 ");
													money = money-(Mok*10000);
												}
												else if(Integer.parseInt(args[1]) >= 10000)
												{
													MoneyString.append("만 ");
												}
												if(money >= 1000)
												{
													Mok = (short) (money / 1000);
													MoneyString.append(Mok+"천");
													money = money-(Mok*1000);
												}
												if(money >= 100)
												{
													Mok = (short) (money / 100);
													MoneyString.append(Mok+"백");
													money = money-(Mok*100);
												}
												if(money >= 10)
												{
													Mok = (short) (money / 10);
													MoneyString.append(Mok+"십");
													money = money-(Mok*10);
												}
												if(money >= 1)
												{
													Mok = (short) (money / 1);
													MoneyString.append(Mok);
												}
											}
											Icon_Meta.setLore(Arrays.asList(ChatColor.YELLOW+"[돈]             "+ChatColor.WHITE+"[일반]",ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money,ChatColor.GRAY+"("+MoneyString.toString()+" "+ChatColor.stripColor(ServerOption.Money)+")","",ChatColor.GRAY+"우 클릭시 내 계좌로",ChatColor.GRAY+"입금됩니다."));
											Icon.setItemMeta(Icon_Meta);
											target.getInventory().addItem(Icon);
											s.SP(target, org.bukkit.Sound.LAVA_POP, 2.0F, 1.7F);
											target.sendMessage(ChatColor.GREEN + "[System] : 관리자로 부터 "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money+ChatColor.GREEN+" 을(를) 받았습니다!");
											s.SP(player, org.bukkit.Sound.LAVA_POP, 2.0F, 1.7F);
											player.sendMessage(ChatColor.GREEN + "[System] : "+target.getName()+"에게 "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money+ChatColor.GREEN+" 을(를) 주었습니다!");
											return;
										}
									}
									s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
									player.sendMessage(ChatColor.RED + "[System] : 인벤토리 공간이 부족합니다!");
									return;
								}
								else
								{
									player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+1+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+100000000+ChatColor.RED+" 이하의 숫자를 입력하세요!");
									s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
								}
							}
							catch(NumberFormatException e)
							{
								player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+1+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+100000000+ChatColor.RED+")");
								s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
							}
		  				}
		  				else
		  				{
							player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
							s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  				}
	  				}
	  				else
	  				{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
						s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				}
	  			}
	  			else
	  			{
	  				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 0.8F, 1.8F);
	  				player.sendMessage(ChatColor.GOLD + "/돈"+ChatColor.WHITE+" 현재 자신이 보유한 금액을 확인합니다.");
	  				player.sendMessage(ChatColor.GOLD + "/돈 꺼내기 [금액]"+ChatColor.WHITE+" 해당 금액 만큼 돈을 아이템으로 꺼냅니다.");
	  				if(player.isOp()==true)
		  				player.sendMessage(ChatColor.AQUA + "/돈 주기 [금액] [플레이어]"+ChatColor.WHITE+" 해당 금액 만큼 플레이어에게 돈을 줍니니다."+ChatColor.AQUA+""+ChatColor.BOLD+"(관리자)");
	  			}
	  		}
	  		return;
			case "강제철거":
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /강제철거 [1~10000]");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				if(player.isOp() == true)
				{
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
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				return;
			case "엔티티제거":
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /엔티티제거 [1~10000]");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				if(player.isOp() == true)
				{
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
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				return;
			case "아이템제거":
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /아이템제거 [1~10000]");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				if(player.isOp() == true)
				{
				    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
				    short amount = 0;
				    for(short count = 0; count < entities.size(); count++)
				    {
				    	if(entities.get(count).getType() == EntityType.DROPPED_ITEM)
				    	{
				    		entities.get(count).remove();
				    		amount++;
				    	}
				    }
				    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"개의 아이템을 삭제하였습니다!");
				}
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				return;
		}
		return;
	}
}
