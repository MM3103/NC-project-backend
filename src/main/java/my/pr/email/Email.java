package my.pr.email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Email {
    private String to;
    private String title;
    private String content;

    public Email(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}