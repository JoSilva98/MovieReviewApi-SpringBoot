package mindswap.academy.moviereview_api.command.movie.genre;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GenreUpdateDto {
    @NotEmpty
    @Size(max = 50,message = " max size 50")
    private String value;
}
