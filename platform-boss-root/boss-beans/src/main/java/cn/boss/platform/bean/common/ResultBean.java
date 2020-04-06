package cn.boss.platform.bean.common;


public class ResultBean<T> {

	private String message;
	private T data;
	private String status;

	public ResultBean() {
		super();
	}
	
	public static <T>  ResultBean<T> success(String message) {
		return new ResultBean<>("success", message, null);
	}
	
	public static <T>  ResultBean<T> success(String message, T data) {
		return new ResultBean<>("success", message, data);
	}
	
	public static <T>  ResultBean<T> failed(String message) {
		return new ResultBean<>("failed", message, null);
	}
	
	public static <T>  ResultBean<T> failed(String message, T data) {
		return new ResultBean<>("failed", message, data);
	}
	
	public static <T>  ResultBean<T> notExist(String message) {
		return new ResultBean<>("not-exist", message, null);
	}
	
	public static <T>  ResultBean<T> duplicate(String message) {
		return new ResultBean<>("duplicate", message, null);
	}
	
	public ResultBean(String status, String message, T date) {
		this.setMessage(message);
		this.setData(date);
		this.setStatus(status);
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "ResultBean [status=" + status + ", message=" + message
				+ ", data=" + data + "]";
	}

}