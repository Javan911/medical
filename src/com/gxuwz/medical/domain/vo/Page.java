package com.gxuwz.medical.domain.vo;

import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.config.Constant;

/**
 * 分页工具类
 * @ClassName: Page
 * @author SunYi
 * @Date 2019年4月3日下午10:43:00
 */
public class Page {

	// 记录总数
	private int total;
	// 页面总数
	private int pages;
	// 当前页码
	private int pageNo;
	// 页面显示记录数
	private int limit;
	// 要显示到页面的list数据集  
	private List datas;

	public Page(int total, int pageNo, int limit, List datas) {
		super();
		this.total = total;
		this.pageNo = pageNo;
		this.limit = limit;
		this.datas = datas;
		// 判断记录总数total,对页面显示记录数limit取余是否等于0
		if(this.total % this.limit == 0){
			this.pages = this.total / this.limit;
		}else{
			this.pages = (this.total / this.limit) + 1;
		}
	}
	
	// 获取要显示到页面的list数据集 
	public List getDatas() {
		return datas;
	}	
	
	// 页面首页
	public int getFirstPage() {
		return 1;
	}
	// 页面总数
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	// 是否有下一页
	public boolean hasNext() {
		return this.pageNo < this.pages;
	}
	
	// 是否有上一页
	public boolean hasPre() {
		return (this.pageNo - 1) >= 1;
	}
	
	// 跳转上一页
	public int prePage(){
		return (this.pageNo - 1);
	}
	
	// 跳转下一页
	public int lastPage(){
		return (this.pageNo + 1);
	}
	
    // 是否第一页
	public boolean isFirst() {
		return (this.pageNo==1);
	}
	
	// 是否最后一页
	public boolean isLast() {
		return (this.pageNo==this.pages);
	}
	
	// 是否当前页
	public boolean isCurrent(int p) {
		return (this.pageNo == p);
	}
	
	/**
	 * 生成分页数
	 * @author SunYi
	 * @Date 2019年4月3日下午10:51:57
	 * @return List<Integer>
	 */
	public  List<Integer> linkNumbers() {
		// 根据页面总数得到页面平均数
		int avg = Constant.PAGED / 2;
		// 当前页面-页面平均数得到开始位置start
		int start = this.pageNo - avg;
		if (start <= 0) {
			start = 1;
		}
		// 计算得到结束位置end
		int end = start + Constant.PAGED - 1;
		if (end > this.pages) {
			end = this.pages;
		}
		if ((end - start) < Constant.PAGED) {
			// 计算start
			start = end - Constant.PAGED;
			start = start <= 0 ? 1:start;
		}
		// 实例化整型List集合
		List<Integer> result = new ArrayList<Integer>();
		for (int i = start; i <= end; i++) {
			result.add(new Integer(i));
		}
		// 返回集合
		return result;
	}

}
