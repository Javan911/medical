package com.gxuwz.medical.domain.area;

import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.domain.area.Area;
import com.gxuwz.medical.domain.area.AreaTreeNode;

/**
 * 行政区域树组件
 * @ClassName: AreaTree
 * @author SunYi
 * @Date 2019年4月3日下午11:47:34
 */
public class AreaTree {
	
	// 声明行政区域节点集合
	private List<AreaTreeNode> nodeList;

	public List<AreaTreeNode> getNodeList() {
		return nodeList;
	}

	 /**
	  * 构造根节点为treeID的行政区域树
	  * @param id
	  * @throws Exception
	  */
	public AreaTree(String treeID) throws Exception {
		// 实例化行政区域节点集合
		nodeList = new ArrayList<AreaTreeNode>();
		// 构造行政区域节点实例
		Area area = new Area(treeID);
		// 调用方法获取该行政区域id下的所有子行政区域
		List<Area> areaList = area.getAreaChildren();
		for (Area a : areaList) {
			// 将遍历得到的行政区域集合来构造得到一棵行政区域树,再将的得到的行政区域树存进节点集合
			nodeList.add(tree(a, true));
		}
	}
	
	 /**
	  * 构造带有指定行政区域编号的行政区域树
	  * @param id
	  * @throws Exception
	  */
	public AreaTree(String treeID, String areacode) throws Exception {
		// 实例化行政区域节点集合
		nodeList = new ArrayList<AreaTreeNode>();
		// 构造行政区域节点实例
		Area area = new Area(treeID);
		// 调用方法获取该行政区域id下的所有子行政区域
		List<Area> areaList = area.getAreaChildren();
		for (Area a : areaList) {
			// 将遍历得到的行政区域集合来构造得到一棵行政区域树,再将的得到的行政区域树存进节点集合
			nodeList.add(tree(a, areacode, true));
		}
	}
	
	/**
	 * 获取子节点信息
	 * @author SunYi
	 * @Date 2019年4月3日下午3:41:12
	 * @return void
	 */
	public void visit(AreaTreeNode node,List<AreaTreeNode> nodes){
		// 调用方法获取子节点集合
		List<AreaTreeNode> children = node.getChildren();
		// 将节点对象存进节点集合
		nodes.add(node);
		if(children != null && !children.isEmpty()) {
			for(AreaTreeNode e:node.getChildren()){
				// 循环执行方法
				visit(e,nodes);
			}
		}
	}
	
	/**
	 * 获取子行政区域节点集合
	 * @author SunYi
	 * @Date 2019年4月3日下午3:28:13
	 * @return List<AreaTreeNode>
	 */
	public List<AreaTreeNode> getChildNodeList() {
		// 实例化行政区域节点集合
		List<AreaTreeNode> allNodes = new ArrayList<AreaTreeNode>();
		// 遍历当前行政区域节点集合，调用visit方法
		for(AreaTreeNode node:this.nodeList){
			visit(node, allNodes);
		}
		return allNodes;			
	}
	
	 /**
	  * 生成行政区域树
	  * @author SunYi
	  * @Date 2019年4月3日下午11:51:54
	  * @return AreaTreeNode
	  */
	public AreaTreeNode tree(Area area,boolean recursive) throws Exception {
		try {
			// 构造行政区域树节点
			AreaTreeNode node = new AreaTreeNode(area);
			// 调用方法获取指定上一级权限编号的子行政区域信息
			List<Area> subAreaList = area.getAreaChildren();
			if (!subAreaList.isEmpty()) {
				if (recursive) { 
					// 递归查询子节点
					List<AreaTreeNode> children = new ArrayList<AreaTreeNode>();
					for (Area a : subAreaList) {
						// 循环生成行政区域树
						AreaTreeNode t = tree(a, true);
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
	
	/**
	  * 生成带有指定行政区域编号的行政区域树
	  * @author SunYi
	  * @Date 2019年4月3日下午11:51:54
	  * @return AreaTreeNode
	  */
	public AreaTreeNode tree(Area area, String areacode, boolean recursive) throws Exception {
		try {
			// 构造行政区域树节点
			AreaTreeNode node = new AreaTreeNode(area);
			// 调用方法获取指定上一级权限编号的子行政区域信息
			List<Area> subAreaList = area.getAreaChildren();
			if (!subAreaList.isEmpty()) {
				if (recursive) { 
					// 递归查询子节点
					List<AreaTreeNode> children = new ArrayList<AreaTreeNode>();
					for (Area a : subAreaList) {
						// 循环生成行政区域树
						AreaTreeNode t = tree(a, areacode, true);
						// 默认选中当前行政区域
						if(areacode != null) {
							t.setChecked(a.hasAreacode(areacode));
						}
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
