package com.agileEAP.workflow.engine;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.xml.bind.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.workflow.engine.enums.*;
import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.engine.utility.WFUtil;
import com.agileEAP.workflow.entity.ActivityInst;
import com.agileEAP.workflow.entity.Participant;
import com.agileEAP.workflow.entity.ProcessDef;
import com.agileEAP.workflow.entity.ProcessInst;
import com.agileEAP.workflow.entity.TraceLog;
import com.agileEAP.workflow.entity.TransControl;
import com.agileEAP.workflow.entity.Transition;
import com.agileEAP.workflow.entity.WorkItem;
import com.agileEAP.workflow.repository.*;
import com.agileEAP.workflow.viewModel.TransitionModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@Component("workflowPersistence")
public class WorkflowPersistence implements IWorkflowPersistence
{
	private Logger logger = LoggerFactory.getLogger(WorkflowPersistence.class);

	@Autowired
	private ProcessDefRepository processDefRepository;
	@Autowired
	private ProcessInstRepository processInstRepository;
	@Autowired
	private ActivityInstRepository activityInstRepository;
	@Autowired
	private WorkItemRepository workItemRepository;
	@Autowired
	private TraceLogRepository traceLogRepository;
	@Autowired
	private ParticipantRepository participantRepository;
	@Autowired
	private TransitionRepository transitionRepository;
	@Autowired
	private TransControlRepository transControlRepository;
	
	/** 
	 获取工作流定义
	 @param processDefID 流程定义ID
	 @return 
	 * @throws JAXBException 
	*/
	public final ProcessDefine GetProcessDefine(String processDefID)
	{
		ProcessDef processDef = processDefRepository.get(processDefID);
		
		return WFUtil.parseProcessDefine(processDef.getContent());
	}

	/** 
	 获取工作流某个活动
	 
	 @param processDefID 流程定义ID
	 @param activityDefID 活动定义ID
	 @return 
	*/
	public final Activity GetActivity(String processDefID, String activityDefID)
	{
		return WFUtil.getActivity(GetProcessDefine(processDefID).getActivities(), activityDefID);
	}

	/** 
	 获取流程所有定义活动
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	public final java.util.List<Activity> GetActivities(String processDefID)
	{
		return GetProcessDefine(processDefID).getActivities();
	}

	/** 
	 获取流程初始活动
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	public final Activity GetStartActivity(String processDefID)
	{
		return GetProcessDefine(processDefID).getStartActivity();
	}

	/** 
	 获取活动所有的入口活动
	 
	 @param processDefID 流程定义ID
	 @param activityID 活动ID
	 @return 
	*/
	public final List<Activity> GetInActivities(String processDefID, String activityID)
	{
		ProcessDefine processDefine = GetProcessDefine(processDefID);
		List<Activity> inActivities=new ArrayList<Activity>();
		for(Activity activity :processDefine.getActivities())
		{
			for(com.agileEAP.workflow.definition.Transition transition:processDefine.getTransitions())
			{
				if(transition.getDestActivity().equals(activityID)&&transition.getSrcActivity().equals(activity.getID()))
				{
					inActivities.add(activity);
				}
			}
		}

		return inActivities;
	}

	/** 
	 获取活动所有的出口活动
	 
	 @param processDefID 流程定义ID
	 @param activityID 活动ID
	 @return 
	*/
	public final java.util.List<Activity> GetOutActivities(String processDefID, String activityID)
	{
		ProcessDefine processDefine = GetProcessDefine(processDefID);

		List<Activity> outActivities=new ArrayList<Activity>();
		for(Activity activity :processDefine.getActivities())
		{
			for(com.agileEAP.workflow.definition.Transition transition:processDefine.getTransitions())
			{
				if(transition.getSrcActivity().equals(activityID)&&transition.getDestActivity().equals(activity.getID()))
				{
					outActivities.add(activity);
				}
			}
		}

		return outActivities;
	}

	/** 
	 获取活动之间的迁移
	 
	 @param processDefID 流程定义ID
	 @param srcActivityID 源活动ID
	 @param destActivityID 目标活动ID
	 @return 
	*/
	public final com.agileEAP.workflow.definition.Transition GetTransition(String processDefID, String srcActivityID, String destActivityID)
	{
		for(com.agileEAP.workflow.definition.Transition transition: GetProcessDefine(processDefID).getTransitions())
		{
			if(transition.getSrcActivity().equals(srcActivityID) && transition.getDestActivity().equals(destActivityID))
			{
				return transition;
			}
		}
		
		return null;
	}

	/** 
	 获取活动所有的入口迁移
	 
	 @param processDefID 流程定义ID
	 @param activityDefID 活动ID
	 @return 
	*/
	public final List<com.agileEAP.workflow.definition.Transition> GetInTransitions(String processDefID, String activityDefID)
	{
		List<com.agileEAP.workflow.definition.Transition> inTransitions=new ArrayList<com.agileEAP.workflow.definition.Transition>();
		for(com.agileEAP.workflow.definition.Transition transition: GetProcessDefine(processDefID).getTransitions())
		{
			if(transition.getDestActivity().equals(activityDefID))
			{
				inTransitions.add(transition);
			}
		}
		
		return inTransitions;
	}

	/** 
	 获取活动所有的入口迁移
	 
	 @param processDefID 流程定义ID
	 @param activityDefID 活动ID
	 @return 
	*/
	public final List<com.agileEAP.workflow.definition.Transition> GetOutTransitions(String processDefID, String activityDefID)
	{
		List<com.agileEAP.workflow.definition.Transition> outTransitions=new ArrayList<com.agileEAP.workflow.definition.Transition>();
		for(com.agileEAP.workflow.definition.Transition transition: GetProcessDefine(processDefID).getTransitions())
		{
			if(transition.getSrcActivity().equals(activityDefID))
			{
				outTransitions.add(transition);
			}
		}
		
		return outTransitions;
	}

	/** 
	 获取流程实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	public final ProcessInst GetProcessInst(String processInstID)
	{
		return processInstRepository.get(processInstID);
	}

	/** 
	 获取某个活动实例
	 
	 @param activityInstID 活动实例ID
	 @return 
	*/
	public final ActivityInst GetActivityInst(String activityInstID)
	{
		return activityInstRepository.get(activityInstID);
	}

	/** 
	 获取某个流程活动的所有活动实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	public final List<ActivityInst> GetActivityInsts(String processInstID, String activityDefID)
	{
		Map<String,Object> parameters=new HashMap<String,Object>();
		parameters.put("processInstID", processInstID);
		parameters.put("activityDefID", activityDefID);
		parameters.put("rollbackFlag", (short)0);
		
		return activityInstRepository.search(parameters);
	}

	/** 
	 获取某个流程活动的所有活动实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	public final List<ActivityInst> GetActivityInsts(String processInstID, String activityDefID, ActivityInstStatus actInstStatus)
	{
		List<ActivityInst> activityInsts=new ArrayList<ActivityInst>();
		for(ActivityInst ai: GetActivityInsts(processInstID, activityDefID))
		{
			if(ai.getCurrentState() == (short)actInstStatus.getValue())
			{
				activityInsts.add(ai);
			}
		}
		
		return activityInsts;
	}

	/** 
	 获取某流程正在运行的活动实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	public List<ActivityInst> GetRunningActInsts(String processInstID)
	{
		Map<String,Object> parameters=new HashMap<String,Object>();
		parameters.put("processInstID", processInstID);
		parameters.put("currentState", (short)ActivityInstStatus.Running.getValue());
		
		return activityInstRepository.search(parameters);
	}
	/** 
	 获取某个工作项
	 
	 @param workItemID 工作项ID
	 @return 
	*/
	public final WorkItem GetWorkItem(String workItemID)
	{
		return workItemRepository.get(workItemID);
	}

	/** 
	 获取某个流程实例的所有活动实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	public final java.util.List<ActivityInst> GetActivityInsts(String processInstID)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("processInstID", processInstID);

		return activityInstRepository.search(parameters);
	}

	/** 
	 获取某个活动实例的所有工作项
	 
	 @param activityInstID 活动实例ID
	 @return 
	*/
	public final java.util.List<WorkItem> GetWorkItems(String activityInstID)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("activityInstID", activityInstID);

		return workItemRepository.search(parameters);
	}


	/** 
	 获取待办工作项列表
	 
	 @param userID 用户ID
	 @param parameters 参数
	 @param pageInfo 分页信息
	 @return 
	*/
	public final PageDataResult GetMyWorkItems(String userId, java.util.Map<String, Object> parameters, String orderby, RequestPageData pageInfo, boolean includeAuto)
	{
		if (parameters == null)
		{
			parameters = new java.util.HashMap<String, Object>();
		}
		ShiroUser user =WFUtil.getUser();
		parameters.put("orderby", orderby);
		parameters.put("page", pageInfo.getPage());
    	parameters.put("pageSize", pageInfo.getPageSize());
    	parameters.putAll(parameters);
        PageDataResult pageDataResult=new PageDataResult();
		if(user.IsAdmin())
		{
	        pageDataResult.setTotal(workItemRepository.count(parameters));
	        pageDataResult.setData(workItemRepository.searchByPage(parameters));
	        return pageDataResult;
		}
		
		parameters.put("userId", userId);
		parameters.put("includeAuto", includeAuto);

        pageDataResult.setTotal(workItemRepository.countMyWorkItems(parameters));
        pageDataResult.setData(workItemRepository.getMyWorkItemsByPage(parameters));

        return pageDataResult;
        
/*		String includesql = includeAuto ? "or Type=5 " : "";

		DatabaseType databaseType = UnitOfWork.CurrentDatabaseType;
		if (databaseType == DatabaseType.MySQL)
		{

			System.Data.DataTable dt = repository.<WorkItem>ExecuteDataTable(String.format("select a.* from WF_WorkItem a " + "\r\n" + "inner join  WF_Participant p on a.ID=p.WorkItemID" + "\r\n" + "where ((p.ParticipantID='%1$s' or" + "\r\n" + " 		p.ParticipantID in(select  RoleID from OM_ObjectRole r where r.ObjectID='%1$s')" + "\r\n" + " 		or p.ParticipantID in (select OrgID from OM_EmployeeOrg o where o.EmployeeID='%1$s')) %2$s) and 1=1 ", userID, includesql), parameters, (sortCommand != null) ? sortCommand : "order by CreateTime desc", pageInfo);


			PageOfList<WorkItem> tempVar = new PageOfList<WorkItem>();
			tempVar.ItemList = dt.<WorkItem>ToList();
			tempVar.PageInfo = pageInfo;
			return tempVar;
		}
		else
		{
			parameters.SafeAdd(userID, new Condition(String.format("(ID in" + "\r\n" + "                                (select WorkItemID from WF_Participant p where p.ParticipantID='%1$s' or" + "\r\n" + "                                 exists(select ObjectID from OM_ObjectRole r where r.ObjectID='%1$s' and r.RoleID=p.ParticipantID) or" + "\r\n" + "                                 exists(select ID from OM_EmployeeOrg o where o.EmployeeID='%1$s' and o.OrgID=p.ParticipantID)) %2$s)", userID, includesql)));


			return repository.<WorkItem>FindAll(parameters, (sortCommand != null) ? sortCommand : "order by CreateTime desc", pageInfo);
		}*/
	}

	/** 
	 修改工作项状态
	 
	 @param workItemID 工作项ID
	 @param status 工作项状态
	*/
	public final void UpdateWorkItemStatus(String workItemID, WorkItemStatus status)
	{
		WorkItem workItem = workItemRepository.get(workItemID);

		if (workItem == null)
		{
			logger.warn("UpdateWorkItemStatus error workitem is null");
			return;
		}

		workItem.setCurrentState((short)status.getValue());
		workItemRepository.update(workItem);
	}

	/** 
	 修改活动状态
	 
	 @param activityInstID 活动实例ID
	 @param status 活动状态
	*/
	public final void UpdateActivityInstStatus(String activityInstID, ActivityInstStatus status)
	{
		ActivityInst activityInst = activityInstRepository.get(activityInstID);

		if (activityInst == null)
		{
			logger.warn("UpdateActivityInstStatus error activityInst is null");
			return;
		}

		activityInst.setCurrentState((short)status.getValue());
		activityInstRepository.update(activityInst);
	}

	/** 
	修改流程状态 
	 
	 @param processInstID 流程实例ID
	 @param status 流程状态
	*/
	public final void UpdateProcessInstStatus(String processInstID, ProcessInstStatus status)
	{
		ProcessInst instance = processInstRepository.get(processInstID);

		if (instance == null)
		{
			logger.warn("UpdateActivityInstStatus error activityInst is null");
			return;
		}

		instance.setCurrentState((short)status.getValue());
		processInstRepository.update(instance);
	}

	/** 
	 获取活动参与者
	 
	 @param processDefID 流程定义ID
	 @param activityDefID 活动定义ID
	 @return 
	*/
	public final String GetParticipant(String processDefID, String activityDefID)
	{
		return "";
		//ManualActivity ma = GetActivity(processDefID, activityDefID) as ManualActivity;

		////if (ma.Participant.ParticipantType.Cast<ParticipantType>(ParticipantType.Person) == ParticipantType.Person)
		////{

		////}
		//return string.Join(",", ma.Participant.Participantors.Select(p => p.ID).ToArray());
	}

	public final void SaveTraceLog(TraceLog traceLog) {
		traceLogRepository.save(traceLog);
	}

	public final void SaveWorkItem(WorkItem workItem) {
		workItemRepository.save(workItem);
	}

	public final void UpdateWorkItem(WorkItem workItem) {
		workItemRepository.update(workItem);
	}

	public final void UpdateActivityInst(ActivityInst activityInst) {
		activityInstRepository.update(activityInst);		
	}

	public final void SaveParticipant(Participant participant) {
		participantRepository.save(participant);
	}

	public final void SaveActivityInst(ActivityInst activityInst) {
		activityInstRepository.save(activityInst);		
	}

	public final void SaveTransition(Transition transition) {
		transitionRepository.save(transition);
	}

	public final void SaveTransControl(TransControl transControl) {
		transControlRepository.save(transControl);
	}

	public final void SaveOrUpdateActivityInst(ActivityInst activityInst) {
		if(activityInstRepository.get(activityInst.getId())!=null)
		{
			activityInstRepository.update(activityInst);
		}
		else
		{
			activityInstRepository.save(activityInst);
		}
	}

	public final ProcessDef GetProcessDef(String processDefID) {
		return processDefRepository.get(processDefID);
	}

	public final void SaveProcessInst(ProcessInst processInst) {
		processInstRepository.save(processInst);
	}

	public final void SaveProcessDef(ProcessDef processDef) {
		 processDefRepository.save(processDef);
	}

	public final void UpdateProcessInst(ProcessInst processInst) {
		processInstRepository.update(processInst);
	}

	public final List<WorkItem> getWorkItems(Map<String, Object> parameters) {
		return workItemRepository.search(parameters);
	}

	public final void UpdateWorkItemStatus(String processInstID,
			WorkItemStatus oldStatus, WorkItemStatus newStatus) {
		WorkItem workItem=new WorkItem();
		workItem.setCurrentState((short)newStatus.getValue());
		
		Map<String, Object> parameters =new HashMap<String,Object>();
		parameters.put("processInstID", processInstID);
		parameters.put("currentState", (short)oldStatus.getValue());
		
		workItemRepository.updateByWhere(workItem, parameters);
	}

	public final void UpdateActivityInstStatus(String processInstID,
			ActivityInstStatus oldStatus, ActivityInstStatus newStatus) {
		ActivityInst activityInst=new ActivityInst();
		activityInst.setCurrentState((short)newStatus.getValue());
		
		Map<String, Object> parameters =new HashMap<String,Object>();
		parameters.put("processInstID", processInstID);
		parameters.put("currentState", (short)oldStatus.getValue());
		
		activityInstRepository.updateByWhere(activityInst, parameters);
	}

	public final void DeleteProcessInst(String processInstID) {
		Map<String, Object> parameters =new HashMap<String,Object>();
		parameters.put("ProcessInstID", processInstID);
		
		transControlRepository.deleteByWhere(parameters);
		transitionRepository.deleteByWhere(parameters);
		workItemRepository.deleteByWhere(parameters);
		activityInstRepository.deleteByWhere(parameters);
		processInstRepository.delete(processInstID);
	}

	public final void DeleteWorkItem(String workItemID) {
		workItemRepository.delete(workItemID);
	}

	public final List<Operator> GetWorkItemParticipant(String workItemID) {
		return 	workItemRepository.getWorkItemParticipant(workItemID);
	}
	
	/** 
	 获取活动实例列表
	 
	 @param parameters 参数
	 @return
	*/
	public final List<ActivityInst> ActivityInsts(Map<String, Object> parameters){
		return activityInstRepository.search(parameters);
	}

	/** 
	 获取流程实例迁移记录
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	public final List<TransitionModel> GetProcessInstTransitions(String processInstID) {
		return processInstRepository.getProcessInstTransitions(processInstID);
	}
}