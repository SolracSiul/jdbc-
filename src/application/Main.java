package application;

import entites.Department;
import entites.Seller;
import modeoDAO.DaoFactory;
import modeoDAO.SellerDao;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

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
        System.out.println("\n=== TESTEe: SELLER insert");
        Seller newSeller = new Seller(null, "sorak", "srk@gmail.com", new Date(), 4000.0, dep);
        sellerDao.insert(newSeller);
        System.out.println("Insert! New id = " + newSeller.getId());

        System.out.println("\n=== TESTEe: SELLER update");
        seller = sellerDao.findById(1);
        seller.setName("SorakSiul");
        sellerDao.update(seller);
        System.out.println("Atualizado");

        System.out.println("\n=== TEST 6: seller delete =====");
        System.out.println("informe o id para ser deletado");
        int id = scan.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Deletado com sucesso");


        scan.close();

    }
}