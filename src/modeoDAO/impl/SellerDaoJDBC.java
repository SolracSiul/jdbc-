package modeoDAO.impl;

import db.DB;
import db.DbException;
import modeoDAO.SellerDao;
import entites.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entites.Department;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    public void insert(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller"
                    + "(Name, email, BirthDate, BaseSalary, DepartmentId) "
                    +"VALUES"
                    +" (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, String.valueOf(new Date(obj.getBirthDate().getTime())));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Erro inesperado, nenhuma linha afetada");
            }

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                        "UPDATE seller "
                            + "SET Name= ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?");
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, String.valueOf(new Date(obj.getBirthDate().getTime())));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6,obj.getId());

            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

            st.setInt(1, id);
            st.executeUpdate();
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
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
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name AS DepName "
                            + "FROM seller "
                            + "INNER JOIN department ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if(dep == null){
                    dep = instanciateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instanciateSeller(rs,dep);
                list.add(obj);
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name AS DepName "
                            + "FROM seller "
                            + "INNER JOIN department ON seller.DepartmentId = department.Id "
                            + "WHERE Department.Id = ? "
                            + "ORDER BY Name");
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if(dep == null){
                    dep = instanciateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instanciateSeller(rs,dep);
                list.add(obj);
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
