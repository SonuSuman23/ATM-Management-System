
package bank.management.system;
import javax.swing.*;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;
public class FastCash extends JFrame implements ActionListener{
    JButton deposite,cashWithdrawl,ministatement,pinchange,fastcash,balanceenquey,Exit;
    String pinnumber;
    FastCash(String pinnumber){
        this.pinnumber = pinnumber;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900,900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        JLabel text = new JLabel("Select Withdrawl Amount");
        text.setBounds(200,100,700,35);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("System",Font.BOLD,16));
        image.add(text);
        
        deposite = new JButton("Rs 100");
        deposite.setBounds(120,230,150,30);
        deposite.addActionListener(this);
        image.add(deposite);
        
        cashWithdrawl = new JButton("Rs 500");
        cashWithdrawl.setBounds(400,230,150,30);
        cashWithdrawl.addActionListener(this);
        image.add(cashWithdrawl);
        
        fastcash = new JButton("Rs 1000");
        fastcash.setBounds(120,270,150,30);
        fastcash.addActionListener(this);
        image.add(fastcash);
        
        ministatement = new JButton("Rs 2000");
        ministatement.setBounds(400,270,150,30);
        ministatement.addActionListener(this);
        image.add(ministatement);
        
        pinchange = new JButton("Rs 5000");
        pinchange.setBounds(120,310,150,30);
        pinchange.addActionListener(this);
        image.add(pinchange);
        
        balanceenquey = new JButton("Rs 10000");
        balanceenquey.setBounds(400,310,150,30);
        balanceenquey.addActionListener(this);
        image.add(balanceenquey);
        
        Exit = new JButton("BACK");
        Exit.setBounds(400,350,150,30);
        Exit.addActionListener(this);
        image.add(Exit);
        

        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
        
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==Exit){
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
         //System.exit(0);
        }else{
            String amount = ((JButton)ae.getSource()).getText().substring(3); //500
            Conn c = new Conn();
            try{
                ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pinnumber+"'");
                int balance = 0;
                while(rs.next()){
                    if(rs.getString("type").equals("Deposit")){
                        balance += Integer.parseInt(rs.getString("amount"));
                    }else{
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }
                if(ae.getSource()!=Exit && balance<Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }
                Date date = new Date();
                String query = "insert into bank values('"+pinnumber+"','"+date+"','withdrawl','"+amount+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "RS "+amount+" Debited Successfully");
                
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    public static void main(String[] args) {
        new FastCash("");
    }
}
