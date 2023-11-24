package taskmanagement.restful.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchTaskRequest {
    private String title;

    private String description;

    private Boolean completed;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;
}
