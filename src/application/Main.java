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
        System.out.println("\n=== TESTE2: SELLER findByDepartment");
        Department dep = new Department(4,null);
        List<Seller> list = sellerDao.findByDepartment(dep);
        for(Seller obj : list){
            System.out.println(obj);
        }
        System.out.println("\n=== TESTEe: SELLER findAll");
        list = sellerDao.findAll();
        for(Seller obj : list){
            System.out.println(obj);
        }

    }
}