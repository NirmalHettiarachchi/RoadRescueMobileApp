package models;

public class FAQModel {

    private String faqId;
    private String question;
    private String answer;

    public FAQModel(String faqId, String question, String answer) {
        this.faqId = faqId;
        this.question = question;
        this.answer = answer;
    }

    public FAQModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public FAQModel() {
    }

    public String getFaqId() {
        return faqId;
    }

    public void setFaqId(String faqId) {
        this.faqId = faqId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "FAQModel{" +
                "faqId='" + faqId + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
