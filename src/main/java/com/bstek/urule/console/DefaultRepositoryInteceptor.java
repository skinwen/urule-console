package com.bstek.urule.console;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRepositoryInteceptor implements RepositoryInteceptor {
    private static final Logger logger  = LoggerFactory.getLogger(DefaultRepositoryInteceptor.class);

    @Override
    public void readFile(String file) {

    }

    @Override
    public void saveFile(String file, String content) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createFile(String file, String content) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteFile(String file) {
        // TODO Auto-generated method stub

    }

    @Override
    public void renameFile(String oldFileName, String newFileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createDir(String dir) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createProject(String project) {
        // TODO Auto-generated method stub

    }
}