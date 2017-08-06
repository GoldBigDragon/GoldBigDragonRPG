package structure;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import effect.SoundEffect;
import user.UserData_Object;
import util.YamlLoader;



public class Structure_Chat
{
	public void PlayerChatrouter(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		UserData_Object u = new UserData_Object();
		if(u.getType(player).compareTo("Post")==0)
			PostChatting(event);
		else if(u.getType(player).compareTo("Board")==0)
			BoardChatting(event);
		else if(u.getType(player).compareTo("TradeBoard")==0)
			TradeBoardChatting(event);
		else if(u.getType(player).compareTo("CampFire")==0)
			CampFireChatting(event);
		
	}
	
	private void PostChatting(PlayerChatEvent event)
	{
		
		Player player = event.getPlayer();
		UserData_Object u = new UserData_Object();
		String Message = ChatColor.stripColor(event.getMessage());
		//Reciever NickName
		if(u.getString(player,(byte)0).compareTo("RN")==0)
		{
			String lower = Message.toLowerCase();
			if(lower.compareTo(ChatColor.stripColor(player.getName().toLowerCase()))==0)
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
				player.sendMessage(ChatColor.RED+"[우편] : 자기 자신에게는 보낼 수 없습니다!");
				return;
			}
				
			if(Bukkit.getPlayer(Message) != null)
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				u.setString(player, (byte)0, "Title");
				u.setString(player, (byte)1, Message);
				u.setTemp(player,"Structure");
				player.sendMessage(ChatColor.GREEN+"[우편] : 우편 제목을 입력 하세요.");
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
				player.sendMessage(ChatColor.RED+"[우편] : 해당 닉네임을 가진 플레이어가 없습니다!");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("Title")==0)
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			u.setString(player, (byte)0, "Memo");
			u.setString(player, (byte)2, event.getMessage());
			player.sendMessage(ChatColor.GREEN+"[우편] : 우편 내용을 입력 하세요.");
			u.setTemp(player,"Structure");
		}
		else if(u.getString(player,(byte)0).compareTo("Memo")==0)
		{
			SoundEffect.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.5F);
			u.setString(player, (byte)3,ChatColor.WHITE+event.getMessage());
			u.setItemStack(player, null);
			new Struct_PostBox().ItemPutterGUI(player);
		}
		else if(u.getString(player,(byte)0).compareTo("Value")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 2000000))
			{
				u.setInt(player, (byte)0, Integer.parseInt(event.getMessage()));
				new structure.Struct_PostBox().SendPost(player);
			}
			else
			{
				player.sendMessage(ChatColor.GREEN+"[우편] : 우편물 수령을 위한 대금을 입력 하세요.");
				u.setTemp(player,"Structure");
			}
		}
	}

	private void BoardChatting(PlayerChatEvent event)
	{
		
		Player player = event.getPlayer();
		UserData_Object u = new UserData_Object();
		String Message = ChatColor.stripColor(event.getMessage());
		
		//Board_PostTitle
		if(u.getString(player,(byte)0).compareTo("Title")==0)
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			u.setString(player, (byte)0, "Memo");
			u.setString(player, (byte)1, ChatColor.WHITE+event.getMessage());
			player.sendMessage(ChatColor.GREEN+"[게시판] : 게시글 내용을 입력 하세요.");
			u.setTemp(player,"Structure");
		}
		else if(u.getString(player,(byte)0).compareTo("Memo")==0)
		{
			SoundEffect.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.5F);
			u.setString(player, (byte)2,ChatColor.WHITE+event.getMessage());
			YamlLoader Board = new YamlLoader();
			Board.getConfig("Structure/"+u.getString(player, (byte)3)+".yml");
			Board.set("User."+Board.getInt("Post_Number")+".User", player.getName());
			Board.set("User."+Board.getInt("Post_Number")+".Title", u.getString(player, (byte)1));
			Board.set("User."+Board.getInt("Post_Number")+".Memo", u.getString(player, (byte)2));
			Board.set("User."+Board.getInt("Post_Number")+".UTC", new servertick.ServerTick_Main().nowUTC);
			Board.set("Post_Number", Board.getInt("Post_Number")+1);
			Board.saveConfig();
			new Struct_Board().BoardMainGUI(player, u.getString(player, (byte)3), (byte) 0);
			u.clearAll(player);
		}
		else if(u.getString(player,(byte)0).compareTo("Notice")==0)
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			u.setString(player, (byte)2,ChatColor.WHITE+event.getMessage());
			YamlLoader Board = new YamlLoader();
			Board.getConfig("Structure/"+u.getString(player, (byte)1)+".yml");
			Board.set("Notice", event.getMessage());
			Board.saveConfig();
			new Struct_Board().BoardSettingGUI(player, u.getString(player, (byte)1));
			u.clearAll(player);
		}
	}
	
	private void TradeBoardChatting(PlayerChatEvent event)
	{
		
		Player player = event.getPlayer();
		UserData_Object u = new UserData_Object();
		String Message = ChatColor.stripColor(event.getMessage());
		
		if(u.getString(player,(byte)0).compareTo("Notice")==0)
		{
			if(askOX(Message, player)==1)
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				if(u.getInt(player, (byte)0)==1)//거래 타입이 판매일 경우
				{
					new Struct_TradeBoard().SelectSellItemGUI(player);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 판매할 아이템을 선택하세요.");
				}
				else if(u.getInt(player, (byte)0)==3)//거래 타입이 구매일 경우
				{
					new Struct_TradeBoard().SelectBuyItemGUI(player);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 구매할 아이템을 선택하세요.");
				}
				else //거래 타입이 교환일 경우
				{
					new Struct_TradeBoard().SelectExchangeItem_YouGUI(player);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 상대방이 나에게 줘야하는 아이템을 선택하세요.");
				}
				u.setString(player, (byte)0, "SelectItem");
			}
			else if(Message.compareTo("아니오")==0||Message.compareTo("x")==0
				||Message.compareTo("X")==0)
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 물품 등록이 취소되었습니다.");
				u.clearAll(player);
				return;
			}
		}
		else if(u.getString(player,(byte)0).compareTo("SetNeedAmount")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 1, 1000))
			{
				if(u.getInt(player, (byte)0)==5)
				{
					u.setInt(player, (byte) 2, Integer.parseInt(event.getMessage()));
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 당신은 무엇을 주실건가요?");
					u.setString(player, (byte)0, "SetMyItem");
					new Struct_TradeBoard().SelectExchangeItem_MyGUI(player);
					return;
				}
				u.setInt(player, (byte) 2, Integer.parseInt(event.getMessage()));
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 1개당 얼마에 구매 하실건가요? (0 ~ 2백만)");
				u.setString(player, (byte)0, "SetPrice");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("SetPrice")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 2000000))
			{
				u.setInt(player, (byte)1, Integer.parseInt(event.getMessage()));

				//판매할 경우
				if(u.getInt(player, (byte)0)==3)
				{
					YamlLoader Board = new YamlLoader();
					Board.getConfig("Structure/UserShopBoard.yml");
					if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()<Board.getInt("RegisterCommission"))
					{
						u.clearAll(player);
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 등록 수수료가 부족합니다! 재 등록 해 주세요!");
						return;
					}
					ItemStack itemAmountOne = u.getItemStack(player);
					String ItemName = u.getItemStack(player).getType().name()+"%d%"+itemAmountOne.getData().getData();
					if(u.getItemStack(player).hasItemMeta())
						if(u.getItemStack(player).getItemMeta().hasDisplayName())
							ItemName = u.getItemStack(player).getItemMeta().getDisplayName()+"%d%"+itemAmountOne.getData().getData();

					ItemName = ItemName.replace(":","");
					ItemName = ItemName.replace(".","");
					ItemName = ItemName.replace("[","");
					ItemName = ItemName.replace("]","");
					if(u.getItemStack(player).hasItemMeta())
					{
						if(u.getItemStack(player).getItemMeta().hasLore())
							ItemName = ItemName+u.getItemStack(player).getItemMeta().getLore().toString().length();
						else
							ItemName = ItemName+0;
					}
					else
						ItemName = ItemName+0;
					if(Board.contains("Sell."+ItemName+"."+player.getUniqueId().toString()))
					{
						u.clearAll(player);
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 동일 상품을 이미 등록하셨습니다!");
						return;
					}
					if(Board.contains("Sell."+ItemName)==false)
						Board.set("Sell."+ItemName+".Item", itemAmountOne);
					Board.set("SellRegistered", Board.getInt("SellRegistered")+1);
					Board.set("Sell."+ItemName+"."+player.getUniqueId().toString()+".Name", player.getName());
					Board.set("Sell."+ItemName+"."+player.getUniqueId().toString()+".Price", u.getInt(player, (byte)1));
					Board.set("Sell."+ItemName+"."+player.getUniqueId().toString()+".Amount", u.getInt(player,(byte)2));
					Board.saveConfig();
					main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * Board.getInt("RegisterCommission"), 0, false);
					YamlLoader USRL = new YamlLoader();
					USRL.getConfig("Structure/UserShopRegisterList.yml");
					USRL.set(player.getUniqueId().toString(), USRL.getInt(player.getUniqueId().toString())+1);
					USRL.saveConfig();
					u.clearAll(player);
					SoundEffect.SP(player, Sound.BLOCK_CHEST_OPEN, 1.0F, 1.8F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 등록이 완료되었습니다!");
					return;
				}
				for(int count = 0; count < player.getInventory().getSize(); count++)
				{
					if(player.getInventory().getItem(count)!=null)
						if(player.getInventory().getItem(count).equals(u.getItemStack(player)))
						{
							YamlLoader Board = new YamlLoader();
							Board.getConfig("Structure/UserShopBoard.yml");
							if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()<Board.getInt("RegisterCommission"))
							{
								u.clearAll(player);
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
								player.sendMessage(ChatColor.RED+"[거래 게시판] : 등록 수수료가 부족합니다! 재 등록 해 주세요!");
								return;
							}
							//구매할 경우
							if(u.getInt(player, (byte)0)==1)
							{
								ItemStack itemAmountOne = u.getItemStack(player);
								String ItemName = u.getItemStack(player).getType().name()+"%d%"+itemAmountOne.getData().getData();
								if(u.getItemStack(player).hasItemMeta())
									if(u.getItemStack(player).getItemMeta().hasDisplayName())
										ItemName = u.getItemStack(player).getItemMeta().getDisplayName()+"%d%"+itemAmountOne.getData().getData();

								ItemName = ItemName.replace(":","");
								ItemName = ItemName.replace(".","");
								ItemName = ItemName.replace("[","");
								ItemName = ItemName.replace("]","");
								if(u.getItemStack(player).hasItemMeta())
								{
									if(u.getItemStack(player).getItemMeta().hasLore())
										ItemName = ItemName+u.getItemStack(player).getItemMeta().getLore().toString().length();
									else
										ItemName = ItemName+0;
								}
								else
									ItemName = ItemName+0;
								itemAmountOne.setAmount(1);
								if(Board.contains("Buy."+ItemName+"."+player.getUniqueId().toString()))
								{
									u.clearAll(player);
									SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
									player.sendMessage(ChatColor.RED+"[거래 게시판] : 동일 상품을 이미 등록하셨습니다!");
									return;
								}
								if(Board.contains("Buy."+ItemName)==false)
									Board.set("Buy."+ItemName+".Item", itemAmountOne);
								Board.set("BuyRegistered", Board.getInt("BuyRegistered")+1);
								Board.set("Buy."+ItemName+"."+player.getUniqueId().toString()+".Name", player.getName());
								Board.set("Buy."+ItemName+"."+player.getUniqueId().toString()+".Price", u.getInt(player, (byte)1));
								Board.set("Buy."+ItemName+"."+player.getUniqueId().toString()+".Amount", u.getItemStack(player).getAmount());
								Board.saveConfig();
							}
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * Board.getInt("RegisterCommission"), 0, false);

							YamlLoader USRL = new YamlLoader();
							USRL.getConfig("Structure/UserShopRegisterList.yml");
							USRL.set(player.getUniqueId().toString(), USRL.getInt(player.getUniqueId().toString())+1);
							USRL.saveConfig();
							player.getInventory().setItem(count, null);
							u.clearAll(player);
							SoundEffect.SP(player, Sound.BLOCK_CHEST_OPEN, 1.0F, 1.8F);
							player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 등록이 완료되었습니다!");
							return;
						}
				}
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED+"[거래 게시판] : 등록할 아이템이 없습니다! 재 등록 해 주세요!");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("TradeBoardSetting")==0)
		{
			String SettingType = u.getString(player, (byte)1);
			YamlLoader Board = new YamlLoader();
			Board.getConfig("Structure/UserShopBoard.yml");
			if(SettingType.compareTo("SellCommission")==0)
			{
				if(isIntMinMax(event.getMessage(), player, 0, 100))
					Board.set(SettingType, Integer.parseInt(event.getMessage()));
			}
			else if(SettingType.compareTo("RegisterCommission")==0)
			{
				if(isIntMinMax(event.getMessage(), player, 0, 2000000))
					Board.set(SettingType, Integer.parseInt(event.getMessage()));
			}
			else if(SettingType.compareTo("LimitPerPlayer")==0)
			{
				if(isIntMinMax(event.getMessage(), player, 1, 100))
					Board.set(SettingType, Integer.parseInt(event.getMessage()));
			}
			Board.saveConfig();
			new structure.Struct_TradeBoard().TradeBoardSettingGUI(player);
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			u.clearAll(player);
		}
		else if(u.getString(player,(byte)0).compareTo("BuyTrade")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, u.getInt(player, (byte)0)))
			{
				int needAmount = Integer.parseInt(ChatColor.stripColor(event.getMessage()));
				if(needAmount==0)
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 거래가 취소되었습니다.");
					u.clearAll(player);
					return;
				}
				else
				{
					YamlLoader Board = new YamlLoader();
					Board.getConfig("Structure/UserShopBoard.yml");
					short ExitAmount = (byte) Board.getInt("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount");
					if(Board.contains("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2))==false)
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 거래 정보가 바뀌었습니다! 재 시도 해 주시길 바랍니다!");
						u.clearAll(player);
						return;
					}
					else if(ExitAmount<needAmount)
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 거래 정보가 바뀌었습니다! 재 시도 해 주시길 바랍니다!");
						u.clearAll(player);
						return;
					}
					else
					{
						int Price = Board.getInt("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Price");
						if(Price*needAmount <= main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
						{
							Price = Price*needAmount;
							ItemStack item = Board.getItemStack("Buy."+u.getString(player, (byte)1)+".Item");
							int SellCommission = Board.getInt("SellCommission");
							int MinusSellCommission = (int)((Price/100.0) * SellCommission);
							
							if(Bukkit.getPlayer(Board.getString("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Name"))!=null)
							{
								Player Target = Bukkit.getPlayer(Board.getString("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Name"));
								if(Target.isOnline())
								{
									if(main.Main_ServerOption.PlayerList.get(u.getString(player, (byte)2)).getStat_Money()+Price > 2000000000)
									{
										SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
										player.sendMessage(ChatColor.RED+"[거래 게시판] : 판매자의 계좌 잔고가 초과되어 거래를 진행할 수 없습니다!");
										u.clearAll(player);
										return;
									}
									SoundEffect.SP(Target, Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F);
									new effect.SendPacket().sendTitleSubTitle(Target, "\'§3[거래 성사]\'","\'§3거래 게시판에 등록한 물품이 판매되었습니다.\'", (byte)1, (byte)3, (byte)1);
									main.Main_ServerOption.PlayerList.get(u.getString(player, (byte)2)).addStat_MoneyAndEXP(Price-MinusSellCommission, 0, false);
								}
								else
								{
									YamlLoader TargetYML = new YamlLoader();
									TargetYML.getConfig("Stats/"+u.getString(player, (byte)2)+".yml");
									if(TargetYML.getLong("Stat.Money") + Price-MinusSellCommission > 2000000000)
									{
										SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
										player.sendMessage(ChatColor.RED+"[거래 게시판] : 판매자의 계좌 잔고가 초과되어 거래를 진행할 수 없습니다!");
										u.clearAll(player);
										return;
									}
									TargetYML.set("Stat.Money", TargetYML.getLong("Stat.Money") + Price-MinusSellCommission);
									TargetYML.saveConfig();
								}
							}
							else
							{
								YamlLoader TargetYML = new YamlLoader();
								TargetYML.getConfig("Stats/"+u.getString(player, (byte)2)+".yml");
								TargetYML.set("Stat.Money", TargetYML.getLong("Stat.Money") + Price-MinusSellCommission);
								TargetYML.saveConfig();
							}
							if(ExitAmount-needAmount ==0)
							{
								if(Board.getConfigurationSection("Buy."+u.getString(player, (byte)1)).getKeys(false).size()==2)
									Board.removeKey("Buy."+u.getString(player, (byte)1));
								else
									Board.removeKey("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2));
								Board.set("BuyRegistered", Board.getInt("BuyRegistered")-1);
								YamlLoader USRL = new YamlLoader();
								USRL.getConfig("Structure/UserShopRegisterList.yml");
								if(USRL.contains(u.getString(player, (byte)2))==true)
								{
									USRL.set(u.getString(player, (byte)2), USRL.getInt(u.getString(player, (byte)2))-1);
									USRL.saveConfig();
								}
							}
							else
								Board.set("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount", ExitAmount-needAmount);
							Board.saveConfig();
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1*Price, 0, false);
							new structure.Struct_PostBox().SendPost_Server(u.getString(player, (byte)2), "[거래 게시판]", "[물품 판매 영수증]", 
							player.getName()+"님께서 ["+u.getString(player, (byte)3)+"] 아이템을 "+needAmount+"개 구매하여 "+Price+" "+ChatColor.WHITE+ChatColor.stripColor(main.Main_ServerOption.Money)+ChatColor.WHITE+"의 이익을 냈으며, "
							+MinusSellCommission+" "+ChatColor.WHITE+ChatColor.stripColor(main.Main_ServerOption.Money)+" 만큼의 수수료를 지불하였습니다.", null);

							if(needAmount!=1)
								item.setAmount(needAmount);
							new util.Util_Player().giveItemForce(player, item);
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
							player.sendMessage(ChatColor.RED+"[거래 게시판] : 소지금이 부족합니다!");
							player.sendMessage(ChatColor.RED+"[필요 금액 : "+(Price*needAmount)+" "+main.Main_ServerOption.Money+ChatColor.RED+"]");
							player.sendMessage(ChatColor.RED+"[소지 금액 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+" "+main.Main_ServerOption.Money+ChatColor.RED+"]");
						}
						u.clearAll(player);
						return;
					}
				}
			}
		}
		else if(u.getString(player,(byte)0).compareTo("SellTrade")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, u.getInt(player, (byte)0)))
			{
				short needAmount = (short) Integer.parseInt(ChatColor.stripColor(event.getMessage()));
				if(needAmount==0)
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 거래가 취소되었습니다.");
					u.clearAll(player);
					return;
				}
				else
				{
					YamlLoader Board = new YamlLoader();
					Board.getConfig("Structure/UserShopBoard.yml");
					Player Target = Bukkit.getPlayer(Board.getString("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Name"));
					short itemcount = 0;
					ItemStack BoardAddedItem = Board.getItemStack("Sell."+u.getString(player, (byte)1)+".Item");
					for(int count=0; count<player.getInventory().getSize();count++)
					{
						ItemStack PlayerHave = player.getInventory().getItem(count);
						if(PlayerHave!=null)
						{
							int amount = PlayerHave.getAmount();
							PlayerHave.setAmount(1);
							if(PlayerHave.equals(BoardAddedItem))
								itemcount= (short) (itemcount+amount);
							PlayerHave.setAmount(amount);
						}
					}
					if(itemcount<needAmount)
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 물건을 충분히 가지고 있지 않습니다!");
						u.clearAll(player);
						return;
					}
					if(needAmount > Board.getInt("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount"))
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 거래 정보가 바뀌었습니다! 재 시도 해 주시길 바랍니다!");
						u.clearAll(player);
						return;
					}
					int rawprice = Board.getInt("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Price");
					long price = rawprice*needAmount;
					
					if(Target!=null)
					{
						if(Target.isOnline())
						{
							if(main.Main_ServerOption.PlayerList.get(u.getString(player, (byte)2)).getStat_Money()<price)
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED+"[거래 게시판] : 판매자의 계좌 잔고가 부족하여 거래가 취소되었습니다!");
								u.clearAll(player);
								return;
							}
							else
							{
								SoundEffect.SP(Target, Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F);
								main.Main_ServerOption.PlayerList.get(u.getString(player, (byte)2)).addStat_MoneyAndEXP(-1*price, 0, false);
								new effect.SendPacket().sendTitleSubTitle(Target, "\'§3[거래 성사]\'","\'§3거래 게시판에 의뢰한 물품이 도착하였습니다.\'", (byte)1, (byte)3, (byte)1);
							}
						}
						else
						{
							YamlLoader TargetYML = new YamlLoader();
							TargetYML.getConfig("Stats/"+u.getString(player, (byte)2)+".yml");
							if(TargetYML.getLong("Stat.Money") < price)
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED+"[거래 게시판] : 판매자의 계좌 잔고가 부족하여 거래가 취소되었습니다!");
								u.clearAll(player);
								return;
							}
							else
							{
								TargetYML.set("Stat.Money", TargetYML.getLong("Stat.Money") - price);
								TargetYML.saveConfig();
							}
						}
					}
					else
					{
						YamlLoader TargetYML = new YamlLoader();
						TargetYML.getConfig("Stats/"+u.getString(player, (byte)2)+".yml");
						if(TargetYML.getLong("Stat.Money") < price)
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED+"[거래 게시판] : 판매자의 계좌 잔고가 부족하여 거래가 취소되었습니다!");
							u.clearAll(player);
							return;
						}
						else
						{
							TargetYML.set("Stat.Money", TargetYML.getLong("Stat.Money") - price);
							TargetYML.saveConfig();
						}
					}
					main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(price, 0, false);
					if(Board.getInt("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount")-needAmount ==0)
					{
						if(Board.getConfigurationSection("Sell."+u.getString(player, (byte)1)).getKeys(false).size()==2)
							Board.removeKey("Sell."+u.getString(player, (byte)1));
						else
							Board.removeKey("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2));
						Board.set("SellRegistered", Board.getInt("SellRegistered")-1);
						YamlLoader USRL = new YamlLoader();
						USRL.getConfig("Structure/UserShopRegisterList.yml");
						if(USRL.contains(Target.getUniqueId().toString())==true)
						{
							USRL.set(Target.getUniqueId().toString(), USRL.getInt(Target.getUniqueId().toString())-1);
							USRL.saveConfig();
						}
					}
					else
						Board.set("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount", Board.getInt("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount")-needAmount);
					Board.saveConfig();
					if(needAmount>64)
					{
						short pack = (short) (needAmount/64);
						short trash = (short) (needAmount-(pack*64));
						BoardAddedItem.setAmount(64);
						for(int count=0;count<pack;count++)
							new structure.Struct_PostBox().SendPost_Server(u.getString(player, (byte)2), "[거래 게시판]", "[물품 구매 영수증]",
									player.getName()+"님께서 ["+u.getString(player, (byte)3)+"] 아이템을 64개 판매하여 "+(rawprice*64)+" "+ChatColor.stripColor(main.Main_ServerOption.Money)+ChatColor.WHITE+"의 지출을 했습니다.", BoardAddedItem);
						if(trash!=0)
						{
							BoardAddedItem.setAmount(trash);
							new structure.Struct_PostBox().SendPost_Server(u.getString(player, (byte)2), "[거래 게시판]", "[물품 구매 영수증]",
									player.getName()+"님께서 ["+u.getString(player, (byte)3)+"] 아이템을 "+trash+"개 판매하여 "+(rawprice*trash)+" "+ChatColor.stripColor(main.Main_ServerOption.Money)+ChatColor.WHITE+"의 지출을 했습니다.", BoardAddedItem);
						}
					}
					else
					{
						BoardAddedItem.setAmount(needAmount);
						new structure.Struct_PostBox().SendPost_Server(u.getString(player, (byte)2), "[거래 게시판]", "[물품 구매 영수증]",
								player.getName()+"님께서 ["+u.getString(player, (byte)3)+"] 아이템을 "+needAmount+"개 판매하여 "+price+" "+ChatColor.stripColor(main.Main_ServerOption.Money)+ChatColor.WHITE+"의 지출을 했습니다.", BoardAddedItem);
					}
					BoardAddedItem.setAmount(1);
					for(int count=0; count<player.getInventory().getSize();count++)
					{
						ItemStack PlayerHave = player.getInventory().getItem(count);
						if(PlayerHave!=null)
						{
							byte amount = (byte) PlayerHave.getAmount();
							PlayerHave.setAmount(1);
							if(PlayerHave.equals(BoardAddedItem))
							{
								if(amount > needAmount)
								{
									PlayerHave.setAmount(amount-needAmount);
									player.getInventory().setItem(count, PlayerHave);
								}
								else
									player.getInventory().setItem(count, null);
								needAmount = (short) (needAmount - amount);
							}
							if(needAmount<=0)
								break;
						}
					}
					//플레이어 인벤토리에서 판매한 물품 삭제하기
				}
			}
		}//SellTrade 끝
	}
	
	private void CampFireChatting(PlayerChatEvent event)
	{
		
		Player player = event.getPlayer();
		UserData_Object u = new UserData_Object();
		String Message = ChatColor.stripColor(event.getMessage());
		//Reciever NickName
		if(u.getString(player,(byte)0).compareTo("RN")==0)
		{
			if(Message.compareTo(ChatColor.stripColor(player.getName()))==0)
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
				player.sendMessage(ChatColor.RED+"[우편] : 자기 자신에게는 보낼 수 없습니다!");
				return;
			}
		}
	}
	
	private boolean isIntMinMax(String message,Player player, int Min, int Max)
	{
		try
		{
			if(message.split(" ").length <= 1 && Integer.parseInt(message) >= Min&& Integer.parseInt(message) <= Max)
				return true;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+Min+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+Max+ChatColor.RED+" 이하의 숫자를 입력하세요!");
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+Min+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+Max+ChatColor.RED+")");
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}

	private byte askOX(String message,Player player)
	{
		if(message.split(" ").length <= 1)
		{
			if(message.compareTo("x")==0||message.compareTo("X")==0||message.compareTo("아니오")==0)
				return 0;
			else if(message.compareTo("o")==0||message.compareTo("O")==0||message.compareTo("네")==0)
				return 1;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : [네/O] 혹은 [아니오/X]를 입력 해 주세요!");
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
			
		}
		else
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : [네/O] 혹은 [아니오/X]를 입력 해 주세요!");
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
		return -1;
	}
}
