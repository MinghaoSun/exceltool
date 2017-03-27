package com.aas.hss.common.util;

public class Page {

	private int pageIndex;
	private int pageSize;
	private int totalRecords;
	private int startIndex;
	private int endIndex;
	private Object object;

	public Page() {
		super();
	}

	public Page(int pageIndex, int pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.setStartIndex((pageIndex - 1) * pageSize);
		this.setEndIndex(pageIndex * pageSize);
	}

	public Page(Object objPageIndex, Object ObjPageSize) {
		super();
		this.pageIndex = Utility.toSafePageIndex(objPageIndex);
		this.pageSize = Utility.toSafePageSize(ObjPageSize);
		this.setStartIndex((pageIndex - 1) * pageSize);
		this.setEndIndex(pageIndex * pageSize);
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
