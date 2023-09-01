package boss.dto.request;

import lombok.Builder;

@Builder
public record GroupRequest(String groupName,String imageLink,String description){

}
