package mindswap.academy.moviereview_api.command.movie.actor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ActorDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotEmpty
    @Pattern(regexp = "((https?:\\/\\/)?[^\\s.]+\\.[\\w][^\\s]+)", message = "You need a link")
    private String image;
    @NotEmpty
    @Size(max = 50,message = " max size 50")
    private String name;
}
