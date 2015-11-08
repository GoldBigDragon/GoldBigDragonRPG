package OtherPlugins;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class NoteBlockAPIMain implements Listener
{
	static List<Song> Musics = new ArrayList<Song>();
	HashMap<Player,SongPlayer> currentStations = new HashMap<Player,SongPlayer>();
	HashMap<Player,SongPlayer> joinedPlayer = new HashMap<Player,SongPlayer>();
	int maxStations = 1000;
	static boolean MusicAdded =false;
  	public File MusicFolder = new File("plugins/GoldBigDragonRPG/NoteBlockSound/");

	public NoteBlockAPIMain(Main plugin)
	{
		NoteBlockAPIAddMusic();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		Main.NoteBlockAPIAble = true;
	}
	
	public NoteBlockAPIMain()
	{}
	
	public void NoteBlockAPIAddMusic()
	{
		if(!MusicFolder.exists())
			MusicFolder.mkdirs();
		else
			if(Musics.size() ==0&&MusicAdded==false)
			{
				if(MusicFolder.listFiles().length >= 0)
				{
					for (File child : MusicFolder.listFiles())
					  Musics.add(new Song(NBSDecoder.parse(child)));
				}
				else
					MusicAdded = false;
			}
	}

	public boolean SoundList(Player player, boolean isGUIclicked)
	{
		if(MusicAdded==false)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
			if(isGUIclicked == false)
				return true;
		}
		if(Musics.size()==0)
		{
			player.sendMessage(ChatColor.RED+"[BGM] : 재생 가능한 nbs파일이 없습니다! nbs 파일을 아래 경로에 넣어 주세요.");
			player.sendMessage(ChatColor.RED+"[자신의 버킷 폴더] -> [plugins] -> [GoldBigDragonRPG] -> [NoteBlockSound]");
			return false;
		}
		else
		{
			for(int count = 0; count <Musics.size();count++)
			{
				player.sendMessage(ChatColor.BLUE+"[트랙 번호 " +ChatColor.WHITE+count+ChatColor.BLUE+"] [제목 "+ChatColor.WHITE+ Musics.get(count).getTitle()+ChatColor.BLUE+"]");
			}
			player.sendMessage(ChatColor.BLUE+"[BGM] : 현재 등록된 사운드 수 : "+Musics.size() +"개");
			if(isGUIclicked == true)
				player.sendMessage(ChatColor.DARK_AQUA+"[영역] : 배경음으로 사용하실 트랙 번호를 입력하세요!");
			return true;
		}
	}
	
	public String getTitle(int MusicNumber)
	{
		if(MusicAdded==false||Musics.size()==0)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
			return "[음반 없음]";
		}
		if(Musics.size()>=1)
		{
			if(Musics.get(MusicNumber).getTitle() == null||Musics.get(MusicNumber).getTitle().length()<=0)
				return "[제목 없음]";
			else
				return Musics.get(MusicNumber).getTitle();
		}
		else
			return "[음반 없음]";
	}

	public String getAuthor(int MusicNumber)
	{
		if(MusicAdded==false||Musics.size()==0)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
			return "[음반 없음]";
		}
		if(Musics.size()>=1)
		{
			if(Musics.get(MusicNumber).getAuthor() == null||Musics.get(MusicNumber).getAuthor().length()<=0)
				return "[작자 미상]";
			else
				return Musics.get(MusicNumber).getAuthor();
		}
		else
			return "[음반 없음]";
	}

	public String getDescription(int MusicNumber)
	{
		if(MusicAdded==false||Musics.size()==0)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
			return "[음반 없음]";
		}
		if(Musics.size()>=1)
		{
			if(Musics.get(MusicNumber).getDescription() == null||Musics.get(MusicNumber).getDescription().length()<=0)
				return "[설명 없음]";
			else
				return Musics.get(MusicNumber).getDescription();
		}
		else
			return "[음반 없음]";
	}
	
	public int SoundSize()
	{
		if(MusicAdded==false||Musics.size()==0)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
		}
		return Musics.size();
	}
	
	public void Play(Player player,int number)
	{
		if(number < Musics.size())
		{
			Stop(player);
			SongPlayer sp = new RadioSongPlayer(Musics.get(number));
			
			sp.addPlayer(player);
			sp.setPlaying(true);
			sp.setAutoDestroy(true);
			currentStations.put(player, sp);
		}
		else
		{
			if(MusicAdded==false)
			{
				MusicAdded = true;
				NoteBlockAPIAddMusic();
				Play(player, number);
				return;
			}
			player.sendMessage(ChatColor.RED+"[BGM] : "+number+"번째 사운드 트랙을 찾을 수 없습니다! 관리자에게 문의하세요!");
		}
	}
    
	public void Stop(Player player)
	{
		currentStations.remove(player);
		joinedPlayer.remove(player);
		com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain.stopPlaying(player);
	}
    
	@EventHandler
	public void SongEndEvent(SongEndEvent event)
	{
		event.getSongPlayer().setPlaying(false);

		Player player = null;
		for(int count = 0; count < event.getSongPlayer().getPlayerList().size(); count++)
		{
			player = Bukkit.getPlayer(event.getSongPlayer().getPlayerList().get(count));
			if(Main.PlayerCurrentArea.get(player) != "null")
			{
				GBD.GoldBigDragon_Advanced.ETC.Area A = new GBD.GoldBigDragon_Advanced.ETC.Area();
				String Area;
				Area = A.getAreaName(player);
				if(Area != null)
				{
					if(Main.PlayerCurrentArea.get(player).equals(Area)==true)
					{
						if(A.getAreaOption(Area, (char) 6) == true)
						{
							if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
							{
								YamlController Location_YC = GBD.GoldBigDragon_Advanced.Main.Main.Location_YC;
								YamlManager AreaList = Location_YC.getNewConfig("Area/AreaList.yml");
								Play(player, AreaList.getInt(Area+".BGM"));
							}
						}
					}
				}
			}
		}
	}
	    
}
