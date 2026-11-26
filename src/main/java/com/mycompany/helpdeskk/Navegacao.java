package com.mycompany.helpdeskk;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Navegacao {

    private static final Color AZUL = new Color(0, 0, 102);
    private static final Color BRANCO = Color.WHITE;

   
    public static void configurar(JFrame atual, JButton ativo,
            JButton dashboard, JButton chamados, JButton detalhes,
            JButton setores, JButton relatorios, JButton usuarios) {

        ligar(atual, ativo, dashboard, Dashboard::new);
        ligar(atual, ativo, chamados, Chamados::new);
        ligar(atual, ativo, detalhes, DetalheChamado::new);
        ligar(atual, ativo, setores, GerenciarSetoresEmpresa::new);
        ligar(atual, ativo, relatorios, Relatórios::new);
        ligar(atual, ativo, usuarios, GerenciarUsuários::new);
    }

    private static void ligar(JFrame atual, JButton ativo, JButton btn, Supplier<JFrame> tela) {
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (btn == ativo) {
            pintar(btn, true);         
            return;
        }

        pintar(btn, false);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { pintar(btn, true); }

            @Override
            public void mouseExited(MouseEvent e) { pintar(btn, false); }
        });
        btn.addActionListener(e -> ir(atual, tela.get()));
    }

    private static void ir(JFrame atual, JFrame destino) {
        destino.setBounds(atual.getBounds());            
        destino.setExtendedState(atual.getExtendedState());
        destino.setVisible(true);
        atual.dispose();
    }

    private static void pintar(JButton btn, boolean destacado) {
        btn.setBackground(destacado ? BRANCO : AZUL);
        btn.setForeground(destacado ? AZUL : BRANCO);
    }
}