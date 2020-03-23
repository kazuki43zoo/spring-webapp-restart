package com.example;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ConfigurableWebEnvironment;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import java.util.Optional;

public class ParentConfigurableContextLoaderListener extends ContextLoaderListener {
    @Override
    protected ApplicationContext loadParentContext(ServletContext servletContext) {
        ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(determineContextClass(servletContext));
        Optional.ofNullable(servletContext.getInitParameter("parentContextConfigLocation")).ifPresent(wac::setConfigLocation);
        if (wac.getEnvironment() instanceof ConfigurableWebEnvironment) {
            ((ConfigurableWebEnvironment) wac.getEnvironment()).initPropertySources(servletContext, null);
        }
        customizeContext(servletContext, wac);
        wac.refresh();
        return wac;
    }
}
