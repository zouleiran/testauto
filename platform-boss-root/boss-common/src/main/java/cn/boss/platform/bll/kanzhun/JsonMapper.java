package cn.boss.platform.bll.kanzhun;

//import cn.techwolf.common.log.LoggerManager;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.BasicClassIntrospector;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.introspect.VisibilityChecker;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author gongbiao
 *
 */
public class JsonMapper {

    private ObjectMapper mapper;

    public JsonMapper(Inclusion inclusion) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        mapper.getSerializationConfig().setSerializationInclusion(inclusion);

        mapper.getSerializationConfig().disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);

        // 序列化日期格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 设置有属性不能映射成POJO时不报错
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 允许字段名不带引号
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

    }

    public JsonMapper(Inclusion inclusion, boolean isAllowUnquotedControlChars) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        mapper.getSerializationConfig().setSerializationInclusion(inclusion);
        mapper.getSerializationConfig().disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许字段名不带引号
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // allow JSON Strings to contain unquoted control characters
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, isAllowUnquotedControlChars);
        // 序列化日期格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 创建输出全部属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNormalMapper() {
        return new JsonMapper(Inclusion.ALWAYS);
    }

    /**
     * 创建只输出非空属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonNullMapper() {
        return new JsonMapper(Inclusion.NON_NULL);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonDefaultMapper() {
        return new JsonMapper(Inclusion.NON_DEFAULT);
    }

    public static JsonMapper buildAnnotationIgnoreMapper() {
        JsonMapper jsonMapper = JsonMapper.buildNormalMapper();
        jsonMapper.getMapper().configure(SerializationConfig.Feature.USE_ANNOTATIONS, false);
        jsonMapper.getMapper().configure(DeserializationConfig.Feature.USE_ANNOTATIONS, false);
        return jsonMapper;
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需读取集合如List/Map, 且不是List<String>这种简单类型时使用如下语句,使用後面的函數.
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            T t = (T) mapper.readValue(jsonString, clazz);
            return t;
        } catch (IOException e) {
//            LoggerManager.error("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需读取集合如List/Map, 且不是List<String>時,
     * 先用constructParametricType(List.class,MyBean.class)構造出JavaType,再調用本函數.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            T t = (T) mapper.readValue(jsonString, javaType);
            return t;
        } catch (IOException e) {
            // ExceptionLogger.error("parse json string error:" + jsonString,
            // e);
            return null;
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需读取集合如List/Map, 且不是List<String>時, TypeReference 可以 通过 new 方法来解决：比如
     *
     * List<String> 通过 new TypeReference<List<String>>(){} 相对于JavaType 更直观明了些
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, TypeReference<T> type) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            T t = (T) mapper.readValue(jsonString, type);
            return t;
        } catch (IOException e) {
            // ExceptionLogger.error("parse json string error:" + jsonString,
            // e);
            return null;
        }
    }

    /**
     * 構造泛型的Type如List<MyBean>, Map<String,MyBean>
     */
    public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    /**
     * 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            // ExceptionLogger.error("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object, String[] includeFields) {
        try {
            mapper.setSerializationConfig(new SerializationConfig(BasicClassIntrospector.instance, new CustomerIntrospector(includeFields), VisibilityChecker.Std.defaultInstance(), null, null,
                    TypeFactory.defaultInstance(), null));
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            // ExceptionLogger.error("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    @SuppressWarnings("unchecked")
    public <T> T update(T object, String jsonString) {
        try {
            return (T) mapper.updatingReader(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            // ExceptionLogger.error("parse json string" + jsonString +
            // " to object:" + object + " error.", e);
        } catch (IOException e) {
            // ExceptionLogger.error("parse json string" + jsonString +
            // " to object:" + object + " error.", e);
        }
        return null;
    }

    /**
     * 輸出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum, 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
     * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
     */
    public void setEnumUseToString(boolean value) {
        mapper.getSerializationConfig().set(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, value);
        mapper.getDeserializationConfig().set(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, value);
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    public class CustomerIntrospector extends JacksonAnnotationIntrospector {

        private Set<String> includefileds;

        public CustomerIntrospector(String[] f) {
            includefileds = new HashSet<String>();
            for (String field : f) {
                includefileds.add(field);
            }
        }

        @Override
        public String[] findPropertiesToIgnore(AnnotatedClass ac) {
            if (ac == null) {
                return null;
            }

            List<String> list = new ArrayList<String>();

            Iterable<AnnotatedField> fields = ac.fields();
            for (AnnotatedField field : fields) {
                if (!includefileds.contains(field.getName())) {
                    list.add(field.getName());
                }
            }
            return list.toArray(new String[] {});
        }
    }
}
