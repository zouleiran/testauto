/**
 * 
 */
package cn.boss.platform.web.form;


import cn.boss.platform.web.validator.JSON;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 *版权所有：北京光宇在线科技有限责任公司
 *创建时间：2017年7月10日 下午2:44:02 
 *类名：
 * @author caosenquan
 */
public class RequestForm {

	private Integer projectId;
	//请求地址
	@NotBlank(message = "请求地址不能为空！")
	@Pattern(regexp="^((ht|f)tps?):\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-\\.,@?^=%&:\\/~\\+#]*[\\w\\-\\@?^=%&\\/~\\+#])?$", message="接口地址格式错误！")
	private String url;
	//请求参数
	@JSON(message = "请求参数必须为json格式!")
	private String parameter;
	//请求方式
	@NotBlank(message = "请求方式不能为空！")
	private String method;
	//请求cookie
	private String cookie; 
	
	//请求头文件
	private String headers;
	
	//请求签名值
	private String signKey;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
}
