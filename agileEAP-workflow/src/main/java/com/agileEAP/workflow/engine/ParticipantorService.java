package com.agileEAP.workflow.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agileEAP.workflow.definition.Participantor;
import com.agileEAP.workflow.definition.ParticipantorType;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.entity.Organization;
import com.agileEAP.security.entity.Role;
import com.agileEAP.security.repository.RoleRepository;
import com.agileEAP.security.repository.OperatorRepository;
import com.agileEAP.security.repository.OrganizationRepository;

@Component("participantorService")
public class ParticipantorService implements IParticipantorService {
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private OperatorRepository operatorRepository;
	@Autowired
	private OrganizationRepository organizationRepository;
	
	/** 
	 获取角色和组织参与者
	 
	 @return 
	*/
	@Override
	public List<Participantor> getRoleAndOrgParticipantors() {
	    List<Participantor> participantors = new ArrayList<Participantor>();
	    
	    Map<String,Object> parameters=new HashMap<String,Object>();
	    List<Role> roles=roleRepository.search(parameters);
		int index = 0;
	    for(Role role:roles)
	    {
			Participantor participantor = new Participantor();
			participantor.setID(role.getId());
			participantor.setParticipantorType(ParticipantorType.Role);
			participantor.setSortOrder(++index);
			participantor.setName(role.getName());
			participantor.setParentID(role.getOrgID());
			
			participantors.add(participantor);
	    }
	    
	    List<Organization> organizations=organizationRepository.search(parameters);
	    index = 0;
	    for(Organization organization:organizations)
	    {
			Participantor participantor = new Participantor();
			participantor.setID(organization.getId());
			participantor.setParticipantorType(ParticipantorType.Org);
			participantor.setSortOrder(++index);
			participantor.setName(organization.getName());
			participantor.setParentID(organization.getParentID());
			
			participantors.add(participantor);
	    }
		return participantors;
	}

	/** 
	获取某参与者类型下的所有参与者
	
	@param parentType 父参与者类型
	@param parentID 父参与者ID
	@return 
	*/
	@Override
	public List<Participantor> getPersonParticipantors(
			ParticipantorType parentType, String parentID) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (parentType == ParticipantorType.Role)
		{
			parameters.put("_rawsql", String.format("ID in (select b.ObjectID from OM_ObjectRole b where b.RoleID='%1$s')", parentID));
		}
		else if (parentType == ParticipantorType.Org)
		{
			parameters.put("_rawsql", String.format("ID in (select b.EmployeeID from OM_EmployeeOrg b where b.OrgID='%1$s')", parentID));
		}

		List<Operator> operators=operatorRepository.search(parameters);
        int index = 0;
        List<Participantor> participantors=new ArrayList<Participantor>();
        for(Operator operator:operators)
        {
    		Participantor participantor = new Participantor();
    		participantor.setID(operator.getId());
    		participantor.setName(operator.getName());
    		participantor.setParticipantorType(ParticipantorType.Person);
    		participantor.setSortOrder(++index);
    		participantor.setParentID(parentID);
    		
    		participantors.add(participantor);
        }
		
		return participantors;
	}
}
