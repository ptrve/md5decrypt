package Utils;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("task")
    private String task;
    @SerializedName("code")
    private String code;
    @SerializedName("crypt")
    private String crypt;

    public Message(String code, String task, String crypt) {
        this.task = task;
        this.code = code;
        this.crypt = crypt;
    }

    public Message(String code, String task) {
        this(code, task, "");
    }

    public Message(String code) {
        this(code, "", "");
    }

    public Message() {};

    public void setTask(String task) {
        this.task = task;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCrypt(String crypt) {
        this.crypt = crypt;
    }

    public String getTask() {
        return task;
    }

    public String getCode() {
        return code;
    }

    public String getCrypt() {
        return crypt;
    }

    @Override
    public String toString() {
        return code + " " + task + " " + crypt;
    }
}
