/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */
package com.hfepay.scancode.channel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.MerchantActivityDiscountCondition;
import com.hfepay.scancode.commons.entity.MerchantActivityDiscount;
import com.hfepay.scancode.service.operator.MerchantActivityDiscountService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantactivitydiscount")
public class MerchantActivityDiscountController extends BaseController{
	
	@Autowired
	private MerchantActivityDiscountService merchantActivityDiscountService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantactivitydiscount/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantActivityDiscountCondition merchantActivityDiscountCondition){		
		PagingResult<MerchantActivityDiscount> page = merchantActivityDiscountService.findPagingResult(merchantActivityDiscountCondition);
		Pager<MerchantActivityDiscount> pager = new Pager<MerchantActivityDiscount>();
		pager.setPageNo(merchantActivityDiscountCondition.getPageNo());
		pager.setPageSize(merchantActivityDiscountCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantactivitydiscount/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantActivityDiscountCondition merchantActivityDiscountCondition){		
        List<MerchantActivityDiscount> list = merchantActivityDiscountService.findAll(merchantActivityDiscountCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantActivityDiscount entity = merchantActivityDiscountService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/merchantactivitydiscount/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantActivityDiscountCondition merchantActivityDiscountCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			if(Strings.isEmpty(merchantActivityDiscountCondition.getId())){
				//新增
				merchantActivityDiscountCondition.setId(Strings.getUUID());
				merchantActivityDiscountService.insert(merchantActivityDiscountCondition);
			}else{
				merchantActivityDiscountService.update(merchantActivityDiscountCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantactivitydiscount");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,MerchantActivityDiscountCondition merchantActivityDiscountCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantActivityDiscountService.update(merchantActivityDiscountCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantActivityDiscount entity = merchantActivityDiscountService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantactivitydiscount/detail";
	}
}

