package com.bstek.urule.runtime.rete;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.bstek.urule.runtime.WorkingMemory;

public class EvaluationContextImpl extends ContextImpl implements EvaluationContext {
    private Activity prevActivity;

    public EvaluationContextImpl(WorkingMemory workingMemory, ApplicationContext applicationContext, Map<String, String> variableCategoryMap) {
        super(workingMemory, applicationContext, variableCategoryMap);
    }

    @Override
    public Activity getPrevActivity() {
        return prevActivity;
    }

    @Override
    public void setPrevActivity(Activity activity) {
        this.prevActivity = activity;
    }
}
