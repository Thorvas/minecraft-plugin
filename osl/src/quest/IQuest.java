package quest;

import java.util.UUID;

public interface IQuest {
	public void Activate();
	public void Deactivate();
	public void completeQuest();
	public QuestState getActualState();
	public void setPlayer(UUID p);
	public boolean checkIfActive();
	public void changeActualState();
}
