package com.vic.lombak.builder;


import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

public class TrafficLog {
    private String agvId,jobId,pushId;
    private Stage stage;
    private String content;

    @Builder(builderMethodName = "bl")
    private TrafficLog(@NonNull String agvId, @NonNull Stage stage, String jobId, String pushId, @Singular(value = "content") List<String> content){
        this.agvId = agvId;
        this.stage = stage;
        this.jobId = jobId;
        this.pushId = pushId;
//        this.content = content;
    }

    public static TrafficLog.TrafficLogBuilder premove() { return new TrafficLogBuilder().stage(Stage.premove); }

    @Override
    public String toString() {
        return agvId + "|" +
                stage.name() + "|" +
                jobId + "|" +
                pushId + "|" +
                content;
    }
    public enum Stage{
        init,
        premove,
        calculate,
        request,
    }
}
