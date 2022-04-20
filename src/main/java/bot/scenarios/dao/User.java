package bot.scenarios.dao;


import org.springframework.stereotype.Component;

@Component
public class User {

    private String answerPoll;
    private String userId;
    private String fio;
    private int age;
    private String placeOfResidence;
    private String placeOfStudy;
    private String career;

    public String getAnswerPoll() {
        return answerPoll;
    }

    public void setAnswerPoll(String answerPoll) {
        this.answerPoll = answerPoll;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public String getPlaceOfStudy() {
        return placeOfStudy;
    }

    public void setPlaceOfStudy(String placeOfStudy) {
        this.placeOfStudy = placeOfStudy;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    private String purpose;
    

}
