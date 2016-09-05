package GoldBigDragon_RPG.Structure;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import GoldBigDragon_RPG.Dungeon.Schematic;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;

public class StructureMain
{
	/*
	커맨드 블록 중앙에 돌 놓기
	/summon ArmorStand ~-0.18 ~-0.348 ~-0.28 {ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}
	
	그 바로 위에 돌 놓기
	/summon ArmorStand ~-0.18 ~0.008 ~-0.28 {ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stone,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}
	아머 스탠드의 손에 쥐어진 블록의 거리 차는 0.34씩임.
	
	커맨드 블록 중앙에 서 있는 막대 놓기
	/summon ArmorStand ~-0.14 ~0.07 ~0.12 {ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}
	
	그 바로 위에 막대 놓기
	/summon ArmorStand ~-0.14 ~0.896 ~0.12 {ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}
	아머 스탠드의 손에 쥐어진 막대기 거리 차는 세로 0.826임.
	
	막대기 옆에 블록 놓기
	/summon ArmorStand ~0.08 ~0.268 ~-0.216 {ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}
	
	아이템 프레임
	/summon ArmorStand ~0.3 ~1.63 ~1.08 {ShowArms:1,Invisible:1,Invulnerable:1,NoBasePlate:1,NoGravity:1,Rotation:[90f,0.0f],Equipment:[{id:item_frame,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,270f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}
	
	 */
	public void CreateSturcture(Player player, String StructureCode, short StructureID, int Direction)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		ServerTickMain.ServerTask="[구조물 설치]";
		Long UTC = ServerTickMain.nowUTC;
		
		GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(UTC, "C_S");
		STSO.setCount(0);//현재 진행 단계
		
		Location PlayerLoc = player.getLocation();
		Location CMLock = new Location(PlayerLoc.getWorld(), PlayerLoc.getX()-1, PlayerLoc.getY()-2, PlayerLoc.getZ()-1);
		Location RSLock = new Location(PlayerLoc.getWorld(), PlayerLoc.getX(), PlayerLoc.getY()-3, PlayerLoc.getZ());
		
		switch(StructureID)
		{
			case 0://우편함
				STSO.setString((byte)0, "PB");//설치할 구조물 이름 저장 PostBox
				break;
			case 1://게시판
				STSO.setString((byte)0, "B");//설치할 구조물 이름 저장 Board
				break;
			case 2://거래 게시판
				STSO.setString((byte)0, "TB");//설치할 구조물 이름 저장 TradeBoard
				break;
			case 3://캠프 파이어
				STSO.setString((byte)0, "CF");//설치할 구조물 이름 저장 CampFire
				break;
				
				
			case 101://이끼 제단
				STSO.setString((byte)0, "A_M");//설치할 구조물 이름 저장 Altar_Mossy
				break;
			case 102://金泰龍
				STSO.setString((byte)0, "A_GoldBigDragon");//설치할 구조물 이름 저장 Altar_GoldBigDragon
				break;
			case 103://스톤 헨지
				STSO.setString((byte)0, "A_SH");//설치할 구조물 이름 저장 Altar_StoneHenge
				break;
			case 104://해부대
				STSO.setString((byte)0, "A_AB");//설치할 구조물 이름 저장 Altar_AnatomicalBoard
				break;
			case 145://테스트용 제단
				STSO.setString((byte)0, "A_TEST");//설치할 구조물 이름 저장 Altar_TEST
				break;
		}
		STSO.setString((byte)1, player.getLocation().getWorld().getName());//플레이어의 월드 이름 저장
		STSO.setString((byte)2, StructureCode);//구조물 코드 저장
		STSO.setString((byte)3, Direction+"");//구조물 방향 저장
		
		STSO.setInt((byte)0, (int) CMLock.getX());//커맨드 블록X 위치저장
		STSO.setInt((byte)1, (int) CMLock.getY());//커맨드 블록Y 위치저장
		STSO.setInt((byte)2, (int) CMLock.getZ());//커맨드 블록Z 위치저장
		STSO.setInt((byte)3, (int) 0);//현재 해야 할 일 0이면 레드스톤 블록 생성, 1이면 제거

		STSO.setInt((byte)4, (int) CMLock.getBlock().getTypeId());//커맨드 블록 위치의 원래 블록 ID
		STSO.setInt((byte)5, (int) CMLock.getBlock().getData());//커맨드 블록 위치의 원래 블록 DATA

		STSO.setInt((byte)6, (int) RSLock.getBlock().getTypeId());//레드 스톤 블록 위치의 원래 블록 ID
		STSO.setInt((byte)7, (int) RSLock.getBlock().getData());//레드 스톤 블록 위치의 원래 블록 DATA

		
		player.sendMessage(ChatColor.YELLOW+"[SYSTEM] : 구조물 설치가 되지 않을 경우, 서버 설정에서 커맨드 블록 사용을 활성화 시켜 주세요!");
		GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC, STSO);
	}
	
	public String getCMD(String StructureName, int LineNumber, String StructureCode, String Direction)
	{
		if(StructureName.compareTo("PB")==0)
			return new Structure_PostBox().CreatePostBox(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.compareTo("B")==0)
			return new Structure_Board().CreateBoard(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.compareTo("TB")==0)
			return new Structure_TradeBoard().CreateTradeBoard(LineNumber,StructureCode,(byte) Byte.parseByte(Direction));
		else if(StructureName.compareTo("CF")==0)
			return new Structure_CampFire().CreateCampFire(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));

		else if(StructureName.compareTo("A_M")==0)
			return new Structure_Altar().CreateMossyAltar(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.compareTo("A_GoldBigDragon")==0)
			return new Structure_Altar().CreateGoldBigDragon1(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.compareTo("A_SH")==0)
			return new Structure_Altar().CreateStoneHenge(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.compareTo("A_AB")==0)
			return new Structure_Altar().CreateAtonomicBoard(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.compareTo("A_TEST")==0)
			return new Structure_Altar().CreateTestAltar(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		return "null";
	}
	
	public static void pasteSchematic(Location loc, Schematic schematic)
    {
		byte[] blocks = schematic.getBlocks();
		byte[] blockData = schematic.getData();
 
        short length = schematic.getLenght();
        short width = schematic.getWidth();
        short height = schematic.getHeight();
 
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Block block = new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                    block.setTypeIdAndData(blocks[index], (byte) blockData[index], true);
                }
            }
        }
    }

	public void StructureUse(Player player, String StructureName)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		String Structrue = ChatColor.stripColor(StructureName);
		if(Structrue.compareTo("[우편함]")==0)
		{
			s.SP(player, Sound.CHEST_OPEN, 0.8F, 1.0F);
			new Structure_PostBox().PostBoxMainGUI(player, (byte) 0);
		}
		else if(Structrue.compareTo("[게시판]")==0)
		{
			s.SP(player, Sound.ZOMBIE_WOOD, 0.5F, 1.8F);
			new Structure_Board().BoardMainGUI(player,StructureName, (byte) 0);
		}
		else if(Structrue.compareTo("[거래 게시판]")==0)
		{
			s.SP(player, Sound.VILLAGER_IDLE, 1.0F, 1.0F);
			new Structure_TradeBoard().TradeBoardMainGUI(player, (byte)0, (byte)0);
		}
		else if(Structrue.compareTo("[모닥불]")==0)
		{
			s.SP(player, Sound.FIRE, 2.0F, 1.0F);
			new Structure_CampFire().CampFireMainGUI(player, StructureName);
		}
		else if(Structrue.compareTo("[제단]")==0)
		{
			s.SP(player, Sound.AMBIENCE_CAVE, 1.2F, 1.2F);
			new GoldBigDragon_RPG.Dungeon.DungeonGUI().AltarUseGUI(player, StructureName);
		}
	}
	
	public void InventoryClickRouter(InventoryClickEvent event, String InventoryName)
	{
		String Striped = ChatColor.stripColor(event.getInventory().getName().toString());
		if(event.getInventory().getType()==InventoryType.CHEST)
		{
			if(!(Striped.compareTo("보낼 아이템")==0
			))
				event.setCancelled(true);
		}
		if(InventoryName.compareTo("우편함")==0)
			new Structure_PostBox().PostBoxMainGUIClick(event);
		else if(InventoryName.compareTo("보낼 아이템")==0)
			new Structure_PostBox().ItemPutterGUIClick(event);
		else if(InventoryName.contains("게시판"))
		{
			if(InventoryName.contains("거래"))
			{
				if(InventoryName.contains("메뉴"))
					new Structure_TradeBoard().SelectTradeTypeGUIClick(event);
				else if(InventoryName.contains("설정"))
					new Structure_TradeBoard().TradeBoardSettingGUIClick(event);
				else
					new Structure_TradeBoard().TradeBoardMainGUIClick(event);
			}
			else
			{
				if(InventoryName.contains("설정"))
					new Structure_Board().BoardSettingGUIClick(event);
				else
					new Structure_Board().BoardMainGUIClick(event);
			}
		}
		else if(InventoryName.compareTo("판매할 아이템을 고르세요")==0)
			new Structure_TradeBoard().SelectSellItemGUIClick(event);
		else if(InventoryName.compareTo("구매할 아이템을 고르세요")==0)
			new Structure_TradeBoard().SelectBuyItemGUIClick(event);
		else if(InventoryName.contains("일반 아이템"))
			new Structure_TradeBoard().SelectNormalItemGUIClick(event);
		else if(InventoryName.compareTo("받고싶은 아이템을 고르세요")==0)
			new Structure_TradeBoard().SelectExchangeItem_YouGUIClick(event);
		else if(InventoryName.compareTo("내가 줄 아이템을 고르세요")==0)
			new Structure_TradeBoard().SelectExchangeItem_MyGUIClick(event);
		else if(InventoryName.compareTo("모닥불")==0)
			new Structure_CampFire().CampFireGUIClick(event);
	}
	
	
	public void InventoryCloseRouter(InventoryCloseEvent event, String InventoryName)
	{
		Object_UserData u = new Object_UserData();
		Player player = (Player)event.getPlayer();
		
		if(InventoryName.compareTo("보낼 아이템")==0)
			new Structure_PostBox().ItemPutterGUIClose(event);
		else if(InventoryName.compareTo("판매할 아이템을 고르세요")==0||InventoryName.compareTo("구매할 아이템을 고르세요")==0)
		{
			if(u.getItemStack((Player)event.getPlayer())==null)
				u.clearAll(player);
		}
		else if(InventoryName.contains("일반 아이템"))
			u.clearAll(player);
	}
}