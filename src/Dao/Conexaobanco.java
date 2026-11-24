/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexaobanco {

    private String servidor;
    private String banco;
    private String usuario;
    private String senha;
    private Connection conexao;

    public Conexaobanco() {
        this.servidor = "177.220.142.20";
        this.banco = "grupo03_projetomensal";
        this.usuario = "grupo03";
        this.senha = "2V2cXpNJHNZ03a9d@";
    }

    public boolean conectar() {
        try {
            this.conexao = DriverManager.getConnection(
                    "jdbc:mysql://" + this.servidor + "/" + this.banco,
                    this.usuario,
                    this.senha);
            return true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Connection getConnection() {
        return conexao;
    }

    public static void main(String[] args) {
        new Conexaobanco().conectar();
        System.out.println("Conectou!");
    }
}