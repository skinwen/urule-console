package com.bstek.urule.runtime;

import java.util.List;

public class BatchThread implements Runnable {
    private List<Business> businesses;
    private KnowledgeSession session;

    public BatchThread(KnowledgePackage knowledgePackage, List<Business> businesses) {
        session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
        this.businesses = businesses;
    }

    public BatchThread(KnowledgePackage[] knowledgePackages, List<Business> businesses) {
        session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackages);
        this.businesses = businesses;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        String oldThreadName = thread.getName();
        thread.setName("urule-" + oldThreadName);
        try {
            int size = businesses.size();
            for (int i = 0; i < size; i++) {
                Business business = businesses.get(i);
                business.execute(session);
            }
        } finally {
            thread.setName(oldThreadName);
        }
    }
}
