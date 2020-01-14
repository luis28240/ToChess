package com.ToChess.repository;

import com.ToChess.models.clubs.Club;
import com.ToChess.models.user.User;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.ToChess.repository.RepositoryUtils.*;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarde
 */
@Repository
public class RepositoryClubs {

    @Autowired
    private DataSource dataSource;

    public List<Club> getAllClubs() {
        String qGetAllClubs
                = "SELECT id_club,\n"
                + "       \"name\",\n"
                + "       id_leader,\n"
                + "       date_created,\n"
                + "       max_players\n"
                + "FROM   clubs";

        try (Connection cn = dataSource.getConnection();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(qGetAllClubs)) {

            ArrayList<Club> clubList = new ArrayList<>();

            while (rs.next()) {

                Club club = new Club();
                club.setId(rs.getInt("id_club"));
                club.setName(rs.getString("name"));
                club.setIdLeader(rs.getInt("id_leader"));
                club.setDateCreated(rs.getString("date_created"));
                club.setMaxUsers((Integer) rs.getObject("max_players"));

                clubList.add(club);
            }

            return clubList;

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void createClub(Club club) {
        String qCreateClub
                = "INSERT INTO clubs\n"
                + "    (\"name\",\n"
                + "     id_leader,\n"
                + "     date_created,\n"
                + "     max_players)\n"
                + "VALUES (?,\n"
                + "        ?,\n"
                + "        SYSDATE,\n"
                + "        ?)";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qCreateClub, new String[]{"id_club"})) {

            pSt.setString(1, club.getName());
            pSt.setInt(2, club.getIdLeader());
//            pSt.setString(3, club.getDateCreated());
            setNullableInt(pSt, 3, club.getMaxUsers());

            int clubsCreated = pSt.executeUpdate();

            //Asssign automatically Club to user
            if (clubsCreated > 0) {
                try (ResultSet generatedKey = pSt.getGeneratedKeys()) {
                    if (generatedKey.next()) {
//                    printMetaData(generatedKey);
                        long idClub = generatedKey.getLong(1);

                        System.out.println(String.valueOf(idClub));

                        String qAssignClub
                                = "UPDATE \"users\"\n"
                                + "SET    id_club = ?\n"
                                + "WHERE  id_user = ?";

                        try (PreparedStatement pStAssignClub = cn.prepareStatement(qAssignClub);) {
                            pStAssignClub.setLong(1, idClub);
                            pStAssignClub.setInt(2, club.getIdLeader());

                            pStAssignClub.executeUpdate();
                        }

                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> getClubMembers(int id) {
        String qGetClubMembers
                = "SELECT id_user,\n"
                + "       username,\n"
                + "       email\n"
                + "FROM   \"users\"\n"
                + "WHERE  id_club = ?";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qGetClubMembers);
                ResultSet rs = executeQuery(pSt, id)) {

            ArrayList<User> userList = new ArrayList<>();

            while (rs.next()) {
                User user = new User(rs.getString("username"));
                user.setId(rs.getInt("id_user"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }

            return userList;
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User getClubLeader(int id) {
        String qGetClubMembers
                = "SELECT id_user,\n"
                + "       username,\n"
                + "       email\n"
                + "FROM   \"users\"\n"
                + "WHERE  EXISTS (SELECT NULL \n"
                + "               FROM   clubs \n"
                + "               WHERE  id_leader = id_user\n"
                + "                      AND id_club = ?)";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qGetClubMembers);
                ResultSet rs = executeQuery(pSt, id)) {

            if (rs.next()) {
                User leader = new User(rs.getString("username"));
                leader.setId(rs.getInt("id_user"));
                leader.setEmail(rs.getString("email"));
                return leader;
            }

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Club getClub(int id) {
        String qGetClub
                = "SELECT id_club,\n"
                + "       \"name\",\n"
                + "       id_leader,\n"
                + "       date_created,\n"
                + "       max_players\n"
                + "FROM   clubs\n"
                + "WHERE  id_club = ?";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qGetClub);
                ResultSet rs = executeQuery(pSt, id)) {
            if (rs.next()) {

                Club club = new Club();
                club.setId(rs.getInt("id_club"));
                club.setName(rs.getString("name"));
                club.setIdLeader(rs.getInt("id_leader"));
                club.setDateCreated(rs.getString("date_created"));
                club.setMaxUsers((Integer) rs.getObject("max_players"));

                return club;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void joinClub(User user, int clubId) {
        String qSetClub
                = "UPDATE \"users\"\n"
                + "SET    id_club = ?\n"
                + "WHERE  id_user = ?";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qSetClub);) {

            pSt.setInt(1, clubId);
            pSt.setInt(2, user.getId());

            pSt.executeUpdate();
            user.setIdClub(clubId);

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leaveClub(User user) {
        String qSetClub
                = "UPDATE \"users\"\n"
                + "SET    id_club = NULL\n"
                + "WHERE  id_user = ?";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qSetClub);) {

            pSt.setInt(1, user.getId());

            pSt.executeUpdate();
            user.setIdClub(null);

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteClub(int id) {
        String qRemoveAllUsers
                = "UPDATE \"users\"\n"
                + "SET    id_club = NULL\n"
                + "WHERE  id_club = ?";

        String qDeleteClub
                = "DELETE FROM clubs\n"
                + "WHERE  id_club = ?";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pStRemoveAllUsers = cn.prepareStatement(qRemoveAllUsers);
                PreparedStatement pStDeleteClub = cn.prepareStatement(qDeleteClub);) {

            pStRemoveAllUsers.setInt(1, id);
            pStRemoveAllUsers.executeUpdate();

            pStDeleteClub.setInt(1, id);
            pStDeleteClub.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeLeaderClub(int id, int userId) {
        String qSetClub
                = "UPDATE clubs\n"
                + "SET    id_leader = ?\n"
                + "WHERE  id_club = ?";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qSetClub);) {

            pSt.setInt(1, userId);
            pSt.setInt(2, id);

            pSt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void banUserClub(int id, int userId) {
        String qSetClub
                = "UPDATE \"users\"\n"
                + "SET    id_club = NULL\n"
                + "WHERE  id_user = ?"
                + "       and id_club = ?";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qSetClub);) {

            pSt.setInt(1, userId);
            pSt.setInt(2, id);

            pSt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
