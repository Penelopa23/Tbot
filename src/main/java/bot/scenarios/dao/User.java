package bot.scenarios.dao;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class User {

    private String answerPoll;
    private long userId;
    private String fio;
    private Integer age;
    private String placeOfResidence;
    private String placeOfStudy;
    private String career;
    private String whatThePoint;
    private List<String> productCreationExperience;
    private String url;

    public String getAnswerPoll() {
        return answerPoll;
    }

    public void setAnswerPoll(String answerPoll) {
        this.answerPoll = answerPoll;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public String getWhatThePoint() {
        return whatThePoint;
    }

    public void setWhatThePoint(String whatThePoint) {
        this.whatThePoint = whatThePoint;
    }

    public List<String> getProductCreationExperience() {
        return productCreationExperience;
    }

    public void setProductCreationExperience(List<String> productCreationExperience) {
        this.productCreationExperience = productCreationExperience;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
