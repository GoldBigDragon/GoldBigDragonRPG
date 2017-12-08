package otherplugins;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.ManaChangeEvent;
import com.nisovin.magicspells.events.SpellTargetEvent;
import com.nisovin.magicspells.events.SpellTargetLocationEvent;
import com.nisovin.magicspells.mana.ManaChangeReason;

import battle.BattleCalculator;
import main.MainServerOption;
import util.YamlLoader;

public class SpellMain implements Listener
{
	public SpellMain(JavaPlugin plugin)
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	public SpellMain()
	{}

	@EventHandler
	public void MagicSpellsTargeted (SpellTargetEvent event)
	{
		event.setPower(event.getPower()+BonusPowerCalculator(event.getCaster(),event.getTarget()));
		//Bukkit.broadcastMessage(event.getCaster().getName()+"가"+event.getPower()+"강도로 때려서 "+event.getTarget().getName()+"이 맞음!");
	}
	@EventHandler
	public void MagicSpellsTargeted (SpellTargetLocationEvent event)
	{
		event.setPower(event.getPower()+BonusPowerCalculator(event.getCaster(),null));
		//Bukkit.broadcastMessage(event.getCaster().getName()+"가"+event.getPower()+"강도로 광역 공격을 가함!");
	}
	
	public float BonusPowerCalculator (Player player, LivingEntity target)
	{
		int bonuspower = 0;
		if(main.MainServerOption.PlayerUseSpell.containsKey(player))
		{
			Damageable p = player;
			String switchCheck = main.MainServerOption.PlayerUseSpell.get(player);
			if(switchCheck.compareTo("생명력")==0)
				bonuspower = BattleCalculator.magicSpellsDamageBonus((int) p.getHealth())+BattleCalculator.getPlayerEquipmentStat(player, "HP", false, null)[0];
			else if(switchCheck.compareTo("마나")==0)
				bonuspower = BattleCalculator.magicSpellsDamageBonus(getPlayerMana(player))+BattleCalculator.getPlayerEquipmentStat(player, "MP", false, null)[0];
			else if(switchCheck.compareTo(MainServerOption.statSTR)==0)
				bonuspower = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()+BattleCalculator.getPlayerEquipmentStat(player, "STR", false, null)[0];
			else if(switchCheck.compareTo(MainServerOption.statDEX)==0)
				bonuspower = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()+BattleCalculator.getPlayerEquipmentStat(player, "DEX", false, null)[0];
			else if(switchCheck.compareTo(MainServerOption.statINT)==0)
				bonuspower = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()+BattleCalculator.getPlayerEquipmentStat(player, "INT", false, null)[0];
			else if(switchCheck.compareTo(MainServerOption.statWILL)==0)
				bonuspower = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()+BattleCalculator.getPlayerEquipmentStat(player, "WILL", false, null)[0];
			else if(switchCheck.compareTo(MainServerOption.statLUK)==0)
				bonuspower = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()+BattleCalculator.getPlayerEquipmentStat(player, "LUK", false, null)[0];
			else
				bonuspower = 0;
		}
		int[] weaponPower = BattleCalculator.getPlayerEquipmentStat(player, "MagicDamage",  false, main.MainServerOption.PlayerlastItem.get(player));
		int weaponPowerFixed = new util.UtilNumber().RandomNum(weaponPower[0], weaponPower[1]);
		bonuspower = bonuspower+weaponPowerFixed;
		int negativeBonus = 0;
		if(target!=null)
		{
			if(target.getType()==EntityType.PLAYER)
			{
				Player t = (Player) target;
				if(t.isOnline())
				  	negativeBonus = BattleCalculator.getMagicProtect(t, main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT());
				else
				{
					if(t.isCustomNameVisible())
					{
						String name = new monster.MonsterKill().getRealName(target);
						if(main.MainServerOption.MonsterList.containsKey(name))
							negativeBonus = main.MainServerOption.MonsterList.get(name).getMPRO();
					}
				}
			}
			else
			{
				if(target.isCustomNameVisible())
				{
					String name = new monster.MonsterKill().getRealName(target);
					if(main.MainServerOption.MonsterList.containsKey(name))
						negativeBonus = main.MainServerOption.MonsterList.get(name).getMPRO();
				}
			}
		}
		main.MainServerOption.PlayerUseSpell.put(player, "us");
		return (float) (bonuspower*0.006)-negativeBonus;
	}
	
	
	
	public void getSpellsList (Player player)
	{
		Collection<Spell> SC = MagicSpells.spells();
		Object[] SO = SC.toArray();
		for(short count = 0; count < SO.length; count++)
			player.sendMessage(SO[count].toString());
	}

	public void ShowAllMaigcGUI(Player player, short page,String skillName, short skillLevel,byte sort)
	{
		Object[] spells = new Object[46];
		int temp = 0;
		byte counter=0;
		switch(sort)
		{
		case 0:
			for(counter = 0;counter<46;counter++)
				if(counter+(page*45) < MagicSpells.spells().toArray().length)
					spells[counter] = MagicSpells.spells().toArray()[counter+(page*45)];
				else
					break;
			break;
		case 1:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().compareTo("DummySpell")==0)
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		case 2:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().compareTo("AreaEffectSpell")==0)
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		case 3:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().compareTo("MultiSpell")==0)
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		case 4:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().compareTo("PainSpell")==0)
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		case 5:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().compareTo("ParticleProjectileSpell")!=0)
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		}

		String uniqueCode = "§0§0§b§0§7§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0매직스펠 목록 : " + (page+1));

		Spell spell;
		for(short count = 0; count < spells.length;count++)
		{
			spell = (Spell) spells[count];
			if(spell!=null)
			{
				String spellName = spell.getName();
				short id = 403;
				if(spell.getClass().getSimpleName().compareTo("PainSpell")==0)
					id= 370;
				else if(spell.getClass().getSimpleName().compareTo("DummySpell")==0)
					id= 416;
				else if(spell.getClass().getSimpleName().compareTo("AreaEffectSpell")==0)
					id= 438;
				else if(spell.getClass().getSimpleName().compareTo("MultiSpell")==0)
					id= 345;
				else if(spell.getClass().getSimpleName().compareTo("ParticleProjectileSpell")==0)
					id= 401;
				else if(spell.getClass().getSimpleName().compareTo("ArmorSpell")==0)
					id= 315;
				else if(spell.getClass().getSimpleName().compareTo("EmpowerSpell")==0)
					id= 373;
				else if(spell.getClass().getSimpleName().compareTo("ForcetossSpell")==0)
					id= 33;
				else if(spell.getClass().getSimpleName().compareTo("ExplodeSpell")==0)
					id= 46;
				else if(spell.getClass().getSimpleName().compareTo("ThrowBlockSpell")==0)
					id= 145;
				else if(spell.getClass().getSimpleName().compareTo("VolleySpell")==0)
					id= 262;
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + spellName, id, (byte)0,1,Arrays.asList(ChatColor.WHITE+spell.getClass().getSimpleName()+"",ChatColor.YELLOW+"[좌 클릭시 스킬 등록]"), (byte)count, inv);
			}
		}
		
		if(counter>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,(byte)0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), (byte)50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,(byte)0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), (byte)48, inv);
		switch(sort)
		{
		case 0:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[모두 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 1:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[더미 스펠 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 2:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[영역 스펠 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 3:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[멀티 스펠 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 4:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[pain 스펠 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 5:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[P.P 제외 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,(byte)0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+""+skillLevel), (byte)45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,(byte)0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+skillName), (byte)53, inv);
		player.openInventory(inv);
	}
	
	public void Stack2(String Display, int ID, byte DATA, int Stack, List<String> Lore, byte Loc, Inventory inventory)
	{
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(Stack);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.setDisplayName(Display);
		meta.setLore(Lore);
		item.setItemMeta(meta);
		inventory.setItem(Loc, item);
	}

	public void ShowAllMaigcGUIClick(InventoryClickEvent event)
	{
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		String skillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		int skillLevel = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		int sort = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(1)));
		
		Player player = (Player) event.getWhoClicked();
		effect.SoundEffect s = new effect.SoundEffect();

		event.setCancelled(true);
		switch (event.getSlot())
		{
			case 45://이전 목록으로
				s.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
				new skill.OPboxSkillGui().SkillRankOptionGUI(player, skillName, (short) skillLevel);
				break;
			case 48://이전 페이지
				s.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
				ShowAllMaigcGUI(player, (byte)(page-1), skillName, (short)skillLevel, (byte)sort);
				break;
			case 49://정렬 방식 변경
				s.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 0.8F);
				if(sort <= 4)
					sort = sort +1;
				else
					sort = 0;
				ShowAllMaigcGUI(player, (byte)0, skillName, (short) skillLevel, (byte)sort);
				break;
			case 50://다음 페이지
				s.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
				ShowAllMaigcGUI(player, (byte)(page+1), skillName, (short) skillLevel, (byte)sort);
				break;
			case 53://나가기
				s.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			default:
				s.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.8F);
				YamlLoader skillList = new YamlLoader();
				skillList.getConfig("Skill/SkillList.yml");
				skillList.set(skillName+".SkillRank."+skillLevel+".MagicSpells", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				skillList.saveConfig();
				new skill.OPboxSkillGui().SkillRankOptionGUI(player, skillName, (short) skillLevel);
				return;
		}
	}
	
	public void CastSpell(Player player, String spellName)
	{
		Object[] spells = MagicSpells.spells().toArray();
		Spell spell = null;
		for(short count = 0; count < spells.length;count++)
		{
			spell = (Spell) spells[count];
			if(spell.getName().compareTo(spellName) == 0)
				spell.cast(player);
		}
	}
	
	@EventHandler
	public void manaregen(ManaChangeEvent event)
	{//ManaChangeEvent
		if(event.getReason() == ManaChangeReason.REGEN)
		{
			Player player = event.getPlayer();
			setPlayerMaxAndNowMana(player);
		}
	}
	
	
	
	public int getPlayerMana(Player player)
	{
		return MagicSpells.getManaHandler().getMana(player);
	}

	public void setPlayerMaxAndNowMana(Player player)
	{
		int bonusMana = BattleCalculator.getPlayerEquipmentStat(player, "마나", false, null)[0];
		int maxMana = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxMP()+bonusMana;
		if(maxMana > 0 && MainServerOption.MagicSpellsEnable)
		{
			try
			{
				MagicSpells.getManaHandler().setMaxMana(player, maxMana);
			}
			catch(NullPointerException e)
			{				
			}
		}
		return;
	}

	public void setSlotChangePlayerMaxAndNowMana(Player player, ItemStack newSlot)
	{
		int bonusMana = BattleCalculator.getPlayerEquipmentStat(player, "마나", false, newSlot)[0];
		int maxMana = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxMP()+bonusMana;

		if(maxMana > 0 && main.MainServerOption.MagicSpellsEnable)
		{
			try
			{
				MagicSpells.getManaHandler().setMaxMana(player, maxMana);
			}
			catch(NoClassDefFoundError e)
			{
				System.out.println("매직 스펠의 com/nisovin/magicspells/MagicSpells 클래스를 찾을 수 없습니다!");
			}
			catch(NullPointerException e)
			{
			}
		}
		return;
	}
	
	public void DrinkManaPotion(Player player, int mana)
	{
		int m = MagicSpells.getManaHandler().getMana(player)+mana;
		if(MagicSpells.getManaHandler().getMana(player)+mana >= MagicSpells.getManaHandler().getMaxMana(player))
			m = MagicSpells.getManaHandler().getMaxMana(player);
		MagicSpells.getManaHandler().setMana(player, m, ManaChangeReason.POTION);
	}
}