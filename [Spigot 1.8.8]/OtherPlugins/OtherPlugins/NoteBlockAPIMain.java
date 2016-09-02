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
import org.bukkit.plugin.java.JavaPlugin;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public class NoteBlockAPIMain implements Listener
{
	public static List<Song> Musics = new ArrayList<Song>();
	HashMap<Player,SongPlayer> currentStations = new HashMap<Player,SongPlayer>();
	HashMap<Player,SongPlayer> joinedPlayer = new HashMap<Player,SongPlayer>();
	short maxStations = 1000;
	static boolean MusicAdded =false;
  	public File MusicFolder = new File("plugins/GoldBigDragonRPG/NoteBlockSound/");

	public NoteBlockAPIMain(JavaPlugin plugin)
	{
		NoteBlockAPIAddMusic();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		GoldBigDragon_RPG.Main.ServerOption.NoteBlockAPIAble = true;
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
					{
						if(child.getName().contains("nbs"))
						Musics.add(new Song(NBSDecoder.parse(child)));
					}
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
			return true;
	}
	
	public String getTitle(int MusicNumber)
	{
		if(MusicAdded==false||Musics.size()==0||Musics.size() < MusicNumber)
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
		if(MusicAdded==false||Musics.size()==0||Musics.size() < MusicNumber)
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
		if(MusicAdded==false||Musics.size()==0||Musics.size() < MusicNumber)
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

	public void SongPlay(Player player,Song song)
	{
		Stop(player);
		SongPlayer sp = new RadioSongPlayer(song);
		
		sp.addPlayer(player);
		sp.setPlaying(true);
		sp.setAutoDestroy(true);
		currentStations.put(player, sp);
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
			if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
				SongPlay(player, event.getSongPlayer().getSong());
			else if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_CurrentArea() != "null")
				SongPlay(player, event.getSongPlayer().getSong());
		}
	}
	    
}
