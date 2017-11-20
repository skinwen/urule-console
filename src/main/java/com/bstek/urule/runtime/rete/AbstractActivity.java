package com.bstek.urule.runtime.rete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractActivity implements Activity {
    private List<Path> paths;

    public List<Path> getPaths() {
        return paths;
    }

    public void addPath(Path path) {
        if (paths == null) {
            paths = new ArrayList<Path>();
        }
        this.paths.add(path);
    }

    protected List<FactTracker> visitPahs(EvaluationContext context, Object obj, FactTracker tracker, Map<String, Object> variableMap) {
        if (paths == null || paths.size() == 0) {
            return null;
        }
        List<FactTracker> trackers = null;
        int size = paths.size();
        for (Path path : paths) {
            Collection<FactTracker> results = null;
            Activity activity = path.getTo();
            path.setPassed(true);
            if (size > 0) {
                Map<String, Object> newVariableMap = new HashMap<String, Object>();
                newVariableMap.putAll(variableMap);
                results = activity.enter(context, obj, tracker.newSubFactTracker(), newVariableMap);
            } else {
                results = activity.enter(context, obj, tracker, variableMap);
            }
            if (results != null) {
                if (trackers == null) {
                    trackers = new ArrayList<FactTracker>();
                }
                trackers.addAll(results);
            }
        }
        return trackers;
    }

    public abstract boolean orNodeIsPassed();

    public abstract void reset();
}
