package com.agileEAP.workflow.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.agileEAP.utils.JsonConvert;
import com.agileEAP.workflow.entity.ActivityInst;
import com.agileEAP.workflow.entity.ProcessDef;
import com.agileEAP.workflow.entity.Transition;
import com.agileEAP.workflow.repository.ActivityInstRepository;
import com.agileEAP.workflow.repository.ProcessDefRepository;
import com.agileEAP.workflow.repository.ProcessInstRepository;
import com.agileEAP.workflow.repository.TransitionRepository;


public class WorkflowService implements IWorkflowService
{
	@Autowired
	private ProcessDefRepository processDefRepository;
	
	@Autowired
	private ProcessInstRepository processInstRepository;
	
	@Autowired
	private ActivityInstRepository activityInstRepository;
	
	@Autowired
	private TransitionRepository ransitionRepository;
	
	public final String GetProcessDefine(String processDefID)
	{
		ProcessDef processDef=processDefRepository.get(processDefID);
		return processDef != null ? processDef.getContent() : null;
	}

	public final String GetProcessActivityInsts(String processInstID)
	{
		Map<String,Object> parameters=new HashMap<String,Object>();
		parameters.put("processInstID", processInstID);
		List<ActivityInst> activityInsts = activityInstRepository.search(parameters);
		
		JsonConvert jsonConvert = new JsonConvert();
		return jsonConvert.toJson(activityInsts);
	}

	public final String GetProcessTransitions(String processInstID)
	{
		Map<String,Object> parameters=new HashMap<String,Object>();
		parameters.put("processInstID", processInstID);
		List<Transition> transactions = ransitionRepository.search(parameters);

		JsonConvert jsonConvert = new JsonConvert();
		return jsonConvert.toJson(transactions);
	}
}