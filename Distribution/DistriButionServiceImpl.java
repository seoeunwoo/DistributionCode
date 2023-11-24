package hello.distribution.distri;

import java.util.List;

public class DistriButionServiceImpl implements DistriButionService{
    private final DistriButionRepository distriButionRepository = new DistriButionRepositoryImpl();

    @Override
    public DistriBution save(DistriBution distriBution) {
        return distriButionRepository.save(distriBution);
    }

    @Override
    public DistriBution findById(Long id) {
        return distriButionRepository.findById(id);
    }

    @Override
    public List<DistriBution> findAll() {
        return distriButionRepository.findAll();
    }

    @Override
    public Long login(String userid, String password) {
        return distriButionRepository.login(userid, password);
    }

    @Override
    public String randomdistribution(Long id) {
        return distriButionRepository.randomdistribution(id);
    }

    @Override
    public int randomdistributioncount(Long id) {
        return distriButionRepository.randomdistributioncount(id);
    }

    @Override
    public void receivingdistribution(Long id, String distributionname, int distributioncount) {
        distriButionRepository.receivingdistribution(id, distributionname, distributioncount);
    }

    @Override
    public void deliverydistribution(Long id, String distributionname, int distributioncount) {
        distriButionRepository.deliverydistribution(id, distributionname, distributioncount);
    }

    @Override
    public int householdgoodsmaxinventory() {
        return distriButionRepository.householdgoodsmaxinventory();
    }

    @Override
    public int kitchentoolsmaxinventory() {
        return distriButionRepository.kitchentoolsmaxinventory();
    }

    @Override
    public int foodmaxcountinventory() {
        return distriButionRepository.foodmaxcountinventory();
    }

    @Override
    public int healthfoodmaxinventory() {
        return distriButionRepository.healthfoodmaxinventory();
    }

    @Override
    public int cosmeticsmaxinventory() {
        return distriButionRepository.cosmeticsmaxinventory();
    }

    @Override
    public int householdgoodsCurrentinventory(Long id) {
        return distriButionRepository.householdgoodsCurrentinventory(id);
    }

    @Override
    public int kitchentoolsCurrentinventory(Long id) {
        return distriButionRepository.kitchentoolsCurrentinventory(id);
    }

    @Override
    public int foodCurrentinventory(Long id) {
        return distriButionRepository.foodCurrentinventory(id);
    }

    @Override
    public int healthfoodCurrentinventory(Long id) {
        return distriButionRepository.healthfoodCurrentinventory(id);
    }

    @Override
    public int cosmeticsCurrentinventory(Long id) {
        return distriButionRepository.cosmeticsCurrentinventory(id);
    }
}
