package modeoDAO;
import entites.Department;
import entites.Seller;

import java.util.List;

public interface SellerDao {
    void insert (SellerDao obj);
    void update(SellerDao obj);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
}
