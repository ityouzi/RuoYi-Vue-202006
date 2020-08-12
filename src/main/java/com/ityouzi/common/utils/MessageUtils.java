package com.ityouzi.common.utils;

import com.ityouzi.common.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 09:01
 * @Description: 获取i18n 资源文件
 */
public class MessageUtils {
    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args){
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        String message = messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        return message;
    }

}
