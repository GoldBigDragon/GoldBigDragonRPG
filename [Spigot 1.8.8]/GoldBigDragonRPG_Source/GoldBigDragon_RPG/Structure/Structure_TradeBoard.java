package GoldBigDragon_RPG.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Structure_TradeBoard extends GUIutil
{
	
	public void TradeBoardMainGUI(Player player, byte page, byte ShopType)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");
		String Color = ChatColor.BLUE+"";
		if(ShopType==1)
			Color=ChatColor.RED+"";
		else if(ShopType==2)
			Color=ChatColor.DARK_GREEN+"";
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED +""+ChatColor.BOLD +""+Color+""+ChatColor.BOLD +""+ "거래 게시판 : "+(page+1));
		
		if(Board.contains("Buy")==false)
		{
			Board.set("LimitPerPlayer",5);
			Board.set("RegisterCommission",1000);//등록 수수료
			Board.set("SellCommission",1);//판매 수수료
			Board.set("BuyRegistered",0);//현재 등록된 구매 아이템 개수
			Board.set("SellRegistered",0);//현재 등록된 판매 아이템 개수
			Board.set("ExchangeRegistered",0);//현재 등록된 교환 아이템 개수
			Board.set("Buy.1", null);
			Board.set("Sell.1",null);
			Board.set("Exchange.1",null);
			Board.saveConfig();
		}
		byte data = 11;
		String ShopTypeString = "Buy";
		if(ShopType==1)//Sell
		{
			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+"[거래 타입 변경]", 130,0,1,Arrays.asList("",ChatColor.DARK_AQUA+""+ChatColor.BOLD+"[현재 거래 타입]",ChatColor.RED+""+ChatColor.BOLD+"[    판매    ]"), 46, inv);
			ShopTypeString = "Sell";
			data = 14;
		}
		else if(ShopType==2)//Exchange
		{
			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+"[거래 타입 변경]", 388,0,1,Arrays.asList("",ChatColor.DARK_AQUA+""+ChatColor.BOLD+"[현재 거래 타입]",ChatColor.GREEN+""+ChatColor.BOLD+"[    교환    ]"), 46, inv);
			ShopTypeString = "Exchange";
			data = 5;
		}
		else
			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+"[거래 타입 변경]", 54,0,1,Arrays.asList("",ChatColor.DARK_AQUA+""+ChatColor.BOLD+"[현재 거래 타입]",ChatColor.BLUE+""+ChatColor.BOLD+"[    구매    ]"), 46, inv);

		Stack2(ChatColor.GRAY+""+ChatColor.BOLD+"[물품 검색]", 345,0,1,Arrays.asList(ChatColor.GRAY+"등록된 물품을 검색합니다.","",ChatColor.RED+""+ChatColor.BOLD+"[GoldBigDragonRPG 버전 업 필요]"), 52, inv);
		

		for(byte count2 = 0; count2 < 9; count2++)
			Stack2(ChatColor.RED + " ", 160,data,1,Arrays.asList(""), count2, inv);
		for(byte count2 = 35; count2 < 45; count2++)
			Stack2(ChatColor.RED + " ", 160,data,1,Arrays.asList(""), count2, inv);
		for(byte count2 = 9; count2 < 45; count2=(byte) (count2+9))
			Stack2(ChatColor.RED + " ", 160,data,1,Arrays.asList(""), count2, inv);
		for(byte count2 = 17; count2 < 53; count2=(byte) (count2+9))
			Stack2(ChatColor.RED + " ", 160,data,1,Arrays.asList(""), count2, inv);

		byte loc=10;
		
		Object[] ItemList = Board.getConfigurationSection(ShopTypeString).getKeys(false).toArray();

		short Added = 0;
		boolean isFinished=false;
		ArrayList<String> AddItem = new ArrayList<String>();
		ArrayList<String> AddPlayer = new ArrayList<String>();
		if(page==0)
		{
			for(int count = ItemList.length-1; count >= 0 ; count--)
			{
				Object[] PlayerList = Board.getConfigurationSection(ShopTypeString+"."+ItemList[count].toString()).getKeys(false).toArray();
				for(int count3 = PlayerList.length-1; count3 >= 0; count3--)
					if(PlayerList[count3].toString().compareTo("Item")!=0)
					{
						AddItem.add(ItemList[count].toString());
						AddPlayer.add(PlayerList[count3].toString());
					}
				if(AddItem.size()>=21)
					break;
			}
		}
		else
		{
			for(int count = ItemList.length-1; count >= 0 ; count--)
			{
				Object[] PlayerList = Board.getConfigurationSection(ShopTypeString+"."+ItemList[count].toString()).getKeys(false).toArray();
				if((PlayerList.length-1)+Added >= (page*21)+21)
				{
					int getoff = ((PlayerList.length-1)+Added)-((page*21)+21);
					for(int count3 = PlayerList.length-1-getoff; count3 >= 0; count3--)
						if(PlayerList[count3].toString().compareTo("Item")!=0)
						{
							AddItem.add(ItemList[count].toString());
							AddPlayer.add(PlayerList[count3].toString());
						}
					for(int count2 = count+1; count2<ItemList.length; count2++)
					{
						if(AddItem.size()>=21)
							break;
						PlayerList = Board.getConfigurationSection(ShopTypeString+"."+ItemList[count2].toString()).getKeys(false).toArray();
						for(int count3 = PlayerList.length-1; count3 >= 0; count3--)
							if(PlayerList[count3].toString().compareTo("Item")!=0)
							{
								AddItem.add(ItemList[count2].toString());
								AddPlayer.add(PlayerList[count3].toString());
							}
					}
					isFinished = true;
				}
				else
					Added = (short) (Added+(PlayerList.length-1));
				if(isFinished)
					break;
			}
			if(AddItem.size()==0)
			{
				for(short count = (short) (ItemList.length-1); count >= 0 ; count--)
				{
					Object[] PlayerList = Board.getConfigurationSection(ShopTypeString+"."+ItemList[count].toString()).getKeys(false).toArray();
					for(short count2 = (short) (PlayerList.length-1); count2 >= 0; count2--)

					if((PlayerList.length-1)+Added >= Board.getInt(ShopTypeString+"Registered")+((page*21)+21))
					{
						if(PlayerList[count2].toString().compareTo("Item")!=0)
						{
							if(AddItem.size()<Board.getInt(ShopTypeString+"Registered")-(page*21))
							{
								AddItem.add(ItemList[count].toString());
								AddPlayer.add(PlayerList[count2].toString());
							}
							else
							{
								AddItem.remove(0);
								AddPlayer.remove(0);
								AddItem.add(ItemList[count].toString());
								AddPlayer.add(PlayerList[count2].toString());
							}
						}
					}
					else
						Added = (short) (Added+(PlayerList.length-1));
				}
			}
		}
		
		for(short count = (short) (AddItem.size()-1);count >= 0; count--)
		{
			if(count<0||AddItem.isEmpty()||loc>=35)
				break;
			if(Board.getItemStack(ShopTypeString+"."+AddItem.get(count).toString()+".Item")!=null)
			{
				ItemStack item = Board.getItemStack(ShopTypeString+"."+AddItem.get(count)+".Item");;
				if(ShopType==2)
				{
					item = Board.getItemStack(ShopTypeString+"."+AddItem.get(count)+"."+AddPlayer.get(count)+".GiveItem");
					item.setAmount(Board.getInt(ShopTypeString+"."+AddItem.get(count)+"."+AddPlayer.get(count)+".GiveItemAmount"));
				}
				else
					item = Board.getItemStack(ShopTypeString+"."+AddItem.get(count)+".Item");
				String RegisteredUser = Board.getString(ShopTypeString+"."+AddItem.get(count)+"."+AddPlayer.get(count)+".Name");

				long Price = 0;
				short Amount = 0;
				String Temp = null;
				if(ShopType==2)
				{
					ItemStack CurrentItem = Board.getItemStack(ShopTypeString+"."+AddItem.get(count)+"."+AddPlayer.get(count)+".WantItem");
					Temp = new GoldBigDragon_RPG.Event.Interact().SetItemDefaultName((short) CurrentItem.getTypeId(), CurrentItem.getData().getData());
					if(CurrentItem.hasItemMeta())
						if(CurrentItem.getItemMeta().hasDisplayName())
							Temp = CurrentItem.getItemMeta().getDisplayName();
					Amount = (short) Board.getInt(ShopTypeString+"."+AddItem.get(count)+"."+AddPlayer.get(count)+".WantItemAmount");
				}
				else
				{
					Price = Board.getInt(ShopTypeString+"."+AddItem.get(count)+"."+AddPlayer.get(count)+".Price");
					Amount = (short) Board.getInt(ShopTypeString+"."+AddItem.get(count)+"."+AddPlayer.get(count)+".Amount");
				}
				ItemMeta itemM = item.getItemMeta();
				List<String> Memo = null;
				if(itemM.hasLore())
					Memo = itemM.getLore();
				else
					Memo = new ArrayList<String>();
				Memo.add("");
				if(ShopType==0)
				{
					Memo.add(ChatColor.BLUE+"판매자 : " + ChatColor.WHITE + RegisteredUser);
					Memo.add(ChatColor.BLUE+"1개당 "+ChatColor.WHITE+Price+" "+GoldBigDragon_RPG.Main.ServerOption.Money);
					Memo.add(ChatColor.BLUE+"재고 : "+ChatColor.WHITE+Amount+ChatColor.BLUE+" 개");
				}
				else if(ShopType==1)
				{
					Memo.add(ChatColor.BLUE+"구매자 : " + ChatColor.WHITE + RegisteredUser);
					Memo.add(ChatColor.BLUE+"1개당 "+ChatColor.WHITE+Price+" "+GoldBigDragon_RPG.Main.ServerOption.Money);
					Memo.add(ChatColor.BLUE+"필요 수량 : "+ChatColor.WHITE+Amount+ChatColor.BLUE+" 개");
				}
				else if(ShopType==2)
				{
					Memo.add(ChatColor.BLUE+"교환자 : " + ChatColor.WHITE + RegisteredUser);
					Memo.add(ChatColor.BLUE+"교환 물품 : "+ Temp);
					Memo.add(ChatColor.BLUE+"필요 수량 : "+ChatColor.WHITE+Amount+ChatColor.BLUE+" 개");
				}
				if(ShopType==2)
				{
					String Transer = AddItem.get(count);
					Transer = Transer.replace('§','&');
					Memo.add(ChatColor.BLACK+Transer);
				}
				else
					Memo.add("");
				itemM.setLore(Memo);
				item.setItemMeta(itemM);
				ItemStackStack(item, loc, inv);
				if(loc==16||loc==25)
					loc = (byte) (loc+3);
				else
					loc++;
			}
		}
		boolean showNextPage = false;
		if(ShopType==0)
			if(Board.getInt("BuyRegistered")>(21*page)+21)
				showNextPage=true;
		if(ShopType==1)
			if(Board.getInt("SellRegistered")>(21*page)+21)
				showNextPage=true;
		if(ShopType==2)
			if(Board.getInt("ExchangeRegistered")>(21*page)+21)
				showNextPage=true;
		if(showNextPage)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[거래 메뉴]", 386,0,1,Arrays.asList(ChatColor.GRAY + "거래 물품을 등록하거나",ChatColor.GRAY + "자신이 등록한 물품을 봅니다."), 49, inv);
		player.openInventory(inv);
		return;
	}
	
	public void TradeBoardSettingGUI(Player player)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +""+ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "거래 게시판 설정");
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[판매 수수료]", 266,0,1,Arrays.asList(ChatColor.GRAY + "물품 판매 수수료를 설정합니다.","",ChatColor.BLUE+"[현재 수수료]",ChatColor.WHITE+"판매 가격의 "+Board.getInt("SellCommission")+"%"), 1, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[등록 수수료]", 54,0,1,Arrays.asList(ChatColor.GRAY + "물품 등록 수수료를 설정합니다.","",ChatColor.BLUE+"[현재 수수료]",ChatColor.WHITE+"등록시 "+Board.getInt("RegisterCommission")+" "+GoldBigDragon_RPG.Main.ServerOption.Money+ChatColor.WHITE+" 필요"), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[등록 물품 제한]", 397,3,1,Arrays.asList(ChatColor.GRAY + "1인당 등록 가능한 물품 수를 제한합니다.","",ChatColor.BLUE+"[현재 최대 개수]",ChatColor.WHITE+""+Board.getInt("LimitPerPlayer")+" 개"), 3, inv);
		//
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
		player.openInventory(inv);
	}
	
	public void SelectTradeTypeGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +ChatColor.BLACK + "거래 게시판 메뉴");

		Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[물품 판매]", 54,0,1,Arrays.asList(ChatColor.GRAY + "자신이 현재 가지고 있는 아이템을", ChatColor.GRAY + "등록하여 판매합니다."), 1, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[물품 구매]", 130,0,1,Arrays.asList(ChatColor.GRAY + "구매하고 싶은 아이템을 등록하여", ChatColor.GRAY + "구매합니다."), 3, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[물품 교환]", 388,0,1,Arrays.asList(ChatColor.GRAY + "자신이 원하는 아이템과 자신의", ChatColor.GRAY + "아이템을 등록하여 교환합니다."), 5, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[등록 확인]", 386,0,1,Arrays.asList(ChatColor.GRAY + "거래 게시판에 등록한 아이템을", ChatColor.GRAY + "확인하거나 등록을 취소합니다.","",ChatColor.RED+""+ChatColor.BOLD+"[GoldBigDragonRPG 버전 업 필요]"), 7, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
		player.openInventory(inv);
		return;
	}

	public void SelectSellItemGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +ChatColor.BLACK + "판매할 아이템을 고르세요");
		Stack2(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.RED + "" + ChatColor.BOLD + "[판매 취소]", 166,0,1,Arrays.asList(ChatColor.GRAY + "아이템 판매를 취소합니다."), 4, inv);
		player.openInventory(inv);
		return;
	}
	
	public void SelectBuyItemGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +ChatColor.BLACK + "구매할 아이템을 고르세요");
		Stack2(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.WHITE + "" + ChatColor.BOLD + "[일반 아이템]", 2,0,1,Arrays.asList(ChatColor.GRAY + "일반 아이템 중에서 고릅니다."), 2, inv);
		Stack2(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.RED + "" + ChatColor.BOLD + "[구매 취소]", 166,0,1,Arrays.asList(ChatColor.GRAY + "아이템 구매를 취소합니다."), 4, inv);
		//Stack2(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.AQUA + "" + ChatColor.BOLD + "[특수 아이템]", 384,0,1,Arrays.asList(ChatColor.GRAY + "특수 아이템 중에서 고릅니다."), 6, inv);
		player.openInventory(inv);
		return;
	}

	public void SelectExchangeItem_YouGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +ChatColor.BLACK + "받고싶은 아이템을 고르세요");
		Stack2(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.WHITE + "" + ChatColor.BOLD + "[일반 아이템]", 2,0,1,Arrays.asList(ChatColor.GRAY + "일반 아이템 중에서 고릅니다."), 2, inv);
		Stack2(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.RED + "" + ChatColor.BOLD + "[구매 취소]", 166,0,1,Arrays.asList(ChatColor.GRAY + "아이템 구매를 취소합니다."), 4, inv);
		player.openInventory(inv);
		return;
	}
	
	public void SelectExchangeItem_MyGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +ChatColor.BLACK + "내가 줄 아이템을 고르세요");
		Stack2(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.RED + "" + ChatColor.BOLD + "[판매 취소]", 166,0,1,Arrays.asList(ChatColor.GRAY + "아이템 판매를 취소합니다."), 4, inv);
		player.openInventory(inv);
		return;
	}
	
	public void SelectNormalItemGUI(Player player, byte page, byte ShopType)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED +""+ChatColor.BOLD +ChatColor.BLACK+ChatColor.BOLD +""+ "일반 아이템 : "+(page+1));

		byte loc=0;
		int count = 0;
		for(count = page*45;loc < 45;count++)
		{
			if(getNormalItem(count)!=null)
				ItemStackStack(getNormalItem(count), loc, inv);
			else
				break;
			loc=(byte) (loc+1);
		}
		
		if(getNormalItem(count+1)!=null)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+ShopType), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void TradeBoardMainGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1);
		byte ShopType = 0;
		if(event.getInventory().getItem(0).getData().getData()==14)
			ShopType = 1;
		else if(event.getInventory().getItem(0).getData().getData()==5)
			ShopType = 2;
		
		switch (event.getSlot())
		{
		case 46://거래 타입 변경
			s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
			if(event.isLeftClick())
			{
				if(ShopType != 2)
					TradeBoardMainGUI(player, (byte)0, (byte)(ShopType+1));
				else
					TradeBoardMainGUI(player, (byte)0, (byte)0);
			}
			if(event.isRightClick())
			{
				if(ShopType != 0)
					TradeBoardMainGUI(player, (byte)0, (byte)(ShopType-1));
				else
					TradeBoardMainGUI(player, (byte)0, (byte)2);
			}
			return;
		case 48://이전 페이지
			if(event.getCurrentItem().getTypeId()==323)
			{
				s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
				TradeBoardMainGUI(player, (byte)(page-1), (byte)ShopType);
			}
			return;
		case 49://물품 등록
			s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
			SelectTradeTypeGUI(player);
			return;
		case 50://다음 페이지
			if(event.getCurrentItem().getTypeId()==323)
			{
				s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
				TradeBoardMainGUI(player, (byte)(page+1), (byte)ShopType);
			}
			return;
		case 52://물품 검색
			s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
			return;
		}
		
		if((event.getSlot()>=10&&event.getSlot()<=16)||
		   (event.getSlot()>=19&&event.getSlot()<=25)||
		   (event.getSlot()>=28&&event.getSlot()<=34))
		{
			if(event.getCurrentItem().getTypeId()==0)
				return;
			String Register = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-4).split(" : ")[1]);
			String RegisterUUID = Bukkit.getServer().getOfflinePlayer(Register).getUniqueId().toString();

			String ShopTypeString = "Buy";
			if(ShopType==1)
				ShopTypeString = "Sell";
			if(ShopType==2)
				ShopTypeString = "Exchange";
			
			ItemStack CurrentItem = event.getCurrentItem();
			String ItemName = null;
			if(ShopType==2)
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");
				String Transer = CurrentItem.getItemMeta().getLore().get(CurrentItem.getItemMeta().getLore().size()-1).substring(2, event.getCurrentItem().getItemMeta().getLore().get(CurrentItem.getItemMeta().getLore().size()-1).length());
				ItemName = Transer.replace('&', '§');
				CurrentItem = Board.getItemStack("Exchange."+Transer+".Item");
			}
			else
			{
				ItemName = CurrentItem.getType().name()+"%d%"+CurrentItem.getData().getData();
				if(CurrentItem.hasItemMeta())
					if(CurrentItem.getItemMeta().hasDisplayName())
						ItemName = CurrentItem.getItemMeta().getDisplayName()+"%d%"+CurrentItem.getData().getData();
				ItemName = ItemName.replace(":","");
				ItemName = ItemName.replace(".","");
				ItemName = ItemName.replace("[","");
				ItemName = ItemName.replace("]","");
				byte TrashLoreSize = (byte) (10 + CurrentItem.getItemMeta().getLore().get(CurrentItem.getItemMeta().getLore().size()-2).length());
				TrashLoreSize = (byte) (TrashLoreSize + CurrentItem.getItemMeta().getLore().get(CurrentItem.getItemMeta().getLore().size()-3).length());
				TrashLoreSize = (byte) (TrashLoreSize + CurrentItem.getItemMeta().getLore().get(CurrentItem.getItemMeta().getLore().size()-4).length());
				TrashLoreSize = (byte) (TrashLoreSize + CurrentItem.getItemMeta().getLore().get(CurrentItem.getItemMeta().getLore().size()-5).length());
				ItemName = ItemName+(CurrentItem.getItemMeta().getLore().toString().length()-TrashLoreSize);
			}
			
			if(event.isRightClick()&&event.isShiftClick())
			{
				if(RegisterUUID.compareTo(player.getUniqueId().toString())==0)
				{
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");
					if(Board.contains(ShopTypeString+"."+ItemName+"."+RegisterUUID))
					{
						YamlManager USRL =YC.getNewConfig("Structure/UserShopRegisterList.yml");
						if(USRL.contains(player.getUniqueId().toString())==true)
						{
							USRL.set(player.getUniqueId().toString(), USRL.getInt(player.getUniqueId().toString())-1);
							USRL.saveConfig();
						}
						if(ShopType!=1)
						{
							if(ShopType==2)
							{
								ItemStack item = Board.getItemStack(ShopTypeString+"."+ItemName+"."+RegisterUUID+".GiveItem");
								item.setAmount(Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".GiveItemAmount"));
								new Structure_PostBox().SendPost_Server(RegisterUUID, "[물품 회수]", "[물품 회수]", "거래 게시판에 등록하였던 물품을 회수하였습니다.", item);
								player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 물품 등록을 취소하였습니다! [우편함을 확인하세요!]");
							}
							else
							{
								ItemStack item = Board.getItemStack(ShopTypeString+"."+ItemName+".Item");
								item.setAmount(Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".Amount"));
								new Structure_PostBox().SendPost_Server(RegisterUUID, "[물품 회수]", "[물품 회수]", "거래 게시판에 등록하였던 물품을 회수하였습니다.", item);
								player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 물품 등록을 취소하였습니다! [우편함을 확인하세요!]");
							}
						}
						else
							player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 물품 구매를 취소하였습니다!");
						if(Board.getConfigurationSection(ShopTypeString+"."+ItemName).getKeys(false).size()==2)
							Board.removeKey(ShopTypeString+"."+ItemName);
						else
							Board.removeKey(ShopTypeString+"."+ItemName+"."+RegisterUUID);
						Board.set(ShopTypeString+"Registered", Board.getInt(ShopTypeString+"Registered")-1);
						Board.saveConfig();
						s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
						TradeBoardMainGUI(player, (byte)page, (byte)ShopType);
					}
				}
				else
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[거래 게시판] : 자신이 등록한 물품만 등록 철회할 수 있습니다!");
					return;
				}
			}
			//물품 구매 모드일 때, 좌클릭
			else if(ShopType==0 && event.isLeftClick() && event.isShiftClick()==false)
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");

				if(ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()>=Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".Price"))
				{
					Object_UserData u = new Object_UserData();
					u.setTemp(player, "Structure");
					u.setType(player, "TradeBoard");
					u.setString(player, (byte)0, "BuyTrade");
					u.setString(player, (byte)1, ItemName);
					u.setString(player, (byte)2, RegisterUUID);
					
					String RealName = ChatColor.stripColor(new GoldBigDragon_RPG.Event.Interact().SetItemDefaultName((short) event.getCurrentItem().getTypeId(), event.getCurrentItem().getData().getData()));
					if(CurrentItem.hasItemMeta())
						if(CurrentItem.getItemMeta().hasDisplayName())
							RealName = CurrentItem.getItemMeta().getDisplayName();
					
					u.setString(player, (byte)3, RealName);
					
					
					u.setInt(player, (byte)0, Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".Amount"));
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.YELLOW+"[거래 게시판] : 얼마나 필요하신가요? (0 입력시 물품 구매 취소)");
					player.sendMessage(ChatColor.YELLOW+"[물품 재고 : "+Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".Amount")+" 개]");
					player.sendMessage(ChatColor.YELLOW+"[1개당 "+Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".Price")+" "+GoldBigDragon_RPG.Main.ServerOption.Money+ChatColor.YELLOW+"]");
					player.closeInventory();
				}
				else
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[거래 게시판] : 소지금이 부족하여 물품을 구매할 수 없습니다!");
					return;
				}
			}
			//물품 판매 모드일 때, 좌클릭
			else if(ShopType==1 && event.isLeftClick() && event.isShiftClick()==false)
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");
				short itemcount = 0;
				ItemStack BoardAddedItem = Board.getItemStack(ShopTypeString+"."+ItemName+".Item");
				for(byte count=0; count<player.getInventory().getSize();count++)
				{
					ItemStack PlayerHave = player.getInventory().getItem(count);
					byte amount = 1;
					if(PlayerHave!=null)
					{
						amount = (byte) PlayerHave.getAmount();
						PlayerHave.setAmount(1);
						if(PlayerHave.equals(BoardAddedItem))
							itemcount= (short) (itemcount+amount);
						PlayerHave.setAmount(amount);
					}
				}
				if(itemcount==0)
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[거래 게시판] : 해당 물건을 가지고 있지 않습니다!");
					return;
				}
				Object_UserData u = new Object_UserData();
				u.setTemp(player, "Structure");
				u.setType(player, "TradeBoard");
				u.setString(player, (byte)0, "SellTrade");
				u.setString(player, (byte)1, ItemName);
				u.setString(player, (byte)2, RegisterUUID);
				
				String RealName = ChatColor.stripColor(new GoldBigDragon_RPG.Event.Interact().SetItemDefaultName((short) event.getCurrentItem().getTypeId(), event.getCurrentItem().getData().getData()));
				if(CurrentItem.hasItemMeta())
					if(CurrentItem.getItemMeta().hasDisplayName())
						RealName = CurrentItem.getItemMeta().getDisplayName();
				
				u.setString(player, (byte)3, RealName);
				
				if(itemcount>Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".Amount"))
					itemcount=(short) Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".Amount");
				u.setInt(player, (byte)0, itemcount);
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.YELLOW+"[거래 게시판] : 얼마나 판매 하실건가요? (0 입력시 물품 판매 취소)");
				player.sendMessage(ChatColor.YELLOW+"[최대 "+itemcount+" 개 판매 가능]");
				player.sendMessage(ChatColor.YELLOW+"[1개당 "+Board.getInt(ShopTypeString+"."+ItemName+"."+RegisterUUID+".Price")+" "+GoldBigDragon_RPG.Main.ServerOption.Money+ChatColor.YELLOW+"]");
				player.closeInventory();
			}

			//물품 교환 모드일 때, 좌클릭
			else if(ShopType==2 && event.isLeftClick() && event.isShiftClick()==false)
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");
				
				Object_UserData u = new Object_UserData();
				short needAmount = (short) Board.getInt("Exchange."+ItemName+"."+RegisterUUID+".WantItemAmount");
				if(Board.contains("Exchange."+ItemName+"."+RegisterUUID)==false)
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.RED+"[거래 게시판] : 거래 정보가 바뀌었습니다! 재 시도 해 주시길 바랍니다!");
					u.clearAll(player);
					return;
				}
				else
				{
					Player Target = Bukkit.getPlayer(Board.getString("Exchange."+ItemName+"."+RegisterUUID+".Name"));
					short itemcount = 0;
					ItemStack BoardAddedItem = Board.getItemStack("Exchange."+ItemName+"."+RegisterUUID+".WantItem");
					for(byte count=0; count<player.getInventory().getSize();count++)
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
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 물건을 충분히 가지고 있지 않습니다!");
						u.clearAll(player);
						return;
					}
					if(needAmount > Board.getInt("Exchange."+ItemName+"."+RegisterUUID+".WantItemAmount"))
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 거래 정보가 바뀌었습니다! 재 시도 해 주시길 바랍니다!");
						player.closeInventory();
						u.clearAll(player);
						return;
					}
					String TempName = ItemName;
					ItemStack BoardGiveItem = Board.getItemStack("Exchange."+ItemName+"."+RegisterUUID+".GiveItem");
					String BoardGiveItemName = new GoldBigDragon_RPG.Event.Interact().SetItemDefaultName((short) BoardGiveItem.getTypeId(), BoardGiveItem.getData().getData());
					ItemName = new GoldBigDragon_RPG.Event.Interact().SetItemDefaultName((short) BoardAddedItem.getTypeId(), BoardAddedItem.getData().getData());
					if(BoardAddedItem.hasItemMeta())
						if(BoardAddedItem.getItemMeta().hasDisplayName())
							ItemName = BoardAddedItem.getItemMeta().getDisplayName();
					if(BoardGiveItem.hasItemMeta())
						if(BoardGiveItem.getItemMeta().hasDisplayName())
							BoardGiveItemName = BoardGiveItem.getItemMeta().getDisplayName();
					short GiveAmount = (short) Board.getInt("Exchange."+TempName+"."+RegisterUUID+".GiveItemAmount");
					short GetAmount = (short) Board.getInt("Exchange."+TempName+"."+RegisterUUID+".WantItemAmount");
					BoardAddedItem.setAmount(GetAmount);
					BoardGiveItem.setAmount(GiveAmount);
					new GoldBigDragon_RPG.Structure.Structure_PostBox().SendPost_Server(RegisterUUID, "[거래 게시판]", "[물물 교환 영수증]",
							player.getName()+"님께서 ["+ChatColor.stripColor(ItemName)+"] 아이템 "+GetAmount+"개를 당신의 ["+ChatColor.stripColor(BoardGiveItemName)+"] 아이템 "+GiveAmount+"개와 교환 하였습니다.", BoardAddedItem);

					new GoldBigDragon_RPG.Structure.Structure_PostBox().SendPost_Server(player.getUniqueId().toString(), "[거래 게시판]", "[물물 교환 영수증]",
							"당신은 ["+ChatColor.stripColor(ItemName)+"] 아이템 "+GetAmount+"개를 "+Target.getName()+"의 ["+ChatColor.stripColor(BoardGiveItemName)+"] 아이템 "+GiveAmount+"개와 교환 하였습니다.", BoardGiveItem);
					ItemName = TempName;
					if(Board.getConfigurationSection("Exchange."+ItemName).getKeys(false).size()==2)
						Board.removeKey("Exchange."+ItemName);
					else
						Board.removeKey("Exchange."+ItemName+"."+RegisterUUID);
					Board.set("ExchangeRegistered", Board.getInt("ExchangeRegistered")-1);
					YamlManager USRL =YC.getNewConfig("Structure/UserShopRegisterList.yml");
					if(USRL.contains(player.getUniqueId().toString())==true)
					{
						USRL.set(Target.getUniqueId().toString(), USRL.getInt(Target.getUniqueId().toString())-1);
						USRL.saveConfig();
					}
					Board.saveConfig();
					
					if(Target!=null)
						if(Target.isOnline())
						{
							s.SP(Target, Sound.VILLAGER_YES, 1.0F, 1.0F);
							new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(Target, "\'§3[거래 성사]\'","\'§3거래 게시판에 의뢰한 물품이 도착하였습니다.\'", (byte)1, (byte)3, (byte)1);
						}
					
					BoardAddedItem.setAmount(1);
					for(byte count=0; count<player.getInventory().getSize();count++)
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
					TradeBoardMainGUI(player, (byte) page, ShopType);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 교환 완료! 우편함을 확인 해 주세요!");
				}
				
			}
		}
	}

	public void TradeBoardSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		if(event.getSlot()>=1 && event.getSlot() <= 3)
		{
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			Object_UserData u = new Object_UserData();
			u.setTemp(player, "Structure");
			u.setType(player, "TradeBoard");
			u.setString(player, (byte)0, "TradeBoardSetting");
			if(event.getSlot()==1)
			{
				u.setString(player, (byte)1, "SellCommission");
				player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 판매 수수료를 몇 %로 하시겠습니까? (0 ~ 100)");
			}
			if(event.getSlot()==2)
			{
				u.setString(player, (byte)1, "RegisterCommission");
				player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 등록 수수료를 몇 "+GoldBigDragon_RPG.Main.ServerOption.Money+ChatColor.GREEN+"로 하시겠습니까? (0 ~ 2백만)");
			}
			if(event.getSlot()==3)
			{
				u.setString(player, (byte)1, "LimitPerPlayer");
				player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 1인당 최대 몇 개까지 등록 가능하게 하시겠습니까? (1 ~ 100)");
			}
		}
		
		switch (event.getSlot())
		{
		case 0://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.Structure.StructureGUI().StructureListGUI(player, 0);
			return;
		case 8://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		return;
	}

	public void SelectTradeTypeGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getSlot()==1||event.getSlot()==3||event.getSlot()==5)
		{
			YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager USRL =YC.getNewConfig("Structure/UserShopRegisterList.yml");
			if(USRL.contains(player.getUniqueId().toString())==true)
			{
				YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");
				if(USRL.getInt(player.getUniqueId().toString())>=Board.getInt("LimitPerPlayer"))
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[거래 게시판] : 당신은 더 이상 등록할 수 없습니다! (최대 "+Board.getInt("LimitPerPlayer")+"개만 등록 가능합니다.)");
					return;
				}
			}
			player.closeInventory();
			YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");

			
			s.SP(player, Sound.ORB_PICKUP, 0.8F, 1.8F);
			Object_UserData u = new Object_UserData();
			u.setTemp(player, "Structure");
			u.setType(player, "TradeBoard");
			u.setString(player, (byte)0, "Notice");
			u.setInt(player, (byte)0, event.getSlot());//거래 타입

			if(event.getSlot()==1)
			{
				player.sendMessage(ChatColor.YELLOW+"[거래 게시판] : 거래 게시판을 통해 물품을 판매할 경우 아래와 같은 등록 비용과 판매 수수료가 붙습니다.");
				player.sendMessage(ChatColor.YELLOW+"[판매 수수료] : "+Board.getInt("SellCommission")+" %");
			}
			if(event.getSlot()==3)
				player.sendMessage(ChatColor.YELLOW+"[거래 게시판] : 거래 게시판을 통해 물품을 구매할 경우 아래와 같은 등록 비용이 붙습니다.");
			if(event.getSlot()==5)
				player.sendMessage(ChatColor.YELLOW+"[거래 게시판] : 거래 게시판을 통해 물품을 교환할 경우 아래와 같은 등록 비용이 붙습니다.");
			player.sendMessage(ChatColor.YELLOW+"[등록 수수료] : "+Board.getInt("RegisterCommission")+ " "+new GoldBigDragon_RPG.Main.ServerOption().Money);
			player.sendMessage(ChatColor.YELLOW+"[거래 게시판] : 그래도 계속 하시겠습니까? (네 / 아니오)");
		}
		switch (event.getSlot())
		{
		case 0://이전 페이지
			s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
			TradeBoardMainGUI(player, (byte)0, (byte)0);
			return;
		case 7://등록 확인
			s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
			return;
		case 8://화면 닫기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.0F);
			player.closeInventory();
			return;
		}
	}
	
	public void SelectSellItemGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		Object_UserData u = new Object_UserData();
		if(event.getCurrentItem()!=null)
		{
			if(event.getCurrentItem().getTypeId()!=0)
			{
				ItemStack item = event.getCurrentItem();
				if(item.hasItemMeta())
					if(item.getItemMeta().hasDisplayName())
						if(item.getItemMeta().getDisplayName().compareTo(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.RED + "" + ChatColor.BOLD + "[판매 취소]")==0)
							u.clearAll(player);
				if(u.getString(player, (byte)0).compareTo("SelectItem")==0)
				{
					u.setItemStack(player, item);
					u.setString(player, (byte)0, "SetPrice");
					if(item.getAmount()!=1)
						player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 1개당 얼마에 판매 되기를 원하시나요?");
					else
						player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 얼마에 판매 되기를 원하시나요?");
				}
				else
					u.clearAll(player);
				s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.8F);
				player.closeInventory();
			}
		}
	}
	
	public void SelectBuyItemGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		Object_UserData u = new Object_UserData();
		if(event.getCurrentItem()!=null)
		{
			if(event.getCurrentItem().getTypeId()!=0)
			{
				ItemStack item = event.getCurrentItem();
				if(event.getSlot()==4)
					if(item.hasItemMeta())
						if(item.getItemMeta().hasDisplayName())
							if(item.getItemMeta().getDisplayName().compareTo(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.RED + "" + ChatColor.BOLD + "[구매 취소]")==0)
								u.clearAll(player);
				if(u.getString(player, (byte)0).compareTo("SelectItem")==0)
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
					if(event.getSlot()==2)
						if(item.hasItemMeta())
							if(item.getItemMeta().hasDisplayName())
								if(item.getItemMeta().getDisplayName().compareTo(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.WHITE + "" + ChatColor.BOLD + "[일반 아이템]")==0)
								{
									SelectNormalItemGUI(player, (byte)0, (byte)3);
									return;
								}
					byte amount = (byte) item.getAmount();
					item.setAmount(1);
					u.setItemStack(player, item);
					item.setAmount(amount);
					u.setString(player, (byte)0, "SetNeedAmount");
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 얼마나 구매하실건가요? (1 ~ 1000)");
				}
				else
					u.clearAll(player);
				s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.8F);
				player.closeInventory();
			}
		}
	}
	
	public void SelectNormalItemGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		byte page =  (byte) (Byte.parseByte(event.getInventory().getTitle().split(" : ")[1])-1);
		byte ShopType = Byte.parseByte(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()!=0)
			switch (event.getSlot())
			{
				case 48://이전 페이지
					s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
					SelectNormalItemGUI(player, (byte) (page-1), ShopType);
					return;
				case 50://다음 페이지
					s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
					SelectNormalItemGUI(player, (byte) (page+1), ShopType);
					return;
				case 53://닫기
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					player.closeInventory();
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					Object_UserData u = new Object_UserData();
					u.setInt(player, (byte)0,ShopType);
					u.setTemp(player, "Structure");
					u.setType(player, "TradeBoard");
					u.setItemStack(player, event.getCurrentItem());
					u.setString(player, (byte)0, "SetNeedAmount");
					u.setBoolean(player, (byte)1, true);
					if(ShopType==3)
						player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 얼마나 구매하실건가요? (1 ~ 1000)");
					else if(ShopType==5)
						player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 해당 아이템이 얼마나 필요한가요? (1 ~ 1000)");
						
					return;
			}
	}
	
	public void SelectExchangeItem_YouGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		Object_UserData u = new Object_UserData();
		if(event.getCurrentItem()!=null)
		{
			if(event.getCurrentItem().getTypeId()!=0)
			{
				ItemStack item = event.getCurrentItem();
				
				if(event.getSlot()==4)
					if(item.hasItemMeta())
						if(item.getItemMeta().hasDisplayName())
							if(item.getItemMeta().getDisplayName().compareTo(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.RED + "" + ChatColor.BOLD + "[구매 취소]")==0)
								u.clearAll(player);
				if(u.getString(player, (byte)0).compareTo("SelectItem")==0)
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
					if(event.getSlot()==2)
						if(item.hasItemMeta())
							if(item.getItemMeta().hasDisplayName())
								if(item.getItemMeta().getDisplayName().compareTo(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.WHITE + "" + ChatColor.BOLD + "[일반 아이템]")==0)
								{
									SelectNormalItemGUI(player, (byte)0, (byte)5);
									return;
								}
					byte amount = (byte) item.getAmount();
					u.setInt(player, (byte)0,5);
					u.setTemp(player, "Structure");
					u.setType(player, "TradeBoard");
					u.setInt(player, (byte) 2, amount);
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 당신은 무엇을 주실건가요?");
					u.setString(player, (byte)0, "SetMyItem");
					new Structure_TradeBoard().SelectExchangeItem_MyGUI(player);
					item.setAmount(1);
					u.setItemStack(player, item);
					item.setAmount(amount);
					return;
				}
				else
					u.clearAll(player);
				s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.8F);
				player.closeInventory();
			}
		}
	}
	
	public void SelectExchangeItem_MyGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		Object_UserData u = new Object_UserData();
		if(event.getCurrentItem()!=null)
		{
			if(event.getCurrentItem().getTypeId()!=0)
			{
				ItemStack item = event.getCurrentItem();
				byte amount = (byte)item.getAmount();
				item.setAmount(1);
				if(event.getSlot()==4)
					if(item.hasItemMeta())
						if(item.getItemMeta().hasDisplayName())
							if(item.getItemMeta().getDisplayName().compareTo(ChatColor.RED + "" +ChatColor.RED + "" +ChatColor.RED + "" + ChatColor.BOLD + "[구매 취소]")==0)
								u.clearAll(player);
				if(u.getString(player, (byte)0).compareTo("SetMyItem")==0)
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);

					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager Board =YC.getNewConfig("Structure/UserShopBoard.yml");
					if(ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()<Board.getInt("RegisterCommission"))
					{
						u.clearAll(player);
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
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
					if(Board.contains("Exchange."+ItemName+"."+player.getUniqueId().toString()))
					{
						u.clearAll(player);
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 동일 상품을 이미 등록하셨습니다!");
						return;
					}
					if(Board.contains("Exchange."+ItemName)==false)
						Board.set("Exchange."+ItemName+".Item", event.getCurrentItem());
					Board.set("ExchangeRegistered", Board.getInt("ExchangeRegistered")+1);
					Board.set("Exchange."+ItemName+"."+player.getUniqueId().toString()+".Name", player.getName());
					Board.set("Exchange."+ItemName+"."+player.getUniqueId().toString()+".WantItem", u.getItemStack(player));
					Board.set("Exchange."+ItemName+"."+player.getUniqueId().toString()+".WantItemAmount", u.getInt(player,(byte)2));

					Board.set("Exchange."+ItemName+"."+player.getUniqueId().toString()+".GiveItem", item);
					Board.set("Exchange."+ItemName+"."+player.getUniqueId().toString()+".GiveItemAmount", amount);
					Board.saveConfig();

					ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Money(ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() - Board.getInt("RegisterCommission"));
					YamlManager USRL =YC.getNewConfig("Structure/UserShopRegisterList.yml");
					USRL.set(player.getUniqueId().toString(), USRL.getInt(player.getUniqueId().toString())+1);
					USRL.saveConfig();
					u.clearAll(player);
					player.closeInventory();
					s.SP(player, Sound.CHEST_OPEN, 1.0F, 1.8F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 등록이 완료되었습니다!");
					player.getInventory().setItem(event.getSlot(), null);
					return;
				}
				else
					u.clearAll(player);
				s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.8F);
				player.closeInventory();
			}
		}
	}
	
	
	public String CreateTradeBoard(int LineNumber, String StructureCode, byte Direction)
	{
		switch(Direction)
		{
		case 1://동
		case 3://서
			switch(LineNumber)
			{
			case 0:
				return "/summon ArmorStand ~-0.216 ~0.57 ~-0.20 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 1:
				return "summon ArmorStand ~-0.5 ~1.07 ~-0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:blaze_rod,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 2:
				return "summon ArmorStand ~-0.5 ~1.896 ~-0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:blaze_rod,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 3:
				return "/summon ArmorStand ~-0.216 ~2.288 ~-0.26 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 4:
				return "/summon ArmorStand ~-0.216 ~0.57 ~1.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 5:
				return "summon ArmorStand ~-0.5 ~1.07 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:blaze_rod,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 6:
				return "summon ArmorStand ~-0.5 ~1.896 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:blaze_rod,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 7:
				return "/summon ArmorStand ~-0.216 ~2.288 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			if(LineNumber<=22) //판떼기
			{
				if(LineNumber<=10)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-7)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=13)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-10)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=16)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-13)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=19)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-16)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=22)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-19)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			switch(LineNumber)
			{
			case 23:
				return "/summon ArmorStand ~-0.046 ~2.628 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone_slab,Damage:6,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 24:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 25:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 26:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 27:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 28:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 29:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.60 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone_slab,Damage:6,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

			case 30:
				return "/summon ArmorStand ~-0.386 ~2.628 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone_slab,Damage:6,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 31:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 32:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 33:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 34:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 35:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 36:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.60 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone_slab,Damage:6,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 5://남
		case 7://북
			switch(LineNumber)
			{
			case 0:
				return "/summon ArmorStand ~-0.20 ~0.57 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 1:
				return "summon ArmorStand ~-0.14 ~1.07 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:blaze_rod,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 2:
				return "summon ArmorStand ~-0.14 ~1.896 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:blaze_rod,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 3:
				return "/summon ArmorStand ~-0.26 ~2.288 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 4:
				return "/summon ArmorStand ~1.76 ~0.57 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 5:
				return "summon ArmorStand ~1.80 ~1.07 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:blaze_rod,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 6:
				return "summon ArmorStand ~1.80 ~1.896 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:blaze_rod,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 7:
				return "/summon ArmorStand ~1.80 ~2.288 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			if(LineNumber<=22) //판떼기
			{
				if(LineNumber<=10)
					return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-7)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=13)
					return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-10)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=16)
					return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-13)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=19)
					return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-16)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=22)
					return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-19)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:quartz_block,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			switch(LineNumber)
			{
			case 23:
				return "/summon ArmorStand ~-0.28 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone_slab,Damage:6,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 24:
				return "/summon ArmorStand ~0.08 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 25:
				return "/summon ArmorStand ~0.42 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 26:
				return "/summon ArmorStand ~0.76 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 27:
				return "/summon ArmorStand ~1.10 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 28:
				return "/summon ArmorStand ~1.46 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 29:
				return "/summon ArmorStand ~1.60 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone_slab,Damage:6,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

			case 30:
				return "/summon ArmorStand ~-0.28 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone_slab,Damage:6,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 31:
				return "/summon ArmorStand ~0.08 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 32:
				return "/summon ArmorStand ~0.42 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 33:
				return "/summon ArmorStand ~0.76 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 34:
				return "/summon ArmorStand ~1.10 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 35:
				return "/summon ArmorStand ~1.46 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 36:
				return "/summon ArmorStand ~1.60 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone_slab,Damage:6,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		}

		switch(Direction)
		{
			case 1://동
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-36)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-40)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-43)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-46)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-49)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~-0.51 ~1.63 ~1.5 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~-0.51 ~1.63 ~0.9 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~-0.51 ~1.63 ~0.3 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~-0.51 ~1.03 ~1.5 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 57:
					return "/summon ArmorStand ~-0.51 ~1.03 ~0.9 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 58:
					return "/summon ArmorStand ~-0.51 ~1.03 ~0.3 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 59:
					return "/summon ArmorStand ~-0.36 ~2 ~0.9 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:gold_ingot,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 60:
					return "/summon ArmorStand ~0.0 ~0.5 ~0.9 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 3://서
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-36)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[180f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-40)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[180f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-43)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[180f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-46)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[180f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-49)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[180f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~-0.91 ~1.63 ~1.52 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~-0.91 ~1.63 ~0.92 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~-0.91 ~1.63 ~0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~-0.91 ~1.03 ~1.52 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 57:
					return "/summon ArmorStand ~-0.91 ~1.03 ~0.92 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 58:
					return "/summon ArmorStand ~-0.91 ~1.03 ~0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 59:
					return "/summon ArmorStand ~-1.08 ~2 ~0.9 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[0f,0.0f],Equipment:[{id:gold_ingot,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 60:
					return "/summon ArmorStand ~-1.0 ~0.5 ~1.1 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 5://남
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-36)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-40)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-43)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-46)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-49)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~1.64 ~1.63 ~0 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[90f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~1.04 ~1.63 ~0 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[90f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~0.44 ~1.63 ~0 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[90f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~1.64 ~1.03 ~0 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[90f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 57:
					return "/summon ArmorStand ~1.04 ~1.03 ~0 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[90f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 58:
					return "/summon ArmorStand ~0.44 ~1.03 ~0 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[90f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 59:
					return "/summon ArmorStand ~1.04 ~2 ~0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[90f,0.0f],Equipment:[{id:gold_ingot,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 60:
					return "/summon ArmorStand ~1.0 ~0.5 ~0.5 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 7://북
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-36)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-40)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-43)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-46)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-49)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:nether_brick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~1.52 ~1.63 ~0.1 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[270f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~0.92 ~1.63 ~0.1 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[270f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~0.32 ~1.63 ~0.1 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[270f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~1.52 ~1.03 ~0.1 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[270f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 57:
					return "/summon ArmorStand ~0.92 ~1.03 ~0.1 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[270f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 58:
					return "/summon ArmorStand ~0.32 ~1.03 ~0.1 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[270f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 59:
					return "/summon ArmorStand ~0.9 ~2 ~-0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[270f,0.0f],Equipment:[{id:gold_ingot,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 60:
					return "/summon ArmorStand ~0.96 ~0.5 ~-0.5 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
		}
		return "null";
	}


	
	
	public ItemStack getNormalItem(int number)
	{
		int ID = -1;
		byte DATA = 0;
		switch(number)
		{
		case 0: ID=1;DATA=0;break;
		case 1: ID=1;DATA=1;break;
		case 2: ID=1;DATA=2;break;
		case 3: ID=1;DATA=3;break;
		case 4: ID=1;DATA=4;break;
		case 5: ID=1;DATA=5;break;
		case 6: ID=1;DATA=6;break;
		case 7: ID=2;DATA=0;break;
		case 8: ID=3;DATA=0;break;
		case 9: ID=3;DATA=1;break;
		case 10: ID=3;DATA=2;break;
		case 11: ID=4;DATA=0;break;
		case 12: ID=5;DATA=0;break;
		case 13: ID=5;DATA=1;break;
		case 14: ID=5;DATA=2;break;
		case 15: ID=5;DATA=3;break;
		case 16: ID=5;DATA=4;break;
		case 17: ID=5;DATA=5;break;
		case 18: ID=6;DATA=0;break;
		case 19: ID=6;DATA=1;break;
		case 20: ID=6;DATA=2;break;
		case 21: ID=6;DATA=3;break;
		case 22: ID=6;DATA=4;break;
		case 23: ID=6;DATA=5;break;
		case 24: ID=7;DATA=0;break;
		case 25: ID=12;DATA=0;break;
		case 26: ID=12;DATA=1;break;
		case 27: ID=13;DATA=0;break;
		case 28: ID=14;DATA=0;break;
		case 29: ID=15;DATA=0;break;
		case 30: ID=16;DATA=0;break;
		case 31: ID=17;DATA=0;break;
		case 32: ID=17;DATA=1;break;
		case 33: ID=17;DATA=2;break;
		case 34: ID=17;DATA=3;break;
		case 35: ID=18;DATA=0;break;
		case 36: ID=18;DATA=1;break;
		case 37: ID=18;DATA=2;break;
		case 38: ID=18;DATA=3;break;
		case 39: ID=19;DATA=0;break;
		case 40: ID=19;DATA=1;break;
		case 41: ID=20;DATA=0;break;
		case 42: ID=21;DATA=0;break;
		case 43: ID=22;DATA=0;break;
		case 44: ID=23;DATA=0;break;
		case 45: ID=24;DATA=0;break;
		case 46: ID=24;DATA=1;break;
		case 47: ID=24;DATA=2;break;
		case 48: ID=25;DATA=0;break;
		case 49: ID=27;DATA=0;break;
		case 50: ID=28;DATA=0;break;
		case 51: ID=29;DATA=0;break;
		case 52: ID=30;DATA=0;break;
		case 53: ID=31;DATA=1;break;
		case 54: ID=31;DATA=2;break;
		case 55: ID=32;DATA=0;break;
		case 56: ID=33;DATA=0;break;
		case 57: ID=35;DATA=0;break;
		case 58: ID=35;DATA=1;break;
		case 59: ID=35;DATA=2;break;
		case 60: ID=35;DATA=3;break;
		case 61: ID=35;DATA=4;break;
		case 62: ID=35;DATA=5;break;
		case 63: ID=35;DATA=6;break;
		case 64: ID=35;DATA=7;break;
		case 65: ID=35;DATA=8;break;
		case 66: ID=35;DATA=9;break;
		case 67: ID=35;DATA=10;break;
		case 68: ID=35;DATA=11;break;
		case 69: ID=35;DATA=12;break;
		case 70: ID=35;DATA=13;break;
		case 71: ID=35;DATA=14;break;
		case 72: ID=35;DATA=15;break;
		case 73: ID=37;DATA=0;break;
		case 74: ID=38;DATA=0;break;
		case 75: ID=38;DATA=1;break;
		case 76: ID=38;DATA=2;break;
		case 77: ID=38;DATA=3;break;
		case 78: ID=38;DATA=4;break;
		case 79: ID=38;DATA=5;break;
		case 80: ID=38;DATA=6;break;
		case 81: ID=38;DATA=7;break;
		case 82: ID=38;DATA=8;break;
		case 83: ID=39;DATA=0;break;
		case 84: ID=40;DATA=0;break;
		case 85: ID=41;DATA=0;break;
		case 86: ID=42;DATA=0;break;
		case 87: ID=44;DATA=0;break;
		case 88: ID=44;DATA=1;break;
		case 89: ID=44;DATA=3;break;
		case 90: ID=44;DATA=4;break;
		case 91: ID=44;DATA=5;break;
		case 92: ID=44;DATA=6;break;
		case 93: ID=44;DATA=7;break;
		case 94: ID=45;DATA=0;break;
		case 95: ID=46;DATA=0;break;
		case 96: ID=47;DATA=0;break;
		case 97: ID=48;DATA=0;break;
		case 98: ID=49;DATA=0;break;
		case 99: ID=50;DATA=0;break;
		case 100: ID=53;DATA=0;break;
		case 101: ID=54;DATA=0;break;
		case 102: ID=56;DATA=0;break;
		case 103: ID=57;DATA=0;break;
		case 104: ID=58;DATA=0;break;
		case 105: ID=61;DATA=0;break;
		case 106: ID=65;DATA=0;break;
		case 107: ID=66;DATA=0;break;
		case 108: ID=67;DATA=0;break;
		case 109: ID=69;DATA=0;break;
		case 110: ID=70;DATA=0;break;
		case 111: ID=72;DATA=0;break;
		case 112: ID=73;DATA=0;break;
		case 113: ID=76;DATA=0;break;
		case 114: ID=77;DATA=0;break;
		case 115: ID=78;DATA=0;break;
		case 116: ID=79;DATA=0;break;
		case 117: ID=80;DATA=0;break;
		case 118: ID=81;DATA=0;break;
		case 119: ID=82;DATA=0;break;
		case 120: ID=84;DATA=0;break;
		case 121: ID=85;DATA=0;break;
		case 122: ID=86;DATA=0;break;
		case 123: ID=87;DATA=0;break;
		case 124: ID=88;DATA=0;break;
		case 125: ID=89;DATA=0;break;
		case 126: ID=91;DATA=0;break;
		case 127: ID=95;DATA=0;break;
		case 128: ID=95;DATA=1;break;
		case 129: ID=95;DATA=2;break;
		case 130: ID=95;DATA=3;break;
		case 131: ID=95;DATA=4;break;
		case 132: ID=95;DATA=5;break;
		case 133: ID=95;DATA=6;break;
		case 134: ID=95;DATA=7;break;
		case 135: ID=95;DATA=8;break;
		case 136: ID=95;DATA=9;break;
		case 137: ID=95;DATA=10;break;
		case 138: ID=95;DATA=11;break;
		case 139: ID=95;DATA=12;break;
		case 140: ID=95;DATA=13;break;
		case 141: ID=95;DATA=14;break;
		case 142: ID=95;DATA=15;break;
		case 143: ID=96;DATA=0;break;
		case 144: ID=98;DATA=0;break;
		case 145: ID=98;DATA=1;break;
		case 146: ID=98;DATA=2;break;
		case 147: ID=98;DATA=3;break;
		case 148: ID=99;DATA=0;break;
		case 149: ID=100;DATA=0;break;
		case 150: ID=101;DATA=0;break;
		case 151: ID=102;DATA=0;break;
		case 152: ID=103;DATA=0;break;
		case 153: ID=106;DATA=0;break;
		case 154: ID=107;DATA=0;break;
		case 155: ID=108;DATA=0;break;
		case 156: ID=109;DATA=0;break;
		case 157: ID=110;DATA=0;break;
		case 158: ID=111;DATA=0;break;
		case 159: ID=112;DATA=0;break;
		case 160: ID=113;DATA=0;break;
		case 161: ID=114;DATA=0;break;
		case 162: ID=116;DATA=0;break;
		case 163: ID=120;DATA=0;break;
		case 164: ID=121;DATA=0;break;
		case 165: ID=122;DATA=0;break;
		case 166: ID=123;DATA=0;break;
		case 167: ID=126;DATA=0;break;
		case 168: ID=126;DATA=1;break;
		case 169: ID=126;DATA=2;break;
		case 170: ID=126;DATA=3;break;
		case 171: ID=126;DATA=4;break;
		case 172: ID=126;DATA=5;break;
		case 173: ID=128;DATA=0;break;
		case 174: ID=129;DATA=0;break;
		case 175: ID=130;DATA=0;break;
		case 176: ID=133;DATA=0;break;
		case 177: ID=134;DATA=0;break;
		case 178: ID=135;DATA=0;break;
		case 179: ID=136;DATA=0;break;
		case 180: ID=138;DATA=0;break;
		case 181: ID=139;DATA=0;break;
		case 182: ID=145;DATA=0;break;
		case 183: ID=145;DATA=1;break;
		case 184: ID=145;DATA=2;break;
		case 185: ID=146;DATA=0;break;
		case 186: ID=147;DATA=0;break;
		case 187: ID=148;DATA=0;break;
		case 188: ID=151;DATA=0;break;
		case 189: ID=152;DATA=0;break;
		case 190: ID=153;DATA=0;break;
		case 191: ID=154;DATA=0;break;
		case 192: ID=155;DATA=0;break;
		case 193: ID=155;DATA=1;break;
		case 194: ID=155;DATA=2;break;
		case 195: ID=156;DATA=0;break;
		case 196: ID=157;DATA=0;break;
		case 197: ID=158;DATA=0;break;
		case 198: ID=159;DATA=0;break;
		case 199: ID=159;DATA=1;break;
		case 200: ID=159;DATA=2;break;
		case 201: ID=159;DATA=3;break;
		case 202: ID=159;DATA=4;break;
		case 203: ID=159;DATA=5;break;
		case 204: ID=159;DATA=6;break;
		case 205: ID=159;DATA=7;break;
		case 206: ID=159;DATA=8;break;
		case 207: ID=159;DATA=9;break;
		case 208: ID=159;DATA=10;break;
		case 209: ID=159;DATA=11;break;
		case 210: ID=159;DATA=12;break;
		case 211: ID=159;DATA=13;break;
		case 212: ID=159;DATA=14;break;
		case 213: ID=159;DATA=15;break;
		case 214: ID=160;DATA=0;break;
		case 215: ID=160;DATA=1;break;
		case 216: ID=160;DATA=2;break;
		case 217: ID=160;DATA=3;break;
		case 218: ID=160;DATA=4;break;
		case 219: ID=160;DATA=5;break;
		case 220: ID=160;DATA=6;break;
		case 221: ID=160;DATA=7;break;
		case 222: ID=160;DATA=8;break;
		case 223: ID=160;DATA=9;break;
		case 224: ID=160;DATA=10;break;
		case 225: ID=160;DATA=11;break;
		case 226: ID=160;DATA=12;break;
		case 227: ID=160;DATA=13;break;
		case 228: ID=160;DATA=14;break;
		case 229: ID=160;DATA=15;break;
		case 230: ID=161;DATA=0;break;
		case 231: ID=161;DATA=1;break;
		case 232: ID=162;DATA=0;break;
		case 233: ID=162;DATA=1;break;
		case 234: ID=163;DATA=0;break;
		case 235: ID=164;DATA=0;break;
		case 236: ID=165;DATA=0;break;
		case 237: ID=167;DATA=0;break;
		case 238: ID=168;DATA=0;break;
		case 239: ID=168;DATA=1;break;
		case 240: ID=168;DATA=2;break;
		case 241: ID=169;DATA=0;break;
		case 242: ID=170;DATA=0;break;
		case 243: ID=171;DATA=0;break;
		case 244: ID=171;DATA=1;break;
		case 245: ID=171;DATA=2;break;
		case 246: ID=171;DATA=3;break;
		case 247: ID=171;DATA=4;break;
		case 248: ID=171;DATA=5;break;
		case 249: ID=171;DATA=6;break;
		case 250: ID=171;DATA=7;break;
		case 251: ID=171;DATA=8;break;
		case 252: ID=171;DATA=9;break;
		case 253: ID=171;DATA=10;break;
		case 254: ID=171;DATA=11;break;
		case 255: ID=171;DATA=12;break;
		case 256: ID=171;DATA=13;break;
		case 257: ID=171;DATA=14;break;
		case 258: ID=171;DATA=15;break;
		case 259: ID=172;DATA=0;break;
		case 260: ID=173;DATA=0;break;
		case 261: ID=174;DATA=0;break;
		case 262: ID=175;DATA=0;break;
		case 263: ID=175;DATA=1;break;
		case 264: ID=175;DATA=2;break;
		case 265: ID=175;DATA=3;break;
		case 266: ID=175;DATA=4;break;
		case 267: ID=175;DATA=5;break;
		case 268: ID=179;DATA=0;break;
		case 269: ID=179;DATA=1;break;
		case 270: ID=179;DATA=2;break;
		case 271: ID=180;DATA=0;break;
		case 272: ID=182;DATA=0;break;
		case 273: ID=183;DATA=0;break;
		case 274: ID=184;DATA=0;break;
		case 275: ID=185;DATA=0;break;
		case 276: ID=186;DATA=0;break;
		case 277: ID=187;DATA=0;break;
		case 278: ID=188;DATA=0;break;
		case 279: ID=189;DATA=0;break;
		case 280: ID=190;DATA=0;break;
		case 281: ID=191;DATA=0;break;
		case 282: ID=192;DATA=0;break;
		case 283: ID=256;DATA=0;break;
		case 284: ID=257;DATA=0;break;
		case 285: ID=258;DATA=0;break;
		case 286: ID=259;DATA=0;break;
		case 287: ID=260;DATA=0;break;
		case 288: ID=261;DATA=0;break;
		case 289: ID=262;DATA=0;break;
		case 290: ID=263;DATA=0;break;
		case 291: ID=263;DATA=1;break;
		
		case 350: ID=322;DATA=0;break;
		case 351: ID=322;DATA=1;break;
		
		case 377: ID=349;DATA=0;break;
		case 378: ID=349;DATA=1;break;
		case 379: ID=349;DATA=2;break;
		case 380: ID=349;DATA=3;break;
		case 381: ID=350;DATA=0;break;
		case 382: ID=350;DATA=1;break;
		case 383: ID=351;DATA=0;break;
		case 384: ID=351;DATA=1;break;
		case 385: ID=351;DATA=2;break;
		case 386: ID=351;DATA=3;break;
		case 387: ID=351;DATA=4;break;
		case 388: ID=351;DATA=5;break;
		case 389: ID=351;DATA=6;break;
		case 390: ID=351;DATA=7;break;
		case 391: ID=351;DATA=8;break;
		case 392: ID=351;DATA=9;break;
		case 393: ID=351;DATA=10;break;
		case 394: ID=351;DATA=11;break;
		case 395: ID=351;DATA=12;break;
		case 396: ID=351;DATA=13;break;
		case 397: ID=351;DATA=14;break;
		case 398: ID=351;DATA=15;break;
		case 399: ID=352;DATA=0;break;
		case 400: ID=353;DATA=0;break;
		case 401: ID=354;DATA=0;break;
		case 402: ID=355;DATA=0;break;
		case 403: ID=356;DATA=0;break;
		case 404: ID=357;DATA=0;break;
		case 405: ID=359;DATA=0;break;
		case 406: ID=360;DATA=0;break;
		case 407: ID=361;DATA=0;break;
		case 408: ID=362;DATA=0;break;
		case 409: ID=363;DATA=0;break;
		case 410: ID=364;DATA=0;break;
		case 411: ID=365;DATA=0;break;
		case 412: ID=366;DATA=0;break;
		case 413: ID=367;DATA=0;break;
		case 414: ID=368;DATA=0;break;
		case 415: ID=369;DATA=0;break;
		case 416: ID=370;DATA=0;break;
		case 417: ID=371;DATA=0;break;
		case 418: ID=372;DATA=0;break;
		case 419: ID=373;DATA=0;break;
		case 420: ID=374;DATA=0;break;
		case 421: ID=375;DATA=0;break;
		case 422: ID=376;DATA=0;break;
		case 423: ID=377;DATA=0;break;
		case 424: ID=378;DATA=0;break;
		case 425: ID=379;DATA=0;break;
		case 426: ID=380;DATA=0;break;
		case 427: ID=381;DATA=0;break;
		case 428: ID=382;DATA=0;break;
		case 429: ID=384;DATA=0;break;
		case 430: ID=385;DATA=0;break;
		case 431: ID=386;DATA=0;break;
		case 432: ID=388;DATA=0;break;
		case 433: ID=389;DATA=0;break;
		case 434: ID=390;DATA=0;break;
		case 435: ID=391;DATA=0;break;
		case 436: ID=392;DATA=0;break;
		case 437: ID=393;DATA=0;break;
		case 438: ID=394;DATA=0;break;
		case 439: ID=395;DATA=0;break;
		case 440: ID=396;DATA=0;break;
		case 441: ID=397;DATA=0;break;
		case 442: ID=397;DATA=1;break;
		case 443: ID=397;DATA=2;break;
		case 444: ID=397;DATA=3;break;
		case 445: ID=397;DATA=4;break;
		case 446: ID=398;DATA=0;break;
		case 447: ID=399;DATA=0;break;
		case 448: ID=400;DATA=0;break;
		case 449: ID=402;DATA=0;break;
		case 450: ID=404;DATA=0;break;
		case 451: ID=405;DATA=0;break;
		case 452: ID=406;DATA=0;break;
		case 453: ID=407;DATA=0;break;
		case 454: ID=408;DATA=0;break;
		case 455: ID=409;DATA=0;break;
		case 456: ID=410;DATA=0;break;
		case 457: ID=411;DATA=0;break;
		case 458: ID=412;DATA=0;break;
		case 459: ID=413;DATA=0;break;
		case 460: ID=414;DATA=0;break;
		case 461: ID=415;DATA=0;break;
		case 462: ID=416;DATA=0;break;
		case 463: ID=417;DATA=0;break;
		case 464: ID=418;DATA=0;break;
		case 465: ID=419;DATA=0;break;
		case 466: ID=420;DATA=0;break;
		case 467: ID=421;DATA=0;break;
		case 468: ID=423;DATA=0;break;
		case 469: ID=424;DATA=0;break;
		case 470: ID=425;DATA=0;break;
		case 471: ID=427;DATA=0;break;
		case 472: ID=428;DATA=0;break;
		case 473: ID=429;DATA=0;break;
		case 474: ID=430;DATA=0;break;
		case 475: ID=2256;DATA=0;break;
		case 476: ID=2257;DATA=0;break;
		case 477: ID=2258;DATA=0;break;
		case 478: ID=2259;DATA=0;break;
		case 479: ID=2260;DATA=0;break;
		case 480: ID=2261;DATA=0;break;
		case 481: ID=2262;DATA=0;break;
		case 482: ID=2263;DATA=0;break;
		case 483: ID=2264;DATA=0;break;
		case 484: ID=2265;DATA=0;break;
		case 485: ID=2266;DATA=0;break;
		case 486: ID=2267;DATA=0;break;
		}
		if(number>=292&&number<=349)
		{
			ID=number-28;
			DATA=0;
		}
		else if(number>=351&&number<=376)
		{
			ID=number-29;
			DATA=0;
		}
		if(ID==-1)
			return null;

		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(1);
		
		return Icon;
	}
}