package com.bstek.urule.runtime.rete;

public interface EvaluationContext extends Context {
    Activity getPrevActivity();

    void setPrevActivity(Activity activity);
}
