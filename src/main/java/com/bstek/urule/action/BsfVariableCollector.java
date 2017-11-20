package com.bstek.urule.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.urule.runtime.rete.Context;

public class BsfVariableCollector implements ApplicationContextAware {
    public static final String BEAN_ID = "urule.bsfVariableCollector";
    private Collection<BsfVariableProvider> providers;
    private ApplicationContext applicationContext;

    public Map<String, Object> getVariableMap(Context context) {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("workingMemory", context.getWorkingMemory());
        variableMap.put("applicationContext", applicationContext);
        for (BsfVariableProvider provider : providers) {
            Map<String, Object> map = provider.provide();
            if (map == null) {
                continue;
            }
            variableMap.putAll(map);
        }
        return variableMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        providers = applicationContext.getBeansOfType(BsfVariableProvider.class).values();
        this.applicationContext = applicationContext;
    }
}
