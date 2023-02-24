package icd;

public class IcdClamlRubricDTO {
    private String kind;
    private String text;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "IcdClamlRubricDTO{" + "kind='" + kind + '\'' + ", text='" + text + '\'' + '}';
    }
}
