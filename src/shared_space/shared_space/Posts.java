/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_space;

/**
 *
 * @author Sandeepa Ranathunga
 */
public class Posts {

    private int id;
    private String title;
    private String URL;
    private String body;
    private String autor;

    public Posts(int id, String title, String URL, String body, String autor) {
        this.id = id;
        this.title = title;
        this.URL = URL;
        this.body = body;
        this.autor = autor;
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @return the autor
     */
    public String getAutor() {
        return autor;
    }

}