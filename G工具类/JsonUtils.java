import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: R.O.S.E框架的JSON操作工具类
 * @author: tiger
 * @create: 2020-04-14 22:27
 */
public class JsonUtils {
    public static ObjectMapper mapper;

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static class Test {
        public Date now = new Date();
    }

    static {
        mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 实体 --> json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json --> 实体
     *
     * @param json  json字符串
     * @param clazz 实体类 T
     * @param <T>   泛型
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T fromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(json, clazz);
    }

    /**
     * json --> List<T>
     * 异常往上抛出
     *
     * @param json  json字符串
     * @param clazz 实体类 T
     * @param <T>   泛型
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> List<T> toList(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    /**
     * json --> List<T>
     * 已经抛出异常
     *
     * @param json  json字符串
     * @param clazz 实体类 T
     * @param <T>   泛型
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> List<T> toListHasCatch(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json --> Map<K, V>
     *
     * @param json       json字符串
     * @param keyClazz   key K
     * @param valueClazz value V
     * @param <K>
     * @param <V>
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClazz, Class<V> valueClazz)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(json, mapper.getTypeFactory().constructMapType(HashMap.class, keyClazz, valueClazz));
    }

    /**
	 *
     * @param json json字符串
     * @param typeReference
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object fromJson(String json, TypeReference<?> typeReference)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(json, typeReference);
    }

    public static Object fromJson(String json, JavaType javaType) {
        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            logger.error("JSON反序列化【{}】为【{}】时出错", json, javaType);
            throw new RuntimeException("JSON反序列化出错");
        }
    }

    public static String jsonArrayToString(String json) {
        return json.replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "").replaceAll("\\{", "").replaceAll("}",
                "");
    }

    public static String jsonArrayToStringTwo(String json) {
        return json.replaceAll("\"", "");
    }
}
