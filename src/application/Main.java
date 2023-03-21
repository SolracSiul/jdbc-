package application;

import entites.Department;
import entites.Seller;
import modeoDAO.DaoFactory;
import modeoDAO.SellerDao;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("=== TESTE1: SELLER findByID");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println("\n=== TESTE1: SELLER findByID");
        Department dep = new Department(2,null);
        List<Seller> list = sellerDao.findByDepartment(dep);
        for(Seller obj : list){
            System.out.println(obj);
        }
    }
}