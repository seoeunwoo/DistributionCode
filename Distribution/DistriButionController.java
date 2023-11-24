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
        model.addAttribute("distribution", distriBution);
        model.addAttribute("loginuserid", loginuserid);
        String randomResult = distriButionService.randomdistribution(id);
        System.out.println("randomResult = " + randomResult);
        if (randomResult.equals("생활용품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.receivingdistribution(id, randomResult, countResult);
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
        System.out.println("randomResult = " + randomResult);
        if (randomResult.equals("생활용품"))
        {
            int countResult = distriButionService.randomdistributioncount(id);
            distriButionService.deliverydistribution(id, randomResult, countResult);
            model.addAttribute("randomResult", randomResult);
            model.addAttribute("countResult", countResult);
            if (distriBution.getHouseholdgoods_count() < countResult)
            {
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
        System.out.println("distributionlist = " + distriButionList);
        model.addAttribute("distributionlist", distriButionList);
        return "/distribution/allorderlist";
    }


}
