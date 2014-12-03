package responseObjects;

import objects.Question;
import objects.Answer;

public class QuestionAnswer {

	private Question question;
	private Answer answer;

	public QuestionAnswer() {
	}

	public QuestionAnswer(Question Q, Answer A) {
		this.question = Q;
		this.answer = A;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

}
