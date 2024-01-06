package hello.distribution.distri;

import hello.restaurant.restaurantconnection.DBConnectionUtility;
import hello.restaurant.user.Rating;
import hello.restaurant.user.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class DistriButionRepositoryImpl implements DistriButionRepository{
    private final Map<Long, DistriBution> data = new HashMap<>();
    private Map<Long, Map<String, Integer>> distributionCounts = new HashMap<>();
    private Long id = 0L;
    private Long loginid;
    private final int HouseHoldGoodsMaxCount = 100;
    private final int KitchentoolsMaxCount = 100;
    private final int FoodMaxCount = 100;
    private final int HealthFoodMaxCount = 100;
    private final int CosmeticsMaxCount = 100;


    private Long getNextId()
    {
        String sql = "SELECT MAX(id) FROM distribution";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {
            if (resultSet.next())
            {
                Long maxId = resultSet.getLong(1);
                // 최대 ID 값에 1을 더한 값을 반환
                return maxId + 1;
            } else
            {
                // 테이블이 비어있을 경우, 1부터 시작
                return 1L;
            }
        }
        catch (SQLException e)
        {
            log.error("데이터베이스 오류입니다", e);
            return null;
        }
    }


    @Override
    public DistriBution save(DistriBution distriBution) {
        Long nexdId = getNextId();

        distriBution.setId(nexdId);
        data.put(nexdId, distriBution);

        String SaveSql = "insert into distribution (id, userid, password, name, age, address, email, householdgoods, kitchentools, food, healthfood, cosmetics," +
                "householdgoods_maxcount, kitchentools_maxcount, food_maxcount, healthfood_maxcount, cosmetics_maxcount) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try
        {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SaveSql);

            preparedStatement.setLong(1, distriBution.getId());
            preparedStatement.setString(2, distriBution.getUserid());
            preparedStatement.setString(3, distriBution.getPassword());
            preparedStatement.setString(4, distriBution.getName());
            preparedStatement.setInt(5, distriBution.getAge());
            preparedStatement.setString(6, distriBution.getAddress());
            preparedStatement.setString(7, distriBution.getEmail());
            preparedStatement.setString(8, distriBution.getHouseholdgoods());
            preparedStatement.setString(9, distriBution.getKitchentools());
            preparedStatement.setString(10, distriBution.getFood());
            preparedStatement.setString(11, distriBution.getHealthfood());
            preparedStatement.setString(12, distriBution.getCosmetics());
            preparedStatement.setInt(13, distriBution.getHouseholdgoods_maxcount());
            preparedStatement.setInt(14, distriBution.getKitchentools_maxcount());
            preparedStatement.setInt(15, distriBution.getFood_maxcount());
            preparedStatement.setInt(16, distriBution.getHealthfood_maxcount());
            preparedStatement.setInt(17, distriBution.getCosmetics_maxcount());


            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            log.error("데이터 오류 입니다", e);
        }

        System.out.println();
        System.out.println(data.toString());
        System.out.println();
        return distriBution;
    }

    @Override
    public DistriBution findById(Long id) {
        return data.get(id);
    }

    @Override
    public Long login(String userid, String password) {
        for(Map.Entry<Long, DistriBution> entry : data.entrySet())
        {
            DistriBution distriBution = entry.getValue();
            if (distriBution.getUserid().equals(userid) && distriBution.getPassword().equals(password))
            {
                loginid = entry.getKey();
                System.out.println();
                System.out.println("로그인한 id = " + loginid);
                System.out.println();
                return loginid;
            }
        }

        // Map에 저장된 데이터를 기준으로 userid와 password가 일치하는 곳의 key 값인 id값을 가져와서 loginId에 재할당
        // 일치하는 데이터가 없으면 null을 반환

        return null;
    }

    @Override
    public List<DistriBution> findAll() {
        List<DistriBution> distriButionList = new ArrayList<>();

        String findAllSql = "select * from distribution";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllSql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {

            while (resultSet.next())
            {
                // Distribution 객체에 new 연산자로 빈공간을 만들고
                // db에 저장된 데이터를 각 Distribution 객체 필드 변수에 해당값을 담는 구조

                DistriBution distriBution = new DistriBution();
                distriBution.setId(resultSet.getLong("id"));
                distriBution.setUserid(resultSet.getString("userid"));
                distriBution.setPassword(resultSet.getString("password"));
                distriBution.setName(resultSet.getString("name"));
                distriBution.setAge(resultSet.getInt("age"));
                distriBution.setAddress(resultSet.getString("address"));
                distriBution.setEmail(resultSet.getString("email"));
                distriBution.setHouseholdgoods(resultSet.getString("householdgoods"));
                distriBution.setKitchentools(resultSet.getString("kitchentools"));
                distriBution.setFood(resultSet.getString("food"));
                distriBution.setHealthfood(resultSet.getString("healthfood"));
                distriBution.setCosmetics(resultSet.getString("cosmetics"));
                distriBution.setHouseholdgoods_maxcount(resultSet.getInt("householdgoods_maxcount"));
                distriBution.setKitchentools_maxcount(resultSet.getInt("kitchentools_maxcount"));
                distriBution.setFood_maxcount(resultSet.getInt("food_maxcount"));
                distriBution.setHealthfood_maxcount(resultSet.getInt("healthfood_maxcount"));
                distriBution.setCosmetics_maxcount(resultSet.getInt("cosmetics_maxcount"));
                distriBution.setHouseholdgoods_count(resultSet.getInt("householdgoods_count"));
                distriBution.setKitchentools_count(resultSet.getInt("kitchentools_count"));
                distriBution.setFood_count(resultSet.getInt("food_count"));
                distriBution.setHealthfood_count(resultSet.getInt("healthfood_count"));
                distriBution.setCosmetics_count(resultSet.getInt("cosmetics_count"));
                distriBution.setHouseholdgoods_receiving(resultSet.getInt("householdgoods_receiving"));
                distriBution.setKitchentools_receiving(resultSet.getInt("kitchentools_receiving"));
                distriBution.setFood_receiving(resultSet.getInt("food_receiving"));
                distriBution.setHealthfood_receiving(resultSet.getInt("healthfood_receiving"));
                distriBution.setCosmetics_receiving(resultSet.getInt("cosmetics_receiving"));
                distriBution.setHouseholdgoods_release(resultSet.getInt("householdgoods_release"));
                distriBution.setKitchentools_release(resultSet.getInt("kitchentools_release"));
                distriBution.setFood_release(resultSet.getInt("food_release"));
                distriBution.setHealthfood_release(resultSet.getInt("healthfood_release"));
                distriBution.setCosmetics_release(resultSet.getInt("cosmetics_release"));


                distriButionList.add(distriBution);
                log.info("가져온 사용자 수: {}", distriButionList.size());
                log.info("사용자: {}", distriButionList);
            }
        }
        catch (SQLException e)
        {
            log.error("데이터베이스 오류입니다", e);
        }

        return distriButionList;
    }

    @Override
    public String randomdistribution(Long id) {
        /*String[] distributionoption = {"생활용품", "주방용품", "식품", "건강보조식품", "화장품"};

        Random random = new Random();

        int selecttype = random.nextInt(distributionoption.length);

        String result = distributionoption[selecttype];
        System.out.println("랜덤으로 나온 물류명 = " + result);*/
        List<String> items = new ArrayList<>();
        items.add("생활용품");
        items.add("주방용품");
        items.add("식품");
        items.add("건강보조식품");
        items.add("화장품");

        Random random = new Random();

        while (!items.isEmpty()) {
            int randomIndex = random.nextInt(items.size()); // 랜덤 인덱스 선택
            String result = items.get(randomIndex); // 선택된 문자열 가져오기
            System.out.println("랜덤으로 나온 물류명 = " + result);

            // 선택된 문자열 제거
            items.remove(randomIndex);
            return result;
        }

        // items가 비어있지 않으면 모든 데이터를 돌면서 값을 추출
        // List에 저장된 데이터의 사이즈에 따라 랜덤으로 인덱스 번호를 하나 가져온다
        // 예를들어 생활용품이 2번 인덱스에 위치 하고 랜덤으로 2번이 선택 됐으면
        // 생활용품의 문자열이 추출되는 방식
        // 방금 선택된 문자열은 제거 하고 나머지 남은 문자열 중에서 랜덤으로 선택함
        return null;
    }

    @Override
    public int randomdistributioncount(Long id) {
        Random random = new Random();
        int countResult = random.nextInt(20) + 1;
        System.out.println("랜덤으로 나온 물류개수 = " + countResult);

        // 물류개수는 최소 1개 최대 20개 중 랜덤으로 물류개수를 추출함
        // 추출된 값을 return으로 반환
        return countResult;
    }

    
    @Override
    public void receivingdistribution(Long id, String distributionname, int distributioncount) {
        DistriBution distriBution = data.get(id);

        if (distributionname.equals("생활용품"))
        {
            int currentHouseholdgoodsCount = distriBution.getHouseholdgoods_count();

            // 현재 생활용품 개수

            if (currentHouseholdgoodsCount + distributioncount < HouseHoldGoodsMaxCount)
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);

                // 매개변수로 입력받은 아이디를 추가 Map의 key 값으로 전달
                if (subdistributionCount == null)
                {
                    subdistributionCount = new HashMap<>();
                    distributionCounts.put(id, subdistributionCount);

                    // value 값은 비어있는 상태니 new 연산자로 sub Map에 빈 공간을 만들어서 데이터 저장

                    String SubHouseHoldGoodsSql = "insert into distribution_individual_count(id) values (?)";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHouseHoldGoodsSql);

                        preparedStatement.setLong(1, distriBution.getId());


                        preparedStatement.executeUpdate();
                    }
                    catch (SQLException e)
                    {
                        log.error("데이터 오류 입니다", e);
                    }
                }
                if (subdistributionCount.containsKey(distributionname))
                {
                    int currentCount = subdistributionCount.get(distributionname);
                    subdistributionCount.put(distributionname, currentCount + distributioncount);

                    // 생활용품의 현재 개수를 가져와서 key 값으로 생활용품, value 값으로 현재 생활용품 개수에서 새로 들어온 개수를 + 해서 저장

                    String SubHouseHoldgoodsCountSql = "update distribution_individual_count set householdgoods_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHouseHoldgoodsCountSql);

                        preparedStatement.setInt(1, currentCount + distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual householdgoods_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                }
                else
                {
                    subdistributionCount.put(distributionname, distributioncount);

                    // 데이터에 아무것도 없다면 key 값으로 생활용품, value 값으로 전달 받은 개수를 저장

                    String SubHouseHoldgoodsCountSql = "update distribution_individual_count set householdgoods_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHouseHoldgoodsCountSql);

                        preparedStatement.setInt(1, distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual householdgoods_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                System.out.println();
                System.out.println(distributionCounts.toString());
                System.out.println();
                distriBution.addhouseholdgoods_count(distributioncount);
                distriBution.setHouseholdgoods_receiving(distriBution.getHouseholdgoods_receiving() + 1);

                // Distribution 객체에 메소드를 이용해서 생활용품 개수와 입고 횟수를 + 1해서 저장

                String HouseHoldGoodsSql = "update distribution set householdgoods_count = ?, householdgoods_receiving = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(HouseHoldGoodsSql);

                    preparedStatement.setInt(1, distriBution.getHouseholdgoods_count());
                    preparedStatement.setInt(2, distriBution.getHouseholdgoods_receiving());
                    preparedStatement.setLong(3, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving householdgoods result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }
                System.out.println("생활용품 " + distributioncount + " 개 입고 되었습니다");
            }
            else
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                int remainingCount = currentHouseholdgoodsCount + distributioncount - HouseHoldGoodsMaxCount;
                distriBution.setHouseholdgoods_count(HouseHoldGoodsMaxCount);
                subdistributionCount.put(distributionname, HouseHoldGoodsMaxCount);

                // 재고가 꽉 찼을경우 현재 생활용품 개수에서 입력받은 개수를 더한 뒤 최대 재고 수량을 - 하면 몇 개가 초과 되었는지 나옴
                // 최대 재고수량을 넘지 못하게 임의로 생활용품 개수를 최대 재고 수량으로 다시 저장

                String HouseHoldGoodsMaxSql = "update distribution set householdgoods_count = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(HouseHoldGoodsMaxSql);

                    preparedStatement.setInt(1, HouseHoldGoodsMaxCount);
                    preparedStatement.setLong(2, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving householdgoods result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }

                System.out.println("재고가 꽉찼습니다");
                System.out.println("생활용품 초과된 개수: " + remainingCount);
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }

        if (distributionname.equals("주방용품"))
        {
            int currentKitchentoolsCount = distriBution.getKitchentools_count();

            if (currentKitchentoolsCount + distributioncount < KitchentoolsMaxCount)
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                if (subdistributionCount == null)
                {
                    subdistributionCount = new HashMap<>();
                    distributionCounts.put(id, subdistributionCount);

                    String SubKitchenToolsSql = "insert into distribution_individual_count(id) values (?)";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubKitchenToolsSql);

                        preparedStatement.setLong(1, distriBution.getId());


                        preparedStatement.executeUpdate();
                    }
                    catch (SQLException e)
                    {
                        log.error("데이터 오류 입니다", e);
                    }
                }
                if (subdistributionCount.containsKey(distributionname))
                {
                    int currentCount = subdistributionCount.get(distributionname);
                    subdistributionCount.put(distributionname, currentCount + distributioncount);

                    String SubKitchenToolsCountSql = "update distribution_individual_count set kitchentools_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubKitchenToolsCountSql);

                        preparedStatement.setInt(1, currentCount + distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual kitchentools_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                else
                {
                    subdistributionCount.put(distributionname, distributioncount);

                    String SubKitchenToolsCountSql = "update distribution_individual_count set kitchentools_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubKitchenToolsCountSql);

                        preparedStatement.setInt(1, distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual kitchentools_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                System.out.println();
                System.out.println(distributionCounts.toString());
                System.out.println();
                distriBution.addkitchentools_count(distributioncount);
                distriBution.setKitchentools_receiving(distriBution.getKitchentools_receiving() + 1);

                String KitchenToolsSql = "update distribution set kitchentools_count = ?, kitchentools_receiving = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(KitchenToolsSql);

                    preparedStatement.setInt(1, distriBution.getKitchentools_count());
                    preparedStatement.setInt(2, distriBution.getKitchentools_receiving());
                    preparedStatement.setLong(3, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving kitchentools result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }
                System.out.println("주방용품 " + distributioncount + " 개 입고 되었습니다");
            }
            else
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                int remainingCount = currentKitchentoolsCount + distributioncount - KitchentoolsMaxCount;
                distriBution.setKitchentools_count(KitchentoolsMaxCount);
                subdistributionCount.put(distributionname, KitchentoolsMaxCount);

                String KitchenToolsMaxSql = "update distribution set kitchentools_count = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(KitchenToolsMaxSql);

                    preparedStatement.setInt(1, KitchentoolsMaxCount);
                    preparedStatement.setLong(2, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving kitchentools result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }

                System.out.println("재고가 꽉찼습니다");
                System.out.println("주방용품 초과된 개수: " + remainingCount);
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }

        if (distributionname.equals("식품"))
        {
            int currentFoodCount = distriBution.getFood_count();

            if (currentFoodCount + distributioncount < FoodMaxCount)
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                if (subdistributionCount == null)
                {
                    subdistributionCount = new HashMap<>();
                    distributionCounts.put(id, subdistributionCount);

                    String SubFoodSql = "insert into distribution_individual_count(id) values (?)";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubFoodSql);

                        preparedStatement.setLong(1, distriBution.getId());


                        preparedStatement.executeUpdate();
                    }
                    catch (SQLException e)
                    {
                        log.error("데이터 오류 입니다", e);
                    }
                }
                if (subdistributionCount.containsKey(distributionname))
                {
                    int currentCount = subdistributionCount.get(distributionname);
                    subdistributionCount.put(distributionname, currentCount + distributioncount);

                    String SubFoodCountSql = "update distribution_individual_count set food_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubFoodCountSql);

                        preparedStatement.setInt(1, currentCount + distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual food_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                else
                {
                    subdistributionCount.put(distributionname, distributioncount);

                    String SubFoodCountSql = "update distribution_individual_count set food_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubFoodCountSql);

                        preparedStatement.setInt(1, distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual food_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                System.out.println();
                System.out.println(distributionCounts.toString());
                System.out.println();
                distriBution.addfood_count(distributioncount);
                distriBution.setFood_receiving(distriBution.getFood_receiving() + 1);

                String FoodSql = "update distribution set food_count = ?, food_receiving = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(FoodSql);

                    preparedStatement.setInt(1, distriBution.getFood_count());
                    preparedStatement.setInt(2, distriBution.getFood_receiving());
                    preparedStatement.setLong(3, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving food result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }
                System.out.println("식품 " + distributioncount + " 개 입고 되었습니다");
            }
            else
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                int remainingCount = currentFoodCount + distributioncount - FoodMaxCount;
                distriBution.setFood_count(FoodMaxCount);
                subdistributionCount.put(distributionname, FoodMaxCount);

                String FoodMaxSql = "update distribution set food_count = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(FoodMaxSql);

                    preparedStatement.setInt(1, FoodMaxCount);
                    preparedStatement.setLong(2, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving food result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }

                System.out.println("재고가 꽉찼습니다");
                System.out.println("식품 초과된 개수: " + remainingCount);
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }

        if (distributionname.equals("건강보조식품"))
        {
            int currentHealthfoodCount = distriBution.getHealthfood_count();

            if (currentHealthfoodCount + distributioncount < HealthFoodMaxCount)
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                if (subdistributionCount == null)
                {
                    subdistributionCount = new HashMap<>();
                    distributionCounts.put(id, subdistributionCount);

                    String SubHealthFoodSql = "insert into distribution_individual_count(id) values (?)";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHealthFoodSql);

                        preparedStatement.setLong(1, distriBution.getId());


                        preparedStatement.executeUpdate();
                    }
                    catch (SQLException e)
                    {
                        log.error("데이터 오류 입니다", e);
                    }
                }
                if (subdistributionCount.containsKey(distributionname))
                {
                    int currentCount = subdistributionCount.get(distributionname);
                    subdistributionCount.put(distributionname, currentCount + distributioncount);

                    String SubHealthFoodCountSql = "update distribution_individual_count set healthfood_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHealthFoodCountSql);

                        preparedStatement.setInt(1, currentCount + distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual healthfood_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                else
                {
                    subdistributionCount.put(distributionname, distributioncount);

                    String SubHealthFoodCountSql = "update distribution_individual_count set healthfood_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHealthFoodCountSql);

                        preparedStatement.setInt(1, distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual healthfood_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                System.out.println();
                System.out.println(distributionCounts.toString());
                System.out.println();
                distriBution.addhealthfood_count(distributioncount);
                distriBution.setHealthfood_receiving(distriBution.getHealthfood_receiving() + 1);

                String HealthFoodSql = "update distribution set healthfood_count = ?, healthfood_receiving = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(HealthFoodSql);

                    preparedStatement.setInt(1, distriBution.getHealthfood_count());
                    preparedStatement.setInt(2, distriBution.getHealthfood_receiving());
                    preparedStatement.setLong(3, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving healthfood result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }
                System.out.println("건강보조식품 " + distributioncount + " 개 입고 되었습니다");
            }
            else
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                int remainingCount = currentHealthfoodCount + distributioncount - HealthFoodMaxCount;
                distriBution.setHealthfood_count(HealthFoodMaxCount);
                subdistributionCount.put(distributionname, HealthFoodMaxCount);

                String HealthFoodMaxSql = "update distribution set healthfood_count = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(HealthFoodMaxSql);

                    preparedStatement.setInt(1, HealthFoodMaxCount);
                    preparedStatement.setLong(2, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving healthfood result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }
                System.out.println("재고가 꽉찼습니다");
                System.out.println("건강보조식품 초과된 개수: " + remainingCount);
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }

        if (distributionname.equals("화장품"))
        {
            int currentCosmeticsCount = distriBution.getCosmetics_count();

            if (currentCosmeticsCount + distributioncount < CosmeticsMaxCount)
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                if (subdistributionCount == null)
                {
                    subdistributionCount = new HashMap<>();
                    distributionCounts.put(id, subdistributionCount);

                    String SubCosmeticsSql = "insert into distribution_individual_count(id) values (?)";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubCosmeticsSql);

                        preparedStatement.setLong(1, distriBution.getId());


                        preparedStatement.executeUpdate();
                    }
                    catch (SQLException e)
                    {
                        log.error("데이터 오류 입니다", e);
                    }
                }
                if (subdistributionCount.containsKey(distributionname))
                {
                    int currentCount = subdistributionCount.get(distributionname);
                    subdistributionCount.put(distributionname, currentCount + distributioncount);

                    String SubCosmeticsCountSql = "update distribution_individual_count set cosmetics_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubCosmeticsCountSql);

                        preparedStatement.setInt(1, currentCount + distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual cosmetics_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                else
                {
                    subdistributionCount.put(distributionname, distributioncount);

                    String SubHealthFoodCountSql = "update distribution_individual_count set cosmetics_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHealthFoodCountSql);

                        preparedStatement.setInt(1, distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual cosmetics_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }
                }
                System.out.println();
                System.out.println(distributionCounts.toString());
                System.out.println();
                distriBution.addcosmetics_count(distributioncount);
                distriBution.setCosmetics_receiving(distriBution.getCosmetics_receiving() + 1);

                String CosmeticsSql = "update distribution set cosmetics_count = ?, cosmetics_receiving = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(CosmeticsSql);

                    preparedStatement.setInt(1, distriBution.getCosmetics_count());
                    preparedStatement.setInt(2, distriBution.getCosmetics_receiving());
                    preparedStatement.setLong(3, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving cosemtics result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }
                System.out.println("화장품 " + distributioncount + " 개 입고 되었습니다");
            }
            else
            {
                Map<String, Integer> subdistributionCount = distributionCounts.get(id);
                int remainingCount = currentCosmeticsCount + distributioncount - CosmeticsMaxCount;
                distriBution.setCosmetics_count(CosmeticsMaxCount);
                subdistributionCount.put(distributionname, CosmeticsMaxCount);

                String CosmeticsMaxSql = "update distribution set cosmetics_count = ? where id = ?";

                try
                {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement(CosmeticsMaxSql);

                    preparedStatement.setInt(1, CosmeticsMaxCount);
                    preparedStatement.setLong(2, distriBution.getId());


                    int resultSize = preparedStatement.executeUpdate();

                    System.out.println();
                    log.info("receiving cosmetics result = {}", resultSize);
                    System.out.println();
                    // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                }
                catch (SQLException e)
                {
                    System.out.println("데이터 오류 입니다" + e);
                }

                System.out.println("재고가 꽉찼습니다");
                System.out.println("화장품 초과된 개수: " + remainingCount);
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }
    }

    @Override
    public void deliverydistribution(Long id, String distributionname, int distributioncount) {
        DistriBution distriBution = data.get(id);

        if (distributionname.equals("생활용품"))
        {
            Map<String, Integer> subdistributionCount = distributionCounts.get(id);

            if (subdistributionCount != null && subdistributionCount.containsKey(distributionname))
            {
                int currentCount = subdistributionCount.get(distributionname);
                System.out.println("생활용품 currentCount = " + currentCount);

                // 생활용품의 Map이 null이 아니고 생활용품의 key값이 존재한다면
                // key 값을 가져와서 현재 생활용품의 개수가 몇개인지 구함

                if (currentCount >= distributioncount)
                {
                    subdistributionCount.put(distributionname, currentCount - distributioncount); // 출하 개수만큼 빼줌
                    distriBution.minushouseholdgoods_count(distributioncount);
                    distriBution.setHouseholdgoods_release(distriBution.getHouseholdgoods_release() + 1);

                    // 현재 생활용품 개수가 입력 받은 개수보다 크거나 같으면
                    // 현재 생활용품 개수에서 입력 받은 개수를 빼주고
                    // set 메소드를 이용해서 데이터 저장

                    String HouseHoldGoodsSql = "update distribution set householdgoods_count = ?, householdgoods_release = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(HouseHoldGoodsSql);

                        preparedStatement.setInt(1, distriBution.getHouseholdgoods_count());
                        preparedStatement.setInt(2, distriBution.getHouseholdgoods_release());
                        preparedStatement.setLong(3, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("delivery householdgoods result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    String SubHouseHoldgoodsCountSql = "update distribution_individual_count set householdgoods_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHouseHoldgoodsCountSql);

                        preparedStatement.setInt(1, currentCount - distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual householdgoods_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }


                    System.out.println("물류가 출하되었습니다");
                    System.out.println("출하된 생활용품 개수: " + distributioncount);
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
                else
                {
                    // 현재 생활용품 개수가 입력 받은 개수보다 작으면 오류메세지 출력
                    System.out.println("출하 요청한 물류보다 현재 재고가 부족합니다");
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
            }
            else
            {
                // 생활용품이라는 key 값이 없으면 오류메세지 출력
                System.out.println("해당 물류에 대한 정보가 없거나 출하할 수량이 없습니다");
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }

        if (distributionname.equals("주방용품"))
        {
            Map<String, Integer> subdistributionCount = distributionCounts.get(id);

            if (subdistributionCount != null && subdistributionCount.containsKey(distributionname))
            {
                int currentCount = subdistributionCount.get(distributionname);
                System.out.println("주방용품 currentCount = " + currentCount);

                if (currentCount >= distributioncount)
                {
                    subdistributionCount.put(distributionname, currentCount - distributioncount); // 출하 개수만큼 빼줌
                    distriBution.minuskitchentools_count(distributioncount);
                    distriBution.setKitchentools_release(distriBution.getKitchentools_release() + 1);

                    String KitchenToolsSql = "update distribution set kitchentools_count = ?, kitchentools_release = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(KitchenToolsSql);

                        preparedStatement.setInt(1, distriBution.getKitchentools_count());
                        preparedStatement.setInt(2, distriBution.getKitchentools_release());
                        preparedStatement.setLong(3, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("delivery kitchentools result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    String SubKitchentoolsCountSql = "update distribution_individual_count set kitchentools_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubKitchentoolsCountSql);

                        preparedStatement.setInt(1, currentCount - distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual kitchentools_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    System.out.println("물류가 출하되었습니다");
                    System.out.println("출하된 주방용품 개수: " + distributioncount);
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
                else
                {
                    System.out.println("출하 요청한 물류보다 현재 재고가 부족합니다");
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
            }
            else
            {
                System.out.println("해당 물류에 대한 정보가 없거나 출하할 수량이 없습니다");
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }

        if (distributionname.equals("식품"))
        {
            Map<String, Integer> subdistributionCount = distributionCounts.get(id);

            if (subdistributionCount != null && subdistributionCount.containsKey(distributionname))
            {
                int currentCount = subdistributionCount.get(distributionname);
                System.out.println("식품 currentCount = " + currentCount);

                if (currentCount >= distributioncount)
                {
                    subdistributionCount.put(distributionname, currentCount - distributioncount); // 출하 개수만큼 빼줌
                    distriBution.minusfood_count(distributioncount);
                    distriBution.setFood_release(distriBution.getFood_release() + 1);

                    String FoodSql = "update distribution set food_count = ?, food_release = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(FoodSql);

                        preparedStatement.setInt(1, distriBution.getFood_count());
                        preparedStatement.setInt(2, distriBution.getFood_release());
                        preparedStatement.setLong(3, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("delivery food result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    String SubFoodCountSql = "update distribution_individual_count set food_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubFoodCountSql);

                        preparedStatement.setInt(1, currentCount - distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual food_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    System.out.println("물류가 출하되었습니다");
                    System.out.println("출하된 식품 개수: " + distributioncount);
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
                else
                {
                    System.out.println("출하 요청한 물류보다 현재 재고가 부족합니다");
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
            }
            else
            {
                System.out.println("해당 물류에 대한 정보가 없거나 출하할 수량이 없습니다");
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }

        if (distributionname.equals("건강보조식품"))
        {
            Map<String, Integer> subdistributionCount = distributionCounts.get(id);

            if (subdistributionCount != null && subdistributionCount.containsKey(distributionname))
            {
                int currentCount = subdistributionCount.get(distributionname);
                System.out.println("건강보조식품 currentCount = " + currentCount);

                if (currentCount >= distributioncount)
                {
                    subdistributionCount.put(distributionname, currentCount - distributioncount); // 출하 개수만큼 빼줌
                    distriBution.minushealthfood_count(distributioncount);
                    distriBution.setHealthfood_release(distriBution.getHealthfood_release() + 1);

                    String HealthFoodSql = "update distribution set healthfood_count = ?, healthfood_release = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(HealthFoodSql);

                        preparedStatement.setInt(1, distriBution.getHealthfood_count());
                        preparedStatement.setInt(2, distriBution.getHealthfood_release());
                        preparedStatement.setLong(3, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("delivery healthfood result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    String SubHealthFoodCountSql = "update distribution_individual_count set healthfood_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubHealthFoodCountSql);

                        preparedStatement.setInt(1, currentCount - distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual healthfood_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    System.out.println("물류가 출하되었습니다");
                    System.out.println("출하된 건강보조식품 개수: " + distributioncount);
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
                else
                {
                    System.out.println("출하 요청한 물류보다 현재 재고가 부족합니다");
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
            }
            else
            {
                System.out.println("해당 물류에 대한 정보가 없거나 출하할 수량이 없습니다");
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }

        if (distributionname.equals("화장품"))
        {
            Map<String, Integer> subdistributionCount = distributionCounts.get(id);

            if (subdistributionCount != null && subdistributionCount.containsKey(distributionname))
            {
                int currentCount = subdistributionCount.get(distributionname);
                System.out.println("화장품 currentCount = " + currentCount);

                if (currentCount >= distributioncount)
                {
                    subdistributionCount.put(distributionname, currentCount - distributioncount); // 출하 개수만큼 빼줌
                    distriBution.minuscosmetics_count(distributioncount);
                    distriBution.setCosmetics_release(distriBution.getCosmetics_release() + 1);

                    String CosmeticsSql = "update distribution set cosmetics_count = ?, cosmetics_release = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(CosmeticsSql);

                        preparedStatement.setInt(1, distriBution.getCosmetics_count());
                        preparedStatement.setInt(2, distriBution.getCosmetics_release());
                        preparedStatement.setLong(3, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("delivery cosmetics result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    String SubCosmeticsCountSql = "update distribution_individual_count set cosmetics_count = ? where id = ?";

                    try
                    {
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        connection = getConnection();
                        preparedStatement = connection.prepareStatement(SubCosmeticsCountSql);

                        preparedStatement.setInt(1, currentCount - distributioncount);
                        preparedStatement.setLong(2, distriBution.getId());


                        int resultSize = preparedStatement.executeUpdate();

                        System.out.println();
                        log.info("individual cosmetics_count result = {}", resultSize);
                        System.out.println();
                        // resultSize가 1이 나오면 성공적으로 수정, 0이 나오면 실패
                    }
                    catch (SQLException e)
                    {
                        System.out.println("데이터 오류 입니다" + e);
                    }

                    System.out.println("물류가 출하되었습니다");
                    System.out.println("출하된 화장품 개수: " + distributioncount);
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
                else
                {
                    System.out.println("출하 요청한 물류보다 현재 재고가 부족합니다");
                    System.out.println();
                    System.out.println(distributionCounts.toString());
                    System.out.println();
                }
            }
            else
            {
                System.out.println("해당 물류에 대한 정보가 없거나 출하할 수량이 없습니다");
            }
            System.out.println();
            System.out.println(data.toString());
            System.out.println();
        }
    }

    @Override
    public int householdgoodsmaxinventory() {
        return HouseHoldGoodsMaxCount;
    }

    @Override
    public int kitchentoolsmaxinventory() {
        return KitchentoolsMaxCount;
    }

    @Override
    public int foodmaxcountinventory() {
        return FoodMaxCount;
    }

    @Override
    public int healthfoodmaxinventory() {
        return HealthFoodMaxCount;
    }

    @Override
    public int cosmeticsmaxinventory() {
        return CosmeticsMaxCount;
    }

    @Override
    public int householdgoodsCurrentinventory(Long id) {
        DistriBution distriBution = data.get(id);
        System.out.println("현재 생활용품 개수 = " + distriBution.getHouseholdgoods_count());
        return distriBution.getHouseholdgoods_count();
    }

    @Override
    public int kitchentoolsCurrentinventory(Long id) {
        DistriBution distriBution = data.get(id);
        System.out.println("현재 주방용품 개수 = " + distriBution.getKitchentools_count());
        return distriBution.getKitchentools_count();
    }

    @Override
    public int foodCurrentinventory(Long id) {
        DistriBution distriBution = data.get(id);
        System.out.println("현재 식품 개수 = " + distriBution.getFood_count());
        return distriBution.getFood_count();
    }

    @Override
    public int healthfoodCurrentinventory(Long id) {
        DistriBution distriBution = data.get(id);
        System.out.println("현재 건강보조식품 개수 = " + distriBution.getHealthfood_count());
        return distriBution.getHealthfood_count();
    }

    @Override
    public int cosmeticsCurrentinventory(Long id) {
        DistriBution distriBution = data.get(id);
        System.out.println("현재 화장품 개수 = " + distriBution.getCosmetics_count());
        return distriBution.getCosmetics_count();
    }


    private Connection getConnection()
    {
        return DBConnectionUtility.getConnection();
    }
}
