package Services;

import Entities.Article;
import Interfaces.IArticleService;
import Utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleService implements IArticleService<Article> {
    Connection connection;
    Statement statement;
    public ArticleService() {
        connection = MyDB.getInstance().getCon();
    }

    @Override
    public int add(Article article) {
        int rowsInserted = 0;
        try {
            java.sql.Date datec = new java.sql.Date(System.currentTimeMillis());
            String query = "INSERT INTO `article`(`titre`, `description`, `contenu`, `date_publication`," +
                    " `id_categorie`, `archived`, `id_user`, `auteur`) VALUES ('" + article.getTitre() + "','" + article.getDescription() + "'," +
                    "'" + article.getContenu() + "','" + datec + "','" + article.getId_categorie() + "','" + article.getArchived() + "','" + article.getId_user() + "'" +
                    ",'" + article.getAuteur() + "')";
            statement = connection.createStatement();
            rowsInserted = statement.executeUpdate(query);
            if (rowsInserted > 0) {
                System.out.println("Inserted successfully ! ");
                return rowsInserted;
            } else {
                System.out.println("Insert failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsInserted;
    }

    /**
     * Afficher al of the non archived articles in the database
     *
     * @return Returns List<Article>
     * @throws java.sql.SQLException
     */
    @Override
    public List<Article> afficher() {
        List<Article> articles = new ArrayList<>();
        try{
            String query="SELECT * FROM `article` WHERE `archived`=0";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Article article = new Article();
                article.setId_article(resultSet.getInt("id_article"));
                article.setTitre(resultSet.getString("titre"));
                article.setDescription(resultSet.getString("description"));
                article.setContenu(resultSet.getString("contenu"));
                article.setDate_publication(resultSet.getDate("date_publication"));
                article.setId_categorie(resultSet.getInt("id_categorie"));
                article.setArchived(resultSet.getInt("archived"));
                article.setId_user(resultSet.getInt("id_user"));
                article.setAuteur(resultSet.getString("auteur"));
                articles.add(article);
            }
            return  articles;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return articles;
    }

    /**
     * Afficher  the archived articles in the database only
     *
     * @return Returns List<Article>
     * @throws java.sql.SQLException
     */
    @Override
    public List<Article> afficherArchived() {

        List<Article> articles = new ArrayList<>();
        try{
            String query="SELECT * FROM `article` WHERE `archived`<>0";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Article article = new Article();
                article.setId_article(resultSet.getInt("id_article"));
                article.setTitre(resultSet.getString("titre"));
                article.setDescription(resultSet.getString("description"));
                article.setContenu(resultSet.getString("contenu"));
                article.setDate_publication(resultSet.getDate("date_publication"));
                article.setId_categorie(resultSet.getInt("id_categorie"));
                article.setArchived(resultSet.getInt("archived"));
                article.setId_user(resultSet.getInt("id_user"));
                article.setAuteur(resultSet.getString("auteur"));
                articles.add(article);
            }
            return  articles;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return articles;
    }

    /**
     * Updated the non archived articles in the database using the ID
     *
     * @return Returns true (with success msg) if Updated else false (with fail msg)
     * @throws java.sql.SQLException
     */
    @Override
    public Boolean update(Article article) {
        String query ="UPDATE `article` SET `titre`='"+article.getTitre()+"',`description`='"+article.getDescription()+"'," +
                     "`contenu`='"+article.getContenu()+"',`date_publication`='"+article.getDate_publication()+"',`id_categorie`='"+article.getId_categorie()+"'," +
                     "`id_user`='"+article.getId_user()+"',`auteur`='"+article.getAuteur()+"' WHERE id_article="+article.getId_article();
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated >0){
                System.out.println("Updated article successfully ! ");
            }else {
                System.out.println("Update failed");
            }
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return  false;
        }
    }
    /**
     * Soft delete of an article, changed the `archived` attribute to 0
     *
     * @return Returns true (with success msg) if Updated else false (with fail msg)
     * @throws java.sql.SQLException
     */
    @Override
    public Boolean delete(Article article) {
        String query ="UPDATE `article` SET `archived`='"+1+"' WHERE id_article="+article.getId_article();
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated >0){
                System.out.println("Deleted article successfully ! ");
            }else {
                System.out.println("Delete article failed");
            }
            return  true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
