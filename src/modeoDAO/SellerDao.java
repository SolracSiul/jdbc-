package modeoDAO;
import entites.Seller;

import java.util.List;

public interface SellerDao {
    void insert (SellerDao obj);
    void update(SellerDao obj);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<SellerDao> findAll();
}
