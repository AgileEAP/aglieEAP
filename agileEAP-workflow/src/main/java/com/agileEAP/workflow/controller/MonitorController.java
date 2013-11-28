package com.agileEAP.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agileEAP.utils.*;
import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.workflow.service.ProcessInstService;
import com.agileEAP.workflow.service.TraceLogService;
import com.agileEAP.workflow.service.TransitionService;
import com.agileEAP.workflow.viewModel.ProcessInstModel;
import com.agileEAP.workflow.engine.*;
import com.agileEAP.workflow.engine.enums.*;

@Controller
@RequestMapping(value = "/workflow/monitor")
public class MonitorController {
	@Autowired
	private IWorkflowEngine workflowEngine;
	@Autowired
	private ProcessInstService processInstService;
	@Autowired
	private TraceLogService traceLogService;
	@Autowired
	private TransitionService transitionService;

	@RequestMapping(value = "processinst",method = RequestMethod.GET)
	public final String ProcessInst() {
		return "workflow/processInst";
	}

	@RequestMapping(value = "processInst_filter",method = RequestMethod.POST)
	@ResponseBody
	public final PageDataResult ProcessInst_Filter(HttpServletRequest request)
			throws IOException {
		InputStream is = request.getInputStream();
		byte[] bytes = new byte[request.getContentLength()];
		is.read(bytes);
		String json = new String(bytes, request.getCharacterEncoding());

		JsonConvert jsonConvert = new JsonConvert();
		RequestPageData requestData = jsonConvert.fromJson(json,
				RequestPageData.class);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("page", requestData.getPage());
		parameters.put("pageSize", requestData.getPageSize());
		parameters.putAll(requestData.getData());

		if (parameters != null && parameters.containsKey("currentState")) {
			String currentState = (String) parameters.get("currentState");
			if (currentState.equals("未启动")) {
				parameters.put("currentState", (short) 1);
			} else if (currentState.equals("运行")) {
				parameters.put("currentState", (short) 2);
			} else if (currentState.equals("挂起")) {
				parameters.put("currentState", (short) 3);
			} else if (currentState.equals("完成")) {
				parameters.put("currentState", (short) 4);
			} else if (currentState.equals("终止")) {
				parameters.put("currentState", (short) 5);
			} else if (currentState.equals("取消")) {
				parameters.put("currentState", (short) 6);
			}
		}
		parameters.put("orderby", "order by CreateTime desc");

		PageDataResult pageDataResult = processInstService
				.searchByPage(parameters);

		if (pageDataResult.getData() != null) {
			List<ProcessInstModel> processInstModels = new ArrayList<ProcessInstModel>();
			for (Object item : pageDataResult.getData()) {
				ProcessInst processInst = (ProcessInst) item;
				ProcessInstModel model = new ProcessInstModel();
				model.setID(processInst.getId());
				model.setName(processInst.getName());
				model.setProcessVersion(processInst.getProcessVersion());
				model.setStartTime(processInst.getStartTime());
				model.setEndTime(processInst.getEndTime());

				String currentState = "未发布";
				switch (ProcessInstStatus.forValue((int) processInst
						.getCurrentState())) {
				case NoStart:
					currentState = "未启动";
					break;
				case Running:
					currentState = "运行";
					break;
				case Suspended:
					currentState = "挂起";
					break;
				case Completed:
					currentState = "完成";
					break;
				case Terminated:
					currentState = "终止";
					break;
				case Canceled:
					currentState = "取消";
					break;
				default:
					break;
				}
				model.setCurrentState(currentState);
				processInstModels.add(model);
			}
			pageDataResult.setData(processInstModels);
		}

		return pageDataResult;
	}

	@RequestMapping(value = "transition",method = RequestMethod.GET)
	public final String Transition() {
		return "workflow/transition";
	}

	@RequestMapping(value = "transition_filter",method = RequestMethod.POST)
	@ResponseBody
	public final PageDataResult Transition_Filter(HttpServletRequest request)
			throws IOException {
		InputStream is = request.getInputStream();
		byte[] bytes = new byte[request.getContentLength()];
		is.read(bytes);
		String json = new String(bytes, request.getCharacterEncoding());
		JsonConvert jsonConvert = new JsonConvert();
		RequestPageData requestData = jsonConvert.fromJson(json,
				RequestPageData.class);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("page", requestData.getPage());
		parameters.put("pageSize", requestData.getPageSize());
		String orderby = "order by transTime desc";
		parameters.put("orderby", orderby);
		parameters.putAll(requestData.getData());

		return transitionService.searchByPage(parameters);
	}

	@RequestMapping(value = "tracelog",method = RequestMethod.GET)
	public final String TraceLog() {
		return "workflow/traLcelog";
	}

	@RequestMapping(value = "traceLog_filter",method = RequestMethod.POST)
	@ResponseBody
	public final PageDataResult TraceLog_Filter(HttpServletRequest request)
			throws IOException {
		InputStream is = request.getInputStream();
		byte[] bytes = new byte[request.getContentLength()];
		is.read(bytes);
		String json = new String(bytes, request.getCharacterEncoding());
		JsonConvert jsonConvert = new JsonConvert();
		RequestPageData requestData = jsonConvert.fromJson(json,
				RequestPageData.class);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("page", requestData.getPage());
		parameters.put("pageSize", requestData.getPageSize());
		String orderby = "order by transTime desc";
		parameters.put("orderby", orderby);
		parameters.putAll(requestData.getData());

		return traceLogService.searchTraceLogByPage(parameters);
	}
}