package icd;

import java.util.List;


public class IcdClamlDTO {
    private String code;
    private String usage;

    private String superClass;
    private List<String> subClasses;

    private List<IcdClamlRubricDTO> rubrics;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public List<String> getSubClasses() {
        return subClasses;
    }

    public void setSubClasses(List<String> subClasses) {
        this.subClasses = subClasses;
    }

    public List<IcdClamlRubricDTO> getRubrics() {
        return rubrics;
    }

    public void setRubrics(List<IcdClamlRubricDTO> rubrics) {
        this.rubrics = rubrics;
    }


    @Override
    public String toString() {
        return "IcdClamlDTO{" + "code='" + code + '\'' + ", usage='" + usage + '\'' + ", superClass='" + superClass + '\''
               + ", subClasses=" + subClasses + ", rubrics=" + rubrics + '}';
    }
}
