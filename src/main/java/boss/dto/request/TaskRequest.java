package boss.dto.request;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record TaskRequest(String taskName, String taskText, ZonedDateTime deadLine){

}
