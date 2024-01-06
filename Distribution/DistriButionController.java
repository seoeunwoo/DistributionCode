package hello.distribution.distributioncontroller;

import hello.distribution.distri.DistriBution;
import hello.distribution.distri.DistriButionService;
import hello.distribution.distri.DistriButionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/distribution")
public class DistriButionController {
    DistriButionService distriButionService = new DistriButionServiceImpl();
    private Long loginid;
    private String loginuserid;

    @GetMapping("/index")
    public String Index()
    {
        return "/distribution/index";
    }

    @GetMapping("/join")
    public String Join()
    {
        return "/distribution/join";
    }

    @PostMapping("/join")
    public String JoinComplete(@ModelAttribute DistriBution distriBution)
    {
        distriButionService.save(distriBution);
        return "redirect:/distribution/index";
    }

    @GetMapping("/login")
    public String Login(Model model)
    {
        model.addAttribute("loginid", loginid);
        return "/distribution/login";
    }

    @PostMapping("/login")
    public String LoginComplete(@RequestParam("userid") String userid,
                                @RequestParam("password") String password, Model model)
    {
        loginid = distriButionService.login(userid, password);
        if (loginid != null)
        {
            loginuserid = userid;
            return "redirect:/distribution/main";
        }
        else
        {
            System.out.println("로그인 실패 loginid 값 = " + loginid);
            model.addAttribute("loginid", loginid);
            model.addAttribute("loginFailed", "로그인 정보가 없습니다");
            return "/distribution/login";
        }

    }

    @GetMapping("/main")
    public String Main(Model model)
    {
        DistriBution distriBution = distriButionService.findById(loginid);
        model.addAttribute("distribution", distriBution);
        model.addAttribute("loginuserid", loginuserid);
        return "/distribution/main";
    }

    // 입고
    @GetMapping("/receivinggoods/{id}")
    public String ReceivingGoods(@PathVariable("id") Long id, Model model)
    {
        DistriBution distriBution = distriButionService.findById(id);

        // DistriBution 객체를 생성해서 입력 받은 id에 해당하는 distriBution 객체 값을 가져옴
        // DistriBution 객체를 식별하기 위해 findById 메소드를 통해 가져온 DistriBution 객체를
        // distriBution 객체에 할당
        // distriBution 객체를 통해 모든 DistriBution 객체에 접근 하고 각종 메소드를 사용할 수 있음

        model.addAttribute("distribution", distriBution);
        model.addAttribute("loginuserid", loginuserid);
        String randomResult = distriButionService.randomdistribution(id);
        // randomdistribution 메소드를 이용해서 각 물류를 랜덤으로 하나 출력해서 randomResult에 저장
        // 아래에서 랜덤으로 생성된 물류명에 따라 if문으로 데이터 처리
        System.out.println("randomResult = " + randomResult);
        if (randomResult.equals("생활용품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.receivingdistribution(id, randomResult, countResult);
            // randomdistributioncount 메소드를 이용해 랜덤으로 숫자를 생성
            // receivingdistribution 메소드에 key 값으로 id, 물류명, 물류개수를 저장
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
        }
        if (randomResult.equals("주방용품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.receivingdistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
        }
        if (randomResult.equals("식품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.receivingdistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
        }
        if (randomResult.equals("건강보조식품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.receivingdistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
        }
        if (randomResult.equals("화장품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.receivingdistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
        }
        return "/distribution/receivinggoods";
    }

    // 출고
    @GetMapping("/deliverygoods/{id}")
    public String DeliveryGoods(@PathVariable("id") Long id, Model model)
    {
        DistriBution distriBution = distriButionService.findById(id);
        model.addAttribute("distribution", distriBution);
        model.addAttribute("loginuserid", loginuserid);
        String randomResult = distriButionService.randomdistribution(id);
        // randomdistribution 메소드를 이용해서 각 물류를 랜덤으로 하나 출력해서 randomResult에 저장
        // 아래에서 랜덤으로 생성된 물류명에 따라 if문으로 데이터 처리
        System.out.println("randomResult = " + randomResult);
        if (randomResult.equals("생활용품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.deliverydistribution(id, randomResult, countResult);
            // randomdistributioncount 메소드를 이용해 랜덤으로 숫자를 생성
            // deliverydistribution 메소드에 key 값으로 id, 물류명, 물류개수를 저장
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
            if (distriBution.getHouseholdgoods_count() < countResult)
            {
                // 현재 저장된 생활용품 개수보다 생성된 물류개수가 더 크면 출하를 중지 하고
                // 에러메세지 출력
                model.addAttribute("error", "출하개수가 더 많을 수 없습니다");
            }
        }

        if (randomResult.equals("주방용품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.deliverydistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
            if (distriBution.getKitchentools_count() < countResult)
            {
                model.addAttribute("error", "출하개수가 더 많을 수 없습니다");
            }
        }

        if (randomResult.equals("식품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.deliverydistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
            if (distriBution.getFood_count() < countResult)
            {
                model.addAttribute("error", "출하개수가 더 많을 수 없습니다");
            }
        }

        if (randomResult.equals("건강보조식품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.deliverydistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
            if (distriBution.getHealthfood_count() < countResult)
            {
                model.addAttribute("error", "출하개수가 더 많을 수 없습니다");
            }
        }

        if (randomResult.equals("화장품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.deliverydistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
            if (distriBution.getCosmetics_count() < countResult)
            {
                model.addAttribute("error", "출하개수가 더 많을 수 없습니다");
            }
        }
        return "/distribution/deliverygoods";
    }

    // 내 주문관리
    @GetMapping("/myorderlist/{id}")
    public String MyorderList(@PathVariable("id") Long id, Model model)
    {
        DistriBution distriBution = distriButionService.findById(id);
        model.addAttribute("distribution", distriBution);
        model.addAttribute("loginuserid", loginuserid);
        return "/distribution/myorderlist";
    }

    // 전체 주문리스트
    @GetMapping("/allorderlist")
    public String Inventory(Model model)
    {
        List<DistriBution> distriButionList = distriButionService.findAll();
        // 저장된 모든 데이터를 DB에서 불러와서 List에 새로저장 후 view에 데이터 전달
        System.out.println("distributionlist = " + distriButionList);
        model.addAttribute("distributionlist", distriButionList);
        return "/distribution/allorderlist";
    }


}
