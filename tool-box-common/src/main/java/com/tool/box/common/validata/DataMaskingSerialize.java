package com.tool.box.common.validata;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.tool.box.enums.DataMaskingTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * 自定义序列化类
 *
 * @Author v_haimiyang
 * @Date 2024/3/13 10:58
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class DataMaskingSerialize extends JsonSerializer<String> implements ContextualSerializer {

    private DataMaskingTypeEnum type;

    private Integer start;

    private Integer end;

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        switch (type) {
            //userId
            case USER_ID:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.USER_ID));
                break;
            //中文名
            case CHINESE_NAME:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.CHINESE_NAME));
                break;
            //身份证号
            case ID_CARD:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.ID_CARD));
                break;
            //座机
            case FIXED_PHONE:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.FIXED_PHONE));
                break;
            //手机号
            case MOBILE_PHONE:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.MOBILE_PHONE));
                break;
            //地址
            case ADDRESS:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.ADDRESS));
                break;
            //邮箱
            case EMAIL:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.EMAIL));
                break;
            case BANK_CARD:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.BANK_CARD));
                break;
            //密码
            case PASSWORD:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.PASSWORD));
                break;
            //中国大陆车牌号
            case CAR_LICENSE:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.CAR_LICENSE));
                break;
            //自定义
            case CUSTOM:
                jsonGenerator.writeString(CharSequenceUtil.hide(value, start, end));
                break;
            default:
                break;
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty)
            throws JsonMappingException {
        if (Objects.nonNull(beanProperty)) {
            //判断是否为string类型
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                DataMasking anno = beanProperty.getAnnotation(DataMasking.class);
                if (Objects.isNull(anno)) {
                    anno = beanProperty.getContextAnnotation(DataMasking.class);
                }
                if (Objects.nonNull(anno)) {
                    return new DataMaskingSerialize(anno.type(), anno.start(), anno.end());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
