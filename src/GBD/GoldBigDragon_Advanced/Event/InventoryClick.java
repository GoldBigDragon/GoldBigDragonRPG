package GBD.GoldBigDragon_Advanced.Event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick
{
	public void InventoryClickRouter(InventoryClickEvent event, String InventoryName)
	{
		if(event.getClickedInventory() == null)
			return;
		if(event.getClickedInventory().getTitle().equalsIgnoreCase("container.inventory") == true)
			return;
		if (event.getCurrentItem() == null||event.getCurrentItem().getType() == Material.AIR||event.getCurrentItem().getAmount() == 0)
		{return;}
		
		if(InventoryName.equals("스텟"))
		{
		    if(event.getClickedInventory().getType() != InventoryType.CHEST)
		    {
		    	event.setCancelled(true);
		    	return;
		    }
		    GBD.GoldBigDragon_Advanced.GUI.StatsGUI SGUI = new GBD.GoldBigDragon_Advanced.GUI.StatsGUI();
			SGUI.StatusInventoryclick(event); 
			return;
		}
		else if(InventoryName.equals("몬스터 장비 설정"))
		{
	    	GBD.GoldBigDragon_Advanced.ETC.Monster MC = new GBD.GoldBigDragon_Advanced.ETC.Monster();
			MC.ArmorGUIClick(event);return;
	    }
		else if(InventoryName.equals("장비 구경"))
	    {
		    GBD.GoldBigDragon_Advanced.GUI.EquipGUI EqGUI = new GBD.GoldBigDragon_Advanced.GUI.EquipGUI();
			EqGUI.optionInventoryclick(event);return;
	    }
		else if(InventoryName.equals("옵션"))
	    {
		    GBD.GoldBigDragon_Advanced.GUI.OptionGUI OGUI = new GBD.GoldBigDragon_Advanced.GUI.OptionGUI();
			OGUI.optionInventoryclick(event);return;
	    }
		else if(InventoryName.equals("해당 블록을 캐면 나올 아이템"))
	    {
		    GBD.GoldBigDragon_Advanced.GUI.AreaGUI AGUI = new GBD.GoldBigDragon_Advanced.GUI.AreaGUI();
		    AGUI.AreaBlockItemSettingGUIClick(event);return;
	    }

	    GBD.GoldBigDragon_Advanced.GUI.ETCGUI EGUI = new GBD.GoldBigDragon_Advanced.GUI.ETCGUI();
	    GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
	    GBD.GoldBigDragon_Advanced.GUI.JobGUI JGUI = new GBD.GoldBigDragon_Advanced.GUI.JobGUI();
	    GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI SKGUI = new GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI();
	    GBD.GoldBigDragon_Advanced.GUI.PlayerSkillGUI PSKGUI = new GBD.GoldBigDragon_Advanced.GUI.PlayerSkillGUI();
		switch(InventoryName)
		{
			case "퀵슬롯 등록":
				PSKGUI.AddQuickBarGUIClick(event);
			break;
			case "시스템 선택":
				JGUI.ChooseSystemGUIClick(event);
			break;
			case "계속 등록 하시겠습니까?":
				QGUI.KeepGoingClick(event);
				break;
			case "보상 목록":
			case "채집 해야 할 블록 목록":
			case "사냥 해야 할 몬스터 목록":
				QGUI.ShowItemGUIInventoryClick(event); return;
			case "기타" : 
				EGUI.ETCInventoryclick(event);return;
			case "가이드" : 
				EGUI.ETCInventoryclick(event); return;
			case "오브젝트 추가":
				QGUI.ObjectAddInventoryClick(event);return;
			default :
				if(InventoryName.contains("NPC"))
				{IC_NPC(event, InventoryName);return;}
				else if(InventoryName.contains("파티"))
				{IC_Party(event, InventoryName); return;}
				else if(InventoryName.contains("아이템"))
				{IC_Item(event, InventoryName);return;}
				else if(InventoryName.contains("퀘스트"))
				{IC_Quest(event, InventoryName);return;}
				else if(InventoryName.contains("등록된"))
				{JGUI.AddedSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("[MapleStory]"))
				{IC_MapleStory(event, InventoryName);return;}
				else if(InventoryName.contains("[Mabinogi]"))
				{IC_Mabinogi(event, InventoryName);return;}
				else if(InventoryName.contains("스킬"))
				{IC_Skill(event, InventoryName);return;}
				else if(InventoryName.contains("랭크"))
				{SKGUI.SkillRankOptionGUIClick(event);return;}
				else if(InventoryName.contains("직업군"))
				{PSKGUI.MapleStory_MainSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("카테고리"))
				{PSKGUI.Mabinogi_MainSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("관리자"))
				{IC_OP(event, InventoryName);return;}
				else if(InventoryName.contains("이벤트"))
				{IC_Event(event, InventoryName);return;}
				else if(InventoryName.contains("영역"))
				{IC_Area(event, InventoryName);return;}
				else if(InventoryName.contains("개조식"))
				{IC_Upgrade(event, InventoryName);return;}
				else if(InventoryName.contains("초심자"))
				{IC_NewBie(event, InventoryName);return;}
				else if(InventoryName.contains("몬스터"))
				{IC_Monster(event, InventoryName);return;}
				else if(InventoryName.contains("월드"))
				{IC_World(event, InventoryName);return;}
				else if(InventoryName.contains("워프"))
				{IC_Warp(event, InventoryName);return;}
				else if(InventoryName.contains("매직스펠"))
				{new OtherPlugins.SpellMain().ShowAllMaigcGUIClick(event);return;}
				else if(InventoryName.contains("친구"))
				{IC_Friend(event, InventoryName);return;}
				else if(InventoryName.contains("네비"))
				{IC_Navi(event, InventoryName);return;}
				return;
		}
		return;
	}
	
	private void IC_NPC(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.NPC_GUI NPGUI = new GBD.GoldBigDragon_Advanced.GUI.NPC_GUI();
    	if(InventoryName.equals("NPC 직업 선택"))
    		NPGUI.NPCJobClick(event, ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
    	else if(InventoryName.contains("NPC"))
	    {
        	if(InventoryName.contains("[NPC]"))
    	    {
    			if(event.getInventory().getSize() <= 9)
    				NPGUI.NPCclickMain(event, InventoryName.split("C] ")[1]);
    			else if(event.getInventory().getSize() <= 27)
    				NPGUI.NPCclickMain(event, InventoryName.split("C] ")[1]);
    			else if(event.getInventory().getSize() <= 54)
    				NPGUI.NPCclickMain(event,InventoryName.split("C] ")[1]);	
    	    }
        	else if(InventoryName.contains("워프"))
			{
    			if(InventoryName.contains("가능"))
    				NPGUI.WarpMainGUIClick(event);
    			else if(InventoryName.contains("등록"))
    				NPGUI.WarperGUIClick(event);
			}
        	else if(InventoryName.contains("개조"))
        	{
    			if(InventoryName.contains("장인"))
    				NPGUI.UpgraderGUIClick(event);
    			else
    				NPGUI.SelectUpgradeRecipeGUIClick(event);
    				
        	}
        	else if(InventoryName.contains("가르칠"))
				NPGUI.AddAbleSkillsGUIClick(event);
        	else if(InventoryName.contains("룬"))
				NPGUI.RuneEquipGUIClick(event);
        	else if(InventoryName.contains("대사"))
        	{
        		if(InventoryName.contains("설정"))
            		NPGUI.TalkSettingGUIClick(event);
        		else
        			NPGUI.TalkGUIClick(event);
        	}
	    }
		return;
	}

	private void IC_Quest(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.NPC_GUI NPGUI = new GBD.GoldBigDragon_Advanced.GUI.NPC_GUI();
	    GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();

		if(InventoryName.equals("퀘스트 옵션"))
			QGUI.QuestOptionClick(event);
		else if(InventoryName.equals("[퀘스트]"))
			QGUI.QuestScriptTypeGUIClick(event);
		else if(InventoryName.contains("진행"))
			NPGUI.QuestAcceptclickMain(event);
		else if(InventoryName.contains("전체"))
			QGUI.OPboxAllQuestListInventoryclick(event);
		else if(InventoryName.contains("등록"))
			NPGUI.NPCQuestclickMain(event);
		else if(InventoryName.contains("흐름도"))
			QGUI.FixQuestListInventoryclick(event);
		else if(InventoryName.contains("네비"))
			QGUI.Quest_NavigationListGUIClick(event);
		else if(InventoryName.contains("선택"))
		{
			if(InventoryName.contains("확인"))
				QGUI.Quest_OPChoiceClick(event);
			else
				QGUI.Quest_UserChoiceClick(event);
		}
		else
			QGUI.MyQuestListInventoryclick(event);
		return;
	}

	private void IC_MapleStory(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.JobGUI JGUI = new GBD.GoldBigDragon_Advanced.GUI.JobGUI();

		if(InventoryName.contains("전체"))
			JGUI.MapleStory_ChooseJobClick(event);
		else if(InventoryName.contains("설정"))
			JGUI.MapleStory_JobSettingClick(event);
		return;
	}

	private void IC_Mabinogi(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.JobGUI JGUI = new GBD.GoldBigDragon_Advanced.GUI.JobGUI();

		if(InventoryName.contains("전체"))
			JGUI.Mabinogi_ChooseCategoryClick(event);
		else if(InventoryName.contains("설정"))
			JGUI.MapleStory_JobSettingClick(event);
		else if(InventoryName.contains("관리"))
			JGUI.Mabinogi_SkillSettingClick(event);
		return;
	}

	private void IC_Skill(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI SKGUI = new GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI();
	    GBD.GoldBigDragon_Advanced.GUI.PlayerSkillGUI PSKGUI = new GBD.GoldBigDragon_Advanced.GUI.PlayerSkillGUI();

		if(InventoryName.contains("전체"))
			SKGUI.AllSkillsGUIClick(event);
		else if(InventoryName.contains("관리"))
			SKGUI.IndividualSkillOptionGUIClick(event);
		else if(InventoryName.contains("보유"))
			PSKGUI.SkillListGUIClick(event);
	    else if(InventoryName.contains("가능"))
	    {
	    	GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI UIGUI = new GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI();
	    	UIGUI.SelectSkillGUIClick(event);
	    }
		return;
	}

	private void IC_OP(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI OPGUI = new GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI();

	    if(InventoryName.contains("가이드"))
	    	OPGUI.OPBoxGuideInventoryclick(event);
	    else if(InventoryName.contains("도구"))
	    	OPGUI.OPBoxGUIInventoryclick(event);
	    else if(InventoryName.contains("옵션"))
	    	OPGUI.OPBoxGUI_SettingInventoryClick(event);
	    else if(InventoryName.contains("공지사항"))
	    	OPGUI.OPBoxGUI_BroadCastClick(event);
	    else if(InventoryName.contains("스텟"))
	    	OPGUI.OPBoxGUI_StatChangeClick(event);
	    return;
	}

	private void IC_Party(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.PartyGUI PGUI = new GBD.GoldBigDragon_Advanced.GUI.PartyGUI();

	    if(InventoryName.equals("파티"))
	    	PGUI.partyInventoryclick(event);
		else if(InventoryName.contains("목록"))
			PGUI.PartyListInventoryclick(event);
		else if(InventoryName.contains("멤버")||InventoryName.contains("교체"))
			PGUI.PartyMemberInformationClick(event);
		return;
	}
	
	private void IC_Item(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
	    GBD.GoldBigDragon_Advanced.GUI.ItemGUI IGUI = new GBD.GoldBigDragon_Advanced.GUI.ItemGUI();

	    if(InventoryName.contains("소모성")==true)
	    {
	    	GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI UIGUI = new GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI();
			if(InventoryName.contains("목록"))
		    	UIGUI.UseableItemListGUIClick(event);
			else if(InventoryName.contains("타입"))
		    	UIGUI.ChooseUseableItemTypeGUIClick(event);
			else if(InventoryName.contains("상세"))
		    	UIGUI.NewUseableItemGUIclick(event);
	    }
	    else
	    {
		    if(InventoryName.equals("모아야 할 아이템 등록")||InventoryName.equals("수집할 아이템 등록"))
		    {
				if(event.getSlot() == 8)
					event.getWhoClicked().closeInventory();
		    }
		    else  if(InventoryName.equals("보상 아이템 등록"))
				QGUI.SettingPresentClick(event);
		    else  if(InventoryName.contains("상세"))
				IGUI.NewItemGUIclick(event);
		    else  if(InventoryName.equals("모아야 할 아이템 목록"))
				QGUI.ShowItemGUIInventoryClick(event);
			else if(InventoryName.contains("목록"))
				IGUI.ItemListInventoryclick(event);
	    }
		return;
	}

	private void IC_Area(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.AreaGUI AGUI = new GBD.GoldBigDragon_Advanced.GUI.AreaGUI();
	    if(InventoryName.contains("설정"))
			AGUI.AreaGUIInventoryclick(event);
	    else if(InventoryName.contains("전체"))
	    	AGUI.AreaListGUIClick(event);
	    else if(InventoryName.contains("몬스터"))
	    {
		    if(InventoryName.contains("대체"))
		    	AGUI.AreaMonsterSettingGUIClick(event);
		    else if(InventoryName.contains("선택"))
		    	AGUI.AreaAddMonsterListGUIClick(event);
		    else if(InventoryName.contains("스폰"))
		    	AGUI.AreaAddMonsterSpawnRuleGUIClick(event);
		    else if(InventoryName.contains("특수"))
		    	AGUI.AreaSpawnSpecialMonsterListGUIClick(event);
	    }
	    else if(InventoryName.contains("특산품"))
	    	AGUI.AreaBlockSettingGUIClick(event);
	    else if(InventoryName.contains("어류"))
	    	AGUI.AreaFishSettingGUIClick(event);
	    else if(InventoryName.contains("배경음"))
	    	AGUI.AreaMusicSettingGUIClick(event);
		return;
	}

	private void IC_Upgrade(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.UpGradeGUI UGUI = new GBD.GoldBigDragon_Advanced.GUI.UpGradeGUI();
	    if(InventoryName.contains("목록"))
	    	UGUI.UpgradeRecipeGUIClick(event);
	    else if(InventoryName.contains("설정"))
	    	UGUI.UpgradeRecipeSettingGUIClick(event);
		return;
	}	
	
	private void IC_NewBie(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.NewBieGUI NGUI = new GBD.GoldBigDragon_Advanced.GUI.NewBieGUI();
	    if(InventoryName.contains("옵션"))
	    	NGUI.NewBieGUIMainInventoryclick(event);
	    else if(InventoryName.contains("지원")||InventoryName.contains("가이드"))
	    	NGUI.NewBieSupportItemGUIInventoryclick(event);
	    else if(InventoryName.contains("기본퀘"))
	    	NGUI.NewBieQuestGUIInventoryclick(event);
		return;
	}
	
	private void IC_Monster(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.MonsterGUI MGUI = new GBD.GoldBigDragon_Advanced.GUI.MonsterGUI();
	    if(InventoryName.contains("목록"))
	    	MGUI.MonsterListGUIClick(event);
	    else if(InventoryName.contains("설정"))
	    	MGUI.MonsterOptionSettingGUIClick(event);
	    else if(InventoryName.contains("포션"))
	    	MGUI.MonsterPotionGUIClick(event);
		return;
	}	
	
	private void IC_World(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.WorldCreateGUI WGUI = new GBD.GoldBigDragon_Advanced.GUI.WorldCreateGUI();
	    if(InventoryName.contains("선택"))
	    	WGUI.WorldCreateGUIClick(event);
		return;
	}	

	private void IC_Warp(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.WarpGUI WGUI = new GBD.GoldBigDragon_Advanced.GUI.WarpGUI();
	    if(InventoryName.contains("목록"))
	    	WGUI.WarpListGUIInventoryclick(event);
		return;
	}
	
	private void IC_Event(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.EventGUI EGUI = new GBD.GoldBigDragon_Advanced.GUI.EventGUI();
	    if(InventoryName.contains("지급"))
	    	EGUI.AllPlayerGiveEventGUIclick(event);
		else if(InventoryName.contains("진행"))
			EGUI.EventGUIInventoryclick(event);
		return;
	}		

	private void IC_Friend(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.ETCGUI EGUI = new GBD.GoldBigDragon_Advanced.GUI.ETCGUI();
	    if(InventoryName.contains("목록"))
	    	EGUI.FriendsGUIclick(event);
	    if(InventoryName.contains("요청"))
	    	EGUI.WaittingFriendsGUIclick(event);
		return;
	}

	private void IC_Navi(InventoryClickEvent event, String InventoryName)
	{
	    GBD.GoldBigDragon_Advanced.GUI.NavigationGUI NGUI = new GBD.GoldBigDragon_Advanced.GUI.NavigationGUI();
	    if(InventoryName.contains("목록"))
	    	NGUI.NavigationListGUIClick(event);
	    else if(InventoryName.contains("설정"))
	    	NGUI.NavigationOptionGUIClick(event);
	    else if(InventoryName.contains("사용"))
	    	NGUI.UseNavigationGUIClick(event);
		return;
	}
}