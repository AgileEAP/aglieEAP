package com.agileEAP.workflow.engine;

import java.util.List;

import com.agileEAP.workflow.definition.Participantor;
import com.agileEAP.workflow.definition.ParticipantorType;

public interface IParticipantorService {
	
	/** 
	 获取角色和组织参与者
	 
	 @return 
	*/
	List<Participantor> getRoleAndOrgParticipantors();
	
	/** 
	 获取某参与者类型下的所有参与者
	 
	 @param parentType 父参与者类型
	 @param parentID 父参与者ID
	 @return 
	*/
	List<Participantor> getPersonParticipantors(ParticipantorType parentType, String parentID);
}
