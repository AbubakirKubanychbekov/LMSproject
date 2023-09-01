package boss.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record TaskResponse(Long id, String taskName, String taskText, ZonedDateTime deadLine){

    public TaskResponse {
    }
}
