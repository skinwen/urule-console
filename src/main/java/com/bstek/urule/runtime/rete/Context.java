package com.bstek.urule.runtime.rete;

import org.springframework.context.ApplicationContext;

import com.bstek.urule.runtime.WorkingMemory;
import com.bstek.urule.runtime.assertor.AssertorEvaluator;

public interface Context {
    AssertorEvaluator getAssertorEvaluator();

    ValueCompute getValueCompute();

    ApplicationContext getApplicationContext();

    String getVariableCategoryClass(String variableCategory);

    WorkingMemory getWorkingMemory();

    Object parseExpression(String expression);
}
