package GoldBigDragon_RPG.Monster.AI;
//https://github.com/sgtcaze/Tutorial/tree/master/Season%203/EP27
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;

public class NMSUtils
{
	public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass)
	{
        try
        {
            /*
            * 먼저, 엔티티 타입 클래스가 있는 모든 필드들(fields)을
            * HashMap리스트에 저장한다.
            * 필자는 reflection을 사용했기 때문에, 나중에 마인크래프트가 필드 이름을 바꿔도 문제가 없다.
            * 맵들에 리스트들을 등록함으로 써, 우리들은 나중에 매우 쉽게 수정할 수 있게 된다.
            */
            List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
            for(Field f : EntityTypes.class.getDeclaredFields())
            {
                if(f.getType().getSimpleName().equals(Map.class.getSimpleName()))
                {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }
 
            /*
            * 마인크래프트가 이 맵들을 체크할 때, 이미 등록된 id가 있다면,
            * 이전의 데이터를 지워 버리고, 우리가 등록한 것을 짚어 넣게 된다.
            */
            
            dataMaps.get(0).remove(name);
            dataMaps.get(2).remove(id);
            
            /*
            * 이제 우리들은 우리가 만든 커스텀 엔티티를 등록 시킬 것이다.
            */
            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;

        try
        {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }
        catch(NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return o;
    }
}