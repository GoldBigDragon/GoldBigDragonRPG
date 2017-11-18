package structure;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import dungeon.Schematic;
import effect.SoundEffect;
import servertick.ServerTickMain;

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
		
		ServerTickMain.ServerTask="[구조물 설치]";
		Long UTC = ServerTickMain.nowUTC;
		
		servertick.ServerTickObject STSO = new servertick.ServerTickObject(UTC, "C_S");
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

		
		player.sendMessage("§e[SYSTEM] : 구조물 설치가 되지 않을 경우, 서버 설정에서 커맨드 블록 사용을 활성화 시켜 주세요!");
		servertick.ServerTickMain.Schedule.put(servertick.ServerTickMain.nowUTC, STSO);
	}
	
	public String getCMD(String StructureName, int LineNumber, String StructureCode, String Direction)
	{
		if(StructureName.equals("PB"))
			return new StructPostBox().CreatePostBox(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.equals("B"))
			return new StructBoard().CreateBoard(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.equals("TB"))
			return new StructTradeBoard().CreateTradeBoard(LineNumber,StructureCode,(byte) Byte.parseByte(Direction));
		else if(StructureName.equals("CF"))
			return new StructCampFire().CreateCampFire(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));

		else if(StructureName.equals("A_M"))
			return new StructAltar().CreateMossyAltar(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.equals("A_GoldBigDragon"))
			return new StructAltar().CreateGoldBigDragon1(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.equals("A_SH"))
			return new StructAltar().CreateStoneHenge(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.equals("A_AB"))
			return new StructAltar().CreateAtonomicBoard(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
		else if(StructureName.equals("A_TEST"))
			return new StructAltar().CreateTestAltar(LineNumber,StructureCode, (byte) Byte.parseByte(Direction));
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
		

		String Structrue = ChatColor.stripColor(StructureName);
		if(Structrue.equals("[우편함]"))
		{
			SoundEffect.SP(player, Sound.BLOCK_CHEST_OPEN, 0.8F, 1.0F);
			new StructPostBox().PostBoxMainGUI(player, (byte) 0);
		}
		else if(Structrue.equals("[게시판]"))
		{
			SoundEffect.SP(player, Sound.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, 0.5F, 1.8F);
			new StructBoard().BoardMainGUI(player,StructureName, (byte) 0);
		}
		else if(Structrue.equals("[거래 게시판]"))
		{
			SoundEffect.SP(player, Sound.ENTITY_VILLAGER_AMBIENT, 1.0F, 1.0F);
			new StructTradeBoard().TradeBoardMainGUI(player, (byte)0, (byte)0);
		}
		else if(Structrue.equals("[모닥불]"))
		{
			SoundEffect.SP(player, Sound.BLOCK_FIRE_AMBIENT, 2.0F, 1.0F);
			new StructCampFire().CampFireMainGUI(player, StructureName);
		}
		else if(Structrue.equals("[제단]"))
		{
			SoundEffect.SP(player, Sound.AMBIENT_CAVE, 1.2F, 1.2F);
			new dungeon.DungeonGui().AltarUseGUI(player, StructureName);
		}
	}

}