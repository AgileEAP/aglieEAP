package com.agileEAP.workflow.engine;

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.agileEAP.security.entity.Operator;
import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.workflow.entity.Participant;
import com.agileEAP.workflow.entity.Transition;
import com.agileEAP.workflow.engine.enums.*;
import com.agileEAP.workflow.viewModel.TransitionModel;
import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;

/** 
 定义保存、获取流程定义和实例的接口
 
*/
public interface IWorkflowPersistence
{
	/** 
	 获取工作流定义
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	ProcessDefine GetProcessDefine(String processDefID);

	/** 
	 获取工作流某个活动
	 
	 @param processDefID 流程定义ID
	 @param activityID 活动定义ID
	 @return 
	*/
	Activity GetActivity(String processDefID, String activityID);

	/** 
	 获取流程所有定义活动
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	List<Activity> GetActivities(String processDefID);

	/** 
	 获取流程初始活动
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	Activity GetStartActivity(String processDefID);
	/** 
	 获取活动之间的迁移
	 
	 @param processDefID 流程定义ID
	 @param srcActivityID 源活动ID
	 @param destActivityID 目标活动ID
	 @return 
	*/
	com.agileEAP.workflow.definition.Transition GetTransition(String processDefID, String srcActivityID, String destActivityID);

	/** 
	 获取活动所有的入口迁移
	 
	 @param processDefID 流程定义ID
	 @param activityID 活动ID
	 @return 
	*/
	List<com.agileEAP.workflow.definition.Transition> GetInTransitions(String processDefID, String activityID);

	/** 
	 获取活动所有的出口迁移
	 
	 @param processDefID 流程定义ID
	 @param activityID 活动ID
	 @return 
	*/
	List<com.agileEAP.workflow.definition.Transition> GetOutTransitions(String processDefID, String activityID);

	/** 
	 获取活动所有的入口活动
	 
	 @param processDefID 流程定义ID
	 @param activityID 活动ID
	 @return 
	*/
	List<com.agileEAP.workflow.definition.Activity> GetInActivities(String processDefID, String activityID);

	/** 
	 获取活动所有的出口活动
	 
	 @param processDefID 流程定义ID
	 @param activityID 活动ID
	 @return 
	*/
	List<com.agileEAP.workflow.definition.Activity> GetOutActivities(String processDefID, String activityID);


	/** 
	 获取流程实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	ProcessInst GetProcessInst(String processInstID);

	/** 
	 获取某个活动实例
	 
	 @param activityInstID 活动实例ID
	 @return 
	*/
	ActivityInst GetActivityInst(String activityInstID);
	
	/** 
	 获取某流程正在运行的活动实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	List<ActivityInst> GetRunningActInsts(String processInstID);

	/** 
	 获取某个工作项
	 
	 @param workItemID 工作项ID
	 @return 
	*/
	WorkItem GetWorkItem(String workItemID);

	/** 
	 获取某个流程实例的所有活动实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	List<ActivityInst> GetActivityInsts(String processInstID);

	/** 
	 获取某个流程活动的所有活动实例
	 
	 @param processInstID 流程实例ID
	 @param activityDefID 活动定义ID
	 @return 
	*/
	List<ActivityInst> GetActivityInsts(String processInstID, String activityDefID);

	/** 
	 获取某个流程活动的所有活动实例
	 
	 @param processInstID 流程实例ID
	 @param activityDefID 活动定义ID
	 @param actInstStatus 活动实例状态
	 @return 
	*/
	List<ActivityInst> GetActivityInsts(String processInstID, String activityDefID, ActivityInstStatus actInstStatus);

	/** 
	 获取某个活动实例的所有工作项
	 
	 @param activityInstID 活动实例ID
	 @return 
	*/
	List<WorkItem> GetWorkItems(String activityInstID);
	
	/** 
	 获取工作项
	 
	 @param parameters 查询参数
	 @return 
	*/
	List<WorkItem> getWorkItems(Map<String, Object> parameters);

	/** 
	 获取待办工作项列表
	 
	 @param userID 用户ID
	 @param parameters 参数
	 @param pageInfo 分页信息
	 @return 
	*/
	PageDataResult GetMyWorkItems(String userId, java.util.Map<String, Object> parameters, String orderby, RequestPageData pageInfo, boolean includeAuto);

	/** 
	 修改工作项状态
	 
	 @param workItemID 工作项ID
	 @param status 工作项状态
	*/
	void UpdateWorkItemStatus(String workItemID, WorkItemStatus status);
	
	/** 
	 修改流程工作项状态
	 
	 @param processInstID 流程实例ID
	 @param oldStatus 工作项老状态
	 @param newStatus 工作项新状态
	 @param status 
	*/
	void UpdateWorkItemStatus(String processInstID, WorkItemStatus oldStatus, WorkItemStatus newStatus);

	/** 
	 修改活动状态
	 
	 @param activityInstID 活动实例ID
	 @param status 活动状态
	*/
	void UpdateActivityInstStatus(String activityInstID, ActivityInstStatus status);
	
	/** 
	 修改流程活动状态
	 
	 @param processInstID 流程实例ID
	 @param oldStatus 活动老状态
	 @param newStatus 活动新状态
	*/
	void UpdateActivityInstStatus(String processInstID,ActivityInstStatus oldStatus, ActivityInstStatus newStatus);

	/** 
	修改流程状态 
	 
	 @param processInstID 流程实例ID
	 @param status 流程状态
	*/
	void UpdateProcessInstStatus(String processInstID, ProcessInstStatus status);

	/** 
	 获取活动参与者
	 
	 @param processDefID 流程定义ID
	 @param activityDefID 活动定义ID
	 @return 
	*/
	String GetParticipant(String processDefID, String activityDefID);

	/** 
	 保存迁移日志
	 
	 @param traceLog 迁移日志
	*/
	void SaveTraceLog(TraceLog traceLog);

	/** 
	 保存工作项
	 
	 @param workItem 工作项
	*/
	void SaveWorkItem(WorkItem workItem);

	/** 
	 更新工作项
	 
	 @param workItem 工作项
	*/
	void UpdateWorkItem(WorkItem workItem);

	/** 
	 更新活动实例
	 
	 @param actInst 活动实例
	*/
	void UpdateActivityInst(ActivityInst activityInst);

	/** 
	 保存参与者
	 
	 @param participant 参与者
	*/
	void SaveParticipant(Participant participant);

	/** 
	 保存活动实例
	 
	 @param activityInst 活动实例 
	*/
	void SaveActivityInst(ActivityInst activityInst);

	/** 
	 保存迁移记录
	 
	 @param transition 迁移记录
	*/
	void SaveTransition(Transition transition);

	/** 
	 保存迁移控制记录
	 
	 @param transControl 迁移控制记录
	*/
	void SaveTransControl(TransControl transControl);

	/** 
	 保存或修改活动实例 
	 
	 @param activityInst 活动实例 
	*/
	void SaveOrUpdateActivityInst(ActivityInst activityInst);

	/** 
	 获取流程定义
	 
	 @param processDefID 流程ID
	 @return 
	*/
	ProcessDef GetProcessDef(String processDefID);

	/** 
	 保存流程实例
	 
	 @param processInst 流程实例
	*/
	void SaveProcessInst(ProcessInst processInst);

	/** 
	 保存流程定义
	 
	 @param processInst 流程定义
	*/
	void SaveProcessDef(ProcessDef processDef);

	/** 
	 修改流程实例
	 
	 @param processInst 流程实例
	*/
	void UpdateProcessInst(ProcessInst processInst);

	/** 
	 删除流程流程实例及活动实例，工作项
	 
	 @param processInstID 流程实例ID
	*/
	void DeleteProcessInst(String processInstID);

	/** 
	 删除工作项
	 
	 @param workItemID 工作项ID
	*/
	void DeleteWorkItem(String workItemID);

	/** 
	 获取工作项参与者
	 
	 @param workItemID 工作项ID
	*/
	List<Operator> GetWorkItemParticipant(String workItemID);

	
	/** 
	 获取活动实例列表
	 
	 @param parameters 参数列表
	 @return 
	*/
	List<ActivityInst> ActivityInsts(Map<String, Object> parameters);

	/** 
	 获取流程实例迁移记录
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	List<TransitionModel> GetProcessInstTransitions(String processInstID);
}