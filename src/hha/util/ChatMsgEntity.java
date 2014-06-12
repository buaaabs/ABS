
package hha.util;

public class ChatMsgEntity {
    private static final String TAG = ChatMsgEntity.class.getSimpleName();

    private String text;

    private int layoutID;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    public ChatMsgEntity() {
    }

    public ChatMsgEntity(String name, String date, String text, int layoutID) {
        this.text = text;
        this.layoutID = layoutID;
    }

}
