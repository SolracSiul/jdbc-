package modeoDAO.impl;

import db.DB;
import db.DbException;
import modeoDAO.SellerDao;
import entites.Seller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import entites.Department;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(SellerDao obj) {

    }

    @Override
    public void update(SellerDao obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name AS DepName "
                            + "FROM seller "
                            + "INNER JOIN department ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                Department dep = instanciateDepartment(rs);
                Seller obj = instanciateSeller(rs,dep);
                return obj;
            }
            return null;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instanciateSeller(ResultSet rs,Department dep) throws SQLException {
        Seller sellerTemp = new Seller();
        sellerTemp.setId(rs.getInt("Id"));
        sellerTemp.setName(rs.getString("Name"));
        sellerTemp.setEmail(rs.getString("Email"));
        sellerTemp.setBaseSalary(rs.getDouble("BaseSalary"));
        sellerTemp.setBirthDate(rs.getDate("BirthDate"));
        sellerTemp.setDepartment(dep);
        return sellerTemp;
    }

    private Department instanciateDepartment(ResultSet rs) throws SQLException {
        Department depTemp = new Department();
        depTemp.setId(rs.getInt("DepartmentId"));
        depTemp.setName(rs.getString("DepName"));
        return depTemp;
    }

    @Override
    public List<SellerDao> findAll() {
        return null;
    }
}
