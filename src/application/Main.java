package application;

import entites.Seller;
import modeoDAO.DaoFactory;
import modeoDAO.SellerDao;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("=== TESTE1: SELLER findByID");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

    }
}