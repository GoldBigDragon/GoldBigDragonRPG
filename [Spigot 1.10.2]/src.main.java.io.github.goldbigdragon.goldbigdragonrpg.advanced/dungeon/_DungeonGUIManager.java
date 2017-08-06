package dungeon;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _DungeonGUIManager
{
	//Dungeon GUI Click Unique Number = 0a
	//던전 관련 GUI의 고유 번호는 0a입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 아이템 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("00")==0)//던전 목록
			new dungeon.Dungeon_GUI().DungeonListMainGUIClick(event);
		else if(SubjectCode.compareTo("01")==0)//던전 설정
			new dungeon.Dungeon_GUI().DungeonSetUpGUIClick(event);
		else if(SubjectCode.compareTo("02")==0)//던전 보상
			new dungeon.Dungeon_GUI().DungeonChestRewardClick(event);
		else if(SubjectCode.compareTo("03")==0)//던전 몬스터 메인
			new dungeon.Dungeon_GUI().DungeonMonsterGUIMainClick(event);
		else if(SubjectCode.compareTo("04")==0)//던전 몬스터 타입 선택
			new dungeon.Dungeon_GUI().DungeonMonsterChooseMainClick(event);
		else if(SubjectCode.compareTo("05")==0)//던전 일반 몬스터
			new dungeon.Dungeon_GUI().DungeonSelectNormalMonsterChooseClick(event);
		else if(SubjectCode.compareTo("06")==0)//던전 커스텀 몬스터
			new dungeon.Dungeon_GUI().DungeonSelectCustomMonsterChooseClick(event);
		else if(SubjectCode.compareTo("07")==0)//던전 배경 음악
			new dungeon.Dungeon_GUI().DungeonMusicSettingGUIClick(event);
		else if(SubjectCode.compareTo("08")==0)//던전 통행증 설정
			new dungeon.Dungeon_GUI().EnterCardSetUpGUIClick(event);
		else if(SubjectCode.compareTo("09")==0)//던전 통행증 연결
			new dungeon.Dungeon_GUI().EnterCardDungeonSettingGUIClick(event);
		else if(SubjectCode.compareTo("0a")==0)//던전 제단 목록
			new dungeon.Dungeon_GUI().AltarShapeListGUIClick(event);
		else if(SubjectCode.compareTo("0b")==0)//던전 제단 설정
			new dungeon.Dungeon_GUI().AltarSettingGUIClick(event);
		else if(SubjectCode.compareTo("0c")==0)//던전 제단 연결
			new dungeon.Dungeon_GUI().AltarDungeonSettingGUIClick(event);
		else if(SubjectCode.compareTo("0d")==0)//던전 제단에 등록된 통행증 목록
			new dungeon.Dungeon_GUI().AltarEnterCardSettingGUIClick(event);
		else if(SubjectCode.compareTo("0e")==0)//생성된 통행증 목록
			new dungeon.Dungeon_GUI().AltarEnterCardListGUIClick(event);
		else if(SubjectCode.compareTo("0f")==0)//던전 제단 열람 화면
			new dungeon.Dungeon_GUI().AltarUseGUIClick(event);
		else if(SubjectCode.compareTo("10")==0)//던전 잔류 화인
			new dungeon.Dungeon_GUI().DungeonEXITClick(event);
		
	}

	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("02")==0)//던전 보상
			new dungeon.Dungeon_GUI().DungeonChestRewardClose(event);
		else if(SubjectCode.compareTo("0f")==0)//던전 제단 열람 화면
			new dungeon.Dungeon_GUI().AltarUSEGuiClose(event);
	}
}
