/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface; 


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author DanielCardoso
 */
public class Jogo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Jogo.class.getName());

    
    //JButton presica da importançao
    //matriz com 10 - linhas e 10 colunas 
    
    //btnCampos é o nome da variavel - (voce escolhe) 
    //É O LOCAL ONDE CRIAMOPS NOSSAS VARIAVEIS 
   JButton [][] btnCampos = new JButton[10][10];
    
    //MATRIZ PARA GUARDAR AS BOMBAS - true p/bomba, false p/ numero 
   boolean [][] bombas = new boolean [10][10];
   
    //METRIZPARA PARA GUARDAR OS CAMPOS QUE FOREM ABRETOS  
   boolean[][] abertos = new boolean [10][10];
   
   
   
   
   int quantidadeBombas=15;
   int quantidadecasasAbertas=0;
   
   boolean jogoEncerrado= false; 
    int segundosPassados = 0;
    Timer cronometro;
   
  
   
   
    
   
   
   
    /**
     * Creates new form Jogo
     */
    //CONSTRUTORA DA CLASSE/TELA - SEM ELE A TELA NÃO FUNCIONA 
    public Jogo() {
        initComponents(); 
         //definir tamanho para o painel
         Painelcampo.setPreferredSize(new Dimension(900,700));
    CriarTabuleiro();
    }
 // criar nossos metodos

 public void CriarTabuleiro(){
        // definir que o painel será divido em 10 linhas e 10 colunas
        // com altura 2px e largura 2px
        Painelcampo.setLayout(new GridLayout(10,10,2,2));
        
        for(int coluna=0;coluna<=9;coluna++){
            for(int linha=0;linha<=9;linha++){
                // váriavel botão para guardar os dados provisorios
                JButton botao = new JButton();
                botao.setFont(new Font("Arial",Font.BOLD,16));// fonte
                botao.setBackground(new Color(255,192,230));// cor de fundo
                botao.setForeground(Color.WHITE);// cor de texto
                
                // remover marcas do botão que vem por padrão
                botao.setFocusPainted(false);
                botao.setEnabled(false);
                
                final int linhaSelecionada= linha;
                final int colunaSelecionada = coluna;
                
                //adicionar o evento de clique para abrir as casas
                botao.addActionListener((ActionEvent Evento)->{
                       abrirBotao(linhaSelecionada,colunaSelecionada); 
                
                            });
                
                
                //adicionar o botao dentro da matriz
                btnCampos[linha][coluna]=botao;
                // adicionar ele dentro do painel
                Painelcampo.add(botao);
                
            }// fim do 2° for
        }// fim do 1° for
        
    }// fim do metodo CriarTabuleiro
    


// ADICIONAR BOMBAS
public void AdicionarBombas() {

    Random random = new Random();

    int BombasAdicionadas = 0;

    while (BombasAdicionadas < quantidadeBombas) {

       int linha = random.nextInt(10);
int coluna = random.nextInt(10);

        if (!bombas[linha][coluna])     {

            bombas[linha][coluna] = true;

            BombasAdicionadas++;
        }
    }
}
 public void IniciarJogo(){
        LimparJogo();
        //chamar o metodo adicionarBombas
        AdicionarBombas();
        IniciarCronometro();
        //depois precisamos iniciar os botoes do jogo
        for(int colunas=0;colunas<=9;colunas++){
            for(int linhas=0;linhas<=9;linhas++){
                JButton botao = btnCampos[linhas][colunas];
                //deixar os botoes visiveis e clicaveis
                botao.setEnabled(true);
            }//fim do 2° for
        }//fim do 1° for
        btninicar.setText("REINICIAR");
    }//fim do iniciar jogo
    
    
    public void abrirBotao(int linha, int coluna){
        // verificar se o jogo foi finalizado
        if(jogoEncerrado) return;
        
        //verificar se o botao ja foi aberto
        if(abertos[linha][coluna]) return;
      
        /*se o jogo ainda estiver rodando e o botão ainda não tiver
        sido aberto - então vamos abrir o botão*/
        abertos[linha][coluna]=true;
        quantidadecasasAbertas++;
        
        // acessar o que tem dentro do botão
        JButton botao = btnCampos[linha][coluna];
        //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
        if(bombas[linha][coluna]){
            //variavel que recebe nossa imagem
           ImageIcon imgBomba = new ImageIcon( 
                   getClass().getResource("/assets/bomb.png"));
           //colocar a imagem no botao
           botao.setIcon(imgBomba);
           FinalizarJogo(false);
           return;
        }else{
            ImageIcon imgBandeira = new ImageIcon(
                getClass().getResource("/assets/flag.png"));
            botao.setIcon(imgBandeira);
            return;
        }
        
    }// fim do metodo abrirBotao
    
    
    // este metodo informa quando a pessoa perder ou ganhar o jogo
    public void FinalizarJogo(boolean venceu){
       mostrarBombas();
        //vamos informar que o jogo acabou
        jogoEncerrado=true;
        cronometro.stop();
        
        //verificar se a pessa venceu ou não
        if(venceu){
            JOptionPane.showMessageDialog(
                    this,"Parabéns você venceu!");
            LimparJogo();
        }else{
            JOptionPane.showMessageDialog(
                    this,"Ops, você perdeu o jogo!");
            LimparJogo();
        }
       }//fim do FinalizarJogo
    
    
    public void VerificarVitoria(){
        // armazenar a quantidade de casas com bandeiras
        int casasSemBomba= 100 - quantidadeBombas;
        //se a pessoa abriu todas as bandeiras e não abriu nenhuma bomba
        // então ela venceu o jogo, e o finalizarJogo imprime a mensagem
        if(quantidadecasasAbertas == casasSemBomba){
            FinalizarJogo(true);
        }
  
        
    }
    
    
    public void IniciarCronometro(){
        // zerar o cronometro caso tenha tido um jogo anterior
        if(cronometro !=null){
            cronometro.stop();
        }
        // reseta o cronometro
        segundosPassados = 0;
        tFTempo.setText("00:00");
        
        // converter o tempo em minutos e segundos
        // o cronometro conta de 1 em 1 segundo, e vai convertendo
        cronometro = new Timer(1000, Evento->{
            segundosPassados++;
            int minutos = segundosPassados/60;
            int horas = minutos/60;
            int segundo = segundosPassados%60;
            //mostrar o tempo dentro da váriavel
            tFTempo.setText(
            String.format("%02d:%02d:%02d",horas,minutos,segundo));
                       
        });
        cronometro.start();
        
        
    }
    
    
    public void LimparJogo(){
         quantidadecasasAbertas=0;
         jogoEncerrado=false;
        
         
        for(int coluna=0;coluna<=9;coluna++){
            for(int linha=0;linha<=9;linha++){
                bombas[linha][coluna]=false;
                abertos[linha][coluna]=false;
               
                
                //limpeza dos botões
                JButton botao = btnCampos[linha][coluna];
                botao.setIcon(null);
                
            }//fim do 2° for
        }//fim do 1° for
         AdicionarBombas(); 
         IniciarCronometro();
        
    }//fim do LimparJogo
    
    
    public void mostrarBombas(){
       for(int coluna=0;coluna<=9;coluna++){
           for(int linha=0;linha<=9;linha++){
                JButton botao = btnCampos[linha][coluna];
                //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
                if(bombas[linha][coluna]){
                    //variavel que recebe nossa imagem
                   ImageIcon imgBomba = new ImageIcon( 
                           getClass().getResource("/assets/bomb.png"));
                   //colocar a imagem no botao
                   botao.setIcon(imgBomba);
                   
                }//fim do if
           }//fim do 2° for
       }// fim do 1° for      
    }// fim do mostrarBombas

          
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jProgressBar1 = new javax.swing.JProgressBar();
        jTextField1 = new javax.swing.JTextField();
        btninicar = new javax.swing.JButton();
        CampoMinado = new javax.swing.JLabel();
        tFTempo = new javax.swing.JTextField();
        Painelcampo = new javax.swing.JPanel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btninicar.setBackground(new java.awt.Color(255, 51, 255));
        btninicar.setText("inicar");
        btninicar.addActionListener(this::btninicarActionPerformed);

        CampoMinado.setBackground(new java.awt.Color(255, 255, 255));
        CampoMinado.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        CampoMinado.setForeground(new java.awt.Color(255, 0, 204));
        CampoMinado.setText("CampoMinado");
        CampoMinado.setOpaque(true);

        tFTempo.setText("00:00");
        tFTempo.addActionListener(this::tFTempoActionPerformed);

        Painelcampo.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout PainelcampoLayout = new javax.swing.GroupLayout(Painelcampo);
        Painelcampo.setLayout(PainelcampoLayout);
        PainelcampoLayout.setHorizontalGroup(
            PainelcampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 879, Short.MAX_VALUE)
        );
        PainelcampoLayout.setVerticalGroup(
            PainelcampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CampoMinado, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btninicar, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tFTempo))
                .addGap(330, 330, 330))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(Painelcampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(162, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CampoMinado, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btninicar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tFTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56)
                .addComponent(Painelcampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tFTempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tFTempoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tFTempoActionPerformed

    private void btninicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninicarActionPerformed
        // TODO add your handling code here:
        IniciarJogo();
    }//GEN-LAST:event_btninicarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Jogo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CampoMinado;
    private javax.swing.JPanel Painelcampo;
    private javax.swing.JButton btninicar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField tFTempo;
    // End of variables declaration//GEN-END:variables

   
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

