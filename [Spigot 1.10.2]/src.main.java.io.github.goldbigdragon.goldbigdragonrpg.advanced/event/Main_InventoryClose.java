package event;

import org.bukkit.event.inventory.InventoryCloseEvent;

public class Main_InventoryClose
{
	public void InventoryCloseRouter(InventoryCloseEvent event, String InventoryCode)
	{
		String UniqueCode = InventoryCode.charAt(1) + "" + InventoryCode.charAt(2);
		String SubjectCode = InventoryCode.charAt(3) + "" + InventoryCode.charAt(4);
		if(UniqueCode.compareTo("00")==0)//User 패키지 속의 GUI Click을 관리함.
		    new user._UserGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("01")==0)//Admin 패키지 속의 GUI Close를 관리함.
		    new admin._AdminGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("02")==0)//Area 패키지 속의 GUI Close를 관리함.
		    new area._AreaGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("05")==0)//Quest 패키지 속의 GUI Close를 관리함.
		    new quest._QuestGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("07")==0)//NPC 패키지 속의 GUI Close를 관리함.
		    new npc._NPCGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("08")==0)//Monster 패키지 속의 GUI Close를 관리함.
		    new monster._MonsterGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("0a")==0)//Dungeon 패키지 속의 GUI Close를 관리함.
		    new dungeon._DungeonGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("0d")==0)//Structure 패키지 속의 GUI Close를 관리함.
		    new structure._StructureGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("0e")==0)//Making 패키지 속의 GUI Close를 관리함.
		    new making._MakingGUIManager().CloseRouting(event, SubjectCode);
	}
}
