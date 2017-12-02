package fr.tenebrae;

import java.io.File;
import java.io.FilenameFilter;

import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityInsentient;

import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

final public class Listeners implements Listener {

	@EventHandler
	public void onChunkLoad(final ChunkLoadEvent evt) {
		if (!CustomRegistry.chunkHasCustomEntities(evt.getChunk())) return;
		final Chunk c = evt.getChunk();
		final File cDir = new File("entityNBT"+System.getProperty("file.separator")+c.getWorld().getName()+System.getProperty("file.separator")+c.getX()+System.getProperty("file.separator")+c.getZ());

		for (File f : cDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".tbp");
			}
		})) CustomRegistry.loadEntity(f);
	}
	
	@EventHandler
	public void onChunkUnload(final ChunkUnloadEvent evt) {
		final Chunk c = evt.getChunk();
		for (org.bukkit.entity.Entity e : c.getEntities()) {
			Entity nmsEntity = ((CraftEntity)e).getHandle();
			if (nmsEntity instanceof EntityInsentient) if (CustomRegistry.registryEntries.containsKey(EntityTypes.b(nmsEntity))) CustomRegistry.saveEntity((EntityInsentient) nmsEntity);
		}
	}
}
