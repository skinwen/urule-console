package com.bstek.urule.runtime.rete;

import java.util.Collection;
import java.util.Map;

public interface Instance {
    Collection<FactTracker> enter(EvaluationContext context, Object obj, FactTracker tracker, Map<String, Object> variableMap);
}
