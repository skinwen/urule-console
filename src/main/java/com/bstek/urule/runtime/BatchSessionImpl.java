package com.bstek.urule.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.bstek.urule.RuleException;

public class BatchSessionImpl implements BatchSession {
    private ExecutorService executorService;
    private int batchSize;
    private List<Business> businessList = new ArrayList<Business>();
    private KnowledgePackage knowledgePackage;
    private KnowledgePackage[] knowledgePackages;

    public BatchSessionImpl(KnowledgePackage knowledgePackage, int threadSize, int batchSize) {
        this.executorService = Executors.newFixedThreadPool(threadSize);
        this.knowledgePackage = knowledgePackage;
        this.batchSize = batchSize;
    }

    public BatchSessionImpl(KnowledgePackage[] knowledgePackages, int threadSize, int batchSize) {
        this.executorService = Executors.newFixedThreadPool(threadSize);
        this.knowledgePackages = knowledgePackages;
        this.batchSize = batchSize;
    }

    @Override
    public void addBusiness(Business business) {
        if (businessList != null) {
            if (businessList.size() >= batchSize) {
                doBusinesses();
                businessList = new ArrayList<Business>();
            }
        } else {
            businessList = new ArrayList<Business>();
        }
        businessList.add(business);
    }

    private void doBusinesses() {
        BatchThread thread = null;
        if (knowledgePackage != null) {
            thread = new BatchThread(knowledgePackage, businessList);
        } else if (knowledgePackages != null) {
            thread = new BatchThread(knowledgePackages, businessList);
        } else {
            throw new RuleException("KnowledgePackage can not be null.");
        }
        executorService.execute(thread);
        businessList = null;
    }

    @Override
    public void waitForCompletion() {
        if (businessList != null && businessList.size() > 0) {
            doBusinesses();
        }
        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(300, TimeUnit.MILLISECONDS)) {
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            throw new RuleException(ex);
        }
    }
}