package com.gxuwz.medical.domain.menu;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 定义与zTree的节点对象有相同属性的节点类
 * @ClassName: MenuTreeNode
 * @author SunYi
 * @Date 2019年4月3日下午3:48:32
 */
public class MenuTreeNode {
	
	// 根节点编号
	private String id;
	// 上一级节点编号
	private String pid;
	// 节点名称
	private String name;
	// 子节点集合
	@JSONField(serialize=false)
	private List<MenuTreeNode> children;
	// 是否选中(默认false)
	private boolean checked;
	// 是否展开(默认false)
	private boolean open;
	// 节点级数
    private int level;
    // 方法路径
    private String url;
    
    
    
    public MenuTreeNode(){
    	
    }
    public MenuTreeNode(Menu menu){
		this.id =menu.getMenuid();
		this.name =menu.getMenuname();
		this.pid =menu.getMenupid();
		this.open=true;
		this.level =menu.getLevel();
		this.url =menu.getUrl();
		
	}
	public MenuTreeNode(String id, String name, List<MenuTreeNode> children) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MenuTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<MenuTreeNode> children) {
		this.children = children;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
