package com.bstek.urule.action;

import java.util.List;
import java.util.Map;

import com.bstek.urule.runtime.rete.Context;

public interface Action extends Comparable<Action> {
    ActionValue execute(Context context, Object matchedObject, List<Object> allMatchedObjects, Map<String, Object> variableMap);

    ActionType getActionType();

    int getPriority();
}
