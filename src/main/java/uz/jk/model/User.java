package uz.jk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User extends BaseModel{
    private Long chatId;
    private String firstname;
    private String phoneNumber;
    private UserState state;
    private Location location;
}
