package it.polimi.db2.gamifiedmarketing.application;

import it.polimi.db2.gamifiedmarketing.application.entity.*;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.Sex;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.SubStatus;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.UserRole;
import it.polimi.db2.gamifiedmarketing.application.repository.BadWordRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

@SpringBootApplication

public class GamifiedMarketingApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadWordRepository badWordRepository;

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(GamifiedMarketingApplication.class, args);
    }

    @PostConstruct
    public void init() {

        /*
         * DB initialization for testing
         */

        // --- Users --- \\
        User user1 = User.builder().firstName("Matteo").lastName("Giordano").email("matteo@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.USER).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User user2 = User.builder().firstName("Andrea").lastName("Bovo").email("andrea@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.USER).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User user3 = User.builder().firstName("Giovanni").lastName("Ciriello").email("giovanni@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.USER).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // --- Admins --- \\
        User admin1 = User.builder().firstName("Admin").lastName("1").email("admin1@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.ADMIN).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User admin2 = User.builder().firstName("Admin").lastName("2").email("admin2@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.ADMIN).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();

        // --- Products --- \\
        Product product1 = Product.builder().name("Apple AirPods con custodia di ricarica con cavo").description("AirPods con custodia di ricarica: più di 24 ore di ascolto, fino a 18 ore di conversazione; AirPods (singola ricarica): Fino a 5 ore di ascolto, fino a 3 ore di conversazione; 15 minuti di ricarica nella custodia equivalgono a 3 ore di ascolto aggiuntive o fino a 2 ore di conversazione.").imageUrl("https://images-na.ssl-images-amazon.com/images/I/71NTi82uBEL._AC_SL1500_.jpg").date(LocalDate.now()).questions(new ArrayList<>()).submissions(new ArrayList<>()).reviews(new ArrayList<>()).build();
        admin1.addProduct(product1);

        Product product2 = Product.builder().name("Apple iPhone 12").description("Display Super Retina XDR da 6,1. Ceramic Shield, più duro di qualsiasi vetro per smartphone. 5G per download velocissimi e streaming ad alta qualità. A14 Bionic, il chip più veloce mai visto su uno smartphone").imageUrl("https://images-na.ssl-images-amazon.com/images/I/71MtcgbTdXL._AC_SL1500_.jpg").date(LocalDate.of(2021, Month.NOVEMBER, 18)).questions(new ArrayList<>()).submissions(new ArrayList<>()).reviews(new ArrayList<>()).build();
        admin2.addProduct(product2);

        Product product3 = Product.builder().name("Apple Magic Keyboard").description("Tasti di dimensioni standard retroilluminati, con meccanismo a forbice ed escursione di 1 mm, per scrivere in modo preciso e silenzioso. Il trackpad è progettato per i gesti Multi-Touch, e ti permette di controllare il nuovo cursore in iPadOS.").imageUrl("https://images-na.ssl-images-amazon.com/images/I/71fYV0JMoYL._AC_SL1500_.jpg").date(LocalDate.of(2021, Month.JANUARY, 15)).questions(new ArrayList<>()).submissions(new ArrayList<>()).reviews(new ArrayList<>()).build();
        admin2.addProduct(product3);

        // --- Reviews --- \\
        Product[] products = {product1, product2, product3};
        String[] textReviews = {
                "First of all, I purchased this directly from Amazon. Look out for and you are guaranteed to receive a legitimate product. Other reviewers who are claiming that the product is counterfeit are probably purchasing from third party sellers.",
                "I am someone who does not enjoy the in-ear style earbuds. The ones with rubber tips that you jam into your ear canals. They are usually uncomfortable and give me headaches. For this reason, I have stuck with the traditional EarPods and AirPods design..",
                "i have found that after the airpod shows as being connected and is active on my Google Pixel, if I close the wireless case, the airpod is disconnected. Why is this happening? I thought this was compatible with Android..",
        };
        for (String textReview: textReviews){
            for(Product product: products){
                Review review = Review.builder().text(textReview).build();
                product.addReview(review);
            }
        }


        // --- Questions --- \\
        Question question1_1 = Question.builder().title("Do you like it?").subtitle("Subtitle 1").responses(new ArrayList<>()).build();
        Question question2_1 = Question.builder().title("Would you buy it?").subtitle("Subtitle 2").responses(new ArrayList<>()).build();
        product1.addQuestion(question1_1);
        product1.addQuestion(question2_1);

        Question question1_2 = Question.builder().title("Do you like it?").subtitle("Subtitle 1").responses(new ArrayList<>()).build();
        Question question2_2 = Question.builder().title("Would you buy it?").subtitle("Subtitle 2").responses(new ArrayList<>()).build();
        product2.addQuestion(question1_2);
        product2.addQuestion(question2_2);

        Question question1_3 = Question.builder().title("Do you like it?").subtitle("Subtitle 1").responses(new ArrayList<>()).build();
        Question question2_3 = Question.builder().title("Would you buy it?").subtitle("Subtitle 2").responses(new ArrayList<>()).build();
        product3.addQuestion(question1_3);
        product3.addQuestion(question2_3);

        // Here (persisting the admin) is exampled the use case of the admin that create a new product with related questions.
        userRepository.save(admin1);
        userRepository.save(admin2);

        // --- Responses --- \\
        Response response1_1 = Response.builder().body("yes1").build();
        Response response2_1 = Response.builder().body("no1").build();
        question1_1.addResponse(response1_1);
        question2_1.addResponse(response2_1);


        Response response1_2 = Response.builder().body("yes2").build();
        Response response2_2 = Response.builder().body("no2").build();
        question1_1.addResponse(response1_2);
        question2_1.addResponse(response2_2);

        // --- Submissions --- \\
        Submission sub_user1 = Submission.builder().age(12).expertiseLevel(ExpertiseLevel.LOW).sex(Sex.MALE).submissionStatus(SubStatus.CONFIRMED).responses(new ArrayList<>()).build();
        user1.addSubmission(sub_user1);
        sub_user1.setProduct(product1);
        sub_user1.addResponse(response1_1);
        sub_user1.addResponse(response2_1);

        Submission sub_user2 = Submission.builder().age(24).expertiseLevel(ExpertiseLevel.HIGH).sex(Sex.FEMALE).submissionStatus(SubStatus.CANCELED).responses(new ArrayList<>()).build();
        user2.addSubmission(sub_user2);
        sub_user2.setProduct(product1);
        sub_user2.addResponse(response1_2);
        sub_user2.addResponse(response2_2);

        // Here (persisting the user) is exampled the use case of the user that submit/cancel a new submission with related answers.
        userRepository.save(user1);
        userRepository.save(user2);

        String[] badWords = {"2g1c","acrotomophilia","anal","anilingus","anus","apeshit","arsehole","ass","asshole","assmunch","erotic","autoerotic","babeland","bangbros","bareback","barenaked","bastard","bastardo","bastinado","bbw","bdsm","beaner","beaners","bestiality","bimbos","birdlock","bitch","bitches","blowjob","blumpkin","bollocks","bondage","boner","boob","boobs","bukkake","bulldyke","bullshit","bunghole","busty","butt","buttcheeks","butthole","camgirl","camslut","camwhore","carpetmuncher","circlejerk","clit","clitoris","clusterfuck","cock","cocks","coprolagnia","coprophilia","cornhole","coon","coons","creampie","cum","cumming","cunnilingus","cunt","darkie","date rape","daterape","deepthroat","dendrophilia","dick","dildo","dingleberry","dingleberries","dirty pillows","doggie style","doggiestyle","doggy style","doggystyle","dolcett","domination","dominatrix","dommes","double dong","dry hump","dvda","ecchi","ejaculation","erotic","erotism","escort","eunuch","faggot","fecal","felch","fellatio","feltch","femdom","figging","fingerbang","fingering","fisting","footjob","frotting","fuck","fuckin","fucking","fucktards","fudgepacker","futanari","goatcx","goatse","gokkun","goodpoop","goregasm","grope","g-spot","guro","handjob","hardcore","hentai","homoerotic","honkey","hooker","hot chick","huge fat","humping","incest","intercourse","jailbait","jigaboo","jiggaboo","jiggerboo","jizz","juggs","kike","kinbaku","kinkster","kinky","knobbing","lolita","lovemaking","masturbate","nudity","nympho","nymphomania","octopussy","omorashi","orgasm","orgy","paedophile","paki","panties","panty","pedobear","pedophile","pegging","penis","pissing","piss pig","pisspig","playboy","pole smoker","ponyplay","poof","poon","poontang","punany","poop chute","poopchute","porn","porno","pornography","pthc","pubes","pussy","queaf","queef","quim","raghead","rape","raping","rapist","rectum","reverse cowgirl","rimjob","rimming","sadism","santorum","scat","schlong","scissoring","semen","sex","sexo","sexy","shaved pussy","shemale","shibari","shit","shitblimp","shitty","shota","shrimping","skeet","slanteye","slut","s&m","smut","snatch","snowballing","sodomize","sodomy","spic","splooge","spooge","spunk","strapon","strappado","strip club","suck","sucks","suicide girls","sultry women","swastika","swinger","tainted love","tea bagging","threesome","throating","tight white","tit","tits","titties","titty","tongue in a","topless","tosser","towelhead","tranny","tribadism","tub girl","tubgirl","tushy","twat","twink","twinkie","undressing","upskirt","urethra play","urophilia","vagina","venus mound","vibrator","violet wand","vorarephilia","voyeur","vulva","wank","wetback","wet dream","white power","wrapping men","xx","xxx","yaoi","yellow showers","yiffy","zoophilia","🖕"};
        for(String badWord: badWords){
            badWordRepository.save(BadWord.builder().text(badWord).build());
        }
    }
}
