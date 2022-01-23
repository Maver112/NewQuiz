package com.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.entity.Question;
import com.entity.QuestionOption;
import com.entity.Subject;
import com.repository.QuestionRepository;
import com.repository.SubjectRepository;
import com.repository.UserRepository;

import java.util.Arrays;

@Component
@Profile("dev")
public class InitialData implements ApplicationListener<ApplicationReadyEvent> {

    private SubjectRepository subjectRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public InitialData(SubjectRepository subjectRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        initData();
    }

    public void initData() {

        questionRepository.deleteAll();
        subjectRepository.deleteAll();
        userRepository.deleteAll();

        Subject CS101 = new Subject();
        CS101.setSubjectCode("CS101");
        CS101.setTitle("Komputery i Technologia");

        subjectRepository.save(CS101);


        Subject MTH101 = new Subject();
        MTH101.setSubjectCode("MTH101");
        MTH101.setTitle("Calculus and Analytical Geometry");

        subjectRepository.save(MTH101);


        questionRepository.save(createQuestion(
                CS101,
                "W którym roku została uruchomiona pierwsza sieć ?",
                new String[]{
                        "1969",
                        "1983",
                        "1987",
                        "1996"
                },
                0
        ));

        questionRepository.save(createQuestion(
                CS101,
                "Justowanie jest to: ",
                new String[]{
                        "rozmieszczenie tekstu równomiernie w linii tak, że wyrównany jest do obu marginesów jednocześnie ",
                        "jedno z określeń na wędrowanie po Internecie",
                        "jeden z układów grafiki względem tekstu",
                        "ustawienie tekstu w środku linii"
                },
                0
        ));

        questionRepository.save(createQuestion(
                CS101,
                "co to jest pamięć ROM ?",
                new String[]{
                        "miejsce przechowywania wszystkich danych",
                        "program do zapisywania informacji",
                        "pamięć tylko do odczytu, która umożliwia rozpoczęcie pracy komputera, rozruch poszczególnych podzespołów i współpracę procesów",
                        "Pamięć która wykorzystywana jest na dysku twardym"
                },
                2
        ));

        questionRepository.save(createQuestion(
                CS101,
                "W którym roku powstał pierwszy komputer i jak się nazywał?",
                new String[]{
                        "w 1945 r - Eniac",
                        "w 1943 r - Colossus",
                        "w 1939 r - Atanasoff-Berry ABC",
                        "w 1941 r - maszyna Zusego Z3"
                },
                2
        ));

        questionRepository.save(createQuestion(
                CS101,
                "Jak będzie wyglądała formuła licząca średnią liczb z komórek od A1 do A5?",
                new String[]{
                        "=średnia(A1;A5)",
                        "=średnia(A1+A5)",
                        "średnia(A1;A5)",
                        "=średnia(A1:A5)"
                },
                3
        ));

        questionRepository.save(createQuestion(
                CS101,
                "Procesor to:",
                new String[]{
                        "program nadzorujący pracę sieci",
                        "układ elektroniczny odpowiedzialny za wyświetlanie obrazu na ekranie monitora",
                        "układ elektroniczny odpowiedzialny za realizację najważniejszych funkcji komputera",
                        "urządzenie umożliwiające podłączenie komputerów do sieci"
                },
                2
        ));

        questionRepository.save(createQuestion(
                CS101,
                "Która nazwa firmy nie jest skrótowcem?",
                new String[]{
                        "CERN",
                        "ACER",
                        "VARTA",
                        "INTEL"
                },
                1
        ));

        questionRepository.save(createQuestion(
                CS101,
                "Która część adresu e-mail gim1@chelm.pl jest identyfikatorem?",
                new String[]{
                        "chelm",
                        "@",
                        "gim1",
                        "pl"
                },
                2
        ));

        questionRepository.save(createQuestion(
                CS101,
                "Który typ rekordu DNS jest używany przez serwery pocztowe w celu określenia miejsca wysyłania wiadomości e-mail?",
                new String[]{
                        "rekord CNAME",
                        "rekord A",
                        "rekord SRV",
                        "rekord MX"
                },
                3
        ));

        questionRepository.save(createQuestion(
                CS101,
                "Jak nazywał się pierwszy wirus komputerowy w systemie DOS?",
                new String[]{
                        "wirus Melissa",
                        "wirus Storm Worm",
                        "wirus I Love You",
                        "wirus Brain"
                },
                3
        ));

    }

    private Question createQuestion(Subject subject, String questionText, String[] optionsText, int ansIndex) {
        Question question = new Question();
        question.setQuestion(questionText);
        question.setSubject(subject);
        question.setTotalMarks(1);

        QuestionOption option1 = new QuestionOption();
        option1.setQuestion(question);
        option1.setQuestionOption(optionsText[0]);

        QuestionOption option2 = new QuestionOption();
        option2.setQuestion(question);
        option2.setQuestionOption(optionsText[1]);

        QuestionOption option3 = new QuestionOption();
        option3.setQuestion(question);
        option3.setQuestionOption(optionsText[2]);

        QuestionOption option4 = new QuestionOption();
        option4.setQuestion(question);
        option4.setQuestionOption(optionsText[3]);

        question.setQuestionOptions(Arrays.asList(option1, option2, option3, option4));

        question.setCorrectAnswer(question.getQuestionOptions().get(ansIndex));

        return question;
    }
}
