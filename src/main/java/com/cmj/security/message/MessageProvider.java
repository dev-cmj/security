package com.cmj.security.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProvider {
    private final MessageSource messageSource;

    public String getMessage(String messageKey)    {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
    public String getMessage(String messageKey, Object[] args)    {
        return messageSource.getMessage(messageKey, args, messageKey, LocaleContextHolder.getLocale());
    }
}
