package org.dromara.common.translation.core.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.reflect.ReflectUtils;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.core.TranslationInterface;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 翻译处理器
 *
 * @author Lion Li
 */
@Slf4j
public class TranslationHandler extends JsonSerializer<Object> implements ContextualSerializer {

    /**
     * 全局翻译实现类映射器
     */
    public static final Map<String, TranslationInterface<?>> TRANSLATION_MAPPER = new ConcurrentHashMap<>();

    private Translation translation;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        TranslationInterface<?> trans = TRANSLATION_MAPPER.get(translation.type());
        if (ObjectUtil.isNotNull(trans)) {
            // 如果映射字段不为空 则取映射字段的值
            List<String> filterMappers = filterMappers();
            if (CollUtil.isNotEmpty(filterMappers)) {
                // 适配原有的单值映射
                if (filterMappers.size() == 1) {
                    value = ReflectUtils.invokeGetter(gen.getCurrentValue(), CollUtil.getFirst(filterMappers));
                } else {
                    // 多值映射
                    Dict values = Dict.create();
                    for (String mapper : filterMappers) {
                        values.put(mapper, ReflectUtils.invokeGetter(gen.getCurrentValue(), mapper));
                    }
                    value = values;
                }
            }

            // 如果为 null 直接写出
            if (ObjectUtil.isNull(value)) {
                gen.writeNull();
                return;
            }
            Object result = trans.translation(value, translation.other());
            gen.writeObject(result);
        } else {
            gen.writeObject(value);
        }
    }

    /**
     * 处理映射字段列表
     */
    private List<String> filterMappers() {
        return CollUtil.newHashSet(translation.mapper())
            .stream()
            .filter(StringUtils::isNotBlank)
            .toList();
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Translation translation = property.getAnnotation(Translation.class);
        if (Objects.nonNull(translation)) {
            this.translation = translation;
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
