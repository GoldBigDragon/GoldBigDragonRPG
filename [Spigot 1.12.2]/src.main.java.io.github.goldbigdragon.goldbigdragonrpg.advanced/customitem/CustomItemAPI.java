package customitem;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import customitem.gui.ApplyToolGui;
import effect.SoundEffect;
import main.MainServerOption;
import servertick.ServerTickMain;
import util.YamlLoader;

public class CustomItemAPI {
	
	public String[] loreCreater(int itemNumber)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");
		StringBuilder lore = new StringBuilder();
		String type = ChatColor.stripColor(itemYaml.getString(itemNumber+".ShowType"));
		if(type.equals("[�з�]"))
		{
			lore.append(lore+itemYaml.getString(itemNumber+".Type"));
			for(int count = 0; count<20-itemYaml.getString(itemNumber+".Type").length();count++)
				lore.append(" ");
			if(itemYaml.getString(itemNumber+".JOB").equals("����")) {
				lore.append(itemYaml.getString(itemNumber+".Grade"));
			}
			else {
				lore.append(itemYaml.getString(itemNumber+".Grade"));
				lore.append("%enter%��6��� ���� ���� : ��f");
				lore.append(itemYaml.getString(itemNumber+".JOB"));
			}
			lore.append("%enter%%enter%");

			if(itemYaml.getInt(itemNumber+".MinDamage") != 0||itemYaml.getInt(itemNumber+".MaxDamage") != 0)
				lore.append("��c �� "+main.MainServerOption.damage+" : ��f" + itemYaml.getInt(itemNumber+".MinDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxDamage")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MinMaDamage") != 0||itemYaml.getInt(itemNumber+".MaxMaDamage") != 0)
				lore.append("��3 �� "+main.MainServerOption.magicDamage+" : ��f" + itemYaml.getInt(itemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%");
			if(itemYaml.getInt(itemNumber+".DEF") != 0)
				lore.append("��7 �� ��� : ��f" + itemYaml.getInt(itemNumber+".DEF")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Protect") != 0)
				lore.append("��f �� ��ȣ : ��f" +itemYaml.getInt(itemNumber+".Protect")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaDEF") != 0)
				lore.append("��9 �� ���� ��� : ��f" +itemYaml.getInt(itemNumber+".MaDEF")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaProtect") != 0)
				lore.append("��1 �� ���� ��ȣ : ��f" + itemYaml.getInt(itemNumber+".MaProtect")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Balance") != 0)
				lore.append("��2 �� �뷱�� : ��f" + itemYaml.getInt(itemNumber+".Balance")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Critical") != 0)
				lore.append("��e �� ũ��Ƽ�� : ��f" + itemYaml.getInt(itemNumber+".Critical")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxDurability") > 0)
				lore.append("��6 �� ������ : ��f" + itemYaml.getInt(itemNumber+".Durability")+" / "+ itemYaml.getInt(itemNumber+".MaxDurability")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore.append("��f �� ���õ� : 0.0%��f%enter%");
			
			lore.append("��6___--------------------___%enter%��6       [������ ����]");
			lore.append("%enter%"+ itemYaml.getString(itemNumber+".Lore")+"%enter%");
			lore.append("��6---____________________---%enter%");


			if(itemYaml.getInt(itemNumber+".HP")!=0||itemYaml.getInt(itemNumber+".MP")!=0||
					itemYaml.getInt(itemNumber+".STR")!=0||itemYaml.getInt(itemNumber+".DEX")!=0||
					itemYaml.getInt(itemNumber+".INT")!=0||itemYaml.getInt(itemNumber+".WILL")!=0||
					itemYaml.getInt(itemNumber+".LUK")!=0)
			{
				lore.append("��3___--------------------___%enter%��3       [���ʽ� �ɼ�]%enter%");
				if(itemYaml.getInt(itemNumber+".HP") > 0)
					lore.append("��3 �� ����� : " + itemYaml.getInt(itemNumber+".HP")+"%enter%");
				else if(itemYaml.getInt(itemNumber+".HP") < 0)
					lore.append("��c �� ����� : " + itemYaml.getInt(itemNumber+".HP")+"%enter%");
				if(itemYaml.getInt(itemNumber+".MP") > 0)
					lore.append("��3 �� ���� : " + itemYaml.getInt(itemNumber+".MP")+"%enter%");
				else if(itemYaml.getInt(itemNumber+".MP") < 0)
					lore.append("��c �� ���� : " + itemYaml.getInt(itemNumber+".MP")+"%enter%");
				if(itemYaml.getInt(itemNumber+".STR") > 0)
					lore.append("��3 �� "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%");
				else if(itemYaml.getInt(itemNumber+".STR") < 0)
					lore.append("��c �� "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%");
				if(itemYaml.getInt(itemNumber+".DEX") > 0)
					lore.append("��3 �� "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%");
				else if(itemYaml.getInt(itemNumber+".DEX") < 0)
					lore.append("��c �� "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%");
				if(itemYaml.getInt(itemNumber+".INT") > 0)
					lore.append("��3 �� "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%");
				else if(itemYaml.getInt(itemNumber+".INT") < 0)
					lore.append("��c �� "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%");
				if(itemYaml.getInt(itemNumber+".WILL") > 0)
					lore.append("��3 �� "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%");
				else if(itemYaml.getInt(itemNumber+".WILL") < 0)
					lore.append("��c �� "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%");
				if(itemYaml.getInt(itemNumber+".LUK") > 0)
					lore.append("��3 �� "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%");
				else if(itemYaml.getInt(itemNumber+".LUK") < 0)
					lore.append("��c �� "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%");
				lore.append("��3---____________________---%enter%");
			}

			if(itemYaml.getInt(itemNumber+".MaxSocket")!=0||itemYaml.getInt(itemNumber+".MaxUpgrade")!=0)
			{
				lore.append("��d___--------------------___%enter%��d       [������ ��ȭ]%enter%");
				if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0 && itemYaml.getInt(itemNumber+".MaxSocket") > 0)
				{
					lore.append("��5 �� ���� : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%");
					lore.append("��9 �� �� : ");
					for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
						lore.append("��7�� ");
				}
				else if(itemYaml.getInt(itemNumber+".MaxUpgrade") <= 0 && itemYaml.getInt(itemNumber+".MaxSocket") > 0)
				{
					lore.append("��9 �� �� : ");
					for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
						lore.append("��7�� ");
				}
				else
					lore.append("��5 �� ���� : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade"));
				lore.append("%enter%��d---____________________---%enter%");
			}
			if(itemYaml.getInt(itemNumber+".MinLV")!=0||itemYaml.getInt(itemNumber+".MinRLV")!=0||
					itemYaml.getInt(itemNumber+".MinSTR")!=0||itemYaml.getInt(itemNumber+".MinDEX")!=0||
					itemYaml.getInt(itemNumber+".MinINT")!=0||itemYaml.getInt(itemNumber+".MinWILL")!=0||
					itemYaml.getInt(itemNumber+".MinLUK")!=0)
			{
				lore.append("��4___--------------------___%enter%��4       [���� ����]%enter%");
				if(itemYaml.getInt(itemNumber+".MinLV") > 0)
					lore.append("��4 �� �ּ� ���� : " + itemYaml.getInt(itemNumber+".MinLV")+"%enter%");
				if(itemYaml.getInt(itemNumber+".MinRLV") > 0)
					lore.append("��4 �� �ּ� �������� : " + itemYaml.getInt(itemNumber+".MinRLV")+"%enter%");
				if(itemYaml.getInt(itemNumber+".MinSTR") > 0)
					lore.append("��4 �� �ּ� "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".MinSTR")+"%enter%");
				if(itemYaml.getInt(itemNumber+".MinDEX") > 0)
					lore.append("��4 �� �ּ� "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".MinDEX")+"%enter%");
				if(itemYaml.getInt(itemNumber+".MinINT") > 0)
					lore.append("��4 �� �ּ� "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".MinINT")+"%enter%");
				if(itemYaml.getInt(itemNumber+".MinWILL") > 0)
					lore.append("��4 �� �ּ� "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".MinWILL")+"%enter%");
				if(itemYaml.getInt(itemNumber+".MinLUK") > 0)
					lore.append("��4 �� �ּ� "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".MinLUK")+"%enter%");
				lore.append("��4---____________________---%enter%");
				
			}
		}
		else if(type.equals("[��ȣ]"))
		{
			lore.append(itemYaml.getString(itemNumber+".Type"));
			for(int count = 0; count<20-itemYaml.getString(itemNumber+".Type").length();count++)
				lore.append(" ");
			if(itemYaml.getString(itemNumber+".JOB").equals("����"))
				lore.append(itemYaml.getString(itemNumber+".Grade")+"%enter%%enter%");
			else
				lore.append(itemYaml.getString(itemNumber+".Grade")+"%enter%��6��� ���� ���� : ��f" + itemYaml.getString(itemNumber+".JOB")+"%enter%%enter%");
				
			if(itemYaml.getInt(itemNumber+".MinDamage") != 0||itemYaml.getInt(itemNumber+".MaxDamage") != 0)
				lore.append("��c �� "+main.MainServerOption.damage+" : ��f" + itemYaml.getInt(itemNumber+".MinDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxDamage")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MinMaDamage") != 0||itemYaml.getInt(itemNumber+".MaxMaDamage") != 0)
				lore.append("��3 �� "+main.MainServerOption.magicDamage+" : ��f" + itemYaml.getInt(itemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%");
			if(itemYaml.getInt(itemNumber+".DEF") != 0)
				lore.append("��7 �� ��� : ��f" + itemYaml.getInt(itemNumber+".DEF")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Protect") != 0)
				lore.append("��f �� ��ȣ : ��f" +itemYaml.getInt(itemNumber+".Protect")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaDEF") != 0)
				lore.append("��9 �� ���� ��� : ��f" +itemYaml.getInt(itemNumber+".MaDEF")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaProtect") != 0)
				lore.append("��1 �� ���� ��ȣ : ��f" + itemYaml.getInt(itemNumber+".MaProtect")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Balance") != 0)
				lore.append("��2 �� �뷱�� : ��f" + itemYaml.getInt(itemNumber+".Balance")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Critical") != 0)
				lore.append("��e �� ũ��Ƽ�� : ��f" + itemYaml.getInt(itemNumber+".Critical")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxDurability") > 0)
				lore.append("��6 �� ������ : ��f" + itemYaml.getInt(itemNumber+".Durability")+" / "+ itemYaml.getInt(itemNumber+".MaxDurability")+"%enter%");

			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore.append("��f �� ���õ� : 0.0%��f%enter%");
			
			lore.append("%enter%"+ itemYaml.getString(itemNumber+".Lore")+"%enter%");


			if(itemYaml.getInt(itemNumber+".HP") > 0)
				lore.append("��3 �� ����� : " + itemYaml.getInt(itemNumber+".HP")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".HP") < 0)
				lore.append("��c �� ����� : " + itemYaml.getInt(itemNumber+".HP")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MP") > 0)
				lore.append("��3 �� ���� : " + itemYaml.getInt(itemNumber+".MP")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".MP") < 0)
				lore.append("��c �� ���� : " + itemYaml.getInt(itemNumber+".MP")+"%enter%");
			if(itemYaml.getInt(itemNumber+".STR") > 0)
				lore.append("��3 �� "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".STR") < 0)
				lore.append("��c �� "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%");
			if(itemYaml.getInt(itemNumber+".DEX") > 0)
				lore.append("��3 �� "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".DEX") < 0)
				lore.append("��c �� "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%");
			if(itemYaml.getInt(itemNumber+".INT") > 0)
				lore.append("��3 �� "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".INT") < 0)
				lore.append("��c �� "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%");
			if(itemYaml.getInt(itemNumber+".WILL") > 0)
				lore.append("��3 �� "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".WILL") < 0)
				lore.append("��c �� "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%");
			if(itemYaml.getInt(itemNumber+".LUK") > 0)
				lore.append("��3 �� "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".LUK") < 0)
				lore.append("��c �� "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%");
			
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore.append("%enter%��5 �� ���� : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxSocket") > 0)
			{
				lore.append("%enter%��9 �� �� : ");
				for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
					lore.append("��7�� ");
			}

			if(itemYaml.getInt(itemNumber+".MinLV")!=0||itemYaml.getInt(itemNumber+".MinRLV")!=0||
					itemYaml.getInt(itemNumber+".MinSTR")!=0||itemYaml.getInt(itemNumber+".MinDEX")!=0||
					itemYaml.getInt(itemNumber+".MinINT")!=0||itemYaml.getInt(itemNumber+".MinWILL")!=0||
					itemYaml.getInt(itemNumber+".MinLUK")!=0)
				lore.append("%enter%");
			
			if(itemYaml.getInt(itemNumber+".MinLV") > 0)
				lore.append("��4%enter% �� �ּ� ���� : " + itemYaml.getInt(itemNumber+".MinLV"));
			if(itemYaml.getInt(itemNumber+".MinRLV") > 0)
				lore.append("��4%enter% �� �ּ� �������� : " + itemYaml.getInt(itemNumber+".MinRLV"));
			if(itemYaml.getInt(itemNumber+".MinSTR") > 0)
				lore.append("��4%enter% �� �ּ� "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".MinSTR"));
			if(itemYaml.getInt(itemNumber+".MinDEX") > 0)
				lore.append("��4%enter% �� �ּ� "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".MinDEX"));
			if(itemYaml.getInt(itemNumber+".MinINT") > 0)
				lore.append("��4%enter% �� �ּ� "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".MinINT"));
			if(itemYaml.getInt(itemNumber+".MinWILL") > 0)
				lore.append("��4%enter% �� �ּ� "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".MinWILL"));
			if(itemYaml.getInt(itemNumber+".MinLUK") > 0)
				lore.append("��4%enter% �� �ּ� "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".MinLUK"));
		}
		else if(type.equals("[�÷�]"))
		{

			lore.append(itemYaml.getString(itemNumber+".Type"));
			for(int count = 0; count<20-itemYaml.getString(itemNumber+".Type").length();count++)
				lore.append(" ");
			if(itemYaml.getString(itemNumber+".JOB").equals("����"))
				lore.append(itemYaml.getString(itemNumber+".Grade")+"%enter%%enter%");
			else
				lore.append(itemYaml.getString(itemNumber+".Grade")+"%enter%��6��� ���� ���� : ��f" + itemYaml.getString(itemNumber+".JOB")+"%enter%%enter%");
				
			if(itemYaml.getInt(itemNumber+".MinDamage") != 0||itemYaml.getInt(itemNumber+".MaxDamage") != 0)
				lore.append(ChatColor.RED + main.MainServerOption.damage+" : ��f" + itemYaml.getInt(itemNumber+".MinDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxDamage")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MinMaDamage") != 0||itemYaml.getInt(itemNumber+".MaxMaDamage") != 0)
				lore.append("��3"+main.MainServerOption.magicDamage+" : ��f" + itemYaml.getInt(itemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%");
			if(itemYaml.getInt(itemNumber+".DEF") != 0)
				lore.append("��7��� : ��f" + itemYaml.getInt(itemNumber+".DEF")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Protect") != 0)
				lore.append("��f��ȣ : ��f" +itemYaml.getInt(itemNumber+".Protect")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaDEF") != 0)
				lore.append("��9���� ��� : ��f" +itemYaml.getInt(itemNumber+".MaDEF")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaProtect") != 0)
				lore.append("��1���� ��ȣ : ��f" + itemYaml.getInt(itemNumber+".MaProtect")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Balance") != 0)
				lore.append("��2�뷱�� : ��f" + itemYaml.getInt(itemNumber+".Balance")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Critical") != 0)
				lore.append("��eũ��Ƽ�� : ��f" + itemYaml.getInt(itemNumber+".Critical")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxDurability") > 0)
				lore.append("��6������ : ��f" + itemYaml.getInt(itemNumber+".Durability")+" / "+ itemYaml.getInt(itemNumber+".MaxDurability")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore.append("��f���õ� : 0.0%��f%enter%");
			
			lore.append("%enter%"+ itemYaml.getString(itemNumber+".Lore")+"%enter%");


			if(itemYaml.getInt(itemNumber+".HP") > 0)
				lore.append("��3����� : " + itemYaml.getInt(itemNumber+".HP")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".HP") < 0)
				lore.append("��c����� : " + itemYaml.getInt(itemNumber+".HP")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MP") > 0)
				lore.append("��3���� : " + itemYaml.getInt(itemNumber+".MP")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".MP") < 0)
				lore.append("��c���� : " + itemYaml.getInt(itemNumber+".MP")+"%enter%");
			if(itemYaml.getInt(itemNumber+".STR") > 0)
				lore.append("��3"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".STR") < 0)
				lore.append("��c"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%");
			if(itemYaml.getInt(itemNumber+".DEX") > 0)
				lore.append("��3"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".DEX") < 0)
				lore.append("��c"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%");
			if(itemYaml.getInt(itemNumber+".INT") > 0)
				lore.append("��3"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".INT") < 0)
				lore.append("��c"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%");
			if(itemYaml.getInt(itemNumber+".WILL") > 0)
				lore.append("��3"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".WILL") < 0)
				lore.append("��c"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%");
			if(itemYaml.getInt(itemNumber+".LUK") > 0)
				lore.append("��3"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".LUK") < 0)
				lore.append("��c"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%");
			
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore.append("%enter%��5���� : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxSocket") > 0)
			{
				lore.append("%enter%��9�� : ");
				for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
					lore.append("��7�� ");
			}
			if(itemYaml.getInt(itemNumber+".MinLV")!=0||itemYaml.getInt(itemNumber+".MinRLV")!=0||
					itemYaml.getInt(itemNumber+".MinSTR")!=0||itemYaml.getInt(itemNumber+".MinDEX")!=0||
					itemYaml.getInt(itemNumber+".MinINT")!=0||itemYaml.getInt(itemNumber+".MinWILL")!=0||
					itemYaml.getInt(itemNumber+".MinLUK")!=0)
				lore.append("%enter%");
			if(itemYaml.getInt(itemNumber+".MinLV") > 0)
				lore.append("%enter%��4�ּ� ���� : " + itemYaml.getInt(itemNumber+".MinLV"));
			if(itemYaml.getInt(itemNumber+".MinRLV") > 0)
				lore.append("%enter%��4�ּ� �������� : " + itemYaml.getInt(itemNumber+".MinRLV"));
			if(itemYaml.getInt(itemNumber+".MinSTR") > 0)
				lore.append("%enter%��4�ּ� "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".MinSTR"));
			if(itemYaml.getInt(itemNumber+".MinDEX") > 0)
				lore.append("%enter%��4�ּ� "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".MinDEX"));
			if(itemYaml.getInt(itemNumber+".MinINT") > 0)
				lore.append("%enter%��4�ּ� "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".MinINT"));
			if(itemYaml.getInt(itemNumber+".MinWILL") > 0)
				lore.append("%enter%��4�ּ� "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".MinWILL"));
			if(itemYaml.getInt(itemNumber+".MinLUK") > 0)
				lore.append("%enter%��4�ּ� "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".MinLUK"));
		}
		else
		{
			lore.append(itemYaml.getString(itemNumber+".Type"));
			for(int count = 0; count<20-itemYaml.getString(itemNumber+".Type").length();count++)
				lore.append(" ");
			if(itemYaml.getString(itemNumber+".JOB").equals("����"))
				lore.append(itemYaml.getString(itemNumber+".Grade")+"%enter%%enter%");
			else
				lore.append(itemYaml.getString(itemNumber+".Grade")+"%enter%��f��� ���� ���� : " + itemYaml.getString(itemNumber+".JOB")+"%enter%%enter%");
				
			if(itemYaml.getInt(itemNumber+".MinDamage") != 0||itemYaml.getInt(itemNumber+".MaxDamage") != 0)
				lore.append("��f"+main.MainServerOption.damage+" : " + itemYaml.getInt(itemNumber+".MinDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxDamage")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MinMaDamage") != 0||itemYaml.getInt(itemNumber+".MaxMaDamage") != 0)
				lore.append("��f"+main.MainServerOption.magicDamage+" : " + itemYaml.getInt(itemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%");
			if(itemYaml.getInt(itemNumber+".DEF") != 0)
				lore.append("��f��� : " + itemYaml.getInt(itemNumber+".DEF")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Protect") != 0)
				lore.append("��f��ȣ : " + itemYaml.getInt(itemNumber+".Protect")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaDEF") != 0)
				lore.append("��f���� ��� : " + itemYaml.getInt(itemNumber+".MaDEF")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaProtect") != 0)
				lore.append("��f���� ��ȣ : " + itemYaml.getInt(itemNumber+".MaProtect")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Balance") != 0)
				lore.append("��f�뷱�� : " + itemYaml.getInt(itemNumber+".Balance")+"%enter%");
			if(itemYaml.getInt(itemNumber+".Critical") != 0)
				lore.append("��fũ��Ƽ�� : " + itemYaml.getInt(itemNumber+".Critical")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxDurability") > 0)
				lore.append("��f������ : " + itemYaml.getInt(itemNumber+".Durability")+" / "+ itemYaml.getInt(itemNumber+".MaxDurability")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore.append("��f���õ� : 0.0%��f%enter%");
			
			lore.append("%enter%"+ itemYaml.getString(itemNumber+".Lore")+"%enter%");


			if(itemYaml.getInt(itemNumber+".HP") > 0)
				lore.append("��3����� : " + itemYaml.getInt(itemNumber+".HP")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".HP") < 0)
				lore.append("��c����� : " + itemYaml.getInt(itemNumber+".HP")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MP") > 0)
				lore.append("��3���� : " + itemYaml.getInt(itemNumber+".MP")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".MP") < 0)
				lore.append("��c���� : " + itemYaml.getInt(itemNumber+".MP")+"%enter%");
			if(itemYaml.getInt(itemNumber+".STR") > 0)
				lore.append("��3"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".STR") < 0)
				lore.append("��c"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%");
			if(itemYaml.getInt(itemNumber+".DEX") > 0)
				lore.append("��3"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".DEX") < 0)
				lore.append("��c"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%");
			if(itemYaml.getInt(itemNumber+".INT") > 0)
				lore.append("��3"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".INT") < 0)
				lore.append("��c"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%");
			if(itemYaml.getInt(itemNumber+".WILL") > 0)
				lore.append("��3"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".WILL") < 0)
				lore.append("��c"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%");
			if(itemYaml.getInt(itemNumber+".LUK") > 0)
				lore.append("��3"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%");
			else if(itemYaml.getInt(itemNumber+".LUK") < 0)
				lore.append("��c"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%");
			
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore.append("%enter%��f���� : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%");
			if(itemYaml.getInt(itemNumber+".MaxSocket") > 0)
			{
				lore.append("%enter%��f�� : ");
				for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
					lore.append("��7�� ");
			}
			if(itemYaml.getInt(itemNumber+".MinLV")!=0||itemYaml.getInt(itemNumber+".MinRLV")!=0||
					itemYaml.getInt(itemNumber+".MinSTR")!=0||itemYaml.getInt(itemNumber+".MinDEX")!=0||
					itemYaml.getInt(itemNumber+".MinINT")!=0||itemYaml.getInt(itemNumber+".MinWILL")!=0||
					itemYaml.getInt(itemNumber+".MinLUK")!=0)
				lore.append("%enter%");
			if(itemYaml.getInt(itemNumber+".MinLV") > 0)
				lore.append("%enter%��f�ּ� ���� : " + itemYaml.getInt(itemNumber+".MinLV"));
			if(itemYaml.getInt(itemNumber+".MinRLV") > 0)
				lore.append("%enter%��f�ּ� �������� : " + itemYaml.getInt(itemNumber+".MinRLV"));
			if(itemYaml.getInt(itemNumber+".MinSTR") > 0)
				lore.append("%enter%��f�ּ� "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".MinSTR"));
			if(itemYaml.getInt(itemNumber+".MinDEX") > 0)
				lore.append("%enter%��f�ּ� "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".MinDEX"));
			if(itemYaml.getInt(itemNumber+".MinINT") > 0)
				lore.append("%enter%��f�ּ� "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".MinINT"));
			if(itemYaml.getInt(itemNumber+".MinWILL") > 0)
				lore.append("%enter%��f�ּ� "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".MinWILL"));
			if(itemYaml.getInt(itemNumber+".MinLUK") > 0)
				lore.append("%enter%��f�ּ� "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".MinLUK"));
		}
		
		String[] scriptA = lore.toString().split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}

	public String[] useableLoreCreater(int itemNumber)
	{
	  	YamlLoader useableItemYaml = new YamlLoader();
		useableItemYaml.getConfig("Item/Consume.yml");
		String lore = "";
		String type = ChatColor.stripColor(useableItemYaml.getString(itemNumber+".ShowType"));

		lore = lore+useableItemYaml.getString(itemNumber+".Type");
		for(int count = 0; count<20-useableItemYaml.getString(itemNumber+".Type").length();count++)
			lore = lore+" ";
		lore = lore+useableItemYaml.getString(itemNumber+".Grade")+"%enter% %enter%";
		
		switch(type)
		{
		case "[�з�]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[��ȯ��]":
				lore = lore+"��9 �� ���� : ��f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"��9 �� X ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"��9 �� Y ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"��9 �� Z ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[�ֹ���]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"��3 + ���� ����Ʈ : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"��c - ���� ����Ʈ : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"��3 + ��ų ����Ʈ : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"��c - ��ų ����Ʈ : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"��3 + ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"��c - ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"��3 + ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"��c - ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"��3 + ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"��c - ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"��3 + ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"��c - ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"��3 + �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"��c - �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"��3 + ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"��c - ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"��3 + ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"��c - ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"��3 + ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"��c - ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"��3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"��c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"��3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"��c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"��3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"��c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"��3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"��c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"��3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"��c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[��ų��]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%��c [�ƹ��͵� ���� �� å]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [�� Ŭ���� �Ʒ� ��ų ȹ��]%enter%��3 + ��f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[�Һ�]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"��3 + ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"��c - ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"��3 + ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"��c - ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"��3 + ������ : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"��c - ������ : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"��6��l + ȯ��%enter%";
				break;
			case "[��]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"��3 + �ּ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"��c - �ּ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"��3 + �ִ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"��c - �ִ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"��3 + �ּ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"��c - �ּ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"��3 + �ִ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"��c - �ִ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"��3 + ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"��c - ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"��3 + ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"��c - ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"��3 + ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"��c - ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"��3 + ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"��c - ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"��3 + �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"��c - �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"��3 + ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"��c - ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"��3 + ���� ������ ���� : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"��c - ���� ������ ���� : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"��3 + �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"��c - �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";


				if(useableItemYaml.getInt(itemNumber+".HP")!=0||useableItemYaml.getInt(itemNumber+".MP")!=0||
				useableItemYaml.getInt(itemNumber+".STR")!=0||useableItemYaml.getInt(itemNumber+".DEX")!=0||
				useableItemYaml.getInt(itemNumber+".INT")!=0||useableItemYaml.getInt(itemNumber+".WILL")!=0||
				useableItemYaml.getInt(itemNumber+".LUK")!=0)
				{
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"��3 + ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"��c - ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"��3 + ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"��c - ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"��3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"��c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"��3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"��c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"��3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"��c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"��3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"��c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"��3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"��c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"��5 + ���� : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"��c - ���� : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			case "[����]":
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"��3 + �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Proficiency") > 0)
					lore = lore+"��3 + ���õ� ���� : " + useableItemYaml.getInt(itemNumber+".Proficiency")+"%enter%";
				break;
			}

			lore = lore+"%enter%��6___--------------------___%enter%��6       [������ ����]";
			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%";
			lore = lore+"��6---____________________---%enter%";
		}
		break;
		case "[��ȣ]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[��ȯ��]":
				lore = lore+"��9 �� ���� : ��f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"��9 �� X ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"��9 �� Y ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"��9 �� Z ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[�ֹ���]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"��3 + ���� ����Ʈ : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"��c - ���� ����Ʈ : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"��3 + ��ų ����Ʈ : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"��c - ��ų ����Ʈ : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"��3 + ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"��c - ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"��3 + ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"��c - ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"��3 + ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"��c - ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"��3 + ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"��c - ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"��3 + �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"��c - �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"��3 + ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"��c - ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"��3 + ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"��c - ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"��3 + ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"��c - ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"��3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"��c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"��3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"��c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"��3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"��c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"��3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"��c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"��3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"��c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[��ų��]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%��c [�ƹ��͵� ���� �� å]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [�� Ŭ���� �Ʒ� ��ų ȹ��]%enter%��3 + ��f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[�Һ�]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"��3 + ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"��c - ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"��3 + ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"��c - ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"��3 + ������ : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"��c - ������ : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"��6��l + ȯ��%enter%";
				break;
			case "[��]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"��3 + �ּ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"��c - �ּ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"��3 + �ִ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"��c - �ִ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"��3 + �ּ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"��c - �ּ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"��3 + �ִ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"��c - �ִ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"��3 + ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"��c - ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"��3 + ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"��c - ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"��3 + ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"��c - ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"��3 + ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"��c - ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"��3 + �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"��c - �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"��3 + ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"��c - ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"��3 + ���� ������ ���� : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"��c - ���� ������ ���� : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"��3 + �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"��c - �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".HP")!=0||useableItemYaml.getInt(itemNumber+".MP")!=0||
				useableItemYaml.getInt(itemNumber+".STR")!=0||useableItemYaml.getInt(itemNumber+".DEX")!=0||
				useableItemYaml.getInt(itemNumber+".INT")!=0||useableItemYaml.getInt(itemNumber+".WILL")!=0||
				useableItemYaml.getInt(itemNumber+".LUK")!=0)
				{
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"��3 + ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"��c - ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"��3 + ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"��c - ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"��3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"��c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"��3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"��c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"��3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"��c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"��3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"��c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"��3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"��c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"��5 + ���� : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"��c - ���� : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			case "[����]":
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"��3 + �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Proficiency") > 0)
					lore = lore+"��3 + ���õ� ���� : " + useableItemYaml.getInt(itemNumber+".Proficiency")+"%enter%";
				break;
			}			
			
			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";

		}
		break;
		case "[�÷�]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[��ȯ��]":
				lore = lore+"��9 ���� : ��f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"��9 X ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"��9 Y ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"��9 Z ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[�ֹ���]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"��3 ���� ����Ʈ : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"��c ���� ����Ʈ : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"��3 ��ų ����Ʈ : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"��c ��ų ����Ʈ : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"��3 ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"��c ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"��3 ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"��c ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"��3 ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"��c ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"��3 ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"��c ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"��3 �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"��c �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"��3 ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"��c ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"��3 ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"��c ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"��3 ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"��c ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"��3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"��c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"��3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"��c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"��3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"��c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"��3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"��c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"��3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"��c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[��ų��]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%��c [�ƹ��͵� ���� �� å]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [�� Ŭ���� �Ʒ� ��ų ȹ��]%enter%��3 + ��f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[�Һ�]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"��3 ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"��c ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"��3 ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"��c ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"��3 ������ : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"��c ������ : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"��6��l + ȯ��%enter%";
				break;
			case "[��]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"��3 �ּ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"��c �ּ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"��3 �ִ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"��c �ִ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"��3 �ּ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"��c �ּ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"��3 �ִ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"��c �ִ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"��3 ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"��c ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"��3 ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"��c ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"��3 ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"��c ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"��3 ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"��c ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"��3 �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"��c �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"��3 ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"��c ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"��3 ���� ������ ���� : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"��c ���� ������ ���� : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"��3 �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"��c �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";


					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"��3 ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"��c ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"��3 ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"��c ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"��3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"��c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"��3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"��c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"��3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"��c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"��3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"��c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"��3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"��c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"��5 ���� : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"��c ���� : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				break;
				case "[����]":
					if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
						lore = lore+"��3 �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Proficiency") > 0)
						lore = lore+"��3 ���õ� ���� : " + useableItemYaml.getInt(itemNumber+".Proficiency")+"%enter%";
				break;
			}

			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";


		}
		break;
		
			case "[���]":
			{
				switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
				{
				case "[��ȯ��]":
					lore = lore+"��f ���� : ��f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
					lore = lore+"��f X ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
					lore = lore+"��f Y ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
					lore = lore+"��f Z ��ǥ : ��f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
					break;
				case "[�ֹ���]":
					if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
						lore = lore+"��3 ���� ����Ʈ : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
						lore = lore+"��c ���� ����Ʈ : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
						lore = lore+"��3 ��ų ����Ʈ : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
						lore = lore+"��c ��ų ����Ʈ : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEF")>0)
						lore = lore+"��3 ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
						lore = lore+"��c ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")>0)
						lore = lore+"��3 ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")<0)
						lore = lore+"��c ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
						lore = lore+"��3 ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
						lore = lore+"��c ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
						lore = lore+"��3 ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
						lore = lore+"��c ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")>0)
						lore = lore+"��3 �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")<0)
						lore = lore+"��c �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")>0)
						lore = lore+"��3 ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")<0)
						lore = lore+"��c ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					
						if(useableItemYaml.getInt(itemNumber+".HP") > 0)
							lore = lore+"��3 ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
							lore = lore+"��c ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MP") > 0)
							lore = lore+"��3 ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
							lore = lore+"��c ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".STR") > 0)
							lore = lore+"��3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
							lore = lore+"��c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
							lore = lore+"��3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
							lore = lore+"��c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".INT") > 0)
							lore = lore+"��3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
							lore = lore+"��c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
							lore = lore+"��3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
							lore = lore+"��c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
							lore = lore+"��3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
							lore = lore+"��c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					break;
				case "[��ų��]":
					if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
						lore = lore+"%enter%��f [�ƹ��͵� ���� �� å]%enter%";
					else
						lore = lore+"%enter%��f"  +" [�� Ŭ���� �Ʒ� ��ų ȹ��]%enter%��f + ��f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
					break;
				case "[�Һ�]":
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"��3 ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"��c ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"��3 ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"��c ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
						lore = lore+"��3 ������ : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
						lore = lore+"��c ������ : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
					if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
						lore = lore+"��6��l + ȯ��%enter%";
					break;
				case "[��]":
					if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
						lore = lore+"��3 �ּ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
						lore = lore+"��c �ּ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
						lore = lore+"��3 �ִ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
						lore = lore+"��c �ִ� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
						lore = lore+"��3 �ּ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
						lore = lore+"��c �ּ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
						lore = lore+"��3 �ִ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
						lore = lore+"��c �ִ� ���� ���ݷ� : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
					
					
						
					if(useableItemYaml.getInt(itemNumber+".DEF")>0)
						lore = lore+"��3 ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
						lore = lore+"��c ��� : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")>0)
						lore = lore+"��3 ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")<0)
						lore = lore+"��c ��ȣ : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
						lore = lore+"��3 ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
						lore = lore+"��c ���� ��� : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
						lore = lore+"��3 ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
						lore = lore+"��c ���� ��ȣ : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")>0)
						lore = lore+"��3 �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")<0)
						lore = lore+"��c �뷱�� : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")>0)
						lore = lore+"��3 ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")<0)
						lore = lore+"��c ũ��Ƽ�� : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Durability")>0)
						lore = lore+"��3 ���� ������ ���� : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
						lore = lore+"��c ���� ������ ���� : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
						lore = lore+"��3 �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
						lore = lore+"��c �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
					
						if(useableItemYaml.getInt(itemNumber+".HP") > 0)
							lore = lore+"��3 ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
							lore = lore+"��c ����� : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MP") > 0)
							lore = lore+"��3 ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
							lore = lore+"��c ���� : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".STR") > 0)
							lore = lore+"��3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
							lore = lore+"��c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
							lore = lore+"��3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
							lore = lore+"��c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".INT") > 0)
							lore = lore+"��3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
							lore = lore+"��c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
							lore = lore+"��3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
							lore = lore+"��c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
							lore = lore+"��3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
							lore = lore+"��c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
							lore = lore+"��5 ���� : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
							lore = lore+"��3 ���� : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					break;
					case "[����]":
						if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
							lore = lore+"��3 �ִ� ������ ���� : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".Proficiency") > 0)
							lore = lore+"��3 ���õ� ���� : " + useableItemYaml.getInt(itemNumber+".Proficiency")+"%enter%";
					break;
				}
				
				lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";

			}
			break;
		}

		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}

	public void useAbleItemUse(Player player, String type)
	{
		ItemStack item = player.getInventory().getItemInMainHand();
		if(type.equals("��ȯ��"))
		{
			if(ServerTickMain.PlayerTaskList.containsKey(player.getName())==true)
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				new effect.SendPacket().sendActionBar(player, "��c��l[���� �ڷ���Ʈ�� �� �� ���� �����Դϴ�!]", false);
				return;
			}
			util.ETC etc = new util.ETC();
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime() >= etc.getSec())
			{
				player.sendMessage("��c[�̵� �Ұ�] : ��e"+((main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 - etc.getSec())/1000)+"��c �� �Ŀ� �̵� �����մϴ�!");
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				return;
			}
			String world = "";
			int x = 0;
			short y = 0;
			int z = 0;
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("����"))
						world = nowlore.split(" : ")[1];
					else if(nowlore.contains("X ��ǥ"))
						x = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Y ��ǥ"))
						y = Short.parseShort(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Z ��ǥ"))
						z = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			long utc= servertick.ServerTickMain.nowUTC-1;
			servertick.ServerTickObject STSO = new servertick.ServerTickObject(utc, "P_UTS");
			Location loc = player.getLocation();
			STSO.setTick(utc);//�ڷ���Ʈ ���� �ð�
			STSO.setCount(5);//�ڷ���Ʈ �ð�
			STSO.setString((byte)0, world+","+x+","+y+","+z);//�̵� ��ġ ����
			STSO.setString((byte)1, loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());//���� ��ġ ����
			STSO.setString((byte)2, player.getName());//�÷��̾� �̸� ����
			servertick.ServerTickMain.Schedule.put(utc, STSO);
			ServerTickMain.PlayerTaskList.put(player.getName(), ""+utc);
			new effect.PottionBuff().givePotionEffect(player, PotionEffectType.CONFUSION, 8, 255);
			SoundEffect.playSound(player, Sound.BLOCK_CLOTH_BREAK, 0.7F, 0.5F);
			SoundEffect.playSound(player, Sound.BLOCK_PORTAL_TRAVEL, 0.6F, 1.4F);
		}
		else if(type.equals("�ֹ���"))
		{
			if(item.getItemMeta().getDisplayName().equals("��2��3��4��3��3��l[���� �ʱ�ȭ �ֹ���]"))
			{
			  	YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				if(!configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
				{
					if(item.getAmount() != 1)
					{
						item.setAmount(item.getAmount()-1);
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
					}
					else
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
					int totalStatPoint = configYaml.getInt("DefaultStat.StatPoint")+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() - configYaml.getInt("DefaultStat.STR");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() - configYaml.getInt("DefaultStat.DEX");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() - configYaml.getInt("DefaultStat.INT");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() - configYaml.getInt("DefaultStat.WILL");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() - configYaml.getInt("DefaultStat.LUK");
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_STR(configYaml.getInt("DefaultStat.STR"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_DEX(configYaml.getInt("DefaultStat.DEX"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_INT(configYaml.getInt("DefaultStat.INT"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_WILL(configYaml.getInt("DefaultStat.WILL"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_LUK(configYaml.getInt("DefaultStat.LUK"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_StatPoint(totalStatPoint);
					SoundEffect.playSound(player, Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.2F, 0.5F);
					player.sendMessage("��e��l[SYSTEM] : ������ �ʱ�ȭ�Ǿ����ϴ�!");
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("��c[System] : ������ ���丮 �ý����� ��츸 ��� �����մϴ�!");
				}
				return;
			}
			
			int statPoint = 0;
			int skillPoint = 0;
			int def = 0;
			int protect = 0;
			int magicDef = 0;
			int magicProtect  = 0;
			int balance = 0;
			int critical  = 0;
			int hp  = 0;
			int mp  = 0;
			int str  = 0;
			int dex  = 0;
			int INT  = 0;
			int will  = 0;
			int luk  = 0;
			
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("����Ʈ"))
					{
						if(nowlore.contains("����"))
							statPoint = Integer.parseInt(nowlore.split(" : ")[1]);
						if(nowlore.contains("��ų"))
							skillPoint = Integer.parseInt(nowlore.split(" : ")[1]);
					}
					if(nowlore.contains("���"))
						if(nowlore.contains("����"))
							magicDef = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							def = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("��ȣ"))
						if(nowlore.contains("����"))
							magicProtect = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							protect = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("�뷱��"))
						balance = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("ũ��Ƽ��"))
						critical = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("�����"))
						hp = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("����"))
						mp = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statSTR))
						str = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statDEX))
						dex = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statINT))
						INT = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statWILL))
						will = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statLUK))
						luk = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(skillPoint!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_SkillPoint(skillPoint);
			if(statPoint!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(statPoint);
			if(def!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEF(def);
			if(protect!=0)
			main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Protect(protect);
			if(magicDef!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_DEF(magicDef);
			if(magicProtect!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_Protect(magicProtect);
			if(balance!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Balance(balance);
			if(critical!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Critical(critical);
			if(hp!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxHP(hp);
			if(mp!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxMP(mp);
			if(str!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(str);
			if(dex!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(dex);
			if(INT!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(INT);
			if(will!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(will);
			if(luk!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(luk);

			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(skillPoint>=0&&statPoint>=0&&def>=0&&protect>=0&&magicDef>=0&&magicProtect>=0&&balance>=0&&critical>=0&&hp>0
			&&mp>=0&&str>=0&&dex>=0&&INT>=0&&will>=0&&luk>0)
			{
				SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.8F, 0.5F);
				player.sendMessage("��a��l[      �ɷ�ġ�� ��� �Ͽ����ϴ�!      ]");
			}
			else if(skillPoint<0&&statPoint<0&&def<0&&protect<0&&magicDef<0&&magicProtect<0&&balance<0&&critical<0&&hp<0
					&&mp<0&&str<0&&dex<0&&INT<0&&will<0&&luk<0)
			{
				SoundEffect.playSound(player, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.8F, 0.5F);
				player.sendMessage("��c��l[      �ɷ�ġ�� ���� �Ͽ����ϴ�!      ]");
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.5F);
				player.sendMessage("��e��l[      �ɷ�ġ�� ��ȭ�� ������ϴ�!      ]");
			}
		}
		else if(type.equals("��ų��"))
		{
		  	YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
			{
				String skillName = null;
				for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
				{
					String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
					if(nowlore.contains("[��")&&nowlore.contains("Ŭ����")&&nowlore.contains("�Ʒ�")&&
							nowlore.contains("��ų")&&nowlore.contains("ȹ��]"))
					{
						nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter+1));
						skillName = nowlore.replace(" + ", "");
						break;
					}
				}
				if(skillName == null)
					return;
			  	YamlLoader skillYaml = new YamlLoader();
				skillYaml.getConfig("Skill/SkillList.yml");
				if(skillYaml.contains(skillName))
				{
					skillYaml.getConfig("Skill/JobList.yml");
					if(skillYaml.contains("Mabinogi.Added."+skillName))
					{
					  	YamlLoader playerSkillYaml = new YamlLoader();
						playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
						if(!playerSkillYaml.contains("Mabinogi."+skillYaml.getString("Mabinogi.Added."+skillName)+"."+skillName))
						{
							playerSkillYaml.set("Mabinogi."+skillYaml.getString("Mabinogi.Added."+skillName)+"."+skillName, 1);
							playerSkillYaml.saveConfig();
							if(item.getAmount() != 1)
							{
								item.setAmount(item.getAmount()-1);
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
							}
							else
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
							SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
							player.sendMessage("��d��l[���ο� ��ų�� ȹ�� �Ͽ����ϴ�!] ��e��l"+ChatColor.UNDERLINE+skillName);
						}
						else
						{
							SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage("��c��l[         ����� �̹� �ش� ��ų�� �˰� �ֽ��ϴ�!         ]");
						}
					}
					else
					{
						SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("��c��l[�ش� ��ų�� ��� ī�װ����� �������� �ʽ��ϴ�! �����ڿ��� �����ϼ���!]");
					}
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("��c��l[������ �ش� ��ų�� �������� �ʽ��ϴ�! �����ڿ��� �����ϼ���!]");
				}
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("��c��l[   ���� �ý����� '������'�� ��츸 ��� �����մϴ�!   ]");
			}
		}
		else if(type.equals("�Һ�"))
		{
			int health = 0;
			int mana = 0;
			int food = 0;
			boolean rebirth = false;
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("�����"))
						health = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("����"))
						mana = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("������"))
						food = Integer.parseInt(nowlore.split(" : ")[1]);
				}
				else if(nowlore.contains("ȯ��") && nowlore.contains(" + "))
				{
					rebirth = true;
				}
			}
			usePotion(player, health, mana, food, rebirth);
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
		}
		else if(type.equals("��"))
		{
			int money=Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(1).split(" ")[0]));
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + money <= 2000000000)
			{
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(money, 0, false);
				if(item.getAmount() != 1)
				{
					item.setAmount(item.getAmount()-1);
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
				}
				else
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
				SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
				player.sendMessage("��a[SYSTEM] : ��f��l"+money+" "+MainServerOption.money+"��a �Ա� �Ϸ�!");
				player.sendMessage("��7(���� "+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+ChatColor.stripColor(MainServerOption.money)+" ������)");
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("��c[System] : "+MainServerOption.money+"��c ��(��) 2000000000(20��)�̻� ���� �� �����ϴ�!");
			}
		}
		else if(type.equals("����"))
		{
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			effect.SoundEffect.playSound(player, Sound.BLOCK_ANVIL_HIT, 0.8F, 1.7F);
			item.setAmount(1);
			new ApplyToolGui().applyToolGui(player, item);
		}
	}

	
	public void usePotion(Player player, int health, int mana, int food, boolean rebirth) {
		if(health > 0)
		{
			SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2.0F, 0.8F);
			Damageable damageable = player;
			if(damageable.getMaxHealth() < damageable.getHealth()+health)
				damageable.setHealth(damageable.getMaxHealth());
			else
				damageable.setHealth(damageable.getHealth() + health);
		}
		if(mana >0)
		{
			if(main.MainServerOption.MagicSpellsCatched)
			{
				otherplugins.SpellMain spellMain = new otherplugins.SpellMain();
				spellMain.DrinkManaPotion(player, mana);
				SoundEffect.playSoundLocation(player.getLocation(), Sound.BLOCK_WATER_AMBIENT, 2.0F, 1.9F);
			}
		}
		if(food > 0)
		{
			SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 2.0F, 1.2F);
			if(player.getFoodLevel()+food > 20)
				player.setFoodLevel(20);
			player.setFoodLevel(player.getFoodLevel()+food);
		}
		if(rebirth) {
		  	YamlLoader configYaml = new YamlLoader();
		    configYaml.getConfig("config.yml");
			
			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
			{
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_RealLevel(1);
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Level(1);
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_EXP(0);
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_MaxEXP(100);
				
				SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
				SoundEffect.playSound(player, Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 1.2F);
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
				effect.SendPacket sendPacket = new effect.SendPacket();
				sendPacket.sendTitle(player,"��e�� [ Rebirth ] ��",  "��e[���� �� ����ġ�� �ʱ�ȭ �Ǿ����ϴ�!]", 1, 5, 1);
			}
			else
			{
				player.sendMessage("��c[SYSTEM] : ���� �ý��ۿ� ���� �ʾ� ȯ���� �� �� �����ϴ�!");
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
			}
		}
	}
}