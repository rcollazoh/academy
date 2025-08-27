package cu.academy.shared.configs.text_messages;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {

    private static ResourceBundleMessageSource messageSource;

    public Translator(@Qualifier("textsResourceBundleMessageSource") ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String code) {
//        Locale locale = LocaleContextHolder.getLocale(); // Para usar el que tiene por defecto el navegador
        messageSource.addBasenames("classpath:resources");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource.getMessage(code, null, new Locale("es")); // usaremos siempre el español
    }
}
