package hello.distribution.distri;

import java.util.List;

public interface DistriButionRepository {
    DistriBution save(DistriBution distriBution);
    DistriBution findById(Long id);
    Long login(String userid, String password);
    List<DistriBution> findAll();
    String randomdistribution(Long id);
    int randomdistributioncount(Long id);
    void receivingdistribution(Long id, String distributionname, int distributioncount);
    void deliverydistribution(Long id, String distributionname, int distributioncount);
    int householdgoodsmaxinventory();
    int kitchentoolsmaxinventory();
    int foodmaxcountinventory();
    int healthfoodmaxinventory();
    int cosmeticsmaxinventory();
    int householdgoodsCurrentinventory(Long id);
    int kitchentoolsCurrentinventory(Long id);
    int foodCurrentinventory(Long id);
    int healthfoodCurrentinventory(Long id);
    int cosmeticsCurrentinventory(Long id);
}
