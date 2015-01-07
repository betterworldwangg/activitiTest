package cn.itcast.domain;

import org.activiti.engine.repository.ProcessDefinition;

/**
 * 模板实体 
 * @author zhaoqx
 *
 */
public class Template implements java.io.Serializable{
	private Integer id;
	private String name;
	private String key;//当前模板对应的流程定义的key，用于启动一个流程实例
	private String filePath;//模板对应的doc文件的存储路径，用于文件下载
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
