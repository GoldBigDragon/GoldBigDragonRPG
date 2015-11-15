package GBD.GoldBigDragon_Advanced.ETC;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class Job
{	
	public void AllPlayerFixAllSkillAndJobYML()
	{
    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
    	Player[] players = new Player[playerlist.size()];
    	playerlist.toArray(players);
		FixJobList();
		for(int count = 0; count < players.length;count++)
		{
			FixPlayerJobList(players[count]);
			FixPlayerSkillList(players[count]);
		}
		return;
	}

	public void AllPlayerSkillRankFix()
	{
    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
    	Player[] players = new Player[playerlist.size()];
    	playerlist.toArray(players);
		FixJobList();
		for(int count = 0; count < players.length;count++)
			SkillRankFix(players[count]);
		return;
	}
	
	public void FixJobList()
	//직업 스킬에서 스킬 목록에 등록되지 않은 스킬을 삭제 해 주는 메소드
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager JobList  = Config_YC.getNewConfig("Skill/JobList.yml");
		
		YamlManager Config  = Config_YC.getNewConfig("config.yml");

		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			Object[] Categori = JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			for(int counter = 0; counter < Categori.length; counter++)
			{
				if(Categori[counter].equals("Added") == false)
				{
					Object[] Skills = JobList.getConfigurationSection("Mabinogi."+Categori[counter].toString()).getKeys(false).toArray();
					for(int countta = 0; countta < Skills.length; countta++)
					{
						YamlManager SkillList  = Config_YC.getNewConfig("Skill/SkillList.yml");
						if(SkillList.getConfigurationSection("").getKeys(false).contains(Skills[countta].toString())==false)
						{
							JobList.removeKey("Mabinogi."+Categori[counter].toString()+"."+Skills[countta].toString());
						}
					}
				}
			}
			JobList.saveConfig();
		}
		else
		{
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(int counter = 0; counter < Job.length; counter++)
			{
				Object[] SubJob = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).toArray();
				for(int count = 0; count < SubJob.length; count++)
				{
					Object[] SubJobSkills = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()+"."+SubJob[count]+".Skill").getKeys(false).toArray();
					for(int countta = 0; countta < SubJobSkills.length; countta++)
					{
						YamlManager SkillList  = Config_YC.getNewConfig("Skill/SkillList.yml");
						if(SkillList.getConfigurationSection("").getKeys(false).contains(SubJobSkills[countta].toString())==false)
						{
							JobList.removeKey("MapleStory."+Job[counter].toString()+"."+SubJob[count].toString()+".Skill."+SubJobSkills[countta].toString());
							JobList.saveConfig();
						}
					}
				}
			}
		}
	}
	
	public void FixPlayerJobList(Player player)
	//직업 중에서 직업 목록에 등록되지 않은 직업을 가진 플레이어를 변경 해 주는 메소드
	//마비노기 버전에서는 삭제된 카테고리를 플레이어 스킬 목록에서 제거해 주며, 새로 나온 카테고리를 플레이어에게 등록해 준다.
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager Config  = Config_YC.getNewConfig("config.yml");
		YamlManager JobList  = Config_YC.getNewConfig("Skill/JobList.yml");

	  	if(Config_YC.isExit("Skill/PlayerData/"+player.getUniqueId().toString()+".yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.SkillConfig().CreateNewPlayerSkill(player);
	  	
		YamlManager PlayerList  = Config_YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			Object[] Categori = JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			Object[] PlayerCategori = PlayerList.getConfigurationSection("Mabinogi").getKeys(false).toArray();

			String CategoriContents=" , ";
			String PlayerCategoriContents=" , ";
			
			for(int a = 0; a < Categori.length; a++)
				CategoriContents = CategoriContents + Categori[a].toString() + " , ";
			for(int a = 0; a < PlayerCategori.length; a++)
				PlayerCategoriContents = PlayerCategoriContents + PlayerCategori[a].toString() + " , ";
			
			for(int count = 0; count < PlayerCategori.length; count++)
			{
				if(CategoriContents.contains(PlayerCategori[count].toString()) == false)
				{
					PlayerList.removeKey("Mabinogi."+PlayerCategori[count].toString());
				}
			}
			for(int count = 0; count < Categori.length; count++)
			{
				if(Categori[count].equals("Added") == false)
				{
					if(PlayerCategoriContents.contains(Categori[count].toString()) == false)
					{
						PlayerList.set("Mabinogi."+Categori[count].toString()+".1",null);
					}
				}
			}
			PlayerList.saveConfig();
		}
		else
		{
			boolean isExit = false;
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(int counter = 0; counter < Job.length; counter++)
			{
				Object[] SubJob = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).toArray();
				for(int count = 0; count < SubJob.length; count++)
				{
					if(SubJob[count].toString().equalsIgnoreCase(PlayerList.getString("Job.Type")))
					{
						return;
					}
				}
			}
			if(isExit == false)
			{
				PlayerList.removeKey("MapleStory."+PlayerList.getString("Job.Type"));
				PlayerList.set("Job.Type", Config.getString("Server.DefaultJob"));
				PlayerList.set("MapleStory."+Config.getString("Server.DefaultJob")+".Skill.(%@$#",null);
				Object[] Skills = JobList.getConfigurationSection("MapleStory."+Config.getString("Server.DefaultJob")+"."+Config.getString("Server.DefaultJob")+".Skill").getKeys(false).toArray();
				for(int count = 0; count < Skills.length;count++)
				{
					if(PlayerList.contains("MapleStory."+Config.getString("Server.DefaultJob")+".Skill."+Skills[count].toString())==false)
						PlayerList.set("MapleStory."+Config.getString("Server.DefaultJob")+".Skill."+Skills[count].toString(),1);
				}
				PlayerList.saveConfig();
			}
		}
	}
	
	public void FixPlayerSkillList(Player player)
	//플레이어 스킬에서 직업 스킬 목록에 없는 스킬을 삭제 해 주고, 플레이어가 없는 스킬을 추가시켜 주는 메소드
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager Config  = Config_YC.getNewConfig("config.yml");
		YamlManager JobList  = Config_YC.getNewConfig("Skill/JobList.yml");
		YamlManager PlayerList  = Config_YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");

		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			//직업 스킬과 플레이어 스킬을 비교하여 직업에 없는 스킬 삭제
			//직업 스킬과 플레이어 스킬을 비교하여 플레이어에게 없는 스킬 등록
			Object[] CategoriList = JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			//마비노기 카테고리에 등록된 모든 스킬을 문자열로 나열한 뒤, 플레이어가 가진 스킬을 대입하여
			//만일 카테고리에는 없지만 플레이어에게 스킬이 있다면 삭제해 주는 구문.
			for(int count = 0; count < CategoriList.length; count ++)
			{
				if(CategoriList[count].toString().equals("Added") == false)
				{
					Object[] JSkillList = JobList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray();
					Object[] PlayerSkillList = PlayerList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray();
					StringBuffer SkillList = new StringBuffer();
					for(int a = 0; a < JSkillList.length; a++)
						SkillList.append(JSkillList[a].toString());
					for(int countta = 0; countta < PlayerSkillList.length; countta++)
					{
						if(SkillList.indexOf(PlayerSkillList[countta].toString())==-1)
						{
							PlayerList.removeKey("Mabinogi."+CategoriList[count]+"."+PlayerSkillList[countta].toString());
						}
					}
				}
			}
			
			//히든 스킬 외의 일반 스킬들을 추려내어, 일반 스킬이 없는 플레이어에게
			//스킬을 전수해 주는 구문.
			for(int count = 0; count < CategoriList.length; count ++)
			{
				if(CategoriList[count].equals("Added") == false)
				{
					Object[] JSkillList = JobList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray();
					for(int countta = 0; countta < JSkillList.length; countta++)
					{
						if(JobList.getBoolean("Mabinogi."+CategoriList[count] + "."+JSkillList[countta].toString()) == true)
						{
							if(PlayerList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray().toString().contains(JSkillList[countta].toString())==false)
							{
								PlayerList.set("Mabinogi."+CategoriList[count]+"."+JSkillList[countta].toString(), 1);
							}
						}
					}
				}
			}
			PlayerList.saveConfig();
		}
		else
		{
			PlayerList.set("MapleStory."+PlayerList.getString("Job.Type")+".Skill.*&((%", null);
			PlayerList.saveConfig();
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(int counter = 0; counter < Job.length; counter++)
			{
				Object[] SubJob = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).toArray();
				for(int count = 0; count < SubJob.length; count++)
				{
					Object[] SubJobSkills = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()+"."+SubJob[count]+".Skill").getKeys(false).toArray();
					for(int countta = 0; countta < SubJobSkills.length; countta++)
					{
						if(PlayerList.contains("MapleStory."+SubJob[count])==true)
						{
							PlayerList.set("MapleStory."+SubJob[count]+".Skill.^#$@%", null);
							PlayerList.saveConfig();
							if(PlayerList.getConfigurationSection("MapleStory."+SubJob[count]+".Skill").getKeys(false).size() == 0)
							{
								PlayerList.set("MapleStory."+SubJob[count]+".Skill."+SubJobSkills[countta].toString(),1);
								PlayerList.saveConfig();
							}
							else
							{
								if(PlayerList.getConfigurationSection("MapleStory."+SubJob[count]+".Skill").getKeys(false).contains(SubJobSkills[countta].toString())==false)
								{
									PlayerList.set("MapleStory."+SubJob[count]+".Skill."+SubJobSkills[countta].toString(),1);
									PlayerList.saveConfig();
								}
							}
						}
					}
				}
			}
			Object[] PlayerJob = PlayerList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			Object[] Jobs = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(int counter = 0; counter < Jobs.length; counter++)
			{
				for(int count = 0; count < PlayerJob.length; count++)
				{
					Object[] SubJobs = JobList.getConfigurationSection("MapleStory."+Jobs[counter]).getKeys(false).toArray();
					if(PlayerList.contains("MapleStory."+PlayerJob[count].toString())==true)
					{
						PlayerList.set("MapleStory."+PlayerJob[count].toString()+".Skill.^#$@%", null);
						PlayerList.saveConfig();
						Object[] PlayerJobSkills = PlayerList.getConfigurationSection("MapleStory."+PlayerJob[count].toString()+".Skill").getKeys(false).toArray();
						for(int countta = 0; countta < SubJobs.length; countta++)
						{
							if(SubJobs[countta].equals(PlayerJob[count].toString()))
							{
								for(int countia = 0; countia < PlayerJobSkills.length; countia++)
								{
									if(JobList.getConfigurationSection("MapleStory."+Jobs[counter]+"."+SubJobs[countta]+".Skill").getKeys(false).contains(PlayerJobSkills[countia].toString())==false)
									{
										PlayerList.removeKey("MapleStory."+PlayerJob[count].toString()+".Skill."+PlayerJobSkills[countia].toString());
										PlayerList.saveConfig();
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void SkillRankFix(Player player)
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager Config  = Config_YC.getNewConfig("config.yml");
		YamlManager SkillList  = Config_YC.getNewConfig("Skill/SkillList.yml");
		YamlManager PlayerList  = Config_YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");

		//스킬 최대 레벨보다 레벨이 높은 스킬을 최대 레벨치로 수정해 주는 구문
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			Object[] CategoriList = PlayerList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			PlayerList.saveConfig();
			for(int count = 0; count < CategoriList.length; count ++)
			{
				Object[] PlayerSkills = PlayerList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray();
				for(int countta = 0; countta < PlayerSkills.length; countta++)
				{
					int SkillMaxRank = SkillList.getConfigurationSection(PlayerSkills[countta]+".SkillRank").getKeys(false).size();
					if(PlayerList.getInt("Mabinogi."+CategoriList[count]+"."+PlayerSkills[countta]) >  SkillMaxRank)
					{
						PlayerList.set("Mabinogi."+CategoriList[count]+"."+PlayerSkills[countta].toString(), SkillMaxRank);
					}
				}
			}
		}
		else
		{
			if(PlayerList.contains("MapleStory"))
			{
				Object[] PlayerJob = PlayerList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(int counter = 0; counter < PlayerJob.length; counter++)
				{
					Object[] PlayerSkills = PlayerList.getConfigurationSection("MapleStory."+PlayerJob[counter]+".Skill").getKeys(false).toArray();
					for(int countta = 0; countta < PlayerSkills.length;countta++)
					{
						int SkillMaxRank = SkillList.getConfigurationSection(PlayerSkills[countta]+".SkillRank").getKeys(false).size();
						if(PlayerList.getInt("MapleStory."+PlayerJob[counter]+".Skill."+PlayerSkills[countta]) >  SkillMaxRank)
						{
							PlayerList.set("MapleStory."+PlayerJob[counter]+".Skill."+PlayerSkills[countta], SkillMaxRank);
						}
					}
				}
			}
		}
		PlayerList.saveConfig();
	}
}
