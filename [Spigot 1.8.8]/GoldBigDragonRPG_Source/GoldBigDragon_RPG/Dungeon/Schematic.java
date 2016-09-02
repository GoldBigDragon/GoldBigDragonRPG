package GoldBigDragon_RPG.Dungeon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.google.common.primitives.UnsignedBytes;

import OtherPlugins.jnbt.ByteArrayTag;
import OtherPlugins.jnbt.CompoundTag;
import OtherPlugins.jnbt.NBTInputStream;
import OtherPlugins.jnbt.ShortTag;
import OtherPlugins.jnbt.StringTag;
import OtherPlugins.jnbt.Tag;

public class Schematic
{
	//세메틱 소스 출처 : https://bukkit.org/threads/pasting-loading-schematics.87129 //
 
    private byte[] blocks;
    private byte[] data;
    private short width;
    private short lenght;
    private short height;
 
    public Schematic()
    {}
    public Schematic(byte[] blocks2, byte[] blockData, short width, short lenght, short height)
    {
        this.blocks = blocks2;
        this.data = blockData;
        this.width = width;
        this.lenght = lenght;
        this.height = height;
    }
 

	//블럭을 반환합니다.//
    public byte[] getBlocks(){return blocks;}
    //블럭 데이터를 반환합니다.//
    public byte[] getData() {return data;}
    //가로 길이를 반환합니다.//
    public short getWidth(){return width;}
    //세로 길이를 반환합니다.//
    public short getLenght(){return lenght;}
    //높이를 반환합니다.//
    public short getHeight(){return height;}

    //선택한 세메틱 파일을 해당 위치에 붙여버립니다.//
	public static void pasteSchematic(Location loc, Schematic schematic)
    {
		byte[] blocks = schematic.getBlocks();
		byte[] blockData = schematic.getData();
 
        short length = schematic.getLenght();
        short width = schematic.getWidth();
        short height = schematic.getHeight();
        
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    int blockid = UnsignedBytes.toInt(blocks[index]);
                    if(blockid <= -1)
                    	blockid = 1;
                    Block block = new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                    block.setTypeIdAndData(blockid, (byte) blockData[index], true);
                }
            }
        }
    }
 
    public static Schematic loadSchematic(File file) throws IOException
    {
    	FileInputStream stream = new FileInputStream(file);
		NBTInputStream nbtStream = new NBTInputStream(stream);
        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        Map<String, Tag> schematic = schematicTag.getValue();
        short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(schematic, "Height", ShortTag.class).getValue();
        String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
        byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
        return new Schematic(blocks, blockData, width, length, height);
    }
 
    /**
    * Get child tag of a NBT structure.
    *
    * @param items The parent tag map
    * @param key The name of the tag to get
    * @param expected The expected type of the tag
    * @return child tag casted to the expected type
    * @throws DataException if the tag does not exist or the tag is not of the
    * expected type
    */
    private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException
    {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }
}