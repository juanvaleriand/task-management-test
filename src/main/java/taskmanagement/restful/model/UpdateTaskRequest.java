package taskmanagement.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTaskRequest {
    @JsonIgnore
    private Integer id;

    @Size(max = 100)
    private String title;

    private String description;

    private Boolean completed;
}
