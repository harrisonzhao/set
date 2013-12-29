/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mypackage.hello;

/**
 *
 * @author David
 * This is a test class from the tutorial
 * https://netbeans.org/kb/docs/web/quickstart-webapps.html
 */
public class NameHandler {

    private String name;

    /** Creates a new instance of NameHandler */
    public NameHandler() {
       name = null;
    }

    public String getName() {
       return name;
    }

    public void setName(String name) {
       this.name = name;
    }

}
