package com.pcdeng.notification;

import java.util.Random;

public class NotificationContent {
    /**
     * 通知标题
     */
    private String _title;
    /**
     *  通知详细内容
     */
    private String _content;
    /**
     * 铃声名称（不含 .mp3 后缀），
     * task 相关的通知对应的是 quantum_bell
     * chat 相关的通知对应的 quantum_ding
     */
    private String _sound;

    private String _channelId;

    public NotificationContent() {

    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getTitle() {
        return _title;
    }

    public void setContent(String content) {
        _content = content;
    }

    public String getContent() {
        return _content;
    }

    public void setSound(String sound) {
        _sound = sound;
    }

    public String getSound() {
        return _sound;
    }

    public Integer getId() {
        Random r = new Random();
        return r.nextInt();
    }

    public void setChannelId(String channelId) {
        _channelId = channelId;
    }

    public String getChannelId() {
        return _channelId;
    }
}
