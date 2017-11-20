package com.bstek.urule.console.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.tika.io.IOUtils;

import com.bstek.urule.RuleException;
import com.bstek.urule.console.repository.model.FileType;
import com.bstek.urule.console.repository.updater.ReferenceUpdater;

public class RepositoryRefactor {
	private RepositoryService repositoryService;
	private Collection<ReferenceUpdater> updaters;
	public RepositoryRefactor(RepositoryService repositoryService,Collection<ReferenceUpdater> updaters) {
		this.repositoryService=repositoryService;
		this.updaters=updaters;
	}
	public void rename(Node rootNode,String path,String newpath,String createUser){
		List<String> referenceFiles=getFiles(rootNode,path);
		for(String nodePath:referenceFiles){
			for(ReferenceUpdater updater:updaters){
				if(updater.support(nodePath)){
					InputStream inputStream=repositoryService.readFile(newpath,null);
					try {
						String content = IOUtils.toString(inputStream);
						inputStream.close();
						String newContent=updater.update(path, path, content);
						if(newContent!=null){
							repositoryService.saveFile(newpath,createUser,newContent, false,null);
						}
					} catch (IOException e) {
						throw new RuleException(e);
					}
				}
			}
		}
	}
	
	public List<String> getReferenceFiles(Node rootNode,String path){
		List<String> referenceFiles=new ArrayList<String>();
		List<String> files=getFiles(rootNode, path);
		for(String nodePath:files){
			InputStream inputStream=repositoryService.readFile(nodePath,null);
			try {
				String content = IOUtils.toString(inputStream);
				inputStream.close();
				boolean contain=content.contains(path);
				if(contain){
					referenceFiles.add(nodePath);
				}
			} catch (IOException e) {
				throw new RuleException(e);
			}
		}
		return referenceFiles;
	}
		
	public List<String> getFiles(Node rootNode,String path){
		String project=getProject(path);
		try{
			List<String> list=new ArrayList<String>();
			Node projectNode=rootNode.getNode(project);		
			buildPath(list, projectNode);
			return list;
		}catch(Exception ex){
			throw new RuleException(ex);
		}
	}
	private void buildPath(List<String> list, Node parentNode) throws RepositoryException {
		NodeIterator nodeIterator=parentNode.getNodes();
		while(nodeIterator.hasNext()){
			Node node=nodeIterator.nextNode();
			String nodePath=node.getPath();
			if(nodePath.endsWith(FileType.Ruleset.toString())){
				list.add(nodePath);
			}else if(nodePath.endsWith(FileType.UL.toString())){
				list.add(nodePath);
			}else if(nodePath.endsWith(FileType.DecisionTable.toString())){
				list.add(nodePath);
			}else if(nodePath.endsWith(FileType.ScriptDecisionTable.toString())){
				list.add(nodePath);
			}else if(nodePath.endsWith(FileType.DecisionTree.toString())){
				list.add(nodePath);					
			}else if(nodePath.endsWith(FileType.RuleFlow.toString())){
				list.add(nodePath);					
			}
			buildPath(list,node);
		}
	}
	
	private String getProject(String path){
		if(path.startsWith("/")){
			path=path.substring(1);
		}
		int pos=path.indexOf("/");
		return path.substring(0,pos);
	}
}
