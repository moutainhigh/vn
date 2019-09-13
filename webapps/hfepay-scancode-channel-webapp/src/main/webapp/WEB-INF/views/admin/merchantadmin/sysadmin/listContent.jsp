<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<!-- <th>渠道编号</th>
								<th>渠道名称</th>
								<th>代理商编号</th>
								<th>代理商名称</th> -->
								<th>商户编号</th>
								<th>商户名称</th>
								<th>账号</th>
								<th>姓名</th>
								<th>电话</th>
								<th>角色</th>
								<th>状态</th>
								<th>创建时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
						   		<%-- <td>${item.channelCode}</td>
						   		<td>${item.channelName}</td>
						   		<td>${item.agentNo}</td>
						   		<td>${item.agentName}</td> --%>
						   		<td>${item.merchantNo}</td>
						   		<td>${item.merchantName}</td>
								<td class="parent_id_parse" title="userName">${item.userName}</td>
								<td class="parent_id_parse" title="shortName">${item.shortName}</td>
								<td class="parent_id_parse" title="phone">${item.phone}</td>
								<td class="parent_id_parse" title="roleName">${item.roleName}</td>
								<td class="parent_id_parse" title="status">${item.status}</td>
								<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/merchantadmin/${item.id}"><span class="label label-info">修改</span></a>
						   		<c:if test="${item.status == 0}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
								<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="0" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>	
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="11">查无数据！</td>
						</tr>
					</c:if>
			</tbody>
			</table>
			<script type="text/javascript">
						dataOpr();
						
						//显示详情
						function todetail(id){
							$.ajax({
								   type: "POST",
								   url: "merchantadmin/detail/"+id,
								   success: function(msg){
									   var btn = {success:{   
									       label: "关闭",
									       className: "btn-success",
									       callback: function() {
									       }
									     }};
									   bootbox.dialog({
										   message: msg,
										   title: "详情列表",
										   onEscape: function() {},
										   className: "green-haze",
										   buttons: btn
										 });
								   }
							});
						}
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"  totalCounts="${pager.totalCount }"/>				