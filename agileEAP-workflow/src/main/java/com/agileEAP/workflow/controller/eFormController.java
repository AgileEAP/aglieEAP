package com.agileEAP.workflow.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agileEAP.infrastructure.entity.EForm;
import com.agileEAP.infrastructure.service.EFormService;
import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.workflow.service.*;
import com.agileEAP.workflow.engine.*;
import com.agileEAP.workflow.engine.utility.WFUtil;

@Controller
@RequestMapping(value = "/workflow/eform")
public class eFormController
{
	@Autowired
	private IWorkflowEngine workflowEngine;
	@Autowired
	private EFormService eFormService;
	@Autowired
	private WorkItemService workItemService;
	@Autowired
	private ProcessFormService processFormService;
	
	public final String GetSysVGetSysVarValuearValue(String varName, String defaultValue)
	{
		return "";
	}
	  /*注释 by trh 2013-11-1
   //qlj 2013.1.8改  将表单独立于流程之外
	@RequestMapping(value = "")
	public final String Index(@RequestParam(required=false) String processDefID,@RequestParam(required=false) String processInstID,@RequestParam(required=false)String workItemID, @RequestParam(required=false) String eFormID,@RequestParam String entry)
	{
			ManualActivity manualActivity = null;
			EForm eForm = null;
			if (eFormID!=null&&eFormID.length()>0)
			{
				eForm = eFormService.get(eFormID);
			}
			else if(processDefID!=null&&processDefID.length()>0&&"StartProcess".equalsIgnoreCase(entry))
			{
				Activity startActivity = workflowEngine.getWorkflowPersistence().GetStartActivity(processDefID);
				manualActivity = (ManualActivity)((workflowEngine.getWorkflowPersistence().GetOutActivities(processDefID, startActivity.getID()).get(0) instanceof ManualActivity) ? workflowEngine.getWorkflowPersistence().GetOutActivities(processDefID, startActivity.getID()).get(0) : null);
			}
			else
			{
				WorkItem workItem = workItemService.get(workItemID);
				ActivityInst activityInst = workflowEngine.getWorkflowPersistence().GetActivityInst(workItem.getActivityInstID());
				Object tempVar = workflowEngine.getWorkflowPersistence().GetActivity(workItem.getProcessID(), activityInst.getActivityDefID());
				manualActivity = (ManualActivity)((tempVar instanceof ManualActivity) ? tempVar : null);
			}
			
			if (manualActivity != null && manualActivity.geteForm() != null)
			{
				eForm =  eFormService.get(manualActivity.geteForm());
			}
			   if (eForm != null&&eForm.getContent().length()>0)
			   {
				 Form form=JsonConvert.<Form>DeserializeObject(eForm.getContent());
				   ProcessForm processForm = null;
				  Map<String, Object> values = new HashMap<String, Object>();
				   if (processInstID!=null&&processInstID.length()>0)
				   {
					   Map<String, Object> parameters = new HashMap<String, Object>();
					   parameters.put("processInstID", processInstID);
					   processForm = processFormService.search(parameters).get(0);
					   if (processForm != null)
					   {
						   parameters.clear();
						   parameters.put("id", processForm.getBizID());
						   

						   *  DataTable dt = repository.<ProcessForm>ExecuteDataTable(String.format("select * from %1$s", processForm.BizTable), parameters);
						   if (dt != null && dt.Rows.size() > 0)
						   {

							   for (DataRow row : dt.Rows)
							   {
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
								   for (var field : form.Fields) //manualActivity.Form.Fields)
								   {

									   if (field.ControlType == ControlType.SysVariable || field.ControlType == ControlType.HiddenInput)
									   {
										   switch (field.DefaultValue.ToInt())
										   {
											   case (short)SystemControlType.OrgID:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
												   Organization org = repository.<Organization>Query().FirstOrDefault(o => o.Code == workContext.User.OrgID);
												   values.SafeAdd(field.getName(), org.getName());
												   if (!DotNetToJavaStringHelper.isNullOrEmpty(field.ExtendData))
												   {
													   values.SafeAdd(field.ExtendData, workContext.User.OrgID);
												   }
												   break;
											   case (short)SystemControlType.UserID:
												   values.SafeAdd(field.getName(), workContext.User.getName());
												   if (!DotNetToJavaStringHelper.isNullOrEmpty(field.ExtendData))
												   {
													   values.SafeAdd(field.ExtendData, workContext.User.ID);
												   }
												   break;
											   case (short)SystemControlType.CurrentDate:
												   values.SafeAdd(field.getName(), new java.util.Date().ToString("yyyy-MM-dd HH:mm:ss"));
												   break;
										   }

									   }
									   else if (dt.Columns.Contains(field.getName()))
									   {
										   if (!DotNetToJavaStringHelper.isNullOrEmpty(row[field.getName()].ToSafeString()))
										   {
											   values.SafeAdd(field.getName(), row[field.getName()]);
										   }
										   else
										   {
											   if (!DotNetToJavaStringHelper.isNullOrEmpty(field.DefaultValue))
											   {
												   values.SafeAdd(field.getName(), field.DefaultValue);
											   }
										   }
									   }

								   }
							   }
						   }
					   }
				   }
				   if (processForm == null)
				   {
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
					   for (var field : form.Fields) //manualActivity.Form.Fields)
					   {
						   if (field.ControlType == ControlType.SysVariable||field.ControlType==ControlType.HiddenInput)
						   {
							   switch (field.DefaultValue.ToInt())
							   {
								   case (short)SystemControlType.OrgID:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
									   Organization org = repository.<Organization>Query().FirstOrDefault(o => o.Code == workContext.User.OrgID);
									   values.SafeAdd(field.getName(), org.getName());
									   if (!DotNetToJavaStringHelper.isNullOrEmpty(field.ExtendData))
									   {
										   values.SafeAdd(field.ExtendData, workContext.User.OrgID);
									   }
									   break;
								   case (short)SystemControlType.UserID:
									   values.SafeAdd(field.getName(), workContext.User.getName());
									   if (!DotNetToJavaStringHelper.isNullOrEmpty(field.ExtendData))
									   {
										   values.SafeAdd(field.ExtendData, workContext.User.ID);
									   }
									   break;
								   case (short)SystemControlType.CurrentDate:
									   values.SafeAdd(field.getName(), new java.util.Date().ToSafeDateTime());
									   break;
							   }
						   }
						   else if (field.ControlType != ControlType.SysVariable && field.ControlType != ControlType.HiddenInput)
						   {
							   if (!DotNetToJavaStringHelper.isNullOrEmpty(field.DefaultValue))
							   {
								   values.SafeAdd(field.getName(), field.DefaultValue);
							   }
						   }
						   else
						   {
							   // values.SafeAdd(field.Name, workContext.);
						   }
					   }
				   }
				   ViewData["Values"] = values;
				   ViewData["Form"] = form; //manualActivity.Form;
			   }
			//}
			 

		return "workflow/eform";
	}


	public final JsonResult Submit(String json)
	{
		AjaxResult tempVar = new AjaxResult();
		tempVar.Result = DoResult.Failed;
		tempVar.PromptMsg = "操作失败";
		AjaxResult ajaxResult = tempVar;
		ProcessForm processForm = null;
		try
		{
			String processDefID = Request.Form["processDefID"];
			String entry = Request.Form["Entry"];
			String processInstID = Request.Form["processInstID"];
			String workItemID = Request.Form["workItemID"];
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			UnitOfWork.<ProcessDef>ExecuteWithTrans(() => // string.Empty; // TODO: Initialize to an appropriate value
			{
				if (!DotNetToJavaStringHelper.isNullOrEmpty(processDefID) && entry.EqualIgnoreCase("StartProcess"))
				{
					processInstID = workflowEngine.CreateAProcess(processDefID);
					workflowEngine.StartAProcess(processInstID, null);
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
					WorkItem wi = repository.<WorkItem>Query().FirstOrDefault(w => processInstID.equals(w.ProcessInstID));
					workItemID = wi.ID;
					AddActionLog(wi, DoResult.Success, String.format("启动流程实像%1$s", processInstID));
				}
				java.util.HashMap<String, Object> formValues = JsonConvert.<java.util.HashMap<String, Object>>DeserializeObject(json);
				String table = Request.Form["DataSource"];
				String cmdText = "";
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
				processForm = repository.<ProcessForm>Query().FirstOrDefault(pf => processInstID.equals(pf.ProcessInstID));
				if (processForm != null)
				{
					StringBuilder sbUpdateValues = new StringBuilder();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
					for (var item : formValues)
					{
						if (sbUpdateValues.length() > 0)
						{
							sbUpdateValues.append(",");
						}
							sbUpdateValues.append(String.format("%1$s = :%1$s", item.getKey()));
					}
					cmdText = String.format("update %1$s set %2$s where ID ='%3$s'", table, sbUpdateValues.toString(), processForm.BizID);
				}
				else
				{
					formValues.SafeAdd("ID", IdGenerator.NewComb().ToSafeString());
					formValues.SafeAdd("Applicant", workContext.User.ID);
					formValues.SafeAdd("ApplicantName", workContext.User.getName());
					formValues.SafeAdd("ApplyTime", new java.util.Date());
					StringBuilder sbFields = new StringBuilder();
					StringBuilder sbValues = new StringBuilder();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
					for (var item : formValues)
					{
						sbFields.append(item.getKey()).Append(",");
						sbValues.append(":").Append(item.getKey()).Append(",");
					}
					ProcessForm tempVar2 = new ProcessForm();
					tempVar2.ID = IdGenerator.NewComb().toString();
					tempVar2.BizID = formValues.<String>GetSafeValue("ID");
					tempVar2.BizTable = table;
					tempVar2.CreateTime = new java.util.Date();
					tempVar2.Creator = workContext.User.ID;
					tempVar2.ProcessInstID = processInstID;
					tempVar2.KeyWord = sbValues.toString();
					processForm = tempVar2;
					cmdText = String.format("insert into %1$s(%2$s) values(%3$s)", table, DotNetToJavaStringHelper.trimEnd(sbFields.toString(), ','), DotNetToJavaStringHelper.trimEnd(sbValues.toString(), ','));
				}
				workflowEngine.Persistence.Repository.<WorkItem>ExecuteSql(cmdText, formValues);
				workflowEngine.Persistence.Repository.SaveOrUpdate(processForm);
				workflowEngine.CompleteWorkItem(workItemID, formValues);
			}
		   );
			ajaxResult.Result = DoResult.Success;
			ajaxResult.RetValue = processForm.BizID;
			ajaxResult.PromptMsg = String.format("完成工作流实例%1$s工作项%2$s", processForm.ProcessInstID, workItemID);
			//记录操作日志
			AddActionLog(processForm, ajaxResult.Result, ajaxResult.PromptMsg);
		}
		catch (RuntimeException ex)
		{
			ajaxResult.Result = DoResult.Failed;
			//记录操作日志
			AddActionLog(processForm, ajaxResult.Result, String.format("完成工作流实例%1$s工作项失败", processForm.ProcessInstID));
			log.Error(ex);
		}

		return Json(ajaxResult);

	}

	public final JsonResult Rollback(String json)
	{
		AjaxResult tempVar = new AjaxResult();
		tempVar.Result = DoResult.Failed;
		tempVar.PromptMsg = "操作失败";
		AjaxResult ajaxResult = tempVar;
		String workItemID = Request.QueryString["workItemID"];
		String actionMessage = String.format("退回工作流实例工作项%1$s", workItemID);
		try
		{
			//回退工作项
			workflowEngine.RollbackWorkItem(workContext.User, workItemID);

			ajaxResult.Result = DoResult.Success;
			ajaxResult.PromptMsg = actionMessage;
		}
		catch (RuntimeException ex)
		{
			ajaxResult.Result = DoResult.Failed;
			log.Error(actionMessage, ex);
		}
		finally
		{
			AddActionLog<WorkItem>(actionMessage, ajaxResult.Result);
		}

		return Json(ajaxResult);
	}


	public final JsonResult GetDict()
	{
		AjaxResult tempVar = new AjaxResult();
		tempVar.Result = DoResult.Failed;
		tempVar.PromptMsg = "操作失败";
		AjaxResult ajaxResult = tempVar;
		String id = Request.Form["DataSource"];
		String Name = Request.Form["Name"];
		try
		{
			java.util.HashMap<String, Object> dictItems = new java.util.HashMap<String, Object>();
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			java.util.List<DictItem> dicts = repository.<DictItem>Query().Where(d => id.equals(d.DictID)).ToList();
			if (dicts != null && dicts.size()() > 0)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
				for (var dict : dicts)
				{
					dictItems.put(dict.getValue(), dict.getText());
				}
			}
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: This type of object initializer has no direct Java equivalent:
			var retValue = new { Name = Name, dictItems = dictItems };
			ajaxResult.Result = DoResult.Success;
			ajaxResult.RetValue = retValue;
			ajaxResult.PromptMsg = "操作成功";
		}
		catch (RuntimeException ex)
		{
			ajaxResult.PromptMsg = "操作失败";
			log.Error(ex);
		}
		return Json(ajaxResult);
	}

	public final JsonResult GetFileInfo()
	{
		AjaxResult tempVar = new AjaxResult();
		tempVar.Result = DoResult.Failed;
		tempVar.PromptMsg = "操作失败";
		AjaxResult ajaxResult = tempVar;
		String bizID = Request.Form["BizID"];
		String Name = Request.Form["Name"];
		try
		{
			java.util.List<String> fileItems = new java.util.ArrayList<String>();
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			java.util.List<UploadFile> fileInfos = repository.<UploadFile>Query().Where(d => bizID.equals(d.BizID)).ToList();
			if (fileInfos != null && fileInfos.size()() > 0)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
				for (var fileInfo : fileInfos)
				{
					fileItems.add(fileInfo.FilePath);
				}
			}
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: This type of object initializer has no direct Java equivalent:
			var retValue = new { Name = Name, fileItems = fileItems };
			ajaxResult.Result = DoResult.Success;
			ajaxResult.RetValue = retValue;
			ajaxResult.PromptMsg = "操作成功";
		}
		catch (RuntimeException ex)
		{
			ajaxResult.PromptMsg = "操作失败";
			log.Error(ex);
		}
		return Json(ajaxResult);
	}

	public final ContentResult Upload(System.Web.HttpPostedFileBase FileData, String folder)
	{
		String fileName = "";
		if (null != FileData)
		{
			try
			{
				fileName = Path.GetFileName(FileData.FileName); //获得文件名
				String ext = Path.GetExtension(FileData.FileName); //获得文件扩展名
				String processInstID = Request.Form["processInstID"];
				String saveName = Request.Form["name"] + "$" + fileName; //实际保存文件名
				String path = System.Configuration.ConfigurationManager.AppSettings["FileDirectory"];
				String filePath = Request.MapPath(path + "/Workflow/");
				if (!Directory.Exists(filePath))
				{
					Directory.CreateDirectory(filePath);
				}
				FileData.SaveAs(filePath + saveName);
				UploadFile tempVar = new UploadFile();
				tempVar.ID = IdGenerator.NewGuid().ToSafeString();
				tempVar.UniqueName = saveName;
				tempVar.FileName = fileName;
				tempVar.CreateTime = new java.util.Date();
				tempVar.FilePath = path + "/Workflow/" + saveName;
				tempVar.FileType = 0;
				tempVar.Creator = workContext.User.ID;
				tempVar.BizID = processInstID;
				UploadFile uploadFile = tempVar;
				repository.SaveOrUpdate(uploadFile);
			}
			catch (RuntimeException ex)
			{
				fileName = ex.getMessage();
			}
		}
		return Content(fileName);
	}

	public final void download(String Path, String fileName)
	{
		System.IO.Stream iStream = null;
		byte[] buffer = new byte[10000];
		int length;
		long dataToRead;
		String filepath = Server.MapPath(Path);
		try
		{
			iStream = new System.IO.FileStream(filepath, System.IO.FileMode.Open, System.IO.FileAccess.Read, System.IO.FileShare.Read);
			dataToRead = iStream.getLength();
			Response.ContentType = "application/octet-stream";
			Response.AddHeader("Content-Disposition", "attachment; filename=" + System.Web.HttpUtility.UrlEncode(fileName)); //System.Text.UTF8Encoding.UTF8.GetBytes(FileName)
			while (dataToRead > 0)
			{
				if (Response.IsClientConnected)
				{
					length = iStream.Read(buffer, 0, 10000);
					Response.OutputStream.Write(buffer, 0, length);
					Response.Flush();
					buffer = new byte[10000];
					dataToRead = dataToRead - length;
				}
				else
				{
					dataToRead = -1;
				}
			}
		}
		catch (RuntimeException ex)
		{
			log.Error(ex);

		}
		finally
		{
			if (iStream != null)
			{
				iStream.Close();
			}
		}
	}

	public final JsonResult CreateJSFile()
	{
		AjaxResult tempVar = new AjaxResult();
		tempVar.Result = DoResult.Failed;
		tempVar.PromptMsg = "操作失败";
		AjaxResult ajaxResult = tempVar;
		try
		{
			String ProcessDef = Request.Form["ProcessDefID"];
			String ActivityID = Request.Form["ActivityID"];
			String Content = Request.Form["jsContent"];
			String path = Request.MapPath("/Plugins/FormDesigner/Scripts/");
			String name = ProcessDef + ActivityID + ".js";
			if (!System.IO.File.Exists(path + name))
			{
				FileStream fs1 = new FileStream(path + name, FileMode.Create, FileAccess.Write);
				StreamWriter sw = new StreamWriter(fs1);
				sw.WriteLine(Content); //要写入的信息。
				sw.Close();
				fs1.Close();
			}
			else
			{
				FileStream fs = new FileStream(path + name, FileMode.Open, FileAccess.Write);
				StreamWriter sr = new StreamWriter(fs);
				sr.WriteLine(Content); //开始写入值
				sr.Close();
				fs.Close();
			}
			ajaxResult.Result = DoResult.Success;
			ajaxResult.RetValue = path + name;
			ajaxResult.PromptMsg = "操作成功";
		}
		catch (RuntimeException ex)
		{
			ajaxResult.PromptMsg = "操作失败";
			log.Error(ex);
		}
		return Json(ajaxResult);
	}
  */
}