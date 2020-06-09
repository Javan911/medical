package com.gxuwz.medical.domain.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限菜单树组件
 * @ClassName: MenuTree
 * @author SunYi
 * @Date 2019年4月3日下午3:14:36
 */
public class MenuTree {
	
	// 声明权限菜单节点集合
	private List<MenuTreeNode> nodeList;

	public List<MenuTreeNode> getNodeList() {
		return nodeList;
	}
	
	/**
	 * 构造根节点为id的权限菜单树
	 * @param id
	 * @throws Exception
	 */
	public MenuTree(String id) throws Exception {
		// 实例化权限菜单节点集合
		nodeList = new ArrayList<MenuTreeNode>();
		// 构造菜单节点实例
		Menu menu = new Menu(id);
		// 调用方法获取该权限id下的所有子权限
		List<Menu> menuList = menu.getMenuChildren();
		for (Menu m : menuList) {
			// 将遍历得到的权限集合来构造得到一棵权限树,再将的得到的权限树存进节点集合
			nodeList.add(tree(m, true));
		}
		
	}
	
	/**
	 * 构造带角色的权限树
	 * @param id
	 * @param roleid
	 * @throws Exception
	 */
	public MenuTree(String id,String roleid) throws Exception {
		// 实例化权限菜单节点集合
		nodeList = new ArrayList<MenuTreeNode>();
		// 构造菜单节点实例
		Menu menu = new Menu(id);
		// 调用方法获取该权限id下的所有子权限
		List<Menu> menuList = menu.getMenuChildren();
		for (Menu m : menuList) {
			// 将遍历得到的权限集合和角色编号来构造得到一棵权限树,再将的得到的权限树存进节点集合
			nodeList.add(tree(m,roleid, true));
		}
	}
	
	/**
	 * 获取子节点信息
	 * @author SunYi
	 * @Date 2019年4月3日下午3:41:12
	 * @return void
	 */
	public void visit(MenuTreeNode node,List<MenuTreeNode> nodes){
		// 调用方法获取子节点集合
		List<MenuTreeNode> children = node.getChildren();
		// 将节点对象存进节点集合
		nodes.add(node);
		if(children != null && !children.isEmpty()){
			for(MenuTreeNode e:node.getChildren()){
				// 循环执行方法
				visit(e,nodes);
			}
		}
	}
	
	/**
	 * 获取子权限菜单节点集合
	 * @author SunYi
	 * @Date 2019年4月3日下午3:28:13
	 * @return List<MenuTreeNode>
	 */
	public List<MenuTreeNode> getChildNodeList(){
		// 实例化权限菜单节点集合
		List<MenuTreeNode> allNodes = new ArrayList<MenuTreeNode>();
		// 遍历当前权限菜单节点集合，调用visit方法
		for(MenuTreeNode node:this.nodeList){
			visit(node, allNodes);
		}
		return allNodes;			
	}
	
	/**
	 * 生成带角色的权限菜单树
	 * @author SunYi
	 * @Date 2019年4月3日下午3:32:02
	 * @return MenuTreeNode
	 */
	public MenuTreeNode tree(Menu menu, String roleid,boolean recursive) throws Exception {
		try {
			// 构造权限菜单树节点
			MenuTreeNode node = new MenuTreeNode(menu);
			// 调用方法获取指定上一级权限编号的子权限菜单信息
			List<Menu> subMenuList = menu.getMenuChildren();
			if (!subMenuList.isEmpty()) {
				if (recursive) { 
					// 递归查询子节点
					List<MenuTreeNode> children = new ArrayList<MenuTreeNode>();
					for (Menu m : subMenuList) {
						// 循环生成权限树
						MenuTreeNode t = tree(m,roleid, true);
						// 如果角色包含该菜单则默认选中
						if(roleid != null){
						   t.setChecked(m.hasRole(roleid));
						}
						// 将节点对象存进子节点集合
						children.add(t);
					}
					// 将子节点集合存进节点对象
					node.setChildren(children);
				}
			}
			return node;
		} catch (Exception e) {
			throw new Exception("recursive create the node of tree failed!", e);
		}
	}
	
	/**
	 * 生成权限菜单树
	 * @author SunYi
	 * @Date 2019年4月3日下午3:32:02
	 * @return MenuTreeNode
	 */
	public MenuTreeNode tree(Menu menu,boolean recursive) throws Exception {
		try {
			// 构造权限菜单树节点
			MenuTreeNode node = new MenuTreeNode(menu);
			// 调用方法获取指定上一级权限编号的子权限菜单信息
			List<Menu> subMenuList = menu.getMenuChildren();
			if (!subMenuList.isEmpty()) {
				if (recursive) { 
					// 递归查询子节点
					List<MenuTreeNode> children = new ArrayList<MenuTreeNode>();
					for (Menu m : subMenuList) {
						// 循环生成权限树
						MenuTreeNode t = tree(m,null, true);
						// 将节点对象存进子节点集合
						children.add(t);
					}
					// 将子节点集合存进节点对象
					node.setChildren(children);
				}
			}
			// 返回节点对象
			return node;
		} catch (Exception e) {
			throw new Exception("recursive create the node of tree failed!", e);
		}
	}

}
