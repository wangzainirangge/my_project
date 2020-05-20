package com.wangzai.project.common;

import com.github.pagehelper.PageInfo;

/**
 * @ClassName: BaseModel
 * @Description: 用于封装接口的返回值
 */
public class BaseModel<T> {
	private String status = Constants.SUCCESS;

	private String message = "操作成功";

	private T data;

	private PageModel page;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageInfo<?> pageInfo) {
		this.page = new PageModel(pageInfo);
	}
}
