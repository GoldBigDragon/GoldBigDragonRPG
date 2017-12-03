package effect;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_12_R1.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;

public class SendPacket
{
	public void sendTitle(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime)
	{
		player.sendTitle(title, subtitle, fadeInTime*20, showTime*20, fadeOutTime*20);
	}

	public void sendTitleAll(String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime)
	{
		Object[] players = Bukkit.getOnlinePlayers().toArray();
		for(int count = 0; count < players.length; count++)
			((Player)players[count]).sendTitle(title, subtitle, fadeInTime*20, showTime*20, fadeOutTime*20);
	}
	
	public void sendActionBar(Player p, String msg, boolean isAllPlayer)
	{
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \""  +msg+  "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,  ChatMessageType.a((byte)2));
		if(isAllPlayer)
			((CraftServer) p.getServer()).getHandle().sendAll(ppoc);
		else
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
	}

	public void switchHotbarSlot(Player p, int slot)
	{
		PacketPlayOutHeldItemSlot ppoc = new PacketPlayOutHeldItemSlot(slot);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
	}

	public void testPacket(Player p)
	{
		/*
			minecraft:chest
			minecraft:crafting_table
			minecraft:furnace
			minecraft:dispenser
			minecraft:enchanting_table
			minecraft:brewing_stand
			minecraft:villager
			minecraft:beacon
			minecraft:anvil
			minecraft:hopper
			minecraft:dropper
			EntityHorse
		 */
		Packet slotChange = new PacketPlayOutOpenWindow(1, "PLAYER",IChatBaseComponent.ChatSerializer.a("인벤토리"), 63, 0);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(slotChange);
	}
	
	public void SpawnHallucinations(Player player, Player Hallucination)
	{
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(((CraftPlayer)Hallucination).getHandle());
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(spawn);
	}
	
	public void playerAnimation(Player player, byte Type)
	{
		/*
		Type
		0 = 오른손 사용 모션
		1 = 한대 맞은 모션
		2 = ?
		3 = 왼손 사용 모션
		4 = 크리티컬 이펙트
		5 = 인챈트 타격 이펙트
		 */
		CraftPlayer cp = ((CraftPlayer)player);
		PacketPlayOutAnimation packet = new PacketPlayOutAnimation(cp.getHandle(), Type);
		cp.getHandle().playerConnection.sendPacket(packet);
	}
	
	public void playerGameStateChange(Player player, byte type1, float type2)
	{
		/*
		type1
		0 = 침대 돌아가기
		1 = ?
		2 = ?
		3 = 게임모드 변경하는 것 처럼 보이게 하기
			type2
			0 = 서바이벌인척
			1 = 크리에이티브인척
			2 = 어드밴처인척
			3 = 스펙터인척
			
		4 = 엔더 지형 이동 관련
			type2
			0 = 무한 지형 불러오는중 뜨게 하기
			0초과부터 2 미만까지 = 엔딩 크레딧
		5 = 가이드 패킷
			type2
			0 체험판 GUI 열기
			101 = WASD로 이동 할 수 있습니다. 마우스를 눌려 뭐시기 설명
			102 = Space로 점프하세요
			103 = E를 통해 인벤토리를 열 수 있습니다.
		7 = 스크린 화면 색상 조절
			type2
			0 world의 일반 모습
			0.2 완전 캄캄
			0.3 더 캄캄
			1 겨자색
			. (type2값에 따라 색 바꿈)
			..
		10 = 엘더 가디언 효과
		 */
		PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(type1, type2);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	

	public void sendCustomPacketOnlinePlayers(Player player, byte value)
	{
		/*
		0은 일반
		1은 불타는 효과
		32는 투명 효과
		64는 Glow효과
		96은 투명 Glow효과
		128은 엘트라 비행 효과
		*/
		
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
		packet.getIntegers().write(0, player.getEntityId());
		WrappedDataWatcher watcher = new WrappedDataWatcher();
		Serializer serializer = Registry.get(Byte.class);
		watcher.setEntity(player);
		watcher.setObject(0, serializer, value);
		packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
		
		Iterator<Player> targetList = (Iterator<Player>) Bukkit.getOnlinePlayers().iterator();
		try
		{	
			while(targetList.hasNext())
				ProtocolLibrary.getProtocolManager().sendServerPacket(targetList.next(), packet);
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	
	public void customPacket(Player player, ArrayList<LivingEntity> target, byte value)
	{
		/*
			0은 일반
			1은 불타는 효과
			32는 투명 효과
			64는 Glow효과
			96은 투명 Glow효과
			128은 엘트라 비행 효과
		 */
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
		for(int count = 0; count < target.size(); count++)
		{
			packet.getIntegers().write(0, target.get(count).getEntityId());
			WrappedDataWatcher watcher = new WrappedDataWatcher();
			Serializer serializer = Registry.get(Byte.class);
			watcher.setEntity(target.get(count));
			watcher.setObject(0, serializer, value);
			packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void blockBreakAnimation(Player player, Location loc, int damage)
	{
		//damage 1 : 아주 조금, 9 : 아주 많이 부서진 효과
		Block b = loc.getBlock();
		PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, new BlockPosition(b.getX(), b.getY(), b.getZ()), damage);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		((CraftServer) player.getServer()).getHandle().sendAll(packet);
	}
}
