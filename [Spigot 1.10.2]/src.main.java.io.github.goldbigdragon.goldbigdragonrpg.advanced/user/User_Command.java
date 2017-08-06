package user;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import effect.SoundEffect;
import main.Main_ServerOption;
import servertick.ServerTick_Main;
import util.YamlLoader;



public class User_Command
{
	public void onCommand(Player player, String[] args, String string)
	{
		
		switch(string)
		{
		case "수락":
		{
			if(ServerTick_Main.PlayerTaskList.containsKey(player.getName()))
			{
				long UTC = Long.parseLong(ServerTick_Main.PlayerTaskList.get(player.getName()));
				servertick.ServerTick_Object STSO = ServerTick_Main.Schedule.get(UTC);
				String taskType = STSO.getType();
				
				switch(taskType)
				{
					case "P_EC"://Player Exchange
					{
						if(STSO.getString((byte)1).compareTo(player.getName())==0)
						{
							SoundEffect.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.7F);
							SoundEffect.SP(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.7F);
							new user.Equip_GUI().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)0)), Bukkit.getServer().getPlayer(STSO.getString((byte)1)),null,false,false);
							new user.Equip_GUI().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Bukkit.getServer().getPlayer(STSO.getString((byte)0)),null,false,false);
							ServerTick_Main.PlayerTaskList.remove(ServerTick_Main.Schedule.get(UTC).getString((byte)0));
							ServerTick_Main.PlayerTaskList.remove(ServerTick_Main.Schedule.get(UTC).getString((byte)1));
							ServerTick_Main.Schedule.remove(UTC);
						}
					}
					break;
				}
			}
		}
		return;
		case "거절":
		{
			if(ServerTick_Main.PlayerTaskList.containsKey(player.getName()))
			{
				servertick.ServerTick_Object STSO = ServerTick_Main.Schedule.get(Long.parseLong(ServerTick_Main.PlayerTaskList.get(player.getName())));
				String taskType = STSO.getType();
				switch(taskType)
				{
					case "P_EC"://Player Exchange
					{
						if(STSO.getString((byte)1).compareTo(player.getName())==0)
						{
							servertick.ServerTask_Player SP = new servertick.ServerTask_Player();
							SP.Cancel(STSO.getTick(), (short) 0);
						}
					}
					break;
				}
			}
		}
		return;
		case "돈":
  		{
  			long Money = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
  			if(args.length == 0)
  			{
			 	SoundEffect.SP(player, org.bukkit.Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
			 	player.sendMessage(ChatColor.YELLOW + "[현재 소지 금액] " + ChatColor.WHITE+ChatColor.BOLD +"" +Money + " "+Main_ServerOption.Money);
			 	player.sendMessage(ChatColor.GOLD + "/돈 꺼내기 [금액]"+ChatColor.WHITE+" 해당 금액 만큼 돈을 아이템으로 꺼냅니다.");
			 	player.sendMessage(ChatColor.GOLD + "/돈 랭킹 [닉네임]"+ChatColor.WHITE+" 해당 플레이어의 랭킹을 확인합니다.");
  				if(player.isOp()==true)
	  				player.sendMessage(ChatColor.AQUA + "/돈 주기 [금액] [플레이어]"+ChatColor.WHITE+" 해당 금액 만큼 플레이어에게 돈을 줍니니다."+"§b§l(관리자)");
  			}
  			else if(args.length == 2&&args[0].compareTo("꺼내기")==0)
  			{
				try
				{
					if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
					{
						if(Money >= Integer.parseInt(args[1]))
						{
							for(int count = 0; count < 36;count++)
							{
								if(player.getInventory().getItem(count)==null)
								{
									int money = Integer.parseInt(args[1]);
									main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * money, 0, false);
									ItemStack Icon;
									if(money <= 50)
										Icon = new MaterialData(Main_ServerOption.Money1ID, (byte) Main_ServerOption.Money1DATA).toItemStack();
									else if(money <= 100)
										Icon = new MaterialData(Main_ServerOption.Money2ID, (byte) Main_ServerOption.Money2DATA).toItemStack();
									else if(money <= 1000)
										Icon = new MaterialData(Main_ServerOption.Money3ID, (byte) Main_ServerOption.Money3DATA).toItemStack();
									else if(money <= 10000)
										Icon = new MaterialData(Main_ServerOption.Money4ID, (byte) Main_ServerOption.Money4DATA).toItemStack();
									else if(money <= 50000)
										Icon = new MaterialData(Main_ServerOption.Money5ID, (byte) Main_ServerOption.Money5DATA).toItemStack();
									else
										Icon = new MaterialData(Main_ServerOption.Money6ID, (byte) Main_ServerOption.Money6DATA).toItemStack();
									Icon.setAmount(1);
									ItemMeta Icon_Meta = Icon.getItemMeta();
									Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
									Icon_Meta.setDisplayName(Main_ServerOption.Money);
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
									Icon_Meta.setLore(Arrays.asList(ChatColor.YELLOW+"[돈]             "+ChatColor.WHITE+"[일반]",ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+Main_ServerOption.Money,ChatColor.GRAY+"("+MoneyString.toString()+" "+ChatColor.stripColor(Main_ServerOption.Money)+")","",ChatColor.GRAY+"우 클릭시 내 계좌로",ChatColor.GRAY+"입금됩니다."));
									Icon.setItemMeta(Icon_Meta);
									player.getInventory().addItem(Icon);
									SoundEffect.SP(player, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
									player.sendMessage(ChatColor.GREEN + "[System] : "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+Main_ServerOption.Money+ChatColor.GREEN+" 을(를) 꺼냈습니다!");
									return;
								}
							}
							SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
							player.sendMessage(ChatColor.RED + "[System] : 인벤토리 공간이 부족합니다!");
						}
						else
						{
							player.sendMessage(ChatColor.RED + "[SYSTEM] : 현재 보유 금액을 초과하는 값입니다!");
							SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						}
					}
					else
					{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+1+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+100000000+ChatColor.RED+" 이하의 숫자를 입력하세요!");
						SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					}
				}
				catch(NumberFormatException e)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+1+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+100000000+ChatColor.RED+")");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
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
								for(int count = 0; count < 36;count++)
								{
									if(target.getInventory().getItem(count)==null)
									{
										int money = Integer.parseInt(args[1]);
										ItemStack Icon;
										if(money <= 50)
											Icon = new MaterialData(Main_ServerOption.Money1ID, (byte) Main_ServerOption.Money1DATA).toItemStack();
										else if(money <= 100)
											Icon = new MaterialData(Main_ServerOption.Money2ID, (byte) Main_ServerOption.Money2DATA).toItemStack();
										else if(money <= 1000)
											Icon = new MaterialData(Main_ServerOption.Money3ID, (byte) Main_ServerOption.Money3DATA).toItemStack();
										else if(money <= 10000)
											Icon = new MaterialData(Main_ServerOption.Money4ID, (byte) Main_ServerOption.Money4DATA).toItemStack();
										else if(money <= 50000)
											Icon = new MaterialData(Main_ServerOption.Money5ID, (byte) Main_ServerOption.Money5DATA).toItemStack();
										else
											Icon = new MaterialData(Main_ServerOption.Money6ID, (byte) Main_ServerOption.Money6DATA).toItemStack();
										Icon.setAmount(1);
										ItemMeta Icon_Meta = Icon.getItemMeta();
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
										Icon_Meta.setDisplayName(Main_ServerOption.Money);
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
										Icon_Meta.setLore(Arrays.asList(ChatColor.YELLOW+"[돈]             "+ChatColor.WHITE+"[일반]",ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+Main_ServerOption.Money,ChatColor.GRAY+"("+MoneyString.toString()+" "+ChatColor.stripColor(Main_ServerOption.Money)+")","",ChatColor.GRAY+"우 클릭시 내 계좌로",ChatColor.GRAY+"입금됩니다."));
										Icon.setItemMeta(Icon_Meta);
										target.getInventory().addItem(Icon);
										SoundEffect.SP(target, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
										target.sendMessage(ChatColor.GREEN + "[System] : 관리자로 부터 "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+Main_ServerOption.Money+ChatColor.GREEN+" 을(를) 받았습니다!");
										SoundEffect.SP(player, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
										player.sendMessage(ChatColor.GREEN + "[System] : "+target.getName()+"에게 "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+Main_ServerOption.Money+ChatColor.GREEN+" 을(를) 주었습니다!");
										return;
									}
								}
								SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
								player.sendMessage(ChatColor.RED + "[System] : 인벤토리 공간이 부족합니다!");
								return;
							}
							else
							{
								player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+1+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+100000000+ChatColor.RED+" 이하의 숫자를 입력하세요!");
								SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
							}
						}
						catch(NumberFormatException e)
						{
							player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+1+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+100000000+ChatColor.RED+")");
							SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						}
	  				}
	  				else
	  				{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
						SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  				}
  				}
  				else
  				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
  				}
  			}
  			else if(args[0].compareTo("랭킹")==0)
  			{
    		  	YamlLoader YAML = new YamlLoader();
    			YAML.getConfig("Ranking/money.yml");
				if(YAML.contains("Rank")&&YAML.getConfigurationSection("Rank").getKeys(false).size()>0)
				{
	  				if(args.length == 2)
	  				{
	  					if(YAML.contains("NameSet."+args[1]))
	  	  					player.sendMessage("§e§l┼─[§a§l"+ (YAML.getInt("NameSet."+args[1]+".Rank")+1) +"§e§l] §f§l"+args[1]+ " §6("+YAML.getLong("NameSet."+args[1]+".Money")+")");
	  	  				else
	  	  				{
	  						player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 플레이어에 대한 자료가 없습니다!");
	  						SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  	  				}
	  				}
	  				else
	  				{
  	  					int rankSize = YAML.getConfigurationSection("Rank").getKeys(false).size();
  	  					if(rankSize > 5)
  	  						rankSize = 5;
  	  					player.sendMessage("§e§l┌────────────[Ranking]────────────┐");
	  	  				for(int count = 0 ; count < rankSize; count++)
	  	  					player.sendMessage("§e§l├─[§a§l"+ (count+1) +"§e§l] §f§l"+YAML.getString("Rank."+count+".Name") + " §6("+YAML.getLong("Rank."+count+".Money")+")");
  	  					player.sendMessage("§e§l└────────────[Ranking]────────────┘");
	  				}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 랭킹을 불러 올 수가 없습니다! 잠시 후 다시 시도 해 주시길 바랍니다.");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				}
  			}
  			else
  			{
  				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.8F);
  				player.sendMessage(ChatColor.GOLD + "/돈"+ChatColor.WHITE+" 현재 자신이 보유한 금액을 확인합니다.");
  				player.sendMessage(ChatColor.GOLD + "/돈 꺼내기 [금액]"+ChatColor.WHITE+" 해당 금액 만큼 돈을 아이템으로 꺼냅니다.");
			 	player.sendMessage(ChatColor.GOLD + "/돈 랭킹 [닉네임]"+ChatColor.WHITE+" 해당 플레이어의 랭킹을 확인합니다.");
  				if(player.isOp()==true)
	  				player.sendMessage(ChatColor.AQUA + "/돈 주기 [금액] [플레이어]"+ChatColor.WHITE+" 해당 금액 만큼 플레이어에게 돈을 줍니니다."+"§b§l(관리자)");
  			}
  		}
  		return;
		}
	}
}
