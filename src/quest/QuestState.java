package quest;

import java.util.ArrayList;
import java.util.List;

public class QuestState {
	private ArrayList<String> description = new ArrayList<String>();
	private String beginMessage = null;
	private String completeMessage = null;
	private QuestState nextState;
	private List<QuestType> questObjective = new ArrayList<QuestType>();
	private boolean isLast = false;
	private boolean isActive = false;
	public void setNextState(QuestState state) {
		this.nextState = state;
	}
	public QuestState getNextState() {
		return this.nextState;
	}
	QuestState(boolean isLastStage, String bMessage, String cMessage) {
		beginMessage = bMessage;
		completeMessage = cMessage;
		isLast = isLastStage;
	}
	
	public void addObjective(QuestType newType) {
		questObjective.add(newType);
	}
	public List<QuestType> getQuestObjective() {
		return questObjective;
	}
	public String getBeginMessage() {
		return beginMessage;
	}
	
	public String getCompleteMessage() {
		return completeMessage;
	}
	public boolean getIsLast() {
		return isLast;
	}
	public boolean checkCompletion() {
		for (QuestType quest : questObjective) {
			if (!quest.checkCompletion()) {
				return false;
			}
		}
		return true;
	}

	

}
