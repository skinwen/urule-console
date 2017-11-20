package com.bstek.urule.runtime.rete;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.bstek.urule.runtime.WorkingMemory;
import com.bstek.urule.runtime.assertor.AssertorEvaluator;

public class ContextImpl implements Context {
    private ApplicationContext applicationContext;
    private AssertorEvaluator assertorEvaluator;
    private Map<String, String> variableCategoryMap;
    private ValueCompute valueCompute;
    private WorkingMemory workingMemory;
    private JexlEngine jexlEngine;

    public ContextImpl(WorkingMemory workingMemory, ApplicationContext applicationContext, Map<String, String> variableCategoryMap) {
        this.workingMemory = workingMemory;
        this.applicationContext = applicationContext;
        this.assertorEvaluator = (AssertorEvaluator) applicationContext.getBean(AssertorEvaluator.BEAN_ID);
        this.variableCategoryMap = variableCategoryMap;
        this.valueCompute = (ValueCompute) applicationContext.getBean(ValueCompute.BEAN_ID);
        this.jexlEngine = new JexlEngine();
    }

    @Override
    public WorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public AssertorEvaluator getAssertorEvaluator() {
        return assertorEvaluator;
    }

    @Override
    public Object parseExpression(String expression) {
        Expression expr = jexlEngine.createExpression(expression);
        return expr.evaluate(null);
    }

    public String getVariableCategoryClass(String variableCategory) {
        String clazz = variableCategoryMap.get(variableCategory);
        if (StringUtils.isEmpty(clazz)) {
            //throw new RuleException("Variable category ["+variableCategory+"] not exist.");
            clazz = HashMap.class.getName();
        }
        return clazz;
    }

    public ValueCompute getValueCompute() {
        return valueCompute;
    }
}
