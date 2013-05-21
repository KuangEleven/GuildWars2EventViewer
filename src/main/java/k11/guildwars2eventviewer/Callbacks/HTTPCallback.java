package k11.guildwars2eventviewer.Callbacks;

public interface HTTPCallback {
	void success(String output);
	void failure(Exception e);
}
