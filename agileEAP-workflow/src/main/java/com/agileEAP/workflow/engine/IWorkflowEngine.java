package com.agileEAP.workflow.engine;

import java.util.List;
import java.util.Map;

import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;

/** 
 工作流引擎接口
 
*/
public interface IWorkflowEngine
{
	/** 
	 流程数据接口
	 
	*/
	IWorkflowPersistence getWorkflowPersistence();

	/** 
	 创建工作流
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	String CreateAProcess(String processDefID);

	/** 
	 创建流程实例
	 
	 @param processDefID 流程定义ID
	 @param parentProcessDefID 父流程ID
	 @param parentActivityID 父活动ID
	 @return 
	*/
	String CreateAProcess(String processDefID, String parentProcessDefID, String parentActivityID);

	/** 
	 开始启动一个流程
	 
	 @param processInstID 流程实例ID
	 @param parameters 参数
	*/
	void StartAProcess(String processInstID, Map<String, Object> parameters);

	/** 
	 开始启动一个流程
	 
	 @param processInstID 启动
	*/
	void StartAProcess(String processInstID);

	/** 
	 获取流程定义
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	ProcessDefine GetProcessDefine(String processDefID);

	/** 
	 挂起流程实例
	 
	 @param processInstID 流程实例ID
	*/
	void SuspendProcessInst(String processInstID);

	/** 
	 恢复流程实例
	 
	 @param processInstID 流程实例ID
	*/
	void ResumeProcessInst(String processInstID);

	/** 
	 终止流程实例
	 
	 @param processInstID 流程实例ID
	*/
	void TerminateProcessInst(String processInstID);

	/** 
	 删除流程实例
	 
	 @param processInstID 流程实例ID
	*/
	void DeleteProcessInst(String processInstID);

	/** 
	 完成活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	void CompleteActivityInst(String activityInstID);

	/** 
	 重启活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	void ResetActivityInst(String activityInstID);

	/** 
	 挂起活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	void SuspendActivityInst(String activityInstID);

	/** 
	 恢复活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	void ResumeActivityInst(String activityInstID);

	/** 
	 激活活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	void ActivateActivityInst(String activityInstID);

	/** 
	 终止活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	void TerminateActivityInst(String activityInstID);

	/** 
	 将活动实例置为出错
	 
	 @param activityInstID 活动实例ID
	*/
	void ErrorActivityInst(String activityInstID);

	/** 
	 获取待办工作项列表
	 
	 @param userID 用户ID
	 @param parameters 参数
	 @param pageInfo 分页信息
	 @return 
	*/
	PageDataResult GetMyWorkItems(String userID, Map<String, Object> parameters, String sortCommand, RequestPageData pageInfo, boolean includeAuto);

	/** 
	 获取当前流程实例待执行工作项及参与者字典
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	Map<WorkItem, List<Operator>> GetActiveWorkItems(String processInstID);

	/** 
	 完成一个工作项
	 
	 @param workItemID 工作项ID
	 @param parameters 参数
	*/
	void CompleteWorkItem(String workItemID, Map<String, Object> parameters, IAssignParticipant assignParticipant);

	/** 
	 委托代办工作项
	 
	 @param user 当前用户
	 @param agentID 代理用户
	 @param workItemID 工作项ID
	*/
	void DelegateWorkItem(ShiroUser user, String agentID, String workItemID);

	/** 
	 删除工作项
	 
	 @param workItemID 工作项ID
	*/
	void DeleteWorkItem(String workItemID);

	/** 
	 代办工作项
	 
	 @param user 当前用户
	 @param workItemID 工作项ID
	*/
	void AgentWorkItem(ShiroUser user, String workItemID);

	/** 
	 领取工作项
	 
	 @param user 当前用户
	 @param workItemID 工作项ID
	*/
	void FetchWorkItem(ShiroUser user, String workItemID);

	/** 
	 退回工作项
	 
	 @param user 当前用户
	 @param workItemID 工作项ID
	*/
	void RollbackWorkItem(ShiroUser user, String workItemID);


	/** 
	 获取当前活动，可回退的活动列表
	 
	 @param processDefID 流程定义ID
	 @param activityID 当前活动ID
	 @return 
	*/
	List<Activity> GetRollbackActivityList(String processDefID, String activityID);

	/** 
	 退回工作项
	 
	 @param user 当前用户
	 @param workItemID 回退的源活动实例
	 @param parameters 回退到的目标活动
	*/
	void RollbackActivity(ShiroUser user, ActivityInst srcActInst, Activity destActivity);

	/** 
	 跳到某个工作项
	 
	 @param user 当前用户
	 @param workItemID 工作项ID
	*/
	void SkipToWorkItem(ShiroUser user, String workItemID);

	/** 
	 获取可跳过的活动列表
	 
	 @param user 当前用户
	 @param srcActInst 源活动实例
	*/
	List<Activity> GetSkipActivityList(ShiroUser user, Activity srcAct);

	/** 
	 跳到某个活动
	 @param user 当前用户
	 @param srcActInst 源活动实例
	 @param destActivity 目标活动
	*/
	void SkipToActivity(ShiroUser user, ActivityInst srcActInst, Activity destActivity);

	/** 
	 获取角色和组织参与者
	 
	 @return 
	*/
	List<Participantor> GetRoleAndOrgParticipantors();

	/** 
	 获取某参与者类型下的所有参与者
	 
	 @param parentType 父参与者类型
	 @param parentID 父参与者ID
	 @return 
	*/
	List<Participantor> GetPersonParticipantors(ParticipantorType parentType, String parentID);
}