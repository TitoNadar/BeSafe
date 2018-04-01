package com.example.tito.womensecurity.Modal;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("groupID")
	private int groupID;

	@SerializedName("MessageIDs")
	private String messageIDs;

	@SerializedName("status")
	private String status;

	public void setGroupID(int groupID){
		this.groupID = groupID;
	}

	public int getGroupID(){
		return groupID;
	}

	public void setMessageIDs(String messageIDs){
		this.messageIDs = messageIDs;
	}

	public String getMessageIDs(){
		return messageIDs;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"groupID = '" + groupID + '\'' + 
			",messageIDs = '" + messageIDs + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}