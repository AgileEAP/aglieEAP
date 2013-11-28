package com.agileEAP.workflow.definition;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 活动参与者
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Participant {
	/**
	 * 参与者方式:rblOrgization组织机构、角色或用户,rblWFStarter流程启动者
	 */
	private ParticipantType participantType = ParticipantType.Participantor;

	public final ParticipantType getParticipantType() {
		return participantType;
	}

	public final void setParticipantType(ParticipantType value) {
		participantType = value;
	}

	/**
	 * 是否允许前驱活动根据如上参与者列表指派该活动的参与者
	 */
	private boolean allowAppointParticipants;

	public final boolean getAllowAppointParticipants() {
		return allowAppointParticipants;
	}

	public final void setAllowAppointParticipants(boolean value) {
		allowAppointParticipants = value;
	}

	/**
	 * 参与者
	 */
	@XmlElementWrapper(name = "participantors")
	@XmlElement(name = "participantor")
	private List<Participantor> participantors;

	public final List<Participantor> getParticipantors() {
		return participantors;
	}

	public final void setParticipantors(List<Participantor> value) {
		participantors = value;
	}

	private String participantValue;

	public final String getParticipantValue() {
		return participantValue;
	}

	public final void setParticipantValue(String value) {
		participantValue = value;
	}

	/**
	 * 从活动中获取
	 */
	private String specialActivityID;

	public final String getSpecialActivityID() {
		return specialActivityID;
	}

	public final void setSpecialActivityID(String value) {
		specialActivityID = value;
	}

	/**
	 * 从相关数据获取
	 */
	private String specialPath;

	public final String getSpecialPath() {
		return specialPath;
	}

	public final void setSpecialPath(String value) {
		specialPath = value;
	}
}